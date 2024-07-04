package core.game.world.map.zone.impl;

import content.global.handlers.item.equipment.gloves.BrawlingGloves;
import content.global.skill.combat.summoning.familiar.Familiar;
import content.region.wilderness.handlers.DeepWildyThreat;
import core.api.consts.NPCs;
import core.game.component.Component;
import core.game.interaction.Option;
import core.game.node.Node;
import core.game.node.entity.Entity;
import core.game.node.entity.combat.CombatStyle;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.npc.agg.AggressiveBehavior;
import core.game.node.entity.npc.agg.AggressiveHandler;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.Rights;
import core.game.node.entity.player.link.SkullManager;
import core.game.node.item.GroundItemManager;
import core.game.node.item.Item;
import core.game.world.GameWorld;
import core.game.world.map.Location;
import core.game.world.map.zone.MapZone;
import core.game.world.map.zone.ZoneBorders;
import core.game.world.map.zone.ZoneRestriction;
import core.game.world.repository.Repository;
import core.tools.RandomFunction;

import static core.api.ContentAPIKt.sendNews;

/**
 * Handles the wilderness zone.
 * @author Emperor
 * @author Ceikry
 */
public final class WildernessZone extends MapZone {

    private static int[] PVP_GEAR = {13887, 13893, 13899, 13905, 13870, 13873, 13876, 13879, 13883, 13884, 13890, 13896, 13902, 13858, 13861, 13864, 13867};

    private static final WildernessZone INSTANCE = new WildernessZone(new ZoneBorders(2944, 3525, 3400, 3975), new ZoneBorders(3070, 9924, 3135, 10002), ZoneBorders.forRegion(12192), ZoneBorders.forRegion(12193), ZoneBorders.forRegion(11937));

    public static final String WILDERNESS_PROT_ATTR = "/save:wilderness-protection-active";
    public static final String WILDERNESS_HIGHER_NEXTFEE = "/save:wilderness-higher-next-fee";

    private ZoneBorders[] borders;

    public WildernessZone(ZoneBorders... borders) {
        super("Wilderness", true, ZoneRestriction.RANDOM_EVENTS);
        this.borders = borders;
    }

    @Override
    public void configure() {
        for (ZoneBorders border : borders) {
            register(border);
        }
    }

    /**
     * calculate drop rate for rev items based on combat level
     */
    public int getNewDropRate(int combatLevel) {
        double x = combatLevel;
        double A = 44044.5491;
        double B = -7360.19548;
        return (int) (A + (B * Math.log(x)));
    }

    /**
     * Handles rev drops.
     */
    @Override
    public boolean death(Entity e, Entity killer) {
        if (e instanceof NPC)
            rollWildernessExclusiveLoot(e, killer);
        return false; //DONT override default death handling.
    }

    private void rollWildernessExclusiveLoot(Entity e, Entity killer) {
        if (!(killer instanceof Player)) return;

        boolean isDeepWildy = ((Player) killer).getSkullManager().isDeepWilderness();
        boolean isRevOrCele = e.asNpc().getName().contains("Revenant") || e.getId() == NPCs.CHAOS_ELEMENTAL_3200;
        boolean isSufficientRisk = ((Player) killer).getAttribute("deepwild-value-risk", 0L) > SkullManager.DEEP_WILD_DROP_RISK_THRESHOLD;
        boolean isValidTarget = e instanceof NPC && ((isDeepWildy && isSufficientRisk) || isRevOrCele);

        if (isDeepWildy) {
            DeepWildyThreat.adjustThreat((Player) killer, 50);
        }

        if (!isValidTarget) return;

        int pvpGearRate = getNewDropRate(e.asNpc().getDefinition().getCombatLevel());
        if (isDeepWildy && isRevOrCele)
            pvpGearRate /= 2;

        int cEleGloveRate = isDeepWildy ? 50 : 150;
        int normalGloveRate = isDeepWildy && isRevOrCele ? 100 : (int) ((1.0 / (1.0 - Math.pow(1.0 - (1.0 / (double) pvpGearRate), 16.0))) * 5.0 / 6.0);

        if (RandomFunction.roll(e.getId() == NPCs.CHAOS_ELEMENTAL_3200 ? cEleGloveRate : normalGloveRate)) {
            byte glove = (byte) RandomFunction.random(1, 14);
            Item reward = new Item(BrawlingGloves.forIndicator(glove).getId());
            GroundItemManager.create(reward, e.asNpc().getDropLocation(), killer.asPlayer());
            sendNews(killer.getUsername() + " has received " + reward.getName().toLowerCase() + " from a " + e.asNpc().getName() + "!");
            if (isDeepWildy) DeepWildyThreat.adjustThreat((Player) killer, 750);
        }

        for (int j : PVP_GEAR) {
            boolean chance = RandomFunction.roll(pvpGearRate);
            if (chance) {
                Item reward;
                if (j == 13879 || j == 13883) { // checks if it's a javelin or throwing axe
                    reward = new Item(j, RandomFunction.random(15, 50));
                } else {
                    reward = new Item(j);
                }
                Repository.sendNews(killer.asPlayer().getUsername() + " has received a " + reward.getName() + " from a " + e.asNpc().getName() + "!");
                GroundItemManager.create(reward, ((NPC) e).getDropLocation(), killer.asPlayer());
                if (isDeepWildy) DeepWildyThreat.adjustThreat((Player) killer, 3000);
            }
        }
    }

