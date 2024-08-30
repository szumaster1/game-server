package content.region.misthalin.draynor.quest.vampire

import cfg.consts.Vars
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

/**
 * Vampire slayer quest.
 */
@Initializable
class VampireSlayer : Quest("Vampire Slayer", 30, 29, 3, Vars.VARP_QUEST_VAMPIRE_SLAYER_PROGRESS, 0, 1, 3) {

     /*
      * The people of Draynor village live in constant terror. Their numbers are dwindling,
      * all due to a foul creature known as a vampire, lurking in the manor to the north.
      */

    override fun drawJournal(player: Player, stage: Int) {
        super.drawJournal(player, stage)
        if (getStage(player) == 0) {
            line(player, BLUE + "I can start this quest by speaking to " + RED + "Morgan who is in", 4 + 7)
            line(player, RED + "Draynor Village.", 5 + 7)
            line(player, BLUE + "Requirements:", 7 + 7)
            line(player, BLUE + "Must be able to kill a level 34 " + RED + "Vampire.", 8 + 7)
        }
        if (getStage(player) == 10) {
            line(player, "<str>I spoke to Morgan in Draynor Village. He told me that the", 4 + 7)
            line(player, "<str>locals are being attacked by a terrifying Vampire!", 5 + 7)
            line(player, BLUE + "I need to speak to " + RED + "Dr Harlow " + BLUE + "who can normally be found in", 7 + 7)
            line(player, BLUE + "the " + RED + " Blue Moon Inn" + BLUE + " in " + RED + "Varrock.", 8 + 7)
        }
        if (getStage(player) == 20) {
            line(player, "<str>I spoke to Morgan in Draynor Village. He told me that the", 4 + 7)
            line(player, "<str>locals are being attacked by a terrifying Vampire!", 5 + 7)
            line(player, "<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7 + 7)
            line(player, "<str>he kept asking me to buy him drinks.", 8 + 7)
            line(player, BLUE + "I should see what advice " + RED + "Dr Harlow" + BLUE + " can give me about killing", 10 + 7)
            line(player, RED + "Vampires.", 11 + 7)
            line(player, BLUE + "When I'm ready, I should go to " + RED + "Draynor Manor" + BLUE + ", north of", 12 + 7)
            line(player, BLUE + "Draynor to kill the " + RED + "Vampire" + BLUE + " that's living in the basement.", 13 + 7)
        }
        if (getStage(player) == 30) {
            line(player, "<str>I spoke to Morgan in Draynor Village. He told me that the", 4 + 7)
            line(player, "<str>locals are being attacked by a terrifying Vampire!", 5 + 7)
            line(player, "<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7 + 7)
            line(player, "<str>he kept asking me to buy him drinks.", 8 + 7)
            line(player, "<str>Dr Harlow gave me a stake to finish off the Vampire then", 10 + 7)
            line(player, "<str>I'm fighting it. I've got a hammer to drive the stake deep", 11 + 7)
            line(player, "<str>into the Vampire's chest, and I have some garlic which", 12 + 7)
            line(player, "<str>should weaken the Vampire.", 13 + 7)
            line(player, BLUE + "When i'm ready, I should go to " + RED + "Draynor Manor" + BLUE + ", north of", 14 + 7)
            line(player, BLUE + "Draynor to kill the " + RED + "Vampire" + BLUE + " that's living in the basement.", 15 + 7)
        }
        if (getStage(player) == 100) {
            line(player, "<str>I spoke to Morgan in Draynor Village. He told me that the", 4 + 7)
            line(player, "<str>locals are being attacked by a terrifying Vampire!", 5 + 7)
            line(player, "<str>I have spoken to Dr Harlow. He seemed terribly drunk, and", 7 + 7)
            line(player, "<str>he kept asking me to buy him drinks.", 8 + 7)
            line(player, "<str>I have killed the Vampire, Count Draynor. Draynor Village is", 10 + 7)
            line(player, "<str>now safe!", 11 + 7)
            line(player, "<col=FF0000>QUEST COMPLETE!", 13 + 7)
        }
    }

    override fun finish(player: Player) {
        super.finish(player)
        player.getSkills().addExperience(Skills.ATTACK, 4825.0)
        player.packetDispatch.sendString("3 Quest Point", 277, 8 + 2)
        player.packetDispatch.sendString("4825 Attack XP", 277, 9 + 2)
        player.packetDispatch.sendItemZoomOnInterface(1549, 260, 277, 3 + 2)
    }

    override fun newInstance(`object`: Any): Quest {
        return this
    }
}