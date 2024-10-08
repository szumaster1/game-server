package content.global.skill.summoning.familiar;

import content.global.skill.summoning.SummoningPouch;
import content.global.skill.summoning.pet.Pet;
import content.global.skill.summoning.pet.PetDetails;
import content.global.skill.summoning.pet.Pets;
import core.cache.def.impl.ItemDefinition;
import core.game.component.Component;
import core.game.container.Container;
import core.game.node.entity.combat.BattleState;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.world.map.Location;
import core.game.world.map.zone.ZoneRestriction;
import core.game.world.update.flag.context.Animation;
import core.tools.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static core.api.ContentAPIKt.*;

/**
 * Familiar manager.
 */
public final class FamiliarManager {
    private static final Map<Integer, Familiar> FAMILIARS = new HashMap<>();
    private final Map<Integer, PetDetails> petDetails = new HashMap<Integer, PetDetails>();
    private final Player player;
    private Familiar familiar;
    private int summoningCombatLevel;
    private boolean hasPouch;

    /**
     * Instantiates a new Familiar manager.
     *
     * @param player the player
     */
    public FamiliarManager(Player player) {
        this.player = player;
    }

    /**
     * Parse.
     *
     * @param familiarData the familiar data
     */
    public final void parse(JSONObject familiarData) {
        int currentPet = -1;
        if (familiarData.containsKey("currentPet")) {
            currentPet = Integer.parseInt(familiarData.get("currentPet").toString());
        }
        JSONArray petDetails = (JSONArray) familiarData.get("petDetails");
        for (int i = 0; i < petDetails.size(); i++) {
            JSONObject detail = (JSONObject) petDetails.get(i);
            PetDetails details = new PetDetails(0);
            details.updateHunger(Double.parseDouble(detail.get("hunger").toString()));
            details.updateGrowth(Double.parseDouble(detail.get("growth").toString()));
            int itemIdHash = Integer.parseInt(detail.get("petId").toString());
            if (detail.containsKey("stage")) {
                int babyItemId = itemIdHash;
                int itemId = babyItemId;
                int stage = Integer.parseInt(detail.get("stage").toString());
                if (stage > 0) {
                    Pets pets = Pets.forId(babyItemId);
                    itemId = pets.getNextStageItemId(itemId);
                    if (stage > 1) {
                        itemId = pets.getNextStageItemId(itemId);
                    }
                }
                Item item = new Item(itemId);
                item.setCharge(1000); //this is the default value that will correspond to the player's item
                itemIdHash = item.getIdHash();
                if (currentPet != -1 && currentPet == babyItemId) {
                    currentPet = itemIdHash;
                }
            }
            this.petDetails.put(itemIdHash, details);
        }

        if (currentPet != -1) {
            PetDetails details = this.petDetails.get(currentPet);
            int itemId = currentPet >> 16 & 0xFFFF;
            Pets pets = Pets.forId(itemId);
            if (details == null) {
                details = new PetDetails(pets.growthRate == 0.0 ? 100.0 : 0.0);
                this.petDetails.put(currentPet, details);
            }
            familiar = new Pet(player, details, itemId, pets.getNpcId(itemId));
        } else if (familiarData.containsKey("familiar")) {
            JSONObject currentFamiliar = (JSONObject) familiarData.get("familiar");
            int familiarId = Integer.parseInt( currentFamiliar.get("originalId").toString());
            familiar = FAMILIARS.get(familiarId).construct(player,familiarId);
            familiar.ticks = Integer.parseInt( currentFamiliar.get("ticks").toString());
            familiar.specialPoints = Integer.parseInt( currentFamiliar.get("specialPoints").toString());
            JSONArray famInv = (JSONArray) currentFamiliar.get("inventory");
            if (famInv != null) {
                ((BurdenBeast) familiar).container.parse(famInv);
            }
            familiar.setAttribute("hp",Integer.parseInt( currentFamiliar.get("lifepoints").toString()));
        }
    }

