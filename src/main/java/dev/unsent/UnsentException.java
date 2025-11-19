package dev.unsent;

import com.google.gson.JsonObject;

public class UnsentException extends Exception {
    private final int statusCode;
    private final JsonObject error;
    private final String method;
    private final String path;
    
    public UnsentException(int statusCode, JsonObject error, String method, String path) {
        super(String.format("%s %s -> %d %s: %s", 
            method, 
            path, 
            statusCode,
            error.has("code") ? error.get("code").getAsString() : "UNKNOWN_ERROR",
            error.has("message") ? error.get("message").getAsString() : ""
        ));
        this.statusCode = statusCode;
        this.error = error;
        this.method = method;
        this.path = path;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public JsonObject getError() {
        return error;
    }
    
    public String getMethod() {
        return method;
    }
    
    public String getPath() {
        return path;
    }
}
