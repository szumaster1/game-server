package core.cache.def.impl

import core.cache.Cache
import core.net.packet.IoBuffer
import core.net.packet.PacketHeader
import java.nio.ByteBuffer
import java.util.*

/**
 * Interface definition.
 */
class IfaceDefinition {

    var id: Int = 0
    var type: ComponentType? = null
    var version: Int = 0
    var clientCode: Int = 0
    var baseX: Int = 0
    var baseY: Int = 0
    var baseWidth: Int = 0
    var baseHeight: Int = 0
    var dynWidth: Int = 0
    var dynHeight: Int = 0
    var xMode: Int = 0
    var yMode: Int = 0
    var overlayer: Int = 0
    var hidden: Boolean = false
    var scrollMaxH: Int = 0
    var scrollMaxV: Int = 0
    var noClickThrough: Boolean = false
    var spriteId: Int = 0
    var activeSpriteId: Int = 0
    var angle2d: Int = 0
    var hasAlpha: Boolean = false
    var spriteTiling: Boolean = false
    var alpha: Int = 0
    var outlineThickness: Int = 0
    var shadowColor: Int = 0
    var hFlip: Boolean = false
    var vFlip: Boolean = false
    var modelType: Int = 0
    var activeModelType: Int = 0
    var modelId: Int = 0
    var activeModelId: Int = 0
    var unknownModelProp_1: Int = 0
    var unknownModelProp_2: Int = 0
    var modelXAngle: Int = 0
    var modelYAngle: Int = 0
    var modelYOffset: Int = 0
    var modelZoom: Int = 0
    var modelAnimId: Int = 0
    var activeModelAnimId: Int = 0
    var modelOrtho: Boolean = false
    var unknownModelProp_3: Int = 0
    var unknownModelProp_4: Int = 0
    var unknownModelProp_5: Boolean = false
    var unknownModelProp_6: Int = 0
    var unknownModelProp_7: Int = 0
    var font: Int = 0
    var text: String? = null
    var activeText: String? = null
    var vPadding: Int = 0
    var halign: Int = 0
    var valign: Int = 0
    var shadowed: Boolean = false
    var color: Int = 0
    var activeColor: Int = 0
    var overColor: Int = 0
    var unknownColor: Int = 0
    var filled: Boolean = false
    var lineWidth: Int = 0
    var unknownProp_8: Boolean = false
    var unknownIntArray_1: IntArray? = null
    var unknownIntArray_2: IntArray? = null
    var unknownByteArray_1: ByteArray? = null
    var unknownByteArray_2: ByteArray? = null
    var optionBase: String? = null
    var ops = arrayOfNulls<String>(5)
    var dragDeadzone: Int = 0
    var dragDeadtime: Int = 0
    var dragRenderBehavior: Boolean = false
    var opCircumfix: String? = null
    var opSuffix: String? = null
    var option: String? = null
    var unknownProp_9: Int = 0
    var unknownProp_10: Int = 0
    var unknownProp_11: Int = 0
    var cs1ComparisonOperands: IntArray? = null
    var cs1ComparisonOpcodes: IntArray? = null
    var cs1Scripts: Array<IntArray>? = null
    var objCounts: IntArray? = null
    var objTypes: IntArray? = null
    var invMarginX: Int = 0
    var invMarginY: Int = 0
    var invOffsetX: IntArray? = null
    var invOffsetY: IntArray? = null
    var invSprite: IntArray? = null
    var buttonType: Int = 0
    var invOptions = arrayOfNulls<String>(5)
    var scripts: LinkedScripts? = null
    var triggers: ScriptTriggers? = null
    var children: Array<IfaceDefinition>? = null
    var parent: Int = 0

    override fun toString(): String {
        return if (parent == id) {
            "[IF $id (P)]"
        } else {
            "[IF ${id shr 16}, CH ${id and 0xFF} (${type?.name})]"
        }
    }

