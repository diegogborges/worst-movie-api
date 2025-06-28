package br.com.goldenraspberryawards;

import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Setup {

    private final WorstMovieRepository movieRepository;

    public Setup(WorstMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    private void setupData() throws IOException {

        final String csvFile = "Movielist.csv";

        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            List<Movie> movieList = new ArrayList<>();
            String line;
            boolean isHeader = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] arrModel = line.split(";");

                movieList.add(
                        Movie.builder()
                                .id(null)
                                .movieYear(Integer.parseInt(arrModel[0]))
                                .title(arrModel[1])
                                .studios(arrModel[2])
                                .producers(arrModel[3])
                                .winner(arrModel.length >= 5 && arrModel[4].equals("yes"))
                                .build()
                );
            }

            this.movieRepository.saveAll(movieList);

        } catch (
                IOException exception) {
            System.out.println(exception);
        }
    }
}
