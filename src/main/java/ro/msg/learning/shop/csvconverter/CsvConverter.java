package ro.msg.learning.shop.csvconverter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class CsvConverter {

    private CsvMapper mapper = new CsvMapper();

    public <T> List<T> fromCsv(Class<T> tclass, InputStream input) throws IOException {
        CsvSchema schema = mapper.schemaFor(tclass).withHeader();

        MappingIterator<T> it = mapper.readerFor(tclass).with(schema).readValues(input);
        List<T> list = it.readAll();
        input.close();
        return list;
    }

    public <T> void toCsv(Class<T> tclass, List<T> pojos, OutputStream output) throws IOException {
        CsvSchema schema = mapper.schemaFor(tclass).withHeader();
        mapper.writer(schema).writeValue(output, pojos);
        output.close();
    }
}
