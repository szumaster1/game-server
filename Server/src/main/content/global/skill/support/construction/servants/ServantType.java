package content.global.skill.support.construction.servants;


import core.api.consts.NPCs;
import core.game.node.item.Item;

public enum ServantType {
    NONE(-1, -1, -1, -1, -1),
    RICK(NPCs.RICK_4235, 500, 6, 20, 60),
    MAID(NPCs.MAID_4237, 1000, 10, 25, 50, new Item(2003)),
    COOK(NPCs.COOK_4239, 3000, 16, 30, 17, new Item(2301), new Item(712)),
    BUTLER(NPCs.BUTLER_4241, 5000, 20, 40, 12, new Item(1897), new Item(712)),
    DEMON_BUTLER(NPCs.DEMON_BUTLER_4243, 10000, 26, 50, 7, new Item(2011));

    private int npcId;
    private int cost;
    private int capacity;
    private int level;
    private int timer;
    private Item[] food;

    private ServantType(int npcId, int cost, int capacity, int level, int timer, Item... food) {
        this.npcId = npcId;
        this.cost = cost;
        this.capacity = capacity;
        this.level = level;
        this.timer = timer;
        this.food = food;
    }

    public static ServantType forId(int id) {
        for (ServantType s : ServantType.values()) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public int getId() {
        return npcId;
    }

    public int getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLevel() {
        return level;
    }

    public int getTimer() {
        return timer;
    }

    public Item[] getFood() {
        return food;
    }
}