package br.com.goldenraspberryawards.api.v1.controller.presenter;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.api.v1.controller.view.PremiumRangeView;
import br.com.goldenraspberryawards.util.MaxMovieIntervalComparator;
import br.com.goldenraspberryawards.util.MinMovieIntervalComparator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PremiumRangePresenter {

    public static PremiumRangeView present(Movie entity, Movie entity2) {
        return new PremiumRangeView()
                .withProducer(entity.getProducers())
                .withPreviousWin(entity.getMovieYear())
                .withFollowingWin(entity2.getMovieYear());
    }

    public static List<PremiumRangeView> presentMin(List<Movie> entities) {
        final int interval = MinMovieIntervalComparator.getMinInterval(entities);
        return getPremiumRangeViews(entities, interval);
    }

    public static List<PremiumRangeView> presentMax(List<Movie> entities) {
        final int interval = MaxMovieIntervalComparator.getMaxInterval(entities);
        return getPremiumRangeViews(entities, interval);
    }

    private static List<PremiumRangeView> getPremiumRangeViews(List<Movie> entities, int interval) {
        final var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        final var rangedMovies = filterIntervalMovies(orderedMovies, interval);

        List<PremiumRangeView> winners = new ArrayList<>();
        for(int i = 0; i < rangedMovies.size(); i+=2) {
            winners.add(present(rangedMovies.get(i), rangedMovies.get(i+1))
                    .withInterval(interval));
        }
        return winners;
    }

    private static List<Movie> filterIntervalMovies(List<Movie> movies, int interval){
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
