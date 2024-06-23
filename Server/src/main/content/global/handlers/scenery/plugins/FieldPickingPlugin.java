package content.global.handlers.scenery.plugins;

import core.api.consts.Sounds;
import core.cache.def.impl.SceneryDefinition;
import core.game.container.impl.EquipmentContainer;
import core.game.event.ResourceProducedEvent;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;
import core.utilities.RandomFunction;

import static core.api.ContentAPIKt.playAudio;
import static core.api.ContentAPIKt.setAttribute;

/**
 * The Field picking plugin.
 */
@Initializable
public final class FieldPickingPlugin extends OptionHandler {

    private static final Animation ANIMATION = new Animation(827);

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (PickingPlant p : PickingPlant.values()) {
            SceneryDefinition.forId(p.objectId).getHandlers().put("option:pick", this);
        }
        SceneryDefinition.forId(3511).getHandlers().put("option:take-cutting", this);
        return this;
    }

    @Override
    public boolean handle(final Player player, Node node, String option) {
        if (player.getAttribute("delay:picking", -1) > GameWorld.getTicks()) {
            return true;
        }
        final Scenery object = (Scenery) node;
        final PickingPlant plant = PickingPlant.forId(object.getId());
        if (plant == null) {
            return false;
        }
        if (!object.isActive()) {
            return true;
        }
        final Item reward = new Item(plant == PickingPlant.POTATO && RandomFunction.random(10) == 0 ? 5318 : plant.reward);
        if (!player.getInventory().hasSpaceFor(reward)) {
            player.getPacketDispatch().sendMessage("Not enough space in your inventory!");
            return true;
        }
        if (player.getInventory().freeSlot() == -1) {
            return true;
        }
        player.lock(1);
        setAttribute(player, "delay:picking", GameWorld.getTicks() + (plant == PickingPlant.FLAX ? 2 : 3));
        player.animate(ANIMATION);
        playAudio(player, Sounds.PICK_2581, 30);
        player.dispatch(new ResourceProducedEvent(reward.getId(), reward.getAmount(), node, -1));
        if (plant.name().startsWith("NETTLES") && (player.getEquipment().get(EquipmentContainer.SLOT_HANDS) == null || player.getEquipment().get(EquipmentContainer.SLOT_HANDS) != null && !player.getEquipment().get(EquipmentContainer.SLOT_HANDS).getName().contains("glove"))) {
            player.getPacketDispatch().sendMessage("You have been stung by the nettles!");
            player.getImpactHandler().manualHit(player, 2, HitsplatType.POISON);
            return true;
        }
        if (plant.respawn != -1 && plant != PickingPlant.FLAX) {
            object.setActive(false);
        }
        GameWorld.getPulser().submit(new Pulse(1, player) {
            @Override
            public boolean pulse() {
                if (!player.getInventory().add(reward)) {
                    player.getPacketDispatch().sendMessage("Not enough space in your inventory!");
                    return true;
                }
                if (plant == PickingPlant.FLAX) {
                    handleFlaxPick(player, object, plant);
                    return true;
                }
                boolean banana = plant.name().startsWith("BANANA");
                Scenery full = null;
                if (plant == PickingPlant.BANANA_TREE_4) {
                    full = object.transform(2073);
                    SceneryBuilder.replace(object, full);
                }
                boolean isBloomPlant = plant == PickingPlant.FUNGI_ON_LOG || plant == PickingPlant.BUDDING_BRANCH || plant == PickingPlant.GOLDEN_PEAR_BUSH;
                if (isBloomPlant) {
                    full = object.transform(object.getId() - 1);
                    SceneryBuilder.replace(object, full);
                }
                if (!isBloomPlant) {
                    SceneryBuilder.replace(plant == PickingPlant.BANANA_TREE_4 ? full : object, object.transform(banana ? plant.respawn : 0), banana ? 300 : plant.respawn);
                }
                if (!plant.name().startsWith("NETTLES")) {
                    player.getPacketDispatch().sendMessage("You pick a " + reward.getName().toLowerCase() + ".");
                } else {
                    player.getPacketDispatch().sendMessage("You pick a handful of nettles.");
                }
                return true;
            }
        });
        return true;
    }

    private void handleFlaxPick(final Player player, final Scenery object, final PickingPlant plant) {
        int charge = object.getCharge();
        playAudio(player, Sounds.PICK_2581);
        player.getPacketDispatch().sendMessage("You pick some flax.");

        if (charge > 1000 + RandomFunction.random(2, 8)) {
            object.setActive(false);
            object.setCharge(1000);
            SceneryBuilder.replace(object, object.transform(0), plant.respawn);
            return;
        }
        object.setCharge(charge + 1);
    }

    private static enum PickingPlant {
        /**
         * Potato picking plant.
         */
        POTATO(312, 1942, 30),
        /**
         * Wheat 0 picking plant.
         */
        WHEAT_0(313, 1947, 30),
        /**
         * Wheat 1 picking plant.
         */
        WHEAT_1(5583, 1947, 30),
        /**
         * Wheat 2 picking plant.
         */
        WHEAT_2(5584, 1947, 30),
        /**
         * Wheat 3 picking plant.
         */
        WHEAT_3(5585, 1947, 30),
        /**
         * Wheat 4 picking plant.
         */
        WHEAT_4(15506, 1947, 30),
        /**
         * Wheat 5 picking plant.
         */
        WHEAT_5(15507, 1947, 30),
        /**
         * Wheat 6 picking plant.
         */
        WHEAT_6(15508, 1947, 30),
        /**
         * Wheat 7 picking plant.
         */
        WHEAT_7(22300, 1947, 30),
        /**
         * Wheat 8 picking plant.
         */
        WHEAT_8(22473, 1947, 30),
        /**
         * Wheat 9 picking plant.
         */
        WHEAT_9(22474, 1947, 30),
        /**
         * Wheat 10 picking plant.
         */
        WHEAT_10(22475, 1947, 30),
        /**
         * Wheat 11 picking plant.
         */
        WHEAT_11(22476, 1947, 30),
        /**
         * Cabbage 0 picking plant.
         */
        CABBAGE_0(1161, 1965, 30),
        /**
         * Cabbage 1 picking plant.
         */
        CABBAGE_1(11494, 1967, 30),
        /**
         * Cabbage 2 picking plant.
         */
        CABBAGE_2(22301, 1965, 30),
        /**
         * Nettles 0 picking plant.
         */
        NETTLES_0(1181, 4241, 30),
        /**
         * Nettles 1 picking plant.
         */
        NETTLES_1(5253, 4241, 30),
        /**
         * Nettles 2 picking plant.
         */
        NETTLES_2(5254, 4241, 30),
        /**
         * Nettles 3 picking plant.
         */
        NETTLES_3(5255, 4241, 30),
        /**
         * Nettles 4 picking plant.
         */
        NETTLES_4(5256, 4241, 30),
        /**
         * Nettles 5 picking plant.
         */
        NETTLES_5(5257, 4241, 30),
        /**
         * Nettles 6 picking plant.
         */
        NETTLES_6(5258, 4241, 30),
        /**
         * Pineapple plant 0 picking plant.
         */
        PINEAPPLE_PLANT_0(1408, 2114, 30),
        /**
         * Pineapple plant 1 picking plant.
         */
        PINEAPPLE_PLANT_1(1409, 2114, 30),
        /**
         * Pineapple plant 2 picking plant.
         */
        PINEAPPLE_PLANT_2(1410, 2114, 30),
        /**
         * Pineapple plant 3 picking plant.
         */
        PINEAPPLE_PLANT_3(1411, 2114, 30),
        /**
         * Pineapple plant 4 picking plant.
         */
        PINEAPPLE_PLANT_4(1412, 2114, 30),
        /**
         * Pineapple plant 5 picking plant.
         */
        PINEAPPLE_PLANT_5(1413, 2114, 30),
        /**
         * Pineapple plant 6 picking plant.
         */
        PINEAPPLE_PLANT_6(4827, 2114, 30),
        /**
         * Banana tree 0 picking plant.
         */
        BANANA_TREE_0(2073, 1963, 2074),
        /**
         * Banana tree 1 picking plant.
         */
        BANANA_TREE_1(2074, 1963, 2075),
        /**
         * Banana tree 2 picking plant.
         */
        BANANA_TREE_2(2075, 1963, 2076),
        /**
         * Banana tree 3 picking plant.
         */
        BANANA_TREE_3(2076, 1963, 2077),
        /**
         * Banana tree 4 picking plant.
         */
        BANANA_TREE_4(2077, 1963, 2078),
        /**
         * Banana tree 5 picking plant.
         */
        BANANA_TREE_5(12606, 1963, 2079),
        /**
         * Banana tree 6 picking plant.
         */
        BANANA_TREE_6(12607, 1963, 2080),
        /**
         * Flax picking plant.
         */
        FLAX(2646, 1779, 30),
        /**
         * Onion 0 picking plant.
         */
        ONION_0(3366, 1957, 30),
        /**
         * Onion 1 picking plant.
         */
        ONION_1(5538, 1957, 30),
        /**
         * Fungi on log picking plant.
         */
        FUNGI_ON_LOG(3509, 2970, -1),
        /**
         * Budding branch picking plant.
         */
        BUDDING_BRANCH(3511, 2972, -1),
        /**
         * Golden pear bush picking plant.
         */
        GOLDEN_PEAR_BUSH(3513, 2974, -1),
        /**
         * Glowing fungus 0 picking plant.
         */
        GLOWING_FUNGUS_0(4932, 4075, 30),
        /**
         * Glowing fungus 1 picking plant.
         */
        GLOWING_FUNGUS_1(4933, 4075, 30),
        /**
         * Rare flowers picking plant.
         */
        RARE_FLOWERS(5006, 2460, 30),
        /**
         * Black mushrooms picking plant.
         */
        BLACK_MUSHROOMS(6311, 4620, 30),
        /**
         * Kelp picking plant.
         */
        KELP(12478, 7516, 30),
        /**
         * Red banana tree picking plant.
         */
        RED_BANANA_TREE(12609, 7572, 30),
        /**
         * Bush picking plant.
         */
        BUSH(12615, 7573, 30),
        /**
         * Red flowers picking plant.
         */
        RED_FLOWERS(15846, 8938, 30),
        /**
         * Blue flowers picking plant.
         */
        BLUE_FLOWERS(15872, 8936, 30),
        /**
         * Hardy goutweed picking plant.
         */
        HARDY_GOUTWEED(18855, 3261, 30),
        /**
         * Herbs 0 picking plant.
         */
        HERBS_0(21668, 199, 30),
        /**
         * Herbs 1 picking plant.
         */
        HERBS_1(21669, 201, 30),
        /**
         * Herbs 2 picking plant.
         */
        HERBS_2(21670, 203, 30),
        /**
         * Herbs 3 picking plant.
         */
        HERBS_3(21671, 205, 30),
        /**
         * Fever grass picking plant.
         */
        FEVER_GRASS(29113, 12574, 30),
        /**
         * Lavender picking plant.
         */
        LAVENDER(29114, 12572, 30),
        /**
         * Tansymum picking plant.
         */
        TANSYMUM(29115, 12576, 30),
        /**
         * Primweed picking plant.
         */
        PRIMWEED(29116, 12588, 30),
        /**
         * Stinkbloom picking plant.
         */
        STINKBLOOM(29117, 12590, 30),
        /**
         * Trollweiss flowers picking plant.
         */
        TROLLWEISS_FLOWERS(37328, 4086, 30),
        /**
         * Hollow log picking plant.
         */
        HOLLOW_LOG(37830, 10968, 30);

        /**
         * The Object id.
         */
        final int objectId;
        /**
         * The Reward.
         */
        final int reward;
        /**
         * The Respawn.
         */
        final int respawn;


        private PickingPlant(int objectId, int reward, int respawn) {
            this.objectId = objectId;
            this.reward = reward;
            this.respawn = respawn;
        }

        /**
         * For id picking plant.
         *
         * @param objectId the object id
         * @return the picking plant
         */
        static PickingPlant forId(int objectId) {
            for (PickingPlant plant : values())
                if (plant.objectId == objectId) return plant;
            return null;
        }
    }
}
