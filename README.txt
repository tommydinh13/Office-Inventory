To Run Code:
1) Navigate to final project directory
2) Compile by inserting "javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu\ucalgary\ensf409\OrderOutput.java edu\ucalgary\ensf409\InvalidInputException.java edu\ucalgary\ensf409\InventoryObject.java edu\ucalgary\ensf409\SupplyManagement.java edu\ucalgary\ensf409\SupplyManager.java"
3) Run the program by inserting "java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu.ucalgary.ensf409.SupplyManagement"
4) Insert values following format "[category] [type], [amount]

To Run Junit tests:
1) Navigate to final project directory
2) Compile by inserting "javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar;. edu\ucalgary\ensf409\*.java"
3) Run the program by inserting "java -cp .;lib/mysql-connector-java-8.0.23.jar;lib/hamcrest-core-1.3.jar;lib/junit-4.13.2.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.ProjectTest"