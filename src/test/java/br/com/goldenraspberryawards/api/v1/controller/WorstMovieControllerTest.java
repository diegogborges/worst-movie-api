package br.com.goldenraspberryawards.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
class WorstMovieControllerTest {

  protected MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  private final String urlPathResource = MessageFormat.format(
      "{0}{1}", "/v1/", "intervals");

  @BeforeEach
  void before() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

  @Test
  void getPremiumMinMaxWinner() throws Exception {
    final var response =
        this.mockMvc.perform(
                MockMvcRequestBuilders
                    .get(urlPathResource)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn().getResponse().getContentAsString();

    assertEquals(
        "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}",
        response
    );
  }
}