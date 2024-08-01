package content.global.skill.support.construction.decoration.workshop;

import content.global.skill.support.construction.BuildingUtils;
import content.global.skill.support.construction.Decoration;
import core.api.consts.Items;
import core.cache.def.impl.ItemDefinition;
import core.cache.def.impl.SceneryDefinition;
import core.game.dialogue.Dialogue;
import core.game.dialogue.DialogueInterpreter;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.plugin.ClassScanner;
import core.plugin.Initializable;
import core.plugin.Plugin;

@Initializable
public class CraftingTablePlugin extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        SceneryDefinition.forId(13709).getHandlers().put("option:craft", this);
        SceneryDefinition.forId(13710).getHandlers().put("option:craft", this);
        SceneryDefinition.forId(13711).getHandlers().put("option:craft", this);
        SceneryDefinition.forId(13712).getHandlers().put("option:craft", this);
        ClassScanner.definePlugin(new CraftingTableDialogue());
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        player.getDialogueInterpreter().open(DialogueInterpreter.getDialogueKey("con:crafting-table"), node.asScenery());
        return true;
    }

    private enum Craftable {
        TOY_HORSEY(Items.TOY_HORSEY_2520, 10, BuildingUtils.PLANK),
        CLOCKWORK(Items.CLOCKWORK_8792, 8, new Item(Items.STEEL_BAR_2353)),
        TOY_SOLDIER(Items.TOY_SOLDIER_7759, 13, BuildingUtils.PLANK, new Item(Items.CLOCKWORK_8792)),
        TOY_DOLL(Items.TOY_DOLL_7763, 18, BuildingUtils.PLANK, new Item(Items.CLOCKWORK_8792)),
        TOY_MOUSE(Items.TOY_MOUSE_7767, 33, BuildingUtils.PLANK, new Item(Items.CLOCKWORK_8792)),
        TOY_CAT(Items.CLOCKWORK_CAT_7771, 85, BuildingUtils.PLANK, new Item(Items.CLOCKWORK_8792)),
        WATCH(Items.WATCH_2575, 28, new Item(Items.CLOCKWORK_8792), new Item(Items.STEEL_BAR_2353)),
        SEXTANT(Items.SEXTANT_2574, 23, new Item(Items.STEEL_BAR_2353));


        private final int itemId;


        private final Item[] materials;


        private final int craftingLevel;


        Craftable(int itemId, int craftingLevel, Item... materials) {
            this.itemId = itemId;
            this.craftingLevel = craftingLevel;
            this.materials = materials;
        }
    }

    private final class CraftingTableDialogue extends Dialogue {


        Decoration decoration;

        public CraftingTableDialogue() {
        }

        public CraftingTableDialogue(Player player) {
            super(player);
        }

        @Override
        public Dialogue newInstance(Player player) {
            return new CraftingTableDialogue(player);
        }

        @Override
        public boolean open(Object... args) {
            Scenery object = (Scenery) args[0];
            decoration = Decoration.forObjectId(object.getId());
            if (decoration != null) {
                switch (decoration) {
                    case CRAFTING_TABLE_1:
                        interpreter.sendOptions("Select an Option", "Toy Horsey", "Nevermind");
                        break;
                    case CRAFTING_TABLE_2:
                        interpreter.sendOptions("Select an Option", "Toy Horsey", "Clockwork Mechanism");
                        break;
                    case CRAFTING_TABLE_3:
                        interpreter.sendOptions("Select an Option", "Toy Horsey", "Clockwork Mechanism", "Clockwork Devices");
                        break;
                    case CRAFTING_TABLE_4:
                        interpreter.sendOptions("Select an Option", "Toy Horsey", "Clockwork Mechanism", "Clockwork Devices", "Watch", "Sextant");
                        break;
                    default:
                        break;
                }
            }
            stage = 1;
            return true;
        }

        @Override
        public boolean handle(int interfaceId, int buttonId) {
            switch (stage) {
                case 1:
                    switch (buttonId) {
                        case 1:
                        case 2:
                            if (decoration == Decoration.CRAFTING_TABLE_1 && buttonId == 2) {
                                end();
                                return true;
                            }
                            craftItem(buttonId == 1 ? Craftable.TOY_HORSEY : Craftable.CLOCKWORK);
                            stage = 3;
                            break;
                        case 3:
                            if (decoration == Decoration.CRAFTING_TABLE_3) {
                                interpreter.sendOptions("Select an Option", "Clockwork Soldier", "Clockwork Doll");
                            } else if (decoration == Decoration.CRAFTING_TABLE_4) {
                                interpreter.sendOptions("Select an Option", "Clockwork Soldier", "Clockwork Doll", "Clockwork Mouse", "Clockwork Cat");
                            }
                            stage = 2;
                            break;
                        case 4:
                        case 5:
                            craftItem(buttonId == 4 ? Craftable.WATCH : Craftable.SEXTANT);
                            stage = 3;
                            break;
                    }
                    break;
                case 2:
                    switch (buttonId) {
                        case 1:
                            craftItem(Craftable.TOY_SOLDIER);
                            break;
                        case 2:
                            craftItem(Craftable.TOY_DOLL);
                            break;
                        case 3:
                            craftItem(Craftable.TOY_MOUSE);
                            break;
                        case 4:
                            craftItem(Craftable.TOY_CAT);
                            break;
                    }
                    stage = 3;
                    break;
                case 3:
                    end();
                    break;
            }
            return true;
        }


        private void craftItem(Craftable c) {
            if (c != null) {
                if (player.getSkills().getLevel(Skills.CRAFTING) < c.craftingLevel) {
                    interpreter.sendDialogue("You need level " + c.craftingLevel + " crafting to make that.");
                    return;
                }
                for (Item n : c.materials) {
                    if (!player.getInventory().containsItem(n)) {
                        interpreter.sendDialogue("You need a " + ItemDefinition.forId(n.getId()).getName() + " to make that!");
                        return;
                    }
                }
                for (Item n : c.materials) {
                    n.setAmount(1);
                    player.getInventory().remove(n);
                }
                player.getSkills().addExperience(Skills.CRAFTING, 15);
                player.getInventory().add(new Item(c.itemId, 1));
                player.animate(BuildingUtils.BUILD_MID_ANIM);
                interpreter.sendDialogue("You made a " + ItemDefinition.forId(c.itemId).getName() + "!");
            }
        }

        @Override
        public int[] getIds() {
            return new int[]{DialogueInterpreter.getDialogueKey("con:crafting-table")};
        }
    }
}
