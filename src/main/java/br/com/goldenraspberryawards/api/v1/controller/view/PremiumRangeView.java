package br.com.goldenraspberryawards.api.v1.controller.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremiumRangeView {

  private String producer;
  private Integer interval;
  private Integer previousWin;
  private Integer followingWin;

  public PremiumRangeView withProducer(String producer) {
    this.producer = producer;
    return this;
  }

  public PremiumRangeView withInterval(Integer interval) {
    this.interval = interval;
    return this;
  }

  public PremiumRangeView withPreviousWin(Integer previousWin) {
    this.previousWin = previousWin;
    return this;
  }

  public PremiumRangeView withFollowingWin(Integer followingWin) {
    this.followingWin = followingWin;
    return this;
  }
}
