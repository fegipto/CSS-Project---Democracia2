package pt.ul.fc.css.democracia2.controllers.rest;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ul.fc.css.democracia2.DemoDataInitializer;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.repositories.CitizenRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(pt.ul.fc.css.democracia2.controllers.rest.RestUserController.class)
public class RestUserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CitizenRepository citizenRepository;

  @MockBean private DemoDataInitializer demo;

  @InjectMocks private RestUserController userController;

  @Test
  public void testGetDelegateByName_ExistingCitizen() throws Exception {
    String name = "Citizen 1";
    Citizen citizen = new Citizen(name, 1000000000L + 1);

    Mockito.when(citizenRepository.findByName(name)).thenReturn(Optional.of(citizen));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/citizen/name/{name}", name))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
  }

  @Test
  public void testGetDelegateByName_NonExistingCitizen() throws Exception {
    String name = "Non Existing Citizen";
    Optional<Citizen> citizenOptional = Optional.empty();
    Mockito.when(citizenRepository.findByName(name)).thenReturn(citizenOptional);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/citizen/name/{name}", name))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
