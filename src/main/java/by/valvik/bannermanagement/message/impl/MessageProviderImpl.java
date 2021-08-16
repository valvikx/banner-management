package by.valvik.bannermanagement.message.impl;

import by.valvik.bannermanagement.message.MessageProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageProviderImpl implements MessageProvider {

    private final MessageSource messageSource;

    public MessageProviderImpl(MessageSource messageSource) {

        this.messageSource = messageSource;

    }

    @Override
    public String getMessage(String code) {

        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());

    }

    @Override
    public String getMessage(String code, Object... args) {

        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());

    }

}
