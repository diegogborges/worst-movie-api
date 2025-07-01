package br.com.goldenraspberryawards.api.v1.controller.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremiumMinMaxWinnerView {

  private List<PremiumRangeView> min;
  private List<PremiumRangeView> max;

  public PremiumMinMaxWinnerView withMin(List<PremiumRangeView> min) {
    this.min = min;
    return this;
  }

  public PremiumMinMaxWinnerView withMax(List<PremiumRangeView> max) {
    this.max = max;
    return this;
  }
}
