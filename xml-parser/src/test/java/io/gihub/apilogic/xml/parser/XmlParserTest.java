package io.gihub.apilogic.xml.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class XmlParserTest {

    @Test
    public void testCountriesSample() throws IOException, XMLStreamException {
        var url = getClass().getClassLoader().getResource("./samples/countries.xml");
        var config = getClass().getClassLoader().getResource("./configs/countries.yaml");
        Assertions.assertNotNull(config);
        var response = XmlParser.parse(url, config);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.get("Countries"));
        var countries = ((List)response.get("Countries"));
        Assertions.assertFalse(countries.isEmpty());
        countries.forEach(country -> Assertions.assertNotNull(((Map) country).get("CountryName")));
    }

    @Test
    public void testArrayInArray() throws IOException, XMLStreamException {
        var url = getClass().getClassLoader().getResource("./samples/countriesWithFeedBack.xml");
        var config = getClass().getClassLoader().getResource("./configs/countries.yaml");
        Assertions.assertNotNull(config);
        var response = XmlParser.parse(url, config);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.get("Countries"));
        var countries = ((List)response.get("Countries"));
        Assertions.assertFalse(countries.isEmpty());
        countries.forEach(country -> Assertions.assertNotNull(((Map) country).get("CountryName")));
        countries.forEach(country -> {
            var countryFeedBacks = ((Map) country).get("CountryFeedBacks");
            Assertions.assertNotNull(countryFeedBacks);
            Assertions.assertTrue(countryFeedBacks instanceof List);
            Assertions.assertFalse(((List<?>) countryFeedBacks).isEmpty());
        });
    }

}