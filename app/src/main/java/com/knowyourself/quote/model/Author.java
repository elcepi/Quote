package com.knowyourself.quote.model;

import android.util.Log;

import androidx.annotation.NonNull;

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

    public Author(String fileName, Boolean selected) {
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
                StringBuilder cName = new StringBuilder();
                cName.append(Character.toUpperCase(name.charAt(0)));

                for(int i = 1; i<name.length(); i++) {
                    char a = name.charAt(i);
                    // TODO: Assumes that after _ there is always a character.
                    if('_' == a) {
                        cName.append(' ');
                        cName.append(Character.toUpperCase(name.charAt(++i)));
                    } else {
                        cName.append(a);
                    }
                }
                Log.i("TAG", "aaa  " + cName.toString());
                String line;
                while ((line = br.readLine()) != null) {
                    if(!line.equals("--")) {
                        quotes.add(new Quotes(line, cName.toString()));
                    }
                }
            }
        }

        return quotes;
    }
}
