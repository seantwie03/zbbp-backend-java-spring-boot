package me.seantwiehaus.zbbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZbbpApplication {

  public static void main(String[] args) {
    SpringApplication.run(ZbbpApplication.class, args);
  }

}
