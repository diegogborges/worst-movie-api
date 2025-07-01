package br.com.goldenraspberryawards.configuration;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Setup {

  private static final Logger logger = LogManager.getLogger(Setup.class);
  private final ResourceLoader resourceLoader;
  private final WorstMovieRepository movieRepository;

  @PostConstruct
  private void setupData() throws IOException {
    final String csvFile = "static/Movielist.csv";
    final Resource resource = resourceLoader.getResource("classpath:" + csvFile);
    final InputStream inputStream = resource.getInputStream();

    try (final BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(inputStream))) {
      logger.info("File loaded and read file");

      List<Movie> movieList = new ArrayList<>();
      extractValuesFromLines(bufferedReader, movieList);

      logger.info("Saving the movies");
      this.movieRepository.saveAll(movieList);
    } catch (IOException exception) {
      logger.error("Error to load and read file: {}", String.valueOf(exception));
    }
  }

  private static void extractValuesFromLines(BufferedReader bufferedReader, List<Movie> movieList)
      throws IOException {
    boolean isHeader = true;
    String line;
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
  }
}
