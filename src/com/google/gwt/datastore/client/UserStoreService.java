package com.google.gwt.datastore.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userStore")
public interface UserStoreService extends RemoteService {
	public void addEmployee(Employee employee) throws Exception;
	public void deleteEmployee(Employee employee) throws Exception;
	public void updateEmployee(Employee employee) throws Exception;
	public Employee getEmployee(final String email) throws Exception;
	public Employee[] getAllEmployees() throws Exception;
}
