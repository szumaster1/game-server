package core.game.world.update.flag

import core.game.node.entity.Entity
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.game.world.update.flag.context.HitMark
import core.net.packet.IoBuffer
import kotlin.reflect.typeOf

/**
 * NPC flags530.
 */
sealed class NPCFlags530(p: Int, o: Int, f: EntityFlag) : EFlagProvider(530, EFlagType.NPC, p, o, f) {
    /**
     * Primary hit.
     */
    class PrimaryHit : NPCFlags530(0x40, 0, EntityFlag.PrimaryHit) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is HitMark) {
                logInvalidType(context, typeOf<HitMark>())
                return
            }
            buffer.p1(context.damage)
            buffer.p1neg(context.type)

            val e = context.entity
            var ratio = 255
            val max = e.skills.maximumLifepoints
            if (e.skills.lifepoints < max)
                ratio = e.skills.lifepoints * 255 / max
            buffer.p1sub(ratio)
        }
    }

    /**
     * Secondary hit.
     */
    class SecondaryHit : NPCFlags530(0x2, 1, EntityFlag.SecondaryHit) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is HitMark) {
                logInvalidType(context, typeOf<HitMark>())
                return
            }
            buffer.p1neg(context.damage)
            buffer.p1sub(context.type)
        }
    }

    /**
     * Animate.
     */
    class Animate : NPCFlags530(0x10, 2, EntityFlag.Animate) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is Animation) {
                logInvalidType(context, typeOf<Animation>())
                return
            }
            buffer.p2(context.id)
            buffer.p1(context.delay)
        }
    }

    /**
     * Face entity.
     */
    class FaceEntity : NPCFlags530(0x4, 3, EntityFlag.FaceEntity) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is Entity?) {
                logInvalidType(context, typeOf<Entity>())
                return
            }
            if (context == null) {
                buffer.p2add(-1)
            } else {
                buffer.p2add(context.clientIndex)
            }
        }
    }

    /**
     * Spot anim.
     */
    class SpotAnim : NPCFlags530(0x80, 4, EntityFlag.SpotAnim) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is Graphic) {
                logInvalidType(context, typeOf<Graphic>())
                return
            }
            buffer.p2add(context.id)
            buffer.ip4((context.height shl 16) + context.delay)
        }
    }

    /**
     * Type swap.
     */
    class TypeSwap : NPCFlags530(0x1, 5, EntityFlag.TypeSwap) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is Int) {
                logInvalidType(context, typeOf<Graphic>())
                return
            }
            buffer.ip2(context)
        }
    }

    /**
     * Force chat.
     */
    class ForceChat : NPCFlags530(0x20, 6, EntityFlag.ForceChat) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is String) {
                logInvalidType(context, typeOf<String>())
                return
            }
            buffer.putString(context)
        }
    }

    /**
     * Animation sequence.
     */
    class AnimationSequence : NPCFlags530(0x100, 7, EntityFlag.AnimSeq) {
        //TODO
    }

    /**
     * Face location.
     */
    class FaceLocation : NPCFlags530(0x200, 8, EntityFlag.FaceLocation) {
        override fun writeTo(buffer: IoBuffer, context: Any?) {
            if (context !is Location) {
                logInvalidType(context, typeOf<Location>())
                return
            }
            buffer.p2add((context.x shl 1) + 1)
            buffer.p2((context.y shl 1) + 1)
        }
    }
}
