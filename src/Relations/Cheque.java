package Relations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Cheque class represents all Cheques' operations
 * 
 * @version 26 January 2022
 * @author Loor Sawalhi
 *
 */
public class Cheque {
	private static ArrayList<Cheque> data = new ArrayList<Cheque>();
	private static ObservableList<Cheque> dataList;
	private String ID;
	private String name;
	private LocalDate dateOFWriting;
	private LocalDate dateOfCashing;
	private int paymentID;
	private int managerID;

	/**
	 * Allocates a {@code Cheque} object and initializes it to represent the
	 * specified parameters.
	 * 
	 * @param iD            The ID of The cheque.
	 * @param name          The name of the bank of the Cheque.
	 * @param dateOFWriting The writing date of the Cheque.
	 * @param dateOfCashing The cashing date of the Cheque.
	 * @param paymentID     The ID of the Payment operation.
	 * @param managerID     The ID of the manager wrote the Cheque.
	 */
	public Cheque(String iD, String name, LocalDate dateOFWriting, LocalDate dateOfCashing, int paymentID,
			int managerID) {
		super();
		ID = iD;
		this.name = name;
		this.dateOFWriting = dateOFWriting;
		this.dateOfCashing = dateOfCashing;
		this.paymentID = paymentID;
		this.managerID = managerID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getBankName() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public void setBankName(String name) {
		setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOFWriting() {
		return dateOFWriting;
	}

	public void setDateOFWriting(LocalDate dateOFWriting) {
		this.dateOFWriting = dateOFWriting;
	}

	public LocalDate getDateOfCashing() {
		return dateOfCashing;
	}

	public void setDateOfCashing(LocalDate dateOfCashing) {
		this.dateOfCashing = dateOfCashing;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public static ArrayList<Cheque> getData() {
		return Cheque.data;
	}

	public static ObservableList<Cheque> getDataList() {
		return Cheque.dataList;
	}

	public static void setDataList(ObservableList<Cheque> dataList) {
		Cheque.dataList = dataList;
	}

	/**
	 * Read from data base and fill the ArrayList
	 * 
	 */
	public static void getChequeData() {

		data.clear();
		ArrayList<ArrayList<String>> table = Queries.queryResult("select * from Cheque;", null);

		for (int i = 0; i < table.size(); i++) {

			LocalDate writingDate = LocalDate.parse(table.get(i).get(2));
			LocalDate cashingDate = LocalDate.parse(table.get(i).get(3));
			Cheque temp = new Cheque(table.get(i).get(0), table.get(i).get(1), writingDate, cashingDate,
					Integer.parseInt(table.get(i).get(4)), Integer.parseInt(table.get(i).get(5)));

			data.add(temp);
		}
		dataList = FXCollections.observableArrayList(data);
	}

	/**
	 * Fill the an ArrayList from specific ArrayList<ArrayList<String>> entry
	 * 
	 * @param table ArrayList<ArrayList<String>> to fill data with
	 * @return ArrayList<Cheque> of data
	 */
	public static ArrayList<Cheque> getChequeData(ArrayList<ArrayList<String>> table) {
		ArrayList<Cheque> tempData = new ArrayList<Cheque>();

		for (int i = 0; i < table.size(); i++) {
			LocalDate writingDate = LocalDate.parse(table.get(i).get(1));
			LocalDate cashingDate = LocalDate.parse(table.get(i).get(2));
			Cheque temp = new Cheque(table.get(i).get(0), table.get(i).get(1), writingDate, cashingDate,
					Integer.parseInt(table.get(i).get(3)), Integer.parseInt(table.get(i).get(4)));

			tempData.add(temp);
		}
		return tempData;

	}

	public static void insertCheque(String chequeID, String name, LocalDate dateOFWriting, LocalDate dateOfCashing,
			int paymentID, int managerID) {
		Queries.queryUpdate("Insert into Cheque values (?, ?, ? ,? ,?, ?);", new ArrayList<>(Arrays.asList(chequeID,
				name, dateOFWriting.toString(), dateOfCashing.toString(), paymentID + "", managerID + "")));
	}

	/**
	 * Report Cheques informations on csv file
	 * 
	 * @param path The path of file
	 */
	public static void report(String path) {
		Queries.reportQuery(
				"select c.cheque_ID as 'Cheque ID',e.employee_name as 'Employee Name',c.due_date_of_cashing as 'Due date of cashing',p.payment_amount as 'Payment Amount'\r\n"
						+ "from employee e , cheque c, payment p\r\n"
						+ "where  c.manager_ID=e.employee_ID and c.payment_ID=p.payment_ID ;",
				path);
	}
}
