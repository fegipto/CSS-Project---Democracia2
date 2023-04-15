package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Delegate;

public class DelegateDTO {
  private String name;
  private long cc;

  public DelegateDTO() {
    // No-argument constructor
  }

  public DelegateDTO(Delegate delegate) {
    this.name = delegate.getName();
    this.cc = delegate.getCC();
  }

  public String getName() {
    return name;
  }

  public long getCC() {
    return cc;
  }
}
