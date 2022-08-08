package Backend.Enemies;

import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

public class Monster extends Enemy {

    private int visionRange;


    /**
     *
     * @param tileType the monster symbol on board
     * @param position the monsters position on board
     * @param name the monsters name
     * @param monsterHealth the HP of the monster
     * @param atk the attack of the monster
     * @param def the defense of the mosnter
     * @param exp the exp Value of the monster
     * @param visionRange monsters vision Range
     */
    public Monster(char tileType, Position position, String name, Health monsterHealth, int atk, int def, int exp, int visionRange) {
        super(tileType, position, name, monsterHealth, atk, def, exp);

        this.visionRange = visionRange;
    }

    /**
     *
     * @param pos the position of the player
     * checks if the player is within monsters range
     * @return whether the player is in range
     */
    public boolean playerInRange(Position pos) {
        if (Range(this.getPosition(), pos) <= this.visionRange)
            return true;
        return false;
    }


    /**
     *
     * @param playerPosition the players position
     * checks whether the player is within range and acts accordingly
     * @return the place on board the monster moves to
     */
    @Override
    public Position gameTick(Position playerPosition) {
        Position moveTo;

        if (this.playerInRange(playerPosition))
            return moveTo = this.movement(playerPosition);
        return moveTo = this.randomMove();
    }

    /**
     *
     * while fighting the monster, gives the updated status of the monster
     * @return updated status of the monster
     */
    @Override
    public String describe() {
        return name + "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() + "\t Attack: " + atk + "\t Defense: " + def
                + "\t Experience Value: " + experienceValue + "\tVision Range: " + visionRange + "\n";

    }
}
