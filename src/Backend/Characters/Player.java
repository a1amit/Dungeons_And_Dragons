package Backend.Characters;

import Backend.Enemies.Enemy;
import Backend.Visitors.TileVisitor;
import Backend.Visitors.UnitVisitor;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;
import Backend.Tiles.Unit;

import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {

    /**
     * @param START_EXPERIENCE each player starts with 0 exp
     * @param START_PLAYER_LEVEL each player starts on level 1
     */
    private final static int START_EXPERIENCE = 0;
    private final static int START_PLAYER_LEVEL = 1;

    protected int experience;
    protected int playerLevel;

    /**
     * @param abilityName each player has a special ability
     * @param abilityDamage the ability's damage
     * @param abilityRange the ability's range
     * @param allEnemies the ability's range
     */
    protected String abilityName;
    protected int abilityDamage;
    protected int abilityRange;
    protected List<Enemy> allEnemies;

    public Player(Position position, String name, Health playerHealth, int attackPoints, int defencePoints) {
        super('@', position, name, playerHealth, attackPoints, defencePoints);

        this.experience = START_EXPERIENCE;
        this.playerLevel = START_PLAYER_LEVEL;
    }


    /**
     * every player base attributes upon Leveling up
     */
    public int[] playerLevelUp() {
        int[] newStats = new int[3];
        this.experience -= 50 * playerLevel;
        this.playerLevel += 1;
        newStats[0] = this.health.healthLevelingUp(playerLevel);
        newStats[1] = this.atk += 4 * playerLevel;
        newStats[2] = this.def += playerLevel;
        return newStats;
    }

    /**
     * @param enemyKilled kills the enemy and gives the player exp
     */
    @Override
    public String kill(Enemy enemyKilled) {
        int experienceGained = enemyKilled.getExperienceValue();
        String killSummary = enemyKilled.getName() + " died. " + this.name + " gained " + experienceGained + " experience.\n";
        this.experience += experienceGained;

        if (this.experience >= 50 * playerLevel) {
            killSummary += this.lvlUp();
        }

        enemyKilled.isAlive = false;
        return killSummary;
    }


    @Override
    public String acceptKill(UnitVisitor visitor) {
        return visitor.kill(this);
    }

    /**
     * if the player dies, he's symbolized by an x
     */
    public void died() {
        this.tileType = 'X';
        this.isAlive = false;
    }

    @Override
    public String kill(Player player) {
        return "";
    }

    @Override
    public String accept(TileVisitor tileVisitor) {
        return tileVisitor.interact(this);
    }

    public abstract String castAbility();

    public abstract void gameTick();

    public abstract String lvlUp();

    public List<Enemy> getAllEnemies() {
        return allEnemies;
    }

    public void setAllEnemies(List<Enemy> allEnemies) {
        this.allEnemies = allEnemies;
    }

    public String interact(Enemy enemy) {
        return this.attack(enemy);
    }
}