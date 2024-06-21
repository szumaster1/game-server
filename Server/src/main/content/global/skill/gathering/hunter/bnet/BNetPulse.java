package content.global.skill.gathering.hunter.bnet;

import core.api.consts.Sounds;
import core.game.container.impl.EquipmentContainer;
import core.game.node.entity.combat.DeathTask;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.update.flag.context.Animation;
import core.utilities.RandomFunction;
import core.utilities.StringUtils;

import java.util.Random;

import static core.api.ContentAPIKt.playAudio;

/**
 * The B net pulse.
 */
public final class BNetPulse extends SkillPulse<NPC> {

    private static final Animation ANIMATION = new Animation(6999);

    private final BNetNode type;

    private boolean success;

    private int ticks;

    /**
     * Update lumbridge impling task boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean updateLumbridgeImplingTask(Player player) {
        return player.getZoneMonitor().isInZone("puro puro");
    }

    /**
     * Instantiates a new B net pulse.
     *
     * @param player the player
     * @param node   the node
     * @param type   the type
     */
    public BNetPulse(Player player, NPC node, BNetNode type) {
        super(player, node);
        this.type = type;
        this.resetAnimation = false;
    }

    @Override
    public boolean checkRequirements() {
        if (player.getSkills().getLevel(Skills.HUNTER) < type.getLevel()) {
            player.sendMessage("You need a Hunter level of at least " + type.getLevel() + " in order to do that.");
            return false;
        }
        if (type.hasWeapon(player)) {
            player.getPacketDispatch().sendMessage("Your hands need to be free.");
            return false;
        } else if (!type.hasNet(player)) {
            player.sendMessage("You need to be wielding a butterfly net to catch " + (type instanceof ImplingNode ? "implings" : "butterflies") + ".");
            return false;
        } else if (!type.hasJar(player)) {
            player.getPacketDispatch().sendMessage("You need to have a" + (StringUtils.isPlusN(type.getJar().getName()) ? "n" : "") + " " + type.getJar().getName().toLowerCase() + ".");
            return false;
        }
        return !node.isInvisible() && !DeathTask.isDead(node);
    }

    @Override
    public void animate() {
        if (ticks < 1) {
            player.animate(ANIMATION);
            playAudio(player, Sounds.HUNTING_BUTTERFLYNET_2623);
        }
    }

    @Override
    public boolean reward() {
        if (node.isInvisible() || DeathTask.isDead(node)) {
            return true;
        }
        if (++ticks % 2 != 0) {
            return false;
        }
        if (node.getAttribute("dead", 0) > GameWorld.getTicks()) {
            player.sendMessage("Ooops! It's gone.");
            return true;
        }
        if ((success = isSuccessful())) {
            node.finalizeDeath(player);
            type.reward(player, node);
            node.setAttribute("dead", GameWorld.getTicks() + 10);
            if (type == BNetTypes.ECLECTIC_IMPLING.getNode() || type == BNetTypes.ESSENCE_IMPLING.getNode()) {
                updateLumbridgeImplingTask(player);
            }
        } else {
            node.moveStep();
        }
        return true;
    }

    @Override
    public void message(int type) {
        if (type == 0) {
            node.setAttribute("looting", GameWorld.getTicks() + (ANIMATION.getDuration() + 1));
            player.lock(ANIMATION.getDuration());
        }
        this.type.message(player, type, success);
    }

    private boolean isSuccessful() {
        int huntingLevel = player.getSkills().getLevel(Skills.HUNTER);
        int level = type.getLevel();
        if (type.hasNet(player)) {
            Item net = player.getEquipment().get(EquipmentContainer.SLOT_WEAPON);
            if (net != null && net.getId() == 11259) {
                huntingLevel += 5;
            }
        } else {
            huntingLevel *= 0.5;
        }
        int currentLevel = RandomFunction.random(huntingLevel) + 1;
        double ratio = (double) currentLevel / (new Random().nextInt(level + 5) + 1);
        return Math.round(ratio * huntingLevel) >= level;
    }

}
