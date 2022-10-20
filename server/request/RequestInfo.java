package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

public class RequestInfo {
    private RequestLine requestLine;
    private final List<String> requestHeaders;
    private final String payload;
    public RequestInfo(BufferedReader in) throws IOException {
        this.requestLine = new RequestLine(in.readLine());
        this.requestHeaders = new LinkedList<>();

        String header;
        while ((header = in.readLine()) != null) {
            if (header.length() == 0)
                break;
            this.requestHeaders.add(header);
        }
        
        StringBuilder payloadComposer = new StringBuilder();
        int dataChar;
        while (in.ready()) {
            dataChar = in.read();
            payloadComposer.append((char) dataChar);
        }
        this.payload = payloadComposer.toString();
    }
    
    public RequestLine getRequestLine() {
    	return this.requestLine;
    }
    
    public List<String> getRequestHeaders() {
    	return new LinkedList<String>(requestHeaders);
    }

    public String getPayload() {
        return this.payload;
    }
    
    @Override
    public String toString() {
        return this.requestLine.toString();
    }
}
