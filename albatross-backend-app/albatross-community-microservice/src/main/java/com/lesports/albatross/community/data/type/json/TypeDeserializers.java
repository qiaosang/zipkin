package com.lesports.albatross.community.data.type.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lesports.albatross.community.data.type.AuditStatus;
import com.lesports.albatross.community.data.type.ContentType;
import com.lesports.albatross.community.data.type.Gender;
import com.lesports.albatross.community.data.type.UserRelationType;

import java.io.IOException;

/**
 * Created by litzuhsien on 9/19/15.
 */
public class TypeDeserializers {

    public static class AuditStatusDeserializer extends JsonDeserializer<AuditStatus> {
        @Override
        public AuditStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String rawString = p.getValueAsString();
            return AuditStatus.status(rawString);
        }
    }

    public static class ContentTypeDeserializer extends JsonDeserializer<ContentType> {
        @Override
        public ContentType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return ContentType.contentType(p.getValueAsString());
        }
    }


    public static class GenderDeserializer extends JsonDeserializer<Gender> {
        @Override
        public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return Gender.getType(p.getValueAsString());
        }
    }


    public static class UserRelationTypeDeserializer extends JsonDeserializer<UserRelationType> {
        @Override
        public UserRelationType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return UserRelationType.valueOf(p.getValueAsString());
        }
    }

}
