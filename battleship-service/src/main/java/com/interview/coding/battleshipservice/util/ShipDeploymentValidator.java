package com.interview.coding.battleshipservice.util;

import static java.util.stream.Collectors.toList;

import com.interview.coding.battleshipservice.exception.ShipDeploymentException;
import com.interview.coding.battleshipservice.model.Coordinate;
import com.interview.coding.battleshipservice.model.ship.Ship;
import com.interview.coding.battleshipservice.model.ship.ShipType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Singleton;

@Singleton
public class ShipDeploymentValidator {

    public void validate(List<Ship> ships) {
        numberOfDeployedShips(ships);
        duplicatedDeployment(ships);
        shipsOverlap(ships);
        ships.forEach(ship -> {
            shipLengthIsWrong(ship);
            shipIsOutOfGrid(ship);
            shipIsNotContiguous(ship);
        });
    }

    private void duplicatedDeployment(List<Ship> ships) {
        List<String> deployedTypes = new ArrayList<>();
        for (Ship ship : ships) {
            String shipType = ship.getShipType().getShipTypeName();
            if (deployedTypes.contains(shipType)) {
                throw new ShipDeploymentException(shipType, ship.getCoordinates().stream().map(Coordinate::getValue).collect(toList()));
            }
            deployedTypes.add(shipType);
        }
    }

    private void numberOfDeployedShips(List<Ship> shipDeployments) {
        int requiredNumberOfDeployments = ShipType.values().length;
        int providedDeploymentsNumber = shipDeployments.size();
        if (providedDeploymentsNumber != requiredNumberOfDeployments) {
            throw new ShipDeploymentException(
                String.format("Wrong number of deployed ships: expected %d, got %d", requiredNumberOfDeployments, providedDeploymentsNumber));
        }
    }

    private void shipLengthIsWrong(Ship ship) {
        // Use a set to account for overlapping coordinates
        boolean isLengthWrong = new HashSet<>(ship.getCoordinates()).size() != ship.getShipType().getShipLength();
        if (isLengthWrong) {
            throwException(ship);
        }
    }

    private void shipIsOutOfGrid(Ship ship) {
        boolean isShipOutOfGrid = ship.getCoordinates().stream().anyMatch(ShipDeploymentValidator::coordinateIsOutOfGrid);
        if (isShipOutOfGrid) {
            throwException(ship);
        }
    }

    private static boolean coordinateIsOutOfGrid(Coordinate coordinate) {
        return coordinate.getRow() < 0 ||
            coordinate.getColumn() < 0 ||
            coordinate.getRow() >= GameConfiguration.FIELD_HEIGHT ||
            coordinate.getColumn() >= GameConfiguration.FIELD_WIDTH;
    }

    private void shipIsNotContiguous(Ship ship) {
        boolean shipIsNotContiguous = !isHorizontal(ship) && !isVertical(ship);

        if (shipIsNotContiguous) {
            throwException(ship);
        }

        List<Coordinate> coordinates = ship.getCoordinates();
        for (int i = 0; i <  coordinates.size()-1; i++){

            if (!(coordinates.get(i).getRow()+1 == coordinates.get(i+1).getRow())){
                throwException(ship);
            }

            if (!(coordinates.get(i).getColumn()+1 == coordinates.get(i+1).getColumn())){
                throwException(ship);
            }
        }
    }

    private static void throwException(Ship ship) {
        throw new ShipDeploymentException(ship.getShipType().getShipTypeName(),
            ship.getCoordinates().stream().map(Coordinate::getValue).collect(toList()));
    }

    private boolean isHorizontal(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        int firstRow = coordinates.get(0).getRow();
        return coordinates.stream().allMatch(c -> c.getRow() == firstRow);
    }

    private boolean isVertical(Ship ship) {
        List<Coordinate> coordinates = ship.getCoordinates();
        int firstColumn = coordinates.get(0).getColumn();
        return coordinates.stream().allMatch(c -> c.getColumn() == firstColumn);
    }

    private void shipsOverlap(Collection<Ship> deployedShips) {
        List<Coordinate> allCoordinates = deployedShips.stream()
            .flatMap(ship -> ship.getCoordinates().stream())
            .collect(toList());
        Set<Coordinate> reducedCoordinates = new HashSet<>(allCoordinates);
        boolean isOverlapping = allCoordinates.size() != reducedCoordinates.size();
        if (isOverlapping) {
            throw new ShipDeploymentException("Deployed ships overlap - they cannot overlap!");
        }
    }
}
