package br.com.goldenraspberryawards.service;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.api.v1.controller.view.PremiumMinMaxWinnerView;
import br.com.goldenraspberryawards.exception.MaxAwardsIntervalNotFound;
import br.com.goldenraspberryawards.exception.MinAwardsIntervalNotFound;
import br.com.goldenraspberryawards.api.v1.controller.presenter.PremiumRangePresenter;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import br.com.goldenraspberryawards.util.MaxMovieIntervalComparator;
import br.com.goldenraspberryawards.util.MinMovieIntervalComparator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WorstMovieService {

    private final PremiumRangePresenter premiumRangePresenter;
    private final WorstMovieRepository worstMovieRepository;

    public PremiumMinMaxWinnerView premiumMinMaxWinner() {
        final List<Movie> onlyWinners = worstMovieRepository.getOnlyWinners();

        final var resultMin = getMinRangedWinnerProducerHandler(onlyWinners);
        final var resultMax = getMaxRangedWinnerProducerHandler(onlyWinners);

        final var viewMin = resultMin.stream()
                .map(premiumRangePresenter::presentMin)
                .flatMap(Collection::stream)
                .toList();

        final var viewMax = resultMax.stream()
                .map(premiumRangePresenter::presentMax)
                .flatMap(Collection::stream)
                .toList();

        return new PremiumMinMaxWinnerView()
                .withMin(viewMin)
                .withMax(viewMax);
    }

    private SequencedCollection<List<Movie>> getMinRangedWinnerProducerHandler(List<Movie> onlyWinners) {
        final var producersAward = groupingMoviesByProducerHandler(onlyWinners);

        final var producersMultiplesAwards = producersAward.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .toList();

        final var producerMinInterval = producersMultiplesAwards
                .stream()
                .map(Map.Entry::getValue)
                .min(new MinMovieIntervalComparator())
                .orElseThrow(MinAwardsIntervalNotFound::new);
        final var minInterval = MinMovieIntervalComparator.getMinInterval(producerMinInterval);

        return producersMultiplesAwards
                .stream()
                .filter( e -> MinMovieIntervalComparator.getMinInterval(e.getValue()) == minInterval)
                .map(Map.Entry::getValue)
                .toList();
    }

    private SequencedCollection<List<Movie>> getMaxRangedWinnerProducerHandler(List<Movie> onlyWinners) {
        final var producersAward = groupingMoviesByProducerHandler(onlyWinners);

        final var producersMultiplesAwards = producersAward.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .toList();

        final var producerMaxInterval = producersMultiplesAwards
                .stream()
                .map(Map.Entry::getValue)
                .max(new MaxMovieIntervalComparator())
                .orElseThrow(MaxAwardsIntervalNotFound::new);
        final int maxInterval = MaxMovieIntervalComparator.getMaxInterval(producerMaxInterval);

        return producersMultiplesAwards
                .stream()
                .filter( e -> MaxMovieIntervalComparator.getMaxInterval(e.getValue()) == maxInterval)
                .map(Map.Entry::getValue)
                .toList();
    }

    private Map<String, List<Movie>> groupingMoviesByProducerHandler(List<Movie> onlyWinners) {
        return onlyWinners.stream()
                .map(el -> {
                    String[] producers = el.getProducers()
                            .replace(" and ", ",")
                            .replace(",,",",")
                            .trim().split(",");
                    return Arrays.stream(producers)
                            .map(producer -> new Movie(el.getId(),
                                    el.getMovieYear(),
                                    el.getTitle(),
                                    el.getStudios(),
                                    producer.trim(),
                                    el.getWinner()))
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Movie::getProducers));
    }
}
