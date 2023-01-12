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
 * @version 1.4
 * @since 1.0
 */
package edu.ucalgary.ensf409;

import java.io.IOException;
import java.util.Scanner;


public class SupplyManagement {
  public static void main(String[] args) {
    try {
      // scanner that takes in the request from client
      Scanner input = new Scanner(System.in);
      System.out.print(
          "Please enter a funiture category, type, and the amount: ");
      // takes user input and removes whitespaces from beginning and end
      String request = input.nextLine();

      // create obj SupplyManager with the userinputs
      SupplyManager manager = new SupplyManager(request.trim());
      OrderOutput output = manager.findCheapest();
      // Write to file
      if (output.isPossible()) {
        try {
          output.generateOrderForm();
          System.out.println(output.getPossibleOutput());
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else {
        System.out.println(output.getOutput());
      }
    } catch (InvalidInputException iie) {
      System.out.println(iie.getMessage());
      main(args);
    }
  }
}