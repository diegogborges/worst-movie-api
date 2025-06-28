package br.com.goldenraspberryawards;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private Integer movieYear;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;
}
