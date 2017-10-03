package com.teamtreehouse.techdegrees.exc;

/**
 * Created by Rumy on 9/19/2017.
 */
public class DaoException extends Exception {
    private final Exception originalException;

    public DaoException(Exception originalException, String msg){
        super(msg);
        this.originalException=originalException;
    }
}
