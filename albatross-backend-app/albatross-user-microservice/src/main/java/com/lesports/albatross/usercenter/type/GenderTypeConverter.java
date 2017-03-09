package com.lesports.albatross.usercenter.type;

import com.lesports.albatross.commons.type.GenderType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by gang on 5/10/16.
 */
@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, String> {
    @Override
    public String convertToDatabaseColumn(GenderType attribute) {
        return attribute.toString();
    }

    @Override
    public GenderType convertToEntityAttribute(String dbData) {
        return GenderType.getType(dbData);
    }
}
