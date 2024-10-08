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

/**
 * Represents a dialogue plugin.
 * @author Emperor
 */
@PluginManifest(type = PluginType.DIALOGUE)
public abstract class Dialogue implements Plugin<Player> {

    /**
     * Represents the red string.
     */
    protected static final String RED = "<col=8A0808>";

    /**
     * Represents the blue string.
     */
    protected static final String BLUE = "<col=08088A>";

    /**
     * The Player.
     */
    protected Player player;

    /**
     * The dialogue interpreter.
     */
    protected DialogueInterpreter interpreter;

    /**
     * The File.
     */
    public DialogueFile file;

    /**
     * The Option names.
     */
    protected ArrayList<String> optionNames = new ArrayList<String>(10);

    /**
     * The Option files.
     */
    protected ArrayList<DialogueFile> optionFiles = new ArrayList<DialogueFile>(10);

    /**
     * The Two options interface.
     */
    protected final int TWO_OPTIONS = 228;

    /**
     * The Three options interface.
     */
    protected final int THREE_OPTIONS = 230;

    /**
     * The Four options interface.
     */
    protected final int FOUR_OPTIONS = 232;

    /**
     * The Five options interface.
     */
    protected final int FIVE_OPTIONS = 234;

    /**
     * The NPC the player is talking with.
     */
    protected NPC npc;

    /**
     * The current dialogue stage.
     */
    public int stage;

    /**
     * If the dialogue is finished.
     */
    protected boolean finished;

    /**
     * Constructs a new Dialogue.
     */
    public Dialogue() {
        /*
         * empty.
         */
    }

    /**
     * Pirate gender string.
     *
     * @return the string
     */
    public String pirateGender() {
        return (player.isMale() ? "lad" : "lass");

    }

    /**
     * Constructs a new Dialogue.
     *
     * @param player the player
     */
    public Dialogue(Player player) {
        this.player = player;
        if (player != null) {
            this.interpreter = player.getDialogueInterpreter();
        }
    }

    /**
     * Initializes this class.
     */
    public void init() {
        for (int id : getIds()) {
            DialogueInterpreter.add(id, this);
        }
    }

    /**
     * Closes (but does not end) the dialogue.
     *
     * @return true if the dialogue succesfully closed.
     */
    public boolean close() {
        player.getInterfaceManager().closeChatbox();
        player.getInterfaceManager().openChatbox(137);
        if (file != null) file.end();
        finished = true;
        return true;
    }

    /**
     * Send normal dialogue.
     *
     * @param entity     the entity
     * @param expression the expression
     * @param messages   the messages
     */
    public void sendNormalDialogue(Entity entity, FacialExpression expression, String... messages) {
        interpreter.sendDialogues(entity, expression, messages);
    }

    /**
     * Increments the stage variable.
     */
    public void increment() {
        stage++;
    }


    /**
     * Increments the stage variable.
     *
     * @return The stage variable.
     */
    public int getAndIncrement() {
        return stage++;
    }

    /**
     * Ends the dialogue.
     */
    public void end() {
        if (interpreter != null) {
            interpreter.close();
        }
    }

