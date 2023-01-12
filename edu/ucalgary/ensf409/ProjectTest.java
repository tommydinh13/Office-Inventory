/**
 * @author Tommy Dinh <a href="mailto:tommy.dinh@ucalgary.ca">
 *         tommy.dinh@ucalgary.ca</a>
 *   @author Tien Dat Do <a href="mailto:tiendat.do@ucalgary.ca">
 *         tiendat.do@ucalgary.ca</a>
 *   @author Dominic Li <a href="mailto:dominic.li1@ucalgary.ca">
 *         dominic.li1@ucalgary.ca</a>
 *   @author Mohammed Atifkhan Pathan <a
 * href="mailto:mohammedatifkhan.pat@ucalgary.ca">
 *         mohammedatifkhan.pat@ucalgary.ca</a>
 * @version 1.9
 * @since 1.0
 */

package edu.ucalgary.ensf409;

/*
 * TO RUN THE TEST, RECITE TO README TEXT FILE
 * IN THE WORKING DIRECTORY IN THE
 * COMMAND LINE
 *
 * Dont forget to also import the inventory using the mySQL Command Line Client!
 * Since the database changes after the test, re import the inventory.sql before
 * re running the test!!
 *
 * ALSO, CHANGE THE USER AND PASSWORD IN createConnection() FOR YOUR SYSTEM
 * AND ALSO CHANGE IT IN THE CODE
 *
 * Furthermore, These tests will only run properly on the given database since
 * some values had to be hardcoded for the tests to work
 */

/* In order to check that our code passes and fufills the given requirements,
   we will use junit tests by creating SupplyManager and OrderOutput objects to
   test that they have the expected functionality from a wide range of different
   inputs. We will simulate the same process as shown in the main function in
   the SupplyManagement class to do the testing.
*/

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.String;
import java.sql.*;
import org.junit.*;


public class ProjectTest {
  private Connection dbConnect;
  // private ResultSet results;

  public ProjectTest() {}