    /**
     * Fixes attack options for the revs.
     */
    @Override
    public boolean interact(Entity e, Node target, Option option) {
        if (target instanceof NPC) {
            if (target.asNpc().getName().contains("Revenant")) {
                e.asPlayer().getProperties().getCombatPulse().attack(target);
                return true;
            }
        }
        return super.interact(e, target, option);
    }


    @Override
    public boolean enter(Entity e) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (!p.isArtificial()) {
                show(p);
            } else {
                p.getSkullManager().setWilderness(true);
                p.getSkullManager().setLevel(getWilderness(p));
            }
            for (int i = 0; i < 7; i++) {
                if (i == 5 || i == 3) {
                    continue;
                }
                if (p.getAttributes().containsKey("overload") || p.getSkills().getLevel(i) > 118) {
                    if (p.getSkills().getLevel(i) > p.getSkills().getStaticLevel(i)) {
                        p.getSkills().setLevel(i, p.getSkills().getStaticLevel(i));
                        p.removeAttribute("overload");
                    }
                }
            }
            if (p.getFamiliarManager().hasFamiliar() && !p.getFamiliarManager().hasPet()) {
                Familiar familiar = p.getFamiliarManager().getFamiliar();
                familiar.transform();
            }
            p.getAppearance().sync();
        } else if (e instanceof NPC) {
            NPC n = (NPC) e;
            if (n.getDefinition().hasAttackOption() && n.isAggressive()) {
                //n.setAggressive(true);
                n.setAggressiveHandler(new AggressiveHandler(n, AggressiveBehavior.WILDERNESS));
            }
        }
        return true;
    }

    @Override
    public boolean leave(Entity e, boolean logout) {
        if (!logout && e instanceof Player) {
            Player p = (Player) e;
            leave(p);
            if (p.getFamiliarManager().hasFamiliar() && !p.getFamiliarManager().hasPet()) {
                Familiar familiar = p.getFamiliarManager().getFamiliar();
                if (familiar.isCombatFamiliar()) {
                    familiar.reTransform();
                }
            }
            p.getAppearance().sync();
        }
        return true;
    }

    /**
     * Method used to remove traces of being in the zone.
     */
    public final void leave(final Player p) {
        Component overlay = new Component(381);
        if (overlay.getId() == 381) {
            p.getInterfaceManager().close(overlay);
        }
        p.getInteraction().remove(Option._P_ATTACK);
        p.getSkullManager().setWilderness(false);
        p.getSkullManager().setLevel(0);
    }

    /**
     * Method used to show being the wilderness.
     */
    public static final void show(final Player player) {
        if (player.getSkullManager().isWildernessDisabled()) {
            return;
        }
        player.getInterfaceManager().openOverlay(new Component(381));
        player.getSkullManager().setLevel(getWilderness(player));
        player.getPacketDispatch().sendString("Level: " + player.getSkullManager().getLevel(), 381, 1);
        if (GameWorld.getSettings().getWild_pvp_enabled()) {
            player.getInteraction().set(Option._P_ATTACK);
        }
        player.getSkullManager().setWilderness(true);
    }

    @Override
    public boolean teleport(Entity e, int type, Node node) {
        if (e instanceof Player) {
            Player p = (Player) e;
            if (p.getDetails().getRights() == Rights.ADMINISTRATOR) {
                return true;
            }
            if (!checkTeleport(p, (node != null && node instanceof Item && (((Item) node).getName().contains("glory") || ((Item) node).getName().contains("slaying")) ? 30 : 20))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method used to check if a player can teleport past a level.
     */
    public static boolean checkTeleport(Player p, int level) {
        if (p.getSkullManager().getLevel() > level && !p.getSkullManager().isWildernessDisabled()) {
            message(p, "You can't teleport this deep in the wilderness!");
            return false;
        }
        return true;
    }

    @Override
    public boolean continueAttack(Entity e, Node target, CombatStyle style, boolean message) {
        if (e instanceof Player && target instanceof Player) {
            Player p = (Player) e;
            Player t = (Player) target;
            int level = p.getSkullManager().getLevel();
            if (t.getSkullManager().getLevel() < level) {
                level = t.getSkullManager().getLevel();
            }
            int combat = p.getProperties().getCurrentCombatLevel();
            int targetCombat = t.getProperties().getCurrentCombatLevel();
            if (combat - level > targetCombat || combat + level < targetCombat) {
                if (message) {
                    p.getPacketDispatch().sendMessage("The level difference between you and your opponent is too great.");
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void locationUpdate(Entity e, Location last) {
        if (e instanceof Player && !e.asPlayer().isArtificial()) {
            Player p = (Player) e;
            p.getSkullManager().setLevel(getWilderness(p));
        }
    }

    public static boolean isInZone(Entity e) {
        Location l = e.getLocation();
        for (ZoneBorders z : INSTANCE.borders) {
            if (z.insideBorder(e))
                return true;
        }
        return false;
    }

    public static boolean isInZone(Location l) {
        for (ZoneBorders z : INSTANCE.borders) {
            if (z.insideBorder(l)) return true;
        }
        return false;
    }

    public static int getWilderness(Entity e) {
        int y = e.getLocation().getY();
        if (6400 < y) {
            return ((y - 9920) / 8) + 1;
        } else {
            return ((y - 3520) / 8) + 1;
        }
    }

    public ZoneBorders[] getBorders() {
        return borders;
    }

    public static WildernessZone getInstance() {
        return INSTANCE;
    }

}
