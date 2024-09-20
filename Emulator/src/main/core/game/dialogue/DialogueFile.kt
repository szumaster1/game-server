package core.game.dialogue

import core.api.splitLines
import core.game.component.Component
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.START_DIALOGUE

/**
 * Represents a dialogue file that manages the dialogue interactions between the player and NPCs.
 */
abstract class DialogueFile {
    var player: Player? = null
    var npc: NPC? = null
    var interpreter: DialogueInterpreter? = null
    open var stage = START_DIALOGUE
    var dialogue: Dialogue? = null

    /**
     * Handles button click events within a specific component of the dialogue interface.
     *
     * @param componentID The ID of the component where the button is located.
     * @param buttonID The ID of the button that was clicked.
     */
    abstract fun handle(componentID: Int, buttonID: Int)

    /**
     * Initializes the dialogue by setting up the player, NPC, and interpreter.
     *
     * @param player The player initiating the dialogue.
     * @param npc The NPC involved in the dialogue (optional).
     * @param interpreter The interpreter responsible for processing the dialogue.
     * @return The initialized DialogueFile object.
     */
    fun load(player: Player, npc: NPC?, interpreter: DialogueInterpreter): DialogueFile {
        this.player = player
        this.npc = npc
        this.interpreter = interpreter
        interpreter.activeTopics.clear()

        return this
    }

    /**
     * Sends a dialogue message to the NPC with the specified messages.
     *
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npc(vararg messages: String?): Component? {
        if (npc != null) {
            return interpreter!!.sendDialogues(
                npc, if (npc!!.id > 8591) FacialExpression.OLD_NORMAL else FacialExpression.FRIENDLY, *messages
            )
        }
        return null
    }

    /**
     * Sends a dialogue message to an NPC identified by the specified ID.
     *
     * @param id The ID of the NPC.
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npc(id: Int, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(id, FacialExpression.FRIENDLY, *messages)
    }

    /**
     * Sends a dialogue message to the NPC with the specified facial expression and messages.
     * If the NPC is null, the dialogue is sent to the player instead.
     *
     * @param expression The facial expression of the NPC.
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npc(expression: FacialExpression?, vararg messages: String?): Component? {
        return if (npc == null) {
            interpreter!!.sendDialogues(0, expression, *messages)
        } else interpreter!!.sendDialogues(npc, expression, *messages)
    }

    /**
     * Sends a dialogue message to an NPC identified by the specified ID with a specific facial expression.
     *
     * @param id The ID of the NPC.
     * @param expression The facial expression of the NPC.
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npc(id: Int, expression: FacialExpression?, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(id, expression, *messages)
    }

    /**
     * Sends a dialogue message to an NPC identified by the specified ID and title, with a specific facial expression.
     *
     * @param id The ID of the NPC.
     * @param title The title of the dialogue interface.
     * @param expression The facial expression of the NPC.
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npc(id: Int, title: String, expression: FacialExpression?, vararg messages: String?): Component? {
        val chatBoxComponent = interpreter!!.sendDialogues(id, expression, *messages)
        player!!.packetDispatch.sendString(title, chatBoxComponent.id, 3)
        return chatBoxComponent
    }

    /**
     * Sends a dialogue message to the player with the specified messages.
     *
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun player(vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, null, *messages)
    }

    /**
     * Sends a dialogue message to the player with the specified facial expression and messages.
     *
     * @param expression The facial expression of the player.
     * @param messages The messages to be displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun player(expression: FacialExpression?, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, expression, *messages)
    }

    /**
     * Sends a line-split dialogue message to the NPC with the specified facial expression and message.
     *
     * @param expr The facial expression of the NPC.
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npcl(expr: FacialExpression?, msg: String?): Component? {
        return npc(expr, *splitLines(msg!!))
    }

    /**
     * Sends a line-split dialogue message to an NPC identified by the specified ID, with a specific facial expression.
     *
     * @param id The ID of the NPC.
     * @param expr The facial expression of the NPC.
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npcl(id: Int, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, expr, *splitLines(msg!!))
    }

    /**
     * Sends a line-split dialogue message to an NPC identified by the specified ID and title, with a specific facial expression.
     *
     * @param id The ID of the NPC.
     * @param title The title of the dialogue interface.
     * @param expr The facial expression of the NPC.
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npcl(id: Int, title: String, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, title, expr, *splitLines(msg!!))
    }

    /**
     * Sends a line-split dialogue message to the NPC.
     *
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun npcl(msg: String?): Component? {
        return npc(*splitLines(msg!!))
    }

    /**
     * Sends a line-split dialogue message to the player with the specified facial expression.
     *
     * @param expr The facial expression of the player.
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun playerl(expr: FacialExpression?, msg: String?): Component? {
        return player(expr, *splitLines(msg!!))
    }

    /**
     * Sends a line-split dialogue message to the player.
     *
     * @param msg The message to be split and displayed in the dialogue.
     * @return The Component object representing the dialogue interface.
     */
    open fun playerl(msg: String?): Component? {
        return player(*splitLines(msg!!))
    }

