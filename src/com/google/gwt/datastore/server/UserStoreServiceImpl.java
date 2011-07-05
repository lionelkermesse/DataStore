package com.google.gwt.datastore.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.datastore.client.Employee;
import com.google.gwt.datastore.client.UserStoreService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserStoreServiceImpl extends RemoteServiceServlet implements UserStoreService {
	private final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public UserStoreServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public void deleteEmployee(Employee e) throws Exception {
		List<UserStore> usersStore = new ArrayList<UserStore>();
		PersistenceManager pm = PMF.getPersistenceManager();
		try{
			Query q = pm.newQuery(UserStore.class);
			q.declareParameters("Date hireDate");
			usersStore = (List<UserStore>) q.execute(new Date());
			for (UserStore userStore : usersStore){
				if(userStore.getEmployee().equals(e))
					pm.deletePersistent(userStore);
			}
				
		} finally{
			pm.close();
		}
	}

	@Override
	public void updateEmployee(Employee employee) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Employee getEmployee(String email) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Employee[] getAllEmployees() throws Exception {
		List<UserStore> usersStore = new ArrayList<UserStore>();
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Employee> employees = new ArrayList<Employee>();
		try{
			Query q = pm.newQuery(UserStore.class);
			q.declareParameters("Date hireDate");
			q.setOrdering("hireDate");
			usersStore = (List<UserStore>) q.execute(new Date());
			for (UserStore userStore : usersStore) 
				employees.add(userStore.getEmployee());

		} finally{
			pm.close();
		}
		
		return ((Employee[]) employees.toArray(new Employee[0]));
	}

}
