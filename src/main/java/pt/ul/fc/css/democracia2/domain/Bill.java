package pt.ul.fc.css.democracia2.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Bill {
	private String title;
	private BillStatus status;
	private String description;
	private byte[] file;
	private LocalDateTime validady;
	private Topic topic;
	
	private VoteBox votes;
	private List<Citizen> supporters;
	
	
	
}
