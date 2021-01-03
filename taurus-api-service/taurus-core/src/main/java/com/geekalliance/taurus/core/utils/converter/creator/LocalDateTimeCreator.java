package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.LocalDateTime;

/**
 * @author maxuqiang
 */
public class LocalDateTimeCreator implements Creator<LocalDateTime> {
    @Override
    public LocalDateTime create(Object source) {
        LocalDateTime srcObject = (LocalDateTime) source;
        return LocalDateTime.of(
                srcObject.getYear(),
                srcObject.getMonth(),
                srcObject.getDayOfMonth(),
                srcObject.getHour(),
                srcObject.getMinute(),
                srcObject.getSecond(),
                srcObject.getNano()
        );
    }
}
