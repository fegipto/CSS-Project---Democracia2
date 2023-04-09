package pt.ul.fc.css.democracia2.domain;

import java.util.List;

public class VoteBox {
  private List<Delegate> inFavor;
  private List<Delegate> against;
  private List<Citizen> voted;
  private Bill correspondingBill;

  private int privateInFavor; // number of citizens in favor
}
