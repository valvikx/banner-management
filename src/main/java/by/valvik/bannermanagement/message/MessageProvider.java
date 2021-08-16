package by.valvik.bannermanagement.message;

public interface MessageProvider {

    String getMessage(String code);

    String getMessage(String code, Object...args);

}
