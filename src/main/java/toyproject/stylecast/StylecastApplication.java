package toyproject.stylecast;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StylecastApplication {

	public static void main(String[] args) {
		SpringApplication.run(StylecastApplication.class, args);
	}
//
//	@Bean
//	Hibernate5Module hibernate5Module(){
//		return new Hibernate5Module();
//	}

}
