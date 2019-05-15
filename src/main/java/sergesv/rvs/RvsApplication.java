package sergesv.rvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RvsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RvsApplication.class, args);
	}
}
