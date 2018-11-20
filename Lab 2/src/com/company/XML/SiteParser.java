package com.company.XML;

import com.company.Page.Page;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SiteParser {
    private PageXMLParser pageParser;

    public SiteParser(PageXMLParser pageParser) {
        this.pageParser = pageParser;
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
    public ArrayList<Page> parseSite(String xmlPath, String xsdPath) {
        if (xmlPath == null || xsdPath == null)
            return null;
        boolean valid = validateXMLSchema(xsdPath, xmlPath);
        ArrayList<Page> site = null;
        if (!valid)
            return null;
        try {
            site = pageParser.parsePage(xmlPath);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while parsing: " + e.getMessage());
        }
        return site;
    }
}
