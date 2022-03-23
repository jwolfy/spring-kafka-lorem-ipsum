package jwolfy.controller;

import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, ParagraphLength> {
    @Override
    public ParagraphLength convert(String source) {
        return ParagraphLength.valueOf(source.toUpperCase());
    }
}
