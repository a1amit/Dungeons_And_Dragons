package Backend.Visitors;

import Backend.Enemies.Enemy;
import Backend.Characters.Player;

public interface UnitVisitor {
    String kill(Player player);
    String kill(Enemy enemy);
}