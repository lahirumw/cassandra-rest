package com.lah.smgt.repo;

import com.lah.smgt.domain.User;
import com.lah.smgt.shared.ResponseObject;

public interface UserDao {
	
	public ResponseObject getAllStudent();
	
	public ResponseObject getStudent(int id);
	
	public ResponseObject saveStudent(User user);
	
	public ResponseObject updateStudent(int id, User user);
	
	public ResponseObject removeStudent(int id);
	
	public ResponseObject validateStudent(User user);

}
