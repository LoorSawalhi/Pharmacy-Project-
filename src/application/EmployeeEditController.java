package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import Relations.*;

/**
 * 
 * @version 30 January 2022
 * @author Ahmad Abbas
 */
public class EmployeeEditController implements Initializable {

	@FXML
	private TableView<Employee> employeeTable;

	@FXML
	private TableColumn<Employee, String> idColumn;

	@FXML
	private TableColumn<Employee, String> hourlyPaidColumn;

	@FXML
	private TableColumn<Employee, String> isActiveColumn;

	@FXML
	private TableColumn<Employee, String> isManagerColumn;

	@FXML
	private TableColumn<Employee, String> nameColumn;

	@FXML
	private TableColumn<Employee, String> nidColumn;

	@FXML
	private TableColumn<Employee, String> passwordColumn;

	@FXML
	private ImageView saveEmployee;

	@FXML
	private TextField nidTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private DatePicker dateOfWork;

	@FXML
	private TextField hourlyPaidTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private ListView<String> phoneList;

	@FXML
	private TextField phoneTextField;

	@FXML
	private ImageView addPhone;

	@FXML
	private ImageView deletePhone;

	@FXML
	private ImageView addEmployee;

	@FXML
	private CheckBox isManager;

	Employee employee;
	EmployeeController caller;

