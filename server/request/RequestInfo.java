package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

class RequestLine {
    private String method = null;
    private String endpoint = null;
    private String version = null; 
    public RequestLine(String content) {
        System.out.println(content);
        if (content != null) {
            String[] line_split = content.split(" ");
            if (line_split.length == 3) {
                this.method = line_split[0];
                this.endpoint = line_split[1];
                this.version = line_split[2];
            }
        }
        else {
            System.out.println( "is null...." );
        }

    }

    @Override
    public String toString() {
        return " METHOD IS -> " + this.method 
            + "\n ENDPOINT IS -> " + this.endpoint
            + "\n VERSION IS -> " + this.version;
    }
}


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
        System.out.println("Done parsing request info!");

    }

    @Override
    public String toString() {
        return this.requestLine.toString();
    }
}