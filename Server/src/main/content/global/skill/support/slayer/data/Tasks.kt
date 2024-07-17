package content.global.skill.support.slayer.data

import core.api.consts.NPCs
import core.api.hasRequirement
import core.cache.def.impl.NPCDefinition
import core.game.node.entity.player.Player
import java.util.*

enum class Tasks {
    ABERRANT_SPECTRES(
        combatCheck = 65,
        ids = intArrayOf(NPCs.ABERRANT_SPECTRE_1604, NPCs.ABERRANT_SPECTRE_1605, NPCs.ABERRANT_SPECTRE_1606, NPCs.ABERRANT_SPECTRE_1607, NPCs.ABERRANT_SPECTRE_7801, NPCs.ABERRANT_SPECTRE_7802, NPCs.ABERRANT_SPECTRE_7803, NPCs.ABERRANT_SPECTRE_7804),
        info = arrayOf(
            "Aberrant spectres have an extremely potent stench that drains",
            "stats and life points. A nose peg, protects against the stench."
        ),
        levelReq = 60,
        undead = true,
        dragon = false
    ),
    ABYSSAL_DEMONS(
        combatCheck = 85,
        ids = intArrayOf(NPCs.ABYSSAL_DEMON_1615, NPCs.ABYSSAL_DEMON_4230),
        info = arrayOf(
            "Abyssal Demons are nasty creatures to deal with, they aren't really part, ",
            "of this realm, and are able to move very quickly to trap their prey"
        ),
        levelReq = 85,
        undead = false,
        dragon = false
    ),
    ANKOU(
        combatCheck = 40,
        ids = intArrayOf(NPCs.ANKOU_4381, NPCs.ANKOU_4382, NPCs.ANKOU_4383),
        info = arrayOf("Neither skeleton nor ghost, but a combination of both."),
        levelReq = 1,
        undead = true,
        dragon = false
    ),
    AVIANSIES(
        combatCheck = 60,
        ids = intArrayOf(NPCs.AVIANSIE_6245, NPCs.AVIANSIE_6243, NPCs.AVIANSIE_6235, NPCs.AVIANSIE_6232, NPCs.AVIANSIE_6244, NPCs.AVIANSIE_6246, NPCs.AVIANSIE_6233, NPCs.AVIANSIE_6241, NPCs.AVIANSIE_6238, NPCs.AVIANSIE_6237, NPCs.AVIANSIE_6240, NPCs.AVIANSIE_6242, NPCs.AVIANSIE_6239, NPCs.AVIANSIE_6234),
        info = arrayOf("Graceful, bird-like creature."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    BANSHEE(
        combatCheck = 20,
        ids = intArrayOf(NPCs.BANSHEE_1612),
        info = arrayOf(
            "Banshees use a piercing scream to shock their enemies",
            "you'll need some Earmuffs to protect yourself from them."
        ),
        levelReq = 15,
        undead = true,
        dragon = false
    ),
    BASILISKS(
        combatCheck = 40,
        ids = intArrayOf(NPCs.BASILISK_1616, NPCs.BASILISK_1617, NPCs.BASILISK_4228, NPCs.BASILISK_BOSS_7799),
        info = arrayOf("A mirror shield is much necessary when hunting", "these mad creatures."),
        levelReq = 40,
        undead = false,
        dragon = false
    ),
    BATS(
        combatCheck = 5,
        ids = intArrayOf(NPCs.BAT_412, NPCs.GIANT_BAT_78, NPCs.GIANT_BAT_1005, NPCs.GIANT_BAT_2482, NPCs.GIANT_BAT_3711),
        info = arrayOf(
            "These little creatures are incredibly quick.",
            "make sure you keep your eye on them at all times."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    BEARS(
        combatCheck = 13,
        ids = intArrayOf(NPCs.BLACK_BEAR_106, NPCs.GRIZZLY_BEAR_105, NPCs.GRIZZLY_BEAR_1195, NPCs.ANGRY_BEAR_3645, NPCs.ANGRY_BEAR_3664, NPCs.BEAR_CUB_1326, NPCs.BEAR_CUB_1327),
        info = arrayOf("A large animal with a crunching punch."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    BIRDS(
        combatCheck = 1,
        ids = intArrayOf(147, 1018, 1403, 1475, 5120, 5121, 5122, 5123, 5133, 1475, 1476, 41, 951, 1017, 1401, 1402, 2313, 2314, 2315, 3476, 1016, 1550, 1180, 1754, 1755, 1756, 2252, 4570, 4571, 1911, 6114, 46, 2693, 6113, 6112, 146, 149, 150, 450, 451, 1179, 1322, 1323, 1324, 1325, 1400, 2726, 2727, 3197, 138, 48, 4373, 4374, 4535, 139, 1751, 148, 1181, 6382, 2459, 2460, 2461, 2462, 2707, 2708, 6115, 6116, 3296, 6378, 1996, 3675, 3676, 6792, 6946, 7320, 7322, 7324, 7326, 7328, 1692, 6285, 6286, 6287, 6288, 6289, 6290, 6291, 6292, 6293, 6294, 6295, 6322, 6323, 6324, 6325, 6326, 6327, 6328, 6329, 6330, 6331, 6332),
        info = arrayOf(
            "irds aren't the most intelligent of creatures, but watch out for their",
            "sharp, stabbing beaks."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    BLACK_DEMONS(
        combatCheck = 80,
        ids = intArrayOf(84, 677, 4702, 4703, 4704, 4705, 6208),
        info = arrayOf(
            "Black Demons are magic creatures that are weak to magic attacks.",
            "They're the strongest demon and very dangerous."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    BLACK_DRAGONS(
        combatCheck = 80,
        ids = intArrayOf(54, 4673, 4674, 4675, 4676, 3376, 50),
        info = arrayOf(
            "Black dragons are the strongest dragons",
            "watch out for their fiery breath"
        ),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 40 or (80 shl 16)
    ),
    BLOODVELDS(
        combatCheck = 50,
        ids = intArrayOf(1618, 1619, 6215, 7643, 7642),
        info = arrayOf(
            "Bloodvelds are strange demonic creatures, they use their long rasping tongue",
            "to feed on just about anything they can find."
        ),
        levelReq = 50,
        undead = false,
        dragon = false
    ),
    BLUE_DRAGONS(
        combatCheck = 65,
        ids = intArrayOf(55, 4681, 4682, 4683, 4684, 5178, 52, 4665, 4666),
        info = arrayOf(
            "Blue dragons aren't as strong as other dragons but they're still",
            "very powerful, watch out for their fiery breath."
        ),
        levelReq = 1,
        undead = false,
        dragon = true
    ),
    BRINE_RATS(
        combatCheck = 45,
        ids = intArrayOf(3707),
        info = arrayOf(
            "Small little creatures they are, yet so very",
            "powerful."
        ),
        levelReq = 47,
        undead = false,
        dragon = false
    ),
    BRONZE_DRAGONS(
        combatCheck = 75,
        ids = intArrayOf(1590),
        info = arrayOf(
            "Bronze dragons aren't as strong as other dragons but they're still",
            "very powerful, watch out for their fiery breath."
        ),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 30 or (60 shl 16)
    ),
    CATABLEPONS(
        combatCheck = 35,
        ids = intArrayOf(4397, 4398, 4399),
        info = arrayOf(
            "They use the magic spell Weaken to drain up to 15% of their",
            "opponent's maximum Strength level."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    CAVE_BUG(
        combatCheck = 1,
        ids = intArrayOf(1832, 5750),
        info = arrayOf(
            "It regenerates life points quickly and seems to be a good",
            "herblore monster."
        ),
        levelReq = 7,
        undead = false,
        dragon = false
    ),
    CAVE_CRAWLERS(
        combatCheck = 10,
        ids = intArrayOf(1600, 1601, 1602, 1603),
        info = arrayOf("The poisonous parts of them are presumably removed."),
        levelReq = 10,
        undead = false,
        dragon = false
    ),
    CAVE_HORRORS(
        combatCheck = 85,
        ids = intArrayOf(4353, 4354, 4355, 4356, 4357),
        info = arrayOf(
            "A Cave horror wears a creepy mask, it is",
            "preferred to wear a witchwood icon."
        ),
        levelReq = 58,
        questReq = "Cabin Fever"
    ),
    CAVE_SLIMES(
        combatCheck = 15,
        ids = intArrayOf(1831),
        info = arrayOf("These are lesser versions of jellies, watch out they can poison you."),
        levelReq = 17,
        undead = false,
        dragon = false
    ),
    COCKATRICES(
        combatCheck = 25,
        ids = intArrayOf(1620, 1621, 4227),
        info = arrayOf(
            "A Mirror shield is necessary when",
            "fighting these monsters."
        ),
        levelReq = 25,
        undead = false,
        dragon = false
    ),
    COWS(
        combatCheck = 5,
        ids = intArrayOf(1766, 1768, 2310, 81, 397, 955, 1767, 3309),
        info = arrayOf(
            "Cow's may seem stupid, however they know more then",
            "you think. Don't under estimate them."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    CRAWLING_HAND(
        combatCheck = 1,
        ids = intArrayOf(1648, 1649, 1650, 1651, 1652, 1653, 1654, 1655, 1656, 1657, 4226, 7640, 7641),
        info = arrayOf(
            "Crawling Hands are undead severed hands, fast and",
            "dexterous they claw their victims."
        ),
        levelReq = 5,
        undead = true,
        dragon = false
    ),
    CROCODILES(
        combatCheck = 50,
        ids = intArrayOf(1993, 6779),
        info = arrayOf(
            "Crocodiles can be found near water and marshes ",
            "in and near the Kharidian Desert."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    CYCLOPES(
        combatCheck = 25,
        ids = intArrayOf(116, 4291, 4292, 6078, 6079, 6080, 6081, 6269, 6270),
        info = arrayOf(
            "Large one eyed creatures who normally wield a",
            "large mallet."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    DAGANNOTHS(
        combatCheck = 75,
        ids = intArrayOf(1338, 1339, 1340, 1341, 1342, 1343, 1344, 1345, 1346, 1347, 2454, 2455, 2456, 2880, 2881, 2882, 2883, 2887, 2888, 3591),
        info = arrayOf(
            "There are many types of Dagannoth, the most powerful being",
            "the three Dagannoth Kings."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    DARK_BEASTS(
        combatCheck = 90,
        ids = intArrayOf(2783),
        info = arrayOf("A dark beast can attack using magic or melee."),
        levelReq = 90,
        undead = false,
        dragon = false
    ),
    DESERT_LIZARDS(
        combatCheck = 15,
        ids = intArrayOf(2803, 2804, 2805, 2806, 2807),
        info = arrayOf(
            "Desert lizards are big Slayer monsters found",
            "in the Kharidian Desert."
        ),
        levelReq = 22,
        undead = false,
        dragon = false
    ),
    DOG(
        combatCheck = 15,
        ids = intArrayOf(99, 3582, 6374, 1994, 1593, 1594, 3582),
        info = arrayOf(
            "Dogs are much like Wolves, they are",
            "pack creatures which will hunt in groups."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    DUST_DEVILS(
        combatCheck = 70,
        ids = intArrayOf(1624),
        info = arrayOf(
            "Dust Devils use clouds of dust, sand, ash and whatever",
            "else they can inhale to blind and disorientate",
            "their victims."
        ),
        levelReq = 65,
        undead = false,
        dragon = false
    ),
    DWARF(
        combatCheck = 6,
        ids = intArrayOf(118, 120, 121, 382, 3219, 3220, 3221, 3268, 3269, 3270, 3271, 3272, 3273, 3274, 3275, 3294, 3295, 4316, 5880, 5881, 5882, 5883, 5884, 5885, 2130, 2131, 2132, 2133, 3276, 3277, 3278, 3279, 119, 2423),
        info = arrayOf(
            "They are slightly resistant to Magic attacks",
            "and are not recommended for low levels."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    EARTH_WARRIORS(
        combatCheck = 35,
        ids = intArrayOf(124),
        info = arrayOf(
            "An Earth warrior is a monster made of earth",
            " which fights using melee."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ELVES(
        combatCheck = 70,
        ids = intArrayOf(1183, 1184, 2359, 2360, 2361, 2362, 7438, 7439, 7440, 7441),
        info = arrayOf("Elves are agile creatures."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    FIRE_GIANTS(
        combatCheck = 65,
        ids = intArrayOf(110, 1582, 1583, 1584, 1585, 1586, 7003, 7004),
        info = arrayOf(
            "Like other giants, Fire Giants often wield large weapons",
            "learn to recognise what kind of weapon it is, and act accordingly."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    FLESH_CRAWLERS(
        combatCheck = 15,
        ids = intArrayOf(4389, 4390, 4391),
        info = arrayOf(
            "Flesh crawlers are medium level monsters found on",
            "level 2 of the Stronghold of Security."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GARGOYLES(
        combatCheck = 80,
        ids = intArrayOf(1610, 1611, 6389),
        info = arrayOf(
            "Gargoyles are winged creatures of stone. You'll need to fight them to",
            "near death before breaking them apart with a rock hammer."
        ),
        levelReq = 75,
        undead = false,
        dragon = false
    ),
    GHOSTS(
        combatCheck = 13,
        ids = intArrayOf(103, 104, 491, 1541, 1549, 2716, 2931, 4387, 388, 5342, 5343, 5344, 5345, 5346, 5347, 5348, 1698, 5349, 5350, 5351, 5352, 5369, 5370, 5371, 5372, 5373, 5374, 5572, 6094, 6095, 6096, 6097, 6098, 6504, 13645, 13466, 13467, 13468, 13469, 13470, 13471, 13472, 13473, 13474, 13475, 13476, 13477, 13478, 13479, 13480, 13481),
        info = arrayOf(
            "A Ghost is an undead monster that is found",
            "in various places and dungeons. "
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GHOULS(
        combatCheck = 25,
        ids = intArrayOf(1218, 3059),
        info = arrayOf(
            "Ghouls are a humanoid race and the descendants of a long-dead society",
            "that degraded to the point that its people ate their dead."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GOBLINS(
        combatCheck = 1,
        ids = intArrayOf(100, 101, 102, 444, 445, 489, 1769, 1770, 1771, 1772, 1773, 1774, 1775, 1776, 2274, 2275, 2276, 2277, 2278, 2279, 2280, 2281, 2678, 2679, 2680, 2681, 3060, 3264, 3265, 3266, 3267, 3413, 3414, 3415, 3726, 4261, 4262, 4263, 4264, 4265, 4266, 4267, 4268, 4269, 4270, 4271, 4272, 4273, 4274, 4275, 4276, 4407, 4408, 4409, 4410, 4411, 4412, 4479, 4480, 4481, 4482, 4483, 4484, 4485, 4486, 4487, 4488, 4489, 4490, 4491, 4492, 4499, 4633, 4634, 4635, 4636, 4637, 5786, 5824, 5855, 5856, 6125, 6126, 6132, 6133, 6279, 6280, 6281, 6282, 6283, 6402, 6403, 6404, 6405, 6406, 6407, 6408, 6409, 6410, 6411, 6412, 6413, 6414, 6415, 6416, 6417, 6418, 6419, 6420, 6421, 6422, 6423, 6424, 6425, 6426, 6427, 6428, 6429, 6430, 6431, 6432, 6433, 6434, 6435, 6436, 6437, 6438, 6439, 6440, 6441, 6442, 6443, 6444, 6445, 6446, 6447, 6448, 6449, 6450, 6451, 6452, 6453, 6454, 6455, 6456, 6457, 6458, 6459, 6460, 6461, 6462, 6463, 6464, 6465, 6466, 6467, 6490, 6491, 6492, 6493, 6494, 6495, 6496, 6497),
        info = arrayOf(
            "Goblins are mostly just annoying, but they can be vicious.",
            "Watch out for the spears they sometimes carry."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GORAKS(
        combatCheck = 70,
        ids = intArrayOf(4418, 6218),
        info = arrayOf("Goraks can be tough monsters to fight. Be prepared."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GREATER_DEMONS(
        combatCheck = 75,
        ids = intArrayOf(83, 4698, 4699, 4700, 4701, 6204),
        info = arrayOf(
            "Greater Demons are magic creatures so they are weak to magical attacks.",
            "They're the strongest demon and very dangerous."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    GREEN_DRAGONS(
        combatCheck = 52,
        ids = intArrayOf(742, 941, 4677, 4678, 4679, 4680, 5362),
        info = arrayOf("Green dragons are very powerful, they have", "fierce fiery breath."),
        levelReq = 1,
        undead = false,
        dragon = true
    ),
    HARPIE_BUG_SWARMS(
        combatCheck = 45,
        ids = intArrayOf(3153),
        info = arrayOf("Harpie Bug Swarms are insectoid Slayer monsters."),
        levelReq = 33,
        undead = false,
        dragon = false
    ),
    HELLHOUNDS(
        combatCheck = 75,
        ids = intArrayOf(49, 3586, 6210),
        info = arrayOf("Hellhounds are mid to high level demons."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    HILL_GIANTS(
        combatCheck = 25,
        ids = intArrayOf(117, 4689, 4690, 3058, 4691, 4692, 4693),
        info = arrayOf("Hill giants can hit up to 19 damage, and they only attack with Melee."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    HOBGOBLINS(
        combatCheck = 20,
        ids = intArrayOf(122, 123, 2685, 2686, 3061, 6608, 6642, 6661, 6684, 6710, 6722, 6727, 2687, 2688, 3583, 4898, 6275),
        info = arrayOf("Mysterious goblin like creatures."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ICE_FIENDS(
        combatCheck = 20,
        ids = intArrayOf(3406, 6217, 7714, 7715, 7716),
        info = arrayOf("An Icefiend is a monster found on top of Ice Mountain."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ICE_GIANTS(
        combatCheck = 50,
        ids = intArrayOf(111, 3072, 4685, 4686, 4687),
        info = arrayOf(
            "Ice Giants often wield large weapons, learn to recognise",
            "what kind of weapon it is, and act accordingly"
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ICE_WARRIOR(
        combatCheck = 45,
        ids = intArrayOf(125, 145, 3073),
        info = arrayOf("Ice warriors, are cold majestic creatures."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    INFERNAL_MAGES(
        combatCheck = 40,
        ids = intArrayOf(1643, 1644, 1645, 1646, 1647),
        info = arrayOf(
            "Infernal Mages are dangerous spell users, beware of their magic",
            "spells an go properly prepared"
        ),
        levelReq = 45,
        undead = false,
        dragon = false
    ),
    IRON_DRAGONS(
        combatCheck = 80,
        ids = intArrayOf(1591),
        info = arrayOf(
            "Iron dragons aren't as strong as other dragons but they're still",
            "very powerful, watch out for their firey breath."
        ),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 40 or (59 shl 16)
    ),
    JELLIES(
        combatCheck = 57,
        ids = intArrayOf(1637, 1638, 1639, 1640, 1641, 1642),
        info = arrayOf(
            "Jellies are nasty cube-like gelatinous creatures which",
            "absorb everything they come across into themselves."
        ),
        levelReq = 52,
        undead = false,
        dragon = false
    ),
    JUNGLE_HORRORS(
        combatCheck = 65,
        ids = intArrayOf(4348, 4349, 4350, 4351, 4352),
        info = arrayOf(
            "Jungle Horrors can be found all over Mos Le'Harmless.",
            "They are strong and aggressive, so watch out!"
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    KALPHITES(
        combatCheck = 15,
        ids = intArrayOf(1153, 1154, 1155, 1156, 1157, 1159, 1160, 1161),
        info = arrayOf(
            "Kalaphite are large insects which live in great hives under the",
            "desert sands."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    KURASKS(
        combatCheck = 65,
        ids = intArrayOf(1608, 1609, 4229, 7805, 7797),
        info = arrayOf("A kurask is a very quick creature."),
        levelReq = 70,
        undead = false,
        dragon = false
    ),
    LESSER_DEMONS(
        combatCheck = 60,
        ids = intArrayOf(82, 6203, 3064, 4694, 4695, 6206, 3064, 4696, 4697, 6101),
        info = arrayOf(
            "Lesser Demons are magic creatures so they are weak to magical",
            "attacks."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    MOGRES(
        combatCheck = 1,
        ids = intArrayOf(114),
        info = arrayOf("Mogres are large foes often found inhabiting areas close", "to water."),
        levelReq = 32,
        undead = false,
        dragon = false
    ),
    MITHRIL_DRAGONS(
        combatCheck = 60,
        ids = intArrayOf(5363),
        info = arrayOf(
            "Mithril dragons aren't as strong as other dragons but they're still",
            "very powerful, watch out for their firey breath."
        ),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 5 or (9 shl 16)
    ),
    MINOTAURS(
        combatCheck = 7,
        ids = intArrayOf(4404, 4405, 4406),
        info = arrayOf("Minotaurs are large manlike creatures but you'll", "want to be careful of their horns."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    MONKEYS(
        combatCheck = 1,
        ids = intArrayOf(132, 1463, 1464, 2301, 4344, 4363, 6943, 7211, 7213, 7215, 7217, 7219, 7221, 7223, 7225, 7227, 1455, 1459, 1460, 1456, 1457, 1458),
        info = arrayOf("Small agile creatures, watch out they pinch!"),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    MOSS_GIANTS(
        combatCheck = 40,
        ids = intArrayOf(112, 1587, 1588, 1681, 4534, 4688, 4706),
        info = arrayOf("They are known to carry large sticks."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    NECHRYAELS(
        combatCheck = 85,
        ids = intArrayOf(1613),
        info = arrayOf(
            "Nechryael are demons of decay which summon small winged beings which",
            "help them fight their victims."
        ),
        levelReq = 80,
        undead = false,
        dragon = false
    ),
    OGRES(
        combatCheck = 40,
        ids = intArrayOf(115, 374, 2044, 2045, 2046, 2047, 2048, 2049, 2050, 2051, 2052, 2053, 2054, 2055, 2056, 2057, 2801, 3419, 7078, 7079, 7080, 7081, 7082),
        info = arrayOf(
            "Ogres are brutal creatures, favouring large blunt maces and clubs",
            "they often attack without warning."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    OTHERWORDLY_BEING(
        combatCheck = 40,
        ids = intArrayOf(126),
        info = arrayOf("A creature filled with everlasting power."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    PYREFIENDS(
        combatCheck = 25,
        ids = intArrayOf(1633, 1634, 1635, 1636, 6216, 6631, 6641, 6660, 6668, 6683, 6709, 6721),
        info = arrayOf("A scorching hot creature, watch out!"),
        levelReq = 30,
        undead = false,
        dragon = false
    ),
    RATS(
        combatCheck = 1,
        ids = intArrayOf(2682, 2980, 2981, 3007, 88, 224, 4928, 4929, 4936, 4937, 3008, 3009, 3010, 3011, 3012, 3013, 3014, 3015, 3016, 3017, 3018, 4396, 4415, 7202, 7204, 7417, 7461, 87, 446, 950, 4395, 4922, 4923, 4924, 4925, 4926, 4927, 4942, 4943, 4944, 4945, 86, 87, 446, 950, 4395, 4922, 4923, 4924, 4925, 4926, 4927, 4942, 4943, 4944, 4945),
        info = arrayOf("Quick little rodents!"),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ROCK_SLUGS(
        combatCheck = 20,
        ids = intArrayOf(NPCs.ROCKSLUG_1631, NPCs.ROCKSLUG_1632),
        info = arrayOf("A rock slug can leave behind a trail of his presence.."),
        levelReq = 20,
        undead = false,
        dragon = false
    ),
    SCORPIONS(
        combatCheck = 7,
        ids = intArrayOf(107, 1477, 4402, 4403, 144),
        info = arrayOf("A scorpion makes a piercing sound, watch out for", "its long sharp tail."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    SHADE(
        combatCheck = 30,
        ids = intArrayOf(3617, 1250, 1241, 1246, 1248, 1250, 428, 1240),
        info = arrayOf("Shades are dark and mysterious", "they hide in the shadows so be wary of ambushes."),
        levelReq = 1,
        undead = true,
        dragon = false
    ),
    SKELETONS(
        combatCheck = 15,
        ids = intArrayOf(90, 91, 92, 93, 94, 459, 1471, 1575, 1973, 2036, 2037, 2715, 2717, 3065, 3151, 3291, 3581, 3697, 3698, 3699, 3700, 3701, 3702, 3703, 3704, 3705, 3844, 3850, 3851, 4384, 4385, 4386, 5332, 5333, 5334, 5335, 5336, 5337, 5338, 5339, 5340, 5341, 5359, 5365, 5366, 5367, 5368, 5381, 5384, 5385, 5386, 5387, 5388, 5389, 5390, 5391, 5392, 5411, 5412, 5422, 6091, 6092, 6093, 6103, 6104, 6105, 6106, 6107, 6764, 6765, 6766, 6767, 6768, 2050, 2056, 2057, 1539, 7640),
        info = arrayOf("Skeletons are undead monsters found in various locations."),
        levelReq = 1,
        undead = true,
        dragon = false
    ),
    SPIDERS(
        combatCheck = 1,
        ids = intArrayOf(61, 1004, 1221, 1473, 1474, 63, 4401, 2034, 977, 7207, 134, 1009, 59, 60, 4400, 58, 62, 1478, 2491, 2492, 6376, 6377),
        info = arrayOf("Level 24 spiders are aggressive and can hit up to 60 life points."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    SPIRTUAL_MAGES(
        combatCheck = 60,
        ids = intArrayOf(6221, 6231, 6257, 6278),
        info = arrayOf("They are dangerous, they hit with mage."),
        levelReq = 83,
        undead = false,
        dragon = false
    ),
    SPIRTUAL_RANGERS(
        combatCheck = 60,
        ids = intArrayOf(6220, 6230, 6256, 6276),
        info = arrayOf("They are dangerous, they hit with range."),
        levelReq = 63,
        undead = false,
        dragon = false
    ),
    SPIRTUAL_WARRIORS(
        combatCheck = 60,
        ids = intArrayOf(6219, 6229, 6255, 6277),
        info = arrayOf("They are dangerous, they hit with melee."),
        levelReq = 68,
        undead = false,
        dragon = false
    ),
    STEEL_DRAGONS(
        combatCheck = 85,
        ids = intArrayOf(NPCs.STEEL_DRAGON_1592, NPCs.STEEL_DRAGON_3590),
        info = arrayOf(
            "Steel dragons aren't as strong as other dragons but they're still",
            "very powerful, watch out for their fiery breath."
        ),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 10 or (20 shl 16)
    ),
    TERROR_DOGS(
        combatCheck = 65,
        ids = intArrayOf(5417, 5418),
        info = arrayOf(""),
        levelReq = 40,
        undead = false,
        dragon = false
    ),
    TROLLS(
        combatCheck = 60,
        ids = intArrayOf(72, 3584, 1098, 1096, 1097, 1095, 1101, 1105, 1102, 1103, 1104, 1130, 1131, 1132, 1133, 1134, 1106, 1107, 1108, 1109, 1110, 1111, 1112, 1138, 1560, 1561, 1562, 1563, 1564, 1565, 1566, 1935, 1936, 1937, 1938, 1939, 1940, 1941, 1942, 3840, 3841, 3842, 3843, 3845, 1933, 1934, 1115, 1116, 1117, 1118, 1119, 1120, 1121, 1122, 1123, 1124, 391, 392, 393, 394, 395, 396),
        info = arrayOf("Trolls have a crushing attack, it's bets to wear a high crushing defence."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    TUROTHS(
        combatCheck = 60,
        ids = intArrayOf(1626, 1627, 1628, 1629, 1630),
        info = arrayOf("Turoths are Slayer monsters that require a Slayer level of 55 to kill"),
        levelReq = 55,
        undead = false,
        dragon = false
    ),
    TZHAAR(
        combatCheck = 45,
        ids = intArrayOf(2591, 2592, 2593, 2745, 2594, 2595, 2596, 2597, 2604, 2605, 2606, 2607, 2608, 2609, 7755, 7753, 2598, 2599, 2600, 2601, 2610, 2611, 2612, 2613, 2614, 2615, 2616, 2624, 2617, 2618, 2625, 2602, 2603, 7754, 7767, 2610, 2611, 2612, 2613, 2614, 2615, 2616, 2624, 2625, 2627, 2628, 2629, 2630, 2631, 2632, 7746, 7747, 7748, 7749, 7750, 7751, 7752, 7753, 7754, 7755, 7756, 7757, 7758, 7759, 7760, 7761, 7762, 7763, 7764, 7765, 7766, 7767, 7768, 7769, 7770, 7771, 7747, 7747, 7748, 7749, 7750, 7751, 7752, 7753, 7757, 7765, 7769, 7768),
        info = arrayOf("Young Tzhaar's of the century are furious with your kind."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    SUQAHS(
        combatCheck = 65,
        ids = intArrayOf(4527, 4528, 4529, 4530, 4531, 4532, 4533),
        info = arrayOf("Suquah are big, angry folk that inhabit Lunar Isle."),
        levelReq = 1,
        questReq = "Lunar Diplomacy"
    ),
    VAMPIRES(
        combatCheck = 35,
        ids = intArrayOf(1023, 1220, 1223, 1225, 6214),
        info = arrayOf(
            "Vampires are equipped with large fangs,",
            "they can do serious damage."
        ),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    WATERFIENDS(
        combatCheck = 75,
        ids = intArrayOf(NPCs.WATERFIEND_5361),
        info = arrayOf("A waterfiend takes no damage from fire!"),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    WEREWOLFS(
        combatCheck = 60,
        ids = intArrayOf(1665, 6006, 6007, 6008, 6009, 6010, 6011, 6012, 6013, 6014, 6015, 6016, 6017, 6018, 6019, 6020, 6021, 6022, 6023, 6024, 6025, 6212, 6213, 6607, 6609, 6614, 6617, 6625, 6632, 6644, 6663, 6675, 6686, 6701, 6712, 6724, 6728),
        info = arrayOf("There temper is a lot more nasty then a regular wolf!"),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    WOLVES(
        combatCheck = 20,
        ids = intArrayOf(95, 96, 97, 141, 142, 143, 839, 1198, 1330, 1558, 1559, 1951, 1952, 1953, 1954, 1955, 1956, 4413, 4414, 6046, 6047, 6048, 6049, 6050, 6051, 6052, 6829, 6830, 7005),
        info = arrayOf("Wolves are more aggressive then dog's."),
        levelReq = 1,
        undead = false,
        dragon = false
    ),
    ZOMBIES(
        combatCheck = 10,
        ids = intArrayOf(73, 74, 75, 76, 2714, 2863, 2866, 2869, 2878, 3622, 4392, 4393, 4394, 5293, 5294, 5295, 5296, 5297, 5298, 5299, 5300, 5301, 5302, 5303, 5304, 5305, 5306, 5307, 5308, 5309, 5310, 5311, 5312, 5313, 5314, 5315, 5316, 5317, 5318, 5319, 5320, 5321, 5322, 5323, 5324, 5325, 5326, 5327, 5328, 5329, 5330, 5331, 5375, 5376, 5377, 5378, 5379, 5380, 5393, 5394, 5395, 5396, 5397, 5398, 5399, 5400, 5401, 5402, 5403, 5404, 5405, 5406, 5407, 5408, 5409, 5410, 6099, 6100, 6131, 8149, 8150, 8151, 8152, 8153, 8159, 8160, 8161, 8162, 8163, 8164, 2044, 2045, 2046, 2047, 2048, 2049, 2050, 2051, 2052, 2053, 2054, 2055, 7641, 1465, 1466, 1467, 2837, 2838, 2839, 2840, 2841, 2842, 5629, 5630, 5631, 5632, 5633, 5634, 5635, 5636, 5637, 5638, 5639, 5640, 5641, 5642, 5643, 5644, 5645, 5646, 5647, 5648, 5649, 5650, 5651, 5652, 5653, 5654, 5655, 5656, 5657, 5658, 5659, 5660, 5661, 5662, 5663, 5664, 5665, 2843, 2844, 2845, 2846, 2847, 2848),
        info = arrayOf("Zombies are creatures with no brain, they do hit farley", "high though."),
        levelReq = 1,
        undead = true,
        dragon = false
    ),
    JAD(
        combatCheck = 90,
        ids = intArrayOf(NPCs.TZTOK_JAD_2745),
        info = arrayOf("TzTok-Jad is the king of the Fight Caves."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 1 or (1 shl 16)
    ),
    CHAOS_ELEMENTAL(
        combatCheck = 90,
        ids = intArrayOf(NPCs.CHAOS_ELEMENTAL_3200),
        info = arrayOf("The Chaos Elemental is located in the deep Wilderness."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    GIANT_MOLE(
        combatCheck = 75,
        ids = intArrayOf(NPCs.GIANT_MOLE_3340),
        info = arrayOf("Fighting the Giant Mole will require a light source."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    KING_BLACK_DRAGON(
        combatCheck = 75,
        ids = intArrayOf(NPCs.KING_BLACK_DRAGON_50),
        info = arrayOf("The King Black Dragon is located in the deep wilderness."),
        levelReq = 1,
        undead = false,
        dragon = true,
        amtHash = 5 or (25 shl 16)
    ),
    COMMANDER_ZILYANA(
        combatCheck = 90,
        ids = intArrayOf(NPCs.COMMANDER_ZILYANA_6247),
        info = arrayOf("Commander Zilyana is one of the four Godwars bosses."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    GENERAL_GRAARDOR(
        combatCheck = 90,
        ids = intArrayOf(NPCs.GENERAL_GRAARDOR_6260),
        info = arrayOf("General Graardor is one of the four Godwars bosses."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    KRIL_TSUTSAROTH(
        combatCheck = 90,
        ids = intArrayOf(NPCs.KRIL_TSUTSAROTH_6203),
        info = arrayOf("Kril Tsutsaroth is one of the four Godwars bosses."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    KREE_ARRA(
        combatCheck = 90,
        ids = intArrayOf(NPCs.KREEARRA_6222),
        info = arrayOf("Kree'arra is one of the four Godwars bosses."),
        levelReq = 1,
        undead = false,
        dragon = false,
        amtHash = 5 or (25 shl 16)
    ),
    SKELETAL_WYVERN(
        combatCheck = 70,
        ids = intArrayOf(NPCs.SKELETAL_WYVERN_3068, NPCs.SKELETAL_WYVERN_3069, NPCs.SKELETAL_WYVERN_3070, NPCs.SKELETAL_WYVERN_3071),
        info = arrayOf("A skeletal wyvern requires an elemental, mirror", "or dragonfire shield."),
        levelReq = 72,
        undead = false,
        dragon = false,
        amtHash = 24 or (39 shl 16)
    );

    @JvmField
    val levelReq: Int
    val combatCheck: Int
    val tip: Array<String>
    val npcs: IntArray
    @JvmField
    var undead: Boolean = false
    var dragon: Boolean = false
    var amtHash: Int = 0
    var questReq: String = ""

    constructor(combatCheck: Int, ids: IntArray, info: Array<String>, levelReq: Int, undead: Boolean, dragon: Boolean) {
        this.levelReq = levelReq
        this.npcs = ids
        this.tip = info
        this.undead = undead
        this.dragon = dragon
        this.combatCheck = combatCheck
    }

    constructor(combatCheck: Int, ids: IntArray, info: Array<String>, levelReq: Int, undead: Boolean, dragon: Boolean, amtHash: Int) {
        this.levelReq = levelReq
        this.npcs = ids
        this.tip = info
        this.undead = undead
        this.dragon = dragon
        this.amtHash = amtHash
        this.combatCheck = combatCheck
    }

    constructor(combatCheck: Int, ids: IntArray, info: Array<String>, levelReq: Int, questReq: String) {
        this.combatCheck = combatCheck
        this.npcs = ids
        this.tip = info
        this.levelReq = levelReq
        this.questReq = questReq
    }

    fun hasQuestRequirements(player: Player?): Boolean {
        return questReq == "" || hasRequirement(player!!, questReq, false)
    }

    fun getName(): String {
        val npcId = npcs.firstOrNull() ?: 0
        return NPCDefinition.forId(npcId).name
    }

    companion object {
        val taskMap: HashMap<Int, Tasks> = HashMap()

        init {
            Arrays.stream(values()).forEach { entry: Tasks ->
                Arrays.stream(entry.npcs).forEach { id: Int -> taskMap.putIfAbsent(id, entry) }
            }
        }

        @JvmStatic
        fun forId(id: Int): Tasks? {
            return taskMap[id]
        }
    }
}
