import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Scanner;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class employeeUI {
	public static String schema = "test_vraja";
	public static String user = "";
	public static String password = "";

	private static Connection loadDriver() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_123", user, password);
	}

	private static int getUserIDfromEmail(String email) throws SQLException, ClassNotFoundException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT userid FROM test_vraja.platformuser WHERE email=" + "'" + email + "'");
		rs.next();
		int result = rs.getInt(1);
		rs.close();
		st.close();
		db.close();
		return result;
	}

	private static Boolean checkPass(int userid, String pass) throws SQLException, ClassNotFoundException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT pword FROM test_vraja.platformuser WHERE userid=" + "'" + userid + "'");
		rs.next();
		String correctpass = rs.getString(1);
		if (!(correctpass.equals(pass))) {
			return false;
		}
		rs.close();
		st.close();
		db.close();
		return true;

	}

	private static boolean checkAccount(String email) throws SQLException, ClassNotFoundException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM test_vraja.platformuser WHERE email=" + "'" + email + "'");
		ResultSetMetaData check = rs.getMetaData();
		rs.next();
		if (rs.getString(1).equals("1")) {
			rs.close();
			st.close();
			db.close();
			return true;
		}
		rs.close();
		st.close();
		db.close();
		return false;
	}

	private static void login() throws SQLException, ClassNotFoundException {
		Scanner in = new Scanner(System.in);
		Boolean active = true;
		while (active) {
			System.out.print("Enter your email: ");
			String email = in.nextLine();
			if (!checkAccount(email)) {
				System.out.println("There is no account associated with that email.");
			} else {
				System.out.print("Enter your password: ");
				String pass = in.nextLine();
				if (!checkPass(getUserIDfromEmail(email), pass)) {
					System.out.println("Invalid Password. Try Again");
				} else {
					System.out.println("Successful Login");
					System.out.println("------------------------------------------------------------------");
				}
			}
		}
	}

	private static void getUsersID() throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT userid FROM test_vraja.Platformuser");
		while (rs.next()) {
			System.out.println("_______________________________________________________");
			System.out.println("UserID: " + rs.getInt(1));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getPropertiesID() throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT propertyid FROM test_vraja.property");
		while (rs.next()) {
			System.out.println("_______________________________________________________");
			System.out.println("PropertyID: " + rs.getInt(1));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getBranchesID() throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT branchid FROM test_vraja.branch");
		while (rs.next()) {
			System.out.println("_______________________________________________________");
			System.out.println("BranchID: " + rs.getString(1));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getAmenitiesID() throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT amenityid FROM test_vraja.amenity");
		while (rs.next()) {
			System.out.println("_______________________________________________________");
			System.out.println("AmenityID: " + rs.getInt(1));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getCountriesID() throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT countryid FROM test_vraja.country");
		while (rs.next()) {
			System.out.println("_______________________________________________________");
			System.out.println("CountryID: " + rs.getString(1));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getUsers(int userid) throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery(
				"SELECT pword,firstname,lastname,dob,addressnum,addressstreet,addresscity,addresspostal,countryid,email,phone,about,usertype,created,status FROM test_vraja.Platformuser WHERE userid="
						+ "'" + userid + "'");
		while (rs.next()) {
			System.out.println("The user's password is " + rs.getString(1));
			System.out.println("The user's firstname is " + rs.getString(2));
			System.out.println("The user's lastname is " + rs.getString(3));
			System.out.println("The user's date of birth is " + rs.getDate(4));
			System.out.println("The user's street number is " + rs.getString(5));
			System.out.println("The user's street name is " + rs.getString(6));
			System.out.println("The user's city name is " + rs.getString(7));
			System.out.println("The user's postal code is " + rs.getString(8));
			System.out.println("The user's country id is " + rs.getString(9));
			System.out.println("The user's email address is " + rs.getString(10));
			System.out.println("The user's phone number is " + rs.getString(11));
			System.out.println("Something about the user: " + rs.getString(12));
			System.out.println("The user's type is " + rs.getString(13));
			System.out.println("The created date is " + rs.getDate(14));
			System.out.println("Status: " + rs.getString(15));
		}
		rs.close();
		st.close();
		db.close();

	}

	private static void getProperties(int propertyid) throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery(
				"SELECT userid,branchid,addressnum,addressstreet,addresscity,addresspostal,rooms,bathrooms,created,status FROM test_vraja.property WHERE propertyid="
						+ "'" + propertyid + "'");
		while (rs.next()) {
			System.out.println("The owner's id is: " + rs.getInt(1));
			System.out.println("The branch id of the property is: " + rs.getString(2));
			System.out.println("The street number of the property is:  " + rs.getInt(3));
			System.out.println("The street name of the property is:  " + rs.getString(4));
			System.out.println("The city of the property is: " + rs.getString(5));
			System.out.println("The postal code of the property is: " + rs.getString(6));
			System.out.println("The property has " + rs.getInt(7) + " rooms.");
			System.out.println("The property has " + rs.getInt(8) + " bathrooms.");
			System.out.println("The created date is " + rs.getDate(9));
			System.out.println("Status: " + rs.getString(10));
		}
		rs.close();
		st.close();
		db.close();
	}

	private static void getBranches(int branchid) throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT countryid,created FROM test_vraja.branch WHERE branchid=" + "'" + branchid + "'");
		while (rs.next()) {
			System.out.println("The countryid of the branch is " + rs.getString(1));
			System.out.println("The created date of the branch is " + rs.getDate(2));
		}
		rs.close();
		st.close();
		db.close();

	}

	private static void getAmenities(int amenityid) throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st.executeQuery("SELECT type,name,created,status FROM test_vraja.amenity WHERE amenityid=" + "'" + amenityid + "'");
		while (rs.next()) {
			System.out.println("The type of the amenity is " + rs.getString(1));
			System.out.println("The name of the amenity is " + rs.getString(2));
			System.out.println("The created date of the amenity is " + rs.getDate(3));
			System.out.println("The status of the amenity: " + rs.getString(4));
		}
		rs.close();
		st.close();
		db.close();

	}

	private static void getCountries(int countryid) throws SQLException, ClassNotFoundException, ParseException {
		Connection db = loadDriver();
		Statement st = db.createStatement();
		ResultSet rs = st
				.executeQuery("SELECT countryname FROM test_vraja.country WHERE countryid=" + "'" + countryid + "'");
		while (rs.next()) {
			System.out.println("The name of the country is " + rs.getString(1));
		}
		rs.close();
		st.close();
		db.close();

	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
		Scanner x = new Scanner(System.in);
		System.out.println("------------------------------HOTEL 2.0------------------------------");
		boolean on = true;
		while (on == true) {
			login();
			System.out.println("1.Users");
			System.out.println("2.Properties");
			System.out.println("3.Branches");
			System.out.println("4.Amenities");
			System.out.println("5.Cities");
			System.out.println("What information do you want to see? (1/2/3/4/5): ");
			String number = x.nextLine();
			if (number.equals("1")) {
				getUsersID();
			} else if (number.equals("2")) {
				getPropertiesID();
			} else if (number.equals("3")) {
				getBranchesID();
			} else if (number.equals("4")) {
				getAmenitiesID();
			} else {
				getCountriesID();
			}
			System.out.println("Enter the specific ID to see more information: ");
			String id = x.nextLine();
			int i = Integer.parseInt(id);
			if (number.equals("1")) {
				getUsers(i);
			} else if (number.equals("2")) {
				getProperties(i);
			} else if (number.equals("3")) {
				getBranches(i);
			} else if (number.equals("4")) {
				getAmenities(i);
			} else {
				getCountries(i);
			}
			System.out.println("Do you want to start another search?(Y/N)");
			String answer = x.nextLine();
			if (answer.equals("N") || answer.equals("n")) {
				on = false;
			}
		}
	}
}
