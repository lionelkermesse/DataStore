package com.google.gwt.datastore.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserStoreServiceAsync {

	void addEmployee(Employee employee, AsyncCallback<Void> callback);

	void deleteEmployee(Employee employee, AsyncCallback<Void> callback);

	void getEmployee(String email, AsyncCallback<Employee> callback);

	void updateEmployee(Employee employee, AsyncCallback<Void> callback);

	void getAllEmployees(AsyncCallback<Employee[]> callback);

}
