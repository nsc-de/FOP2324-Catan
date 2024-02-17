package projekt.model.buildings;

import javafx.beans.property.Property;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import projekt.model.*;
import projekt.model.tiles.Tile;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link Edge}.
 *
 * @param grid      the HexGrid instance this edge is placed in
 * @param position1 the first position
 * @param position2 the second position
 * @param roadOwner the road's owner, if a road has been built on this edge
 * @param port      a port this edge provides access to, if any
 */
public record EdgeImpl(
    HexGrid grid, TilePosition position1, TilePosition position2, Property<Player> roadOwner, Port port
) implements Edge {
    @Override
    public HexGrid getHexGrid() {
        return grid;
    }

    @Override
    public TilePosition getPosition1() {
        return position1;
    }

    @Override
    public TilePosition getPosition2() {
        return position2;
    }

    @Override
    public boolean hasPort() {
        return port != null;
    }

    @Override
    public Port getPort() {
        return port;
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public boolean connectsTo(final Edge other) {
        return getIntersections().stream().anyMatch(intersection -> other.getIntersections().contains(intersection));
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public Set<Intersection> getIntersections() {
        HexGrid hexGrid = getHexGrid();
        TilePosition.EdgeDirection edgeDirection = TilePosition.EdgeDirection.fromRelativePosition(TilePosition.subtract(position2, position1));
        List<TilePosition> positions = List.of(position1, position2);
        if (edgeDirection == edgeDirection.getLeftIntersection().leftDirection) {
            return Set.of(hexGrid.getIntersectionAt(position1, positions.get(0), positions.get(1)));
        }
        if (edgeDirection == edgeDirection.getRightIntersection().rightDirection) {
            return Set.of(hexGrid.getIntersectionAt(position2, positions.get(1), positions.get(0)));
        }
        return Set.of(
            hexGrid.getIntersectionAt(position1, positions.get(0), positions.get(1)),
            hexGrid.getIntersectionAt(position2, positions.get(1), positions.get(0))
        );
    }

    @Override
    public Property<Player> getRoadOwnerProperty() {
        return roadOwner;
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public Set<Edge> getConnectedRoads(final Player player) {
        return getIntersections().stream()
            .flatMap(intersection -> intersection.getConnectedEdges().stream())
            .filter(edge -> edge.getRoadOwner() == player)
            .collect(Collectors.toSet());
    }
}
