package com.lah.smgt.repo;

/*
 * @   Developed by LahiruA 22/03/2016
 * @   Data persistence
 * 
 * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.lah.smgt.domain.User;
import com.lah.smgt.shared.CassandraCofig;
import com.lah.smgt.shared.Error;
import com.lah.smgt.shared.ResponseObject;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	CassandraCofig cassandraCofig;

	private Session session;
	

	/*
	 * get All records
	 * @see com.lah.smgt.repo.UserDao#getAllStudent()
	 * 
	 */
	@Override
	public ResponseObject getAllStudent() {
		ResponseObject resObj = new ResponseObject();
		ResultSet result = null;
		List<User> userList = new ArrayList<User>();
		Error error = new Error();
		Map<String, String> map = new HashMap<String, String>();
		List<Error> errList = new ArrayList<Error>();

		try {
			session = cassandraCofig.session();
			String cql = "select * from user";
			result = session.execute(cql);

			for (Row row : result.all()) {
				User user = new User();
				user.setId(row.getInt("id"));
				user.setAddress(row.getString("address"));
				user.setName(row.getString("name"));
				userList.add(user);
			}

			if (userList.isEmpty()) {
				map.put("errorLabel1", "No Result Found..");
				error.setErType(map);
				errList.add(error);
				resObj.setErrList(errList);
			} else {
				resObj.setData(userList);
				map.put("numRecords", String.valueOf(userList.size()));
				resObj.setMsg(map);
			}

		} catch (DataAccessException e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		} catch (Exception e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		}

		return resObj;
	}

	
	/*
	 * get specific record
	 * @see com.lah.smgt.repo.UserDao#getStudent(int)
	 * 
	 */
	@Override
	public ResponseObject getStudent(int id) {
		ResponseObject resObj = new ResponseObject();
		List<User> userList = new ArrayList<User>();
		Map<String, String> map = new HashMap<String, String>();
		Error error = new Error();
		List<Error> errList = new ArrayList<Error>();

		try {
			userList = findUser(id, userList);

			if (userList.isEmpty()) {
				map.put("errorLabel1", "No Result Found..");
				error.setErType(map);
				errList.add(error);
				resObj.setErrList(errList);
			} else {
				resObj.setData(userList);
			}

		} catch (DataAccessException e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		} catch (Exception e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		}
		return resObj;
	}
	

	/*
	 * find user
	 * 
	 */
	private List<User> findUser(int id, List<User> userList) throws Exception {
		ResultSet result = null;
		session = cassandraCofig.session();
		String cql = "select * from user where id=" + id;
		result = session.execute(cql);

		for (Row row : result.all()) {
			User user = new User();
			user.setId(row.getInt("id"));
			user.setAddress(row.getString("address"));
			user.setName(row.getString("name"));
			userList.add(user);
		}
		return userList;
	}

	
	/*
	 * save user record
	 * @see com.lah.smgt.repo.UserDao#saveStudent(com.lah.smgt.domain.User)
	 * 
	 */
	@Override
	public ResponseObject saveStudent(User user) {
		//validateStudent(User user) -- validation need to check
		ResponseObject resObj = new ResponseObject();
		Map<String, String> map = new HashMap<String, String>();
		List<User> userList = new ArrayList<User>();
		Error error = new Error();
		List<Error> errList = new ArrayList<Error>();

		String cql = "insert into user (id,address,name) values (?,?,?)";
		try {
			userList = findUser(user.getId(), userList);
			if (!userList.isEmpty()) {
				map.put("errorLabel1", "Allready Registerd user..");
				error.setErType(map);
				errList.add(error);
				resObj.setErrList(errList);
				userList = null;
			} else {
				session = cassandraCofig.session();
				PreparedStatement pStm = session.prepare(cql);
				BoundStatement bound = new BoundStatement(pStm);
				session.execute(bound.bind(user.getId(), user.getAddress(),
						user.getName()));
				map.put("successMsg", "Successfully Saved");
				resObj.setMsg(map);
			}
		} catch (DataAccessException e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		} catch (Exception e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		}

		return resObj;
	}

	
	/*
	 * update existing user record
	 * @see com.lah.smgt.repo.UserDao#updateStudent(int, com.lah.smgt.domain.User)
	 * 
	 */
	@Override
	public ResponseObject updateStudent(int id, User user) {
		//validateStudent(User user) -- validation need to check
		ResponseObject resObj = new ResponseObject();
		Map<String, String> map = new HashMap<String, String>();
		List<User> userList = new ArrayList<User>();
		Error error = new Error();
		List<Error> errList = new ArrayList<Error>();

		String cql = "update user set address = ? ,name = ? where id = ?";
		try {
			userList = findUser(user.getId(), userList);
			if (userList.isEmpty()) {
				map.put("errorLabel1", "No records to update..");
				error.setErType(map);
				errList.add(error);
				resObj.setErrList(errList);
				userList = null;
			} else {
				session = cassandraCofig.session();
				PreparedStatement pStm = session.prepare(cql);
				BoundStatement bound = new BoundStatement(pStm);
				session.execute(bound.bind(user.getAddress(),
						user.getName(), id));
				map.put("successMsg", "Successfully Updated");
				resObj.setMsg(map);
			}
		} catch (DataAccessException e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		} catch (Exception e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		}

		return resObj;
	}

	
	/*
	 * remove record from db
	 * @see com.lah.smgt.repo.UserDao#removeStudent(int)
	 * 
	 */
	@Override
	public ResponseObject removeStudent(int id) {
		ResponseObject resObj = new ResponseObject();
		Map<String, String> map = new HashMap<String, String>();
		List<User> userList = new ArrayList<User>();
		Error error = new Error();
		List<Error> errList = new ArrayList<Error>();

		String cql = "DELETE FROM user where id = ?";
		try {
			userList = findUser(id, userList);
			if (userList.isEmpty()) {
				map.put("errorLabel1", "No records to delete..");
				error.setErType(map);
				errList.add(error);
				resObj.setErrList(errList);
				userList = null;
			} else {
				session = cassandraCofig.session();
				PreparedStatement pStm = session.prepare(cql);
				BoundStatement bound = new BoundStatement(pStm);
				session.execute(bound.bind(id));
				map.put("successMsg", "Successfully Deleted..");
				resObj.setMsg(map);
			}
		} catch (DataAccessException e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		} catch (Exception e) {
			map.put("errorLabel1", e.getMessage());
			error.setErType(map);
			errList.add(error);
			resObj.setErrList(errList);
		}

		return resObj;
	}

	
	/*
	 * all user validations need to implement here
	 * @see com.lah.smgt.repo.UserDao#validateStudent(com.lah.smgt.domain.User)
	 * 
	 */
	@Override
	public ResponseObject validateStudent(User user) {
		ResponseObject resObj = new ResponseObject();
		// validation add here
		return resObj;
	}

}
