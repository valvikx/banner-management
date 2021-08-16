package by.valvik.bannermanagement.validation.impl;

import by.valvik.bannermanagement.error.ErrorAction;
import by.valvik.bannermanagement.validation.IdValidator;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;

@Service
public class CustomIdValidator implements IdValidator {

    private final ErrorAction errorAction;

    public CustomIdValidator(ErrorAction errorAction) {

        this.errorAction = errorAction;

    }

    @Override
    public boolean isValid(String valueId) {

        try {

            int id = parseInt(valueId);

            if (!isPositive(id)) {

                errorAction.onIdInvalid(valueId);

            }

            return true;

        } catch (NumberFormatException e) {

            errorAction.onIdInvalid(valueId);

        }

        return false;

    }

    private boolean isPositive(Integer id) {

        return id > 0;

    }

}
