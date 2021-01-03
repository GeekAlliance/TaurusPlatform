package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.LocalDate;

/**
 * @author maxuqiang
 */
public class LocalDateCreator implements Creator<LocalDate> {
    @Override
    public LocalDate create(Object source) {
        LocalDate srcObject = (LocalDate) source;
        return LocalDate.of(srcObject.getYear(), srcObject.getMonth(), srcObject.getDayOfMonth());
    }
}
