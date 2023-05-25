package pt.ul.fc.css.democracia2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

  private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
  private final DemoDataInitializer demoDataInitializer;

  @Autowired
  public DemoApplication(DemoDataInitializer demoDataInitializer) {
    this.demoDataInitializer = demoDataInitializer;
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo() {
    return (args) -> {
      demoDataInitializer.initialize();
    };
  }
}
