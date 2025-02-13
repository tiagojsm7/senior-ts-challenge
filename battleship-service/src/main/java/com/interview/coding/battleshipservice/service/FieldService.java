package com.interview.coding.battleshipservice.service;

import com.interview.coding.battleshipservice.model.ship.ShipType;
import com.interview.coding.battleshipservice.util.GameConfiguration;
import com.interview.coding.battleshipservice.model.Cell;
import com.interview.coding.battleshipservice.model.ship.Ship;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FieldService {

    @Inject
    private CoordinateService coordinateService;

    public boolean allShipsSunk(Cell[][] field) {
        if (field == null) {
            throw new IllegalArgumentException("field can't be null");
        }

        return Arrays.stream(field)
                .flatMap(Arrays::stream).allMatch(cell -> cell.isWater() || cell.isHit());
    }

    public boolean isShipSunk(Cell[][] field, Ship ship) {
        if (ship == null) {
            throw new IllegalArgumentException("ship can't be null");
        }
        if (field == null) {
            throw new IllegalArgumentException("field can't be null");
        }

        return ship.getCoordinates().stream()
                .allMatch(coordinate -> field[coordinate.getRow()][coordinate.getColumn()].isHit());
    }

    public Cell[][] buildField(List<Ship> shipsDeployment) {
        Cell[][] field = buildWater();
        deployShips(field, shipsDeployment);
        return field;
    }

    private Cell[][] buildWater() {
        Cell[][] field = new Cell[GameConfiguration.FIELD_HEIGHT][GameConfiguration.FIELD_WIDTH];
        for (int row = 0; row < GameConfiguration.FIELD_HEIGHT; row++) {
            for (int col = 0; col < GameConfiguration.FIELD_WIDTH; col++) {
                field[row][col] = new Cell();
            }
        }
        return field;
    }

    private void deployShips(Cell[][] field, List<Ship> ships) {
        ships.forEach(ship ->
                ship.getCoordinates().forEach(coordinate ->
                        field[coordinate.getRow()][coordinate.getColumn()] = new Cell(ship)
                )
        );
    }

}
