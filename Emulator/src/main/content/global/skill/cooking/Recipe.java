package content.global.skill.cooking;

import content.global.skill.cooking.cake.ChocolateCake;
import content.global.skill.cooking.meat.OomlieWrap;
import content.global.skill.cooking.pizza.impl.AnchovyPizza;
import content.global.skill.cooking.pizza.impl.MeatPizza;
import content.global.skill.cooking.pizza.impl.PineapplePizza;
import content.global.skill.cooking.pizza.impl.PlainPizza;
import content.global.skill.cooking.stew.CurryRecipe;
import content.global.skill.cooking.stew.StewRecipe;
import content.global.skill.cooking.pie.impl.*;
import content.global.skill.cooking.potato.impl.*;
import content.global.skill.cooking.topping.impl.*;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

/**
 * Recipe.
 */
public abstract class Recipe {

    /**
     * The recipes.
     */
    public static final Recipe[] RECIPES = new Recipe[]{
        new RedberryPie(), new MeatPie(), new ApplePie(), new MudPie(), new GardenPie(), new FishPie(), new AdmiralPie(), new WildPie(), new SummerPie(),
        new StewRecipe(), new CurryRecipe(),
        new PlainPizza(), new MeatPizza(), new AnchovyPizza(), new PineapplePizza(),
        new ChocolateCake(),
        new ButterPotato(), new ChilliPotato(), new CheesePotato(), new EggPotato(), new MushroomPotato(), new TunaPotato(),
        new SpicySauce(), new ChilliConCarne(), new UncookedEgg(), new EggAndTomato(), new MushroomAndOnion(), new ChoppedOnion(), new SlicedMushroom(), new ChoppedTuna(), new TunaAndCorn(), new OomlieWrap()
    };

    /**
     * Gets base.
     *
     * @return the base
     */
    public abstract Item getBase();

    /**
     * Gets product.
     *
     * @return the product
     */
    public abstract Item getProduct();

    /**
     * Get ingredients item.
     *
     * @return the item.
     */
    public abstract Item[] getIngredients();

    /**
     * Get parts item.
     *
     * @return the item.
     */
    public abstract Item[] getParts();

    /**
     * Gets mix message.
     *
     * @param event the event
     * @return the mix message
     */
    public abstract String getMixMessage(final NodeUsageEvent event);

    /**
     * Is singular boolean.
     *
     * @return the boolean
     */
    public abstract boolean isSingular();

    /**
     * Mix.
     *
     * @param player the player
     * @param event  the event
     */
    public void mix(final Player player, final NodeUsageEvent event) {
        if (getIngredients().length == 1) {
            singleMix(player, event);
        } else {
            multipleMix(player, event);
        }
    }

    /**
     * Single mix.
     *
     * @param player the player
     * @param event  the event
     */
    public void singleMix(final Player player, final NodeUsageEvent event) {
        if (player.getInventory().remove(event.getBaseItem()) && player.getInventory().remove(event.getUsedItem())) {
            player.getInventory().add(getProduct());
            String message = getMixMessage(event);
            if (message != null) {
                player.getPacketDispatch().sendMessage(message);
            }
        }
    }

    /**
     * Multiple mix.
     *
     * @param player the player
     * @param event  the event
     */
    public void multipleMix(final Player player, final NodeUsageEvent event) {
        Item item = null;
        int index = -1;
        for (int counter = 0; counter < getIngredients().length; counter++) {
            item = getIngredients()[counter];
            if (item.getId() == event.getUsedItem().getId() || item.getId() == event.getBaseItem().getId()) {
                index = counter;
                break;
            }
        }
        if (index != -1) {
            if (player.getInventory().remove(event.getBaseItem()) && player.getInventory().remove(event.getUsedItem())) {
                player.getInventory().add(getParts()[index + 1]);
                String message = getMixMessage(event);
                if (message != null) {
                    player.getPacketDispatch().sendMessage(message);
                }
            }
        }
    }
}
