package com.example.demototale.services;


import com.example.demototale.dtos.PaymentDto;
import com.example.demototale.entities.Payment;
import com.example.demototale.entities.PaymentStatus;
import com.example.demototale.entities.PaymentType;
import com.example.demototale.entities.Student;
import com.example.demototale.repository.PaymentRepository;
import com.example.demototale.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;


    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, PaymentDto paymentDto) throws IOException {
        /*debut de partie backend pour upload dun fichier*/
        Path folderPath = Paths.get(System.getProperty("user.home"),"ensetData","payments");
        if (!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"ensetData","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        /*fin de partie backend pour upload dun fichier*/
        Student student = studentRepository.findByCode(paymentDto.getCode());
        Payment payment = Payment.builder()
                .type(paymentDto.getType())
                .status(PaymentStatus.CREATED)
                .date(paymentDto.getDate())
                .student(student)
                .amount(paymentDto.getAmount())
                .file(filePath.toUri().toString())
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymetStatus(PaymentStatus status, Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
