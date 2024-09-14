package content.global.skill.combat.summoning.familiar

import content.global.skill.combat.summoning.SummoningPouch.Companion.get
import content.global.skill.combat.summoning.pet.Pet
import content.global.skill.combat.summoning.pet.PetDetails
import content.global.skill.combat.summoning.pet.Pets.Companion.forId
import core.api.getVarp
import core.api.log
import core.api.setVarp
import core.cache.def.impl.ItemDefinition
import core.game.component.Component
import core.game.node.entity.combat.BattleState
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.map.zone.ZoneRestriction
import core.game.world.update.flag.context.Animation
import core.tools.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 * Familiar manager.
 */
class FamiliarManager
/**
 * Instantiates a new Familiar manager.
 *
 * @param player the player
 */(private val player: Player) {
    private val petDetails: MutableMap<Int, PetDetails> = HashMap()
    /**
     * Gets the familiar.
     *
     * @return the familiar
     */
    /**
     * Sets the familiar.
     *
     * @param familiar the familiar
     */
    @JvmField
    var familiar: Familiar? = null
    /**
     * Gets the summoningCombatLevel.
     *
     * @return the summoning combat level
     */
    /**
     * Sets the summoningCombatLevel.
     *
     * @param summoningCombatLevel the summoning combat level
     */
    @JvmField
    var summoningCombatLevel: Int = 0
    /**
     * Gets the hasPouch.
     *
     * @return the boolean
     */
    /**
     * Sets the hasPouch.
     *
     * @param hasPouch the has pouch
     */
    var isHasPouch: Boolean = false

    /**
     * Parse.
     *
     * @param familiarData the familiar data
     */
    fun parse(familiarData: JSONObject) {
        var currentPet = -1
        if (familiarData.containsKey("currentPet")) {
            currentPet = familiarData["currentPet"].toString().toInt()
        }
        val petDetails = familiarData["petDetails"] as JSONArray
        for (i in petDetails.indices) {
            val detail = petDetails[i] as JSONObject
            val details = PetDetails(0.0)
            details.updateHunger(detail["hunger"].toString().toDouble())
            details.updateGrowth(detail["growth"].toString().toDouble())
            var itemIdHash = detail["petId"].toString().toInt()
            if (detail.containsKey("stage")) {
                val babyItemId = itemIdHash
                var itemId = babyItemId
                val stage = detail["stage"].toString().toInt()
                if (stage > 0) {
                    val pets = forId(babyItemId)
                    itemId = pets!!.getNextStageItemId(itemId)
                    if (stage > 1) {
                        itemId = pets.getNextStageItemId(itemId)
                    }
                }
                val item = Item(itemId)
                item.charge = 1000 //this is the default value that will correspond to the player's item
                itemIdHash = item.idHash
                if (currentPet != -1 && currentPet == babyItemId) {
                    currentPet = itemIdHash
                }
            }
            this.petDetails[itemIdHash] = details
        }

        if (currentPet != -1) {
            var details = this.petDetails[currentPet]
            val itemId = currentPet shr 16 and 0xFFFF
            val pets = forId(itemId)
            if (details == null) {
                details = PetDetails(if (pets!!.growthRate == 0.0) 100.0 else 0.0)
                this.petDetails[currentPet] = details
            }
            familiar = Pet(player, details, itemId, pets!!.getNpcId(itemId))
        } else if (familiarData.containsKey("familiar")) {
            val currentFamiliar = familiarData["familiar"] as JSONObject
            val familiarId = currentFamiliar["originalId"].toString().toInt()
            familiar = familiars[familiarId]!!.construct(player, familiarId)
            familiar!!.ticks = currentFamiliar["ticks"].toString().toInt()
            familiar!!.specialPoints = currentFamiliar["specialPoints"].toString().toInt()
            val famInv = currentFamiliar["inventory"] as JSONArray?
            if (famInv != null) {
                (familiar as BurdenBeast).container.parse(famInv)
            }
            familiar!!.setAttribute("hp", currentFamiliar["lifepoints"].toString().toInt())
        }
    }

    /**
     * Login.
     */
    fun login() {
        if (hasFamiliar()) {
            familiar!!.init()
        }
        player.familiarManager.setConfig(243269632)
    }

    /**
     * Summon.
     *
     * @param item       the item
     * @param pet        the pet
     * @param deleteItem the delete item
     */
    /**
     * Summon.
     *
     * @param item the item
     * @param pet  the pet
     */
    @JvmOverloads
    fun summon(item: Item, pet: Boolean, deleteItem: Boolean = true) {
        var renew = false
        if (hasFamiliar()) {
            if (familiar!!.pouchId == item.id) {
                renew = true
            } else {
                player.packetDispatch.sendMessage("You already have a follower.")
                return
            }
        }
        if (player.zoneMonitor.isRestricted(ZoneRestriction.FOLLOWERS) && !player.locks.isLocked("enable_summoning")) {
            player.packetDispatch.sendMessage("This is a Summoning-free area.")
            return
        }
        if (pet) {
            summonPet(item, deleteItem)
            return
        }
        val pouch = get(item.id) ?: return
        if (player.getSkills().getStaticLevel(Skills.SUMMONING) < pouch.levelRequired) {
            player.packetDispatch.sendMessage("You need a Summoning level of " + pouch.levelRequired + " to summon this familiar.")
            return
        }
        if (player.getSkills().getLevel(Skills.SUMMONING) < pouch.summonCost) {
            player.packetDispatch.sendMessage("You need at least " + pouch.summonCost + " Summoning points to summon this familiar.")
            return
        }
        val npcId = pouch.npcId
        var fam = if (!renew) familiars[npcId] else familiar
        if (fam == null) {
            player.packetDispatch.sendMessage("Nothing interesting happens.")
            log(this.javaClass, Log.ERR, "Invalid familiar: $npcId.")
            return
        }
        if (!renew) {
            fam = fam.construct(player, npcId)
            if (fam!!.spawnLocation == null) {
                player.packetDispatch.sendMessage("The spirit in this pouch is too big to summon here. You will need to move to a larger")
                player.packetDispatch.sendMessage("area.")
                return
            }
        }
        if (!player.inventory.remove(item)) {
            return
        }
        player.getSkills().updateLevel(Skills.SUMMONING, -pouch.summonCost, 0)
        player.getSkills().addExperience(Skills.SUMMONING, pouch.summonExperience)
        if (!renew) {
            familiar = fam
            spawnFamiliar()
        } else {
            familiar!!.refreshTimer()
        }
        player.appearance.sync()
    }

    /**
     * Morph pet.
     *
     * @param item       the item
     * @param deleteItem the delete item
     * @param location   the location
     */
    fun morphPet(item: Item, deleteItem: Boolean, location: Location?) {
        if (hasFamiliar()) {
            familiar!!.dismiss()
        }
        summonPet(item, deleteItem, true, location)
    }

    private fun summonPet(item: Item, deleteItem: Boolean, morph: Boolean = false, location: Location? = null): Boolean {
        val itemId = item.id
        val itemIdHash = item.idHash
        if (itemId > 8850 && itemId < 8900) {
            return false
        }
        val pets = forId(itemId) ?: return false
        if (player.getSkills().getStaticLevel(Skills.SUMMONING) < pets.summoningLevel) {
            player.dialogueInterpreter.sendDialogue("You need a summoning level of " + pets.summoningLevel + " to summon this.")
            return false
        }

        /*
         * If this pet does not have an individual ID yet, we need to find it an available one.
         *
         * If it does, we need to verify that this ID is not already used for a different pet.
         * This is needed to correct a historical bug that allowed multiple pets to be assigned
         * the same individual ID (the historical code only checked the *current* stage item ID,
         * failing to realize that we also need to account for *future* stage item IDs, in case
         * the current pet grows up, resulting in a clash when it did). Saves affected by that
         * bug will have multiple copies of the same item pointing to the same pet, which we
         * have an opportunity to rectify now.
         */
        val taken = ArrayList<Int>()
        val searchSpace = arrayOf(
            player.inventory, player.bankPrimary, player.bankSecondary
        )
        var checkId = pets.babyItemId
        while (checkId != -1) {
            val check = Item(checkId, 1)
            for (container in searchSpace) {
                for (i in container.getAll(check)) {
                    taken.add(i.charge)
                }
            }
            checkId = pets.getNextStageItemId(checkId)
        }
        var details = petDetails[itemIdHash]
        var individual = item.charge
        if (details != null) {
            /*
             * we have this pet on file, but we need to check that it
             * wasn't affected by the historical bug mentioned above.
             */
            details.individual = individual
            var count = 0
            for (i in taken) {
                if (i == individual) {
                    count++
                }
            }
            if (count > 1) {
                /*
                 * this pet is sadly conjoined with another individual of its kind;
                 * untangle it by initializing it anew (which is what should have
                 * happened in the first place, save the minor detail of hunger
                 * propagation from the previous stage, which we no longer have any record of).
                 */
                details = null
            }
        }
        if (details == null) { //init new pet
            details = PetDetails(if (pets.growthRate == 0.0) 100.0 else 0.0)
            individual = 0
            while (taken.contains(individual) && individual < 0xFFFF) {
                individual++
            }
            details.individual = individual
            /*
             * Make a copy of the item to extract what the item's idHash
             * will be when including the individual ID as a "charge" value.
             *
             * The copy is necessary since the player's inventory still contains the
             * default-charged item, which we will be removing only later.
             */
            val newItem = item.copy()
            newItem.charge = individual
            petDetails[newItem.idHash] = details
        }
        val npcId = pets.getNpcId(itemId)
        if (npcId > 0) {
            familiar = Pet(player, details, itemId, npcId)
            if (deleteItem) {
                player.animate(Animation(827))
                /*
                 * We cannot use player().getInventory().remove(item), because that will remove
                 * the first pet item it sees, rather than the specific one
                 * (with the specific charge value) the player clicked.
                 */
                val slot = player.inventory.getSlotHash(item)
                /*
                 * Instead, find the specific item the player dropped by slot, and remove that specific one.
                 */
                player.inventory.remove(item, slot, true)
            }
            if (morph) {
                morphFamiliar(location!!)
            } else {
                spawnFamiliar()
            }
            return true
        }
        return true
    }

    /**
     * Morphs the current familiar.
     *
     * @param location the location
     */
    fun morphFamiliar(location: Location) {
        familiar!!.init(location, false)
        player.interfaceManager.openTab(Component(662))
        player.interfaceManager.setViewedTab(7)
    }

    /**
     * Spawns the current familiar.
     */
    fun spawnFamiliar() {
        familiar!!.init()
        player.interfaceManager.openTab(Component(662))
        player.interfaceManager.setViewedTab(7)
    }

    /**
     * Makes the pet eat.
     *
     * @param foodId the food id
     * @param npc    the npc
     */
    fun eat(foodId: Int, npc: Pet) {
        if (npc != familiar) {
            player.packetDispatch.sendMessage("This isn't your pet!")
            return
        }
        val pet = familiar as Pet?
        val pets = forId(pet!!.itemId) ?: return
        for (food in pets.food) {
            if (food == foodId) {
                player.inventory.remove(Item(foodId))
                player.packetDispatch.sendMessage("Your pet happily eats the " + ItemDefinition.forId(food).name + ".")
                player.animate(Animation(827))
                npc.details.updateHunger(-15.0)
                return
            }
        }
        player.packetDispatch.sendMessage("Nothing interesting happens.")
    }

    /**
     * Picks up a pet.
     */
    fun pickup() {
        if (player.inventory.freeSlots() == 0) {
            player.packetDispatch.sendMessage("You don't have enough room in your inventory.")
            return
        }
        val pet = (familiar as Pet?)
        val details = pet!!.details
        val petItem = Item(pet.itemId)
        petItem.charge = details.individual
        if (player.inventory.add(petItem)) {
            petDetails[pet.itemIdHash] = details
            player.animate(Animation.create(827))
            player.familiarManager.dismiss()
        }
    }

    /**
     * Adjusts the battle state.
     *
     * @param state the state
     */
    fun adjustBattleState(state: BattleState?) {
        if (!hasFamiliar()) {
            return
        }
        familiar!!.adjustPlayerBattle(state)
    }

    /**
     * Gets a boost from a familiar.
     *
     * @param skill the skill
     * @return the boost
     */
    fun getBoost(skill: Int): Int {
        if (!hasFamiliar()) {
            return 0
        }
        return familiar!!.getBoost(skill)
    }

    /**
     * Checks if the player has an active familiar.
     *
     * @return the boolean
     */
    fun hasFamiliar(): Boolean {
        return familiar != null
    }

    /**
     * Checks if the player has an active familiar and is a pet.
     *
     * @return the boolean
     */
    fun hasPet(): Boolean {
        return hasFamiliar() && familiar is Pet
    }

    /**
     * Dismisses the familiar.
     */
    fun dismiss() {
        if (hasFamiliar()) {
            familiar!!.dismiss()
        }
    }

    /**
     * Removes the details for this pet.
     *
     * @param itemIdHash the item id hash
     */
    fun removeDetails(itemIdHash: Int) {
        petDetails.remove(itemIdHash)
    }

    /**
     * Checks if it's the owner of a familiar.
     *
     * @param familiar the familiar
     * @return the boolean
     */
    fun isOwner(familiar: Familiar): Boolean {
        if (!hasFamiliar()) {
            return false
        }
        if (this.familiar !== familiar) {
            player.packetDispatch.sendMessage("This is not your familiar.")
            return false
        }
        return true
    }

    /**
     * Sets a config value.
     *
     * @param value the value
     */
    fun setConfig(value: Int) {
        val current = getVarp(player, 1160)
        val newVal = current + value
        setVarp(player, 1160, newVal)
    }

    val isUsingSummoning: Boolean
        /**
         * Gets the usingSummoning.
         *
         * @return the boolean
         */
        get() = isHasPouch || (hasFamiliar() && !hasPet())

    /**
     * Gets pet details.
     *
     * @return the pet details
     */
    fun getPetDetails(): Map<Int, PetDetails> {
        return petDetails
    }

    companion object {
        /**
         * Gets the familiars.
         *
         * @return the familiars
         */
        val familiars: Map<Int, Familiar> = HashMap()
    }
}
