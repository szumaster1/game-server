package core.game.node.item;

import core.tools.RandomFunction;

/**
 * Weighted chance item.
 */
public class WeightedChanceItem {

    /**
     * The Id.
     */
    int id,
    /**
     * The Minimum amount.
     */
    minimum_amount,
    /**
     * The Maximum amount.
     */
    maximum_amount;
    /**
     * The Weight.
     */
    public int weight;

    /**
     * Constructs a new Weighted chance item.
     *
     * @param id             the id
     * @param minimum_amount the minimum amount
     * @param maximum_amount the maximum amount
     * @param weight         the weight
     */
    public WeightedChanceItem(int id, int minimum_amount, int maximum_amount, int weight) {
        this.id = id;
        this.minimum_amount = minimum_amount;
        this.maximum_amount = maximum_amount;
        this.weight = weight;
    }

    /**
     * Constructs a new Weighted chance item.
     *
     * @param id     the id
     * @param amount the amount
     * @param weight the weight
     */
    public WeightedChanceItem(int id, int amount, int weight) {
        this(id, amount, amount, weight);
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public Item getItem() {
        return new Item(this.id, RandomFunction.random(this.minimum_amount, this.maximum_amount));
    }
}
