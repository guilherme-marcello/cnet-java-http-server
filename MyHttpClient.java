import request.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

class ClientLogger {
    public ClientLogger() {      
    }
    private void printWithLevel(String level, String content) {
        System.out.println(
            String.format("[%s] |%s| - %s", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), level, content)
        );
    }

    public void info(String content) {
        this.printWithLevel("INFO", content);
    }

    public void answer(BufferedReader in) throws IOException {
        System.out.println(
    	   String.format("[%s] |%s|", new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), "Server Answer")
    	);
    	String s = in.readLine();
    	while (s != null) { 		
            System.out.println(s);
            if(in.ready()) {
            	s = in.readLine();
            }
            else {
            	return;
            }
        }
    }
}
public class MyHttpClient {
	
    private final int PORT;
    private final String HOST_NAME;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private ClientLogger logger;
    
	public MyHttpClient(String hostName, int portNumber) throws IOException {
		this.HOST_NAME = hostName;
		this.PORT = portNumber;
	
		this.socket = new Socket(this.HOST_NAME,this.PORT);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(),true);	
		
		this.logger = new ClientLogger();
	}

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
