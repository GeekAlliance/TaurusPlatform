package com.geekalliance.taurus.base.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @description
 * @date 2019/12/30
 * @author maxuqiang
 **/
public class BootOauthExceptionSerializer extends StdSerializer<BootOauthException> {
    public BootOauthExceptionSerializer() {
        super(BootOauthException.class);
    }

    @Override
    public void serialize(BootOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getResult());
    }
}