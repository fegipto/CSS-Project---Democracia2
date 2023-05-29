package pt.ul.fc.css.democracia2.DTO;

import pt.ul.fc.css.democracia2.domain.Citizen;

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
   * Constructs a new citizenDTO object using a citizen
   *
   * @param citizen the citizen to use to create the DTO
   */
  public CitizenDTO(Citizen citizen) {
    this.name = citizen.getName();
    this.cc = citizen.getCC();
    this.token = citizen.getToken();
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

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + name.hashCode();
    result = 31 * result + Long.hashCode(cc);
    result = 31 * result + Long.hashCode(token);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof CitizenDTO)) {
      return false;
    }

    CitizenDTO c = (CitizenDTO) o;

    return name.equals(c.getName()) && cc == c.getCc() && token == c.getToken();
  }
}
