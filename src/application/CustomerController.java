package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Relations.Customer;
import Relations.Queries;

/**
 * 
 * @version 30 January 2022
 * @author Aseel Sabri
 *
 */
public class CustomerController implements Initializable {

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private TableColumn<Customer, Double> debtColumn;

	@FXML
	private TableColumn<Customer, String> nidColumn;

	@FXML
	private TableColumn<Customer, String> nameColumn;

	@FXML
	private TextField searchBox;

	@FXML
	private ComboBox<String> fieldSelector;

	@FXML
	private Button showInfoButton;

	@FXML
	private ImageView editButton;

	@FXML
	private Button showProductsButton;

	@FXML
	private CheckBox haveDebt;

	private FXMLLoader editLoader;

	String stringToSearch = "";

	public void filterList() {
		String debtCondition = "";
		if (haveDebt.isSelected()) {
			debtCondition = " and Customer_Debt>0 ";
		}
		ArrayList<String> parameters = new ArrayList<>();
		parameters.add("%" + stringToSearch + "%");
		ArrayList<Customer> filteredList = new ArrayList<>();
		if (stringToSearch == null || stringToSearch.isEmpty() || stringToSearch.isBlank()) {
			filteredList = Customer.getCustomerData(Queries.queryResult(
					"select * from Customer where true " + debtCondition + " order by Customer_name;", null));
		} else if (fieldSelector.getSelectionModel().getSelectedItem() == "National ID") {
			filteredList = Customer.getCustomerData(Queries.queryResult("select * from Customer "
					+ " where Customer_NID like ? " + debtCondition + " order by Customer_name;", parameters));
		} else if (fieldSelector.getSelectionModel().getSelectedItem() == "Name") {
			filteredList = Customer.getCustomerData(Queries.queryResult("select * from Customer "
					+ " where Customer_Name like ? " + debtCondition + " order by Customer_name;", parameters));

		} else {
			parameters.add("%" + stringToSearch + "%");
			filteredList = Customer
					.getCustomerData(Queries.queryResult("select * from Customer where (Customer_Name like ? "
							+ " or Customer_NID like ? )" + debtCondition + " order by Customer_name;", parameters));
		}
		customerTable.setItems(FXCollections.observableArrayList(filteredList));
		customerTable.refresh();
	}

	public void showProductsOnAction() {
		if (customerTable.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Customer Must Be Chosen First");
			alert.setContentText("Choose A Customer From The Table");
			alert.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerProducts.fxml"));
			Parent products = (Parent) loader.load();
			CustomerProductsController show = loader.getController();
			show.setCustomer(customerTable.getSelectionModel().getSelectedItem());
			Stage productsStage = new Stage();
			productsStage.setAlwaysOnTop(true);
			Scene scene = new Scene(products);
			productsStage.setScene(scene);
			productsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showInfoOnAction() {
		if (customerTable.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Customer Must Be Chosen First");
			alert.setContentText("Choose A Customer From The Table");
			alert.showAndWait();
			return;
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerPhones.fxml"));
			Parent contactInfo = (Parent) loader.load();
			CustomerPhoneController show = loader.getController();
			show.setPhoneNumbers(customerTable.getSelectionModel().getSelectedItem());
			Stage contactInfoStage = new Stage();
			contactInfoStage.setAlwaysOnTop(true);
			Scene scene = new Scene(contactInfo, 500, 500);
			contactInfoStage.setScene(scene);
			contactInfoStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editOnMousePressed() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.8);
		editButton.setEffect(effect);
		try {
			editLoader = new FXMLLoader(getClass().getResource("CustomerEdit.fxml"));
			Parent editPane = (Parent) editLoader.load();
			CustomerEditController edit = editLoader.getController();
			edit.setRow(customerTable.getSelectionModel().getSelectedItem(), this);
			Scene scene = new Scene(editPane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage editStage = new Stage();
			editStage.initModality(Modality.APPLICATION_MODAL);
			editStage.setResizable(false);
			editStage.setScene(scene);
			editStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editOnMouseReleased() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		editButton.setEffect(effect);
	}

	public void editOnMouseEntered() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0.4);
		editButton.setEffect(effect);
	}

	public void editOnMouseExited() {
		ColorAdjust effect = new ColorAdjust();
		effect.setBrightness(0);
		editButton.setEffect(effect);
	}

	public void saveEdits() {
		filterList();
		customerTable.refresh();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		fieldSelector.setItems(FXCollections.observableArrayList("-Specify Field-", "National ID", "Name"));
		nidColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("nationalID"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
		debtColumn.setCellValueFactory(new PropertyValueFactory<Customer, Double>("debt"));
		filterList();

		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			stringToSearch = newValue.trim();
			filterList();
		});

	}
}