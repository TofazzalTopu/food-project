package com.docu.commons.exception;

/**
 * Created by IntelliJ IDEA.
 * User: towhidul.islam
 * Date: 20/04/15
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessAlreadyRunningException extends Exception {
    public ProcessAlreadyRunningException(String message) {
        super(message);
    }
}
