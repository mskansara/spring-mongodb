package com.eirgrid.springmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.eirgrid.springmongodb.dto.Student;
public interface StudentRepository extends MongoRepository<Student, String > {
}
