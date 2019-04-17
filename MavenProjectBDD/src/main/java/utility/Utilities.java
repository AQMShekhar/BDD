/*package utility;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class Utilities {
	public static void main(String[] args) {
		initializeDataBaseConnection();
		fireInsertQuery();
	}

	static Connection connection;

	public static void initializeDataBaseConnection() {
		Fillo fillo = new Fillo();
		try {
			if (connection == null)
				connection = fillo.getConnection("D:\\CucumberBDD\\Book1.xlsx");
			// connection.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}
	}

	public static void fireSelectQuery(String SheetName, String Argument, String argumentValue) {
		String strQuery = "select * from Sheet1 where UserName=Pathak";
		Recordset recordset;
		try {
			recordset = connection.executeQuery(strQuery);
			while (recordset.next()) {
				System.out.println(recordset.getField("Details"));
			}
			recordset.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}
	}

	public static void fireInsertQuery() {
		String strQuery = "INSERT INTO sheet4(Name,Country) VALUES('Peter','UK')";

		try {
			connection.executeUpdate(strQuery);
		} catch (FilloException e) {
			e.printStackTrace();
		}
	}
}
*/