package br.com.goldenraspberryawards;

import br.com.goldenraspberryawards.configuration.Setup;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class SetupConfigurationMocked {

    @Autowired
    private WorstMovieRepository worstMovieRepository;

    @Bean
    @Primary
    public Setup productionFactory() {
        System.out.println("Teste");
        return new Setup(worstMovieRepository);
    }
}
