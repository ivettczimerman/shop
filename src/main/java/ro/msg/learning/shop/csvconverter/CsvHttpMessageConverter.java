package ro.msg.learning.shop.csvconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
public class CsvHttpMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    private static final String MEDIA_TYPE = "text";
    private static final String MEDIA_SUBTYPE = "csv";

    private CsvConverter csvConverter;

    @Autowired
    public CsvHttpMessageConverter(CsvConverter csvConverter) {
        super(new MediaType(MEDIA_TYPE, MEDIA_SUBTYPE));
        this.csvConverter = csvConverter;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<T> readInternal(Class<? extends List<T>> aClass, HttpInputMessage httpInputMessage) throws IOException {
        ParameterizedType parameterizedType = (ParameterizedType) aClass.getGenericSuperclass();
        return csvConverter.fromCsv((Class<T>) parameterizedType.getActualTypeArguments()[0], httpInputMessage.getBody());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void writeInternal(List<T> tlist, Type type, HttpOutputMessage httpOutputMessage) throws IOException {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        csvConverter.toCsv((Class<T>) parameterizedType.getActualTypeArguments()[0], tlist, httpOutputMessage.getBody());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> read(Type type, Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return csvConverter.fromCsv((Class<T>) parameterizedType.getActualTypeArguments()[0], httpInputMessage.getBody());
    }
}
