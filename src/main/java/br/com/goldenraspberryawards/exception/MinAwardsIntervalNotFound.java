package br.com.goldenraspberryawards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MinAwardsIntervalNotFound extends RuntimeException {
    public MinAwardsIntervalNotFound() {
        super("No minimum producer award range found");
    }
}
