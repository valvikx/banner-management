package by.valvik.bannermanagement.util;

import by.valvik.bannermanagement.domain.BaseEntity;
import by.valvik.bannermanagement.message.MessageProvider;
import by.valvik.bannermanagement.service.impl.GenericServiceImpl;
import by.valvik.bannermanagement.validation.annotation.UniqueValue;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;

@UtilityClass
public class Reflections {

    public static <E extends BaseEntity> String[] getNullFieldNames(E entity) {

        Stream<Field> fieldStream = Stream.concat(Arrays.stream(entity.getClass().getSuperclass().getDeclaredFields()),
                                                  Arrays.stream(entity.getClass().getDeclaredFields()));

        return fieldStream.filter(field -> {

                            field.setAccessible(true);

                            return isNull(getField(field, entity));

                        })
                          .map(Field::getName)
                          .toArray(String[]::new);

    }

    public static <S extends GenericServiceImpl<?, ?>> String getEntityName(S service) {

        Class<?> serviceClass = service.getClass();

        Type actualTypeArgument = ((ParameterizedType) serviceClass.getGenericSuperclass()).getActualTypeArguments()[0];

        return ((Class<?>) actualTypeArgument).getSimpleName();

    }

    public static <E extends BaseEntity> String getEntityName(E entity) {

        return entity.getClass().getSimpleName();

    }

    public static Map<String, Object> getUniqueFieldValues(Object obj) {

        return Arrays.stream(obj.getClass().getDeclaredFields())
                     .filter(field -> field.isAnnotationPresent(UniqueValue.class))
                     .collect(toMap(Field::getName, field -> {

                         field.setAccessible(true);

                         return getField(field, obj);

                     }));

    }

    public static Map<String, String> getUniqueFieldMessages(Object obj, MessageProvider messageProvider) {

        return Arrays.stream(obj.getClass().getDeclaredFields())
                     .filter(field -> field.isAnnotationPresent(UniqueValue.class))
                     .collect(toMap(Field::getName,
                              field -> messageProvider.getMessage(field.getAnnotation(UniqueValue.class).value())));

    }

}