    /**
     * Login.
     */
    public void login() {
        if (hasFamiliar()) {
            familiar.init();
        }
        player.getFamiliarManager().setConfig(243269632);
    }

    /**
     * Summon.
     *
     * @param item       the item
     * @param pet        the pet
     * @param deleteItem the delete item
     */
    public void summon(Item item, boolean pet, boolean deleteItem) {
        boolean renew = false;
        if (hasFamiliar()) {
            if(familiar.getPouchId() == item.getId()) {
                renew = true;
            } else {
                player.getPacketDispatch().sendMessage("You already have a follower.");
                return;
            }
        }
        if (player.getZoneMonitor().isRestricted(ZoneRestriction.FOLLOWERS) && !player.getLocks().isLocked("enable_summoning")) {
            player.getPacketDispatch().sendMessage("This is a Summoning-free area.");
            return;
        }
        if (pet) {
            summonPet(item, deleteItem);
            return;
        }
        final SummoningPouch pouch = SummoningPouch.get(item.getId());
        if (pouch == null) {
            return;
        }
        if (player.getSkills().getStaticLevel(Skills.SUMMONING) < pouch.getLevelRequired()) {
            player.getPacketDispatch().sendMessage("You need a Summoning level of " + pouch.getLevelRequired() + " to summon this familiar.");
            return;
        }
        if (player.getSkills().getLevel(Skills.SUMMONING) < pouch.getSummonCost()) {
            player.getPacketDispatch().sendMessage("You need at least " + pouch.getSummonCost() + " Summoning points to summon this familiar.");
            return;
        }
        final int npcId = pouch.getNpcId();
        Familiar fam = !renew ? FAMILIARS.get(npcId) : familiar;
        if (fam == null) {
            player.getPacketDispatch().sendMessage("Nothing interesting happens.");
            log(this.getClass(), Log.ERR, "Invalid familiar: " + npcId + ".");
            return;
        }
        if(!renew) {
            fam = fam.construct(player, npcId);
            if (fam.getSpawnLocation() == null) {
                player.getPacketDispatch().sendMessage("The spirit in this pouch is too big to summon here. You will need to move to a larger");
                player.getPacketDispatch().sendMessage("area.");
                return;
            }
        }
        if (!player.getInventory().remove(item)) {
            return;
        }
        player.getSkills().updateLevel(Skills.SUMMONING, -pouch.getSummonCost(), 0);
        player.getSkills().addExperience(Skills.SUMMONING, pouch.getSummonExperience());
        if(!renew) {
            familiar = fam;
            spawnFamiliar();
        } else {
            familiar.refreshTimer();
        }
        player.getAppearance().sync();
    }

    /**
     * Summon.
     *
     * @param item the item
     * @param pet  the pet
     */
    public void summon(final Item item, boolean pet) {
        summon(item, pet, true);
    }

    /**
     * Morph pet.
     *
     * @param item       the item
     * @param deleteItem the delete item
     * @param location   the location
     */
    public void morphPet(final Item item, boolean deleteItem, Location location) {
        if (hasFamiliar()) {
            familiar.dismiss();
        }
        summonPet(item, deleteItem, true, location);
    }

    private boolean summonPet(final Item item, boolean deleteItem) {
        return summonPet(item, deleteItem, false, null);
    }

