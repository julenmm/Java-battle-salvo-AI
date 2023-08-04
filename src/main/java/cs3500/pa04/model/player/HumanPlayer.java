package cs3500.pa04.model.player;

import cs3500.pa04.model.game.BattleBoard;
import cs3500.pa04.model.game.GameResult;
import cs3500.pa04.model.ship.*;
import cs3500.pa04.model.utils.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents a HumanPlayer that implements the Player interface.
 * It provides methods to initialize the game board, place ships, take shots,
 * report damage, end the game, and get the current state of the game.
 */
public class HumanPlayer implements Player {
    private final int xLength;
    private final int yLength;
    private final BattleBoard myBoard;
    private final BattleBoard enemyBoard;
    private final ArrayList<Ship> fleet;
    public ArrayList<Coord> alreadyShot;
    private ArrayList<Coord> shots;

    /**
     * Constructs a new HumanPlayer with a specified board size and fleet configuration.
     * Initializes the player's own board and the enemy's board.
     *
     * @param x         the width of the game board.
     * @param y         the height of the game board.
     * @param shipSpecs a map where the key is a Ship object and the value is the count of that ship type.
     */
    public HumanPlayer(int x, int y, Map<ShipType, Integer> shipSpecs) {
        this.xLength = x;
        this.yLength = y;
        this.fleet = this.setup(y, x, shipSpecs);
        myBoard = new BattleBoard(x, y, this.fleet);
        enemyBoard = new BattleBoard(x, y);
        this.alreadyShot = new ArrayList<>();
    }

    /**
     * Returns the human player's name.
     *
     * @return A string representing the human player's name.
     */
    @Override
    public String name() {
        return "Lame Human";
    }

    /**
     * Sets up the bot's fleet according to the provided map of ship types and their counts.
     *
     * @param height    the height of the game board.
     * @param width     the width of the game board.
     * @param shipSpecs a map where the key is a Ship object and the value is the count of that ship
     *                  type.
     * @return An ArrayList of Ship objects representing the bot's fleet.
     */
    @Override
    public ArrayList<Ship> setup(int height, int width, Map<ShipType, Integer> shipSpecs) {
        ArrayList<Ship> fleetSelection = new ArrayList<>();
        for (ShipType key : shipSpecs.keySet()) {
            if (key.equals(ShipType.Submarine)) {
                for (int i = 0; i < shipSpecs.get(key); i++) {
                    fleetSelection.add(new Submarine());
                }
            } else if (key.equals(ShipType.Carrier)) {
                for (int i = 0; i < shipSpecs.get(key); i++) {
                    fleetSelection.add(new Carrier());
                }
            } else if (key.equals(ShipType.BattleShip)) {
                for (int i = 0; i < shipSpecs.get(key); i++) {
                    fleetSelection.add(new Battleship());
                }
            } else if (key.equals(ShipType.Destoyer)) {
                for (int i = 0; i < shipSpecs.get(key); i++) {
                    fleetSelection.add(new Destroyer());
                }
            }
        }
        return fleetSelection;
    }

    /**
     * Decides which coordinates the human player should shoot at in the current round.
     *
     * @return An ArrayList of Coord objects representing the coordinates to be shot.
     * @throws IllegalArgumentException if the player tries to shoot at a coordinate they've already
     * shot at.
     */
    @Override
    public ArrayList<Coord> takeShots() {
        for (Coord c : this.shots) {
            if (this.alreadyShot.contains(c)) {
                throw new IllegalArgumentException(
                        "cannot shoot again coordinates X= " + c.getX() + " Y= " + c.getY());
            }
            this.enemyBoard.hitEnemyBoardAttempts(c.getX(), c.getY());
        }
        this.alreadyShot.addAll(this.shots);
        return this.shots;
    }

    /**
     * Reports the damage of opponent's shots on the human player's board.
     *
     * @param opponentShotsOnBoard An ArrayList of Coord objects representing the coordinates of the
     *                             opponent's shots.
     * @return An ArrayList of Coord objects representing the coordinates of successful hits.
     */
    @Override
    public ArrayList<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
        ArrayList<Coord> hits = new ArrayList<>();
        ArrayList<Ship> shipsToRemove = new ArrayList<>();

        ArrayList<Coord> opponentShotsOnBoardCopy = new ArrayList<>(opponentShotsOnBoard);

        for (Coord cord : opponentShotsOnBoardCopy) {
            for (Ship s : this.fleet) {
                if (s.shipInCoord(cord)) {
                    hits.add(cord);
                    s.hitShip(cord);
                    if (s.getCoordinates().size() == 0) {
                        shipsToRemove.add(s);
                    }
                }
            }
        }

        this.fleet.removeAll(shipsToRemove);

        for (Coord cord : opponentShotsOnBoardCopy) {
            this.myBoard.hitMyBoard(cord.getX(), cord.getY());
        }

        return hits;
    }

    /**
     * Reports the human player's successful hits on the opponent's board.
     *
     * @param shotsThatHitOpponentShips An ArrayList of Coord objects representing the coordinates of
     *                                  successful hits.
     */
    @Override
    public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
        for (Coord c : shotsThatHitOpponentShips) {
            this.enemyBoard.successfulEnemyHit(c.getX(), c.getY());
        }
    }

    /**
     * Ends the game and reports the result.
     *
     * @param result A GameResult object representing the result of the game.
     * @param reason A string explaining why the game ended.
     */
    @Override
    public void endGame(GameResult result, String reason) {
        System.out.println(result.toString());
    }

    /**
     * Returns the human player's current fleet.
     *
     * @return An ArrayList of Ship objects representing the human player's current fleet.
     */
    public ArrayList<Ship> getFleet() {
        return this.fleet;
    }

    /**
     * Returns the current state of the human player's board.
     *
     * @return A 2D string array representing the current state of the human player's board.
     */
    public String[][] getMyBoard() {
        return this.myBoard.getBoard();
    }

    /**
     * Returns the current state of the enemy's board from the human player's perspective.
     *
     * @return A 2D string array representing the current state of the enemy's board from the human
     * player's perspective.
     */
    public String[][] getEnemyBoard() {
        return this.enemyBoard.getBoard();
    }

    /**
     * Sets a new list of coordinates to be shot at in the next round.
     *
     * @param newShots An ArrayList of Coord objects representing the new coordinates to be shot.
     */
    public void setNewShots(ArrayList<Coord> newShots) {
        this.shots = newShots;
    }

    /**
     * Checks if the human player has no more shots to take.
     *
     * @param currentShots The number of shots taken in the current round.
     * @return true if the player has no more shots to take, false otherwise.
     */
    public boolean noMoreShots(int currentShots) {
        return this.alreadyShot.size() + currentShots == this.yLength * this.xLength;
    }

    /**
     * Checks if a coordinate has already been shot at.
     *
     * @param c A Coord object representing the coordinate to check.
     * @return true if the coordinate has already been shot at, false otherwise.
     */
    public boolean alreadyShot(Coord c) {
        return this.alreadyShot.contains(c);
    }
}
