package content.region.morytania.phasmatys;

import content.global.skill.combat.prayer.Bones;
import cfg.consts.NPCs;
import cfg.consts.Sounds;
import core.game.global.action.ClimbActionHandler;
import core.game.interaction.Option;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.node.scenery.SceneryBuilder;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneBuilder;
import core.game.world.update.flag.context.Animation;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.playAudio;
import static core.api.ContentAPIKt.sendMessage;

/**
 * Represents the Phasmatys zone.
 */
@Initializable
public final class PhasmatysZone extends MapZone implements Plugin<Object> {

    /**
     * The Bones.
     */
    Bones[] bones;
    /**
     * The Player.
     */
    Player player;
    /**
     * The Bone.
     */
    Bones bone;

    /**
     * Instantiates a new Phasmatys zone.
     */
    public PhasmatysZone() {
        super("Port phasmatys", true);
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        ZoneBuilder.configure(this);
        return this;
    }

    @Override
    public boolean interact(Entity e, Node target, Option option) {
        if (e.isPlayer()) {
            player = e.asPlayer();
            if (target instanceof NPC) {
                NPC npc = (NPC) target;

                if (npc.getId() == NPCs.GHOST_BANKER_1702) {
                    return false;
                }

                if ((npc.getName().toLowerCase().contains("ghost") || npc.getName().equalsIgnoreCase("velorina") || npc.getName().contains("husband")) && !hasAmulet(player)) {
                    player.getDialogueInterpreter().open(target.getId(), target);
                    return true;
                }
            }
            switch (target.getId()) {
                case 5267:
                    player.animate(Animation.create(536));
                    sendMessage(player, "The trapdoor opens...");
                    SceneryBuilder.replace((Scenery) target, ((Scenery) target).transform(5268));
                    return true;
                case 5268:
                    if (option.getName().equals("Close")) {
                        player.animate(Animation.create(535));
                        sendMessage(player, "You close the trapdoor.");
                        SceneryBuilder.replace((Scenery) target, ((Scenery) target).transform(5267));
                    } else {
                        sendMessage(player, "You climb down through the trapdoor...");
                        player.getProperties().setTeleportLocation(Location.create(3669, 9888, 3));
                    }
                    return true;
                case 7434:
                    if (option.getName().equalsIgnoreCase("open")) {
                        SceneryBuilder.replace(target.asScenery(), target.asScenery().transform(7435));
                    }
                    break;
                case 7435:
                    if (option.getName().equalsIgnoreCase("close")) {
                        SceneryBuilder.replace(target.asScenery(), target.asScenery().transform(7434));
                    }
                    break;
                case 9308:
                    if (player.getSkills().getStaticLevel(Skills.AGILITY) < 58) {
                        player.sendMessage("You need an agility level of at least 58 to climb down this wall.");
                        return true;
                    }
                    player.getProperties().setTeleportLocation(Location.create(3671, 9888, 2));
                    return true;
                case 9307:
                    if (player.getSkills().getStaticLevel(Skills.AGILITY) < 58) {
                        player.sendMessage("You need an agility level of at least 58 to climb up this wall.");
                        return true;
                    }
                    player.getProperties().setTeleportLocation(Location.create(3670, 9888, 3));
                    return true;
                case 5264:
                    ClimbActionHandler.climb(player, Animation.create(828), Location.create(3654, 3519, 0));
                    return true;
                case 5282:
                    worship(player);
                    return true;
            }
        }
        return super.interact(e, target, option);
    }

    /**
     * Has amulet boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public static boolean hasAmulet(Player player) {
        return player.getEquipment().contains(552, 1);
    }

    private void worship(Player player) {
        Bones bone = null;
        for (Item i : player.getInventory().toArray()) {
            if (i == null) {
                continue;
            }
            Bones b = Bones.forBoneMeal(i.getId());
            if (b != null) {
                bone = b;
                break;
            }
        }
        if (!player.getInventory().contains(4286, 1) && bone == null) {
            player.getDialogueInterpreter().sendDialogue("You don't have any ectoplasm or crushed bones to put into the", "Ectofuntus.");
            return;
        }
        if (bone == null) {
            player.getDialogueInterpreter().sendDialogue("You need a pot of crushed bones to put into the Ectofuntus.");
            return;
        }
        if (!player.getInventory().contains(4286, 1)) {
            player.getDialogueInterpreter().sendDialogue("You need ectoplasm to put into the Ectofuntus.");
            return;
        }
        if (player.getInventory().remove(new Item(bone.getBonemealId()), new Item(4286, 1))) {
            player.lock(1);
            player.animate(Animation.create(1651));
            playAudio(player, Sounds.PRAYER_BOOST_2671);
            player.getInventory().add(new Item(1925), new Item(1931));
            player.getSkills().addExperience(Skills.PRAYER, bone.getExperience() * 4, true);
            player.sendMessage("You put some ectoplasm and bonemeal into the Ectofuntus, and worship it.");
            player.getSavedData().globalData.setEctoCharges(player.getSavedData().globalData.getEctoCharges() + 1);
        }
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public void configure() {
        register(new ZoneBorders(3583, 3456, 3701, 3552));
        register(new ZoneBorders(3667, 9873, 3699, 9914));
    }

}
