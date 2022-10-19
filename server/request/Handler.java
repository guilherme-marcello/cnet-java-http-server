package request;

import java.net.Socket;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Handler extends Thread {
    
    private Socket socket;
    private int id;
    private List<Integer> idRegistry;
    public Handler(Socket socket, int id, List<Integer> idRegistry) {
    	this.idRegistry = idRegistry;
        this.socket = socket;
        this.id = id;
    }

    @Override 
    public void run() {
        System.out.printf("[Thread-%d] Handling new connection!\n", this.id);
        try {
        	PrintWriter out = new PrintWriter(
                    this.socket.getOutputStream(), true 
                );
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    this.socket.getInputStream() 
                ) 
            );
            if(this.idRegistry.size() == 5) {
            	out.println("HTTP/1.1 503 Service Unavailable " + Util.CRLF);
            }else {
            	this.idRegistry.add(this.id);
            	// parse socket info as a http request
				RequestInfo info = new RequestInfo(in);
				System.out.println(info);

				System.out.println("Preparing response");

				out.println("HTTP/1.1 200 OK " + Util.CRLF);
				this.idRegistry.remove(Integer.valueOf(id));
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
