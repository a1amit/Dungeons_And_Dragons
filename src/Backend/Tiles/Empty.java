package Backend.Tiles;

import Backend.Enemies.Enemy;
import Backend.Characters.Player;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Visitors.TileVisitor;

public class Empty extends Tile {

    // an empty tile on board is represented by: "."
    public Empty(Position position) {
        super('.', position);
    }


    @Override
    public String accept(TileVisitor tileVisitor) {
        return tileVisitor.interact(this);
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
}