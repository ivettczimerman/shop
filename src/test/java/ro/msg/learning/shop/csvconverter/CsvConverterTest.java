package ro.msg.learning.shop.csvconverter;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CsvConverterTest {

    private CsvConverter csvConverter;
    private List<TestStock> stocks = new ArrayList<>();

    @Before
    public void setup() {
        csvConverter = new CsvConverter();
        stocks.add(new TestStock(1, 2, 3, 4));
    }

    @Test
    public void fromCsv() throws IOException {
        String csv = "id, location, product, quantity\n1,2,3,4\n";
        InputStream inputStream = new ByteArrayInputStream(csv.getBytes());
        assertEquals(stocks, csvConverter.fromCsv(TestStock.class, inputStream));
    }

    @Test
    public void toCsv() throws IOException {
        String csv = "id,location,product,quantity\n1,2,3,4\n";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        csvConverter.toCsv(TestStock.class, stocks, outputStream);
        assertEquals(csv, new String(outputStream.toByteArray()));
    }
}
