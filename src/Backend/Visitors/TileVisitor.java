package Backend.Visitors;

import Backend.Enemies.Enemy;
import Backend.Characters.Player;
import Backend.Tiles.Empty;
import Backend.Tiles.Wall;

public interface TileVisitor {
    String interact(Player player);
    String interact(Enemy enemy);
    String interact(Empty empty);
    String interact(Wall wall);
}

