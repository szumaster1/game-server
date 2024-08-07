package core.game.node.entity.npc;

import content.global.skill.combat.summoning.familiar.Familiar;
import content.global.skill.combat.summoning.pet.Pets;
import core.cache.def.impl.NPCDefinition;
import core.game.dialogue.Dialogue;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.ClassScanner;
import core.plugin.Plugin;
import core.tools.RandomFunction;

/**
 * Metamorphosis.
 */
public abstract class Metamorphosis extends OptionHandler {

    /**
     * The Ids.
     */
    protected int[] ids;


    /**
     * Instantiates a new Metamorphosis.
     *
     * @param ids the ids
     */
    public Metamorphosis(int... ids) {
        this.ids = ids;
    }

    /**
     * Gets dialogue.
     *
     * @return the dialogue
     */
    public abstract Dialogue getDialogue();

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        for (int id : getIds()) {
            NPCDefinition.forId(id).getHandlers().put("option:metamorphosis", this);
        }
        if (getDialogue() != null) {
            ClassScanner.definePlugin(getDialogue());
        }
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        Familiar familiar = (Familiar) node;
        switch (option) {
            case "metamorphosis":
                if (player.getFamiliarManager().isOwner(familiar)) {
                    int newNpc = player.getFamiliarManager().getFamiliar().getId();
                    while (newNpc == player.getFamiliarManager().getFamiliar().getId()) {
                        newNpc = getRandomNpcId();
                    }
                    for (Pets p : Pets.values()) {
                        if (p.babyNpcId == newNpc) {
                            player.getFamiliarManager().morphPet(new Item(p.babyItemId), false, player.getFamiliarManager().getFamiliar().getLocation());
                            break;
                        }
                    }
                    player.getPacketDispatch().sendMessage("You transform your " + player.getFamiliarManager().getFamiliar().getName() + "!");
                } else {
                    player.getPacketDispatch().sendMessage("This is not your familiar.");
                }
                break;
        }
        return true;
    }

    /**
     * Gets random npc id.
     *
     * @return the random npc id
     */
    public int getRandomNpcId() {
        int i = RandomFunction.getRandom(getIds().length - 1);
        return getIds()[i];
    }

    /**
     * Get ids int [ ].
     *
     * @return the int [ ]
     */
    public int[] getIds() {
        return ids;
    }
}
