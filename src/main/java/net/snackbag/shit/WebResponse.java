package net.snackbag.shit;

public class WebResponse {
    protected final int code;
    protected final String response;

    public WebResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    public int code() {
        return code;
    }

    public String response() {
        return response;
    }
}
