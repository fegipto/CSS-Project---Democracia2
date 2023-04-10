package pt.ul.fc.css.democracia2.domain;

/** This class represents a chosen delegate */
public class ChosenDelegate {
  private Topic topic;
  private Delegate delegate;

  /**
   * Constructs a new ChosenDelegate object using a delegate and a topic
   *
   * @param delegate a delegate
   * @param topic
   */
  public ChosenDelegate(Delegate delegate, Topic topic) {
    super();
    this.topic = topic;
    this.delegate = delegate;
  }

  /**
   * Method that gets the delegate
   *
   * @return the correspoding delegate
   */
  public Delegate getDelegate() {
    return delegate;
  }

  /**
   * Method that gets the topic
   *
   * @return the correspoding topic
   */
  public Topic getTopic() {
    return topic;
  }
}
