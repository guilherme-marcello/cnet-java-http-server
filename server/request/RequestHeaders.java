package request;

import java.util.List;
import java.util.LinkedList;

public class RequestHeaders {
    private final List<String> headers;

    public RequestHeaders() {
        this.headers = new LinkedList<>();
    }

    public void add(String header) {
        headers.add(header);
    }

    public boolean isValid() {
        for (String header : this.headers) {
            if (!Models.isHeaderValid(header))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder textualHeaders = new StringBuilder("[Headers]\n");
        for (String header : this.headers) {
            textualHeaders.append(header);
        }
        return textualHeaders.toString();
    }

}