package content.region.misthalin.quest.free.vampireslayer.npc;

import core.game.node.entity.Entity;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.quest.Quest;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.plugin.Initializable;
import core.tools.RandomFunction;

/**
 * The Vampire slayer npc.
 */
@Initializable
public class VampireSlayerNPC extends AbstractNPC {

    private static final Item STAKE = new Item(1549);

    private static final Item HAMMER = new Item(2347);

    private static final Item GARLIC = new Item(1550);

    private static final Location[] CANDLE_LOCATION = new Location[]{Location.create(3076, 9772, 0), Location.create(3079, 9772, 0), Location.create(3075, 9778, 0), Location.create(3080, 9778, 0)};

    private static final String[] FORCE_CHAT = new String[]{"Eeek!", "Oooch!", "Gah!", "Ow!"};

    private static final int[] ID = {757};

    /**
     * Instantiates a new Vampire slayer npc.
     */
    public VampireSlayerNPC() {
        super(0, null);
    }

    private VampireSlayerNPC(int id, Location location) {
        super(id, location);
    }

    @Override
    public AbstractNPC construct(int id, Location location, Object... objects) {
        return new VampireSlayerNPC(id, location);
    }

    @Override
    public void init() {
        super.init();
        getSkills().setLifepoints(40);
        getSkills().setStaticLevel(Skills.HITPOINTS, 40);
        getSkills().setLevel(Skills.HITPOINTS, 40);
    }

    @Override
    public void tick() {
        final Player p = getAttribute("player", null);
        if (p != null) {
            if (p.getLocation().getDistance(getLocation()) >= 16) {
                clear();
                return;
            }
            if (!getProperties().getCombatPulse().isAttacking()) {
                getProperties().getCombatPulse().attack(p);
            }
            if (p.getProperties().getCombatPulse().isAttacking() && p.getProperties().getCombatPulse().getVictim() == this) {
                for (Location l : CANDLE_LOCATION) {
                    if (p.getLocation().equals(l)) {
                        p.sendChat(FORCE_CHAT[RandomFunction.random(FORCE_CHAT.length)]);
                        p.getPacketDispatch().sendMessage("The candles burn your feet!");
                        break;
                    }
                }
                if (!p.getInventory().containsItem(HAMMER) || !p.getInventory().containsItem(STAKE)) {
                    getSkills().heal(10);
                }
                if (RandomFunction.random(7) == 2) {
                    getSkills().heal(RandomFunction.random(1, !p.getInventory().containsItem(GARLIC) ? 12 : 2));
                }
            }
        }
        super.tick();
    }

    @Override
    public void onImpact(Entity entity, BattleState state) {
        if (entity instanceof Player) {
            final Player p = ((Player) entity);
            if (!p.getInventory().containsItem(HAMMER) || !p.getInventory().containsItem(STAKE)) {
                getSkills().heal(10);
            }
        }
        super.onImpact(entity, state);
    }

    @Override
    public void finalizeDeath(final Entity killer) {
        setRespawn(false);
        super.finalizeDeath(killer);
        if (!(killer instanceof Player)) {
            return;
        }
        final Player p = ((Player) killer);
        final Quest quest = p.getQuestRepository().getQuest("Vampire Slayer");
        if (p.getInventory().containsItem(HAMMER) && p.getInventory().remove(STAKE)) {
            if (quest.getStage(p) == 30) {
                quest.finish(p);
                p.getPacketDispatch().sendMessage("You hammer the stake into the vampire's chest!");
            }
        } else {
            p.getPacketDispatch().sendMessage("The vampire returns to his coffin. Next time use a stake and hammer.");
        }
        setRespawn(false);
    }

    @Override
    public void checkImpact(BattleState state) {
        if (state.getAttacker() instanceof Player) {
            Player p = (Player) state.getAttacker();
            if (!p.getInventory().containsItem(HAMMER) || !p.getInventory().containsItem(STAKE)) {
                if (state.getEstimatedHit() > -1) {
                    state.setEstimatedHit(0);
                }
                if (state.getSecondaryHit() > -1) {
                    state.setSecondaryHit(0);
                }
            }
        }
    }

    @Override
    public boolean isAttackable(final Entity entity, final CombatStyle style, boolean message) {
        final Player player = ((Player) entity);
        final Player pl = getAttribute("player", null);
        return pl != null && pl == player;
    }

    @Override
    public int[] getIds() {
        return ID;
    }

}
