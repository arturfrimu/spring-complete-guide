package com.arturfrimu.common.converter;

import com.arturfrimu.common.annotation.LogTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PhoneNumberConverter implements AttributeConverter<String, String> {
    @LogTime
    @Override
    public String convertToDatabaseColumn(String phoneNumber) {
        return phoneNumber.replace("-", "");
    }

    @LogTime
    @Override
    public String convertToEntityAttribute(String phoneNumber) {
        return phoneNumber.replaceAll("(.{3})(.{2})(.{2})(.{2})", "$1-$2-$3-$4");
    }
}
