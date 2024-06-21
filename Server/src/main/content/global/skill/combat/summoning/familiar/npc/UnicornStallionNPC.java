package content.global.skill.combat.summoning.familiar.npc;

import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.familiar.FamiliarSpecial;
import core.api.consts.Sounds;
import core.cache.def.impl.NPCDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.combat.equipment.WeaponInterface;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.*;

/**
 * The Unicorn stallion npc.
 */
@Initializable
public class UnicornStallionNPC extends Familiar {
    /**
     * Instantiates a new Unicorn stallion npc.
     */
    public UnicornStallionNPC() {
        this(null, 6822);
    }

    /**
     * Instantiates a new Unicorn stallion npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    public UnicornStallionNPC(Player owner, int id) {
        super(owner, id, 5400, 12039, 20, WeaponInterface.STYLE_CONTROLLED);
    }

    @Override
    public Familiar construct(Player owner, int id) {
        return new UnicornStallionNPC(owner, id);
    }

    @Override
    protected boolean specialMove(FamiliarSpecial special) {
        Player player = (Player) special.getNode();
        playAudio(player, Sounds.HEALING_AURA_4372);
        visualize(Animation.create(8267), Graphic.create(1356));
        player.getSkills().heal((int) (player.getSkills().getMaximumLifepoints() * 0.15));
        return true;
    }

    @Override
    protected void configureFamiliar() {
        ClassScanner.definePlugin(new OptionHandler() {

            @Override
            public Plugin<Object> newInstance(Object arg) throws Throwable {
                NPCDefinition.forId(6822).getHandlers().put("option:cure", this);
                NPCDefinition.forId(6823).getHandlers().put("option:cure", this);
                return this;
            }

            @Override
            public boolean handle(Player player, Node node, String option) {
                Familiar familiar = (Familiar) node;
                if (!player.getFamiliarManager().isOwner(familiar)) {
                    return true;
                }
                Player owner = player;
                if (owner.getSkills().getLevel(Skills.MAGIC) < 2) {
                    player.sendMessage("You don't have enough summoning points left");
                    return true;
                }
                if (!isPoisoned(owner)) {
                    player.sendMessage("You are not poisoned.");
                    return true;
                }
                playAudio(player, Sounds.HEALING_AURA_4372);
                familiar.visualize(Animation.create(8267), Graphic.create(1356));
                curePoison(player);
                player.getSkills().updateLevel(Skills.SUMMONING, -2, 0);
                return true;
            }

        });
    }

    @Override
    public void visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1298));
    }

    @Override
    public int[] getIds() {
        return new int[]{6822, 6823};
    }

}
