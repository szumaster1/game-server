package content.region.misthalin.dialogue.wizardstower

import core.api.consts.NPCs
import core.api.consts.Sounds
import core.api.*
import core.game.dialogue.Dialogue
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable

/**
 * Isidor dialogue.
 */
@Initializable
class IsidorDialogue(player: Player? = null) : Dialogue(player) {

    override fun open(vararg args: Any): Boolean {
        npc = args[0] as NPC
        npc("Oh, hello there! Can I do anything for you?")
        return true
    }

    override fun handle(interfaceId: Int, buttonId: Int): Boolean {
        when (stage) {
            0 -> {
                player("I'm a friend of Explorer Jack and need teleporting", "somewhere. The phrase is 'Ectosum glissendo'. Could", "you help me, please?")
                stage = 1
            }

            1 -> if (!player.familiarManager.hasFamiliar()) {
                npc("Oh, okay then! There you go.")
                stage = 2
            } else {
                npc("I can teleport you, but not any pets or followers you", "may have.")
                stage = 100
            }

            2 -> {
                end()
                npc.animate(Animation(437))
                npc.faceTemporary(player, 1)
                npc.graphics(Graphic(108))
                lock(player, 4)
                playAudio(player, Sounds.CURSE_ALL_125, 0, 1)
                Projectile.create(npc, player, 109).send()
                npc.sendChat("Ectosum glissendo!")
                GameWorld.Pulser.submit(object : Pulse(1) {
                    var counter = 0
                    override fun pulse(): Boolean {
                        when (counter++) {
                            0 -> {
                                lock(player, 2)
                                player.graphics(
                                    Graphic(
                                        110,
                                        150
                                    )
                                )
                            }

                            1 -> {
                                teleport(player, location(2070, 5802, 0))
                                player.graphics(
                                    Graphic(
                                        110,
                                        150
                                    )
                                )
                                unlock(player)
                                return true
                            }
                        }
                        return false
                    }
                })
            }

            100 -> end()
        }
        return true
    }

    override fun newInstance(player: Player): Dialogue {
        return IsidorDialogue(player)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.ISIDOR_8544)
    }

}

