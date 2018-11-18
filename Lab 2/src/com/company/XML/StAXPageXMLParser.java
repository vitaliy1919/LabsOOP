package com.company.XML;

import com.company.Page.Chars;
import com.company.Page.Page;
import com.company.Page.PageType;
import com.company.Page.Poll;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StAXPageXMLParser implements PageXMLParser{
    private Chars chars;
    private ArrayList<String> news;
    private Poll poll;
    private ArrayList<String> links;


    @Override
    public ArrayList<Page> parsePage(String xmlPath) throws IllegalArgumentException {
        ArrayList<Page> site = new ArrayList<>();
        Page page = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlPath));
            while(xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("page")) {
                        page = new Page();
                        Attribute idAttr = startElement.getAttributeByName(new QName("id"));
                        page.setId(idAttr.toString());
                    } else if (qName.equalsIgnoreCase("title")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        page.setTitle(xmlEvent.asCharacters().getData());
                    } else if (qName.equalsIgnoreCase("type")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        page.setType(PageType.valueOf(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("Chars")) {
                        chars = new Chars();
                    } else if (qName.equalsIgnoreCase("authorize")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        page.setAuthorize(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("email")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        chars.setEmail(xmlEvent.asCharacters().getData());
                    } else if (qName.equalsIgnoreCase("news")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        news.add(xmlEvent.asCharacters().getData());
                    } else if (qName.equalsIgnoreCase("newsroot")) {
                        news = new ArrayList<>();
                    } else if (qName.equalsIgnoreCase("poll")) {
                        poll = new Poll();
                    } else if (qName.equalsIgnoreCase("options")) {
                        poll.setOptions(new ArrayList<>());
                    } else if (qName.equalsIgnoreCase("option")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        poll.addOption(xmlEvent.asCharacters().getData());
                    } else if (qName.equalsIgnoreCase("anonymous")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        poll.setAnonymous(Boolean.parseBoolean(xmlEvent.asCharacters().getData()));
                    } else if (qName.equalsIgnoreCase("links")) {
                        links = new ArrayList<>();
                    } else if (qName.equalsIgnoreCase("link")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        links.add(xmlEvent.asCharacters().getData());
                    }
                } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    String qName = endElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("page")) {
                        //add Employee object to list
                        site.add(page);
                    } else if (qName.equalsIgnoreCase("Chars")) {
                        page.setChars(chars);
                    } else if (qName.equalsIgnoreCase("poll")) {
                        chars.setPoll(poll);
                    } else if (qName.equalsIgnoreCase("newsroot")) {
                        chars.setNews(news);
                    } else if (qName.equalsIgnoreCase("links")) {
                        chars.setLinks(links);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            throw new IllegalArgumentException("Error " + e.getMessage());
        }
        return site;
    }
}
