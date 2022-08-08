package Backend.Tiles;

import Backend.Enemies.Enemy;
import Backend.Characters.Player;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Visitors.TileVisitor;

public class Wall extends Tile {

    // a wall tile on the game board, represented by '#'
    public Wall(Position position) {
        super('#', position);

    }

    @Override
    public String interact(Player player) {
        return "";
    }

    @Override
    public String interact(Enemy enemy) {
        return "";
    }

    @Override
    public String interact(Empty empty) {
        return "";
    }

    @Override
    public String interact(Wall wall) {
        return "";
    }

    @Override
    public String accept(TileVisitor tileVisitor) {
        return tileVisitor.interact(this);
    }
}