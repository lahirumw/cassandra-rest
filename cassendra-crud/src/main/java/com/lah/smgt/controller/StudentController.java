package com.lah.smgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lah.smgt.domain.User;
import com.lah.smgt.repo.UserDao;
import com.lah.smgt.shared.ResponseObject;

@Controller
public class StudentController {
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObject getAllStudents(){
		
		return userDao.getAllStudent();
	}
	
	
	@RequestMapping(value="/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseObject getStudentDetails(@PathVariable int id){
		
		return userDao.getStudent(id);
	}
	
	@RequestMapping(value="/saveStudent", method=RequestMethod.POST)
	public @ResponseBody ResponseObject saveStudent(@RequestBody User user){
		
		return userDao.saveStudent(user);
	}
	
	@RequestMapping(value="/updateStudent/{id}" , method=RequestMethod.PUT)
	public @ResponseBody ResponseObject updateStudent(@PathVariable int id, @RequestBody User user){
		
		return userDao.updateStudent(id, user);
	}
	
	@RequestMapping(value="/removeStudent/{id}" , method=RequestMethod.DELETE)
	public @ResponseBody ResponseObject removeStudent(@PathVariable int id){
	
		return userDao.removeStudent(id);
	}

}
