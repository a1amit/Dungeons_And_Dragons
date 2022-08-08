package Backend.Characters;

import Backend.Enemies.Enemy;
import Backend.Characters.RelatedAttributes.Health;
import Backend.Characters.RelatedAttributes.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {

    /**
     * @param manaPool Integer, holds the maximal value of mana.
     * @param currentMana Integer, current amount of mana. Initially manaPool / 4
     * @param manaCost Integer, ability cost
     * @param hitsCount Integer, maximal number of times a single cast of the ability can hit.
     */
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int hitsCount;

    private List<Enemy> enemiesInRange = new ArrayList<Enemy>(); // a list of enemies in hit range

    public Mage(Position position, String name, Health mageHealth, int atk, int def, int spellPower, int manaPool, int manaCost, int hitsCount, int abilityRange) {
        super(position, name, mageHealth, atk, def);

        this.abilityDamage = spellPower;
        this.abilityRange = abilityRange;
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.hitsCount = hitsCount;
        this.abilityName = "Blizzard";

    }

    /**
     * @param manaAdded the mana to add to the currentMana param.
     *                  can't surpass the manaPool param
     */
    public void increaseMana(int manaAdded) {
        this.currentMana = Math.min(currentMana + manaAdded, manaPool);
    }

    /**
     * Mages special attributes upon leveling up
     */
    @Override
    public String lvlUp() {
        int[] newStats = playerLevelUp();
        this.manaPool += 25 * playerLevel;
        this.increaseMana(manaPool / 4);
        this.abilityDamage += 10 * playerLevel;
        return (
                this.getName() + " reached level: " + playerLevel +
                        " Health: " + (newStats[0] + 5 * playerLevel) +
                        " Attack: " + (newStats[1] + 2 * playerLevel) +
                        " Defense: " + (newStats[2] + playerLevel) +
                        " Maximum Mana: " + 25 * playerLevel +
                        " Spell Power: " + 10 * playerLevel
                        + "\n");
    }

    /**
     * on game tick: current mana ← min(mana pool, current mana + 1 × level)
     */
    @Override
    public void gameTick() {
        increaseMana(playerLevel);
    }

    /**
     * Special ability: Blizzard, randomly hit enemies within range for an amount equals to the mage’s
     * spell power at the cost of mana.
     * The mage cannot cast the ability if current mana < cost.
     * On ability cast:
     * current mana ← current mana − cost
     * hits ← 0
     * while (hits < hits count) ∧ (∃ living enemy s.t. range(enemy, player) < ability range) do
     * - Select random enemy within ability range.
     * - Deal damage (reduce health value) to the chosen enemy for an amount equal to spell power
     * (each enemy may try to defend itself).
     * - hits ← hits + 1
     */
    @Override
    public String castAbility() {
        if (currentMana < manaCost) {
            return "Can't cast " + abilityName + ", " + (manaCost - currentMana) + " more mana needed\n";
        } else {
            StringBuilder output = new StringBuilder(this.getName() + " cast " + this.abilityName + ".\n");
            this.currentMana -= manaCost;
            int hits = 0;
            for (Enemy enemy : this.getAllEnemies()) {
                if (this.Range(this.getPosition(), enemy.getPosition()) < this.abilityRange)
                    enemiesInRange.add(enemy);
            }
            Random rand = new Random();
            if (enemiesInRange.isEmpty())
                return output.toString();

            do {
                Enemy defender = enemiesInRange.get(rand.nextInt(enemiesInRange.size()));
                int defenseRoll = rand.nextInt(defender.getDef() + 1);
                int healthDamage = this.abilityDamage - defenseRoll;
                if (healthDamage < 0)
                    healthDamage = 0;
                defender.health.takeDmg(healthDamage);
                output.append(defender.getName()).append(" rolled ").append(defenseRoll).append(" defense points.\n").append(this.getName())
                        .append(" hit ").append(defender.getName()).append(" for ").append(this.abilityDamage).append(" ability damage.\n");

                if (defender.health.getHealthAmount() == 0) {
                    enemiesInRange.remove(defender);
                    output.append(this.kill(defender));
                }

                hits += 1;
            } while (hits < hitsCount && !enemiesInRange.isEmpty());

            return output.toString();
        }
    }

    /**
     * @return detailed description of the Mage current state
     */
    @Override
    public String describe() {
        return name +
                "\t Health: " + health.getHealthAmount() + "/" + health.getHealthPool() +
                "\t Attack: " + atk +
                "\t Defense: " + def +
                "\t Level: " + playerLevel +
                "\t Experience Value: " + experience + "/" + (50 * playerLevel) +
                "\t Mana: " + currentMana + "/" + manaPool +
                "\t Spell Power: " + abilityDamage + "\n";

    }

    @Override
    public String interact(Player player) {
        return null;
    }
}