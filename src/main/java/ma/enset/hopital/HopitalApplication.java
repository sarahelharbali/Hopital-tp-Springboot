package ma.enset.hopital;

import ma.enset.hopital.entities.Patient;
import ma.enset.hopital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {

		SpringApplication.run(HopitalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//creer des patient
		patientRepository.save(new Patient(null,"Mohammed",new Date(),false,500));
		patientRepository.save(new Patient(null,"Hanane",new Date(),false,541));
		patientRepository.save(new Patient(null,"imane",new Date(),false,526));


	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	//@Bean
	CommandLineRunner commandLineRunnerJdbcUsers(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder=passwordEncoder();
		return args -> {
			jdbcUserDetailsManager.createUser(
					User.withUsername("user1").password(passwordEncoder.encode("12345")).roles("USER").build()
			);
			jdbcUserDetailsManager.createUser(
					User.withUsername("user2").password(passwordEncoder.encode("12345")).roles("USER").build()
			);
			jdbcUserDetailsManager.createUser(
					User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
			);
		};
	}
}
