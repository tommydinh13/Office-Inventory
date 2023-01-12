/**
 * @author Tommy Dinh <a href="mailto:tommy.dinh@ucalgary.ca">
 *         tommy.dinh@ucalgary.ca</a>
 *
 *   @author Tien Dat Do <a href="mailto:tiendat.do@ucalgary.ca">
 *         tiendat.do@ucalgary.ca</a>
 *   @author Dominic Li <a href="mailto:dominic.li1@ucalgary.ca">
 *         dominic.li1@ucalgary.ca</a>
 *   @author Mohammed Atifkhan Pathan <a
 * href="mailto:mohammedatifkhan.pat@ucalgary.ca">
 *         mohammedatifkhan.pat@ucalgary.ca</a>
 * @version 1.7
 * @since 1.0
 */

package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.ArrayList;

public class SupplyManager {

  private String request;
  private String category;
  private String type;
  private int number;
  private String successDelete;

  private int arraySize;

  private Connection dbConnection;
  private ResultSet results;

  public SupplyManager(String request) throws InvalidInputException {
    this.request = request;
    parseRequest();
    initializeConnection();
  }

  // default constructor to test the methods in Junit
  public SupplyManager() throws InvalidInputException {}

  public String getDeleteCondition() { return this.successDelete; }

  public void setType(String type) { this.type = type; }

  public void setCategory(String category) { this.category = category; }

  public void setRequest(String request) { this.request = request; }

  public void setNumber(int num) { this.number = num; }

  public void parseRequest() throws InvalidInputException {
    // initialzing variables
    String category = "";
    String type = "";
    int number = 0;

    // create array that containts elements split from number and the rest
    String[] atComma = this.request.split(",");

    // if there are not at least 2 elements, user input was incorrect
    if (atComma.length > 2) {
      throw new InvalidInputException(
          "too many commas, only 1 can be used to separate type and amount" +
          getCategoryAndType());
    }
    // check to see if the input either has invalid comma placement
    // or has no amount associated with the request
    else if (atComma.length < 2) {
      throw new InvalidInputException(
          "need to enter category, type and amount; seperate the type and amount with a comma: [Catergory] [Type], [Amount]" +
          getCategoryAndType());
    }

    // checking to see if number is an integer
    try {
      number = Integer.parseInt(atComma[1].trim());
    } catch (NumberFormatException nfe) {
      throw new InvalidInputException(
          "amount must be a number and should be entered after type: [Category] [Type], [Amount]");
    }

    if (number <= 0) {
      throw new InvalidInputException(
          "the amount must be a positive non-zero integer");
    }

    // creating new array that has elements split at whitespaces
    String[] atSpaces = atComma[0].trim().split("\\s{1,}");

    // check to see if 1 category and 1 type exist
    if (atSpaces.length < 2) {
      throw new InvalidInputException("need a category and/or type" +
                                      getCategoryAndType());

      // check to see if category is 1 word
    } else if (atSpaces.length == 2) {
      type = capitalize(atSpaces[0]);

      // check to see if category is 2 words
    } else if (atSpaces.length == 3) {
      type = capitalize(atSpaces[0]) + " " + capitalize(atSpaces[1]);
    } else {
      throw new InvalidInputException(
          atComma[0] +
          "input is too long, does not contain a furniture category and/or a type" +
          getCategoryAndType());
    }

    // type is the last index in the array
    category = atSpaces[atSpaces.length - 1].toUpperCase();

    // setting the user inputs into the private variables of this class
    this.category = category;
    this.type = type;
    this.number = number;
  }

