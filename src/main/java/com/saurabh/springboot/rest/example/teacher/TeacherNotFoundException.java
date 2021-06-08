package com.saurabh.springboot.rest.example.teacher;

public class TeacherNotFoundException extends RuntimeException {

	public TeacherNotFoundException(String exception) {
		super(exception);
	}

}
