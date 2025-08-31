package SelfSens.SelfSens;

import SelfSens.SelfSens.ProfilesData.ProfilesData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SelfSensApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfSensApplication.class, args);
		System.out.println("SelfSens Running ......! in");
	}

}
