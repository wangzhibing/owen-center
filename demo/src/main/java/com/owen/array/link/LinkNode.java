package com.owen.array.link;

public class LinkNode {

    private String   value;

    private LinkNode pref;

    private LinkNode next;

    public LinkNode() {
    }

    public LinkNode(String value) {

        this.value = value;
        this.next = null;
        this.pref = null;
    }

    public LinkNode(String value, LinkNode pref, LinkNode next) {

        this.value = value;
        this.next = pref;
        this.pref = next;
    }

}
