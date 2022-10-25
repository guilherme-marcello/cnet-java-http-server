package request;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * this class provides methods to check if defined patterns match given patterns
 */
public class Models {
    //valid request line in the http protocol
    public static final Pattern VALID_REQUEST_LINE = Pattern.compile("[A-Z]{3,} /.{0,} HTTP/1.[0-1]\\r\\n"); 
     //valid header in the http protocol
    public static final Pattern VALID_HEADER = Pattern.compile(".{1,}: .{1,}\r\n");
    //valid input for hipotetical /simpleForm.html file
    public static final Pattern SIMPLEFORM_PAYLOAD_FORMAT = Pattern.compile("[{]StudentName:.{0,},StudentID:.{0,}[}]");
    
    /**
     * checks if the given string matches the VALID_REQUEST_LINE
     * @param requestLine string to check
     * @return true if it matches false if otherwise
     */
    public static boolean isRequestLineValid(String requestLine) {
        Matcher requestLineMatcher = VALID_REQUEST_LINE.matcher(requestLine);
        return requestLineMatcher.matches();
    }
    
     /**
     * checks if the given string matches the VALID_HEADER
     * @param header string to check
     * @return true if it matches false if otherwise
     */
    public static boolean isHeaderValid(String header) {
        Matcher headerMatcher = VALID_HEADER.matcher(header);
        return headerMatcher.matches();
    }
    
    /**
     * checks if the given string matches the SIMPLEFORM_PAYLOAD_FORMAT
     * @param payload string to check
     * @return true if it matches false if otherwise
     */
    public static boolean isSimpleFormPayloadValid(String payload) {
    	Matcher payloadMatcher = SIMPLEFORM_PAYLOAD_FORMAT.matcher(payload);
        return payloadMatcher.matches();
    }
}
