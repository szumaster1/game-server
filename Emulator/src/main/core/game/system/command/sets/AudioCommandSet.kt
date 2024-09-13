package core.game.system.command.sets

import core.api.playAudio
import core.api.playGlobalAudio
import core.game.node.entity.player.link.music.MusicEntry
import core.game.system.command.Privilege
import core.game.world.map.Location
import core.game.world.repository.Repository
import core.net.packet.PacketRepository
import core.net.packet.context.MusicContext
import core.net.packet.outgoing.MusicPacket
import core.plugin.Initializable

/**
 * Audio command set.
 */
@Initializable
class AudioCommandSet : CommandSet(Privilege.STANDARD) {
    override fun defineCommands() {

        /*
         * Command that lets you play a specific song.
         */

        define(
            "playsong",
            Privilege.STANDARD,
            "::playsong <lt>Song ID<gt>",
            "Plays the song with the given ID."
        ) { player, args ->
            if (args.size < 2) {
                reject(player, "Usage: ::playsong songID")
            }
            val id = args[1].toIntOrNull()
            if (id == null) {
                reject(player, "Please use a valid integer for the song id.")
            }
            player.musicPlayer.play(MusicEntry.forId(id!!))
            notify(player, "Now playing song $id")
        }

        /*
         * Command that lets you play a specific jingle.
         */

        define(
            "playjingle",
            Privilege.STANDARD,
            "::playjingle <lt>jingle ID<gt>",
            "Plays the jingle with the given ID."
        ) { player, args ->
            if (args.size < 2) {
                reject(player, "Usage: ::playjingle jingleID")
            }
            val id = args[1].toIntOrNull()
            if (id == null) {
                reject(player, "Please use a valid integer for the jingle id.")
            }
            PacketRepository.send(MusicPacket::class.java, MusicContext(player, id!!, true))
            notify(player, "Now playing jingle $id")
        }

        /*
           Command that lets you play a song via the ID instead of the song name
           this is mostly useful for custom tracks that aren't in the in-game
           music player yet.
         */

        define("playid") { player, arg ->
            if (arg.size < 2) reject(player, "Needs more args.")
            val id = arg[1].toIntOrNull()
            if (id != null) {
                PacketRepository.send(MusicPacket::class.java, MusicContext(player, id))
                notify(player, "Now playing song $id")
            }
        }

        /*
            Command that unlocks all music tracks for the player.
         */

        define("allmusic", Privilege.ADMIN, "::allmusic", "Unlocks all music tracks.") { player, _ ->
            for (me in MusicEntry.getSongs().values) {
                player.musicPlayer.unlock(me.id)
            }
        }

        /*
            Command that lets you play an audio id
         */

        define(
            "audio",
            Privilege.ADMIN,
            "::audio id <lt>loops[optional]</lt>",
            "Plays the audio with the given ID."
        ) { player, args ->
            if (args.size < 2 || args.size > 3) reject(player, "Usage: ::audio id loops[optional]")
            val id = args[1].toInt()
            val loops = if (args.size > 2) args[2].toInt() else 1
            playAudio(player, id, 0, loops)
        }

        /*
            Command that lets you play an audio id globally by playername or coords.
         */

        define(
            "globalaudio",
            Privilege.ADMIN,
            "::globalaudio id radius[max 15] location[player name or x y z]",
            "Play global audio by id, radius, and location"
        ) { player, args ->
            if (args.size < 3) reject(player, "Usage: ::globalaudio id radius[max 15] location[player name or x y z]")
            val loc = when (args.size) {
                6 -> Location(args[3].toInt(), args[4].toInt(), args[5].toInt())
                4 -> Repository.getPlayerByName(args[3])?.location
                else -> null
            }
            if (loc == null) reject(player, "Invalid player name / location")
            val id = args[1].toInt()
            val radius = if (args[2].toInt() > 15) 15 else args[2].toInt()
            if (loc != null) playGlobalAudio(loc, id, 0, 1, radius)
        }
    }
}
