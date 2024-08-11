package core.game.dialogue

import core.api.splitLines
import core.game.component.Component
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.START_DIALOGUE

/**
 * Dialogue file.
 */
abstract class DialogueFile {
    var player: Player? = null
    var npc: NPC? = null
    var interpreter: DialogueInterpreter? = null
    open var stage = START_DIALOGUE
    var dialogue: Dialogue? = null

    /**
     * Handle method to process button clicks on a specific component.
     *
     * @param componentID The ID of the component where the button is located
     * @param buttonID The ID of the button clicked
     */
    abstract fun handle(componentID: Int, buttonID: Int)

    /**
     * Load method to initialize dialogue processing.
     *
     * @param player The player object initiating the dialogue
     * @param npc The NPC object involved in the dialogue
     * @param interpreter The dialogue interpreter for processing
     * @return The initialized DialogueFile object
     */
    fun load(player: Player, npc: NPC?, interpreter: DialogueInterpreter): DialogueFile {
        this.player = player
        this.npc = npc
        this.interpreter = interpreter
        interpreter.activeTopics.clear()

        return this
    }

    /**
     * This method sends a dialogue to an NPC with the specified ID and messages.
     *
     * @param messages The messages to be sent to the NPC
     * @return The Component object representing the dialogue box
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
     * This method sends a dialogue to an NPC with the specified ID and messages.
     * It returns a Component object representing the dialogue box.
     *
     * @param id The ID of the NPC
     * @param messages The messages to be sent to the NPC
     * @return The Component object representing the dialogue box
     */
    open fun npc(id: Int, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(id, FacialExpression.FRIENDLY, *messages)
    }

    /**
     * This method sends a dialogue to an NPC with the specified expression and messages.
     * If the NPC is null, it sends the dialogue to the player instead.
     * It returns a Component object representing the dialogue box.
     *
     * @param expression The facial expression of the NPC
     * @param messages The messages to be sent to the NPC
     * @return The Component object representing the dialogue box
     */
    open fun npc(expression: FacialExpression?, vararg messages: String?): Component? {
        return if (npc == null) {
            interpreter!!.sendDialogues(0, expression, *messages)
        } else interpreter!!.sendDialogues(npc, expression, *messages)
    }

    /**
     * This method sends a dialogue to an NPC with the specified ID, expression, and messages.
     * It returns a Component object representing the dialogue box.
     *
     * @param id The ID of the NPC
     * @param expression The facial expression of the NPC
     * @param messages The messages to be sent to the NPC
     * @return The Component object representing the dialogue box
     */
    open fun npc(id: Int, expression: FacialExpression?, vararg messages: String?): Component? {
        val chatBoxComponent = interpreter!!.sendDialogues(id, expression, *messages)
        return chatBoxComponent
    }

    /**
     * This method sends a dialogue to an NPC with the specified ID, title, expression, and messages.
     * It also sets the title of the dialogue box.
     * It returns a Component object representing the dialogue box.
     *
     * @param id The ID of the NPC
     * @param title The title of the dialogue box
     * @param expression The facial expression of the NPC
     * @param messages The messages to be sent to the NPC
     * @return The Component object representing the dialogue box
     */
    open fun npc(id: Int, title: String, expression: FacialExpression?, vararg messages: String?): Component? {
        val chatBoxComponent = interpreter!!.sendDialogues(id, expression, *messages)
        player!!.packetDispatch.sendString(title, chatBoxComponent.id, 3)
        return chatBoxComponent
    }

