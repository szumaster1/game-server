package core.game.node.item;

import core.game.node.entity.npc.drop.DropFrequency;
import core.tools.RandomFunction;

public final class ChanceItem extends Item {

    public static final int[] DROP_RATES = {5, 15, 150, 750};

    private double chanceRate;

    private int minimumAmount;

    private int maximumAmount;

    private int tableSlot;

    private DropFrequency dropFrequency;

    private int setRate = -1;

    public ChanceItem(int id) {
        this(id, 1, 1, 1000, 1.0);
    }

    public ChanceItem(int id, int minimumAmount, double chanceRate) {
        this(id, minimumAmount, minimumAmount, 1000, chanceRate);
    }

    public ChanceItem(int id, int minimumAmount, int chanceRate) {
        this(id, minimumAmount, minimumAmount, 1000, (double) chanceRate);
    }

    public ChanceItem(int id, int minimumAmount, int maximumAmount, double chanceRate) {
        this(id, minimumAmount, maximumAmount, 1000, chanceRate);
    }

    public ChanceItem(int id, int minimumAmount, int maximumAmount, DropFrequency frequency) {
        this(id, minimumAmount, maximumAmount, 1000, DropFrequency.rate(frequency));
    }

    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate) {
        this(id, minimumAmount, maximumAmount, charge, chanceRate, DropFrequency.UNCOMMON);
    }

    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate, DropFrequency frequency) {
        this(id, minimumAmount, maximumAmount, charge, chanceRate, frequency, -1);
    }

    public ChanceItem(int id, int minimumAmount, int maximumAmount, int charge, double chanceRate, DropFrequency frequency, int setRate) {
        super(id, minimumAmount, charge);
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.chanceRate = chanceRate;
        this.dropFrequency = frequency;
        this.setRate = setRate;
    }

    public ChanceItem(int id, int amount, DropFrequency frequency) {
        this(id, amount, amount, frequency);
    }

    public Item getRandomItem() {
        if (minimumAmount == maximumAmount) {
            return new Item(getId(), minimumAmount);
        }
        return new Item(getId(), RandomFunction.random(minimumAmount, maximumAmount));
    }

    public static ChanceItem getItem(ChanceItem... table) {
        return getItem(RandomFunction.getRandomDouble(75.0), table);
    }

    public static ChanceItem getItem(double chance, ChanceItem... table) {
        // TODO:
        return table[0];
    }

    public ChanceItem getCopy() {
        ChanceItem newItem = new ChanceItem(getId(), minimumAmount, maximumAmount, getCharge(), chanceRate, dropFrequency);
        return newItem;
    }

    public double getChanceRate() {
        return chanceRate;
    }

    public void setChanceRate(double chanceRate) {
        this.chanceRate = chanceRate;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public int getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(int maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public DropFrequency getDropFrequency() {
        return dropFrequency;
    }

    public void setDropFrequency(DropFrequency dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    public int getTableSlot() {
        return tableSlot;
    }

    public void setTableSlot(int tableSlot) {
        this.tableSlot = tableSlot;
    }

    @Override
    public String toString() {
        return "ChanceItem " + super.toString() + " [chanceRate=" + chanceRate + ", minimumAmount=" + minimumAmount + ", maximumAmount=" + maximumAmount + ", tableSlot=" + tableSlot + ", dropFrequency=" + dropFrequency + "]";
    }

    public int getSetRate() {
        return setRate;
    }

    public void setSetRate(int setRate) {
        this.setRate = setRate;
    }
}
