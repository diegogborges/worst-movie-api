package br.com.goldenraspberryawards.api.v1.controller;

import br.com.goldenraspberryawards.api.v1.controller.view.PremiumMinMaxWinnerView;
import br.com.goldenraspberryawards.service.WorstMovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("v1/intervals")
public class WorstMovieController {

  private final WorstMovieService worstMovieService;

  @GetMapping
  public PremiumMinMaxWinnerView premiumMinMaxWinner() {
    return worstMovieService.premiumMinMaxWinner();
  }
}
