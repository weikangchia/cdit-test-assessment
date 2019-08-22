package com.cdit.challenge.externalDto;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class CurrencyDoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (null == value) {
            jgen.writeNull();
        } else {
            final String pattern = "0.00";
            final DecimalFormat formatter = new DecimalFormat(pattern);
            final String output = formatter.format(value);
            jgen.writeNumber(output);
        }
    }
}
