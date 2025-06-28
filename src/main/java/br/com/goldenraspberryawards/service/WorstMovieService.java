package br.com.goldenraspberryawards.service;

import br.com.goldenraspberryawards.Movie;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorstMovieService {

    private final WorstMovieRepository worstMovieRepository;

    public WorstMovieService(WorstMovieRepository worstMovieRepository) {
        this.worstMovieRepository = worstMovieRepository;
    }

    public List<Movie> findAll() {
        return worstMovieRepository.findAll();
    }
}
