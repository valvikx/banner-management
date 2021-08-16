package by.valvik.bannermanagement.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final String reason;

    public ServiceException(String reason, String message) {

        super(message);

        this.reason = reason;

    }

}
