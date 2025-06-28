package br.com.goldenraspberryawards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MaxAwardsIntervalNotFound extends RuntimeException {
    public MaxAwardsIntervalNotFound() {
        super("No maximum producer award range found");
    }
}
