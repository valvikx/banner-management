package by.valvik.bannermanagement.error;

import by.valvik.bannermanagement.exception.ServiceException;
import by.valvik.bannermanagement.message.MessageProvider;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ErrorAction {

    private static final String TEMPLATE_ID = "template.id";

    private static final String TEMPLATE_NOT_FOUND = "template.not.found";

    private static final String ID_INVALID = "id.invalid";

    private static final String TEMPLATE_CATEGORY_ASSOCIATED = "template.category.associated";

    private static final String CATEGORY_DELETE = "category.delete";

    private final MessageProvider messageProvider;

    private String message;

    private String reason;

    public ErrorAction(MessageProvider messageProvider) {

        this.messageProvider = messageProvider;

    }

    public Supplier<ServiceException> onNotFound(Object reasonProperty, String messageProperty) {

        message = messageProvider.getMessage(TEMPLATE_NOT_FOUND, messageProperty);

        reason = getReason(reasonProperty);

        return () -> new ServiceException(reason, message);

    }

    public void onIdInvalid(String reasonProperty) {

        message = messageProvider.getMessage(ID_INVALID);

        reason = getReason(reasonProperty);

        throw new ServiceException(reason, message);


    }

    public void onCategoryDelete(String reasonProperty) {

        reason = messageProvider.getMessage(TEMPLATE_CATEGORY_ASSOCIATED, reasonProperty);

        message = messageProvider.getMessage(CATEGORY_DELETE);

        throw new ServiceException(reason, message);

    }

    private String getReason(Object reasonProperty) {

        return messageProvider.getMessage(TEMPLATE_ID, reasonProperty);

    }

}
