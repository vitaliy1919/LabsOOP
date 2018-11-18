package com.company.XML;

import com.company.Page.Page;

import java.util.ArrayList;

public interface PageXMLParser {
    ArrayList<Page> parsePage(String xmlPath) throws IllegalArgumentException;
}
