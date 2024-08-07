package content.global.activity.treasuretrails.npc;

import content.global.activity.treasuretrails.clue.ClueScrollPlugin;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.combat.spell.CombatSpell;
import core.game.node.entity.npc.AbstractNPC;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.SpellBookManager.SpellBook;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.plugin.Plugin;

/**
 * Zamorak wizard npc.
 */
public final class ZamorakWizardNPC extends AbstractNPC {

	private static final int[] IDS = new int[] { 1007 };

	private ClueScrollPlugin clueScroll;

	private Player player;

    /**
     * Instantiates a new Zamorak wizard npc.
     */
    public ZamorakWizardNPC() {
		super(0, null);
	}

    /**
     * Instantiates a new Zamorak wizard npc.
     *
     * @param id       the id
     * @param location the location
     */
    public ZamorakWizardNPC(int id, Location location) {
		super(id, location, false);
		this.setRespawn(false);
	}

	@Override
	public AbstractNPC construct(int id, Location location, Object... objects) {
		return new ZamorakWizardNPC(id, location);
	}

	@Override
	public void init() {
		Player player = getAttribute("player", null);
		ClueScrollPlugin clueScroll = getAttribute("clue", null);
		if (player != null) {
			this.player = player;
		}
		if (clueScroll != null) {
			this.clueScroll = clueScroll;
		}
		if (player != null) {
			location = RegionManager.getSpawnLocation(player, this);
			if (location == null) {
				location = player.getLocation();
			}
		}
		super.init();
		getProperties().setSpell((CombatSpell) SpellBook.MODERN.getSpell(43));
		getProperties().setAutocastSpell((CombatSpell) SpellBook.MODERN.getSpell(43));
	}

	@Override
	public void finalizeDeath(Entity killer) {
		if (killer instanceof Player) {
			Player p = killer.asPlayer();
			if (p == player) {
				p.setAttribute("killed-wizard", clueScroll.getClueId());
			}
		}
		super.finalizeDeath(killer);
	}

	@Override
	public void handleTickActions() {
		super.handleTickActions();
		if (player != null) {
			if (player.getLocation().getDistance(getLocation()) > 10 || !player.isActive()) {
				clear();
			}
			if (!getProperties().getCombatPulse().isAttacking()) {
				getProperties().getCombatPulse().attack(player);
			}
		}
	}

	@Override
	public boolean isAttackable(Entity entity, CombatStyle style, boolean message) {
		if (!(entity instanceof Player)) {
			return false;
		}
		if (player != null) {
			Player p = entity.asPlayer();
			return p == player;
		}
		return super.isAttackable(entity, style, message);
	}

	@Override
	public boolean canAttack(Entity entity) {
		if (!(entity instanceof Player)) {
			return false;
		}
		if (player != null) {
			Player p = entity.asPlayer();
			return p == player;
		}
		return super.canAttack(entity);
	}

	@Override
	public boolean canSelectTarget(Entity target) {
		return target == player;

	}

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		return super.newInstance(arg);
	}

	@Override
	public int[] getIds() {
		return IDS;
	}

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
		return player;
	}

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
		this.player = player;
	}

    /**
     * Gets clue scroll.
     *
     * @return the clue scroll
     */
    public ClueScrollPlugin getClueScroll() {
		return clueScroll;
	}

}
