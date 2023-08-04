package cs3500.pa04.consoleView;

/**
 * This class is responsible for printing game related messages to the console.
 */
public class GameView {

    /**
     * Prints the welcome message.
     */
    public static void welcomeMessage() {
        System.out.println("Welcome to Battle Ship!");
    }

    /**
     * Prompts the user to select a board size.
     */
    public static void askBoardSize() {
        System.out.println("Please select your board Size");
        System.out.println("X and Y values must be between 6 and 15 inclusive");
    }

    /**
     * Informs the user that an invalid board size has been entered and prompts to enter a valid size.
     */
    public static void askValidBoardSize() {
        System.out.println("you entered an invalid board size :(");
        askBoardSize();
    }

    /**
     * Prompts the user to select fleet with given number of ships.
     *
     * @param numberOfShips Maximum total number of ships.
     */
    public static void askFleetSelection(int numberOfShips) {
        System.out.println("Select your fleet with a minimum number of 1 ship"
                + "and maximum total number of " + numberOfShips + " Ships");
        System.out.println("Indicate the number of each type of Ship separated by a white space in "
                + "the following index");
        System.out.println("index 0 = Submarine (size 3)");
        System.out.println("index 1 = Destroyer (size 4)");
        System.out.println("index 2 = Battleship (size 5)");
        System.out.println("index 3 = Carrier (size 6)");
    }

    /**
     * Prompts the user to enter a number of shots.
     *
     * @param numOfShots Number of shots the user is prompted to enter.
     */
    public static void askShots(int numOfShots) {
        System.out.println("Please enter " + numOfShots + " shots");
    }

    /**
     * Informs the user that the coordinate has already been shot and prompts to shoot a different
     * coordinate.
     *
     * @param x X-coordinate.
     * @param y Y-coordinate.
     */
    public static void displayAlreadyShotMessage(int x, int y) {
        System.out.println("You already shot coordinate " + x + ", " + y);
        System.out.println("Please Shoot a different coordinate");
    }

    /**
     * Displays the game board with a given label.
     *
     * @param board The 2D array representation of the board.
     * @param label Label of the board.
     */
    public static void displayBoard(String[][] board, String label) {
        System.out.println(label + " board:");
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print(i);
        }
        System.out.print(" X");
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println("Y");
    }

    /**
     * Informs the user that the entered coordinates are out of bound.
     */
    public static void displayCoordinateOutOfBoundMessage() {
        System.out.println("please enter only valid coordinates in bound");
    }

    /**
     * Displays the name of the winner.
     *
     * @param winner The name of the winner.
     */
    public static void displayWinner(String winner) {
        System.out.println(winner + " Won the game!");
    }
}