	public void phoneTextOnEnter(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			String phone = phoneTextField.getText().trim();
			if (phoneList.getItems().contains(phone.replaceAll("(-|\\s)", ""))) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Phone Number Already Exists");
				alert.showAndWait();
			} else if ((phone.replaceAll("(-|\\s)", "")).matches("[0-9]{10}")) {
				phoneList.getItems().add(phone.replaceAll("(-|\\s)", ""));
				phoneTextField.setText("");
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("Phone Number Must Contain 10 Digits");
				alert.showAndWait();
			}
		}
	}

	public void addPhoneOnMousePressed() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.8);
		addPhone.setEffect(effect);
		String phone = phoneTextField.getText().trim();
		if (phoneList.getItems().contains(phone.replaceAll("(-|\\s)", ""))) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Phone Number Already Exists");
			alert.showAndWait();
		} else if (phone.replaceAll("(-|\\s)", "").matches("[0-9]{10}")) {
			phoneList.getItems().add(phone.replaceAll("(-|\\s)", ""));
			phoneTextField.setText("");
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Wrong Input Format");
			alert.setHeaderText(null);
			alert.setContentText("Phone Number Must Contain 10 Digits");
			alert.showAndWait();
		}
	}

	public void addPhoneOnMouseReleased() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		addPhone.setEffect(effect);
	}

	public void addPhoneOnMouseEntered() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.4);
		addPhone.setEffect(effect);
	}

	public void addPhoneOnMouseExited() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		addPhone.setEffect(effect);
	}

	public void saveOnMousePressed() {
		if (employee != null) {
			ColorAdjust effect = new ColorAdjust();
			effect.setBrightness(0.8);
			saveEmployee.setEffect(effect);
			ArrayList<String> parameters = new ArrayList<>();
			parameters.add(employeeTable.getItems().get(0).getName());
			parameters.add(employeeTable.getItems().get(0).getNationalID());
			parameters.add(employeeTable.getItems().get(0).getHourlyPaid() + "");
			parameters.add(Employee.encryptPassword(employeeTable.getItems().get(0).getName(),
					employeeTable.getItems().get(0).getPassword()));
			parameters.add(employeeTable.getItems().get(0).getIsManager());
			parameters.add(employeeTable.getItems().get(0).getIsActive());
			parameters.add(employee.getID() + "");
			Queries.queryUpdate("update Employee set Employee_Name=?" + " , Employee_National_ID=? "
					+ " , Employee_Hourly_Paid=? " + " , Employee_password=? " + " , isManager=? " + " , isActive=? "
					+ " where Employee_ID=? ;", parameters);

			Employee.getEmployeeData();
			caller.saveEdits();
		}

	}

	public void saveOnMouseReleased() {
		if (employee != null) {
			ColorAdjust effect = new ColorAdjust();
			effect.setBrightness(0);
			saveEmployee.setEffect(effect);
		}
	}

	public void saveOnMouseEntered() {
		if (employee != null) {
			ColorAdjust effect = new ColorAdjust();
			effect.setBrightness(0.4);
			saveEmployee.setEffect(effect);
		}
	}

	public void saveOnMouseExited() {
		if (employee != null) {
			ColorAdjust effect = new ColorAdjust();
			effect.setBrightness(0);
			saveEmployee.setEffect(effect);
		}
	}

	public void addEmployeeOnMousePressed() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.8);
		addEmployee.setEffect(effect);
		Double hourlyPaid = 0.0;
		String NID = nidTextField.getText().trim();
		String name = nameTextField.getText().trim();
		String password = passwordTextField.getText().trim();
		String date = dateOfWork.getValue().toString().trim();
		Boolean checkHourlyPaid = true;
		try {
			hourlyPaid = Double.parseDouble(hourlyPaidTextField.getText().trim());
		} catch (NumberFormatException e) {
			checkHourlyPaid = false;
		}

		if ((Queries.queryResult("select * from Employee where Employee_National_ID=? ;",
				new ArrayList<>(Arrays.asList(NID)))).size() != 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Employee with This National ID Already Exists");
			alert.showAndWait();
		}

		else if (!checkHourlyPaid || hourlyPaid <= 0 || !NID.matches("[0-9]{9}") || !name.matches("[a-z[A-Z]\\s]+")
				|| passwordTextField.getText().isBlank() || passwordTextField.getText().isEmpty()
				|| !date.matches("[0-9]{4}-(0[1-9]|1[02])-(0[1-9]|[1-3][0-9])")) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Wrong Input Format");
			alert.setHeaderText("Wrong Input Format");
			alert.setContentText("National ID Must Be 9 Digits \n" + "Name Must Consist of Alphabetical Characters\n"
					+ "Hourly Paid Must Be A Positive Real Number \n" + "All Fields Must Be Filled");

			alert.showAndWait();
		} else if (phoneList.getItems().size() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing Info");
			alert.setHeaderText(null);
			alert.setContentText("Phone Number Must Be Added");
			alert.showAndWait();
		} else {
			Employee.insertEmployee(name, NID, dateOfWork.getValue(), hourlyPaid, new ArrayList<>(phoneList.getItems()),
					password, isManager.isSelected() ? "true" : "false", "true");

			caller.saveEdits();

			nidTextField.setText("");
			nameTextField.setText("");
			hourlyPaidTextField.setText("");
			passwordTextField.setText("");
			isManager.setSelected(false);
			dateOfWork.getEditor().clear();
			phoneTextField.setText("");
			phoneList.getItems().clear();
		}
	}

	public void addEmployeeOnMouseReleased() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		addEmployee.setEffect(effect);
	}

	public void addEmployeeOnMouseEntered() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.4);
		addEmployee.setEffect(effect);
	}

	public void addEmployeeOnMouseExited() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		addEmployee.setEffect(effect);
	}

	public void listOnEditCommit(ListView.EditEvent<String> editedPhone) {
		if (phoneList.getItems().contains(editedPhone.getNewValue().trim().replaceAll("(-|\\s)", ""))
				&& !editedPhone.getNewValue().trim().replaceAll("(-|\\s)", "")
						.equals(phoneList.getItems().get(editedPhone.getIndex()))) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Phone Number Already Exists");
			alert.showAndWait();
		} else if ((editedPhone.getNewValue().trim().replaceAll("(-|\\s)", "")).matches("[0-9]{10}")) {
			phoneList.getItems().set(editedPhone.getIndex(),
					editedPhone.getNewValue().trim().replaceAll("(-|\\s)", ""));
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Wrong Input Format");
			alert.setHeaderText(null);
			alert.setContentText("Phone Number Must Contain 10 Digits");
			alert.showAndWait();
		}
	}

	public void deletePhoneOnMousePressed() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.8);
		deletePhone.setEffect(effect);
		String phone = phoneList.getSelectionModel().getSelectedItem();
		phoneList.getItems().remove(phone);
	}

	public void deletePhoneOnMouseReleased() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		deletePhone.setEffect(effect);
	}

	public void deletePhoneOnMouseEntered() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.4);
		deletePhone.setEffect(effect);
	}

	public void deletePhoneOnMouseExited() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		deletePhone.setEffect(effect);
	}

	public void setRow(Employee employee, EmployeeController caller) {
		this.caller = caller;
		this.employee = employee;
		employeeTable.setItems(FXCollections.observableArrayList(employee));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("ID"));
		nidColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("NID"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		hourlyPaidColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("hourlyPaid"));
		isManagerColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("isManager"));
		isActiveColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("isActive"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
		nidColumn.setEditable(true);
		nameColumn.setEditable(true);
		hourlyPaidColumn.setEditable(true);
		isManagerColumn.setEditable(true);
		isActiveColumn.setEditable(true);
		passwordColumn.setEditable(true);
		employeeTable.setEditable(true);
		nidColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		nameColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		hourlyPaidColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());

		isManagerColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		isActiveColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		passwordColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		nidColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			if ((Queries.queryResult("select * from Customer where Employee_NID=? ;",
					new ArrayList<>(Arrays.asList(t.getNewValue().trim())))).size() != 0
					&& !t.getOldValue().trim().equals(t.getNewValue().trim())) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Employee with This National ID Already Exists");
				alert.showAndWait();
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setNationalID(t.getOldValue().trim());
			} else if (t.getNewValue().trim().matches("[0-9]{9}")) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setNationalID(t.getNewValue().trim());
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("National ID Must Be 9 Digits");
				alert.showAndWait();
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setNationalID(t.getOldValue().trim());
			}
			employeeTable.refresh();
		});

		nameColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			if (t.getNewValue().trim().matches("[a-z[A-Z]\\s]+")) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setName(t.getNewValue().trim());
			} else {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setName(t.getOldValue().trim());
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("Name Must Consist of Alphabetical Characters");
				alert.showAndWait();
			}
			employeeTable.refresh();
		});

		isManagerColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			if (t.getNewValue().trim().toLowerCase().equals("true")
					|| t.getNewValue().trim().toLowerCase().equals("false")) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setIsManager(t.getNewValue().trim());

			} else {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setIsManager(t.getOldValue().trim());
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("isManager Value Must Be True or False");
				alert.showAndWait();
			}
			employeeTable.refresh();
		});

		isActiveColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			if (t.getNewValue().trim().toLowerCase().equals("true") || t.getNewValue().toLowerCase().equals("false")) {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setIsActive(t.getNewValue().trim());

			} else {
				((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setIsActive(t.getOldValue().trim());

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("isActive Value Must Be True or False");
				alert.showAndWait();
			}
			employeeTable.refresh();
		});

		hourlyPaidColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			Double hourlyPaid = Double.parseDouble(t.getOldValue().trim());
			try {
				hourlyPaid = Double.parseDouble(t.getNewValue().trim());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Wrong Input Format");
				alert.setHeaderText(null);
				alert.setContentText("Hourly Paid Value Must Be A Numeric Value");
				alert.showAndWait();
				employeeTable.refresh();
			}
			((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHourlyPaid(hourlyPaid);
		});
		passwordColumn.setOnEditCommit((CellEditEvent<Employee, String> t) -> {
			((Employee) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPassword(t.getNewValue().trim());
		});
		employeeTable.refresh();
	}
}