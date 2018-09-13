package org.itstep;

import java.text.ParseException;

public class ApplicationRunner {

    public static void main(String[] args) throws ParseException {

        Reader reader = new Reader();
        reader.readFile();
    }
}
