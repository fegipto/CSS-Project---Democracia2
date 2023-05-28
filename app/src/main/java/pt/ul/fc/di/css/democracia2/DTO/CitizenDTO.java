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

  /**
   * Method that gets the token of the citizenDTO
   *
   * @return the token of the corresponding citizenDTO
   */
  public long getToken() {
    return token;
  }

  /**
   * Method that sets the token of the citizenDTO
   *
   * @param token the token of the corresponding citizenDTO
   */
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
   * Method that gets the cc of the citizenDTO
   *
   * @return the cc of the corresponding citizenDTO
   */
  public long getCc() {
    return cc;
  }

  /**
   * Method that sets the name of the citizenDTO
   *
   * @param name the name of the corresponding citizenDTO
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Method that sets the cc of the citizenDTO
   *
   * @param cc the cc of the corresponding citizenDTO
   */
  public void setCc(long cc) {
    this.cc = cc;
  }
}
