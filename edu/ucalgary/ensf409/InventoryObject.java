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
public class InventoryObject {

  private String id;
  private String type;
  private int price;
  private String manufacturer;
  private int[] vals;

  public InventoryObject(String id, String type, int price, String manufacturer,
                         char[] vals) {
    this.id = id;
    this.type = type;
    this.price = price;
    this.manufacturer = manufacturer;

    // turning the Y and N in the sql file to 1's and 0's in terms of quantity
    // to add together later and find if possible to build multiple
    this.vals = new int[vals.length];
    for (int i = 0; i < vals.length; i++) {
      if (vals[i] == 'Y') {
        this.vals[i] = 1;
      } else {
        this.vals[i] = 0;
      }
    }
  }

  // getter method for id
  public String getId() { return id; }

  // getter method for type
  public String getType() { return type; }

  // getter method for price
  public int getPrice() { return price; }

  // getter method for manufacturer
  public String getManufacturer() { return manufacturer; }

  // getter method for val
  public int[] getVals() { return vals; }
}