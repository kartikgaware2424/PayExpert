package org.example.Exceptions;

public class PayrollGenerationException extends Exception  {
    public PayrollGenerationException(String message) {
        super(message);
    }

    public PayrollGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
