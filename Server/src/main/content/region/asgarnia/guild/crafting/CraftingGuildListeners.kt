package content.region.asgarnia.guild.crafting

import core.api.anyInEquipment
import core.api.consts.Items
import core.api.consts.Scenery
import core.api.hasLevelStat
import core.api.inEquipment
import core.api.openDialogue
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.world.map.Location

class CraftingGuildListeners : InteractionListener {

    private val guildDoor = Scenery.GUILD_DOOR_2647
    private val brownApron = Items.BROWN_APRON_1757
    private val craftingSkillcape = intArrayOf(Items.CRAFTING_CAPE_9780, Items.CRAFTING_CAPET_9781)

    override fun defineListeners() {
        on(guildDoor, IntType.SCENERY, "open") { player, door ->
            if (player.location == Location.create(2933, 3289, 0)) {
                if (hasLevelStat(player, Skills.CRAFTING, 40)) {
                    if (inEquipment(player, brownApron)) {
                        openDialogue(player, CraftingGuildDoor(0))
                        DoorActionHandler.handleAutowalkDoor(player, door.asScenery())
                        return@on true
                    } else if (anyInEquipment(player, *craftingSkillcape)) {
                        openDialogue(player, CraftingGuildDoor(0))
                        DoorActionHandler.handleAutowalkDoor(player, door.asScenery())
                        return@on true
                    } else {
                        openDialogue(player, CraftingGuildDoor(1))
                        return@on false
                    }
                } else {
                    openDialogue(player, CraftingGuildDoor(2))
                    return@on false
                }
            } else {
                DoorActionHandler.handleAutowalkDoor(player, door.asScenery())
                return@on true
            }
        }
    }
}
