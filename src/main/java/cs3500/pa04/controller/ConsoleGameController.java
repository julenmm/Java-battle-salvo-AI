package cs3500.pa04.controller;

import static cs3500.pa04.consoleView.GameView.*;
import static cs3500.pa04.model.utils.Utils.smallerInt;
import cs3500.pa04.model.game.GameResult;
import cs3500.pa04.model.player.HumanPlayer;
import cs3500.pa04.model.player.MediumBotPlayer;
import cs3500.pa04.model.player.Player;
import cs3500.pa04.model.ship.ShipType;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

    /**
     * This class is the main controller for the game.
     * It coordinates the game flow between the Human and Bot players.
     * It contains the game loop, which keeps running till the game ends, and methods to validate user
     * inputs.
     */
    public class ConsoleGameController {
        private final ArrayList<Integer> fleetSelection;
        private final HashMap<ShipType, Integer> fleet;
        private HumanPlayer p1;
        private Player p2;
        private int x;
        private int y;
        private int fleetSize;

        /**
         * Constructs a GameController object, initializes an empty fleet and fleet selection.
         */
        public ConsoleGameController() {
            this.fleetSelection = new ArrayList<Integer>();
            this.fleet = new HashMap<ShipType, Integer>();
        }

        /**
         * Runs the game by taking inputs for board size, validating it, setting up the fleet, creating
         * players, running the game loop and deciding the winner.
         */
        public void runGame() {
            Scanner scanner = new Scanner(System.in);
            welcomeMessage();
            askBoardSize();
            this.x = scanner.nextInt();
            this.y = scanner.nextInt();
            this.validateBoardSize(scanner);
            this.fleetSize = smallerInt(this.x, this.y);
            FleetSetUp fc = new FleetSetUp(this.fleet, this.fleetSize, this.fleetSelection);
            fc.fleetSelection(scanner);
            fc.buildFleet();
            this.p1 = new HumanPlayer(this.x, this.y, this.fleet);
            this.p2 = new MediumBotPlayer(this.x, this.y, this.fleet);
            this.gameLoop(scanner);
            String reason = "One of the two or both players lost all their ships";
            GameResult gameResult;
            if (this.p1.getFleet().size() == 0 && this.p2.getFleet().size() == 0){
                gameResult = GameResult.DRAW;
            } else if(this.p1.getFleet().size() == 0){
                gameResult = GameResult.WIN;
            } else {
                gameResult = GameResult.LOSE;
            }
            this.p1.endGame(gameResult, reason);
            return;
        }

        /**
         * Takes a Scanner object as input and validates the board size entered by the user.
         * The board size should be between 6 and 15 (inclusive).
         *
         * @param scanner Scanner object to read user input.
         */
        private void validateBoardSize(Scanner scanner) {
            while (!(this.x >= 6 && this.x <= 15 && this.y >= 6 && this.y <= 15)) {
                askValidBoardSize();
                this.x = scanner.nextInt();
                this.y = scanner.nextInt();
            }
        }

        /**
         * Runs the main game loop till the game ends.
         * The loop consists of displaying the board, running shooting loops for both players,
         * taking shots, reporting damage and processing successful hits.
         *
         * @param scanner Scanner object to read user input.
         */
        private void gameLoop(Scanner scanner) {
            while (!this.gameEnded()) {
                String[][] playerBoard = this.p1.getMyBoard();
                String[][] enemyBoard = this.p1.getEnemyBoard();
                displayBoard(playerBoard, "your");
                displayBoard(enemyBoard, "enemy");
                this.humanPlayerShootingLoop(scanner);
                ArrayList<Coord> p1shots = this.p1.takeShots();
                ArrayList<Coord> p2shots = this.p2.takeShots();
                ArrayList<Coord> p1Damage = this.p1.reportDamage(p2shots);
                ArrayList<Coord> p2Damage = this.p2.reportDamage(p1shots);
                this.p1.successfulHits(p2Damage);
                this.p2.successfulHits(p1Damage);
            }
        }

        /**
         * Runs the shooting loop for the human player.
         * The player is asked to take shots equal to their fleet size. Coordinates outside the board
         * or already shot are not accepted.
         *
         * @param scanner Scanner object to read user input.
         */
        private void humanPlayerShootingLoop(Scanner scanner) {
            askShots(this.p1.getFleet().size());
            ArrayList<Coord> p1PreShots = new ArrayList<Coord>();
            while (!((p1PreShots.size() == this.p1.getFleet().size())
                    || this.p1.noMoreShots(p1PreShots.size()))) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if (x < 0 || x >= this.x || y < 0 || y >= this.y) {
                    displayCoordinateOutOfBoundMessage();
                    askShots(this.p1.getFleet().size());
                    p1PreShots = new ArrayList<Coord>();
                } else if (this.p1.alreadyShot(new Coord(x, y))) {
                    displayAlreadyShotMessage(x, y);
                    askShots(this.p1.getFleet().size());
                    p1PreShots = new ArrayList<Coord>();
                } else {
                    p1PreShots.add(new Coord(x, y));
                }
            }
            this.p1.setNewShots(p1PreShots);
        }


        /**
         * Determines if the game has ended.
         * The game ends when either the human player or the bot player has no ships left in their fleet.
         *
         * @return true if the game has ended, false otherwise.
         */
        public boolean gameEnded() {
            return (this.p1.getFleet().size() == 0) || (this.p2.getFleet().size() == 0);
        }

        /**
         * Returns the HumanPlayer object for player 1 (method strictly used for testing purposes).
         *
         * @return The HumanPlayer object for player 1.
         */
        public HumanPlayer getP1() {
            return this.p1;
        }

        /**
         * Sets the HumanPlayer object for player 1 (method strictly used for testing purposes).
         *
         * @param p The HumanPlayer object to be set as player 1.
         */
        public void setP1(HumanPlayer p) {
            this.p1 = p;
        }

        /**
         * Returns the BotPlayer object for player 2 (method strictly used for testing purposes).
         *
         * @return The BotPlayer object for player 2.
         */
        public Player getP2() {
            return this.p2;
        }

        /**
         * Sets the BotPlayer object for player 2.
         *
         * @param p The BotPlayer object to be set as player 2
         *          (method strictly used for testing purposes).
         */
        public void setP2(Player p) {
            this.p2 = p;
        }
    }

