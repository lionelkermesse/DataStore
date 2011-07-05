package com.google.gwt.datastore.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.datastore.client.Employee;
import com.google.gwt.datastore.client.UserStoreService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserStoreServiceImpl extends RemoteServiceServlet implements UserStoreService {
	private final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public UserStoreServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public void addEmployee(Employee employee) throws Exception{
		PersistenceManager pm = PMF.getPersistenceManager();
		try{
			Random rnd = new Random();
			int salary = rnd.nextInt()*25000;
			pm.makePersistent(new UserStore(employee, salary));
		} finally{
			pm.close();
		}
	}

	public void deleteEmployee(Employee e) throws Exception {
		List<UserStore> usersStore = new ArrayList<UserStore>();
		PersistenceManager pm = PMF.getPersistenceManager();
		try{
			Query q = pm.newQuery(UserStore.class);
			usersStore = ((List<UserStore>) q.execute());
			for (UserStore userStore : usersStore){
				if(userStore.getEmployee().getEmail().equals(e.getEmail()))
					pm.deletePersistent(userStore);
			}
				
		} finally{
			pm.close();
		}
	}
	
	public void deleteAllEmployees() throws Exception {
		PersistenceManager pm = PMF.getPersistenceManager();
		try{
			Query q = pm.newQuery(UserStore.class);
			q.setOrdering("hireDate");
			List<UserStore> usersStore = ((List<UserStore>) q.execute());
			for (UserStore userStore : usersStore)
					pm.deletePersistent(userStore);
				
		} finally{
			pm.close();
		}
	}

	public void updateEmployee(Employee employee) throws Exception {
		// TODO Auto-generated method stub
	}

	public Employee getEmployee(String email) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	public Employee[] getAllEmployees() throws Exception {
		List<UserStore> usersStore = new ArrayList<UserStore>();
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Employee> employees = new ArrayList<Employee>();
		try{
			Query q = pm.newQuery(UserStore.class);
			q.setOrdering("hireDate");
			usersStore = ((List<UserStore>) q.execute());
			for (UserStore userStore : usersStore) 
				employees.add(userStore.getEmployee());

		} finally{
			pm.close();
		}
		
		return ((Employee[]) employees.toArray(new Employee[0]));
	}

	
}
