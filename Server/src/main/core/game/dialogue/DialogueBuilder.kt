package core.game.dialogue

import core.api.splitLines
import core.game.node.entity.player.Player
import core.tools.END_DIALOGUE
import java.util.regex.Pattern

val DEBUG_DIALOGUE = false
val NUMBER_PATTERN1 = Pattern.compile("^(\\d+) \\[label", Pattern.MULTILINE)
val NUMBER_PATTERN2 = Pattern.compile("(\\d+) -> (\\d+)")

/**
 * Dialogue builder.
 */
abstract class DialogueBuilderFile : DialogueFile() {
    var data: ArrayList<DialogueClause> = ArrayList()

    //var stages: ArrayList<Int> = ArrayList()

    /**
     * Abstract function to create a dialogue.
     *
     * @param b The DialogueBuilder object used to build the dialogue.
     */
    abstract fun create(b: DialogueBuilder)

    init {
        // Initialize the dialogue creation process by
        // calling the create function with a new DialogueBuilder object.
        create(DialogueBuilder(this))
        // Check if DEBUG_DIALOGUE flag is enabled
        // for debugging purposes.
        if (DEBUG_DIALOGUE) {
            var graphSb = StringBuilder("digraph {\n")
            var nodeIndex = 0
            for ((i, clause) in data.iterator().withIndex()) {
                var clauseSb = StringBuilder()
                for (j in 0 until clause.nodes.size) {
                    if (!(clause.nodes[j] is OptionsDispatchNode)) {
                        clauseSb.append(
                            "${j} ${
                                clause.nodes[j].toString().replace("@indexPlus", "${j + 1}").replace("@index", "${j}")
                            }\n"
                        )
                    }
                }
                graphSb.append("subgraph cluster_${i} {\n")
                var tmpSb = StringBuilder()
                var m = NUMBER_PATTERN1.matcher(clauseSb)
                while (m.find()) {
                    var x = Integer.valueOf(m.group(1))
                    m.appendReplacement(tmpSb, "${x + nodeIndex} [label")
                }
                m.appendTail(tmpSb)
                m = NUMBER_PATTERN2.matcher(tmpSb)
                while (m.find()) {
                    var x = Integer.valueOf(m.group(1))
                    var y = Integer.valueOf(m.group(2))
                    m.appendReplacement(graphSb, "${x + nodeIndex} -> ${y + nodeIndex}")
                }
                m.appendTail(graphSb)
                graphSb.append("}\n")
                nodeIndex += clause.nodes.size
            }
            graphSb.append("}\n")
            // System.out.println(graphSb.toString())
        }
    }

    override fun handle(componentID: Int, buttonID: Int) {
        for ((i, clause) in data.iterator().withIndex()) {
            if (clause.predicate(player!!)) {
                stage = clause.handle(this, componentID, buttonID, stage)
                if (stage == END_DIALOGUE) {
                    end()
                }
                return
            }
        }
    }
}

/**
 * Interface representing a Dialogue Node in the conversation flow.
 * This interface defines a method to handle dialogue interactions.
 */
interface DialogueNode {
    /**
     * Handles the dialogue flow based on the provided parameters.
     *
     * @param df The DialogueFile containing the conversation data.
     * @param componentID The ID of the dialogue component.
     * @param buttonID The ID of the button clicked.
     * @param stage The current stage of the conversation.
     * @return The updated stage after handling the interaction.
     */
    fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int
}

/**
 * Represents a node in the NPC dialogue tree.
 *
 * @param expression The facial expression associated with the NPC node.
 * @param value The value or text content of the NPC node.
 * @constructor Creates an NPC dialogue node with the given facial expression and value.
 */
class NpcLNode(val expression: FacialExpression, val value: String) : DialogueNode {
    override fun toString(): String {
        return "[label=\"npcl(FacialExpression.${expression.name}, \\\"${value}\\\")\"]; @index -> @indexPlus;"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.npcl(expression, value)
        return stage + 1
    }
}

/**
 * Represents a node in the dialogue tree for an NPC.
 *
 * @param expression The facial expression of the NPC in this node.
 * @param values The array of possible dialogue values for this node.
 * @constructor Creates an instance of NpcNode with the given expression and values.
 */
