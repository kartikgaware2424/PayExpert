package org.example.Exceptions;

public class DatabaseConnectionException extends  Exception{
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
