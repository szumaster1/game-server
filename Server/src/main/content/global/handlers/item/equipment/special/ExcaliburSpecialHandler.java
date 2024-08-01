package content.global.handlers.item.equipment.special;

import core.api.consts.Sounds;
import core.game.container.impl.EquipmentContainer;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.MeleeSwingHandler;
import core.game.node.entity.impl.Animator.Priority;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.world.update.flag.context.Animation;
import core.game.world.update.flag.context.Graphic;
import core.plugin.Initializable;
import core.plugin.Plugin;

import static core.api.ContentAPIKt.*;

@Initializable
public final class ExcaliburSpecialHandler extends MeleeSwingHandler implements Plugin<Object> {

    private static final int SPECIAL_ENERGY = 100;

    private static final Animation ANIMATION = new Animation(1057, Priority.HIGH);

    private static final Graphic GRAPHIC = new Graphic(247);

    @Override
    public Object fireEvent(String identifier, Object... args) {
        switch (identifier) {
            case "instant_spec":
            case "ncspec":
                return true;
        }
        return null;
    }

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        CombatStyle.MELEE.getSwingHandler().register(35, this);
        CombatStyle.MELEE.getSwingHandler().register(14632, this);
        return this;
    }

    @Override
    public int swing(Entity entity, Entity victim, BattleState state) {
        Player p = (Player) entity;
        if (!p.getSettings().drainSpecial(SPECIAL_ENERGY))
            return -1;
        p.sendChat("For Camelot!");
        playAudio(entity.asPlayer(), Sounds.SANCTUARY_2539);
        p.visualize(ANIMATION, GRAPHIC);
        switch (p.getEquipment().get(EquipmentContainer.SLOT_WEAPON).getId()) {
            case 35: // Regular ol' excalibur
                p.getSkills().updateLevel(Skills.DEFENCE, 8, p.getSkills().getStaticLevel(Skills.DEFENCE) + 8);
                break;
            case 14632: // enhanced excalibur
                registerTimer(p, spawnTimer("healovertime", 3, 20, 4));
                p.getSkills().updateLevel(Skills.DEFENCE,
                        (int) (p.getSkills().getStaticLevel(Skills.DEFENCE) * 0.15),
                        (int) (p.getSkills().getStaticLevel(Skills.DEFENCE) * 1.15));
        }
        return -1;
    }
}
