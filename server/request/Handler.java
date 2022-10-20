package request;

import java.net.Socket;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;

class HandlerLogger {
    private String prefix;
    public HandlerLogger(int id) {
        this.prefix = String.format("[Thread-%d]", id);
    }

    private void printWithLevel(String level, String content) {
        System.out.println(
            String.format("%s[%s] |%s| - %s", this.prefix, new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), level, content)
        );
    }

    public void info(String content) {
        this.printWithLevel("INFO", content);
    }

    public void error(String content) {
        this.printWithLevel("ERROR", content);
    }


}

public class Handler extends Thread {
    
    private Socket socket;
    private int id;
    private HashMap<String, String> contentDirectory;
    private HandlerLogger logger;
    public Handler(Socket socket, int id, HashMap<String, String> contentDirectory) {
        this.socket = socket;
        this.id = id;
        this.logger = new HandlerLogger(this.id);
        this.contentDirectory = contentDirectory;
    }

    @Override 
    public void run() {
        this.logger.info("Handling new connection!");
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    this.socket.getInputStream()
                )
            );

            PrintWriter out = new PrintWriter(
                this.socket.getOutputStream(), true 
            );

            RequestInfo info = new RequestInfo(in);

            this.logger.info("Preparing response!");
            RequestLine requestLine = info.getRequestLine();

            if (requestLine.isValid()) {
                switch (requestLine.getMethod()) {
                    case "GET":
                        if (this.contentDirectory.containsKey(requestLine.getEndpoint())) {
                            this.logger.info("Received a GET request to an existing resource! Returning 200");
                            out.println("HTTP/1.1 200 OK" + Util.CRLF + Util.CRLF + this.contentDirectory.get(requestLine.getEndpoint()) + Util.CRLF);
                        }
                        else {
                            this.logger.info("Received a GET request to an unavailable resource! Returning 404");
                            out.println("HTTP/1.1 404 Not Found");
                        }
                        break;
                    case "POST":
                        out.println("HTTP/1.1 200 OK");
                        this.logger.info("Payload is -> " + info.getPayload());
                        break;
                    default:
                        this.logger.error("Method is not implemented (is not GET nor POST)! Returning 405 Method Not Allowed");
                        out.println("HTTP/1.1 405 Method Not Allowed");
                        break;
                }
            }
            else {
                this.logger.error("Invalid request! Returning 400 Bad Request");
                out.println("HTTP/1.1 400 Bad Request");
            }
            in.close();
            out.close();
            socket.close();
            this.logger.info("Connection closed");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
