

import Backend.Characters.Mage;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Characters.Rogue;
import Backend.Characters.Warrior;
import Backend.Enemies.Monster;
import Backend.Tiles.Unit;
import Backend.Tiles.Wall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitTests {
    private Unit unit;
    private Unit enemy;
    private Wall wall;

    @Before
    public void setupCombatInteraction() {
        unit = new Warrior(new Position(0, 0), "Amit", new Health(120), 15, 5, 2);
        enemy = new Monster('d', new Position(0, 2), "Slime", new Health(30), 20, 6, 3, 2);
    }

    @Test
    public void testInteractionWithEnemy() {//tests if a unit contacts a unit it brings to a combat
        Assert.assertTrue("Player contacting with Monster should start a combat", unit.interact(enemy).contains("Amit engaged in combat with Slime"));
    }

    @Before
    public void setupWallInteractionTest() {//tests if a unit contacts a wall nothing happens
        unit = new Rogue(new Position(0, 0), "Amit", new Health(150), 30, 2, 2);
        wall = new Wall(new Position(0, 1));
    }

    @Test
    public void testInteractionWithWall() {
        Assert.assertTrue("nothing should happen", unit.interact(wall).isEmpty());
    }

    @Before
    public void setupKillingTest() {
        unit = new Mage(new Position(0, 0), "Amit", new Health(150), 30, 2, 2,50,50,20,5);
        enemy = new Monster('d', new Position(0, 2), "Slime", new Health(30), 20, 6, 3, 2);
    }

    @Test
    public void testKill() {
        enemy.kill(unit);
        Assert.assertEquals("Player Died", unit.getTileType(), 'X');
    }

}
