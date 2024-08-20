package content.region.desert.quest.deserttreasure.npc

import content.region.desert.quest.deserttreasure.DesertTreasure
import core.api.*
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.npc.NPC
import core.game.node.entity.npc.NPCBehavior
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

// https://www.youtube.com/watch?v=xeu6Ncmt1fY

/**
 * Represents the Kamil behavior.
 */
class KamilBehavior : NPCBehavior(NPCs.KAMIL_1913) {
    override fun onDeathFinished(self: NPC, killer: Entity) {
        if (killer is Player) {
            if (DesertTreasure.getSubStage(killer, DesertTreasure.attributeIceStage) == 2) {
                DesertTreasure.setSubStage(killer, DesertTreasure.attributeIceStage, 3)
                removeAttribute(killer, DesertTreasure.attributeKamilInstance)
                sendPlayerDialogue(killer, "Well, that must have been the 'bad man' that the troll kid was on about... His parents must be up ahead somewhere.")
            }
        }
    }

    override fun getSwingHandlerOverride(self: NPC, original: CombatSwingHandler): CombatSwingHandler {
        return KamilCombatHandler()
    }

    override fun canBeAttackedBy(self: NPC, attacker: Entity, style: CombatStyle, shouldSendMessage: Boolean): Boolean {
        return attacker == getAttribute<Player?>(self, "target", null)
    }
}

/**
 * Represents the Kamil combat handler.
 */
class KamilCombatHandler: MultiSwingHandler(
        SwitchAttack(CombatStyle.MELEE.swingHandler, null),
) {
    override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
        if (victim is Player) {
            // This is following RevenantCombatHandler.java, no idea if this is good.
            // I can't be bothered to fix fucking frozen. The player can hit through frozen. What the fuck is frozen for then, to glue his fucking legs???
            if (RandomFunction.roll(3) && !hasTimerActive(victim, "frozen") && !hasTimerActive(victim, "frozen:immunity")) {
                registerTimer(victim, spawnTimer("frozen", 7, true))
                sendMessage(victim, "You've been frozen!")
                sendChat(entity as NPC, "Sallamakar Ro!") // Salad maker roll.
                sendGraphics(539, victim.location)
                victim.properties.combatPulse.stop() // Force the victim to stop fighting. Whatever.
                // Audio?
            }else {
                animate(entity!!, Animation(440))
            }
        }
        super.impact(entity, victim, state)
    }
}