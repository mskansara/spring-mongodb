package com.eirgrid.springmongodb;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.eirgrid.springmongodb.dto.Address;
import com.eirgrid.springmongodb.dto.Gender;
import com.eirgrid.springmongodb.dto.Student;
import com.eirgrid.springmongodb.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongodbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
        return args -> {
            Address address = new Address(
                    "India", "Mumbai", "4000092"
            );
            String email = "manthankansara@gmail.com";
            Student student = new Student(
                  "Manthan", "Kansara", email, Gender.MALE, address, List.of("Computer Science"), BigDecimal.TEN, LocalDateTime.now()
            );

            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(email));
            List<Student> students = mongoTemplate.find(query, Student.class);

            if(students.size() > 1) {
                throw new IllegalStateException("Found many students with same email - " + email);
            }
            if (students.isEmpty()) {
                System.out.println("Inserting records");
                repository.insert(student);
            } else {
                System.out.println("Already exists");
            }

        };
    }
}