class NpcNode(val expression: FacialExpression, val values: Array<String>) : DialogueNode {
    override fun toString(): String {
        return "[label=\"npc(FacialExpression.${expression.name}, \\\"${values.contentDeepToString()}\\\")\"]; @index -> @indexPlus;"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.npc(expression, *values)
        return stage + 1
    }
}

/**
 * Item node.
 *
 * @param item The item value.
 * @param values The array of string values.
 * @constructor Represents an ItemNode object with the given item and values.
 */
class ItemNode(val item: Int, val values: Array<String>) : DialogueNode {
    override fun toString(): String {
        return "[label=\"item(${item}, \\\"${values.contentDeepToString()}\\\")\"]; @index -> @indexPlus;"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.interpreter!!.sendItemMessage(item, *values)
        return stage + 1
    }
}

/**
 * Player l node.
 *
 * @param expression The facial expression.
 * @param value The string value.
 * @constructor Represents a PlayerLNode object with the given expression and value.
 */
class PlayerLNode(val expression: FacialExpression, val value: String) : DialogueNode {
    override fun toString(): String {
        return "[label=\"playerl(FacialExpression.${expression.name}, \\\"${value}\\\")\"]; @index -> @indexPlus;"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.playerl(expression, value)
        return stage + 1
    }
}

/**
 * Player node.
 *
 * @param expression The facial expression.
 * @param values The array of string values.
 * @constructor Represents a PlayerNode object with the given expression and values.
 */
class PlayerNode(val expression: FacialExpression, val values: Array<String>) : DialogueNode {
    override fun toString(): String {
        return "[label=\"player(FacialExpression.${expression.name}, \\\"${values.contentDeepToString()}\\\")\"]; @index -> @indexPlus;"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.player(expression, *values)
        return stage + 1
    }
}

/**
 * Between stage node.
 *
 * @param f The callback function.
 * @constructor Represents a BetweenStageNode object with the given callback function.
 */
class BetweenStageNode(val f: (DialogueFile, Player, Int, Int) -> Unit) : DialogueNode {
    override fun toString(): String {
        return "[label=\"betweenstage(${(f as Object).getClass().getName()})\"]; @index -> @indexPlus"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.stage = stage + 1 // Simulates moving to this stage.
        f(df, df.player!!, componentID, buttonID) // Calls callback function.
        df.handle(componentID, buttonID) // Handles the current stage
        return stage + 2 // Skips over the current stage.
    }
}

/**
 * Manual stage node.
 *
 * @param f The callback function.
 * @constructor Represents a ManualStageNode object with the given callback function.
 */
class ManualStageNode(val f: (DialogueFile, Player, Int, Int) -> Unit) : DialogueNode {
    override fun toString(): String {
        return "[label=\"manualstage(${(f as Object).getClass().getName()})\"]; @index -> @indexPlus"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        f(df, df.player!!, componentID, buttonID)
        return stage + 1
    }
}

/**
 * Manual stage with goto node.
 *
 * @param f The callback function.
 * @constructor Represents a ManualStageWithGotoNode object with the given callback function and goto function.
 *
 * @param g The goto function.
 */
class ManualStageWithGotoNode(val f: (DialogueFile, Player, Int, Int, Int) -> Int, g: (Int) -> Int) : DialogueNode {
    override fun toString(): String {
        return "[label=\"manualstagewithgoto(${(f as Object).getClass().getName()})\"]; @index -> @indexPlus"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        return f(df, df.player!!, componentID, buttonID, stage)
    }
}

/**
 * Placeholder node.
 *
 * @param dbf The DialogueBuilderFile object.
 * @param clauseIndex The index of the clause.
 * @param targetStage The target stage.
 * @constructor Represents a PlaceholderNode object with the given DialogueBuilderFile, clause index, and target stage.
 */
class PlaceholderNode(val dbf: DialogueBuilderFile, val clauseIndex: Int, var targetStage: Int) : DialogueNode {
    override fun toString(): String {
        return "[label=\"placeholder(${targetStage})\"]; @index -> ${targetStage}"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.stage = targetStage
        df.handle(componentID, buttonID)
        return df.stage
    }

    /**
     * Builder.
     *
     * @return The DialogueBuilder object.
     */
    fun builder(): DialogueBuilder {
        targetStage = dbf.data[clauseIndex].nodes.size
        return DialogueBuilder(dbf, clauseIndex)
    }
}

