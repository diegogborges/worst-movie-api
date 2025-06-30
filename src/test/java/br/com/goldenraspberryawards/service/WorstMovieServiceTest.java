package br.com.goldenraspberryawards.service;

import br.com.goldenraspberryawards.domain.Movie;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WorstMovieServiceTest {

    @InjectMocks
    private WorstMovieService worstMovieService;

    @Mock
    private WorstMovieRepository worstMovieRepository;

    @Test
    void premiumMinMaxWinnerWithSuccess() {
        given(worstMovieRepository.getOnlyWinners()).willReturn(
                Arrays.asList(
                        new Movie(1L, 1930, "teste1", "teste1","teste1", true),
                        new Movie(2L, 1930, "teste3", "teste3","teste3", true),
                        new Movie(10L, 1931, "teste2", "teste2","teste2", true),
                        new Movie(3L, 1932, "teste3", "teste3","teste3", true),
                        new Movie(4L, 1934, "teste2", "teste2","teste2", true),
                        new Movie(5L, 1935, "teste2", "teste2","teste2", true),
                        new Movie(6L, 1936, "teste7", "teste7","teste7", true),
                        new Movie(7L, 1937, "teste2", "teste2","teste2", true),
                        new Movie(8L, 1938, "teste1", "teste1","teste1", true),
                        new Movie(9L, 1939, "teste8", "teste8","teste8", true)
                )
        );

        var result = worstMovieService.premiumMinMaxWinner();

        assertEquals(1, result.getMin().size());
        assertEquals("teste2", result.getMin().getFirst().getProducer());
        assertEquals(1, result.getMin().getFirst().getInterval());
        assertEquals(1934, result.getMin().getFirst().getPreviousWin());
        assertEquals(1935, result.getMin().getFirst().getFollowingWin());

        assertEquals(1, result.getMax().size());
        assertEquals("teste1", result.getMax().getFirst().getProducer());
        assertEquals(8, result.getMax().getFirst().getInterval());
        assertEquals(1930, result.getMax().getFirst().getPreviousWin());
        assertEquals(1938, result.getMax().getFirst().getFollowingWin());
    }

    @Test
    void premiumMinMaxWinnerWithThreeItems() {
        given(worstMovieRepository.getOnlyWinners()).willReturn(
                Arrays.asList(
                        new Movie(1L, 1930, "teste1", "teste1","teste1", true),
                        new Movie(2L, 1930, "teste3", "teste3","teste3", true),
                        new Movie(10L, 1931, "teste3", "teste3","teste3", true)
                )
        );

        var result = worstMovieService.premiumMinMaxWinner();

        assertEquals(1, result.getMin().size());
        assertEquals("teste3", result.getMin().getFirst().getProducer());
        assertEquals(1, result.getMin().getFirst().getInterval());
        assertEquals(1930, result.getMin().getFirst().getPreviousWin());
        assertEquals(1931, result.getMin().getFirst().getFollowingWin());

        assertEquals(1, result.getMax().size());
        assertEquals("teste3", result.getMax().getFirst().getProducer());
        assertEquals(1, result.getMin().getFirst().getInterval());
        assertEquals(1930, result.getMin().getFirst().getPreviousWin());
        assertEquals(1931, result.getMin().getFirst().getFollowingWin());
    }

    @Test
    void premiumMinMaxWinnerOnlyTwoItems() {
        given(worstMovieRepository.getOnlyWinners()).willReturn(
                Arrays.asList(
                        new Movie(1L, 1930, "teste1", "teste1","teste1", true),
                        new Movie(2L, 1930, "teste3", "teste3","teste3", true)
                )
        );

        var result = worstMovieService.premiumMinMaxWinner();

        assertEquals(0, result.getMin().size());
        assertEquals(0, result.getMax().size());
    }

    @Test
    void premiumMinMaxWinnerOnlyOneItem() {
        given(worstMovieRepository.getOnlyWinners()).willReturn(
                Arrays.asList(
                        new Movie(1L, 1930, "teste1", "teste1","teste1", true)
                )
        );

        var result = worstMovieService.premiumMinMaxWinner();

        assertEquals(0, result.getMin().size());
        assertEquals(0, result.getMax().size());
    }

    @Test
    void premiumMinMaxWinnerWithEmptyList() {
        given(worstMovieRepository.getOnlyWinners()).willReturn(List.of());

        var result = worstMovieService.premiumMinMaxWinner();

        assertEquals(0, result.getMin().size());
        assertEquals(0, result.getMax().size());
    }
}