//Explorer Jack: Aha, you're back. Do you have a query about the Tasks in the Lumbridge set? Or, perhaps I know of a quest that might interest you.
//
//1. Tell me about the Task System. Irrelevant to the quest.
//
//2. You mentioned a quest? Explorer Jack: Ah, spiffing. I can see the exploring fibre in you!
//
//Er...
//
//Explorer Jack: Oh, that exploring flame in your eyes! Just like me in my young days - eager to discover and take on new challenges, eh?
//
//Um... so you're going to give me a quest?
//
//Explorer Jack: A quest? But indeed! A tale of riches is what you need, what?
//
//Is it?
//
//Explorer Jack: Yes, and I have just the thing. A snow imp wandered through my door one day and I, curious about foreign lands, as every good explorer should be, asked him for tales of his homeland.
//
//And?
//
//Explorer Jack: What a tale! He told me of priceless riches and knowledge hidden in the frozen caves of the Land of Snow; he also mentioned dark sleek stones protected by a fearsome creature. Apparently they are very precious, and would Explorer Jack: make a fine addition to any explorer's collection. Explorer Jack: So, what do you say, chum? Will you get those precious stones for me?
//
//(Quest overview interface appears)
//
//(If denying)
//
//Explorer Jack: Oh, don't underestimate yourself. Do come back if you change your mind, eh?
//
//(If accepting)
//
//Explorer Jack: Splendid! Now, do you have any questions?
//
//2.1. How do I get to the Land of Snow? Explorer Jack: Ah, a good question indeed! I have an acquaintance who now lives in the Wizards' Tower. He's called Isidor. He'll take you there as long as you give him the teleport phrase, but you'll have to remind him everytime. The poor Explorer Jack: chap has a memory like a sieve! That's what you get for experimenting with magic all day, eh?
//
//What teleport phrase?
//
//Explorer Jack: Oh, yes, of course. "Ectosum glissendo". That's what the snow imp said.
//
//2.2. Are there any rewards? Explorer Jack: Yes, indeed. I have a whole pile of old lamps I got from a certain Ali. I can probably give you one if you're into antiques.
//
//Is that all? For stones so precious?
//
//Explorer Jack: We'll see, once I see how valuable they are - if you do manage to find them.
//
//2.3. How do you know the imp's tale is true? Explorer Jack: Ah, I could see the truthfulness in his eyes - plus he explained how to get to the place. If the place exists, so must the riches. Explorer Jack: Could it have been greed in his eyes? Explorer Jack: Well, if it was greed, then those riches are definitely valuable. Bring them back, eh?
//
//2.4. Actually, never mind. (Closes dialogue)
//
//3. What happened to your house? Irrelevant to the quest.
//
//4. Nothing. (Closes dialogue)
//
//(When talking to Wizard Isidor)
//
//Wizard Isidor: Oh, hello there! Can I do anything for you?
//
//1. What do you do? Wizard Isidor: I'm a wizard, obviously. I work on portals and teleportation.
//
//1.1. Can you teleport me anywhere? Wizard Isidor: In theory I could, if I had a precise means of fixing the destination - a teleport phrase, for example. Right now I'm working on something else, though.
//
//1.2. Never mind. (Closes dialogue)
//
//2. I'm a friend of Explorer Jack and need teleporting. I'm a friend of Explorer Jack and need teleporting somewhere. The phrase is 'Ectosum glissendo'. Could you help me, please?
//
//Wizard Isidor: Oh, very well then! Here you go...
//
//"Ectossum glissendo!"
//
//(A short cutscene appears after the player is teleported, showing the entrance to a cave in the Land of Snow)
//
//3. Never mind. (Closes dialogue)
//
//(After reaching the end of the cave, another cutscene will play where the yeti's scream echoes and the cave quakes lightly, making the player slide back one room)
//
//(If trying to clear the wall before the last room)
//
//This must be the hidden knowledge Explorer Jack was talking about. I should probably focus on getting the stones to Explorer Jack first, though.
//
//(When shouting through the pipe)
//
//"Hey!" "Heeeeeeeeey!"
//
//(After obtaining the yeti stones and exiting the cave)
//
//Benny: Hey, guys, there he/she is! Marius: Yer right! M: What's that in yer bag? Marvin: I know!
//
//What's going on here?
//
//Mv: We pranked ya! M: It's quite funny. B: Haha!
//
//Wait a second. Who are you?
//
//M: We's snow imps! B: Marius 'ere got questioned by a nosy, greedy and generally annoying explorer when we was on mission in RuneScape. Mv: I's teching 'im a lesson: ye don't bovver snow imps! M: I told the explorer about very valuable riches here in the Land of Snow. Mv: But, in fact, the riches are just yeti dung!
//
//Eeeewwww! Gross!
//
//Mv: They's all right now, but let them thaw and it will be worst smell ever. Haha! B: He sent ya though, and yer not so annoying.
//
//Er, thanks, I guess.
//
//M: So, we's telling ya the truth. Also, we help ya; otherwise, it's you who'll smell! M: 'E smells already, tho.
//
//Oi!
//
//B: We's gonna enchant the dung so it only thaws when ya give it to someone else, and so ya can teleport with it. M: 'Cause, if you try ta teleport outta here without it being enchanted, at best it won't let you, and at worst it'll vanish! Weird stuff, yeti dung. M: There ya go! Teach that explorer a good lesson.
//
//(When talking to Explorer Jack again)
//
//Explorer Jack: Aha, you're back. How can I help you?
//
//1. Tell me about the Task System. Irrelevant to the quest.
//
//2. About Myths of the White Lands... Explorer Jack: Aha! Welcome back. Have you found the stones?
//
//I have indeed! Look at them!
//
//Explorer Jack: They look fascinating! They're very cold, too.
//
//2.1. Tell the truth about what the stones are. -
//
//2.2. Carry on with the imps' prank. Explorer Jack: Oh! Flabbergasting!
//
//I just remembered, I must be off. I'm late for a...very important event. Somewhere else. Did you mention a reward?
//
//Explorer Jack: Yes, indeed! Please take this old lamp. I'll study the stones and, if they are valuable, I might have more rewards for you!
//
//Congratulations! Quest complete!
//
//3. What happened to your house? Irrelevant to the quest.
//
//4. Nothing. (Closes dialogue)
//
//Post-quest dialogue
//
//Explorer Jack: Aha, you're back. How can I help you?
//
//1. Tell me about the Task System. Irrelevant to the quest.
//
//2. About Myths of the White Lands... Explorer Jack: Eh, I haven't made any progress with the stones. Please come back later.
//
//3. What happened to your house? Irrelevant to the quest.
//
//4. Nothing. (Closes dialogue)