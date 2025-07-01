package br.com.goldenraspberryawards.service;

import br.com.goldenraspberryawards.api.v1.controller.presenter.PremiumRangePresenter;
import br.com.goldenraspberryawards.api.v1.controller.view.PremiumMinMaxWinnerView;
import br.com.goldenraspberryawards.api.v1.controller.view.PremiumRangeView;
import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import br.com.goldenraspberryawards.util.MaxMovieIntervalComparator;
import br.com.goldenraspberryawards.util.MinMovieIntervalComparator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorstMovieService {

  private final WorstMovieRepository worstMovieRepository;

  public PremiumMinMaxWinnerView premiumMinMaxWinner() {
    final List<Movie> onlyWinners = worstMovieRepository.getOnlyWinners();
    return new PremiumMinMaxWinnerView()
        .withMin(getViewMin(onlyWinners))
        .withMax(getViewMax(onlyWinners));
  }

  private List<PremiumRangeView> getViewMax(List<Movie> onlyWinners) {
    final var resultMax = getMaxRangedWinnerProducerHandler(onlyWinners);
    return resultMax.stream()
        .map(PremiumRangePresenter::presentMax)
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PremiumRangeView> getViewMin(List<Movie> onlyWinners) {
    final var resultMin = getMinRangedWinnerProducerHandler(onlyWinners);
    return resultMin.stream()
        .map(PremiumRangePresenter::presentMin)
        .flatMap(Collection::stream)
        .toList();
  }

  private SequencedCollection<List<Movie>> getMinRangedWinnerProducerHandler(
      List<Movie> onlyWinners) {
    final var producersAward = groupingMoviesByProducerHandler(onlyWinners);

    final var producersMultiplesAwards = producersAward.entrySet().stream()
        .filter(e -> e.getValue().size() > 1)
        .toList();

    final var producerMinInterval = producersMultiplesAwards
        .stream()
        .map(Map.Entry::getValue)
        .min(new MinMovieIntervalComparator())
        .orElse(List.of());
    final var minInterval = MinMovieIntervalComparator.getMinInterval(producerMinInterval);

    return producersMultiplesAwards
        .stream()
        .filter(e -> MinMovieIntervalComparator.getMinInterval(e.getValue()) == minInterval)
        .map(Map.Entry::getValue)
        .toList();
  }

  private SequencedCollection<List<Movie>> getMaxRangedWinnerProducerHandler(
      List<Movie> onlyWinners) {
    final var producersAward = groupingMoviesByProducerHandler(onlyWinners);

    final var producersMultiplesAwards = producersAward.entrySet().stream()
        .filter(e -> e.getValue().size() > 1)
        .toList();

    final var producerMaxInterval = producersMultiplesAwards
        .stream()
        .map(Map.Entry::getValue)
        .max(new MaxMovieIntervalComparator())
        .orElse(List.of());
    final int maxInterval = MaxMovieIntervalComparator.getMaxInterval(producerMaxInterval);

    return producersMultiplesAwards
        .stream()
        .filter(e -> MaxMovieIntervalComparator.getMaxInterval(e.getValue()) == maxInterval)
        .map(Map.Entry::getValue)
        .toList();
  }

  private Map<String, List<Movie>> groupingMoviesByProducerHandler(List<Movie> onlyWinners) {
    return onlyWinners.stream()
        .map(el -> {
          String[] producers = el.getProducers()
              .replace(" and ", ",")
              .replace(",,", ",")
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
