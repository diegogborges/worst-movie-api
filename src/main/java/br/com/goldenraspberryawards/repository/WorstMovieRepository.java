package br.com.goldenraspberryawards.repository;

import br.com.goldenraspberryawards.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorstMovieRepository extends JpaRepository<Movie, Long> {
}
