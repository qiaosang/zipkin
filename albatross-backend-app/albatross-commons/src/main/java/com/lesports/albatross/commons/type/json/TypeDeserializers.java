package com.lesports.albatross.commons.type.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lesports.albatross.commons.type.GenderType;
import com.lesports.albatross.commons.type.UserAddressType;

import java.io.IOException;

/**
 * Created by litzuhsien on 9/19/15.
 */
public class TypeDeserializers {

    public static class GenderTypeDeserializer extends JsonDeserializer<GenderType> {
        @Override
        public GenderType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return GenderType.getType(p.getValueAsString());
        }
    }

    public static class UserAddressTypeDeserializer extends JsonDeserializer<UserAddressType> {
        @Override
        public UserAddressType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return UserAddressType.getType(p.getValueAsString());
        }
    }
}
