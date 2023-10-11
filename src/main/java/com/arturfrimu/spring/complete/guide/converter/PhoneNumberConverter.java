package com.arturfrimu.spring.complete.guide.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PhoneNumberConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String phoneNumber) {
        return phoneNumber.replace("-", "");
    }

    @Override
    public String convertToEntityAttribute(String phoneNumber) {
        return phoneNumber.replaceAll("(.{3})(.{2})(.{2})(.{2})", "$1-$2-$3-$4");
    }
}
