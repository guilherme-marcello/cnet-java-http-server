import request.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import java.net.SocketException;



public class MyHttpClient {
	
    private final int PORT;
    private final String HOST_NAME;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		this.HOST_NAME = hostName;
		this.PORT = portNumber;
	
		this.socket = new Socket(this.HOST_NAME,this.PORT);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(),true);		
	}

	public void getResource(String ObjectName) throws IOException {
		String content = "GET /" + ObjectName + " HTTP/1.1" + Util.CRLF + "Host: " + this.HOST_NAME + ":" + String.valueOf(this.PORT) + Util.CRLF;
		out.print(content);
		out.flush();
	}
	
	public void postData(String[] data) throws IOException {
		
		System.out.print("POST /simpleForm.html HTTP/1.1" + Util.CRLF +
					"Host: " + HOST_NAME + Util.CRLF +
					Util.CRLF +
					String.format("{%s,%s}", data[0], data[1])
		);

		out.print("POST /simpleForm.html HTTP/1.1" + Util.CRLF +
					"Host: " + HOST_NAME + Util.CRLF +
					Util.CRLF +
					String.format("{%s,%s}", data[0], data[1])
		);
		out.flush();
	}
	
	public void sendUnimplementedMethod (String wrongMethodName) throws IOException {
		
		out.println(wrongMethodName + " " + "/ " + Util.CRLF );
	}
	
	public void malformedRequest(int type) throws IOException {
		
		switch (type) {
		case 1: out.println("GET /index.html HTTP/1.1" +
				"\n Host: " + HOST_NAME + Util.CRLF);
		case 2: out.println("GET  /index.html HTTP/1.1 " + Util.CRLF +
				"\n Host: " + HOST_NAME + Util.CRLF);	
		case 3: out.println("GET /index.html" + Util.CRLF +
				"\n Host: " + HOST_NAME + Util.CRLF);
		}
	}
	
	public void close() {
		
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
