package com.example.demototale;

import com.example.demototale.entities.Payment;
import com.example.demototale.entities.PaymentStatus;
import com.example.demototale.entities.PaymentType;
import com.example.demototale.entities.Student;
import com.example.demototale.repository.PaymentRepository;
import com.example.demototale.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoTotaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTotaleApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
		return args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
							.firstName("Ali").code("123").programId("DWFSD")
					.build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Kamal").code("124").programId("DWFSD")
					.build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Mohammed").code("125").programId("DLSV")
					.build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st->{
				for (int i = 0; i<10;i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*20000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);
				}
			});

		};
	}

}
