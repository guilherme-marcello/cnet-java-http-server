import request.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
		out.print(
			String.format(
				"GET /%s HTTP/1.1%sHost: %s%s",
				ObjectName, Util.CRLF, this.HOST_NAME, Util.CRLF
			)
		);
		out.flush();
	}
	
	public void postData(String[] data) throws IOException {
		out.print(
			String.format(
				"POST /simpleForm.html HTTP/1.1%sHost: %s%s%s%s",
				Util.CRLF, this.HOST_NAME, Util.CRLF, Util.CRLF, String.format("{%s,%s}", data[0], data[1])
			)
		);
		out.flush();
	}
	
	public void sendUnimplementedMethod (String wrongMethodName) throws IOException {
		out.print(
			String.format(
				"%s /index.html HTTP/1.1%s",
				wrongMethodName, Util.CRLF
			)
		);
		out.flush();
	}
	
	public void malformedRequest(int type) throws IOException {
		switch (type) {
			case 1:
				out.print("GET /index.html HTTP/1.1");
			case 2: 
				out.print("GET  /index.html  HTTP/1.1  " + Util.CRLF);	
			case 3: 
				out.print("GET /index.html" + Util.CRLF);
			}
		out.flush();
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
