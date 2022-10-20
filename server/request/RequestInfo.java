package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;


public class RequestInfo {
    private RequestLine requestLine;
    private final List<String> requestHeaders;
    public RequestInfo(BufferedReader in) throws IOException {
        this.requestLine = new RequestLine(in.readLine());
        this.requestHeaders = new LinkedList<>();

        String header = in.readLine();
        while ( header != null && header.length() > 0) {
            this.requestHeaders.add(header);
            header = in.readLine();
        }
    }
    
    public RequestLine getRequestLine() {
    	return this.requestLine;
    }
    
    public List<String> getRequestHeaders() {
    	return new LinkedList<String>(requestHeaders);
    }
    
    @Override
    public String toString() {
        return this.requestLine.toString();
    }
}
