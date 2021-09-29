package com.knowyourself.quote.model;

import com.knowyourself.quote.model.Quotes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Author {
    private final String name;
    private final  String fileName;
    private final  Boolean selected;

    public Author(String fileName, Boolean selected) {
        this.name = fileName.replaceAll("^.*/(\\*[.])[^.]+$", "$1");
        this.fileName = fileName;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public String getFileNme() {
        return fileName;
    }

    public Boolean isSelected() {
        return selected;
    }

    public List<Quotes> getQuotes() throws IOException {
        List<Quotes> quotes = new ArrayList<>();

        if(selected) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    quotes.add(new Quotes(line, name));
                }
            }
        }

        return quotes;
    }
}
