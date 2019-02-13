package ro.msg.learning.shop.csvconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
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
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return super.canRead(type, contextClass, mediaType);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return super.canWrite(type, clazz, mediaType);
    }

    @Override
    protected List<T> readInternal(Class<? extends List<T>> aClass, HttpInputMessage httpInputMessage) {
        return Collections.emptyList();
    }

    @Override
    protected void writeInternal(List<T> tlist, Type type, HttpOutputMessage httpOutputMessage) throws IOException {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        csvConverter.toCsv((Class<T>) parameterizedType.getActualTypeArguments()[0], tlist, httpOutputMessage.getBody());
    }

    @Override
    public List<T> read(Type type, Class<?> aClass, HttpInputMessage httpInputMessage) throws IOException {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return csvConverter.fromCsv((Class<T>) parameterizedType.getActualTypeArguments()[0], httpInputMessage.getBody());
    }
}
