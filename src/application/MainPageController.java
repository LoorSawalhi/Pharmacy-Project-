package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Relations.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @version 30 January 2022
 *
 */
public class MainPageController implements Initializable {
	
	static StackPane pane = null;

	@FXML
	private Label choosenButtonLabel;
 
	@FXML
	private Button employeesButton;

	@FXML
	private Button ordersButton;

	@FXML
	private Button paymentButton;

	@FXML
	private Button customersButton;

	@FXML
	private Button productsButton;

	@FXML
	private Button supplierButton;

	@FXML
	private StackPane contentArea;

	@FXML
	private Button dashboardButton;

	@FXML
	private Button logOutButton;

	@FXML
	private Button reportButton;

	@FXML
	private Button sellButton;

	@FXML
	private Button disposeButton;

	@FXML
	private Button payingOffButton;

	@FXML
	private Label userNameLabel;

	@FXML
	private VBox vbox;

	@FXML
	private AnchorPane rightPane;

	@FXML
	private BorderPane mainPane;

	public void PayingOffOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Paying Off");
		Region page = FXMLLoader.load(getClass().getResource("PayingOff.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void DisposeOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Dispose");
		Region page = FXMLLoader.load(getClass().getResource("Disposal.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void ReportOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Reports");

		if (Employee.hasAccess()) {
			Region page = FXMLLoader.load(getClass().getResource("Reports.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		} else {
			Region page = FXMLLoader.load(getClass().getResource("NotAvailable.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		}
	}

	public void logOutAction(ActionEvent e) throws IOException {
		Stage stage = (Stage) logOutButton.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
		Parent root1 = (Parent) loader.load();
		Stage stage2 = new Stage();
		stage2.setResizable(false);
		stage2.setScene(new Scene(root1));
		stage2.show();
	}

	public void SellOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("New Sell");

		Region page = FXMLLoader.load(getClass().getResource("Sell.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void CustomerOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Customers");

		Region page = FXMLLoader.load(getClass().getResource("Customer.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void DashboardOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Dashboard");

		Region page = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void ProductsOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Storage");

		Region page = FXMLLoader.load(getClass().getResource("Product.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
		contentArea.getChildren().setAll(page);
	}

	public void EmployeesOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Employees");
		if (Employee.hasAccess()) {
			Region page = FXMLLoader.load(getClass().getResource("Employee.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		} else {
			Region page = FXMLLoader.load(getClass().getResource("NotAvailable.fxml"));
			contentArea.getChildren().removeAll();
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		}

	}

	public void SupplierOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Suppliers");
		if (Employee.hasAccess()) {
			Region page = FXMLLoader.load(getClass().getResource("Supplier.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		} else {
			Region page = FXMLLoader.load(getClass().getResource("NotAvailable.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		}

	}

	public void PaymentOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Payment");
		if (Employee.hasAccess()) {
			Region page = FXMLLoader.load(getClass().getResource("Payment.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		} else {
			Region page = FXMLLoader.load(getClass().getResource("NotAvailable.fxml"));
			contentArea.getChildren().removeAll();
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		}

	}

	public void OrdersOnAction(ActionEvent e) throws IOException {
		choosenButtonLabel.setText("Orders");
		Region page = FXMLLoader.load(getClass().getResource("SupplierReceivedOrder.fxml"));
		contentArea.getChildren().removeAll();
		page.setMinHeight(0);
		page.setMinWidth(0);
		contentArea.getChildren().setAll(page);
		page.prefWidthProperty().bind(contentArea.widthProperty());
		page.prefHeightProperty().bind(contentArea.heightProperty());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		pane = contentArea;
		rightPane.prefHeightProperty().bind(mainPane.heightProperty());
		dashboardButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		paymentButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		sellButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		payingOffButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		ordersButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		customersButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		productsButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		employeesButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		supplierButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		disposeButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));
		reportButton.prefHeightProperty().bind(vbox.heightProperty().divide(12.5));

		if (Employee.getCurrentID() != 0) {
			userNameLabel.setText(Employee.getEmployeeName());
		} else {
			userNameLabel.setText("");
		}

		Region page;
		try {
			choosenButtonLabel.setText("Dashboard");
			page = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			page.setMinHeight(0);
			page.setMinWidth(0);
			page.prefWidthProperty().bind(contentArea.widthProperty());
			page.prefHeightProperty().bind(contentArea.heightProperty());
			contentArea.getChildren().setAll(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}