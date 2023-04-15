package pt.ul.fc.css.democracia2;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.domain.*;
import pt.ul.fc.css.democracia2.repositories.BillRepository;
import pt.ul.fc.css.democracia2.repositories.DelegateRepository;
import pt.ul.fc.css.democracia2.repositories.TopicRepository;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CreateBillTest extends MockDatabaseTests {

    @Autowired
    private CreateBillService createBillService;

    @Autowired private BillRepository billRepository;
    @Autowired private TopicRepository topicRepository;

    @Autowired private DelegateRepository delegateRepository;

    @Test
    public void shouldCreateBills() {

        Delegate delegate1 = delegateRepository.findByName("Delegate 1");

        // first bill
        createBillService.presentBill(delegate1, "Test1", "Test1", LocalDateTime.now().plusHours(12));

        //Ensure that all topics are returned
        assertEquals(topicRepository.getTopics().size(), createBillService.getTopics().size());

        createBillService.chooseTopic("Education");
        createBillService.appendFile(new byte[16]);
        createBillService.submit();

        // second bill
        createBillService.presentBill(delegate1, "Test2", "Test2", LocalDateTime.now().plusDays(15));
        createBillService.chooseTopic("Education");
        createBillService.appendFile(new byte[16]);
        createBillService.submit();

        // ensure that bills are created
        List<Bill> bills = billRepository.getBills();
        assertEquals(2, bills.size());
    }
}
