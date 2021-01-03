package com.geekalliance.taurus.core.utils.converter.creator;

import java.util.Locale;

/**
 * @author maxuqiang
 */
public class LocaleCreator implements Creator<Locale> {
    @Override
    public Locale create(Object source) {
        Locale srcObject = (Locale) source;
        String language = srcObject.getLanguage() != null ? srcObject.getLanguage() : "";
        String country = srcObject.getCountry() != null ? srcObject.getCountry() : "";
        String variant = srcObject.getVariant() != null ? srcObject.getVariant() : "";
        return new Locale(language, country, variant);
    }
}