/**
 * Goto node.
 *
 * @param node The PlaceholderNode object.
 * @constructor Represents a GotoNode object with the given PlaceholderNode.
 */
class GotoNode(val node: PlaceholderNode) : DialogueNode {
    override fun toString(): String {
        return "[label=\"goto(${node.targetStage})\"]; @index -> ${node.targetStage}"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.stage = node.targetStage
        df.handle(componentID, buttonID)
        return df.stage
    }
}

/**
 * Option entry.
 *
 * @param text The text of the option.
 * @param nextStage The next stage to proceed to.
 * @param predicate The condition for the option to be available.
 * @constructor Creates an OptionEntry.
 */
class OptionEntry(val text: String, val nextStage: Int, val predicate: (Player) -> Boolean = { _ -> true })

/**
 * Options data.
 *
 * @param header The header of the options.
 * @param entries The list of OptionEntry objects.
 * @param attr The attribute associated with the options.
 * @constructor Creates an OptionsData object.
 */
class OptionsData(val header: String, val entries: ArrayList<OptionEntry>, var attr: String? = null)

/**
 * Options node.
 *
 * @param options The OptionsData object.
 * @constructor Creates an OptionsNode.
 */
class OptionsNode(var options: OptionsData) : DialogueNode {
    override fun toString(): String {
        var ret = "[label=\"options(\\\"${options.header}\\\")\"]; "
        for (entry in options.entries) {
            ret += "@index -> ${entry.nextStage} [label=\"\\\"${entry.text}\\\"\"]; "
        }
        return ret
    }

    /**
     * Get the names of the available options for a player.
     *
     * @param player The player object.
     * @return An array of option names.
     */
    fun optionNames(player: Player): Array<String?> {
        return options.entries.asSequence().filter({ it.predicate(player) }).map({ it.text }).toList().toTypedArray()
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        val tmp: Array<String?> = optionNames(df.player!!)
        if (DEBUG_DIALOGUE) {
            System.out.println("OptionsNode: ${tmp.size}")
            for (i in 0 until tmp.size) {
                System.out.println("tmp[${i}]: ${tmp[i]}")
            }
        }
        if (tmp.size > 5) {
            System.out.printf("Only a maximum of 5 options are supported by the interface")
            return END_DIALOGUE
        } else if (tmp.size > 1) {
            df.interpreter!!.sendOptions(options.header, *tmp)
            return stage + 1
        } else if (tmp.size == 1) {
            val tmp: List<OptionEntry> = options.entries.asSequence().filter({ it.predicate(df.player!!) }).toList()
            df.stage = tmp[0].nextStage
            df.handle(componentID, 0)
            return df.stage
        } else {
            return END_DIALOGUE
        }
    }
}

/**
 * Options dispatch node.
 *
 * @param options The OptionsData object.
 * @constructor Creates an OptionsDispatchNode.
 */
class OptionsDispatchNode(var options: OptionsData) : DialogueNode {
    override fun toString(): String {
        return "[label=\"OptionsDispatchNode\"]"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        val tmp: List<OptionEntry> = options.entries.asSequence().filter({ it.predicate(df.player!!) }).toList()
        if (DEBUG_DIALOGUE) {
            System.out.println("OptionsDispatchNode: ${tmp.size}")
        }
        if (options.attr != null) {
            df.player!!.setAttribute(options.attr, buttonID - 1)
        }
        df.stage = tmp[buttonID - 1].nextStage
        df.handle(componentID, 0)
        return df.stage
    }
}


/**
 * Dialogue clause.
 *
 * @param predicate A function that takes a Player object as a parameter and returns a Boolean value.
 * @param nodes An ArrayList of DialogueNode objects.
 * @constructor Creates a DialogueClause object.
 */
class DialogueClause(val predicate: (player: Player) -> Boolean, val nodes: ArrayList<DialogueNode>) {

    /**
     * Handle method.
     *
     * @param df A DialogueFile object.
     * @param componentID An integer representing the component ID.
     * @param buttonID An integer representing the button ID.
     * @param stage An integer representing the stage.
     * @return An integer representing the result of the handle operation.
     */
    fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        if (stage < nodes.size) {
            return nodes[stage].handle(df, componentID, buttonID, stage)
        } else {
            return END_DIALOGUE
        }
    }
}

