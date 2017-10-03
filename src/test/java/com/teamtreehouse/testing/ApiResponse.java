package com.teamtreehouse.testing;

/**
 * Created by Rumy on 9/27/2017.
 */
public class ApiResponse {
    private final int status;
    private final String body;


    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
