package content.global.skill.production.cooking.recipe;

import content.global.skill.production.cooking.recipe.cake.ChocolateCake;
import content.global.skill.production.cooking.recipe.pie.impl.*;
import content.global.skill.production.cooking.recipe.pizza.impl.AnchovyPizza;
import content.global.skill.production.cooking.recipe.pizza.impl.MeatPizza;
import content.global.skill.production.cooking.recipe.pizza.impl.PineapplePizza;
import content.global.skill.production.cooking.recipe.pizza.impl.PlainPizza;
import content.global.skill.production.cooking.recipe.potato.impl.*;
import content.global.skill.production.cooking.recipe.stew.CurryRecipe;
import content.global.skill.production.cooking.recipe.stew.StewRecipe;
import content.global.skill.production.cooking.recipe.topping.impl.*;
import core.game.interaction.NodeUsageEvent;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;

public abstract class Recipe {

    public static final Recipe[] RECIPES = new Recipe[]{
        new RedberryPie(), new MeatPie(), new ApplePie(), new MudPie(), new GardenPie(), new FishPie(), new AdmiralPie(), new WildPie(), new SummerPie(),
        new StewRecipe(), new CurryRecipe(),
        new PlainPizza(), new MeatPizza(), new AnchovyPizza(), new PineapplePizza(),
        new ChocolateCake(),
        new ButterPotato(), new ChilliPotato(), new CheesePotato(), new EggPotato(), new MushroomPotato(), new TunaPotato(),
        new SpicySauce(), new ChilliConCarne(), new UncookedEgg(), new EggAndTomato(), new MushroomAndOnion(), new ChoppedOnion(), new SlicedMushroom(), new ChoppedTuna(), new TunaAndCorn(), new OomlieWrap()
    };

    public abstract Item getBase();

    public abstract Item getProduct();

    public abstract Item[] getIngredients();

    public abstract Item[] getParts();

    public abstract String getMixMessage(final NodeUsageEvent event);

    public abstract boolean isSingular();

    public void mix(final Player player, final NodeUsageEvent event) {
        if (getIngredients().length == 1) {
            singleMix(player, event);
        } else {
            multipleMix(player, event);
        }
    }

    public void singleMix(final Player player, final NodeUsageEvent event) {
        if (player.getInventory().remove(event.getBaseItem()) && player.getInventory().remove(event.getUsedItem())) {
            player.getInventory().add(getProduct());
            String message = getMixMessage(event);
            if (message != null) {
                player.getPacketDispatch().sendMessage(message);
            }
        }
    }

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
