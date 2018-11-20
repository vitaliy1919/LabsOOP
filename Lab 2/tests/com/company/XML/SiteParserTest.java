package com.company.XML;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SiteParserTest {
    static Stream<PageXMLParser> xmlParseProvider() {
        return Stream.of(new DOMPageXMLParser(), new SAXPageXMLParser(), new StAXPageXMLParser());
    }

    @Test
    void validateValidXML() {
        boolean result = SiteParser.validateXMLSchema("./site.xsd","./tests/com/company/XML/test_page.xml" );
        assertTrue(result);
    }

    @Test
    void invalidXML() {
        boolean result = SiteParser.validateXMLSchema("./site.xsd", "./tests/com/company/XML/invalid.xml");
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("xmlParseProvider")
    void returnsNullWhenFileNotFound(PageXMLParser parser) {
        SiteParser siteParser = new SiteParser(parser);
        assertNull(siteParser.parseSite("./site.xsd", "./tests/com/company/XML/.xml"));
    }
}