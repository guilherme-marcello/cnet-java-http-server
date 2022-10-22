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
        StringBuilder requestLineComposer = new StringBuilder();

        int dataChar = 0;
        while (in.ready() && dataChar != '\n') {
            dataChar = in.read();
            requestLineComposer.append((char) dataChar);
        }
        this.requestLine = new RequestLine(requestLineComposer.toString());
        
        this.requestHeaders = new LinkedList<>();

        while (in.ready()) {
            StringBuilder headerComposer = new StringBuilder();
            dataChar = 0;
            while (dataChar != '\n') {
                dataChar = in.read();
                headerComposer.append((char) dataChar);
            }
            String header = headerComposer.toString();
            if (header.equals("\r\n")) {
                System.out.println("CRLF!! begin of body..");
                break;
            }
            this.requestHeaders.add(headerComposer.toString());
            System.out.println(this.requestHeaders);
        }

        
        StringBuilder payloadComposer = new StringBuilder();
        while (in.ready()) {
            dataChar = in.read();
            payloadComposer.append((char) dataChar);
        }
        this.payload = payloadComposer.toString().replaceAll("[\\n\\t\\r ]", "");
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
