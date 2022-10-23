package request;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestInfo {
    private RequestLine requestLine;
    private final RequestHeaders requestHeaders = new RequestHeaders();
    private final String payload;
    public RequestInfo(BufferedReader in) throws IOException {
        StringBuilder requestLineComposer = new StringBuilder();

        int dataChar = 0;
        while (in.ready() && dataChar != '\n') {
            dataChar = in.read();
            requestLineComposer.append((char) dataChar);
        }
        this.requestLine = new RequestLine(requestLineComposer.toString());
        
        while (in.ready()) {
            StringBuilder headerComposer = new StringBuilder();
            dataChar = 0;
            while (dataChar != '\n') {
                dataChar = in.read();
                headerComposer.append((char) dataChar);
            }

            String header = headerComposer.toString();
            this.requestHeaders.add(header);
            if (header.equals("\r\n"))
                break;
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
    
    public RequestHeaders getRequestHeaders() {
    	return this.requestHeaders;
    }

    public String getPayload() {
        return this.payload;
    }
    
    @Override
    public String toString() {
        return String.format(
            "\n[BEGIN - RequestInfo]\n%s%s%s[END - RequestInfo]",
            this.requestLine.toString(), this.requestHeaders.toString(), this.payload + "\n"
        );
    }
}
