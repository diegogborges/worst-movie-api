package br.com.goldenraspberryawards.util;

import br.com.goldenraspberryawards.domain.Movie;
import java.util.Comparator;
import java.util.List;

public class MaxMovieIntervalComparator implements Comparator<List<Movie>> {

  @Override
  public int compare(List<Movie> o1, List<Movie> o2) {
    var interval1 = getMaxInterval(o1);
    var interval2 = getMaxInterval(o2);

    return interval1 - interval2;
  }

  public static int getMaxInterval(List<Movie> movies) {
    int max = 0;
    Integer lastYear = 0;
    var orderedMovies = movies.stream()
        .sorted((a, b) -> a.getMovieYear().compareTo(b.getMovieYear()))
        .toList();
    for (Movie e : orderedMovies) {
      if (lastYear == 0) {
        lastYear = e.getMovieYear();
        continue;
      }
      var diff = e.getMovieYear() - lastYear;
      if (diff > max) {
        max = diff;
      }
      lastYear = e.getMovieYear();
    }
    return max;
  }
}
