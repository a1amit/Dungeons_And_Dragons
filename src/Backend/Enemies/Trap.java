package Backend.Enemies;

import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

public class Trap extends Enemy {

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;


    /**
     *
     * @param tileType Symbol of the Trap
     * @param position position of the Trap
     * @param name the name of the Trap
     * @param trapHealth the traps health
     * @param atk the traps attack
     * @param def the defense of the trap
     * @param exp the expValue of the trap
     * @param visibilityTime the Traps' visibility time
     * @param invisibilityTime the Traps' invisibility time
     */
    public Trap(char tileType, Position position, String name, Health trapHealth, int atk, int def, int exp, int visibilityTime, int invisibilityTime) {
        super (tileType, position, name, trapHealth, atk, def, exp);

        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;

    }

    /**
     * change visibility if needed
     * checks whether the player is within traps range
     */
    @Override
    public Position gameTick(Position playerPosition) {
        boolean playerInRange = false;
        this.ticksCount += 1;
        if (visible && ticksCount == visibilityTime) {
            this.ticksCount = 0;
            this.visible = false;
        }
        else if (!visible && ticksCount == invisibilityTime) {
            this.ticksCount = 0;
            this.visible = true;
        }
        if(Range(this.position, playerPosition) < 2) {
            playerInRange = true;
        }
        return this.getPosition();
    }

    /**
     *
     * @return Traps representation on the board
     */
    @Override
    public String toString() {
        if(visible)
            return "" + this.tileType;
        return ".";
    }

    /**
     *
     * Describes the trap while engaging in combat
     */
    @Override
    public String describe() {
        return name + "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() +  "\t Attack: " + atk
                + "\t Defense: " + def + "\t Experience Value: " + experienceValue + "\n";
    }

}
