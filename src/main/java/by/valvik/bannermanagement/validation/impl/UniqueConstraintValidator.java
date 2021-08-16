package by.valvik.bannermanagement.validation.impl;

import by.valvik.bannermanagement.domain.BaseEntity;
import by.valvik.bannermanagement.message.MessageProvider;
import by.valvik.bannermanagement.validation.annotation.Unique;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

import static by.valvik.bannermanagement.util.Reflections.getUniqueFieldMessages;
import static by.valvik.bannermanagement.util.Reflections.getUniqueFieldValues;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

@Service
public class UniqueConstraintValidator implements ConstraintValidator<Unique, Object> {

    private static final String VALUE = "value";

    private static final String IS_DELETED = "isDeleted";

    private final EntityManager entityManager;

    private final MessageProvider messageProvider;

    private String messageTemplate;

    public UniqueConstraintValidator(EntityManager entityManager, MessageProvider messageProvider) {

        this.entityManager = entityManager;

        this.messageProvider = messageProvider;

    }

    @Override
    public void initialize(Unique constraintAnnotation) {

        messageTemplate = constraintAnnotation.message();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Map<String, Object> uniqueFieldValues = getUniqueFieldValues(value);

        Map<String, Boolean> validatedFields = uniqueFieldValues.entrySet()
                                                                .stream()
                                                                .collect(toMap(Map.Entry::getKey,
                                                                               e -> hasFieldValueInDb(value, e.getKey(),
                                                                                                      (String) e.getValue())));

        if (validatedFields.entrySet().stream().allMatch(Map.Entry::getValue)) {

            return true;

        }

        Map<String, String> uniqueFieldLabels = getUniqueFieldMessages(value, messageProvider);

        HibernateConstraintValidatorContext validatorContext = context.unwrap(HibernateConstraintValidatorContext.class);

        uniqueFieldLabels.entrySet()
                         .stream()
                         .filter(e -> !validatedFields.get(e.getKey()))
                         .forEach(e -> validatorContext.addExpressionVariable(VALUE, e.getValue())
                                                       .buildConstraintViolationWithTemplate(messageTemplate)
                                                       .addPropertyNode(e.getKey())
                                                       .addConstraintViolation());

        return false;

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean hasFieldValueInDb(Object obj, String fieldName, String fieldValue) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<?> query = criteriaBuilder.createQuery(obj.getClass());

        Root root = query.from(obj.getClass());

        ParameterExpression<String> stringParam = criteriaBuilder.parameter(String.class);

        ParameterExpression<Boolean> boolParam = criteriaBuilder.parameter(Boolean.class);

        query.select(root)
             .where(criteriaBuilder.equal(root.get(fieldName), stringParam),
                    criteriaBuilder.equal(root.get(IS_DELETED), boolParam));

        TypedQuery<?> typedQuery = entityManager.createQuery(query);

        typedQuery.setParameter(stringParam, fieldValue);

        typedQuery.setParameter(boolParam, false);

        BaseEntity entity = (BaseEntity) obj;

        List<?> results = typedQuery.getResultList();

        if (nonNull(entity.getId()) && !results.isEmpty()) {

            BaseEntity entityFromDb = (BaseEntity) results.get(0);

            return entity.getId().equals(entityFromDb.getId());

        }

        return results.isEmpty();

    }

}
