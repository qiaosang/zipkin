package com.lesports.albatross.community.data.type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute.toString();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.getType(dbData);
    }
}
