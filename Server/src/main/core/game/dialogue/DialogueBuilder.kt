package core.game.dialogue

import core.api.splitLines
import core.game.node.entity.player.Player
import core.tools.END_DIALOGUE
import java.util.regex.Pattern

val DEBUG_DIALOGUE = false
val NUMBER_PATTERN1 = Pattern.compile("^(\\d+) \\[label", Pattern.MULTILINE)
val NUMBER_PATTERN2 = Pattern.compile("(\\d+) -> (\\d+)")

/**
 * Dialogue builder file
 *
 * @constructor Dialogue builder file
 */
abstract class DialogueBuilderFile : DialogueFile() {
    var data: ArrayList<DialogueClause> = ArrayList()

    //var stages: ArrayList<Int> = ArrayList()

    /**
     * Create
     *
     * @param b
     */
    abstract fun create(b: DialogueBuilder)

    init {
        create(DialogueBuilder(this))
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
 * Dialogue node
 *
 * @constructor Dialogue node
 */
interface DialogueNode {
    /**
     * Handle
     *
     * @param df
     * @param componentID
     * @param buttonID
     * @param stage
     * @return
     */
    fun handle(df: DialogueFile, componentID: Int, buttonID: Int, stage: Int): Int
}

/**
 * Npc l node
 *
 * @property expression
 * @property value
 * @constructor Npc l node
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
 * Npc node
 *
 * @property expression
 * @property values
 * @constructor Npc node
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
 * Item node
 *
 * @property item
 * @property values
 * @constructor Item node
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
 * Player l node
 *
 * @property expression
 * @property value
 * @constructor Player l node
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
 * Player node
 *
 * @property expression
 * @property values
 * @constructor Player node
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
 * Between stage node
 *
 * @property f
 * @constructor Between stage node
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
 * Manual stage node
 *
 * @property f
 * @constructor Manual stage node
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
 * Manual stage with goto node
 *
 * @property f
 * @constructor
 *
 * @param g
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
 * Placeholder node
 *
 * @property dbf
 * @property clauseIndex
 * @property targetStage
 * @constructor Placeholder node
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
     * Builder
     *
     * @return
     */
    fun builder(): DialogueBuilder {
        targetStage = dbf.data[clauseIndex].nodes.size
        return DialogueBuilder(dbf, clauseIndex)
    }
}

/**
 * Goto node
 *
 * @property node
 * @constructor Goto node
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
 * Option entry
 *
 * @property text
 * @property nextStage
 * @property predicate
 * @constructor Option entry
 */
class OptionEntry(val text: String, val nextStage: Int, val predicate: (Player) -> Boolean = { _ -> true })

/**
 * Options data
 *
 * @property header
 * @property entries
 * @property attr
 * @constructor Options data
 */
class OptionsData(val header: String, val entries: ArrayList<OptionEntry>, var attr: String? = null)

/**
 * Options node
 *
 * @property options
 * @constructor Options node
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
     * Option names
     *
     * @param player
     * @return
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
 * Options dispatch node
 *
 * @property options
 * @constructor Options dispatch node
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
 * Dialogue clause
 *
 * @property predicate
 * @property nodes
 * @constructor Dialogue clause
 */
class DialogueClause(val predicate: (player: Player) -> Boolean, val nodes: ArrayList<DialogueNode>) {
    /**
     * Handle
     *
     * @param df
     * @param componentID
     * @param buttonID
     * @param stage
     * @return
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
 * Dialogue options builder
 *
 * @property target
 * @property clauseIndex
 * @property options
 * @constructor Dialogue options builder
 */
class DialogueOptionsBuilder(var target: DialogueBuilderFile, val clauseIndex: Int, var options: OptionsData) {
    /**
     * Option
     *
     * @param value
     * @return
     */
    fun option(value: String): DialogueBuilder {
        options.entries.add(OptionEntry(value, target.data[clauseIndex].nodes.size))
        return DialogueBuilder(target, clauseIndex)
    }

