package br.com.goldenraspberryawards.api.v1.controller.presenter;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.api.v1.controller.view.PremiumRangeView;
import br.com.goldenraspberryawards.util.MaxMovieIntervalComparator;
import br.com.goldenraspberryawards.util.MinMovieIntervalComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PremiumRangePresenter {

    public PremiumRangeView present(Movie entity, Movie entity2) {
        return new PremiumRangeView()
                .withProducer(entity.getProducers())
                .withPreviousWin(entity.getMovieYear())
                .withFollowingWin(entity2.getMovieYear());
    }

    public List<PremiumRangeView> presentMin(List<Movie> entities) {
        final int interval = MinMovieIntervalComparator.getMinInterval(entities);
        final var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        final var rangedMovies = filterMinIntervalMovies(orderedMovies, interval);

        List<PremiumRangeView> winners = new ArrayList<>();
        for(int i = 0; i < rangedMovies.size(); i+=2) {
            winners.add(present(rangedMovies.get(i), rangedMovies.get(i+1))
                    .withInterval(interval));
        }
        return winners;
    }

    public List<PremiumRangeView> presentMax(List<Movie> entities) {
        final int interval = MaxMovieIntervalComparator.getMaxInterval(entities);
        final var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        final var rangedMovies = filterMaxIntervalMovies(orderedMovies, interval);

        List<PremiumRangeView> winners = new ArrayList<>();
        for(int i = 0; i < rangedMovies.size(); i+=2) {
            winners.add(present(rangedMovies.get(i), rangedMovies.get(i+1))
                    .withInterval(interval));
        }
        return winners;
    }

    private List<Movie> filterMinIntervalMovies(List<Movie> movies, int interval){
        Movie last = movies.getFirst();
        List<Movie> rangedList = new ArrayList<>();
        for (Movie movie: movies) {
            var  a = movie.getMovieYear() - last.getMovieYear();
            if(a == interval) {
                rangedList.add(last);
                rangedList.add(movie);
            }
            last = movie;
        }
        return rangedList;
    }

    private List<Movie> filterMaxIntervalMovies(List<Movie> movies, int interval){
        Movie last = movies.getFirst();
        List<Movie> rangedList = new ArrayList<>();
        for (Movie movie: movies) {
            var  a = movie.getMovieYear() - last.getMovieYear();
            if(a == interval) {
                rangedList.add(last);
                rangedList.add(movie);
            }
            last = movie;
        }
        return rangedList;
    }
}
