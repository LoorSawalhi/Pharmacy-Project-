package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Relations.Queries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * 
 * @version 30 January 2022
 * @author Loor Sawalhi
 *
 */
public class ChequesReportController implements Initializable {

	@FXML
	private TableView<ArrayList<String>> cheque;

	@FXML
	private TableColumn<ArrayList<String>, String> chequeDateColumn;

	@FXML
	private TableColumn<ArrayList<String>, String> chequeIDColumn;

	@FXML
	private TableColumn<ArrayList<String>, String> chequeNameColumn;

	@FXML
	private TableColumn<ArrayList<String>, String> chequeValueColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cheque.setItems(FXCollections.observableArrayList(
				Queries.queryResult("select c.cheque_ID,e.employee_name,c.due_date_of_cashing,p.payment_amount\r\n"
						+ "from employee e , cheque c, payment p\r\n"
						+ "where  c.manager_ID=e.employee_ID and c.payment_ID=p.payment_ID ;", null)));

		chequeIDColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ArrayList<String>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ArrayList<String>, String> p) {
						ArrayList<String> x = p.getValue();
						if (x != null && x.size() > 0) {
							return new SimpleStringProperty(x.get(0));
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		chequeNameColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ArrayList<String>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ArrayList<String>, String> p) {
						ArrayList<String> x = p.getValue();
						if (x != null && x.size() > 0) {
							return new SimpleStringProperty(x.get(1));
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		chequeDateColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ArrayList<String>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ArrayList<String>, String> p) {
						ArrayList<String> x = p.getValue();
						if (x != null && x.size() > 0) {
							return new SimpleStringProperty(x.get(2));
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		chequeValueColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ArrayList<String>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ArrayList<String>, String> p) {
						ArrayList<String> x = p.getValue();
						if (x != null && x.size() > 0) {
							return new SimpleStringProperty(x.get(3));
						} else {
							return new SimpleStringProperty("");
						}
					}
				});
	}
}
