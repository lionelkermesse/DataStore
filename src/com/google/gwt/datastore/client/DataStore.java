package com.google.gwt.datastore.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DataStore implements EntryPoint {
	VerticalPanel mainPanel = new VerticalPanel();
	HorizontalPanel addZone = new HorizontalPanel();
	FlexTable flexTable = new FlexTable();
	TextBox nameTextBox = new TextBox();
	TextBox emailTextBox = new TextBox();
	Button deleteButton = new Button("X");
	Button modifyButton = new Button("M");
	Button addButton = new Button("+");
	Label idLabel = new Label("iD");
	Label nameLabel = new Label("Name");
	Label emailLabel = new Label("Email");
	UserStoreServiceAsync userStoreSvc = GWT.create(UserStoreService.class);
	List<Employee> employees = new ArrayList<Employee>();
	List<String> emails = new ArrayList<String>();
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		flexTable.setText(0, 0, "Id");
		flexTable.setText(0, 1, "Name");
		flexTable.setText(0, 2, "Email");
		flexTable.setText(0, 3, "Modify");
		flexTable.setText(0, 4, "Delete");
		
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addEmployee();
			}
		});
		
		addZone.add(nameTextBox);
		addZone.add(emailTextBox);
		addZone.add(addButton);
		
		mainPanel.add(flexTable);
		mainPanel.add(addZone);
		
		RootPanel.get("employeeStore").add(mainPanel);
		
		refreshTable(); 
	}
	
	// Add an employee
	private void addEmployee(){
		String name = nameTextBox.getText().trim();
		String email = emailTextBox.getText().trim();
		if(name.equals("") || email.equals("")){
			Window.alert("Name or Email expected!");
			return;
		}
		
		if(emails.contains(email)){
			Window.alert("'"+email+"' already recorded!!");
			return;
		}
		
		final Employee e = new Employee(name, email);

		userStoreSvc = GWT.create(UserStoreService.class);
		userStoreSvc.addEmployee(e, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				displayTable(e);
			}
			
			public void onFailure(Throwable caught) {}
		});
	}
	
	
	
	
	private void modifyEmployee(Employee e) {
		refreshTable();
	}

	private void deleteEmployee(final Employee e) {
		userStoreSvc = GWT.create(UserStoreService.class);
		userStoreSvc.deleteEmployee(e, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				int row = emails.indexOf(e.getEmail());
				emails.remove(row);
				refreshTable();
			}

			public void onFailure(Throwable caught) {}
		});
	}
	
	private void displayTable(final Employee e){
		
		emails.add(e.getEmail());
		
		deleteButton = new Button("X");
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteEmployee(e);
			}
		});
		
		modifyButton = new Button("M");
		modifyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				modifyEmployee(e);
			}
		});
		
		employees.add(e);
		emails.add(e.getEmail());
		
		int row  = flexTable.getRowCount();
		flexTable.setText(row, 0, "##");
		flexTable.setText(row, 1, e.getName());
		flexTable.setText(row, 2, e.getEmail());
		flexTable.setWidget(row, 3, modifyButton);
		flexTable.setWidget(row, 4, deleteButton);
		
		refreshTable();
	}
	
	// Update the flexTable.	
	private void refreshTable() {
		userStoreSvc = GWT.create(UserStoreService.class);
		AsyncCallback<Employee[]> usAsync = new AsyncCallback<Employee[]>() {
			public void onSuccess(Employee[] result) {
				udpdateTable(result);
			}
			
			public void onFailure(Throwable caught) {}
		};
		
		userStoreSvc.getAllEmployees(usAsync);
	}
	
	protected void udpdateTable(Employee[] result) {
		for (Employee employee : result) {
			updateTable(employee);
		}
	}

	private void updateTable(final Employee e){
		if(!emails.contains(e.getEmail()))
			return;
		
		int row = emails.indexOf(e.getEmail());
		flexTable.setText(row, 1, e.getName());
		flexTable.setText(row, 2, e.getEmail());
	}
	
}
