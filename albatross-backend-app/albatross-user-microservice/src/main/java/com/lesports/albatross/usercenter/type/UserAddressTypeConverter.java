package com.lesports.albatross.usercenter.type;

import com.lesports.albatross.commons.type.UserAddressType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class UserAddressTypeConverter implements AttributeConverter<UserAddressType, String> {
    @Override
    public String convertToDatabaseColumn(UserAddressType attribute) {
        return attribute.toString();
    }

    @Override
    public UserAddressType convertToEntityAttribute(String dbData) {
        return UserAddressType.getType(dbData);
    }
}
