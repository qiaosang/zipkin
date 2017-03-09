package com.lesports.albatross.community.data.type.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lesports.albatross.community.data.type.*;

import java.io.IOException;

/**
 * Created by litzuhsien on 9/19/15.
 */
public class TypeSerializers {

    public static class AuditStatusSerializer extends JsonSerializer<AuditStatus> {
        @Override
        public void serialize(AuditStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }

    public static class ContentTypeSerializer extends JsonSerializer<ContentType> {
        @Override
        public void serialize(ContentType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }


    public static class GenderSerializer extends JsonSerializer<Gender> {
        @Override
        public void serialize(Gender value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }

    public static class UserRelationTypeSerializer extends JsonSerializer<UserRelationType> {
        @Override
        public void serialize(UserRelationType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }

    public static class MessageTypeSerializer extends JsonSerializer<MessageType> {
        @Override
        public void serialize(MessageType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            gen.writeString(value.toString());
        }
    }
}
