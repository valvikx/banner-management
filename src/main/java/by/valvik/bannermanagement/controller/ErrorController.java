package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.error.ErrorMessage;
import by.valvik.bannermanagement.error.SecurityMessage;
import by.valvik.bannermanagement.exception.ServiceException;
import by.valvik.bannermanagement.message.MessageProvider;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
public class ErrorController {

    private static final String DATA_INVALID = "data.invalid";

    private final MessageProvider messageProvider;

    public ErrorController(MessageProvider messageProvider) {

        this.messageProvider = messageProvider;

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<Object, String> onConstraintValidationException(ConstraintViolationException e) {

        return e.getConstraintViolations()
                .stream()
                .collect(toMap(ConstraintViolation::getInvalidValue, ConstraintViolation::getMessage));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return e.getBindingResult().getFieldErrors()
                                   .stream()
                                   .collect(toMap(FieldError::getField,
                                                  DefaultMessageSourceResolvable::getDefaultMessage));

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> onHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        Throwable throwable = e.getCause();

        JsonMappingException jsonMappingException = ((JsonMappingException) throwable);

        List<JsonMappingException.Reference> references = jsonMappingException.getPath();

        return references.stream().collect(toMap(JsonMappingException.Reference::getFieldName,
                                                 r -> messageProvider.getMessage(DATA_INVALID)));
        
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage onServiceException(ServiceException e) {

        return new ErrorMessage(e.getReason(), e.getMessage());

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public SecurityMessage onSecurityError(RuntimeException e) {

        return new SecurityMessage(e.getMessage());

    }

}
