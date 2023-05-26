package pt.ul.fc.css.democracia2.controllers.Web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.css.democracia2.DTO.CitizenDTO;
import pt.ul.fc.css.democracia2.DemoDataInitializer;
import pt.ul.fc.css.democracia2.controllers.web.CitizenController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CitizenController.class)
public class CitizenControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private RestTemplate restTemplate;

  @MockBean private DemoDataInitializer demo;

  @Test
  public void testRestGetCitizen() throws Exception {
    CitizenDTO citizen = new CitizenDTO();
    citizen.setName("Citizen 1");
    citizen.setCc(1000000000L + 1);
    citizen.setToken(1000000000L + 1);

    when(restTemplate.getForEntity(anyString(), eq(CitizenDTO.class)))
        .thenReturn(new ResponseEntity<>(citizen, HttpStatus.OK));

    mockMvc
        .perform(get("/citizen").param("name", "Citizen 1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
        .andExpect(request().sessionAttribute("citizen", citizen));
  }

  @Test
  public void testLogoutString() throws Exception {
    mockMvc
        .perform(post("/citizen/logout"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
        .andExpect(request().sessionAttributeDoesNotExist("citizen"));
  }
}