/**
 * Dialogue options builder.
 *
 * @param target A DialogueBuilderFile object.
 * @param clauseIndex An integer representing the clause index.
 * @param options An OptionsData object.
 * @constructor Creates a DialogueOptionsBuilder object.
 */
class DialogueOptionsBuilder(var target: DialogueBuilderFile, val clauseIndex: Int, var options: OptionsData) {

    /**
     * Option method.
     *
     * @param value A string representing the option value.
     * @return A DialogueBuilder object.
     */
    fun option(value: String): DialogueBuilder {
        options.entries.add(OptionEntry(value, target.data[clauseIndex].nodes.size))
        return DialogueBuilder(target, clauseIndex)
    }

    /**
     * OptionIf method.
     *
     * @param value A string representing the option value.
     * @param predicate A function that takes a Player object as a parameter and returns a Boolean value.
     * @return A DialogueBuilder object.
     */
    fun optionIf(value: String, predicate: (Player) -> Boolean): DialogueBuilder {
        options.entries.add(OptionEntry(value, target.data[clauseIndex].nodes.size, predicate))
        return DialogueBuilder(target, clauseIndex)
    }

    /**
     * Option_playerl method.
     *
     * @param value A string representing the option value.
     * @return A DialogueBuilder object.
     */
    fun option_playerl(value: String): DialogueBuilder {
        return option(value).playerl(value)
    }

    /**
     * RecordAttribute method.
     *
     * @param name A string representing the attribute name.
     * @return A DialogueOptionsBuilder object.
     */
    fun recordAttribute(name: String): DialogueOptionsBuilder {
        options.attr = name
        return this
    }
}

/**
 * Represents the data for branches in a dialogue.
 *
 * @param branches A HashMap containing branch IDs and corresponding dialogue node IDs.
 * @param f A lambda function that takes a Player and returns an Int.
 * @constructor Creates a BranchesData instance with the provided branches and lambda function.
 */
class BranchesData(val branches: HashMap<Int, Int>, val f: (Player) -> Int)

/**
 * Represents a node in a dialogue tree that contains branches.
 *
 * @param branches The BranchesData associated with this branch node.
 * @constructor Creates a BranchNode with the provided BranchesData.
 */
class BranchNode(val branches: BranchesData) : DialogueNode {
    override fun toString(): String {
        var ret = "[label=\"branch(\\\"${(branches.f as Object).getClass().getName()}\\\")\"]; "
        for (entry in branches.branches) {
            ret += "@index -> ${entry.value} [label=\"\\\"${entry.key}\\\"\"]; "
        }
        return ret
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        df.stage = branches.branches[branches.f(df.player!!)] ?: END_DIALOGUE
        df.handle(componentID, buttonID)
        return df.stage
    }
}

/**
 * Dialogue branch builder
 *
 * @param target
 * @param clauseIndex
 * @param branches
 * @constructor Dialogue branch builder
 */
class DialogueBranchBuilder(var target: DialogueBuilderFile, val clauseIndex: Int, var branches: BranchesData) {
    /**
     * Converts a Boolean value to an Integer and calls the overloaded
     * onValue function.
     *
     * @param value the Boolean value to be converted.
     * @return a DialogueBuilder object.
     */
    fun onValue(value: Boolean): DialogueBuilder {
        return onValue(
            if (value) {
                1
            } else {
                0
            }
        )
    }

    /**
     * Maps an Integer value to an index and updates the branches,
     * then returns a DialogueBuilder object.
     *
     * @param value the Integer value to be mapped.
     * @return a DialogueBuilder object.
     */
    fun onValue(value: Int): DialogueBuilder {
        val index = target.data[clauseIndex].nodes.size
        branches.branches.put(value, index)
        return DialogueBuilder(target, clauseIndex)
    }
}

/**
 * Represents an EndWithNode with a function property.
 *
 * @param f the function property of the EndWithNode.
 * @constructor Creates an EndWithNode object with a function property.
 */
class EndWithNode(val f: (DialogueFile, Player) -> Unit) : DialogueNode {
    override fun toString(): String {
        return "[label=\"endWith(${(f as Object).getClass().getName()})\"]"
    }

    override fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int {
        f(df, df.player!!)
        return END_DIALOGUE
    }
}