    /**
     * Finish.
     */
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
            return (Dialogue) classReference
                .getDeclaredConstructor(Player.class)
                .newInstance(player);
        } catch (ClassNotFoundException
                 | IllegalAccessException
                 | IllegalArgumentException
                 | NoSuchMethodException
                 | InvocationTargetException
                 | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Opens the dialogue.
     *
     * @param args The arguments.
     * @return {@code True} if the dialogue plugin succesfully opened.
     */
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

    /**
     * Handles the progress of this dialogue..
     *
     * @param interfaceId the interface id
     * @param buttonId    the button id
     * @return {@code True} if the dialogue has started.
     */
    public abstract boolean handle(int interfaceId, int buttonId);

    /**
     * Gets the ids of the NPCs using this dialogue plugin.
     *
     * @return The array of NPC ids.
     */
    public abstract int[] getIds();

    /**
     * Npc component.
     *
     * @param messages the messages
     * @return the component
     */
    public Component npc(final String... messages) {
        if (npc == null) {
            return interpreter.sendDialogues(getIds()[0], getIds()[0] > 8591 ? FacialExpression.OLD_NORMAL : FacialExpression.FRIENDLY, messages);
        }
        return interpreter.sendDialogues(npc, npc.getId() > 8591 ? FacialExpression.OLD_NORMAL : FacialExpression.FRIENDLY, messages);
    }

    /**
     * Method wrapper to send an npc dial.
     *
     * @param id       the id.
     * @param messages the messages
     * @return the component.
     */
    public Component npc(int id, final String... messages) {
        return interpreter.sendDialogues(id, FacialExpression.FRIENDLY, messages);
    }

    /**
     * Send dialogue component.
     *
     * @param messages the messages
     * @return the component
     */
    public Component sendDialogue(String... messages) {
        return interpreter.sendDialogue(messages);
    }

    /**
     * Method wrapper to send an npc dial.
     *
     * @param expression the expression
     * @param messages   the messages
     * @return the component.
     */
    public Component npc(FacialExpression expression, final String... messages) {
        if (npc == null) {
            return interpreter.sendDialogues(getIds()[0], expression, messages);
        }
        return interpreter.sendDialogues(npc, expression, messages);
    }

    /**
     * Method wrapper to send a player dial.
     *
     * @param messages the messages
     * @return the component.
     */
    public Component player(final String... messages) {
        return interpreter.sendDialogues(player, null, messages);
    }

    /**
     * Method wrapper to send a player dial.
     *
     * @param expression the expression
     * @param messages   the messages
     * @return the component.
     */
    public Component player(FacialExpression expression, final String... messages) {
        return interpreter.sendDialogues(player, expression, messages);
    }

    /**
     * Method used to send options.
     *
     * @param options the options.
     */
    public void options(final String... options) {
        interpreter.sendOptions("Select an Option", options);
    }

    /**
     * Checks if the dialogue plugin is finished.
     *
     * @return true if so.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Gets the player.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the stage.
     *
     * @param i the stage.
     */
    public void setStage(int i) {
        this.stage = i;
    }

    /**
     * Next.
     */
    public void next() {
        this.stage += 1;
    }

    /**
     * Loads a DialogueFile and sets its stage to START_DIALOGUE, and diverts all further handling for the conversation to the file.
     *
     * @param file the DialogueFile to load.
     */
    public void loadFile(DialogueFile file) {
        if (file == null) return;
        this.file = file.load(player, npc, interpreter);
        this.file.setDialogue(this);
        stage = START_DIALOGUE;
    }

    /**
     * Add an option to the list of possible choices a player can pick from. Helps build the options interface for sendChoices()
     *
     * @param name the name of the quest/activity to talk about. Turns into "Talk about $name" on the option interface.
     * @param file the DialogueFile that the option loads when selected.
     */
    public void addOption(String name, DialogueFile file) {
        optionNames.add("Talk about " + name);
        optionFiles.add(file);
    }

    /**
     * Send the player a list of conversation options if there's more than one choice. I.E. multiple quest lines.
     *
     * @return true if an options interface was sent, false if not.
     */
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

    /**
     * Use the automatic line splitting feature in DialUtils to produce npc dialogues.
     *
     * @param expr the FacialExpression to use, located in the FacialExpression enum.
     * @param msg  the message for the NPC to say.
     * @return the component
     */
    public Component npcl(FacialExpression expr, String msg) {
        return npc(expr, splitLines(msg, 54));
    }

    /**
     * Use the automatic line splitting feature in DialUtils to produce player dialogues.
     *
     * @param expr the FacialExpression to use, located in the FacialExpression enum.
     * @param msg  the message for the player to say.
     * @return the component
     */
    public Component playerl(FacialExpression expr, String msg) {
        return player(expr, splitLines(msg, 54));
    }

    /**
     * Show topics boolean.
     *
     * @param topics the topics.
     * @return the boolean.
     */
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
