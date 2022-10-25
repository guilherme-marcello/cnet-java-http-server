package request;

/**
 * this class provides methods to parse information 
 * in the request line in a http request
 */
public class RequestLine {
    private String method = null;
    private String endpoint = null;
    private String version = null; 
    private boolean isValid = false;

    /**
     * Creates a RequestLine object defined by a string, method, the method requested,
     *  a string, endpoint, ex: /index.html and a string, the http version 
     * @param content the content of the line request
     */
    public RequestLine(String content) {
        if (content != null && Models.isRequestLineValid(content)) {
            this.isValid = true;
            String[] line_split = content.split(" ");
            this.method = line_split[0];
            this.endpoint = line_split[1];
            this.version = line_split[2];
        }  
    }
  
        /**
	 * @return this method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return this endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * @return this version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Checks if this RequestLine is a valid http request line 
	 * @return true if this RequestLine is a valid http request
	 * line false if otherwise
	 */
    public boolean isValid() {
        return this.isValid;
    }
	
     /**
     * returns a text representation of this RequestLine
     */
	@Override
    public String toString() {
        return String.format(
            "%s %s %s",
            this.method, this.endpoint, this.version
        );
    }
}
