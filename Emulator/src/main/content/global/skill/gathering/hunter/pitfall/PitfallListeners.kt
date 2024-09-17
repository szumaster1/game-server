package content.global.skill.gathering.hunter.pitfall;

import cfg.consts.Items
import cfg.consts.Sounds
import content.global.skill.gathering.hunter.HunterManager
import core.api.playAudio
import core.api.sendMessage
import core.api.setVarbit
import core.api.teleport
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.Entity
import core.game.node.entity.impl.Animator
import core.game.node.entity.impl.ForceMovement
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction
import java.util.concurrent.TimeUnit

/**
 * Pitfall listeners.
 */
class PitfallListeners : InteractionListener {

    val KNIFE = Item(Items.KNIFE_946)
    val TEASING_STICK = Item(Items.TEASING_STICK_10029)
    val LOGS = Item(Items.LOGS_1511)

    val PIT = 19227
    val SPIKED_PIT = 19228
    val GRAAHK_PIT = 19231
    val LARUPIA_PIT = 19232
    val KYATT_PIT = 19233

    override fun defineListeners() {
        setDest(
            IntType.SCENERY,
            intArrayOf(PIT, SPIKED_PIT, LARUPIA_PIT, GRAAHK_PIT, KYATT_PIT),
            "trap",
            "jump",
            "dismantle"
        ) { player, node ->
            val pit = node as Scenery
            val src = player.location
            var dst = pit.location
            val locs = Pitfall.pitJumpSpots(dst)
            if (locs != null) {
                for (loc in locs.keys) {
                    if (src.getDistance(loc) <= src.getDistance(dst)) {
                        dst = loc
                    }
                }
            } else {
                if (player is Player) {
                    sendMessage(player, "Error: Unimplemented pit at ${pit.location}")
                }
            }
            return@setDest dst
        }

        on(PIT, IntType.SCENERY, "trap") { player, node ->
            val pit = node as Scenery
            // TODO: check hunter level, remove logs
            if (player.skills.getLevel(Skills.HUNTER) < 31) {
                player.sendMessage("You need a hunter level of 31 to set a pitfall trap.")
                return@on true
            }

            val maxTraps = HunterManager.getInstance(player).maximumTraps
            if (player.getAttribute("pitfall:count", 0) >= maxTraps) {
                player.sendMessage("You can't set up more than $maxTraps pitfall traps at your hunter level.")
                return@on true
            }
            player.incrementAttribute("pitfall:count", 1)

            if (!player.inventory.containsItem(KNIFE) || !player.inventory.remove(LOGS)) {
                player.sendMessage("You need some logs and a knife to set a pitfall trap.")
                return@on true
            }

            player.setAttribute("pitfall:timestamp:${pit.location.x}:${pit.location.y}", System.currentTimeMillis())
            setPitState(player, pit.location, 1)
            playAudio(player, Sounds.HUNTING_PLACEBRANCHES_2639)
            val collapsePulse = object : Pulse(201, player) {
                override fun pulse(): Boolean {
                    val oldTime = player.getAttribute(
                        "pitfall:timestamp:${pit.location.x}:${pit.location.y}", System.currentTimeMillis()
                    )
                    if (System.currentTimeMillis() - oldTime >= TimeUnit.MINUTES.toMillis(2)) {
                        player.sendMessage("Your pitfall trap has collapsed.")
                        setPitState(player, pit.location, 0)
                        player.incrementAttribute("pitfall:count", -1)
                    }
                    return true
                }
            }
            GameWorld.Pulser.submit(collapsePulse)
            return@on true
        }

        on(SPIKED_PIT, IntType.SCENERY, "jump") { player, node ->
            val pit = node as Scenery
            val src = player.getLocation()
            val dir = Pitfall.pitJumpSpots(pit.getLocation())!![src]
            if (dir != null) {
                val dst = src.transform(dir, 3)
                ForceMovement.run(player, src, dst, ForceMovement.WALK_ANIMATION, Animation(1603), dir, 16)
                playAudio(player, Sounds.HUNTING_JUMP_2635)
                val pitfall_npc: Entity? = player.getAttribute("pitfall_npc", null)
                if (pitfall_npc != null && pitfall_npc.getLocation().getDistance(src) < 3.0) {
                    val last_pit_loc: Location? = pitfall_npc.getAttribute("last_pit_loc", null)
                    if (last_pit_loc == pit.location) {
                        player.sendMessage("The ${pitfall_npc.name.lowercase()} won't jump the same pit twice in a row.")
                        return@on true
                    }
                    // TODO: what are the actual probabilities of a graahk jumping over a pit?
                    val chance =
                        RandomFunction.getSkillSuccessChance(50.0, 100.0, player.skills.getLevel(Skills.HUNTER))
                    if (RandomFunction.random(0.0, 100.0) < chance) {
                        //ForceMovement.run(pitfall_npc, pitfall_npc.getLocation(), pit.getLocation(), ForceMovement.WALK_ANIMATION, Animation(ANIM), dir, 8);
                        //pitfall_npc.setLocation(pit.getLocation());
                        //pitfall_npc.walkingQueue.addPath(pit.location.x, pit.location.y);
                        teleport(pitfall_npc, pit.location)
                        pitfall_npc.removeAttribute("last_pit_loc")
                        playAudio(player, Sounds.HUNTING_PITFALL_COLLAPSE_2638, 0, 1, pit.location, 10)
                        playAudio(player, Sounds.PANTHER_DEATH_667, 50, 1, pit.location, 10)
                        pitfall_npc.startDeath(null)
                        player.removeAttribute("pitfall:timestamp:${pit.location.x}:${pit.location.y}")
                        player.incrementAttribute("pitfall:count", -1)
                        setPitState(player, pit.location, 3)
                        //pitfall_npc.animate(Animation(5234))
                    } else {
                        //ForceMovement.run(pitfall_npc, pitfall_npc.getLocation(), dst, ForceMovement.WALK_ANIMATION, Animation(ANIM), dir, 8);
                        //pitfall_npc.walkingQueue.addPath(npcdst.x, npcdst.y)
                        val npcdst = dst.transform(dir, if (dir == Direction.SOUTH || dir == Direction.WEST) 1 else 0)
                        teleport(pitfall_npc, npcdst)
                        pitfall_npc.animate(Animation(5232, Animator.Priority.HIGH))
                        playAudio(player, Sounds.HUNTING_BIGCAT_JUMP_2619, 0, 1, pit.location, 10)
                        pitfall_npc.attack(player)
                        pitfall_npc.setAttribute("last_pit_loc", pit.location)
                    }
                }
            }
            return@on true
        }

        on(SPIKED_PIT, IntType.SCENERY, "dismantle") { player, node ->
            val pit = node as Scenery
            playAudio(player, Sounds.HUNTING_TAKEBRANCHES_2649)
            player.removeAttribute("pitfall:timestamp:${pit.location.x}:${pit.location.y}")
            player.incrementAttribute("pitfall:count", -1)
            setPitState(player, pit.location, 0)
            return@on true
        }

        on(LARUPIA_PIT, IntType.SCENERY, "dismantle") { player, node ->
            lootCorpse(player, node as Scenery, 180.0, Items.LARUPIA_FUR_10095, Items.TATTY_LARUPIA_FUR_10093)
            sendMessage(player, "You've caught a spined larupia!")
            return@on true
        }

        on(GRAAHK_PIT, IntType.SCENERY, "dismantle") { player, node ->
            lootCorpse(player, node as Scenery, 240.0, Items.GRAAHK_FUR_10099, Items.TATTY_GRAAHK_FUR_10097)
            sendMessage(player, "You've caught a horned graahk!")
            return@on true
        }

        on(KYATT_PIT, IntType.SCENERY, "dismantle") { player, node ->
            lootCorpse(player, node as Scenery, 300.0, Items.KYATT_FUR_10103, Items.TATTY_KYATT_FUR_10101)
            sendMessage(player, "You've caught a sabretoothed kyatt!")
            return@on true
        }

        on(Pitfall.BEAST_IDS, IntType.NPC, "tease") { player, node ->
            val entity = node as Entity
            val hunterReq = Pitfall.HUNTER_REQS[entity.name]!!
            if (player.skills.getLevel(Skills.HUNTER) < hunterReq) {
                player.sendMessage("You need a hunter level of ${hunterReq} to hunt ${entity.name.lowercase()}s.")
                return@on true
            }
            if (!player.inventory.containsItem(TEASING_STICK)) {
                player.sendMessage("You need a teasing stick to hunt ${entity.name.lowercase()}s.")
                return@on true
            }
            entity.attack(player)
            playAudio(player, Sounds.HUNTING_TEASE_FELINE_2651)
            player.setAttribute("pitfall_npc", entity)
            return@on true
        }
    }

