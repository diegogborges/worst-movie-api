package br.com.goldenraspberryawards.configuration;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class Setup {

    private final ResourceLoader resourceLoader;
    private final WorstMovieRepository movieRepository;

    @PostConstruct
    private void setupData() throws IOException {
        final String csvFile = "static/Movielist.csv";
        final Resource resource = resourceLoader.getResource("classpath:" + csvFile);
        final InputStream inputStream = resource.getInputStream();

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
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
