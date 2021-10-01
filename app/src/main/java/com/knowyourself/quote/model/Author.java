package com.knowyourself.quote.model;

import androidx.annotation.NonNull;

import com.knowyourself.quote.model.Quotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Author {
    private final String name;
    private final String fileName;
    private final Boolean selected;

    public Author(String name, String fileName, Boolean selected) {
        this.name = new File(fileName).getName();
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

    @NonNull
    public List<Quotes> getQuotes() throws IOException {
        List<Quotes> quotes = new ArrayList<>();

        if(selected) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(!line.equals("--")) {
                        quotes.add(new Quotes(line, name));
                    }
                }
            }
        }

        return quotes;
    }
}
