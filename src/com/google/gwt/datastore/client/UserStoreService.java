package com.google.gwt.datastore.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userStore")
public interface UserStoreService extends RemoteService {
	void addEmployee(Employee employee) throws Exception;
	void deleteEmployee(Employee employee) throws Exception;
	void deleteAllEmployees() throws Exception;
	void updateEmployee(Employee employee) throws Exception;
	Employee getEmployee(final String email) throws Exception;
	Employee[] getAllEmployees() throws Exception;
	
}
