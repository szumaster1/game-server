package content.region.kandarin.quest.thegrandtree.dialogue

import core.api.*
import core.api.consts.NPCs
import core.game.dialogue.DialogueFile
import core.game.dialogue.FacialExpression
import core.game.node.entity.npc.NPC
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.utilities.END_DIALOGUE

class GloughDialogueFile : DialogueFile() {

    override fun handle(componentID: Int, buttonID: Int) {
        npc = NPC(NPCs.GLOUGH_671)
        when (getQuestStage(player!!, "The Grand Tree")) {
            40 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello.").also { stage++ }
                    1 -> sendDialogue(player!!, "The gnome is munching on a worm hole.").also { stage++ }
                    2 -> npcl(FacialExpression.OLD_DEFAULT, "Can I help human? Can't you see I'm eating?!").also { stage++ }
                    3 -> sendDialogue(player!!, "The gnome continues to eat.").also { stage++ }
                    4 -> playerl(FacialExpression.FRIENDLY, "The King asked me to inform you that the Daconia rocks have been taken!").also { stage++ }
                    5 -> npcl(FacialExpression.OLD_DEFAULT, "Surely not!").also { stage++ }
                    6 -> playerl(FacialExpression.FRIENDLY, "Apparently a human took them from Hazelmere. Hazelmere believed him; he had the King's seal!").also { stage++ }
                    7 -> npcl(FacialExpression.OLD_DEFAULT, "I should've known! The humans are going to invade!").also { stage++ }
                    8 -> playerl(FacialExpression.FRIENDLY, "Never!").also { stage++ }
                    9 -> npcl(FacialExpression.OLD_DEFAULT, "Your type can't be trusted! I'll take care of this! Go back to the King.").also {
                        setQuestStage(player!!, "The Grand Tree", 45)
                        stage = END_DIALOGUE
                    }
                }
            }

            47 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Glough! I don't know what you're up to but I know you paid Charlie to get those rocks!").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "You're a fool human! You have no idea what's going on.").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "I know the Grand Tree's dying! And I think you're part of the reason.").also { stage++ }
                    3 -> npcl(FacialExpression.OLD_DEFAULT, "How dare you accuse me! I'm the head tree guardian! Guards! Guards!").also { stage++ }
                    4 -> {
                        GameWorld.Pulser.submit(object : Pulse(0) {
                            var count = 0
                            val npc = NPC.create(163, Location.create(2477, 3462, 1), null)
                            val ladderClimbAnimation = Animation(828)
                            val cell = Location.create(2464, 3496, 3)
                            override fun pulse(): Boolean {
                                when (count) {
                                    0 -> {
                                        // Spawn in the gnome guard
                                        lock(player!!, 10)
                                        npc.init()
                                        forceWalk(npc, player!!.location.transform(Direction.WEST, 2), "SMART")
                                        player!!.dialogueInterpreter.sendDialogues(
                                            npc,
                                            FacialExpression.OLD_ANGRY1,
                                            "Come with me!"
                                        )
                                    }// Walk to the ladder
                                    2 -> forceWalk(npc, Location.create(2476, 3462, 1), "SMART")
                                    4 -> {
                                        unlock(player!!)
                                        forceWalk(player!!, Location.create(2477, 3463, 1), "SMART")
                                    }

                                    6 -> player!!.faceLocation(Location.create(2476, 3463, 1))
                                    7 -> animate(player!!, ladderClimbAnimation)
                                    8 -> {
                                        npc.clear()
                                        setQuestStage(player!!, "The Grand Tree", 50)
                                        teleport(player!!, cell)
                                        player!!.unlock()
                                        return true
                                    }
                                }
                                count++
                                return false
                            }
                        }).also { stage = END_DIALOGUE }
                    }
                }
            }

            55 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "I know what you're up to Glough!").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "You have no idea human!").also { stage++ }
                    2 -> playerl(
                        FacialExpression.FRIENDLY,
                        "You may be able to make a fleet but the tree gnomes will never follow you into battle against humans."
                    ).also { stage++ }

                    3 -> npcl(
                        FacialExpression.OLD_DEFAULT,
                        "So, you know more than I thought! The gnomes fear humanity more than any other race. I just need to give them a push in the right direction. There's nothing you can do traveller! Leave before it's too late!"
                    ).also { stage++ }

                    4 -> playerl(FacialExpression.FRIENDLY, "King Narnode won't allow it!").also { stage++ }
                    5 -> npcl(
                        FacialExpression.OLD_DEFAULT,
                        "The King's a fool and a coward! He'll bow to me! You'll soon be back in that cage!"
                    ).also { stage = END_DIALOGUE }
                }
            }

            60 -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "I'm going to stop you, Glough!").also { stage++ }
                    1 -> npcl(
                        FacialExpression.OLD_DEFAULT,
                        "You're becoming quite annoying traveller!"
                    ).also { stage++ }

                    2 -> npcl(FacialExpression.OLD_DEFAULT, "Glough is searching his pockets.").also { stage++ }
                    3 -> npcl(FacialExpression.OLD_DEFAULT, "Where are that darn key?").also { stage++ }
                    4 -> npcl(
                        FacialExpression.OLD_DEFAULT,
                        "Leave human, before I have you put in the cage!"
                    ).also { stage = END_DIALOGUE }
                }
            }

            else -> {
                when (stage) {
                    0 -> playerl(FacialExpression.FRIENDLY, "Hello there!").also { stage++ }
                    1 -> npcl(FacialExpression.OLD_DEFAULT, "You shouldn't be here human!").also { stage++ }
                    2 -> playerl(FacialExpression.FRIENDLY, "What do you mean?").also { stage++ }
                    3 -> npcl(
                        FacialExpression.OLD_DEFAULT,
                        "The Gnome Stronghold is for gnomes alone!"
                    ).also { stage++ }

                    4 -> playerl(FacialExpression.FRIENDLY, "Surely not!").also { stage++ }
                    5 -> npcl(FacialExpression.OLD_DEFAULT, "We don't need your sort round here!").also {
                        stage = END_DIALOGUE
                    }
                }
            }
        }
    }

}
