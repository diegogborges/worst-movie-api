package br.com.goldenraspberryawards.repository;

import br.com.goldenraspberryawards.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorstMovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select * from Movie m where m.winner is true", nativeQuery = true)
    List<Movie> getOnlyWinners();
}
