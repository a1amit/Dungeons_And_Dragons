package Backend.Characters;

import Backend.Enemies.Enemy;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

import java.util.Random;

public class Hunter extends Player {

    /**
     * @param range: shooting range, received as a constructor argument.
     * @param arrows count:  current amount of arrows.
     * @param ticksCount: initially 0.
     */
    private int range;
    private int arrowsCount;
    private int ticksCount;
    private Enemy closestEnemy;

    public Hunter(Position position, String name, Health playerHealth, int atk, int def, int range) {
        super(position, name, playerHealth, atk, def);
        this.range = range;
        this.arrowsCount = 10;
        ticksCount = 0;
        this.abilityName = "Shoot";
    }


    /**
     * Special ability: Shoot, hits the closest enemy for an amount equals to the hunter’s attack points at the cost of an arrow.
     * Using arrows as resource. Starting amount of arrows in quiver is equals to (10 × level).
     * The hunter cannot cast the ability if arrows count = 0 ∨ ∄ enemy s.t. range(enemy, player) ≤ range.
     * On ability cast:
     * - arrows count ← arrows count − 1
     * - Deal damage equals to attack points to the closest enemy within range (The enemy will try to
     * defend itself).
     */
    @Override
    public String castAbility() {
        double minRange = 100;
        double closestEnemyRange;

        if (this.arrowsCount == 0)
            return "Can not cast " + this.abilityName + ". " + this.getName() + " Does not have any arrows.\n";
        else {

            for (Enemy enemy : this.getAllEnemies()) {
                closestEnemyRange = this.Range(enemy.getPosition(), this.getPosition());
                if (closestEnemyRange < minRange) {
                    minRange = closestEnemyRange;
                    closestEnemy = enemy;
                }
            }
            if (minRange > this.range)
                return "Can not cast " + this.abilityName + ". Closest enemy is farther then vision-range.\n";
            String output = this.getName() + " fired an arrow at " + closestEnemy.getName() + "\n";
            this.arrowsCount -= 1;
            Random rand = new Random();
            int defenseRoll = rand.nextInt(closestEnemy.getDef() + 1);
            int healthDamage = this.atk - defenseRoll;
            closestEnemy.health.takeDmg(healthDamage);

            output += closestEnemy.getName() + " rolled " + defenseRoll + " defence points.\n" +
                    this.getName() + " hit " + closestEnemy.getName() + " for " + this.atk + " ability damage.\n";

            if (closestEnemy.health.getHealthAmount() == 0)
                output += this.kill(closestEnemy);

            return output;
        }
    }

    /**
     * On game tick:
     * if ticks count == 10 then
     * arrows count ← arrows count + level
     * ticks count ← 0
     * else
     * ticks count ← ticks count + 1
     */
    @Override
    public void gameTick() {
        if (this.ticksCount == 10) {
            this.arrowsCount += 10;
            this.ticksCount = 0;
        } else this.ticksCount += 1;
    }

    /**
     * Hunters special attributes upon leveling up
     */
    @Override
    public String lvlUp() {
        int[] newStats = playerLevelUp();
        this.arrowsCount += 10 * playerLevel;
        this.atk += 2 * playerLevel;
        this.def += playerLevel;
        return (
                this.getName() + " reached level: " + playerLevel +
                        " Health: " + (newStats[0] + 5 * playerLevel) +
                        " Attack: " + (newStats[1] + 2 * playerLevel) +
                        " Defense: " + (newStats[2] + playerLevel) +
                        "\n");
    }

    /**
     * @return detailed description of the Hunter current state
     */
    @Override
    public String describe() {
        return (name +
                "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() +
                "\t Attack: " + atk +
                "\t Defense: " + def +
                "\t Level:" + playerLevel +
                "\t Experience Value: " + experience + "/" + (50 * playerLevel) +
                "\t Arrows: " + arrowsCount +
                "\t Range: " + range + "\n"
        );
    }

    @Override
    public String interact(Player player) {
        return null;
    }
}