package request;

import java.net.Socket;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Handler extends Thread {
    
    private Socket socket;
    private int id;
    private HashMap<String, String> contentDirectory;
    public Handler(Socket socket, int id, HashMap<String, String> contentDirectory) {
        this.socket = socket;
        this.id = id;
        this.contentDirectory = contentDirectory;
    }

    @Override 
    public void run() {
        System.out.printf("[Thread-%d] Handling new connection!\n", this.id);
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    this.socket.getInputStream() 
                ) 
            );

            // parse socket info as a http request
            RequestInfo info = new RequestInfo(in);
            System.out.println(info);

            System.out.println( "Preparing response" );
            PrintWriter out = new PrintWriter(
                this.socket.getOutputStream(), true 
            );
            if (this.contentDirectory.containsKey(info.getRequestLine().getEndpoint())) {
            	out.println("HTTP/1.1 200 OK " + Util.CRLF);
            }
            else {
            	out.println("HTTP/1.1 404 page not found ");
            }
            in.close();
            out.close();
            socket.close();
            System.out.println( "Connection closed" );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