    private boolean summonPet(final Item item, boolean deleteItem, boolean morph, Location location) {
        final int itemId = item.getId();
        int itemIdHash = item.getIdHash();
        if (itemId > 8850 && itemId < 8900) {
            return false;
        }
        Pets pets = Pets.forId(itemId);
        if (pets == null) {
            return false;
        }
        if (player.getSkills().getStaticLevel(Skills.SUMMONING) < pets.summoningLevel) {
            player.getDialogueInterpreter().sendDialogue("You need a summoning level of " + pets.summoningLevel + " to summon this.");
            return false;
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
        ArrayList<Integer> taken = new ArrayList<Integer>();
        Container[] searchSpace = {player.getInventory(), player.getBankPrimary(), player.getBankSecondary()};
        for (int checkId = pets.babyItemId; checkId != -1; checkId = pets.getNextStageItemId(checkId)) {
            Item check = new Item(checkId, 1);
            for (Container container : searchSpace) {
                for (Item i : container.getAll(check)) {
                    taken.add(i.getCharge());
                }
            }
        }
        PetDetails details = petDetails.get(itemIdHash);
        int individual = item.getCharge();
        if (details != null) {
            /*
             * we have this pet on file, but we need to check that it
             * wasn't affected by the historical bug mentioned above.
             */
            details.setIndividual(individual);
            int count = 0;
            for (int i : taken) {
                if (i == individual) {
                    count++;
                }
            }
            if (count > 1) {
                /*
                 * this pet is sadly conjoined with another individual of its kind;
                 * untangle it by initializing it anew (which is what should have
                 * happened in the first place, save the minor detail of hunger
                 * propagation from the previous stage, which we no longer have any record of).
                 */
                details = null;
            }
        }
        if (details == null) { //init new pet
            details = new PetDetails(pets.growthRate == 0.0 ? 100.0 : 0.0);
            for (individual = 0; taken.contains(individual) && individual < 0xFFFF; individual++) {}
            details.setIndividual(individual);
            /*
             * Make a copy of the item to extract what the item's idHash
             * will be when including the individual ID as a "charge" value.
             *
             * The copy is necessary since the player's inventory still contains the
             * default-charged item, which we will be removing only later.
             */
            Item newItem = item.copy();
            newItem.setCharge(individual);
            petDetails.put(newItem.getIdHash(), details);
        }
        int npcId = pets.getNpcId(itemId);
        if (npcId > 0) {
            familiar = new Pet(player, details, itemId, npcId);
            if (deleteItem) {
                player.animate(new Animation(827));
                /*
                 * We cannot use player().getInventory().remove(item), because that will remove
                 * the first pet item it sees, rather than the specific one
                 * (with the specific charge value) the player clicked.
                 */
                int slot = player.getInventory().getSlotHash(item);
                /*
                 * Instead, find the specific item the player dropped by slot, and remove that specific one.
                 */
                player.getInventory().remove(item, slot, true);
            }
            if (morph) {
                morphFamiliar(location);
            } else {
                spawnFamiliar();
            }
            return true;
        }
        return true;
    }

    /**
     * Morphs the current familiar.
     *
     * @param location the location
     */
    public void morphFamiliar(Location location) {
        familiar.init(location, false);
        player.getInterfaceManager().openTab(new Component(662));
        player.getInterfaceManager().setViewedTab(7);
    }

    /**
     * Spawns the current familiar.
     */
    public void spawnFamiliar() {
        familiar.init();
        player.getInterfaceManager().openTab(new Component(662));
        player.getInterfaceManager().setViewedTab(7);
    }

    /**
     * Makes the pet eat.
     *
     * @param foodId the food id
     * @param npc    the npc
     */
    public void eat(int foodId, Pet npc) {
        if (npc != familiar) {
            player.getPacketDispatch().sendMessage("This isn't your pet!");
            return;
        }
        Pet pet = (Pet) familiar;
        Pets pets = Pets.forId(pet.getItemId());
        if (pets == null) {
            return;
        }
        for (int food : pets.food) {
            if (food == foodId) {
                player.getInventory().remove(new Item(foodId));
                player.getPacketDispatch().sendMessage("Your pet happily eats the " + ItemDefinition.forId(food).getName() + ".");
                player.animate(new Animation(827));
                npc.getDetails().updateHunger(-15.0);
                return;
            }
        }
        player.getPacketDispatch().sendMessage("Nothing interesting happens.");
    }

    /**
     * Picks up a pet.
     */
    public void pickup() {
        if (player.getInventory().freeSlots() == 0) {
            player.getPacketDispatch().sendMessage("You don't have enough room in your inventory.");
            return;
        }
        Pet pet = ((Pet) familiar);
        PetDetails details = pet.getDetails();
        Item petItem = new Item(pet.getItemId());
        petItem.setCharge(details.getIndividual());
        if (player.getInventory().add(petItem)) {
            petDetails.put(pet.getItemIdHash(),details);
            player.animate(Animation.create(827));
            player.getFamiliarManager().dismiss();
        }
    }

    /**
     * Adjusts the battle state.
     *
     * @param state the state
     */
    public void adjustBattleState(final BattleState state) {
        if (!hasFamiliar()) {
            return;
        }
        familiar.adjustPlayerBattle(state);
    }

    /**
     * Gets a boost from a familiar.
     *
     * @param skill the skill
     * @return the boost
     */
    public int getBoost(int skill) {
        if (!hasFamiliar()) {
            return 0;
        }
        return familiar.getBoost(skill);
    }

    /**
     * Checks if the player has an active familiar.
     *
     * @return the boolean
     */
    public boolean hasFamiliar() {
        return familiar != null;
    }

    /**
     * Checks if the player has an active familiar and is a pet.
     *
     * @return the boolean
     */
    public boolean hasPet() {
        return hasFamiliar() && familiar instanceof Pet;
    }

    /**
     * Dismisses the familiar.
     */
    public void dismiss() {
        if (hasFamiliar()) {
            familiar.dismiss();
        }
    }

    /**
     * Removes the details for this pet.
     *
     * @param itemIdHash the item id hash
     */
    public void removeDetails(int itemIdHash) {
        petDetails.remove(itemIdHash);
    }

    /**
     * Checks if it's the owner of a familiar.
     *
     * @param familiar the familiar
     * @return the boolean
     */
    public boolean isOwner(Familiar familiar) {
        if (!hasFamiliar()) {
            return false;
        }
        if (this.familiar != familiar) {
            player.getPacketDispatch().sendMessage("This is not your familiar.");
            return false;
        }
        return true;
    }

    /**
     * Sets a config value.
     *
     * @param value the value
     */
    public void setConfig(int value) {
        int current = getVarp(player, 1160);
        int newVal = current + value;
        setVarp(player, 1160, newVal);
    }

    /**
     * Gets the familiar.
     *
     * @return the familiar
     */
    public Familiar getFamiliar() {
        return familiar;
    }

    /**
     * Sets the familiar.
     *
     * @param familiar the familiar
     */
    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    /**
     * Gets the familiars.
     *
     * @return the familiars
     */
    public static Map<Integer, Familiar> getFamiliars() {
        return FAMILIARS;
    }

    /**
     * Gets the usingSummoning.
     *
     * @return the boolean
     */
    public boolean isUsingSummoning() {
        return hasPouch || (hasFamiliar() && !hasPet());
    }

    /**
     * Gets the hasPouch.
     *
     * @return the boolean
     */
    public boolean isHasPouch() {
        return hasPouch;
    }

    /**
     * Sets the hasPouch.
     *
     * @param hasPouch the has pouch
     */
    public void setHasPouch(boolean hasPouch) {
        this.hasPouch = hasPouch;
    }

    /**
     * Gets the summoningCombatLevel.
     *
     * @return the summoning combat level
     */
    public int getSummoningCombatLevel() {
        return summoningCombatLevel;
    }

    /**
     * Sets the summoningCombatLevel.
     *
     * @param summoningCombatLevel the summoning combat level
     */
    public void setSummoningCombatLevel(int summoningCombatLevel) {
        this.summoningCombatLevel = summoningCombatLevel;
    }

    /**
     * Gets pet details.
     *
     * @return the pet details
     */
    public Map<Integer, PetDetails> getPetDetails() {
        return petDetails;
    }
}
