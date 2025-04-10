package org.example.Exceptions;

public class FinancialRecordException extends  Exception{
    public FinancialRecordException(String message) {
        super(message);
    }

    public FinancialRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
