package com.teamtreehouse.techdegrees.exc;

/**
 * Created by Rumy on 9/29/2017.
 */
public class AppError extends RuntimeException {
    private final int status;

    public AppError(int status, String message){
        super(message);
        this.status=status;
    }

    public int getStatus() {
        return status;
    }
}
