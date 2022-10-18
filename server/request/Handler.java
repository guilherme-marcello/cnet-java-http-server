package request;

import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Handler extends Thread {
    
    private Socket socket;
    private int id;
    public Handler(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
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

            out.println("HTTP/1.1 200 OK " + Util.CRLF);

            in.close();
            out.close();
            socket.close();
            System.out.println( "Connection closed" );

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}