package com.google.gwt.datastore.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.datastore.client.Employee;



@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserStore {
	@PrimaryKey @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent(serialized = "true") Employee employee;
	@Persistent int salary;
	@Persistent private Date hireDate;
	
	public UserStore(Employee employee, int salary){
		this.key = KeyFactory.createKey(UserStore.class.getSimpleName(), employee.getEmail());
		this.employee = employee;
		this.salary = salary;
		this.hireDate = new Date();
	}

	public Key getKey() {
		return key;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
}
