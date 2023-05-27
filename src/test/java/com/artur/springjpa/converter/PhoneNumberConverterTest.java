package com.artur.springjpa.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberConverterTest {

    private final PhoneNumberConverter converter = new PhoneNumberConverter();

    @Test
    public void testConvertToDatabaseColumn() {
        String input = "010-12-34-56";
        String expected = "010123456";
        String actual = converter.convertToDatabaseColumn(input);
        assertEquals(expected, actual, "Failed to convert phone number to database column format");
    }

    @Test
    public void testConvertToEntityAttribute() {
        String input = "010123456";
        String expected = "010-12-34-56";
        String actual = converter.convertToEntityAttribute(input);
        assertEquals(expected, actual, "Failed to convert phone number to entity attribute format");
    }

    @Test
    public void testConversionRoundTrip() {
        String input = "010-12-34-56";
        String intermediate = converter.convertToDatabaseColumn(input);
        String output = converter.convertToEntityAttribute(intermediate);
        assertEquals(input, output, "Failed to perform a round trip conversion");
    }
}