package request;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Models {
    public static final Pattern VALID_REQUEST_LINE = Pattern.compile("[A-Z]{3,} /.{0,} HTTP/1.[0-1]\\r\\n"); 
    public static final Pattern VALID_HEADER = Pattern.compile(".{1,}: .{1,}\\r\\n");


    public static boolean isRequestLineValid(String requestLine) {
        Matcher requestLineMatcher = VALID_REQUEST_LINE.matcher(requestLine);
        return requestLineMatcher.matches();
    }

    public static boolean isHeaderValid(String header) {
        Matcher headerMatcher = VALID_HEADER.matcher(header);
        return headerMatcher.matches();
    }
}