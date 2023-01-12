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
 * @version 1.0
 * @since 1.0
 */
package edu.ucalgary.ensf409;

// Custom exception class
public class InvalidInputException extends Exception {

  public static final long serialVersionUID = 1L;

  public InvalidInputException(String message) {

    super("Could not parse that request, " + message);
  }
}