    /**
     * Option if
     *
     * @param value
     * @param predicate
     * @receiver
     * @return
     */
    fun optionIf(value: String, predicate: (Player) -> Boolean): DialogueBuilder {
        options.entries.add(OptionEntry(value, target.data[clauseIndex].nodes.size, predicate))
        return DialogueBuilder(target, clauseIndex)
    }

    /**
     * Option_playerl
     *
     * @param value
     * @return
     */
    fun option_playerl(value: String): DialogueBuilder {
        return option(value).playerl(value)
    }

    /**
     * Record attribute
     *
     * @param name
     * @return
     */
    fun recordAttribute(name: String): DialogueOptionsBuilder {
        options.attr = name
        return this
    }
}

/**
 * Branches data
 *
 * @property branches
 * @property f
 * @constructor Branches data
 */
class BranchesData(val branches: HashMap<Int, Int>, val f: (Player) -> Int)

/**
 * Branch node
 *
 * @property branches
 * @constructor Branch node
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
 * @property target
 * @property clauseIndex
 * @property branches
 * @constructor Dialogue branch builder
 */
class DialogueBranchBuilder(var target: DialogueBuilderFile, val clauseIndex: Int, var branches: BranchesData) {
    /**
     * On value
     *
     * @param value
     * @return
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
     * On value
     *
     * @param value
     * @return
     */
    fun onValue(value: Int): DialogueBuilder {
        val index = target.data[clauseIndex].nodes.size
        branches.branches.put(value, index)
        return DialogueBuilder(target, clauseIndex)
    }
}

/**
 * End with node
 *
 * @property f
 * @constructor End with node
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
 * Dialogue builder
 *
 * @property target
 * @property clauseIndex
 * @constructor Dialogue builder
 */
class DialogueBuilder(var target: DialogueBuilderFile, var clauseIndex: Int = -1) {
    companion object DialogueBuilderStatic {
        @JvmStatic
        fun endWithNoop(df: DialogueFile, player: Player) {
        }
    }

    /**
     * On predicate
     *
     * @param predicate
     * @receiver
     * @return
     */
    fun onPredicate(predicate: (player: Player) -> Boolean): DialogueBuilder {
        target.data.add(DialogueClause(predicate, ArrayList()))
        clauseIndex = target.data.size - 1
        return this
    }

    /**
     * Default dialogue
     *
     * @return
     */
    fun defaultDialogue(): DialogueBuilder {
        return onPredicate({ _ -> return@onPredicate true })
    }

    /**
     * On quest stages
     *
     * @param name
     * @param stages
     * @return
     */
    fun onQuestStages(name: String, vararg stages: Int): DialogueBuilder {
        return onPredicate { player ->
            val questStage = player.questRepository.getStage(name)
            return@onPredicate stages.contains(questStage)
        }
    }

    /**
     * Playerl
     *
     * @param value
     * @return
     */
    fun playerl(value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerLNode(FacialExpression.NEUTRAL, value))
        return this
    }

    /**
     * Playerl
     *
     * @param expression
     * @param value
     * @return
     */
    fun playerl(expression: FacialExpression, value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerLNode(expression, value))
        return this
    }

