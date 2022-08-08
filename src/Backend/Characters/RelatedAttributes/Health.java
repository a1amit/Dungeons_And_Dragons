package Backend.Characters.RelatedAttributes;

public class Health {


    /**
     * @param healthAmount current health
     * @param healthPool max health
     */
    protected int healthAmount;
    protected int healthPool;

    public Health(int healthPool) {
        this.healthPool = healthPool;
        this.healthAmount = healthPool;
    }


    /**
     * @param level the lvl of the player
     * @return the new health value upon leveling up
     */
    public int healthLevelingUp(int level) {
        this.healthPool += 10 * level;
        this.healthAmount = this.healthPool;
        return 10 * level;
    }

    public void setHealthAmount(int healthAmount) {
        this.healthAmount = healthAmount;
    }

    public int getHealthPool() {
        return healthPool;
    }


    public void increaseHealthAmount(int healthAdded) {
        this.healthAmount = Math.min(this.healthAmount + healthAdded, healthPool);
    }

    // limits the minimum amount to 0
    public void takeDmg(int healthLost) {
        this.healthAmount -= healthLost;
        if (healthAmount < 0)
            healthAmount = 0;
    }

    /**
     *
     * @param healthAdded health to add
     * increases the max healthPool
     */
    public void increaseHealthPool(int healthAdded) {
        this.healthPool += healthAdded;

    }

    public int getHealthAmount() {
        return healthAmount;
    }
}
