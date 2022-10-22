package request;

public class RequestLine {
    private String method = null;
    private String endpoint = null;
    private String version = null; 
    private boolean isValid = false;;
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
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}


	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

    public boolean isValid() {
        return this.isValid;
    }

	@Override
    public String toString() {
        return String.format(
            "[Request Line] %s %s %s",
            this.method, this.endpoint, this.version
        );
    }
}