  // test a valid input for chair (add two new rows in chair)
  @Test
  public void testValidInputChair1() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInChair("C1234", "mesh", "Y", "N", "Y", "N", 40, "003");
    insertRowInChair("C5678", "mesh", "N", "Y", "N", "Y", 30, "003");
    int expectedCost = 70;
    String[] expectedIDs = new String[] {"C1234", "C5678"};
    SupplyManager supply = new SupplyManager("mesh chair, 1");
    OrderOutput output = supply.findCheapest();
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }

    assertTrue(flag);
  }

  // test valid input but with extra spaces in between
  @Test
  public void testValidInputChairWithTooManySpacesInBetween()
      throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInChair("C8888", "mesh", "N", "N", "Y", "N", 10, "003");
    insertRowInChair("C6666", "mesh", "Y", "Y", "N", "Y", 10, "003");
    int expectedCost = 20;
    String[] expectedIDs = new String[] {"C8888", "C6666"};
    SupplyManager supply = new SupplyManager("  mesh      chair , 1  ");
    OrderOutput output = supply.findCheapest();
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }

    assertTrue(flag);
  }

  // test a valid input for desk (add two new rows in desk)
  @Test
  public void testValidInputDesk1() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInDesk("D1234", "Standing", "Y", "N", "N", 20, "001");
    insertRowInDesk("D5678", "Standing", "N", "Y", "Y", 30, "001");
    SupplyManager supply = new SupplyManager("standing desk, 1");
    OrderOutput output = supply.findCheapest();
    int expectedCost = 50;
    String[] expectedIDs = new String[] {"D1234", "D5678"};
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input for lamp (add two new rows in lmap)
  @Test
  public void testValidInputLamp1() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInLamp("L000", "swing arm", "Y", "N", 2, "002");
    insertRowInLamp("L111", "swing arm", "N", "Y", 1, "003");
    SupplyManager supply = new SupplyManager("swing arm lamp, 1");
    OrderOutput output = supply.findCheapest();
    int expectedCost = 3;
    String[] expectedIDs = new String[] {"L000", "L111"};
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input for filing (add two new rows in filing)
  @Test
  public void testValidInputFiling1() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInFiling("F589", "small", "Y", "Y", "N", 40, "005");
    insertRowInFiling("F403", "small", "N", "N", "Y", 40, "004");
    SupplyManager supply = new SupplyManager("small filing, 1");
    OrderOutput output = supply.findCheapest();
    int expectedCost = 80;
    String[] expectedIDs = new String[] {"F589", "F403"};
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input for chair but with no supply as seen in the project
  // document
  @Test
  public void testValidInputChair2() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("executive chair, 7");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible(); // this should set flag to true
    if (!expectedOutput.equals(
            output.getOutput())) // set flag to false if the output to terminal
                                 // does not match the expected output
    {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input for desk that has no supply
  @Test
  public void testValidInputDesk2() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Academic Desks, Office Furnishings, Furniture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("standing desk, 9");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible(); // this should set flag to true
    if (!expectedOutput.equals(
            output.getOutput())) // set flag to false if the output to terminal
                                 // does not match the expected output
    {
      flag = false;
    }
    assertTrue(flag);
  }

  @Test // test a valid input for a lamp but does not have enough supply
  public void testValidInputLamp2() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("desk lamp, 8");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible(); // this should set flag to true
    if (!expectedOutput.equals(
            output.getOutput())) // set flag to false if the output to terminal
                                 // does not match the expected output
    {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input for filing but not enough supply to fufil
  @Test
  public void testValidInputFiling2() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("medium filing, 9");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible(); // this should set flag to true
    if (!expectedOutput.equals(
            output.getOutput())) // set flag to false if the output to terminal
                                 // does not match the expected output
    {
      flag = false;
    }
    assertTrue(flag);
  }

  // test a valid input with three chairs that can be combined to one
  @Test
  public void testValidInputChair3() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInChair("C1212", "task", "Y", "N", "Y", "N", 3, "003");
    insertRowInChair("C2121", "task", "N", "Y", "N", "N", 2, "003");
    insertRowInChair("C7878", "task", "N", "N", "N", "Y", 2, "003");
    int expectedCost = 7;
    String[] expectedIDs = new String[] {"C1212", "C2121", "C7878"};
    SupplyManager supply = new SupplyManager("task chair, 1");
    OrderOutput output = supply.findCheapest();
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1]) &&
        !actualIDs[0].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1]) &&
        !actualIDs[1].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[2].equals(expectedIDs[0]) &&
        !actualIDs[2].equals(expectedIDs[1]) &&
        !actualIDs[2].equals(expectedIDs[2])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }

    assertTrue(flag);
  }

  // test valid input with 3 desks that can be used to create one
  @Test
  public void testValidInputDesk3() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInDesk("D1212", "standing", "N", "N", "Y", 1, "002");
    insertRowInDesk("D2121", "standing", "N", "Y", "N", 1, "004");
    insertRowInDesk("D7878", "standing", "Y", "N", "N", 1, "004");
    int expectedCost = 3;
    String[] expectedIDs = new String[] {"D1212", "D2121", "D7878"};
    SupplyManager supply = new SupplyManager("standing desk, 1");
    OrderOutput output = supply.findCheapest();
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1]) &&
        !actualIDs[0].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1]) &&
        !actualIDs[1].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[2].equals(expectedIDs[0]) &&
        !actualIDs[2].equals(expectedIDs[1]) &&
        !actualIDs[2].equals(expectedIDs[2])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }

    assertTrue(flag);
  }

  // test a valid input with three filings that can be used to create one
  @Test
  public void testValidInputFiling3() throws InvalidInputException {
    boolean flag = true;
    createConnection();
    insertRowInFiling("F123", "small", "Y", "N", "N", 10, "005");
    insertRowInFiling("F567", "small", "N", "Y", "N", 13, "002");
    insertRowInFiling("F789", "small", "N", "N", "Y", 12, "004");
    int expectedCost = 35;
    String[] expectedIDs = new String[] {"F123", "F567", "F789"};
    SupplyManager supply = new SupplyManager("small filing, 1");
    OrderOutput output = supply.findCheapest();
    int actualCost = output.getCost();
    String[] actualIDs = output.getItems();
    if (!actualIDs[0].equals(expectedIDs[0]) &&
        !actualIDs[0].equals(expectedIDs[1]) &&
        !actualIDs[0].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[1].equals(expectedIDs[0]) &&
        !actualIDs[1].equals(expectedIDs[1]) &&
        !actualIDs[1].equals(expectedIDs[2])) {
      flag = false;
    }
    if (!actualIDs[2].equals(expectedIDs[0]) &&
        !actualIDs[2].equals(expectedIDs[1]) &&
        !actualIDs[2].equals(expectedIDs[2])) {
      flag = false;
    }
    if (expectedCost != actualCost) {
      flag = false;
    }

    assertTrue(flag);
  }

  // type of chair is not available in the chair database, should print the
  // manufacturers
  @Test
  public void testInvalidChairType() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("flat chair, 1");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible();
    if (!expectedOutput.equals(output.getOutput())) {
      flag = false;
    }
    assertTrue(flag);
  }

  // type of desk is not available in the desk database, should print the
  // manufacturers
  @Test
  public void testInvalidDeskType() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Academic Desks, Office Furnishings, Furniture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("blue desk, 1");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible();
    if (!expectedOutput.equals(output.getOutput())) {
      flag = false;
    }
    assertTrue(flag);
  }

  // type of lamp is not available in the lamp database, should print the
  // manufacturers
  @Test
  public void testInvalidLampType() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("car lamp, 8");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible();
    if (!expectedOutput.equals(output.getOutput())) {
      flag = false;
    }
    assertTrue(flag);
  }

  // type of filing is not available in the filing database, should print the
  // manufacturers
  @Test
  public void testInvalidFilingType() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    SupplyManager supply = new SupplyManager("bigger filing, 2");
    OrderOutput output = supply.findCheapest();
    boolean flag = !output.isPossible();
    if (!expectedOutput.equals(output.getOutput())) {
      flag = false;
    }
    assertTrue(flag);
  }

  // Test parseRequest() method with extra commas in the input
  @Test
  public void testTooManyCommas() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh, chair, 1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "too many commas, only 1 can be used to separate type and amount" +
              getCategoryAndTypeTest(),
          e.getMessage());
    }
  }

  // Test parseRequest() method with no commas in the input
  @Test
  public void testNoCommas() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh chair 1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "need to enter category, type and amount; seperate the type and amount with a comma: [Catergory] [Type], [Amount]" +
              getCategoryAndTypeTest(),
          e.getMessage());
    }
  }

  // Test parseRequest() method with invalid number (a double value)
  @Test
  public void testInvalidInputNumber() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh chair, 1.0");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "amount must be a number and should be entered after type: [Category] [Type], [Amount]",
          e.getMessage());
    }
  }

  // Test parseRequest() method with invalid number (a negative integer)
  @Test
  public void testInvalidInputNumber1() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh chair, -1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals("Could not parse that request, "
                       + "the amount must be a positive non-zero integer",
                   e.getMessage());
    }
  }

  // Test parseRequest() method with invalid number (zero)
  @Test
  public void testInvalidInputNumber2() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh chair, 0");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals("Could not parse that request, "
                       + "the amount must be a positive non-zero integer",
                   e.getMessage());
    }
  }

  // Test parseRequest() method with invalid number (no number/amount)
  @Test
  public void testInvalidInputNumber3() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh chair");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "need to enter category, type and amount; seperate the type and amount with a comma: [Catergory] [Type], [Amount]" +
              getCategoryAndTypeTest(),
          e.getMessage());
    }
  }

  // Test parseRequest() method with invalid input (no type)
  @Test
  public void testInputWithoutType() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("mesh , 1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals("Could not parse that request, "
                       + "need a category and/or type" +
                       getCategoryAndTypeTest(),
                   e.getMessage());
    }
  }

  // Test parseRequest() method with invalid input (no Category)
  @Test
  public void testInputWithoutCategory() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest(" chair, 1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals("Could not parse that request, "
                       + "need a category and/or type" +
                       getCategoryAndTypeTest(),
                   e.getMessage());
    }
  }

  // Test parseRequest() method with invalid input (no Category)
  @Test
  public void testInputWithThreeWords() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("green mesh chair, 1");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "input is too long, does not contain a furniture category and/or a type" +
              getCategoryAndTypeTest(),
          e.getMessage());
    }
  }

  // Test parseRequest() method with invalid input (wrong order)
  @Test
  public void testInputWithWrongOrder() {
    createConnection();
    try {
      SupplyManager test = new SupplyManager();
      test.setRequest("1, mesh chair");
      test.parseRequest();
    } catch (InvalidInputException e) {
      assertEquals(
          "Could not parse that request, "
              +
              "amount must be a number and should be entered after type: [Category] [Type], [Amount]",
          e.getMessage());
    }
  }

  // Test capitalize() method with non-capital word
  @Test
  public void testNonCapitalWord() throws InvalidInputException {
    createConnection();
    String expectedOutput = "Executive";
    SupplyManager test = new SupplyManager();
    assertTrue(
        "Method capitalize() does not capitalize the first letter of the word",
        expectedOutput.equals(test.capitalize("executive")));
  }

  @Test
  // Test the canBuild() method using valid input
  public void testCanBuildWithValidArray() throws InvalidInputException {
    createConnection();
    SupplyManager test = new SupplyManager();
    int[] array = {2, 2, 2, 2};
    test.setNumber(1);
    assertTrue(
        "Method canBuild was unable to build the cheapest combination based on the number user requested",
        test.canBuild(array));
  }

  @Test
  // Test the canBuild() method using valid input
  public void testCanBuildWithValidArray1() throws InvalidInputException {
    createConnection();
    SupplyManager test = new SupplyManager();
    int[] array = {2, 3, 2, 4};
    test.setNumber(2);
    assertTrue(
        "Method canBuild was unable to build the cheapest combination based on the number user requested",
        test.canBuild(array));
  }

  @Test
  // Test the canBuild() method using invalid input
  public void testCanBuildWithInvalidArray() throws InvalidInputException {
    createConnection();
    SupplyManager test = new SupplyManager();
    int[] array = {2, 2, 2, 2};
    test.setNumber(3);
    assertFalse(
        "Method canBuild returned true for invalid number that the user requested",
        test.canBuild(array));
  }

  @Test
  // Test the canBuild() method using invalid input
  public void testCanBuildWithInvalidArray1() throws InvalidInputException {
    createConnection();
    SupplyManager test = new SupplyManager();
    int[] array = {2, 3, 3, 3};
    test.setNumber(3);
    assertFalse(
        "Method canBuild returned true for invalid number that the user requested",
        test.canBuild(array));
  }

  // Test deleting all of 005 ManuID chairs and check to see if it prints the
  // correct manufacturers
  @Test
  public void testManufacturers() throws InvalidInputException {
    createConnection();
    String expectedOutput =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Chairs R Us, Furniture Goods, and Fine Office Supplies.";
    insertRowInChair("C0000", "kneeling", "N", "Y", "N", "Y", 1, "005");
    insertRowInChair("C0505", "mesh", "N", "Y", "N", "N", 1, "005");
    insertRowInChair("C0707", "mesh", "Y", "Y", "N", "Y", 1, "005");
    // delete all the mesh and kneeling chairs that have ManuID of 005
    SupplyManager supply = new SupplyManager("mesh chair, 1");
    OrderOutput output = supply.findCheapest();
    // boolean flag1 = !output.isPossible();
    SupplyManager supply2 = new SupplyManager("kneeling chair, 1");
    OrderOutput output2 = supply2.findCheapest();
    // boolean flag2 = !output2.isPossible();
    SupplyManager supply3 = new SupplyManager("mesh chair, 1");
    OrderOutput output3 = supply3.findCheapest();
    // boolean flag3 = !output3.isPossible();

    // Now if we try to print 2 mesh chairs, it should print all manufactuerers
    SupplyManager supply4 = new SupplyManager("mesh chair, 2");
    OrderOutput output4 = supply4.findCheapest();
    boolean flag4 = !output4.isPossible();
    if (!expectedOutput.equals(output4.getOutput())) {
      flag4 = false;
    }
    assertTrue(
        "Method getManufacturers() was unable to return the correct list of suggested manufacturers",
        flag4);
  }

  // getManufacturer method for desk category
  @Test
  public void testGetManufacturerDesk() throws InvalidInputException {
    createConnection();
    SupplyManager supply = new SupplyManager();
    String expected =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Academic Desks, Office Furnishings, Furniture Goods, and Fine Office Supplies.";
    assertTrue(
        "Method getManufacturers(String category) does not return the desired Manufactures list",
        expected.equals(supply.getManufacturers("DESK")));
  }

  // getManufacturer method for lamp category
  @Test
  public void testGetManufacturerLamp() throws InvalidInputException {
    createConnection();
    SupplyManager supply = new SupplyManager();
    String expected =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    assertTrue(
        "Method getManufacturers(String category) does not return the desired Manufactures list",
        expected.equals(supply.getManufacturers("LAMP")));
  }

  // getManufacturer method for filing category
  @Test
  public void testGetManufacturerFiling() throws InvalidInputException {
    createConnection();
    SupplyManager supply = new SupplyManager();
    String expected =
        "Order cannot be fulfilled based on current inventory. Suggested manufacturers are Office Furnishings, Funirture Goods, and Fine Office Supplies.";
    assertTrue(
        "Method getManufacturers(String category) does not return the desired Manufactures list",
        expected.equals(supply.getManufacturers("FILING")));
  }

  // Test getCategoryAndType method
  @Test
  public void testGetCategoryAndType() throws InvalidInputException {
    String expected =
        "\nThis is a list of possible categories and their types:\n";
    expected += "CHAIR: Kneeling, Task, Mesh, Executive, Ergonomic.\n";
    expected += "DESK: Standing, Adjustable, Traditional.\n";
    expected += "LAMP: Desk, Study, Swing Arm.\n";
    expected += "FILING: Small, Medium, Large.\n";
    createConnection();
    SupplyManager supply = new SupplyManager();
    assertTrue(
        "Method getCategoryAndType does not return the expected string message",
        expected.equals(supply.getCategoryAndType()));
  }

  // Test generateOrderForm() to see if it prints the accurate form
  @Test
  public void testGenerateOrderForm1() throws IOException {
    String[] items = {"C1333", "C2069"};
    String expected = "Furniture Order Form"
                      + "\n\n"
                      + "Faculty Name:"
                      + "\n"
                      + "Contact:"
                      + "\n"
                      + "Date:"
                      + "\n\n"
                      + "Original Request: mesh chair, 1"
                      + "\n\n"
                      + "Items Ordered"
                      + "\n"
                      + "ID: C1333"
                      + "\n"
                      + "ID: C2069"
                      + "\n\n"
                      + "Total Price: $100";
    OrderOutput test = new OrderOutput("mesh chair, 1", items, 100);
    test.generateOrderForm();
    assertTrue(
        "Method generateOrderForm() did not return the proper order form that was expected",
        expected.equals(test.getOutputToOrderForm()));
  }

  // Test generateOrderForm() to see if it prints the accurate form
  @Test
  public void testGenerateOrderForm2() throws IOException {
    String[] items = {"C1333", "C2069", "C0070"};
    String expected = "Furniture Order Form"
                      + "\n\n"
                      + "Faculty Name:"
                      + "\n"
                      + "Contact:"
                      + "\n"
                      + "Date:"
                      + "\n\n"
                      + "Original Request: executive chair, 1"
                      + "\n\n"
                      + "Items Ordered"
                      + "\n"
                      + "ID: C1333"
                      + "\n"
                      + "ID: C2069"
                      + "\n"
                      + "ID: C0070"
                      + "\n\n"
                      + "Total Price: $150";
    OrderOutput test = new OrderOutput("executive chair, 1", items, 150);
    test.generateOrderForm();
    assertTrue(
        "Method generateOrderForm() did not return the proper order form that was expected",
        expected.equals(test.getOutputToOrderForm()));
  }

  // Test The constructor with inavlid inputs for all tables in the inventory
  // no comma present for the selected desk input
  @Test(expected = InvalidInputException.class)
  public void testInvalidDeskInput1() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("standing desk 1");
  }

  // no number for desk input
  @Test(expected = InvalidInputException.class)
  public void testInvalidDeskInput2() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("adjustable desk");
  }

  // test with no type of desk declared
  @Test(expected = InvalidInputException.class)
  public void testInvalidDeskInput3() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("desk, 1");
  }

  // test where quantity is not a number
  @Test(expected = InvalidInputException.class)
  public void testInvalidDeskInput5() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("traditional desk, two");
  }

  // test where order is incorrect
  @Test(expected = InvalidInputException.class)
  public void testInvalidDeskInput6() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("2, traditional desk");
  }

  // no comma present for the selected chair input
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput1() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("mesh chair 1");
  }

  // no number for chair input
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput2() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("mesh chair");
  }

  // test with no type of chair declared
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput3() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("chair, 1");
  }

  // test an input where there are two commas
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput4() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("mesh,chair,1");
  }

  // test where quantity is not a number
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput5() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("mesh chair, two");
  }

  // input order is incorrect
  @Test(expected = InvalidInputException.class)
  public void testInvalidChairInput6() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("1, chair executive");
  }

  // no comma present for the selected lamp input
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput1() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("swing arm lamp 1");
  }

  // no number for lamp input
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput2() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("desk lamp");
  }

  // test with no type of lamp declared
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput3() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("lamp, 1");
  }

  // test an input where there are two commas
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput4() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("swing arm,lamp,1");
  }

  // test where quantity is not a number
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput5() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("desk lamp two");
  }

  // incorrect input order
  @Test(expected = InvalidInputException.class)
  public void testInvalidLampInput6() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("2, desk lamp");
  }

  // no comma present for the selected filing input
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput1() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("small filing 1");
  }

  // no number for filing input
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput2() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("medium filing");
  }

  // test with no type of filing declared
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput3() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("filing, 1");
  }

  // test an input where there are two commas
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput4() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("large,filing,1");
  }

  // test where quantity is not a number
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput5() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("medium filing, two");
  }

  // incorrect input order
  @Test(expected = InvalidInputException.class)
  public void testInvalidFilingInput6() throws InvalidInputException {
    SupplyManager supply = new SupplyManager("large, 2 filing");
  }

  public void createConnection() {
    try {
      // using this for test, change it once done
      this.dbConnect = DriverManager.getConnection(
          "jdbc:mysql://localhost/inventory", "scm", "ensf409");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRowInDesk(String id, String type, String legs, String top,
                              String drawer, int price, String manuID) {

    try {
      String query =
          "INSERT INTO desk (ID, Type, Legs, Top, Drawer, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement myStmnt = this.dbConnect.prepareStatement(query);
      myStmnt.setString(1, id);
      myStmnt.setString(2, type);
      myStmnt.setString(3, legs);
      myStmnt.setString(4, top);
      myStmnt.setString(5, drawer);
      myStmnt.setInt(6, price);
      myStmnt.setString(7, manuID);

      myStmnt.executeUpdate();
      myStmnt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRowInChair(String id, String type, String legs, String arms,
                               String seat, String cushion, int price,
                               String manuID) {

    try {
      String query =
          "INSERT INTO chair (ID, Type, Legs, Arms, Seat, Cushion, Price, ManuID) VALUES (?,?,?,?,?,?,?,?)";
      PreparedStatement myStmnt = this.dbConnect.prepareStatement(query);
      myStmnt.setString(1, id);
      myStmnt.setString(2, type);
      myStmnt.setString(3, legs);
      myStmnt.setString(4, arms);
      myStmnt.setString(5, seat);
      myStmnt.setString(6, cushion);
      myStmnt.setInt(7, price);
      myStmnt.setString(8, manuID);

      myStmnt.executeUpdate();
      myStmnt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRowInLamp(String id, String type, String base, String bulb,
                              int price, String manuID) {

    try {
      String query =
          "INSERT INTO lamp (ID, Type, Base, Bulb, Price, ManuID) VALUES (?,?,?,?,?,?)";
      PreparedStatement myStmnt = this.dbConnect.prepareStatement(query);
      myStmnt.setString(1, id);
      myStmnt.setString(2, type);
      myStmnt.setString(3, base);
      myStmnt.setString(4, bulb);
      myStmnt.setInt(5, price);
      myStmnt.setString(6, manuID);

      myStmnt.executeUpdate();
      myStmnt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertRowInFiling(String id, String type, String rails,
                                String drawers, String cabinet, int price,
                                String manuID) {

    try {
      String query =
          "INSERT INTO filing (ID, Type, Rails, Drawers, Cabinet, Price, ManuID) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement myStmnt = this.dbConnect.prepareStatement(query);
      myStmnt.setString(1, id);
      myStmnt.setString(2, type);
      myStmnt.setString(3, rails);
      myStmnt.setString(4, drawers);
      myStmnt.setString(5, cabinet);
      myStmnt.setInt(6, price);
      myStmnt.setString(7, manuID);

      myStmnt.executeUpdate();
      myStmnt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public String getCategoryAndTypeTest() {
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
}
