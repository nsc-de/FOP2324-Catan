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
        return getIntersections().stream().anyMatch(intersection -> other.getIntersections().stream().anyMatch(intersection1 -> intersection == intersection1));
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public Set<Intersection> getIntersections() {
        HexGrid hexGrid = getHexGrid();
        List<Tile> neighboringTiles = hexGrid.getTileAt(position1).getNeighbours()
            .stream().filter(tile -> hexGrid.getTileAt(position2).getNeighbours().stream().anyMatch(tile2 -> tile2 == tile)).toList();
        return Set.of(hexGrid.getIntersectionAt(position1, position2, neighboringTiles.get(0).getPosition()), hexGrid.getIntersectionAt(position1, position2, neighboringTiles.get(1).getPosition()));
    }

    @Override
    public Property<Player> getRoadOwnerProperty() {
        return roadOwner;
    }

    @Override
    @StudentImplementationRequired("H1.3")
    public Set<Edge> getConnectedRoads(final Player player) {
        return getHexGrid().getRoads(player).values().stream().filter(edge -> edge.connectsTo(this)).collect(Collectors.toSet());
    }
}
