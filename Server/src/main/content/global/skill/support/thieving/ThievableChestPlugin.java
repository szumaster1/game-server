package content.global.skill.support.thieving;

import core.cache.def.impl.SceneryDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.combat.ImpactHandler.HitsplatType;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.system.task.Pulse;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.animate;
import static core.api.ContentAPIKt.lock;

@Initializable
public final class ThievableChestPlugin extends OptionHandler {
    private static final Item LOCK_PICK = new Item(1523);

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {

        for (Chest chest : Chest.values()) {
            for (int id : chest.getObjectIds()) {
                SceneryDefinition def = SceneryDefinition.forId(id);
                def.getHandlers().put("option:open", this);
                def.getHandlers().put("option:search for traps", this);
            }
        }
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        final Chest chest = Chest.forId(node.getId());
        switch (option) {
            case "open":
                if (chest != null) {
                    chest.open(player, (Scenery) node);
                    return true;
                }
                return true;
            case "search for traps":
                chest.searchTraps(player, (Scenery) node);
                return true;
        }
        return true;
    }

    public enum Chest {
        TEN_COIN(2566, 13, 7.8, new Item[]{new Item(995, 10)}, 7),
        NATURE_RUNE(2567, 28, 25, new Item[]{new Item(995, 3), new Item(561, 1)}, 8),
        FIFTY_COIN(2568, 43, 125, new Item[]{new Item(995, 50)}, 55),
        STEEL_ARROWHEADS(2573, 47, 150, new Item[]{new Item(41, 5)}, 210),
        BLOOD_RUNES(2569, 59, 250, new Item[]{new Item(995, 500), new Item(565, 2)}, 135),
        PALADIN(2570, 72, 500, new Item[]{new Item(995, 1000), new Item(383, 1), new Item(449, 1), new Item(1623, 1)}, 120);

        private final int[] objectIds;
        private final int level;
        private final double experience;
        private final Item[] rewards;
        private final int respawn;
        private int currentRespawn;

        Chest(int[] objectIds, int level, double experience, Item[] rewards, int respawn) {
            this.objectIds = objectIds;
            this.level = level;
            this.experience = experience;
            this.rewards = rewards;
            this.respawn = respawn;
        }

        Chest(int objectId, int level, double experience, Item[] rewards, int respawn) {
            this(new int[]{objectId}, level, experience, rewards, respawn);
        }

        private void open(final Player player, final Scenery object) {
            if (isRespawning()) {
                player.sendMessage("It looks like this chest has already been looted.");
                return;
            }
            player.lock(2);
            player.sendMessage("You have activated a trap on the chest.");
            player.getImpactHandler().manualHit(player, getHitAmount(player), HitsplatType.NORMAL);
        }

        private void searchTraps(final Player player, final Scenery object) {
            player.faceLocation(object.getLocation());
            if (isRespawning()) {
                player.sendMessage("It looks like this chest has already been looted.");
                return;
            }
            if (player.getSkills().getLevel(Skills.THIEVING) < level) {
                animate(player, 536, false);
                lock(player, 2);
                player.sendMessage("You search the chest for traps.");
                player.sendMessage("You find nothing.", 1);
                return;
            }
            if (player.getInventory().freeSlots() == 0) {
                player.getPacketDispatch().sendMessage("Not enough inventory space.");
                return;
            }
            lock(player, 6);
            animate(player, 536, false);
            player.sendMessage("You find a trap on the chest...");
            player.getImpactHandler().setDisabledTicks(6);
            GameWorld.getPulser().submit(new Pulse(1, player) {
                int counter;

                @Override
                public boolean pulse() {
                    switch (++counter) {
                        case 2:
                            player.sendMessage("You disable the trap.");
                            break;
                        case 4:
                            player.animate(Animation.create(536));
                            player.faceLocation(object.getLocation());
                            player.sendMessage("You open the chest.");
                            break;
                        case 6:
                            for (Item i : rewards) {
                                player.getInventory().add(i, player);
                            }
                            player.sendMessage("You find treasure inside!");
                            player.getSkills().addExperience(Skills.THIEVING, experience, true);
                            if (object.isActive()) {
                                SceneryBuilder.replace(object, object.transform(2574), 3);
                            }
                            setRespawn();
                            return true;
                    }
                    return false;
                }
            });
        }

        public void setRespawn() {
            currentRespawn = GameWorld.getTicks() + (int) (respawn / 0.6);
        }

        public boolean isRespawning() {
            return currentRespawn > GameWorld.getTicks();
        }

        protected static int getHitAmount(Player player) {
            int hit = player.getSkills().getLifepoints() / 12;
            if (hit < 2) {
                hit = 2;
            }
            return hit;
        }

        public static Chest forId(int id) {
            for (Chest chest : values()) {
                for (int i : chest.getObjectIds()) {
                    if (i == id) {
                        return chest;
                    }
                }
            }
            return null;
        }

        public int[] getObjectIds() {
            return objectIds;
        }
        public int getLevel() {
            return level;
        }
        public double getExperience() {
            return experience;
        }
        public Item[] getRewards() {
            return rewards;
        }
        public int getRespawn() {
            return respawn;
        }
    }
}
