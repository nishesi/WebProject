package ru.itis.MyTube.auxiliary.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