    /**
     * Ends the dialogue by closing the interpreter.
     */
    open fun end() {
        if (interpreter != null) interpreter!!.close()
    }

    /**
     * Sends a normal dialogue message to the specified entity with a given facial expression.
     *
     * @param entity The entity to send the dialogue to.
     * @param expression The facial expression to use.
     * @param messages The messages to be displayed in the dialogue.
     */
    open fun sendNormalDialogue(entity: Entity?, expression: FacialExpression?, vararg messages: String?) {
        interpreter!!.sendDialogues(entity, expression, *messages)
    }

    /**
     * Displays a set of selectable options in the dialogue interface.
     *
     * @param options The options to be displayed.
     * @param title The title of the options selection dialogue.
     */
    open fun options(vararg options: String?, title: String = "Select an Option") {
        interpreter!!.sendOptions(title, *options)
    }

    /**
     * Ends the current dialogue file, clearing it from the interpreter.
     */
    fun endFile() {
        interpreter!!.dialogue.file = null
    }

    /**
     * Returns to a specific stage in the dialogue.
     *
     * @param toStage The stage to return to.
     */
    fun returnAtStage(toStage: Int) {
        dialogue!!.file = null
        dialogue!!.stage = toStage
    }

    /**
     * Abandons the current dialogue file, resetting the dialogue state and displaying a default message.
     */
    fun abandonFile() {
        interpreter!!.dialogue.file = null
        player("Nevermind.")
    }

    /**
     * Retrieves the current stage of the dialogue.
     *
     * @return The current stage of the dialogue.
     */
    open fun getCurrentStage(): Int {
        return stage
    }

    /**
     * Calculates the substage based on the given stage.
     *
     * @param stage The base stage value.
     * @return The calculated substage.
     */
    fun Int.substage(stage: Int): Int {
        return this + stage
    }

    /**
     * Initiates a dialogue with the player, displaying the specified messages.
     *
     * @param messages The messages to be displayed in the dialogue.
     */
    fun dialogue(vararg messages: String) {
        player?.dialogueInterpreter?.sendDialogue(*messages)
    }

    /**
     * Displays a set of selectable topics in the dialogue interface.
     *
     * @param topics The topics to be displayed.
     * @param title The title of the topic selection dialogue.
     * @return Boolean indicating if the topics were successfully displayed.
     */
    fun showTopics(vararg topics: Topic<*>, title: String = "Select an Option:"): Boolean {
        val validTopics = ArrayList<String>()
        topics.filter { if (it is IfTopic) it.showCondition else true }.forEach { topic ->
            interpreter!!.activeTopics.add(topic)
            validTopics.add(topic.text)
        }
        if (validTopics.size == 0) {
            return true
        } else if (validTopics.size == 1) {
            val topic = topics[0]
            if (topic.toStage is DialogueFile) {
                val topicFile = topic.toStage
                interpreter!!.dialogue.loadFile(topicFile)
            } else if (topic.toStage is Int) {
                stage = topic.toStage
            }
            player(topic.text)
            interpreter!!.activeTopics.clear()
            return false
        } else {
            options(*validTopics.toTypedArray(), title = title)
            return false
        }
    }
}
