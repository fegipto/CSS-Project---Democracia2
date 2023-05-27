package pt.ul.fc.css.democracia2.controllers.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pt.ul.fc.css.democracia2.DTO.BillDTO;
import pt.ul.fc.css.democracia2.DemoDataInitializer;
import pt.ul.fc.css.democracia2.domain.Bill;
import pt.ul.fc.css.democracia2.domain.Citizen;
import pt.ul.fc.css.democracia2.domain.Delegate;
import pt.ul.fc.css.democracia2.domain.Topic;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;
import pt.ul.fc.css.democracia2.services.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(pt.ul.fc.css.democracia2.controllers.rest.RestBillController.class)
public class RestBillControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired ObjectMapper mapper;

  @MockBean private TopicRepository topicRepository;
  @MockBean private ListAvailableVotesService billsService;
  @MockBean private ConsultNonExpiredBillService consultBillService;
  @MockBean private ProposeBillService proposeBillService;
  @MockBean private SupportBillService supportBillService;
  @MockBean private VotingService votingService;
  @MockBean private DemoService demoService;

  @MockBean private DemoDataInitializer demo;

  @InjectMocks private RestBillController billController;

  @Test
  public void testListAvailableVotes() throws Exception {
    List<BillDTO> billDTOList = new ArrayList<>();
    for (long i = 0; i < 25; i++) {
      BillDTO bill = new BillDTO();
      bill.setId(i);
      billDTOList.add(bill);
    }

    System.out.println(billsService.listAvailableVotes());
    Mockito.when(billsService.listAvailableVotes()).thenReturn(billDTOList);

    MvcResult result =
        mockMvc.perform(get("/api/bills/votable")).andExpect(status().isOk()).andReturn();
    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  public void testAllTopics() throws Exception {
    List<Topic> topics = new ArrayList<>();
    for (long i = 1; i <= 5; i++) {
      Topic t = new Topic();
      t.setName("Topic " + i);
      t.setId(1000000L + i);
      topics.add(t);
    }

    Mockito.when(proposeBillService.getTopics()).thenReturn(topics);

    MvcResult result = mockMvc.perform(get("/api/topics")).andReturn();

    System.out.print(result.getResponse().getContentAsString());
  }

  @Test
  public void testSpecificBill() throws Exception {

    List<BillDTO> billDTOList = new ArrayList<>();
    for (long i = 1; i <= 25; i++) {
      BillDTO bill = new BillDTO();
      bill.setId(1000000L + i);
      billDTOList.add(bill);
    }
    long id = billDTOList.get(0).getId();

    Mockito.when(consultBillService.getBill(id)).thenReturn(Optional.of(billDTOList.get(0)));

    mockMvc.perform(get("/api/bill/{id}", id)).andExpect(status().isOk());
  }

  @Test
  public void testOpenBills() throws Exception {
    Delegate delegate1 = new Delegate("Delegate 1", 2000000L + 1);
    List<BillDTO> billDTOList = new ArrayList<>();

    Topic t = new Topic();
    t.setName("Health");
    t.setId(1000000L + 1);

    for (long i = 1; i <= 5; i++) {
      Bill added1 =
          delegate1.proposeBill(
              "Bill " + i, "null", new byte[] {}, LocalDateTime.now().plusMonths(4), t);
      int count = 0;
      for (int j = 1; j <= 100000; j++) {
        Citizen cit = new Citizen("Citizen " + j, j);
        added1.supportBill(cit);
        if (count == 10000) {
          break;
        }
        count++;
      }
      BillDTO billDTO = new BillDTO(added1);
      billDTO.setId(i);
      billDTOList.add(billDTO);
    }
    Bill added1 =
        delegate1.proposeBill(
            "Bill " + 6, "null", new byte[] {}, LocalDateTime.now().plusMonths(4), t);
    BillDTO billDTO = new BillDTO(added1);
    billDTO.setId(6);
    billDTOList.add(billDTO);

    Mockito.when(consultBillService.listNonExpired()).thenReturn(billDTOList);
    System.out.println(consultBillService.listNonExpired());

    mockMvc.perform(get("/api/bills/open")).andExpect(status().isOk()).andReturn();
  }

  @Test
  public void testCreateBill() throws Exception {
    BillDTO bill = new BillDTO();
    bill.setId(1);
    bill.setTitle("Bill 1");

    Mockito.when(proposeBillService.presentBill(bill)).thenReturn(bill);

    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/api/bills")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(bill));

    mockMvc.perform(mockRequest).andExpect(status().isOk());
  }

  @Test
  public void testGetVote() throws Exception {
    BillDTO bill = new BillDTO();
    bill.setId(1);
    bill.setTitle("Bill 1");

    Citizen citizen = new Citizen("Citizen 1", 1000L);

    Mockito.when(votingService.getOmmitedVote(citizen.getToken(), bill.getId()))
        .thenReturn(Optional.of(true));

    MvcResult resultTrue =
        mockMvc
            .perform(get("/api/bill/{citizenId}/voted/{billId}", citizen.getToken(), bill.getId()))
            .andExpect(status().isOk())
            .andReturn();

    Mockito.when(votingService.getOmmitedVote(citizen.getToken(), bill.getId()))
        .thenReturn(Optional.of(false));

    MvcResult resultFalse =
        mockMvc
            .perform(get("/api/bill/{citizenId}/voted/{billId}", citizen.getToken(), bill.getId()))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(resultTrue.getResponse().getContentAsString(), "true");
    assertEquals(resultFalse.getResponse().getContentAsString(), "false");
  }
}
