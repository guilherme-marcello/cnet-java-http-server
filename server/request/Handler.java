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

            // parse socket info as a http request
            RequestInfo info = new RequestInfo(in);
            //System.out.println(info);

            this.logger.info("Preparing response" );
            PrintWriter out = new PrintWriter(
                this.socket.getOutputStream(), true 
            );

            RequestLine requestLine = info.getRequestLine();

            if (requestLine.isValid()) {
                if (this.contentDirectory.containsKey(requestLine.getEndpoint())) {
                    this.logger.info("contem chaveeee -> ");
                    this.logger.info(requestLine.getEndpoint());
                    out.println("HTTP/1.1 200 OK " + Util.CRLF);
                }
                else {
                    this.logger.info("nao contem chaveeee");
                    this.logger.info(requestLine.getEndpoint());
                    out.println("HTTP/1.1 404 page not found ");
                }
            }
            else {
                this.logger.error("Invalid request!!");
            }


            in.close();
            out.close();
            socket.close();
            this.logger.info( "Connection closed" );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
