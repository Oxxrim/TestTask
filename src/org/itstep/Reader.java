package org.itstep;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    FileReader reader;
    Scanner scanner;
    List<String> listWithWaitingTime = new ArrayList<>();
    Service service = new Service();

    public void readFile() throws ParseException {
        {
            String line;
            try {
                reader = new FileReader("resources//test.txt");
                scanner = new Scanner(reader);

                while (scanner.hasNextLine()){
                    line = scanner.nextLine();
                    if (line.contains("C")){
                        listWithWaitingTime.add(line);
                    } else {
                        service.queryProcessing(listWithWaitingTime, line);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
