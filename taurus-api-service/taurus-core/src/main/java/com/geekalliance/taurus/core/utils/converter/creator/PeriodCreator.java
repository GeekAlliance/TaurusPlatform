package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.Period;

/**
 * @author maxuqiang
 */
public class PeriodCreator implements Creator<Period> {
    @Override
    public Period create(Object source) {
        Period srcObject = (Period) source;
        return Period.of(srcObject.getYears(), srcObject.getMonths(), srcObject.getDays());
    }
}
