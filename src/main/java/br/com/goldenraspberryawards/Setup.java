package br.com.goldenraspberryawards;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class Setup {

    @PostConstruct
    private void setupData() throws IOException {

        String csvFile = "Movielist.csv";

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] arrModel = line.split(";");
                System.out.println(arrModel);
            }

        } catch (IOException exception) {
            System.out.println(exception);
        }
    }
}
