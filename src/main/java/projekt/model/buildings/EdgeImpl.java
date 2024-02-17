package projekt.model.buildings;

import javafx.beans.property.Property;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import projekt.model.HexGrid;
import projekt.model.Intersection;
import projekt.model.Player;
import projekt.model.TilePosition;
import projekt.model.TilePosition.*;
import projekt.model.tiles.Tile;

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
    HexGrid grid,
    TilePosition position1,
    TilePosition position2,
    Property<Player> roadOwner,
    Port port
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
        return getIntersections().stream().anyMatch(it -> it.getConnectedEdges().contains(other));
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public Set<Intersection> getIntersections() {
        assert grid.getTileAt(position1) != null;
        EdgeDirection edgeDirection = EdgeDirection.fromRelativePosition(TilePosition.subtract(position2, position1));
        Tile tile1 = grid.getTileAt(position1);
        return Set.of(tile1.getIntersection(edgeDirection.getLeftIntersection()), tile1.getIntersection(edgeDirection.getRightIntersection()));
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
            .filter(edge -> edge.getRoadOwnerProperty().getValue() == player)
            .collect(Collectors.toSet());
    }
}
