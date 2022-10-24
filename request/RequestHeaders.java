package request;

import java.util.List;
import java.util.LinkedList;

public class RequestHeaders {
    private final List<String> headers;

    public RequestHeaders() {
        this.headers = new LinkedList<>();
    }

    public void add(String header) {
        this.headers.add(header);
    }

    public boolean contains(String header) {
        return this.headers.contains(header);
    }

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

    @Override
    public String toString() {
        StringBuilder textualHeaders = new StringBuilder();
        for (String header : this.headers) {
            textualHeaders.append(header);
        }
        return textualHeaders.toString();
    }

}