    /**
     * Loot corpse.
     *
     * @param player    the player.
     * @param pit       the pit.
     * @param xp        the xp.
     * @param goodFur   the good fur.
     * @param badFur    the bad fur.
     */
    fun lootCorpse(player: Player, pit: Scenery, xp: Double, goodFur: Int, badFur: Int) {
        if (player.inventory.freeSlots() < 2) {
            player.sendMessage("You don't have enough inventory space. You need 2 more free slots.")
            return
        }
        setPitState(player, pit.location, 0)
        player.getSkills().addExperience(Skills.HUNTER, xp, true)
        player.inventory.add(Item(Items.BIG_BONES_532))
        playAudio(player, Sounds.HUNTING_TAKEBRANCHES_2649)
        // TODO: what's the actual probability of tatty vs perfect fur?
        val chance = RandomFunction.getSkillSuccessChance(50.0, 100.0, player.skills.getLevel(Skills.HUNTER))
        if (RandomFunction.random(0.0, 100.0) < chance) {
            player.inventory.add(Item(goodFur))
        } else {
            player.inventory.add(Item(badFur))
        }
    }

    /**
     * Set pit state.
     *
     * @param player    the player.
     * @param loc       the location.
     * @param state     the state.
     */
    fun setPitState(player: Player, loc: Location, state: Int) {
        val pit = Pitfall.pitVarps[loc]!!
        setVarbit(player, pit.varbitId, state)
    }
}