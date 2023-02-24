package jhkim105.tutorials.spring.mvc.config;

import org.springframework.core.convert.converter.Converter;

public class CaseInsensitiveEnumConverter<T extends Enum<T>> implements Converter<String, T> {
    private final Class<T> enumClass;

    public CaseInsensitiveEnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convert(String from) {
        return T.valueOf(enumClass, from.toUpperCase());
    }
}