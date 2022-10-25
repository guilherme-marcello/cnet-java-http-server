import request.Util;
import request.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * this class provides methods to communicate with a server
 */
public class MyHttpClient {
	
    private final int PORT;
    private final String HOST_NAME;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private Logger logger;
    	
     /**
     * Creates a MyHttpClient with of a server with a given hostname and connects trough a given portNumber
     * @param hostName server hostname
     * @param portNumber server port to make connection
     * @throws IOException
     */
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		this.HOST_NAME = hostName;
		this.PORT = portNumber;
	
		this.socket = new Socket(this.HOST_NAME,this.PORT);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(),true);	
		
		this.logger = new Logger();
	}
	
	/**
	 * Sends a http GET request 
	 * @param ObjectName name of the file to access
	 * @throws IOException
	 */
	public void getResource(String ObjectName) throws IOException {
		this.logger.info("Making GET request to server");
		out.print(
			String.format(
				"GET /%s HTTP/1.1%sHost: %s%s",
				ObjectName, Util.CRLF, this.HOST_NAME, Util.CRLF
			)
		);
		out.flush();
		this.logger.answer(in);
	}
	
	/**
	 * Sends a http POST request
	 * @param data data to send to the server
	 * @throws IOException
	 */
	public void postData(String[] data) throws IOException {
		this.logger.info("Making POST request to server");
		out.print(
			String.format(
				"POST /simpleForm.html HTTP/1.1%sHost: %s%s%s%s",
				Util.CRLF, this.HOST_NAME, Util.CRLF, Util.CRLF, String.format("{%s,%s}", data[0], data[1])
			)
		);
		out.flush();
		this.logger.answer(in);
	}
	
	/**
	 * Sends an http request with a method name not supported by MyHttpServer
	 * @param wrongMethodName the unimplemented method to send
	 * @throws IOException
	 */
	public void sendUnimplementedMethod (String wrongMethodName) throws IOException {
		this.logger.info("Making a request to an unimplementedMethod to server");
		out.print(
			String.format(
				"%s /index.html HTTP/1.1%s",
				wrongMethodName, Util.CRLF
			)
		);
		out.flush();
		this.logger.answer(in);
	}
	
	/**
	 * Sends a malformed http GET of types i -> character "\r\n" not present after the request line, 
	 * ii -> presence of additional space characters between the fields of the request line
	 * iii -> HTTP version field not present in the request
	 * @param type the type of malformedRequest to send
	 * @throws IOException
	 */
	public void malformedRequest(int type) throws IOException {
		switch (type) {
			case 1:
				this.logger.info("Making a malformed request of the i type to server");
				out.print("GET /index.html HTTP/1.1");
				break;
			case 2: 
				this.logger.info("Making a malformed request of the ii type to server");
				out.print("GET  /index.html  HTTP/1.1  " + Util.CRLF);	
				break;
			case 3: 
				this.logger.info("Making a malformed request of the iii type to server");
				out.print("GET /index.html" + Util.CRLF);
				break;
			}
		out.flush();
		this.logger.answer(in);
	}
	
	/**
	 * closes the communication between client and server
	 */
	public void close() {
		try {
			out.print("DELETE /session HTTP/1.1" + Util.CRLF + "Connection: close" + Util.CRLF);
			out.flush();
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