    companion object {
        private val defCache = HashMap<Int, IfaceDefinition>()

        /**
         * For id interface definition.
         *
         * @param id the id
         * @return the interface definition
         */
        @JvmStatic
        fun forId(id: Int): IfaceDefinition {
            return defCache[id] ?: loadAndParse(id).also { defCache[id] = it }
        }

        /**
         * For id interface definition.
         *
         * @param id    the id
         * @param child the child
         * @return the interface definition
         */
        @JvmStatic
        fun forId(id: Int, child: Int): IfaceDefinition {
            return defCache[child + (id shl 16)] ?: forId(id).children!![child]
        }

        private fun loadAndParse(id: Int): IfaceDefinition {
            val def = IfaceDefinition()
            val childCount = Cache.getIndexes()[3].getFilesSize(id)
            def.children = Array(childCount) { parseChild(id, it) }
            def.id = id
            def.parent = id
            return def
        }

        private fun parseChild(id: Int, childIndex: Int): IfaceDefinition {
            val def = IfaceDefinition()
            def.id = childIndex + (id shl 16)
            def.parent = id
            defCache[def.id] = def

            val dataRaw = Cache.getIndexes()[3].getFileData(id, childIndex) ?: return def
            val data = IoBuffer(-1, PacketHeader.NORMAL, ByteBuffer.wrap(dataRaw))

            if (dataRaw[0] == (-1).toByte()) {
                decodeIf3(def, data)
            } else {
                decodeIf1(def, data)
            }

            return def
        }

        private fun decodeIf1(def: IfaceDefinition, data: IoBuffer) {
            data.g1()
            def.version = 1
            def.type = ComponentType.values()[data.g1()]
            def.buttonType = data.g1()
            def.clientCode = data.g2()
            def.baseX = data.g2b()
            def.baseY = data.g2b()
            def.baseWidth = data.g2()
            def.baseHeight = data.g2()
            def.dynWidth = 0
            def.dynHeight = 0
            def.xMode = 0
            def.yMode = 0
            def.alpha = data.g1()
            def.overlayer = data.g2()
            if (def.overlayer == 65535) def.overlayer = -1
            else def.overlayer += def.id and -0x10000
            def.unknownProp_11 = data.g2()

            val unknownLocal_1 = data.g1()
            if (unknownLocal_1 > 0) {
                def.cs1ComparisonOpcodes = IntArray(unknownLocal_1)
                def.cs1ComparisonOperands = IntArray(unknownLocal_1)
                for (i in 0 until unknownLocal_1) {
                    def.cs1ComparisonOpcodes!![i] = data.g1()
                    def.cs1ComparisonOperands!![i] = data.g2()
                }
            }

            val unknownLocal_2 = data.g1()
            if (unknownLocal_2 > 0) {
                def.cs1Scripts = Array(unknownLocal_2) { IntArray(data.g2()) }
                for (i in 0 until unknownLocal_2) {
                    for (j in def.cs1Scripts!![i].indices) {
                        def.cs1Scripts!![i][j] = data.g2()
                        if (def.cs1Scripts!![i][j] == 65535) def.cs1Scripts!![i][j] = -1
                    }
                }
            }

            var flags = 0
            when (def.type!!) {
                ComponentType.SCROLLABLE -> {
                    def.scrollMaxV = data.g2()
                    def.hidden = data.g1() == 1
                }
                ComponentType.UNKNOWN_1 -> {
                    data.g2()
                    data.g1()
                }
                ComponentType.UNKNOWN_2 -> {
                    def.dynHeight = 3
                    def.objCounts = IntArray(def.baseWidth * def.baseHeight)
                    def.objTypes = IntArray(def.baseWidth * def.baseHeight)
                    def.dynWidth = 3
                    val unknownLocal_4 = data.g1()
                    val unknownLocal_5 = data.g1()
                    if (unknownLocal_4 == 1) flags = 268435456
                    val unknownLocal_6 = data.g1()
                    if (unknownLocal_5 == 1) flags = 268435456 or 1073741824
                    val unknownLocal_7 = data.g1()
                    if (unknownLocal_6 == 1) flags = 268435456 or -0x80000000
                    def.objTypes = IntArray(20)
                    def.objCounts = IntArray(20)
                    for (i in 0..19) {
                        def.objTypes!![i] = data.g2()
                        def.objCounts!![i] = data.g2b()
                    }
                    def.invOptions = arrayOfNulls(5)
                    for (i in 0..4) {
                        def.invOptions[i] = data.jagString
                    }
                    def.invMarginX = data.g1()
                    def.invMarginY = data.g1()
                    def.invOffsetX = IntArray(20)
                    def.invOffsetY = IntArray(20)
                    for (i in 0..19) {
                        def.invOffsetX!![i] = data.g2()
                        def.invOffsetY!![i] = data.g2()
                    }
                    def.unknownProp_8 = data.g1() == 1
                }
                ComponentType.UNKNOWN_3 -> {
                    def.unknownProp_8 = data.g1() == 1
                }
                ComponentType.MODEL_VIEWER -> {
                    def.modelType = 1
                    def.modelId = data.g2()
                    if (def.modelId == 65535) def.modelId = -1
                    def.modelXAngle = data.g2()
                    def.modelYAngle = data.g2()
                    def.modelZoom = data.g2()
                    def.modelYOffset = data.g2()
                    def.modelAnimId = data.g2()
                    if (def.modelAnimId == 65535) def.modelAnimId = -1
                    def.activeModelType = 1
                    def.activeModelId = data.g2()
                    if (def.activeModelId == 65535) def.activeModelId = -1
                    def.activeModelAnimId = data.g2()
                    if (def.activeModelAnimId == 65535) def.activeModelAnimId = -1
                    def.unknownModelProp_5 = data.g1() == 1
                    def.modelOrtho = data.g1() == 1
                    def.unknownModelProp_6 = data.g2()
                    def.unknownModelProp_7 = data.g2()
                    def.unknownModelProp_3 = data.g2()
                    def.unknownModelProp_4 = data.g2()
                }
                ComponentType.RECTANGLE -> {
                    def.filled = data.g1() == 1
                }
                ComponentType.TEXT -> {
                    def.shadowed = data.g1() == 1
                }
                ComponentType.SPRITE -> {
                    def.spriteId = data.g2()
                    if (def.spriteId == 65535) def.spriteId = -1
                    def.spriteTiling = data.g1() == 1
                    def.activeSpriteId = data.g2()
                    if (def.activeSpriteId == 65535) def.activeSpriteId = -1
                    def.angle2d = data.g1()
                    def.hasAlpha = data.g1() == 1
                }
                ComponentType.LINE -> {
                    def.lineWidth = data.g1()
                    def.unknownProp_8 = data.g1() == 1
                }
                ComponentType.TEXT_ENGINE -> {
                    def.font = data.g2()
                    if (def.font == 65535) def.font = -1
                    def.text = data.jagString
                    def.vPadding = data.g1()
                    def.halign = data.g1()
                    def.valign = data.g1()
                    def.shadowed = data.g1() == 1
                    def.color = data.g4()
                }
                ComponentType.INVENTORY -> {
                    def.unknownProp_9 = data.g1()
                    def.unknownProp_10 = data.g1()
                    def.dynHeight = 3
                    def.objCounts = IntArray(def.baseWidth * def.baseHeight)
                    def.objTypes = IntArray(def.baseWidth * def.baseHeight)
                    def.dynWidth = 3
                    def.invMarginX = data.g1()
                    def.invMarginY = data.g1()
                    def.invOffsetX = IntArray(20)
                    def.invOffsetY = IntArray(20)
                    def.invSprite = IntArray(20)
                    for (i in 0..19) {
                        def.invOffsetX!![i] = data.g2()
                        def.invOffsetY!![i] = data.g2()
                        def.invSprite!![i] = data.g2()
                    }
                    def.unknownProp_8 = data.g1() == 1
                    def.invOptions = arrayOfNulls(5)
                    for (i in 0..4) {
                        def.invOptions[i] = data.jagString
                    }
                }
                ComponentType.OPTION_BUTTON -> {
                    def.optionBase = data.jagString
                    def.ops = arrayOfNulls(5)
                    for (i in 0..4) {
                        def.ops[i] = data.jagString
                    }
                }

                ComponentType.MODEL -> TODO()
                ComponentType.UNKNOWN_7 -> TODO()
                ComponentType.UNKNOWN_8 -> TODO()
                ComponentType.UNKNOWN_9 -> TODO()
            }
            def.option = data.jagString
            if (def.option == "") {
                if (flags and 268435456 != 0) {
                    def.option = "Ok"
                }
                if (flags and 1073741824 != 0) {
                    def.option = "Select"
                }
                if (flags and -0x80000000 != 0) {
                    def.option = "Continue"
                }
            }
        }

        private fun decodeIf3(def: IfaceDefinition, data: IoBuffer) {
            data.g1()
            def.version = 3
            var type = data.g1()
            if ((type and 0x80) != 0) {
                type = type and 0x7F
                data.jagString
            }
            def.type = ComponentType.values()[type]
            def.clientCode = data.g2()
            def.baseX = data.g2b()
            def.baseY = data.g2b()
            def.baseWidth = data.g2()
            def.baseHeight = data.g2()
            def.dynWidth = data.g1b()
            def.dynHeight = data.g1b()
            def.yMode = data.g1b()
            def.xMode = data.g1b()
            def.overlayer = data.g2()
            if (def.overlayer == 65535) {
                def.overlayer = -1
            } else {
                def.overlayer = (def.id and -0x10000) + def.overlayer
            }
            def.hidden = data.g1() == 1
            parseIf3Type(def, data)

            val unknownLocal_1 = data.g3()
            var unknownLocal_2 = data.g1()
            var unknownLocal_3: Int
            if (unknownLocal_2 != 0) {
                def.unknownIntArray_1 = IntArray(10)
                def.unknownByteArray_1 = ByteArray(10)
                def.unknownByteArray_2 = ByteArray(10)

                while (unknownLocal_2 != 0) {
                    unknownLocal_3 = (unknownLocal_2 shr 4) - 1
                    unknownLocal_2 = data.g1() or (unknownLocal_2 shl 8)
                    unknownLocal_2 = unknownLocal_2 and 0xFFF
                    if (unknownLocal_2 == 4095) {
                        def.unknownIntArray_1!![unknownLocal_3] = -1
                    } else {
                        def.unknownIntArray_1!![unknownLocal_3] = unknownLocal_2
                    }
                    def.unknownByteArray_2!![unknownLocal_3] = data.g1b().toByte()
                    def.unknownByteArray_1!![unknownLocal_3] = data.g1b().toByte()
                    unknownLocal_2 = data.g1()
                }
            }

            def.optionBase = data.jagString
            unknownLocal_2 = data.g1()
            val opCount = unknownLocal_2 and 0xF
            if (opCount > 0) {
                def.ops = arrayOfNulls(opCount)
                for (i in 0 until opCount) {
                    def.ops[i] = data.jagString
                }
            }

            var unknownLocal_5: Int
            val unknownLocal_6 = unknownLocal_2 shr 4
            if (unknownLocal_6 > 0) {
                unknownLocal_5 = data.g1()
                def.unknownIntArray_2 = IntArray(unknownLocal_5 + 1)
                Arrays.fill(def.unknownIntArray_2, -1)
                def.unknownIntArray_2!![unknownLocal_5] = data.g2()
            }
            if (unknownLocal_6 > 1) {
                unknownLocal_5 = data.g1()
                def.unknownIntArray_2!![unknownLocal_5] = data.g2()
            }
            def.dragDeadzone = data.g1()
            def.dragDeadtime = data.g1()
            def.dragRenderBehavior = data.g1() == 1
            unknownLocal_5 = -1
            def.opCircumfix = data.jagString

            if ((unknownLocal_1 shr 11 and 0x7F) != 0) {
                unknownLocal_5 = data.g2()
                def.unknownProp_9 = data.g2()
                if (unknownLocal_5 == 65535) unknownLocal_5 = -1
                if (def.unknownProp_9 == 65535) def.unknownProp_9 = -1
                def.unknownProp_10 = data.g2()
                if (def.unknownProp_10 == 65535) def.unknownProp_10 = -1
            }

            def.scripts = LinkedScripts()
            def.scripts!!.unknown = parseIf3ScriptArgs(data)
            def.scripts!!.onMouseOver = parseIf3ScriptArgs(data)
            def.scripts!!.onMouseLeave = parseIf3ScriptArgs(data)
            def.scripts!!.onUseWith = parseIf3ScriptArgs(data)
            def.scripts!!.onUse = parseIf3ScriptArgs(data)
            def.scripts!!.onVarpTransmit = parseIf3ScriptArgs(data)
            def.scripts!!.onInvTransmit = parseIf3ScriptArgs(data)
            def.scripts!!.onStatTransmit = parseIf3ScriptArgs(data)
            def.scripts!!.onTimer = parseIf3ScriptArgs(data)
            def.scripts!!.onOptionClick = parseIf3ScriptArgs(data)
            def.scripts!!.onMouseRepeat = parseIf3ScriptArgs(data)
            def.scripts!!.onClickRepeat = parseIf3ScriptArgs(data)
            def.scripts!!.onDrag = parseIf3ScriptArgs(data)
            def.scripts!!.onRelease = parseIf3ScriptArgs(data)
            def.scripts!!.onHold = parseIf3ScriptArgs(data)
            def.scripts!!.onDragStart = parseIf3ScriptArgs(data)
            def.scripts!!.onDragRelease = parseIf3ScriptArgs(data)
            def.scripts!!.onScroll = parseIf3ScriptArgs(data)
            def.scripts!!.onVarcTransmit = parseIf3ScriptArgs(data)
            def.scripts!!.onVarcstrTransmit = parseIf3ScriptArgs(data)

            def.triggers = ScriptTriggers()
            def.triggers!!.varpTriggers = parseIf3Triggers(data)!!
            def.triggers!!.inventoryTriggers = parseIf3Triggers(data)!!
            def.triggers!!.statTriggers = parseIf3Triggers(data)!!
            def.triggers!!.varcTriggers = parseIf3Triggers(data)!!
            def.triggers!!.varcstrTriggers = parseIf3Triggers(data)!!
        }

        private fun parseIf3ScriptArgs(data: IoBuffer): ScriptArgs? {
            val argCount = data.g1()
            if (argCount == 0) return null
            val argArray = arrayOfNulls<Any>(argCount)
            for (i in 0 until argCount) {
                val type = data.g1()
                if (type == 0) {
                    val datum = data.g4()
                    argArray[i] = datum
                } else if (type == 1) {
                    val datum = data.jagString
                    argArray[i] = datum
                }
            }
            return ScriptArgs(argArray[0] as Int, Arrays.copyOfRange(argArray, 1, argArray.size))
        }

        private fun parseIf3Triggers(data: IoBuffer): IntArray? {
            val triggerCount = data.g1()
            if (triggerCount == 0) return null
            val triggers = IntArray(triggerCount)
            for (i in 0 until triggerCount) {
                triggers[i] = data.g4()
            }
            return triggers
        }

        private fun parseIf3Type(def: IfaceDefinition, data: IoBuffer) {
            when (def.type) {
                ComponentType.SCROLLABLE -> {
                    def.scrollMaxH = data.g2()
                    def.scrollMaxV = data.g2()
                    def.noClickThrough = data.g1() == 1
                }

                ComponentType.SPRITE -> {
                    def.spriteId = data.g4()
                    def.angle2d = data.g2()
                    val spriteFlags = data.g1()
                    def.hasAlpha = (spriteFlags and 0x2) != 0
                    def.spriteTiling = (spriteFlags and 0x1) != 0
                    def.alpha = data.g1()
                    def.outlineThickness = data.g1()
                    def.shadowColor = data.g4()
                    def.vFlip = data.g1() == 1
                    def.hFlip = data.g1() == 1
                }

                ComponentType.MODEL -> {
                    def.modelType = 1
                    def.modelId = data.g2()
                    if (def.modelId == 65535) def.modelId = -1
                    def.unknownModelProp_1 = data.g2b()
                    def.unknownModelProp_2 = data.g2b()
                    def.modelXAngle = data.g2()
                    def.modelYAngle = data.g2()
                    def.modelYOffset = data.g2()
                    def.modelZoom = data.g2()
                    def.modelAnimId = data.g2()
                    if (def.modelAnimId == 65535) def.modelAnimId = -1
                    def.modelOrtho = data.g1() == 1
                    def.unknownModelProp_3 = data.g2()
                    def.unknownModelProp_4 = data.g2()
                    def.unknownModelProp_5 = data.g1() == 1
                    if (def.dynWidth != 0) def.unknownModelProp_6 = data.g2()
                    if (def.dynHeight != 0) def.unknownModelProp_7 = data.g2()
                }

                ComponentType.TEXT -> {
                    def.font = data.g2()
                    if (def.font == 65535) def.font = -1
                    def.text = data.jagString
                    def.vPadding = data.g1()
                    def.halign = data.g1()
                    def.valign = data.g1()
                    def.shadowed = data.g1() == 1
                    def.color = data.g4()
                }

                ComponentType.UNKNOWN_3 -> {
                    def.color = data.g4()
                    def.filled = data.g1() == 1
                    def.alpha = data.g1()
                }

                ComponentType.UNKNOWN_9 -> {
                    def.lineWidth = data.g1()
                    def.color = data.g4()
                    def.unknownProp_8 = data.g1() == 1
                }

                ComponentType.MODEL_VIEWER -> TODO()
                ComponentType.RECTANGLE -> TODO()
                ComponentType.TEXT_ENGINE -> TODO()
                ComponentType.INVENTORY -> TODO()
                ComponentType.OPTION_BUTTON -> TODO()
                ComponentType.LINE -> TODO()
                ComponentType.UNKNOWN_1 -> TODO()
                ComponentType.UNKNOWN_2 -> TODO()
                ComponentType.UNKNOWN_7 -> TODO()
                ComponentType.UNKNOWN_8 -> TODO()
                null -> TODO()
            }
        }
    }
}
