package br.com.goldenraspberryawards.api.v1.controller;

import br.com.goldenraspberryawards.configuration.Setup;
import br.com.goldenraspberryawards.repository.WorstMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Setup.class)
@ActiveProfiles("test")
class WorstMovieController2Test {

    protected MockMvc mockMvc;

    @InjectMocks
    private Setup service;

    @MockitoBean
    private final WorstMovieRepository worstMovieRepository = Mockito.mock(WorstMovieRepository.class);

    @Autowired
    private WebApplicationContext context;

    private final String urlPathResource = MessageFormat.format(
            "{0}{1}", "/v1/", "worst-movie");

    @BeforeEach
    void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        ReflectionTestUtils.setField(service, "csvFile", "teste");
        System.out.println("teste");
    }

    @Test
    void getPremiumMinMaxWinner() throws Exception {


        var response = this.mockMvc.perform(MockMvcRequestBuilders.get(urlPathResource).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals("{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}", response);
    }
}