    /**
     * Player
     *
     * @param values
     * @return
     */
    fun player(vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerNode(FacialExpression.NEUTRAL, values as Array<String>))
        return this
    }

    /**
     * Player
     *
     * @param expression
     * @param values
     * @return
     */
    fun player(expression: FacialExpression, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(PlayerNode(expression, values as Array<String>))
        return this
    }

    /**
     * Npcl
     *
     * @param value
     * @return
     */
    fun npcl(value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcLNode(FacialExpression.NEUTRAL, value))
        return this
    }

    /**
     * Npcl
     *
     * @param expression
     * @param value
     * @return
     */
    fun npcl(expression: FacialExpression, value: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcLNode(expression, value))
        return this
    }

    /**
     * Npc
     *
     * @param values
     * @return
     */
    fun npc(vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcNode(FacialExpression.NEUTRAL, values as Array<String>))
        return this
    }

    /**
     * Npc
     *
     * @param expression
     * @param values
     * @return
     */
    fun npc(expression: FacialExpression, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(NpcNode(expression, values as Array<String>))
        return this
    }

    /**
     * Item
     *
     * @param item
     * @param values
     * @return
     */
    fun item(item: Int, vararg values: String): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ItemNode(item, values as Array<String>))
        return this
    }

    /**
     * Iteml
     *
     * @param item
     * @param value
     * @return
     */
    fun iteml(item: Int, value: String): DialogueBuilder {
        return item(item, *splitLines(value))
    }

    /**
     * Line
     *
     * @param values
     * @return
     */
    fun line(vararg values: String): DialogueBuilder {
        return manualStage { df, player, _, _ ->
            df.interpreter!!.sendDialogue(*(values as Array<String>))
        }
    }

    /**
     * Linel
     *
     * @param value
     * @return
     */
    fun linel(value: String): DialogueBuilder {
        return manualStage { df, _, _, _ ->
            df.interpreter!!.sendDialogue(*splitLines(value))
        }
    }

    /**
     * End with
     *
     * @param f
     * @receiver
     */
    fun endWith(f: (DialogueFile, Player) -> Unit) {
        target.data[clauseIndex].nodes.add(EndWithNode(f))
    }

    /**
     * End
     *
     */
    fun end() {
        target.data[clauseIndex].nodes.add(EndWithNode(::endWithNoop))
    }

    /**
     * Between stage
     *
     * @param f
     * @receiver
     * @return
     */
    fun betweenStage(f: (DialogueFile, Player, Int, Int) -> Unit): DialogueBuilder {
        target.data[clauseIndex].nodes.add(BetweenStageNode(f))
        return this
    }

    /**
     * Manual stage
     *
     * @param f
     * @receiver
     * @return
     */
    fun manualStage(f: (DialogueFile, Player, Int, Int) -> Unit): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ManualStageNode(f))
        return this
    }

    /**
     * Manual stage with goto
     *
     * @param f
     * @param g
     * @receiver
     * @receiver
     * @return
     */
    fun manualStageWithGoto(f: (DialogueFile, Player, Int, Int, Int) -> Int, g: (Int) -> Int): DialogueBuilder {
        target.data[clauseIndex].nodes.add(ManualStageWithGotoNode(f, g))
        return this
    }

    /**
     * Options
     *
     * @return
     */
    fun options(): DialogueOptionsBuilder {
        return options("Select an Option")
    }

    /**
     * Options
     *
     * @param header
     * @return
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
     * Placeholder
     *
     * @return
     */
    fun placeholder(): PlaceholderNode {
        val node = PlaceholderNode(target, clauseIndex, END_DIALOGUE)
        target.data[clauseIndex].nodes.add(node)
        return node
    }

    /**
     * Goto
     *
     * @param node
     * @return
     */
    fun goto(node: PlaceholderNode): DialogueBuilder {
        target.data[clauseIndex].nodes.add(GotoNode(node))
        return this
    }

    /**
     * Branch
     *
     * @param f
     * @receiver
     * @return
     */
    fun branch(f: (Player) -> Int): DialogueBranchBuilder {
        var branches = BranchesData(HashMap(), f)
        target.data[clauseIndex].nodes.add(BranchNode(branches))
        return DialogueBranchBuilder(target, clauseIndex, branches)
    }

    /**
     * Branch stages
     *
     * @param f
     * @receiver
     * @return
     */
    fun branchStages(f: (Player) -> Int): DialogueBranchBuilder {
        var branches = BranchesData(HashMap(), f)
        target.data[clauseIndex].nodes.add(BranchNode(branches))
        return DialogueBranchBuilder(target, clauseIndex, branches)
    }

    /**
     * Branch bool attribute
     *
     * @param attrName
     * @param defaultVal
     * @return
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