  // https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
  public String capitalize(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  // Connect to database (reffered to lab 9)
  public void initializeConnection() {
    try {
      this.dbConnection = DriverManager.getConnection(
          "jdbc:mysql://localhost/inventory", "scm", "ensf409");
    } catch (SQLException sqle) {
      sqle.printStackTrace();
      System.exit(-1);
    }
  }

  // closing result and connection
  public void close() {
    try {
      if (results != null) {
        results.close();
      }

      if (dbConnection != null) {
        dbConnection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Checks if all values are greater than or equal to this.number in an array
  public boolean canBuild(int[] combined) {

    // loop that goes through array to make sure that
    // there are enough components to build the amount user requested
    for (int i = 0; i < combined.length; i++) {
      if (combined[i] < this.number) {
        return false;
      }
    }

    return true;
  }

  // Takes a list of objs and does a sum of the vals for each index
  //  Example: [1, 0, 0, 1] + [0, 1, 1, 1] => [1, 1, 1, 2]
  public int[] combine(ArrayList<InventoryObject> objs) {

    // create an array that will hold the amount of parts available with
    // combination
    int[] output = new int[arraySize];

    // nested loop that gets the values from each ArrayList index and adds it to
    // its respected index in the output
    for (int i = 0; i < objs.size(); i++) {
      for (int k = 0; k < arraySize; k++) {
        output[k] += objs.get(i).getVals()[k];
      }
    }

    return output;
  }

  // Objs is list from database, num is between 1 - 2^n, used as binary to
  // select which obj from objs is included in this combination
  public ArrayList<InventoryObject>
  getCombination(ArrayList<InventoryObject> objs, int num) {

    ArrayList<InventoryObject> output = new ArrayList<InventoryObject>();

    // loop that goes through ArrayList
    for (int i = 0; i < objs.size(); i++) {
      if ((num & 1) == 1) {
        output.add(objs.get(i));
      }
      num = num >> 1;
    }

    return output;
  }

  public String getCategoryAndType() {
    StringBuilder result = new StringBuilder(
        "\nThis is a list of possible categories and their types:\n");

    String[] chair = {"CHAIR", "Kneeling",  "Task",
                      "Mesh",  "Executive", "Ergonomic"};

    String[] desk = {"DESK", "Standing", "Adjustable", "Traditional"};

    String[] lamp = {"LAMP", "Desk", "Study", "Swing Arm"};

    String[] filing = {"FILING", "Small", "Medium", "Large"};

    String chairOutput = "";
    String deskOutput = "";
    String lampOutput = "";
    String filingOutput = "";

    // loop that runs through chair array and formats the elements inside of the
    // array
    for (int i = 0; i < chair.length; i++) {

      // if first element, add a colon
      if (i == 0) {
        chairOutput += chair[i] + ": ";

        // if last element, add a period
      } else if (i == chair.length - 1) {
        chairOutput += chair[i] + ".\n";

        // else case, just add a comma after appending
      } else {
        chairOutput += chair[i] + ", ";
      }
    }

    // loop that runs through desk array and formats the elements inside the
    // array
    for (int i = 0; i < desk.length; i++) {

      // if first element, add a colon
      if (i == 0) {
        deskOutput += desk[i] + ": ";

        // if last element, add a period
      } else if (i == desk.length - 1) {
        deskOutput += desk[i] + ".\n";

        // else case, just add a comma after appending
      } else {
        deskOutput += desk[i] + ", ";
      }
    }
    // loop that runs through lamp array and formats the elements inside the
    // array

    for (int i = 0; i < lamp.length; i++) {

      // if first element, add a colon
      if (i == 0) {
        lampOutput += lamp[i] + ": ";

        // if last element, add a period
      } else if (i == lamp.length - 1) {
        lampOutput += lamp[i] + ".\n";

        // else case, just add a comma after appending
      } else {
        lampOutput += lamp[i] + ", ";
      }
    }
    // loop that runs through filing array and formats the elements inside of
    // the array
    for (int i = 0; i < filing.length; i++) {

      // if first element, add a colon
      if (i == 0) {
        filingOutput += filing[i] + ": ";

        // if last element, add a period
      } else if (i == filing.length - 1) {
        filingOutput += filing[i] + ".\n";

        // else case, just add a comma after appending
      } else {
        filingOutput += filing[i] + ", ";
      }
    }

    // appending all of the categories formatted with their types
    result.append(chairOutput);
    result.append(deskOutput);
    result.append(lampOutput);
    result.append(filingOutput);

    return result.toString();
  }

  // chair : Office Furnishings, Chairs R Us, Furniture Goods, Fine Office
  // Supplies
  //  desk: Academic Desks, Office Furnishings, Furniture Goods, Fine
  // Office Supplies lamp: Office Furnishings, Funirture Goods, Fine Office
  // Supplies filing: Office Furnishings, Funirture Goods, Fine Office Supplies
  public String getManufacturers(String category) {

    if (category.equals("CHAIR")) {
      return "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, and Fine Office Supplies.";
    } else if (category.equals("DESK")) {
      return "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Academic Desks, Office Furnishings, Furniture Goods, and Fine Office Supplies.";
    } else {
      return "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    }
  }

  public void deleteItems(ArrayList<InventoryObject> objs) {
    for (int i = 0; i < objs.size(); i++) {
      try {
        // Delete item from table
        // accesses deleting item through id in the SQL file
        String query = String.format("DELETE FROM %s WHERE ID = '%s'",
                                     this.category, objs.get(i).getId());
        Statement myStmt = dbConnection.createStatement();
        myStmt.executeUpdate(query);
        // successfully deleted the row(s)
        this.successDelete = "successful";
        myStmt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  public OrderOutput findCheapest() throws InvalidInputException {
    OrderOutput result = new OrderOutput(false, "");

    // access database
    Statement stmt = null;
    try {

      // query that searches for what the user requested
      String query = String.format("SELECT * FROM %s WHERE Type = '%s'",
                                   this.category, this.type);

      // create statement
      stmt = dbConnection.createStatement();

      // execute/search for statement
      results = stmt.executeQuery(query);

      // MetaData used to find the number of components used for the
      // desired funiture category
      ResultSetMetaData md = results.getMetaData();

      // this will be used to determine the array size in combine method
      arraySize = md.getColumnCount() - 4;

      ArrayList<InventoryObject> objs = new ArrayList<InventoryObject>();

      // loop that goes through table
      while (results.next()) {
        // gets ID
        String id = results.getString("ID");

        // gets type
        String type = results.getString("Type");

        // gets price
        int price = results.getInt("Price");

        // gets ManuID
        String manufacturer = results.getString("ManuID");

        // char array that holds the Y and N values of each component
        char[] vals = new char[md.getColumnCount() - 4];
        int idx = 0;
        for (int i = 3; i <= md.getColumnCount() - 2; i++) {
          vals[idx] = results.getString(i).charAt(0);
          idx++;
        }

        // new inventory object that will store the values from the database
        InventoryObject obj =
            new InventoryObject(id, type, price, manufacturer, vals);

        // add this piece of furniture to objs
        objs.add(obj);
      }

      ArrayList<ArrayList<InventoryObject>> accepted =
          new ArrayList<ArrayList<InventoryObject>>();

      // LOOP START
      // Go through all combinations
      for (int i = 1; i < (int)Math.pow(2, objs.size()); i++) {
        // Combine for a combination and check if it would build the requested
        // number of objects
        ArrayList<InventoryObject> combination = getCombination(objs, i);

        int[] combined = combine(combination);

        // Add to accepted list if it can be built
        if (canBuild(combined)) {
          accepted.add(combination);
        }
      }

      if (accepted.size() > 0) {
        // Find cheapest combination
        int cheapest = Integer.MAX_VALUE;
        int cheapestIDX = 0;

        for (int i = 0; i < accepted.size(); i++) {
          int cost = 0;

          for (int j = 0; j < accepted.get(i).size(); j++) {
            InventoryObject obj = accepted.get(i).get(j);
            cost += obj.getPrice();
          }

          if (cost < cheapest) {
            cheapest = cost;
            cheapestIDX = i;
          }
        }

        ArrayList<InventoryObject> cheapestObjs = accepted.get(cheapestIDX);

        // Get IDs of cheapest
        String[] items = new String[cheapestObjs.size()];
        for (int i = 0; i < cheapestObjs.size(); i++) {
          items[i] = cheapestObjs.get(i).getId();
        }

        result = new OrderOutput(this.request, items, cheapest);

        // Remove items
        deleteItems(cheapestObjs);
      } else {
        // Return failure if accepted list is empty
        String manufacturers = getManufacturers(this.category);

        result = new OrderOutput(false, manufacturers);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      this.close();
      throw new InvalidInputException("type is invalid.");
    } finally {
      try {
        if (stmt != null) {
          stmt.close();
        }
      } catch (SQLException e) {
      }
    }

    this.close();
    return result;
  }
}