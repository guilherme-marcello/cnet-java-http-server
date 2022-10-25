package request;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * this class provides methods o parse information in a http format
 */
public class RequestInfo {
    private RequestLine requestLine;
    private final RequestHeaders requestHeaders = new RequestHeaders();
    private final String payload;
    
     /**
     * Creates a RequestInfo Object that is defined by a RequestLine, the request line of the http request
     *  a RequestHeaders, the header of the http request, and a String, the message body
     * read through a given BufferedReader
     * @param the BufferedReader to read through
     * @throws IOException
     */
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
    
     /**
     * returns this RequestInfo RequestLine,
     * the request line of the http request
     * @return this.requestLine
     */
    public RequestLine getRequestLine() {
    	return this.requestLine;
    }
    
     /**
     * returns this RequestInfo RequestHeaders,
     * the header of the http request
     * @return this.requestHeaders
     */
    public RequestHeaders getRequestHeaders() {
    	return this.requestHeaders;
    }
    
     /**
     * returns this RequestInfo payload, the string with 
     * the message body
     * @return this.payload
     */
    public String getPayload() {
        return this.payload;
    }
    
    /**
     * returns a string representation of this RequestInfo
     */
    @Override
    public String toString() {
        return String.format(
            "\n[BEGIN - RequestInfo]\n%s%s%s[END - RequestInfo]",
            this.requestLine.toString(), this.requestHeaders.toString(), this.payload + "\n"
        );
    }
}
