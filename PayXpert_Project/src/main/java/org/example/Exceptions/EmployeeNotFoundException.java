package org.example.Exceptions;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(String message){
        System.err.println(message);
    }
}
