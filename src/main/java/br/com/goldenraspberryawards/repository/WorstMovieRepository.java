package br.com.goldenraspberryawards.repository;

import br.com.goldenraspberryawards.domain.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorstMovieRepository extends JpaRepository<Movie, Long> {

  @Query(value = "select * from Movie m where m.winner is true", nativeQuery = true)
  List<Movie> getOnlyWinners();
}
