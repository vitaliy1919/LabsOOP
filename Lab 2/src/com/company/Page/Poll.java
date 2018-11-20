package com.company.Page;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Objects;

public class Poll {
    public static Poll parsePoll(Node node) {
        Poll poll = new Poll();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element)node;
            poll.anonymous = Boolean.parseBoolean(element.getElementsByTagName("anonymous").item(0).getTextContent());
            Element optionsElement = (Element)element.getElementsByTagName("options").item(0);
            NodeList options = optionsElement.getElementsByTagName("option");
            for (int i = 0; i < options.getLength(); i++) {
                Node option = options.item(i);
                poll.options.add(option.getTextContent());
            }
        }
        return poll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Poll)) return false;
        Poll poll = (Poll) o;
        return anonymous == poll.anonymous &&
                Objects.equals(options, poll.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anonymous, options);
    }

    @Override
    public String toString() {
        return "Poll{" +
                "anonymous=" + anonymous +
                ", options=" + options +
                '}';
    }

    private boolean anonymous = true;
    private ArrayList<String> options = new ArrayList<>();

    public Poll() {
    }

    public Poll(boolean anonymous, ArrayList<String> options) {
        this.anonymous = anonymous;
        this.options = options;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        options.add(option);
    }

}
