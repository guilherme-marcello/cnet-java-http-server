package request;

// socket-related imports
import java.net.Socket;
import java.net.SocketException;

// data handling-related imports
import java.util.HashMap;
import java.util.List;

// IO-related imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

// logging/debugging-related imports
import java.text.SimpleDateFormat;
import java.util.Date;

class HandlerLogger {
	private String prefix;

	public HandlerLogger(int id) {
		this.prefix = String.format("[Thread-%d]", id);
	}

	private void printWithLevel(String level, String content) {
		System.out.println(String.format("%s[%s] |%s| - %s", this.prefix,
				new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date()), level, content));
	}

	public void info(String content) {
		this.printWithLevel("INFO", content);
	}

	public void error(String content) {
		this.printWithLevel("ERROR", content);
	}

}

public class Handler extends Thread {
	private static final int MAX_THREADS = 5;
	private static final String THREADS_EXCEEDED_ERROR = String.format(
			"The server does not handle more than %d requests simultaneously. Discarding this request.", MAX_THREADS);

	private boolean closeConnection = false;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private int id;
	private HashMap<String, String> contentDirectory;
	private List<Integer> idRegistry;
	private HandlerLogger logger;

	public Handler(Socket socket, int id, HashMap<String, String> contentDirectory, List<Integer> idRegistry)
			throws SocketException, IOException {
		this.socket = socket;

		this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		this.out = new PrintWriter(this.socket.getOutputStream(), true);

		this.id = id;
		this.logger = new HandlerLogger(this.id);
		this.contentDirectory = contentDirectory;
		this.idRegistry = idRegistry;

	}

	@Override
	public void run() {
		this.logger.info("Handling new client!");
		this.idRegistry.add(this.id);
		while (!this.socket.isClosed()) {
			try {
				this.handle_request();
			} catch (Exception e) {
				this.logger.error("Unable to handle request.");
			}

		}
		this.logger.info("Finished client handling!");
		this.idRegistry.remove(Integer.valueOf(id));
	}

	private void handle_request() throws IOException, InterruptedException {
		if (this.socket.getInputStream().available() <= 0)
			return;

		this.logger.info("Handling new request!");

		if (this.idRegistry.size() > MAX_THREADS) {
			this.logger.error(THREADS_EXCEEDED_ERROR);
			out.println("HTTP/1.1 503 Service Unavailable " + Util.CRLF);
			this.socket.close();
		} else {

			this.logger.info("Parsing data from socket!");
			RequestInfo info = new RequestInfo(in);
			this.logger.info("Finished request info parsing!");

			RequestLine requestLine = info.getRequestLine();
			RequestHeaders requestHeaders = info.getRequestHeaders();

			this.logger.info(info.toString());
			this.logger.info("Preparing response!");

			if (requestLine.isValid() && requestHeaders.isValid()) {
				if (requestHeaders.contains("Connection: close" + Util.CRLF)) {
					this.logger.info("Received a close connection header. Closing connection after sending response!");
					this.closeConnection = true;
				}

				switch (requestLine.getMethod()) {
				case "GET":
					if (this.contentDirectory.containsKey(requestLine.getEndpoint())) {
						this.logger.info("Received a valid GET request to an existing resource! Returning 200");
						out.println("HTTP/1.1 200 OK" + Util.CRLF + Util.CRLF
								+ this.contentDirectory.get(requestLine.getEndpoint()) + Util.CRLF);
					} else {
						this.logger.info("Received a valid GET request to an unavailable resource! Returning 404");
						out.println("HTTP/1.1 404 Not Found");
					}
					break;
				case "POST":
					out.println("HTTP/1.1 200 OK");
					break;
				default:
					this.logger
							.error("Method is not implemented (is not GET nor POST)! Returning 405 Method Not Allowed");
					out.println("HTTP/1.1 405 Method Not Allowed");
					break;
				}
			} else {
				this.logger.error("Invalid request! Returning 400 Bad Request");
				out.println("HTTP/1.1 400 Bad Request");
			}

			if (this.closeConnection) {
				this.socket.close();
			}

			if (System.getenv("DEBUG") != null) {
				this.logger.info("For debug purposes, pausing this execution...");
				Thread.sleep(6000);
			}
		}

		this.logger.info("Request processing is finished.");
	}
}
