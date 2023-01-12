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
 * @version 1.2
 * @since 1.0
 */
package edu.ucalgary.ensf409;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class OrderOutput {

  private boolean possible;
  private String output;
  private String outputToOrderForm = "";
  private String request;
  private String[] items;
  private String possibleOutput = "Purchase ";
  private int cost;

  public OrderOutput(boolean possible, String output) {
    this.possible = possible;
    this.output = output;
  }

  public OrderOutput(String request, String[] items, int cost) {
    this(true, "");
    this.request = request;
    this.items = items;
    this.cost = cost;
  }

  public String[] getItems() { return this.items; };

  public int getCost() { return this.cost; };

  public String getPossibleOutput() { return this.possibleOutput; };

  public boolean isPossible() { return this.possible; };

  public String getOutput() { return this.output; };

  public String getOutputToOrderForm() { return this.outputToOrderForm; };

  public void generateOrderForm() throws IOException {

    // create output file
    BufferedWriter writer = new BufferedWriter(new FileWriter("orderform.txt"));

    // all of writers below are writing into the output file

    writer.write("Furniture Order Form");
    this.outputToOrderForm += "Furniture Order Form";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    writer.write("Faculty Name:");
    this.outputToOrderForm += "Faculty Name:";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    writer.write("Contact:");
    this.outputToOrderForm += "Contact:";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    writer.write("Date:");
    this.outputToOrderForm += "Date:";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    writer.write("Original Request: " + this.request);
    this.outputToOrderForm += "Original Request: " + this.request;
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    writer.write("Items Ordered");
    this.outputToOrderForm += "Items Ordered";
    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.flush();

    // need a loop to write the items ordered
    for (int i = 0; i < items.length; i++) {
      writer.write("ID: " + items[i]);
      this.outputToOrderForm += "ID: " + items[i];
      writer.newLine();
      this.outputToOrderForm += "\n";
      writer.flush();
      if (i < items.length - 1) {
        this.possibleOutput += items[i] + " and ";
      } else {
        this.possibleOutput += items[i];
      }
    }

    writer.newLine();
    this.outputToOrderForm += "\n";
    writer.write("Total Price: $" + this.cost);
    this.outputToOrderForm += "Total Price: $" + this.cost;
    writer.flush();
    this.possibleOutput += " $" + this.cost;

    writer.close();
  }
}