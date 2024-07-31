package content.global.skill.combat.summoning;

import core.api.consts.Sounds;
import core.cache.def.impl.CS2Mapping;
import core.game.component.Component;
import core.game.node.entity.player.Player;
import core.game.node.entity.skill.SkillPulse;
import core.game.node.entity.skill.Skills;
import core.game.node.item.Item;
import core.game.node.scenery.Scenery;
import core.game.world.map.Location;
import core.game.world.map.RegionManager;
import core.game.world.update.flag.context.Animation;

import static core.api.ContentAPIKt.playAudio;

public final class SummoningCreator {
    private static final Animation ANIMATION = new Animation(9068);
    private static final Object[] POUCH_PARAMS = new Object[]{"List<col=FF9040>", "Infuse-X<col=FF9040>", "Infuse-All<col=FF9040>", "Infuse-10<col=FF9040>", "Infuse-5<col=FF9040>", "Infuse<col=FF9040>", 20, 4, 669 << 16 | 15};
    private static final Object[] SCROLL_PARAMS = new Object[]{"Transform-X<col=ff9040>", "Transform-All<col=ff9040>", "Transform-10<col=ff9040>", "Transform-5<col=ff9040>", "Transform<col=ff9040>", 20, 4, 673 << 16 | 15};
    private static final Component SUMMONING_COMPONENT = new Component(669);
    private static final Component SCROLL_COMPONENT = new Component(673);

    public static void open(final Player player, final boolean pouch) {
        configure(player, pouch);
    }

    public static void configure(final Player player, final boolean pouch) {
        player.getInterfaceManager().open(pouch ? SUMMONING_COMPONENT : SCROLL_COMPONENT);
        player.getPacketDispatch().sendRunScript(pouch ? 757 : 765, pouch ? "Iiissssss" : "Iiisssss", pouch ? POUCH_PARAMS : SCROLL_PARAMS);
        player.getPacketDispatch().sendIfaceSettings(pouch ? 190 : 126, 15, pouch ? 669 : 673, 0, 78);
    }

    public static void create(final Player player, final int amount, Object node) {
        if (node == null) {
            return;
        }
        player.getPulseManager().run(new CreatePulse(player, null, SummoningNode.parse(node), amount));
    }

    public static void list(final Player player, final SummoningPouch pouch) {
        player.getPacketDispatch().sendMessage((String) CS2Mapping.forId(1186).getMap().get(pouch.getPouchId()));
    }

    public static final class CreatePulse extends SkillPulse<Item> {
        private final SummoningCreator.SummoningNode type;
        private final Scenery object;
        private final int amount;

        public CreatePulse(Player player, Item node, final SummoningCreator.SummoningNode type, final int amount) {
            super(player, node);
            this.type = type;
            this.amount = amount;
            this.object = RegionManager.getObject(new Location(2209, 5344, 0));

        }

        @Override
        public boolean checkRequirements() {
            player.getInterfaceManager().close();
            if (player.getSkills().getStaticLevel(Skills.SUMMONING) < type.getLevel()) {
                player.getPacketDispatch().sendMessage("You need a Summoning level of at least " + type.getLevel() + " in order to do this.");
                return false;
            }
            if (amount == 0) {
                player.getPacketDispatch().sendMessage("You don't have the required item(s) to make this.");
                return false;
            }
            for (Item i : type.getRequired()) {
                if (!player.getInventory().containsItem(i)) {
                    player.getPacketDispatch().sendMessage("You don't have the required item(s) to make this.");
                    return false;
                }
            }
            return true;
        }

        @Override
        public void animate() {
            player.lock(3);
            player.animate(ANIMATION);
        }

        @Override
        public void stop() {
            super.stop();
            player.getPacketDispatch().sendSceneryAnimation(object, new Animation(8510));
        }

        @Override
        public boolean reward() {
            if (getDelay() == 1) {
                setDelay(4);
                player.getPacketDispatch().sendSceneryAnimation(object, Animation.create(8509));
                playAudio(player, Sounds.CRAFT_POUCH_4164); // 4277 also sounds the same
                return false;
            }
            player.getPacketDispatch().sendSceneryAnimation(object, Animation.create(8510));
            for (int i = 0; i < amount; i++) {
                for (Item item : type.getRequired()) {
                    if (!player.getInventory().containsItem(item)) {
                        return true;
                    }
                }
                if (player.getInventory().remove(type.getRequired())) {
                    final Item item = type.getProduct();
                    player.getInventory().add(item);
                    player.getSkills().addExperience(Skills.SUMMONING, type.getExperience(), true);
                }
            }
            return true;
        }

    }

    public static class SummoningNode {
        private final Object base;
        private final Item[] required;
        private final Item product;
        private final double experience;
        private final int level;

        public SummoningNode(final Object base, Item[] required, Item product, double experience, int level) {
            this.base = base;
            this.required = required;
            this.product = product;
            this.experience = experience;
            this.level = level;
        }

        public Item[] getRequired() {
            return required;
        }
        public Item getProduct() {
            return product;
        }
        public double getExperience() {
            return experience;
        }
        public int getLevel() {
            return level;
        }
        public Object getBase() {
            return base;
        }
        public boolean isPouch() {
            return base instanceof SummoningPouch;
        }

        public static SummoningNode parse(final Object node) {
            final Item[] required = node instanceof SummoningPouch ? ((SummoningPouch) node).getItems() : createList(((SummoningScroll) node).getItems());
            final Item product = node instanceof SummoningPouch ? new Item(((SummoningPouch) node).getPouchId(), 1) : new Item(((SummoningScroll) node).getItemId(), 10);
            final int level = node instanceof SummoningPouch ? ((SummoningPouch) node).getLevelRequired() : ((SummoningScroll) node).getLevel();
            final double experience = node instanceof SummoningPouch ? ((SummoningPouch) node).getCreateExperience() : ((SummoningScroll) node).getExperience();
            return new SummoningNode(node, required, product, experience, level);
        }

        private static final Item[] createList(final int... ids) {
            Item[] list = new Item[ids.length];
            for (int i = 0; i < ids.length; i++) {
                list[i] = new Item(ids[i], 1);
            }
            return list;
        }
    }
}
