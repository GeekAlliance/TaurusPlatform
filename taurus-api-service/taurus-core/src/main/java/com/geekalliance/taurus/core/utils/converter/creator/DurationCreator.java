package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.Duration;

/**
 * @author maxuqiang
 */
public class DurationCreator implements Creator<Duration> {
    @Override
    public Duration create(Object source) {
        return Duration.parse(source.toString());
    }
}
