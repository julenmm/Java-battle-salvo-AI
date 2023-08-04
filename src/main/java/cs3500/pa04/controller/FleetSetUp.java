package cs3500.pa04.controller;

import static cs3500.pa04.consoleView.GameView.askFleetSelection;

import cs3500.pa04.model.ship.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is responsible for managing the fleet setup process in a game.
 * It handles the selection of ships and their quantities for the fleet,
 * and ensures the fleet size is within the specified limit.
 */
public class FleetSetUp {
    /**
     * The fleet of ships represented as a mapping from the Ship object to their respective
     * quantities.
     */
    private final HashMap<ShipType, Integer> fleet;
    /**
     * The maximum size limit for the fleet.
     */
    private final int fleetSize;
    /**
     * Stores the selection of ships for the fleet.
     */
    private ArrayList<Integer> fleetSelection;

    /**
     * The constructor for FleetSetUp class.
     *
     * @param fleet          A mapping from the Ship object to their respective quantities.
     * @param fleetSize      The maximum size limit for the fleet.
     * @param fleetSelection Stores the selection of ships for the fleet.
     */
    public FleetSetUp(HashMap<ShipType, Integer> fleet, int fleetSize,
                      ArrayList<Integer> fleetSelection) {
        this.fleet = fleet;
        this.fleetSelection = fleetSelection;
        this.fleetSize = fleetSize;
    }

    /**
     * This method collects user input to select the quantity of each type of ship for the fleet.
     * The method ensures that the total number of ships does not exceed the fleet size.
     *
     * @param scanner A Scanner object to read the user input.
     */
    public void fleetSelection(Scanner scanner) {
        askFleetSelection(this.fleetSize);
        int totalShips = 0;
        int selectionCount = 0;
        while (!(selectionCount == 4)) {
            int nextInt = scanner.nextInt();
            this.fleetSelection.add(nextInt);
            totalShips += nextInt;
            selectionCount++;
        }
        if (totalShips > this.fleetSize || this.fleetSize == 0) {
            this.fleetSelection = new ArrayList<>();
            this.fleetSelection(scanner);
        }
    }

    /**
     * This method adds the selected ships to the fleet.
     * The selection is based on the choices made in the fleetSelection method.
     */
    public void buildFleet() {
        this.fleet.put(ShipType.Submarine, this.fleetSelection.get(0));
        this.fleet.put(ShipType.Destoyer, this.fleetSelection.get(1));
        this.fleet.put(ShipType.BattleShip, this.fleetSelection.get(2));
        this.fleet.put(ShipType.Carrier, this.fleetSelection.get(3));
    }

    /**
     * This method returns the fleet of ships.
     *
     * @return A HashMap representing the fleet, where each entry is a Ship object and its quantity.
     */
    public HashMap<ShipType, Integer> getFleet() {
        return this.fleet;
    }

    /**
     * This method returns and ArrayList specifying the number of each type of ship
     *
     * @return An ArrayList representing the number of each type of ship
     */
    public ArrayList<Integer> getFleetSelection() {
        return this.fleetSelection;
    }
}