/**
 * Represents a DialogueBuilder with target and clauseIndex properties.
 *
 * @param target the target property of the DialogueBuilder.
 * @param clauseIndex the clauseIndex property of the DialogueBuilder.
 * @constructor Creates a DialogueBuilder object with target and clauseIndex properties.
 */
class DialogueBuilder(var target: DialogueBuilderFile, var clauseIndex: Int = -1) {
    companion object DialogueBuilderStatic {
        @JvmStatic
        fun endWithNoop(df: DialogueFile, player: Player) {
        }
    }

    /**
     * On predicate.
     *
     * @param predicate The predicate function that takes a Player object as a parameter and returns a Boolean value.
     * @receiver The current instance of the DialogueBuilder class.
     * @return The updated instance of the DialogueBuilder class.
     */
    fun onPredicate(predicate: (player: Player) -> Boolean): DialogueBuilder {
        target.data.add(DialogueClause(predicate, ArrayList()))
        clauseIndex = target.data.size - 1
        return this
    }

    /**
     * Default dialogue.
     *
     * @return The updated instance of the DialogueBuilder class.
     */
    fun defaultDialogue(): DialogueBuilder {
        return onPredicate({ _ -> return@onPredicate true })
    }

    /**
     * On quest stages.
     *
     * @param name The name of the quest.
     * @param stages The stages of the quest.
     * @return The updated instance of the DialogueBuilder class.
     */
    fun onQuestStages(name: String, vararg stages: Int): DialogueBuilder {
        return onPredicate { player ->
            val questStage = player.questRepository.getStage(name)
            return@onPredicate stages.contains(questStage)
        }
    }

