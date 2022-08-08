package Backend.Characters;

import Backend.Enemies.Enemy;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {

    private int abilityCoolDown;
    private int remainingCoolDown;
    private int abilityHeal;
    private List<Enemy> enemiesWithinRange = new ArrayList<Enemy>();


    /**
     * @param position        the position of the warrior on board
     * @param name            the name of the warrior
     * @param health          the HP of the warrior
     * @param atk             warriors attack
     * @param def             warriors defense
     * @param abilityCoolDown warriors ability coolDown
     */
    public Warrior(Position position, String name, Health health, int atk, int def, int abilityCoolDown) {
        super(position, name, health, atk, def);

        this.abilityCoolDown = abilityCoolDown;
        this.remainingCoolDown = 0;
        this.abilityDamage = (int) (this.health.getHealthPool() * 0.1);
        this.abilityRange = 3;
        this.abilityHeal = 10 * def;
        this.abilityName = "Avenger's Shield";
    }


    /**
     * Warriors special attributes upon leveling up
     */
    @Override
    public String lvlUp() {
        int[] newStats = playerLevelUp();
        this.remainingCoolDown = 0;
        this.health.increaseHealthPool(5 * playerLevel);
        this.health.setHealthAmount(this.health.getHealthPool());

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
     * in each turn the warriors' CoolDown is decreased by 1 in case he used his ability
     */
    @Override
    public void gameTick() {
        if (remainingCoolDown > 0)
            this.remainingCoolDown -= 1;
    }

    /**
     * Special ability: Avenger’s Shield, randomly hits one enemy withing range < 3 for an amount
     * equals to 10% of the warrior’s max health and heals the warrior for amount equals to (10×defense)
     * (but will not exceed the total amount of health pool).
     * The warrior’s ability has a cooldown, meaning it can only be used once every ability cooldown
     * game ticks.
     * The warrior cannot cast the ability if remaining cooldown > 0.
     * On ability cast:
     * - remaining cooldown ← ability cooldown
     * - current health ← min (current health + (10 × defense), health pool)
     * - Randomly hits one enemy within range < 3 for an amount equals to 10% of the warrior’s
     * health pool
     */
    @Override
    public String castAbility() {
        if (remainingCoolDown > 0) {
            return name + "tried to use " +
                    abilityName + ", " +
                    remainingCoolDown + "/" + abilityCoolDown +
                    " turns left until can cast again";
        } else {
            int healed = Math.min(this.health.getHealthAmount() + this.def * 10, this.health.getHealthPool());
            String output = this.getName() + " used " + this.abilityName + ", healing for " + (healed - health.getHealthAmount()) + ".\n";
            this.health.increaseHealthAmount(healed - health.getHealthAmount());
            this.health.increaseHealthAmount(this.def * 10);
            this.remainingCoolDown = abilityCoolDown + 1;
            for (Enemy enemy : this.getAllEnemies()) {
                if (Range(this.getPosition(), enemy.getPosition()) < 3)
                    this.enemiesWithinRange.add(enemy);
            }

            Random rand = new Random();
            if (enemiesWithinRange.isEmpty())
                return output;

            Enemy defender = enemiesWithinRange.get(rand.nextInt(enemiesWithinRange.size()));
            int defenseRoll = rand.nextInt(defender.getDef() + 1);
            int healthDamage = this.abilityDamage - defenseRoll;
            if (healthDamage < 0)
                healthDamage = 0;
            defender.health.takeDmg(healthDamage);

            output += defender.getName() +
                    " rolled " + defenseRoll + " defense points.\n" +
                    this.getName() + " hit " + defender.getName() + " for " + this.abilityDamage + " ability damage.\n";
            if (defender.health.getHealthAmount() == 0)
                output += this.kill(defender);
            return output;
        }
    }


    /**
     * @return detailed description of the Warriors current state
     */
    @Override
    public String describe() {
        return name +
                "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() +
                "\t Attack: " + atk +
                "\t Defense: " + def +
                "\t Level:" + playerLevel +
                "\t Experience Value: " + experience + "/" + (50 * playerLevel) +
                "\t Cooldown: " + remainingCoolDown + "/" + abilityCoolDown + "\n";
    }

    @Override
    public String interact(Player player) {
        return null;
    }
}
