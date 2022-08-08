package Backend.Characters;

import Backend.Enemies.Enemy;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

import java.util.Random;

public class Rogue extends Player {


    /**
     * @param MAX_ENERGY each Rouge starts the game with 100 energy
     * @param energyCost the ability cost
     * @param currentEnergy the current energy the character has
     */
    private final static int MAX_ENERGY = 100;
    private int energyCost;
    private int currentEnergy;

    public Rogue(Position position, String name, Health health, int atk, int def, int cost) {
        super(position, name, health, atk, def);
        this.energyCost = cost;
        this.currentEnergy = MAX_ENERGY;
        this.abilityName = "Fan of Knives";
    }


    /**
     * updates the rouge stats upon leveling up
     */
    @Override
    public String lvlUp() {
        int[] newStats = playerLevelUp();
        this.currentEnergy = MAX_ENERGY;
        this.atk += 3 * playerLevel;
        return (
                this.getName() + " reached level: " + playerLevel +
                        " Health: " + (newStats[0] + 5 * playerLevel) +
                        " Attack: " + (newStats[1] + 2 * playerLevel) +
                        " Defense: " + (newStats[2] + playerLevel) +
                        "\n");
    }


    /**
     * the special game tick of the rogue class
     * upon a game tick, the rouge gains 10 energy
     */
    @Override
    public void gameTick() {
        this.currentEnergy = increaseCurrentEnergy(10);
    }


    /**
     * Special ability: Fan of Knives, hits everyone around the rogue for an amount equals to the
     * rogue’s attack points at the cost of energy.
     * The rogue cannot cast the ability if current energy < cost.
     * On ability cast:
     * - current energy ← current energy − cost
     * - For each enemy within range < 2, deal damage (reduce health value) equals to the rogue’s
     * attack points (each enemy will try to defend itself).
     */
    @Override
    public String castAbility() {
        if (this.currentEnergy < this.energyCost) {
            return "Can not cast " + this.abilityName + ", " + (this.energyCost - this.currentEnergy) + " more energy needed";
        } else {
            StringBuilder output = new StringBuilder(this.getName() + " cast " + this.abilityName + ".\n");
            this.currentEnergy -= this.energyCost;
            Random rand = new Random();
            for (Enemy defender : this.getAllEnemies()) {
                if (this.Range(this.getPosition(), defender.getPosition()) < 2) {
                    int defenseRoll = rand.nextInt(defender.getDef() + 1);
                    int healthDamage = this.atk - defenseRoll;
                    defender.health.takeDmg(healthDamage);
                    output.append(defender.getName()).append(" rolled ").append(defenseRoll).append(" defense points.\n")
                            .append(this.getName()).append(" hit ").append(defender.getName()).append(" for ").append(this.atk)
                            .append(" ability damage.\n");
                    if (defender.health.getHealthAmount() == 0)
                        output.append(this.kill(defender));
                }
            }

            return output.toString();
        }
    }


    /**
     * @return detailed description of the Rouge current state
     */
    @Override
    public String describe() {
        return name + "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() +
                "\t Attack: " + atk +
                "\t Defense: " + def +
                "\t Level:" + playerLevel +
                "\t Experience Value: "
                + experience + "/" + (50 * playerLevel) +
                "\t Energy: " + currentEnergy + "/" + MAX_ENERGY +
                "\t Spell Power: " + abilityDamage + "\n";

    }

    /**
     * @param energyAdded the amount of energy to be added upon gameTick.
     *                    the energy amount can't surpass the MAX_ENERGY value
     */
    public int increaseCurrentEnergy(int energyAdded) {
        return Math.min(this.currentEnergy + energyAdded, MAX_ENERGY);
    }

    @Override
    public String interact(Player player) {
        return "";
    }
}