package com.company.XML;

import com.company.Page.Chars;
import com.company.Page.Page;
import com.company.Page.PageType;
import com.company.Page.Poll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PageXMLParserTest {
    Page page1;
    Page page2;
    static Stream<PageXMLParser> xmlParseProvider() {
        return Stream.of(new DOMPageXMLParser(), new SAXPageXMLParser(), new StAXPageXMLParser());
    }
    PageXMLParserTest() {
        page1 = new Page();
        page1.setTitle("Page 1");
        page1.setType(PageType.NewsPage);
        page1.setAuthorize(false);

        ArrayList<String> options1 = new ArrayList<>();
        options1.add("o1");
        options1.add("o2");
        ArrayList<String> news1 = new ArrayList<>();
        ArrayList<String> links1 = new ArrayList<>();
        news1.add("News 1");
        news1.add("News 2");
        links1.add("Link A");
        links1.add("Link B");
        Poll poll1 = new Poll(true, options1);
        Chars chars1 = new Chars("example@mail.com", news1, links1, poll1);
        page1.setChars(chars1);
        page2 = new Page();
        page2.setTitle("Page 2");
        page2.setType(PageType.AdPage);
        ArrayList<String> options2 = new ArrayList<>();
        options2.add("o3");
        options2.add("o4");
        options2.add("o5");

        ArrayList<String> news2 = new ArrayList<>();
        ArrayList<String> links2 = new ArrayList<>();
        news2.add("News 3");
        news2.add("News 4");
        links2.add("Link AC");
        links2.add("Link BC");
        Poll poll2 = new Poll(false, options2);
        Chars chars2 = new Chars("test@gmail.com", news2, links2, poll2);
        page2.setChars(chars2);
        page2.setAuthorize(true);
    }
    @ParameterizedTest
    @MethodSource("xmlParseProvider")
    void parsePage(PageXMLParser parser) {
        ArrayList<Page> site = parser.parsePage("./tests/com/company/XML/test_page.xml");
        assertEquals(page1, site.get(0));
        assertEquals(page2, site.get(1));

    }
}