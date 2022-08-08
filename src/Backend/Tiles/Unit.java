package Backend.Tiles;

import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Visitors.UnitVisited;
import Backend.Visitors.UnitVisitor;

import java.util.Random;

public abstract class Unit extends Tile implements UnitVisited, UnitVisitor {


    protected String name;
    protected int atk;
    protected int def;
    public Health health;
    public boolean isAlive;

    /**
     * @param tileType   = the symbol of the tile on board
     * @param position   = the position of the tile on board
     * @param name       = the name of the tile
     * @param unitHealth = the health amount of the unit
     * @param atk        = units attack
     * @param def        = units defense
     */
    public Unit(char tileType, Position position, String name, Health unitHealth, int atk, int def) {
        super(tileType, position);

        this.name = name;
        this.atk = atk;
        this.def = def;
        this.health = unitHealth;
        this.isAlive = true;

    }

    /**
     * @return units position
     */
    public Position getUnitPosition() {
        return this.position;
    }


    /**
     * @param empty interaction of unit with an empty tile
     */
    @Override
    public String interact(Empty empty) {
        Position changePosition = this.getPosition();
        this.setPosition(empty.getPosition());
        empty.setPosition(changePosition);
        return "";
    }

    /**
     * @param wall = interaction of unit with a wall
     */
    @Override
    public String interact(Wall wall) {
        return "";
    }

    /**
     * @return the units name
     */
    public String getName() {
        return name;
    }

    public abstract String describe();


    public String kill(Unit unit) {
        return unit.acceptKill(this);
    }

    /**
     * @return this units def
     */
    public int getDef() {
        return this.def;
    }


    /**
     * @param defender the unit the defends itself
     * @return combatSummary
     */
    public String attack(Unit defender) {
        Random diceRoll = new Random();
        int attackRoll = diceRoll.nextInt(this.atk + 1);
        int defenseRoll = diceRoll.nextInt(defender.def + 1);
        int dmgTaken = attackRoll - defenseRoll;
        String combatSummary = this.getName() + " engaged in combat with " + defender.getName() + ".\n" + this.describe()
                + defender.describe() + this.getName() + " rolled " + attackRoll + " attack points.\n"
                + defender.getName() + " rolled " + defenseRoll + " defense points.\n";

        if (dmgTaken < 0) {
            dmgTaken = 0;
        }
        defender.health.takeDmg(dmgTaken);
        combatSummary += this.getName() + " dealt " + dmgTaken + " damage to " + defender.getName() + ".\n";
        if (defender.health.getHealthAmount() < 1) {
            combatSummary += this.kill(defender);
            this.setPosition(defender.getPosition());
        }
        return combatSummary;
    }

}