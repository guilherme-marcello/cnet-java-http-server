package request;

public class RequestLine {
    private String method = null;
    private String endpoint = null;
    private String version = null; 
    private boolean isValid;
    public RequestLine(String content) {
        if (content != null) {
            String[] line_split = content.split(" ");
            if (line_split.length == 3) {
                this.method = line_split[0];
                this.endpoint = line_split[1];
                this.version = line_split[2];
            }
        }
        this.isValid = this.method != null && this.endpoint != null && this.version != null;  
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
        return " METHOD IS -> " + this.method 
            + "\n ENDPOINT IS -> " + this.endpoint
            + "\n VERSION IS -> " + this.version;
    }
}
