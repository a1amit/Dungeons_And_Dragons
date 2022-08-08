package Backend.Enemies;

import Backend.Characters.Player;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Visitors.TileVisitor;
import Backend.Visitors.UnitVisitor;
import Backend.Tiles.Unit;

import java.util.Random;

public abstract class Enemy extends Unit {

    protected boolean playerInRange;
    protected int experienceValue;


    /**
     * @param tileType        the symbol of the enemy on board
     * @param position        the position of the enemy on board
     * @param name            the name of the enemy
     * @param health          the enemy's health
     * @param atk             the enemy's attack
     * @param def             the enemy's defense
     * @param experienceValue the enemy's exp value
     */
    public Enemy(char tileType, Position position, String name, Health health, int atk, int def, int experienceValue) {
        super(tileType, position, name, health, atk, def);

        this.experienceValue = experienceValue;

    }

    /**
     * @return the exp value of the monster
     */
    public int getExperienceValue() {
        return experienceValue;
    }


    /**
     * @param playerPosition the position of the player
     * @return the enemy's movement
     */
    public abstract Position gameTick(Position playerPosition);

    public Position movement(Position playerPosition) {
        Position moveTo = new Position(this.getPosition().getX(), this.getPosition().getY());
        int dx = this.position.getX() - playerPosition.getX();
        int dy = this.position.getY() - playerPosition.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            playerInRange = true;
            if (dx > 0) {
                moveTo.moveUp();
            } else
                moveTo.moveDown();
        } else {
            if (dy > 0) {
                moveTo.moveLeft();
            } else
                moveTo.moveRight();
        }
        return moveTo;
    }

    /**
     * if the the player is not within monsters range, the monster will make a random move
     */
    public Position randomMove() {
        Position moveTo = new Position(this.getPosition().getX(), this.getPosition().getY());
        playerInRange = false;
        Random rand = new Random();
        int randomMove = rand.nextInt(5);
        switch (randomMove) {
            case 0 -> moveTo.moveUp();
            case 1 -> moveTo.moveDown();
            case 2 -> moveTo.moveRight();
            case 3 -> moveTo.moveLeft();
            default -> {
            }
        }
        return moveTo;
    }

    public String interact(Enemy enemy) {
        return "";
    }

    public String interact(Player player) {
        return this.attack(player);
    }


    /**
     * @param player the player that the monster killed
     * @return kill summary
     */
    @Override
    public String kill(Player player) {
        String killSummary = player.getName() + " was killed by " + this.name + ".\n"
                + "You lost.\n";

        player.died();
        return killSummary;
    }

    @Override
    public String accept(TileVisitor tileVisitor) {
        return tileVisitor.interact(this);
    }

    @Override
    public String acceptKill(UnitVisitor visitor) {
        return visitor.kill(this);
    }

    @Override
    public String kill(Enemy enemy) {
        return "";
    }

}
