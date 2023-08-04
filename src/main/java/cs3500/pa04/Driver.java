package cs3500.pa04;

import cs3500.pa04.controller.ConsoleGameController;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.net.Socket;

/**
 * Creates the main functionality
 */
public class Driver {

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      String host = "0.0.0.0";
      int port = 35001;

      try {
        Socket socket = new Socket(host, port);
        new ProxyController(socket).run();

      } catch (IOException e) {
        System.err.println("Connection failed");
      }
    } else if (args.length == 1){
      new ConsoleGameController().runGame();
    } else {
//      launch();
    }
  }
}
