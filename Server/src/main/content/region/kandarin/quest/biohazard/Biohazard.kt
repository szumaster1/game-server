package content.region.kandarin.quest.biohazard

import content.region.kandarin.quest.biohazard.util.BiohazardUtils
import core.api.consts.Components
import core.api.consts.Items
import core.api.consts.Vars
import core.api.isQuestComplete
import core.api.removeAttributes
import core.api.rewardXP
import core.api.sendItemZoomOnInterface
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.quest.Quest
import core.game.node.entity.skill.Skills
import core.plugin.Initializable

@Initializable
class Biohazard : Quest("Biohazard", 36, 35, 3, Vars.VARP_QUEST_BIOHAZARD_PROGRESS, 0, 1, 16) {

    // Base created by Bushtail.
    // https://gitlab.com/bushtail

    override fun drawJournal(player: Player?, stage: Int) {
        super.drawJournal(player, stage)
        var line = 11
        player ?: return

        if (stage == 0) {
            line(player, "I can start this quest by speaking to !!Elena?? who is in !!East??", line++)
            line(player, "!!Ardougne??.", line++)
            line++
            line(player, "Requirements:", line++)
            line(
                player,
                "!!I need to complete Plague City before I can attempt this??",
                line++,
                isQuestComplete(player, "Plague City")
            )
            line(player, "!!Quest??.", line++, isQuestComplete(player, "Plague City"))
        } else {
            if (stage > 0) {
                line(player, "I've spoken to Elena in East Ardougne. She told me that", line++, true)
                line(player, "the Mourners confiscated her Distillator while she was", line++, true)
                line(player, "helping plague victims in West Ardougne. She's asked me", line++, true)
                line(player, "to help her recover it.", line++, true)
            }

            if (stage == 1) {
                line(player, "I need to get into !!West Ardougne?? to find !!Elena's Distillator??.", line++)
                line(player, "However, the !!Mourners?? have blocked the tunnel I used", line++)
                line(player, "before. !!Elena?? sugested I talk to !!Jerico??, an old friend of", line++)
                line(player, "her fathers, and ask for his help in crossing the wall. His", line++)
                line(player, "home is north of the chapel in !!East Ardougne??.", line++)
            } else if (stage > 1) {
                line(player, "The Mourners blocked the tunnel I used to enter West", line++, true)
                line(player, "Ardougne before. However, I spoke to Jerico, an old friend", line++, true)
                line(player, "of Elena's father, about finding another way into the city.", line++, true)
            }

            if (stage == 2) {
                line(player, "!!Jerico?? told me that !!Omart??, a friend of his, should be able", line++)
                line(player, "to help me get into !!West Ardougne??. He is waiting for me at", line++)
                line(player, "the southern end of the wall.", line++)
            }

            if (stage == 2) {
                line(player, "!!Omart??, a friend of !!Jerico??, has agreed to help me cross the", line++)
                line(player, "wall into !!West Ardougne??. However, I need to distract the", line++)
                line(player, "guards in the !!Watchtower?? first. Following the suggestion", line++)
                line(player, "of !!Omart??, I threw some !!Bird Feed?? near the tower. Now I just", line++)
                line(player, "need to release some !!Pigeons?? nearby and they should", line++)
                line(player, "distract the guards. !!Jerico?? should be able to supply the", line++)
                line(player, "!!Pigeons??.", line++)
            } else if (stage == 3) {
                line(player, "!!Omart??, a friend of !!Jerico??, has agreed to help me cross the", line++)
                line(player, "wall into !!West Ardougne??. He is waiting for me at the", line++)
                line(player, "southern end of the wall.", line++)
            } else if (stage > 3) {
                line(player, "Omart and Kilron, friends of Jerico, helped me cross the", line++, true)
                line(player, "wall into West Ardougne.", line++, true)
            }

            if (stage == 4) {
                line(player, "!!Elena's Distillator?? should be somewhere in the !!Mourner??", line++)
                line(player, "!!Headquarters?? in the north east of !!West Ardougne??. I need", line++)
                line(player, "to get in and recover it.", line++)
            } else if (stage == 7) {
                line(player, "!!Elena's Distillator?? should be somewhere in the !!Mourner??", line++)
                line(player, "!!Headquarters?? in the north east of !!West Ardougne??. I need", line++)
                line(player, "to get in and recover it. I used a !!Rotten Apple?? to poison", line++)
                line(player, "the !!Mourner's Stew?? which might help me.", line++)
            }

            if (stage == 8) {
                line(player, "I managed to get into the !!Mourner Headquarters?? in !!West??", line++)
                line(player, "!!Ardougne??. I should see if I can find !!Elena's Distillator??", line++)
                line(player, "inside.", line++)
            } else if (stage == 10) {
                line(player, "I managed to get into the !!Mourner Headquarters?? in !!West??", line++)
                line(player, "!!Ardougne??. I found !!Elena's Distillator?? inside. I should return", line++)
                line(player, "it to her.", line++)
            } else if (stage > 10) {
                line(player, "I managed to get into the Mourner Headquarters in West", line++, true)
                line(player, "Ardougne. I found Elena's Distillator and returned it", line++, true)
                line(player, "to her.", line++, true)
            }

            if (stage == 11) {
                line(player, "!!Elena?? ran some tests on a !!Plague Sample?? using her", line++)
                line(player, "!!Distillator??. However, she wasn't able to discover anything.", line++)
                line(player, "She asked me to go to the !!Chemist?? in !!Rimmington?? to pick up", line++)
                line(player, "some !!Touch Paper?? before continuing on to take some", line++)
                line(player, "items to !!Guidor?? in !!Varrock??. She warned me that carrying", line++)
                line(player, "some of the items into !!Varrock?? might be problematic and", line++)
                line(player, "suggested I ask the !!Chemist?? for help with this.", line++)
            } else if (stage > 10) {
                line(player, "Elena ran some tests on a Plague Sample using her", line++, true)
                line(player, "Distillator. However, she wasn't able to discover anything.", line++, true)
            }

            if (stage >= 11) {
                line(player, "!!Elena?? asked me to take some items to !!Guidor?? in !!Varrock??.", line++)
                line(player, "I've been warned that carrying some of these items into", line++)
                line(player, "!!Varrock?? might be problematic. The !!Chemist?? in !!Rimmington??", line++)
                line(player, "recommended I ask his !!Errand Boys?? for help with this.", line++)
                line(player, "However, he warned me they like to steal things so", line++)
                line(player, "suggested I only give them something they won't have", line++)
                line(player, "need of themselves.", line++)
            }
            if (stage >= 15) {
                line(player, "!!Elena?? asked me to take some items to !!Guidor?? in !!Varrock??. I", line++, true)
                line(player, "was warned that carrying some of these items into !!Varrock??", line++, true)
                line(player, "might be problematic so I gave them to the !!Chemist's??", line++, true)
                line(player, "!!Errand Boys?? to smuggle in. I should meet them at the", line++, true)
                line(player, "!!Dancing Donkey Inn?? in south east !!Varrock??.", line++, true)
            }

            if (stage == 14) {
                line(player, "!!Elena?? asked me to take some items to !!Guidor??. I can find his", line++)
                line(player, "home in the south east corner of !!Varrock??.", line++)
            } else if (stage > 14) {
                line(player, "Elena asked me to take some items to Guidor in Varrock so", line++, true)
                line(player, "that he could run his own tests on the Plague Sample.", line++, true)
            }

            if (stage == 15) {
                line(player, "!!Guidor?? ran his own tests on the !!Plague Sample?? and", line++)
                line(player, "discovered a disturbing truth, that the !!Plague?? of !!West??", line++)
                line(player, "!!Ardougne?? is a lie. I should return to !!Elena?? and tell her.", line++)
            } else if (stage > 15) {
                line(player, "Guidor ran his tests and discovered a disturbing truth,", line++, true)
                line(player, "that the Plague of West Ardougne is a lie.", line++, true)
            }

            if (stage == 99) {
                line(player, "I returned to !!Elena?? and told her the truth about the !!Plague??.", line++)
                line(player, "She asked me to confront !!King Lathas?? immediately. I can", line++)
                line(player, "find him in !!Ardougne Castle??.", line)
            } else if (stage == 100) {
                line(player, "I confronted King Lathas and he revealed the truth to me.", line++, true)
                line(player, "The Plague is a hoax he created to keep people safe from", line++, true)
                line(player, "his brother, King Tyras, who was corrupted by the Dark", line++, true)
                line(player, "Lord on an expedition to the west. King Lathas rewarded", line++, true)
                line(player, "me and warned me to prepare for the challenges to come.", line++, true)
                line++
                line(player, "%%QUEST COMPLETE!&&", line)
            }
        }
    }

    override fun finish(player: Player?) {
        super.finish(player)
        player ?: return
        var line = 10
        sendItemZoomOnInterface(player, Components.QUEST_COMPLETE_SCROLL_277, 5, Items.DISTILLATOR_420, 230)
        drawReward(player, "3 Quest Points", line++)
        drawReward(player, "1250 Thieving XP", line)
        rewardXP(player, Skills.THIEVING, 1250.0)
        removeAttributes(player, BiohazardUtils.FEED_ON_FENCE, BiohazardUtils.ELENA_REPLACE)
    }

    override fun newInstance(`object`: Any?): Quest {
        return this
    }

}