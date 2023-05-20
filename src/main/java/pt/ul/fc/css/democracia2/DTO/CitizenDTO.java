package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Citizen;

/**
 * Class that represents a DelegateDTO
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class CitizenDTO {
  private String name;
  private long cc;

  /**
   * Constructs a new DelegateDTO object
   */
  public CitizenDTO() {
    // No-argument constructor
  }

  /**
   * Constructs a new DelegateDTO object using a delegate
   *
   * @param delegate the delegate to use to create the DTO
   */
  public CitizenDTO(Citizen delegate) {
    this.name = delegate.getName();
    this.cc = delegate.getCC();
  }

  /**
   * Method that get the name of the DelegateDTO
   *
   * @return the name of the corresponding DelegateDTO
   */
  public String getName() {
    return name;
  }

  /**
   * Method that get the cc of the DelegateDTO
   *
   * @return the cc of the corresponding DelegateDTO
   */
  public long getCc() {
    return cc;
  }

  public void setName(String name) {
    this.name = name;
  }


  public void setCc(long cc) {
    this.cc = cc;
  }


}
