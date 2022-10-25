package request;

import java.util.List;
import java.util.LinkedList;

/**
 * this class represents the headers of a http request
 */
public class RequestHeaders {
    private final List<String> headers;
    
    /**
     * Creates a RequestHeaders object that is defined by a list 
     * of the different headers in the http request
     */
    public RequestHeaders() {
        this.headers = new LinkedList<>();
    }
    
     /**
     * adds a header to this headers
     * @param header
     */
    public void add(String header) {
        this.headers.add(header);
    }
    
    /**
     * checks if a given header, string, 
     * is present in the list this headers
     * @param header the string to check in this headers
     * @return true if header is present false if otherwise
     */
    public boolean contains(String header) {
        return this.headers.contains(header);
    }

    /**
     * checks if this RequestHeaders is a valid http header 
     * @return true if this RequestHeaders is a valid http
     * header false if otherwise
     */
    public boolean isValid() {
        for (int i = 0; i < this.headers.size(); i++) {
            String header = this.headers.get(i);
            
            if (header.equals(Util.CRLF))
                return i == this.headers.size() - 1;
                
            if (!Models.isHeaderValid(header))
                return false;
        }
        return true;
    }

        /**
     * returns a text representation of this RequestHeaders
     */
    @Override
    public String toString() {
        StringBuilder textualHeaders = new StringBuilder();
        for (String header : this.headers) {
            textualHeaders.append(header);
        }
        return textualHeaders.toString();
    }

}
