package com.company;


import com.company.Page.Page;
import com.company.XML.DOMPageXMLParser;
import com.company.XML.SAXPageXMLParser;
import com.company.XML.SiteParser;
import com.company.XML.StAXPageXMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String filePath = "/home/vitdmit/IdeaProjects/Third year problems/Lab 2/example_site.xml";
        String xsdPath = "/home/vitdmit/IdeaProjects/Third year problems/Lab 2/site.xsd";

        SiteParser siteParser = new SiteParser(new DOMPageXMLParser());
        ArrayList<Page> site = siteParser.parseSite(filePath, xsdPath);
        System.out.println(site);

        Collections.sort(site);
    }
}