    /**
     * Playerl.
     *
     * @param value The value to be displayed for the player.
     * @return The updated instance of the DialogueBuilder class.
     */
    fun playerl(value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerLNode(FacialExpression.NEUTRAL, value))
        return this
    }

    /**
     * Playerl.
     *
     * @param expression the facial expression of the player.
     * @param value the value associated with the expression.
     * @return the DialogueBuilder instance.
     */
    fun playerl(expression: FacialExpression, value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerLNode(expression, value))
        return this
    }

    /**
     * Player.
     *
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun player(vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerNode(FacialExpression.NEUTRAL, values as Array<String>))
        return this
    }

    /**
     * Player.
     *
     * @param expression the facial expression of the player.
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun player(expression: FacialExpression, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerNode(expression, values as Array<String>))
        return this
    }

    /**
     * Npcl.
     *
     * @param value the value associated with the NPC line.
     * @return the DialogueBuilder instance.
     */
    fun npcl(value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcLNode(FacialExpression.NEUTRAL, value))
        return this
    }

    /**
     * Npcl.
     *
     * @param expression the facial expression of the NPC.
     * @param value the value associated with the NPC line.
     * @return the DialogueBuilder instance.
     */
    fun npcl(expression: FacialExpression, value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcLNode(expression, value))
        return this
    }

    /**
     * Npc.
     *
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun npc(vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcNode(FacialExpression.NEUTRAL, values as Array<String>))
        return this
    }

    /**
     * Npc.
     *
     * @param expression the facial expression of the NPC.
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun npc(expression: FacialExpression, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcNode(expression, values as Array<String>))
        return this
    }

    /**
     * Item.
     *
     * @param item the item ID.
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun item(item: Int, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ItemNode(item, values as Array<String>))
        return this
    }

    /**
     * Iteml.
     *
     * @param item the item ID.
     * @param value the value associated with the item line.
     * @return the DialogueBuilder instance.
     */
    fun iteml(item: Int, value: String): DialogueBuilder {
        return item(item, *splitLines(value))
    }

    /**
     * Line.
     *
     * @param values the values to be used in the dialogue.
     * @return the DialogueBuilder instance.
     */
    fun line(vararg values: String): DialogueBuilder {
        return manualStage { df, player, _, _ ->
            df.interpreter!!.sendDialogue(*(values as Array<String>))
        }
    }

    /**
     * Linel.
     *
     * @param value the value associated with the line.
     * @return the DialogueBuilder instance.
     */
    fun linel(value: String): DialogueBuilder {
        return manualStage { df, _, _, _ ->
            df.interpreter!!.sendDialogue(*splitLines(value))
        }
    }

    /**
     * End with.
     *
     * @param f the function to be executed at the end of the dialogue.
     * @receiver the DialogueFile instance.
     */
    fun endWith(f: (DialogueFile, Player) -> Unit) {
        target.data[clauseIndex].nodes.add(EndWithNode(f))
    }

    /**
     * End
     */
    fun end() {
        target.data[clauseIndex].nodes.add(EndWithNode(::endWithNoop))
    }

    /**
     * Between stage.
     *
     * @param f the function to be executed between stages of the dialogue.
     * @receiver the DialogueFile instance.
     * @return the DialogueBuilder instance.
     */
    fun betweenStage(f: (DialogueFile, Player, Int, Int) -> Unit): DialogueBuilder {
        target.data[clauseIndex].nodes.add(BetweenStageNode(f))
        return this
    }

    /**
     * Manual stage.
     *
     * @param f the function to be executed at a manual stage of the dialogue.
     * @receiver the DialogueFile instance.
     * @return the DialogueBuilder instance.
     */
    fun manualStage(f: (DialogueFile, Player, Int, Int) -> Unit): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ManualStageNode(f))
        return this
    }

    /**
     * Manual stage with goto.
     *
     * @param f The function that defines the behavior of the manual stage.
     * @param g The function that defines the behavior of the goto.
     * @receiver The receiver of the function.
     * @return The updated DialogueBuilder object.
     */
    fun manualStageWithGoto(f: (DialogueFile, Player, Int, Int, Int) -> Int, g: (Int) -> Int): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ManualStageWithGotoNode(f, g))
        return this
    }

    /**
     * Options.
     *
     * @return The DialogueOptionsBuilder object.
     */
    fun options(): DialogueOptionsBuilder {
        return options("Select an Option")
    }

    /**
     * Options.
     *
     * @param header The header text for the options.
     * @return The DialogueOptionsBuilder object.
     */
    fun options(header: String): DialogueOptionsBuilder {
        val options: ArrayList<OptionEntry> = ArrayList()
        val optionsData = OptionsData(header, options)
        val node = OptionsNode(optionsData)
        val dispatchNode = OptionsDispatchNode(optionsData)
        target.data[clauseIndex].nodes.add(node)
        target.data[clauseIndex].nodes.add(dispatchNode)
        return DialogueOptionsBuilder(target, clauseIndex, optionsData)
    }

    /**
     * Placeholder.
     *
     * @return The PlaceholderNode object.
     */
    fun placeholder(): PlaceholderNode {
        val node = PlaceholderNode(target, clauseIndex, END_DIALOGUE)
        target.data[clauseIndex].nodes.add(node)
        return node
    }

    /**
     * Goto.
     *
     * @param node The PlaceholderNode to go to.
     * @return The updated DialogueBuilder object.
     */
    fun goto(node: PlaceholderNode): DialogueBuilder {
        target.data[clauseIndex].nodes.add(GotoNode(node))
        return this
    }

    /**
     * Branch.
     *
     * @param f The function that defines the behavior of the branch.
     * @receiver The receiver of the function.
     * @return The DialogueBranchBuilder object.
     */
    fun branch(f: (Player) -> Int): DialogueBranchBuilder {
        var branches = BranchesData(HashMap(), f)
        target.data[clauseIndex].nodes.add(BranchNode(branches))
        return DialogueBranchBuilder(target, clauseIndex, branches)
    }

    /**
     * Branch stages.
     *
     * @param f The function that defines the behavior of the branch stages.
     * @receiver The receiver of the function.
     * @return The DialogueBranchBuilder object.
     */
    fun branchStages(f: (Player) -> Int): DialogueBranchBuilder {
        var branches = BranchesData(HashMap(), f)
        target.data[clauseIndex].nodes.add(BranchNode(branches))
        return DialogueBranchBuilder(target, clauseIndex, branches)
    }

    /**
     * Branch bool attribute.
     *
     * @param attrName The name of the boolean attribute.
     * @param defaultVal The default value of the boolean attribute.
     * @return The DialogueBranchBuilder object.
     */
    fun branchBoolAttribute(attrName: String, defaultVal: Boolean): DialogueBranchBuilder {
        return branch({ player ->
            return@branch if (player.getAttribute(attrName, defaultVal)) {
                1
            } else {
                0
            }
        })
    }
}
