package ru.isands.test.estore.exception;

public class JobTitleNotFoundException extends RuntimeException {
    public JobTitleNotFoundException(String jobTitle) {
        super(jobTitle);
    }

}
