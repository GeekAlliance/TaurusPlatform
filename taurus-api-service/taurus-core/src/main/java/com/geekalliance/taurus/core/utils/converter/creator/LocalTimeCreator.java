package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.LocalTime;

/**
 * @author maxuqiang
 */
public class LocalTimeCreator implements Creator<LocalTime> {
    @Override
    public LocalTime create(Object source) {
        LocalTime srcObject = (LocalTime) source;
        return LocalTime.of(srcObject.getHour(), srcObject.getMinute(), srcObject.getSecond(), srcObject.getNano());
    }
}
