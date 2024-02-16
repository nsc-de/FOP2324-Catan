package projekt.model.buildings;

import javafx.beans.property.Property;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import projekt.model.HexGrid;
import projekt.model.Intersection;
import projekt.model.Player;
import projekt.model.TilePosition;
import projekt.model.tiles.Tile;

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
        Set<Tile> pos1neighbours = grid.getTileAt(position1).getNeighbours();
        Set<Tile> pos2neighbours = grid.getTileAt(position2).getNeighbours();
        pos1neighbours.retainAll(pos2neighbours);
        return pos1neighbours
            .stream()
            .map(tile -> grid.getIntersectionAt(position1, position2, tile.getPosition()))
            .collect(Collectors.toSet());
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
