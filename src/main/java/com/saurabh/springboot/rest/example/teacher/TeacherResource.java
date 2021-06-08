package com.saurabh.springboot.rest.example.teacher;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class TeacherResource {

	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping("/teachers")
	public List<Teacher> retrieveAllTeachers() {
		return teacherRepository.findAll();
	}

	@GetMapping("/teachers/{id}")
	public Teacher retrieveTeacher(@PathVariable long id) {
		Optional<Teacher> student = teacherRepository.findById(id);

		if (!student.isPresent())
			throw new TeacherNotFoundException("id-" + id);

		return student.get();
	}

	@DeleteMapping("/teachers/{id}")
	public void deleteTeacher(@PathVariable long id) {
		teacherRepository.deleteById(id);
	}

	@PostMapping("/teachers")
	public ResponseEntity<Object> createTeacher(@RequestBody Teacher student) {
		Teacher savedTeacher = teacherRepository.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedTeacher.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/teachers/{id}")
	public ResponseEntity<Object> updateTeacher(@RequestBody Teacher student, @PathVariable long id) {

		Optional<Teacher> studentOptional = teacherRepository.findById(id);

		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();

		student.setId(id);
		
		teacherRepository.save(student);

		return ResponseEntity.noContent().build();
	}
}
