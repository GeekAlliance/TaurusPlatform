package com.geekalliance.taurus.core.utils.converter.creator;

import java.time.ZoneId;

/**
 * @author maxuqiang
 */
public class ZoneIdCreator implements Creator<ZoneId> {
    @Override
    public ZoneId create(Object source) {
        return ZoneId.of(((ZoneId) source).getId());
    }
}
