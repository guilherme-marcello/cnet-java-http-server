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

/**
 * Class that handels the threads
 */
public class Handler extends Thread {
    private static final int MAX_THREADS = 5;
    private static final String THREADS_EXCEEDED_ERROR = String.format(
        "The server does not handle more than %d requests simultaneously. Discarding this request.",
        MAX_THREADS
    );
   //go true if it receives a close connection header and turn down the socket
    private boolean closeConnection = false;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int id;
    private HashMap<String, String> contentDirectory;
    private List<Integer> idRegistry;
    private Logger logger;
	
    /**
     * Contructor of this Handler
     * 
     * @param socket
     * @param id thread id
     * @param contentDirectory hashmap with resources available for this server
     * @param idRegistry list with online sockets
     * @throws SocketException
     * @throws IOException
     */
    public Handler(Socket socket, int id, HashMap<String, String> contentDirectory, List<Integer> idRegistry) throws SocketException, IOException {
        this.socket = socket;

        this.in = new BufferedReader(
            new InputStreamReader(
                this.socket.getInputStream()
            )
        );
    
        this.out = new PrintWriter(
            this.socket.getOutputStream(), true 
        );            

        this.id = id;
        this.logger = new Logger(this.id);
        this.contentDirectory = contentDirectory;
        this.idRegistry = idRegistry;

    }

    @Override
    public void run() {
        try {
			this.logger.info("Handling new client!");
			this.idRegistry.add(this.id);
			if (this.idRegistry.size() > MAX_THREADS) {
				this.logger.error(THREADS_EXCEEDED_ERROR);
				out.println("HTTP/1.1 503 Service Unavailable " + Util.CRLF);
				this.socket.close();
			}
			while (!this.socket.isClosed()) {
				this.handle_request();
			}
			this.logger.info("Finished client handling!");
			this.idRegistry.remove(Integer.valueOf(id));
		} catch (Exception e) {
			this.logger.error("Unable to handle request.");
		}
    }

    private void handle_post_request(String endpoint, String requestPayload) {
        switch (endpoint) {
            case "/simpleForm.html":
                if (Models.isSimpleFormPayloadValid(requestPayload)) {
                    this.logger.info("Received a valid POST request with correct format! Returning 200");
                    out.println("HTTP/1.1 200 OK");
                }
                else {
                    this.logger.info("Received a valid POST request with an incorrect format! Returning 406");
                    out.println("HTTP/1.1 406 not Acceptable");
                }
                break;
            default:
                this.logger.info("Received a valid POST request to an unavailable endpoint! Returning 404");
                out.println("HTTP/1.1 404 Not Found");
                break;
        }    
    }
	/**
	 * handles this Handler http GET requests
	 * @param endpoint the name of the resource to get
	 */
    private void handle_get_request(String endpoint) {
        if (this.contentDirectory.containsKey(endpoint)) {
            this.logger.info("Received a valid GET request to an existing resource! Returning 200");
            out.println("HTTP/1.1 200 OK" + Util.CRLF + Util.CRLF + this.contentDirectory.get(endpoint) + Util.CRLF);
        }
        else {
            this.logger.info("Received a valid GET request to an unavailable resource! Returning 404");
            out.println("HTTP/1.1 404 Not Found");
        }
    }
	/**
	 * handles this Handler http requests
	 * @throws IOException
	 * @throws InterruptedException
	 */
    private void handle_request() throws IOException, InterruptedException {
	//checks if the number of expected bytes in the sockets is more than 0
        if (this.socket.getInputStream().available() <= 0)
            return;           

        this.logger.info("Handling new request!");
        this.logger.info("Parsing data from socket!");
        RequestInfo info = new RequestInfo(in);
        this.logger.info("Finished request info parsing!");

        RequestLine requestLine = info.getRequestLine();
        RequestHeaders requestHeaders = info.getRequestHeaders();

        this.logger.info(info.toString());
        this.logger.info("Preparing response!");

        if (requestLine.isValid() && requestHeaders.isValid()) {
            if (requestHeaders.contains("Connection: close" + Util.CRLF)) {
                this.logger.info("Received a close connection header. Closing connection after sending a response!");
                this.closeConnection = true;
            }

            switch (requestLine.getMethod()) {
                case "GET":
                    this.handle_get_request(requestLine.getEndpoint());
                    break;
                case "POST":
                    this.handle_post_request(requestLine.getEndpoint(), info.getPayload());
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

        if (this.closeConnection) {
            this.socket.close();
        }

        if (System.getenv("DEBUG") != null) {
            this.logger.info("For debug purposes, pausing this execution...");
            Thread.sleep(6000);
        }
            
        this.logger.info("Request processing is finished.");
    }
}
