package com.company.Page;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Objects;

public class Chars {
    private String email;
    private ArrayList<String> news;
    private ArrayList<String> links;
    private Poll poll;

    public Chars() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chars)) return false;
        Chars chars = (Chars) o;
        return Objects.equals(email, chars.email) &&
                Objects.equals(news, chars.news) &&
                Objects.equals(links, chars.links) &&
                Objects.equals(poll, chars.poll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, news, links, poll);
    }

    @Override
    public String toString() {
        return "Chars{" +
                "email='" + email + '\'' +
                ", news=" + news +
                ", links=" + links +
                ", poll=" + poll +
                '}';
    }

    public static Chars parseChars(Node node) {
        Chars chars = new Chars();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element)node;
            NodeList emailNodes = element.getElementsByTagName("email");
            NodeList newsRootNodes = element.getElementsByTagName("newsroot");
            NodeList linksNodes = element.getElementsByTagName("links");
            NodeList pollNodes = element.getElementsByTagName("poll");

            if (emailNodes.getLength() == 1) {
                chars.email = emailNodes.item(0).getTextContent();
            }

            if (newsRootNodes.getLength() == 1) {
                Element newsRoot = (Element)newsRootNodes.item(0);
                NodeList newsNodes = newsRoot.getElementsByTagName("news");
                for (int i = 0; i < newsNodes.getLength(); i++) {
                    chars.addNews(newsNodes.item(i).getTextContent());
                }
            }

            if (linksNodes.getLength() == 1) {
                Element links = (Element)linksNodes.item(0);
                NodeList linkNodes = links.getElementsByTagName("link");
                for (int i = 0; i < linkNodes.getLength(); i++) {
                    chars.addLink(linkNodes.item(i).getTextContent());
                }
            }

            if (pollNodes.getLength() == 1) {
                chars.poll = Poll.parsePoll(pollNodes.item(0));
            }

        }
        return chars;
    }

    public Chars(String email, ArrayList<String> news, ArrayList<String> links, Poll poll) {
        this.email = email;
        this.news = news;
        this.links = links;
        this.poll = poll;
    }

    public void addNews(String news) {
        if (this.news == null)
            this.news = new ArrayList<>();
        this.news.add(news);
    }

    public void addLink(String link) {
        if (links == null)
            links = new ArrayList<>();
        links.add(link);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getNews() {
        return news;
    }

    public void setNews(ArrayList<String> news) {
        this.news = news;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