    /**
     * This method sends a dialogue to the player with the specified messages.
     * It returns a Component object representing the dialogue box.
     *
     * @param messages The messages to be sent to the player
     * @return The Component object representing the dialogue box
     */
    open fun player(vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, null, *messages)
    }


    /**
     * This method sends a dialogue to the player with the specified expression and messages.
     * It returns a Component object representing the dialogue box.
     *
     * @param expression The facial expression of the player.
     * @param messages The messages to be sent to the player.
     * @return The Component object representing the dialogue box.
     */
    open fun player(expression: FacialExpression?, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, expression, *messages)
    }

    /**
     * This method sends a dialogue to an NPC with the specified expression and message.
     * It returns a Component object representing the dialogue box.
     *
     * @param expr The facial expression of the NPC.
     * @param msg The message to be sent to the NPC.
     * @return The Component object representing the dialogue box.
     */
    open fun npcl(expr: FacialExpression?, msg: String?): Component? {
        return npc(expr, *splitLines(msg!!))
    }

    /**
     * This method sends a dialogue to an NPC with the specified ID, expression, and message.
     * It returns a Component object representing the dialogue box.
     *
     * @param id The ID of the NPC.
     * @param expr The facial expression of the NPC.
     * @param msg The message to be sent to the NPC.
     * @return The Component object representing the dialogue box.
     */
    open fun npcl(id: Int, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, expr, *splitLines(msg!!))
    }

    /**
     * This method sends a dialogue to an NPC with the specified ID, title, expression, and message.
     * It returns a Component object representing the dialogue box.
     *
     * @param id The ID of the NPC.
     * @param title The title of the dialogue box.
     * @param expr The facial expression of the NPC.
     * @param msg The message to be sent to the NPC.
     * @return The Component object representing the dialogue box.
     */
    open fun npcl(id: Int, title: String, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, title, expr, *splitLines(msg!!))
    }

    /**
     * This method sends a dialogue to an NPC with the specified message.
     * It returns a Component object representing the dialogue box.
     *
     * @param msg The message to be sent to the NPC.
     * @return The Component object representing the dialogue box.
     */
    open fun npcl(msg: String?): Component? {
        return npc(*splitLines(msg!!))
    }

    /**
     * This method sends a dialogue to the player with the specified expression and message.
     * It returns a Component object representing the dialogue box.
     *
     * @param expr The facial expression of the player.
     * @param msg The message to be sent to the player.
     * @return The Component object representing the dialogue box.
     */
    open fun playerl(expr: FacialExpression?, msg: String?): Component? {
        return player(expr, *splitLines(msg!!))
    }

    /**
     * This method sends a dialogue to the player with the specified message.
     * It returns a Component object representing the dialogue box.
     *
     * @param msg The message to be sent to the player.
     * @return The Component object representing the dialogue box.
     */
    open fun playerl(msg: String?): Component? {
        return player(*splitLines(msg!!))
    }

    /**
     * End
     *
     */
    open fun end() {
        if (interpreter != null) interpreter!!.close()
    }


    /**
     * Send normal dialogue
     *
     * @param entity the entity to send the dialogue to
     * @param expression the facial expression to use
     * @param messages the messages to be sent
     */
    open fun sendNormalDialogue(entity: Entity?, expression: FacialExpression?, vararg messages: String?) {
        interpreter!!.sendDialogues(entity, expression, *messages)
    }

    /**
     * Display options for selection
     *
     * @param options the options to display
     * @param title the title of the options
     */
    open fun options(vararg options: String?, title: String = "Select an Option") {
        interpreter!!.sendOptions(title, *options)
    }

    /**
     * End the current dialogue file
     */
    fun endFile() {
        interpreter!!.dialogue.file = null
    }

    /**
     * Return to a specific stage in the dialogue
     *
     * @param toStage the stage to return to
     */
    fun returnAtStage(toStage: Int) {
        dialogue!!.file = null
        dialogue!!.stage = toStage
    }

    /**
     * Abandon the current dialogue file
     */
    fun abandonFile() {
        interpreter!!.dialogue.file = null
        player("Nevermind.")
    }

    /**
     * Get the current stage of the dialogue
     *
     * @return the current stage
     */
    open fun getCurrentStage(): Int {
        return stage
    }

    /**
     * Calculate the substage based on the current stage
     *
     * @param stage the current stage
     * @return the calculated substage
     */
    fun Int.substage(stage: Int): Int {
        return this + stage
    }

    /**
     * Initiate a dialogue with the player
     *
     * @param messages the messages to be displayed in the dialogue
     */
    fun dialogue(vararg messages: String) {
        player?.dialogueInterpreter?.sendDialogue(*messages)
    }

    /**
     * Function to display topics
     *
     * @param topics The list of topics to display
     * @param title The title of the topic selection
     * @return Boolean indicating if the topics were successfully displayed
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
