package com.company.XML;

import com.company.Page.Chars;
import com.company.Page.Page;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.company.Page.PageType;
import com.company.Page.Poll;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

enum FieldTypes {
    Email, News, Title, Type, Links, Chars, Link, NewsRoot, Authorize, None, Anonymous, Option
}
class MyHandler extends DefaultHandler {

    //List to hold Employees object
    private ArrayList<Page> site = null;
    private Page page = null;
    private Chars chars = null;
    private Poll poll= null;
    private ArrayList<String> news = null;
    private ArrayList<String> links = null;
    private boolean bEmail;
    private boolean bNews;


    //getter method for employee list
    public ArrayList<Page> getSite() {
        return site;
    }
    FieldTypes currentState = FieldTypes.None;
    boolean bTitle = false;
    boolean bType = false;
    boolean bChars = false;
    boolean bAuthorize = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("page")) {
            page = new Page();
            page.setId(attributes.getValue("id"));
            if (site == null)
                site = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("title")) {
            //set boolean values for fields, will be used in setting Employee variables
            currentState = FieldTypes.Title;
        } else if (qName.equalsIgnoreCase("type")) {
            currentState = FieldTypes.Type;
        } else if (qName.equalsIgnoreCase("Chars")) {
            chars = new Chars();
        } else if (qName.equalsIgnoreCase("authorize")) {
            currentState = FieldTypes.Authorize;
        } else if (qName.equalsIgnoreCase("email")) {
            currentState = FieldTypes.Email;
        } else if (qName.equalsIgnoreCase("news")) {
            currentState = FieldTypes.News;
        } else if (qName.equalsIgnoreCase("newsroot")) {
            news = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("poll")) {
            poll = new Poll();
        } else if (qName.equalsIgnoreCase("options")) {
            poll.setOptions(new ArrayList<>());
        } else if (qName.equalsIgnoreCase("option")) {
            currentState = FieldTypes.Option;
        } else if (qName.equalsIgnoreCase("anonymous")) {
            currentState = FieldTypes.Anonymous;
        } else if (qName.equalsIgnoreCase("links")) {
            links = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("link")) {
            currentState = FieldTypes.Link;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
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

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        switch (currentState) {
            case Title:
                page.setTitle(data);
                break;
            case Type:
                page.setType(PageType.valueOf(data));
                break;
            case Email:
                chars.setEmail(data);
                break;
            case Authorize:
                page.setAuthorize(Boolean.parseBoolean(data));
                break;
            case News:
                news.add(data);
                break;
            case Option:
                poll.addOption(data);
                break;
            case Anonymous:
                poll.setAnonymous(Boolean.parseBoolean(data));
                break;
            case Link:
                links.add(data);
                break;
            default:
                break;
        }
        currentState = FieldTypes.None;
    }
}

public class SAXPageXMLParser implements PageXMLParser {
    @Override
    public ArrayList<Page> parsePage(String xmlPath) throws IllegalArgumentException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        ArrayList<Page> site = null;
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new File(xmlPath), handler);
            //Get Employees list
            site = handler.getSite();
            //print employee information
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //System.out.println("Error while parsing: " + e.getMessage());
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
        return site;
    }
}
