package com.geekalliance.taurus.core.utils.converter;


import com.geekalliance.taurus.core.utils.converter.creator.CreatorFactory;
import org.dozer.CustomConverter;

import java.util.Locale;

/**
 * @author maxuqiang
 */
public class JdkMissingConverter implements CustomConverter {

    private final CreatorFactory creatorFactory = new CreatorFactory();

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

        if (destinationClass == null || sourceClass == null) {
            return destination;
        }

        if (source == null) {
            destination = null;
        } else if (destinationClass.isAssignableFrom(Locale.class) && sourceClass.isAssignableFrom(Locale.class)) {
            destination = creatorFactory.createLocaleCreator().create(source);
        }

        return destination;
    }
}
