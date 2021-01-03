package com.geekalliance.taurus.toolkit.utils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author maxuqiang
 */
public class NumberFormatUtils {
    public static String roundHalfUpToString(double value) {
        return String.valueOf(roundHalfUp(value));
    }

    public static String roundHalfUpToString(Double value, int scale) {
        return String.valueOf(roundHalfUp(value, scale));
    }

    public static Double roundHalfUp(Double value) {
        return roundHalfUp(value, 2);
    }

    public static Double roundHalfUp(Double value, int scale) {
        if (Objects.isNull(value)) {
            return null;
        }
        BigDecimal bg = new BigDecimal(value);
        return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
