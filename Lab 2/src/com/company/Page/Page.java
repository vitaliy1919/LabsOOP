package com.company.Page;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Objects;

public class Page implements Comparable<Page>{
    private String id;
    private String title;
    private PageType type;
    private Chars chars;
    private boolean authorize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    static public Page parsePage(Node node) {
        Page page = new Page();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element)node;
            page.id = element.getAttribute("id");
            page.title = element.getElementsByTagName("title").item(0).getTextContent();
            page.type = PageType.valueOf(element.getElementsByTagName("type").item(0).getTextContent());
            Node charsNode = element.getElementsByTagName("Chars").item(0);
            page.chars = Chars.parseChars(charsNode);
            page.authorize = Boolean.parseBoolean(element.getElementsByTagName("authorize").item(0).getTextContent());

        }
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;
        Page page = (Page) o;
        return authorize == page.authorize &&
                Objects.equals(title, page.title) &&
                type == page.type &&
                Objects.equals(chars, page.chars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, chars, authorize);
    }

    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", chars=" + chars +
                ", authorize=" + authorize +
                '}';
    }

    public Page() {
    }

    public Page(String title, PageType type, Chars chars, boolean authorize) {
        this.title = title;
        this.type = type;
        this.chars = chars;
        this.authorize = authorize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
    }

    public Chars getChars() {
        return chars;
    }

    public void setChars(Chars chars) {
        this.chars = chars;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    @Override
    public int compareTo(Page page) {
        int comp = title.compareTo(page.title);
        if (comp == 0) {
            return type.compareTo(page.type);
        }
        return comp;
    }
}
