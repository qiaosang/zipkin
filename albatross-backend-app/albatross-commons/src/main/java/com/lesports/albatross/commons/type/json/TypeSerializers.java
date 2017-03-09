package com.lesports.albatross.commons.type.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lesports.albatross.commons.type.GenderType;
import com.lesports.albatross.commons.type.UserAddressType;

import java.io.IOException;

/**
 * Created by litzuhsien on 9/19/15.
 */
public class TypeSerializers {

    public static class GenderTypeSerializer extends JsonSerializer<GenderType> {
        @Override
        public void serialize(GenderType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }

    public static class UserAddressTypeSerializer extends JsonSerializer<UserAddressType> {
        @Override
        public void serialize(UserAddressType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }
}
