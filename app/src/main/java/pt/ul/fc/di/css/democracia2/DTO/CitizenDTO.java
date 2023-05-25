package pt.ul.fc.di.css.democracia2.DTO;

/**
 * Class that represents a citizenDTO
 *
 * @author David Dantas, 56331
 * @author Filipe Egipto, 56272
 * @author Rafael Nisa, 56329
 */
public class CitizenDTO {
  private String name;
  private long cc;
  private long token;

  public long getToken() {
    return token;
  }

  public void setToken(long token) {
    this.token = token;
  }

  /** Constructs a new citizenDTO object */
  public CitizenDTO() {
    // No-argument constructor
  }

  /**
   * Method that get the name of the citizenDTO
   *
   * @return the name of the corresponding citizenDTO
   */
  public String getName() {
    return name;
  }

  /**
   * Method that get the cc of the citizenDTO
   *
   * @return the cc of the corresponding citizenDTO
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
