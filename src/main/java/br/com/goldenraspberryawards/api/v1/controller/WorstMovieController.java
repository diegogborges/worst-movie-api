package br.com.goldenraspberryawards.api.v1.controller;

import br.com.goldenraspberryawards.api.v1.controller.view.PremiumMinMaxWinnerView;
import br.com.goldenraspberryawards.service.WorstMovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/worst-movie")
public class WorstMovieController {

    private final WorstMovieService worstMovieService;

    public WorstMovieController(WorstMovieService worstMovieService) {
        this.worstMovieService = worstMovieService;
    }

    @GetMapping
    public PremiumMinMaxWinnerView premiumMinMaxWinner() {
        return worstMovieService.premiumMinMaxWinner();
    }
}
