package core.game.dialogue;

import core.game.component.Component;
import core.game.node.entity.Entity;
import core.game.node.entity.npc.NPC;
import core.game.node.entity.player.Player;
import core.plugin.Plugin;
import core.plugin.PluginManifest;
import core.plugin.PluginType;
import core.tools.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static core.api.ContentAPIKt.log;
import static core.api.DialUtilsKt.splitLines;
import static core.tools.DialogueHelperKt.DIALOGUE_INITIAL_OPTIONS_HANDLE;
import static core.tools.DialogueHelperKt.START_DIALOGUE;

@PluginManifest(type = PluginType.DIALOGUE)
public abstract class Dialogue implements Plugin<Player> {

    protected static final String RED = "<col=8A0808>";

    protected static final String BLUE = "<col=08088A>";

    protected Player player;

    protected DialogueInterpreter interpreter;

    public DialogueFile file;

    protected ArrayList<String> optionNames = new ArrayList<String>(10);
    protected ArrayList<DialogueFile> optionFiles = new ArrayList<DialogueFile>(10);

    protected final int TWO_OPTIONS = 228;

    protected final int THREE_OPTIONS = 230;

    protected final int FOUR_OPTIONS = 232;

    protected final int FIVE_OPTIONS = 234;

    protected NPC npc;

    public int stage;

    protected boolean finished;

    public Dialogue() {
        /*
         * empty.
         */
    }

    public String pirateGender() {
        return (player.isMale() ? "lad" : "lass");

    }

    public Dialogue(Player player) {
        this.player = player;
        if (player != null) {
            this.interpreter = player.getDialogueInterpreter();
        }
    }

    public void init() {
        for (int id : getIds()) {
            DialogueInterpreter.add(id, this);
        }
    }

    public boolean close() {
        player.getInterfaceManager().closeChatbox();
        player.getInterfaceManager().openChatbox(137);
        if (file != null) file.end();
        finished = true;
        return true;
    }

    public void sendNormalDialogue(Entity entity, FacialExpression expression, String... messages) {
        interpreter.sendDialogues(entity, expression, messages);
    }

    public void increment() {
        stage++;
    }


    public int getAndIncrement() {
        return stage++;
    }

    public void end() {
        if (interpreter != null) {
            interpreter.close();
        }
    }

    public void finish() {
        setStage(-1);
    }

    @Override
    public Object fireEvent(String identifier, Object... args) {
        return null;
    }

    @Override
    public Dialogue newInstance(Player player) {
        try {
            Class<?> classReference = Class.forName(this.getClass().getCanonicalName());

            return (Dialogue) classReference.getDeclaredConstructor(Player.class).newInstance(player);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException |
                 InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean open(Object... args) {
        player.getDialogueInterpreter().activeTopics.clear();
        if (args.length > 0 && args[0] instanceof NPC) {
            npc = (NPC) args[0];
        }

        if (npc == null) {
            log(this.getClass(), Log.WARN, args[0].getClass().getSimpleName() + "Is not assigning an NPC. Whoever did that should fix it.");
        }

        player.getDialogueInterpreter().handle(0, 0);
        return true;
    }

    public abstract boolean handle(int interfaceId, int buttonId);

    public abstract int[] getIds();

    public Component npc(final String... messages) {
        if (npc == null) {
            return interpreter.sendDialogues(getIds()[0], getIds()[0] > 8591 ? FacialExpression.OLD_NORMAL : FacialExpression.FRIENDLY, messages);
        }
        return interpreter.sendDialogues(npc, npc.getId() > 8591 ? FacialExpression.OLD_NORMAL : FacialExpression.FRIENDLY, messages);
    }

    public Component npc(int id, final String... messages) {
        return interpreter.sendDialogues(id, FacialExpression.FRIENDLY, messages);
    }

    public Component sendDialogue(String... messages) {
        return interpreter.sendDialogue(messages);
    }

    public Component npc(FacialExpression expression, final String... messages) {
        if (npc == null) {
            return interpreter.sendDialogues(getIds()[0], expression, messages);
        }
        return interpreter.sendDialogues(npc, expression, messages);
    }

    public Component player(final String... messages) {
        return interpreter.sendDialogues(player, null, messages);
    }

    public Component player(FacialExpression expression, final String... messages) {
        return interpreter.sendDialogues(player, expression, messages);
    }

    public void options(final String... options) {
        interpreter.sendOptions("Select an Option", options);
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getPlayer() {
        return player;
    }

    public void setStage(int i) {
        this.stage = i;
    }

    public void next() {
        this.stage += 1;
    }

    public void loadFile(DialogueFile file) {
        if (file == null) return;
        this.file = file.load(player, npc, interpreter);
        this.file.setDialogue(this);
        stage = START_DIALOGUE;
    }

    public void addOption(String name, DialogueFile file) {
        optionNames.add("Talk about " + name);
        optionFiles.add(file);
    }

    public boolean sendChoices() {
        if (optionNames.size() == 1) {
            loadFile(optionFiles.get(0));
            return false;
        } else if (optionNames.isEmpty()) {
            stage = START_DIALOGUE;
            return false;
        } else {
            options(optionNames.toArray(new String[0]));
            stage = DIALOGUE_INITIAL_OPTIONS_HANDLE;
            return true;
        }
    }

    public Component npcl(FacialExpression expr, String msg) {
        return npc(expr, splitLines(msg, 54));
    }

    public Component playerl(FacialExpression expr, String msg) {
        return player(expr, splitLines(msg, 54));
    }

    public boolean showTopics(Topic<?>... topics) {
        ArrayList<String> validTopics = new ArrayList<>();
        for (Topic<?> topic : topics) {
            if (topic instanceof IfTopic && !((IfTopic<?>) topic).getShowCondition()) continue;
            interpreter.activeTopics.add(topic);
            validTopics.add(topic.getText());
        }
        if (validTopics.size() == 0) {
            return true;
        } else if (validTopics.size() == 1) {
            Topic topic = interpreter.activeTopics.get(0);
            if (topic.getToStage() instanceof DialogueFile) {
                DialogueFile topicFile = (DialogueFile) topic.getToStage();
                interpreter.getDialogue().loadFile(topicFile);
            } else if (topic.getToStage() instanceof Integer) {
                stage = (Integer) topic.getToStage();
            }
            player(topic.getText());
            interpreter.activeTopics.clear();
            return false;
        } else {
            options(validTopics.toArray(new String[0]));
            return false;
        }
    }
}
