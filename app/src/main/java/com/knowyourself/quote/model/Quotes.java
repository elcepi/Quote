package com.knowyourself.quote.model;

public final class Quotes {
    private static  String text;

    public Quotes(String text) {
        this.text = text;
    }

    public static String getText() {
        return text;
    }
}
