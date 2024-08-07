package core.game.node.item;

import core.game.node.entity.npc.drop.DropFrequency;
import core.tools.RandomFunction;

/**
 * Chance item.
 */
public final class ChanceItem extends Item {

    /**
     * The constant DROP_RATES.
     */
    public static final int[] DROP_RATES = {5, 15, 150, 750};

    private double chanceRate;

    private int minimumAmount;

    private int maximumAmount;

    private int tableSlot;

    private DropFrequency dropFrequency;

    private int setRate = -1;

    /**
     * Instantiates a new Chance item.
     *
     * @param id the id
     */
    public ChanceItem(int id) {
        this(id, 1, 1, 1000, 1.0);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param chanceRate    the chance rate
     */
    public ChanceItem(int id, int minimumAmount, double chanceRate) {
        this(id, minimumAmount, minimumAmount, 1000, chanceRate);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param chanceRate    the chance rate
     */
    public ChanceItem(int id, int minimumAmount, int chanceRate) {
        this(id, minimumAmount, minimumAmount, 1000, (double) chanceRate);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param maximumAmount the maximum amount
     * @param chanceRate    the chance rate
     */
    public ChanceItem(int id, int minimumAmount, int maximumAmount, double chanceRate) {
        this(id, minimumAmount, maximumAmount, 1000, chanceRate);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param maximumAmount the maximum amount
     * @param frequency     the frequency
     */
    public ChanceItem(int id, int minimumAmount, int maximumAmount, DropFrequency frequency) {
        this(id, minimumAmount, maximumAmount, 1000, DropFrequency.rate(frequency));
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param maximumAmount the maximum amount
     * @param charge        the charge
     * @param chanceRate    the chance rate
     */
    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate) {
        this(id, minimumAmount, maximumAmount, charge, chanceRate, DropFrequency.UNCOMMON);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param maximumAmount the maximum amount
     * @param charge        the charge
     * @param chanceRate    the chance rate
     * @param frequency     the frequency
     */
    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate, DropFrequency frequency) {
        this(id, minimumAmount, maximumAmount, charge, chanceRate, frequency, -1);
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id            the id
     * @param minimumAmount the minimum amount
     * @param maximumAmount the maximum amount
     * @param charge        the charge
     * @param chanceRate    the chance rate
     * @param frequency     the frequency
     * @param setRate       the set rate
     */
    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate, DropFrequency frequency, int setRate) {
        super(id, minimumAmount, charge);
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.chanceRate = chanceRate;
        this.dropFrequency = frequency;
        this.setRate = setRate;
    }

    /**
     * Instantiates a new Chance item.
     *
     * @param id        the id
     * @param amount    the amount
     * @param frequency the frequency
     */
    public ChanceItem(int id, int amount, DropFrequency frequency) {
        this(id, amount, amount, frequency);
    }

    /**
     * Gets random item.
     *
     * @return the random item
     */
    public Item getRandomItem() {
        if (minimumAmount == maximumAmount) {
            return new Item(getId(), minimumAmount);
        }
        return new Item(getId(), RandomFunction.random(minimumAmount, maximumAmount));
    }

    /**
     * Gets item.
     *
     * @param table the table
     * @return the item
     */
    public static ChanceItem getItem(ChanceItem... table) {
        return getItem(RandomFunction.getRandomDouble(75.0), table);
    }

    /**
     * Gets item.
     *
     * @param chance the chance
     * @param table  the table
     * @return the item
     */
    public static ChanceItem getItem(double chance, ChanceItem... table) {
        // TODO:
        return table[0];
    }

    /**
     * Gets copy.
     *
     * @return the copy
     */
    public ChanceItem getCopy() {
        ChanceItem newItem = new ChanceItem(getId(), minimumAmount, maximumAmount, getCharge(), chanceRate, dropFrequency);
        return newItem;
    }

    /**
     * Gets chance rate.
     *
     * @return the chance rate
     */
    public double getChanceRate() {
        return chanceRate;
    }

    /**
     * Sets chance rate.
     *
     * @param chanceRate the chance rate
     */
    public void setChanceRate(double chanceRate) {
        this.chanceRate = chanceRate;
    }

    /**
     * Gets minimum amount.
     *
     * @return the minimum amount
     */
    public int getMinimumAmount() {
        return minimumAmount;
    }

    /**
     * Sets minimum amount.
     *
     * @param minimumAmount the minimum amount
     */
    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    /**
     * Gets maximum amount.
     *
     * @return the maximum amount
     */
    public int getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Sets maximum amount.
     *
     * @param maximumAmount the maximum amount
     */
    public void setMaximumAmount(int maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    /**
     * Gets drop frequency.
     *
     * @return the drop frequency
     */
    public DropFrequency getDropFrequency() {
        return dropFrequency;
    }

    /**
     * Sets drop frequency.
     *
     * @param dropFrequency the drop frequency
     */
    public void setDropFrequency(DropFrequency dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    /**
     * Gets table slot.
     *
     * @return the table slot
     */
    public int getTableSlot() {
        return tableSlot;
    }

    /**
     * Sets table slot.
     *
     * @param tableSlot the table slot
     */
    public void setTableSlot(int tableSlot) {
        this.tableSlot = tableSlot;
    }

    @Override
    public String toString() {
        return "ChanceItem " + super.toString() + " [chanceRate=" + chanceRate + ", minimumAmount=" + minimumAmount + ", maximumAmount=" + maximumAmount + ", tableSlot=" + tableSlot + ", dropFrequency=" + dropFrequency + "]";
    }

    /**
     * Gets set rate.
     *
     * @return the set rate
     */
    public int getSetRate() {
        return setRate;
    }

    /**
     * Sets set rate.
     *
     * @param setRate the set rate
     */
    public void setSetRate(int setRate) {
        this.setRate = setRate;
    }
}
