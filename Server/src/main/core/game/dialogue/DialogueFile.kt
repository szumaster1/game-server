package core.game.dialogue

import core.api.splitLines
import core.game.component.Component
import core.game.node.entity.Entity
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.tools.START_DIALOGUE

/**
 * Dialogue file
 *
 * @constructor Dialogue file
 */
abstract class DialogueFile {
    var player: Player? = null
    var npc: NPC? = null
    var interpreter: DialogueInterpreter? = null
    open var stage = START_DIALOGUE
    var dialogue: Dialogue? = null

    /**
     * Handle
     *
     * @param componentID
     * @param buttonID
     */
    abstract fun handle(componentID: Int, buttonID: Int)

    /**
     * Load
     *
     * @param player
     * @param npc
     * @param interpreter
     * @return
     */
    fun load(player: Player, npc: NPC?, interpreter: DialogueInterpreter): DialogueFile {
        this.player = player
        this.npc = npc
        this.interpreter = interpreter
        interpreter.activeTopics.clear()

        return this
    }

    /**
     * Npc
     *
     * @param messages
     * @return
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
     * Npc
     *
     * @param id
     * @param messages
     * @return
     */
    open fun npc(id: Int, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(id, FacialExpression.FRIENDLY, *messages)
    }

    /**
     * Npc
     *
     * @param expression
     * @param messages
     * @return
     */
    open fun npc(expression: FacialExpression?, vararg messages: String?): Component? {
        return if (npc == null) {
            interpreter!!.sendDialogues(0, expression, *messages)
        } else interpreter!!.sendDialogues(npc, expression, *messages)
    }

    /**
     * Npc
     *
     * @param id
     * @param expression
     * @param messages
     * @return
     */
    open fun npc(id: Int, expression: FacialExpression?, vararg messages: String?): Component? {
        val chatBoxComponent = interpreter!!.sendDialogues(id, expression, *messages)
        return chatBoxComponent
    }

    /**
     * Npc
     *
     * @param id
     * @param title
     * @param expression
     * @param messages
     * @return
     */
    open fun npc(id: Int, title: String, expression: FacialExpression?, vararg messages: String?): Component? {
        val chatBoxComponent = interpreter!!.sendDialogues(id, expression, *messages)
        player!!.packetDispatch.sendString(title, chatBoxComponent.id, 3)
        return chatBoxComponent
    }

    /**
     * Player
     *
     * @param messages
     * @return
     */
    open fun player(vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, null, *messages)
    }

    /**
     * Player
     *
     * @param expression
     * @param messages
     * @return
     */
    open fun player(expression: FacialExpression?, vararg messages: String?): Component? {
        return interpreter!!.sendDialogues(player, expression, *messages)
    }

    /**
     * Npcl
     *
     * @param expr
     * @param msg
     * @return
     */
    open fun npcl(expr: FacialExpression?, msg: String?): Component? {
        return npc(expr, *splitLines(msg!!))
    }

    /**
     * Npcl
     *
     * @param id
     * @param expr
     * @param msg
     * @return
     */
    open fun npcl(id: Int, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, expr, *splitLines(msg!!))
    }

    /**
     * Npcl
     *
     * @param id
     * @param title
     * @param expr
     * @param msg
     * @return
     */
    open fun npcl(id: Int, title: String, expr: FacialExpression?, msg: String?): Component? {
        return npc(id, title, expr, *splitLines(msg!!))
    }

    /**
     * Npcl
     *
     * @param msg
     * @return
     */
    open fun npcl(msg: String?): Component? {
        return npc(*splitLines(msg!!))
    }

    /**
     * Playerl
     *
     * @param expr
     * @param msg
     * @return
     */
    open fun playerl(expr: FacialExpression?, msg: String?): Component? {
        return player(expr, *splitLines(msg!!))
    }

    /**
     * Playerl
     *
     * @param msg
     * @return
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
     * @param entity
     * @param expression
     * @param messages
     */
    open fun sendNormalDialogue(entity: Entity?, expression: FacialExpression?, vararg messages: String?) {
        interpreter!!.sendDialogues(entity, expression, *messages)
    }

    /**
     * Options
     *
     * @param options
     * @param title
     */
    open fun options(vararg options: String?, title: String = "Select an Option") {
        interpreter!!.sendOptions(title, *options)
    }

    /**
     * End file
     *
     */
    fun endFile() {
        interpreter!!.dialogue.file = null

    }

    /**
     * Return at stage
     *
     * @param toStage
     */
    fun returnAtStage(toStage: Int) {
        dialogue!!.file = null
        dialogue!!.stage = toStage
    }

    /**
     * Abandon file
     *
     */
    fun abandonFile() {
        interpreter!!.dialogue.file = null
        player("Nevermind.")
    }

    /**
     * Get current stage
     *
     * @return
     */
    open fun getCurrentStage(): Int {
        return stage
    }

    /**
     * Substage
     *
     * @param stage
     * @return
     */
    fun Int.substage(stage: Int): Int {
        return this + stage
    }

    /**
     * Dialogue
     *
     * @param messages
     */
    fun dialogue(vararg messages: String) {
        player?.dialogueInterpreter?.sendDialogue(*messages)
    }

    /**
     * Show topics
     *
     * @param topics
     * @param title
     * @return
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
