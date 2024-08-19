package core.game.bots

import core.game.node.entity.player.Player
import core.game.node.entity.player.link.appearance.Gender
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.tools.RandomFunction
import java.util.*

/**
 * Used for "building" artificial intelligent players.
 * @author Emperor
 */
class AIPBuilder {
    private val ITEMS: List<Item> = ArrayList(
        Arrays.asList(
            Item(286),  //Orange goblin mail

            Item(287),  //Blue goblin mail

            Item(288),  //Goblin mail

            Item(426),  //Priest gown

            Item(428),  //Priest gown

            Item(466),  //Pickaxe handle

            Item(492),  //Picture

            Item(542),  //Monk's robe

            Item(544),  //Monk's robe

            Item(546),  //Shade robe

            Item(548),  //Shade robe

            Item(552),  //Ghostspeak amulet

            Item(577),  //Wizard robe

            Item(579),  //Wizard hat

            Item(581),  //Black robe

            Item(599),  //Picture

            Item(667),  //Blurite sword

            Item(713),  //Picture

            Item(762),  //Picture

            Item(764),  //Picture

            Item(766),  //Picture

            Item(767),  //Phoenix crossbow

            Item(770),  //Picture

            Item(837),  //Crossbow

            Item(839),  //Longbow

            Item(841),  //Shortbow

            Item(843),  //Oak shortbow

            Item(845),  //Oak longbow

            Item(847),  //Willow longbow

            Item(849),  //Willow shortbow

            Item(851),  //Maple longbow

            Item(853),  //Maple shortbow

            Item(877),  //Bronze bolts

            Item(882),  //Bronze arrow

            Item(884),  //Iron arrow

            Item(886),  //Steel arrow

            Item(888),  //Mithril arrow

            Item(890),  //Adamant arrow

            Item(1005),  //White apron

            Item(1007),  //Cape

            Item(1009),  //Brass necklace

            Item(1011),  //Blue skirt

            Item(1013),  //Pink skirt

            Item(1015),  //Black skirt

            Item(1017),  //Wizard hat

            Item(1019),  //Cape

            Item(1021),  //Cape

            Item(1023),  //Cape

            Item(1027),  //Cape

            Item(1029),  //Cape

            Item(1031),  //Cape

            Item(1037),  //Bunny ears

            Item(1038),  //Red partyhat

            Item(1040),  //Yellow partyhat

            Item(1042),  //Blue partyhat

            Item(1044),  //Green partyhat

            Item(1046),  //Purple partyhat

            Item(1048),  //White partyhat

            Item(1050),  //Santa hat

            Item(1053),  //Green h'ween mask

            Item(1055),  //Blue h'ween mask

            Item(1057),  //Red h'ween mask

            Item(1059),  //Leather gloves

            Item(1061),  //Leather boots

            Item(1063),  //Leather vambraces

            Item(1065),  //Green d'hide vamb

            Item(1067),  //Iron platelegs

            Item(1069),  //Steel platelegs

            Item(1071),  //Mithril platelegs

            Item(1073),  //Adamant platelegs

            Item(1075),  //Bronze platelegs

            Item(1077),  //Black platelegs

            Item(1079),  //Rune platelegs

            Item(1081),  //Iron plateskirt

            Item(1083),  //Steel plateskirt

            Item(1085),  //Mithril plateskirt

            Item(1087),  //Bronze plateskirt

            Item(1089),  //Black plateskirt

            Item(1091),  //Adamant plateskirt

            Item(1093),  //Rune plateskirt

            Item(1095),  //Leather chaps

            Item(1097),  //Studded chaps

            Item(1099),  //Green d'hide chaps

            Item(1101),  //Iron chainbody

            Item(1103),  //Bronze chainbody

            Item(1105),  //Steel chainbody

            Item(1107),  //Black chainbody

            Item(1109),  //Mithril chainbody

            Item(1111),  //Adamant chainbody

            Item(1113),  //Rune chainbody

            Item(1115),  //Iron platebody

            Item(1117),  //Bronze platebody

            Item(1119),  //Steel platebody

            Item(1121),  //Mithril platebody

            Item(1123),  //Adamant platebody

            Item(1125),  //Black platebody

            Item(1127),  //Rune platebody

            Item(1129),  //Leather body

            Item(1131),  //Hardleather body

            Item(1133),  //Studded body

            Item(1135),  //Green d'hide body

            Item(1137),  //Iron med helm

            Item(1139),  //Bronze med helm

            Item(1141),  //Steel med helm

            Item(1143),  //Mithril med helm

            Item(1145),  //Adamant med helm

            Item(1147),  //Rune med helm

            Item(1151),  //Black med helm

            Item(1153),  //Iron full helm

            Item(1155),  //Bronze full helm

            Item(1157),  //Steel full helm

            Item(1159),  //Mithril full helm

            Item(1161),  //Adamant full helm

            Item(1163),  //Rune full helm

            Item(1165),  //Black full helm

            Item(1167),  //Leather cowl

            Item(1169),  //Coif

            Item(1171),  //Wooden shield

            Item(1173),  //Bronze sq shield

            Item(1175),  //Iron sq shield

            Item(1177),  //Steel sq shield

            Item(1179),  //Black sq shield

            Item(1181),  //Mithril sq shield

            Item(1183),  //Adamant sq shield

            Item(1185),  //Rune sq shield

            Item(1189),  //Bronze kiteshield

            Item(1191),  //Iron kiteshield

            Item(1193),  //Steel kiteshield

            Item(1195),  //Black kiteshield

            Item(1197),  //Mithril kiteshield

            Item(1199),  //Adamant kiteshield

            Item(1201),  //Rune kiteshield

            Item(1203),  //Iron dagger

            Item(1205),  //Bronze dagger

            Item(1207),  //Steel dagger

            Item(1209),  //Mithril dagger

            Item(1211),  //Adamant dagger

            Item(1213),  //Rune dagger

            Item(1217),  //Black dagger

            Item(1265),  //Bronze pickaxe

            Item(1267),  //Iron pickaxe

            Item(1269),  //Steel pickaxe

            Item(1271),  //Adamant pickaxe

            Item(1273),  //Mithril pickaxe

            Item(1275),  //Rune pickaxe

            Item(1277),  //Bronze sword

            Item(1279),  //Iron sword

            Item(1281),  //Steel sword

            Item(1283),  //Black sword

            Item(1285),  //Mithril sword

            Item(1287),  //Adamant sword

            Item(1289),  //Rune sword

            Item(1291),  //Bronze longsword

            Item(1293),  //Iron longsword

            Item(1295),  //Steel longsword

            Item(1297),  //Black longsword

            Item(1299),  //Mithril longsword

            Item(1301),  //Adamant longsword

            Item(1303),  //Rune longsword

            Item(1307),  //Bronze 2h sword

            Item(1309),  //Iron 2h sword

            Item(1311),  //Steel 2h sword

            Item(1313),  //Black 2h sword

            Item(1315),  //Mithril 2h sword

            Item(1317),  //Adamant 2h sword

            Item(1319),  //Rune 2h sword

            Item(1321),  //Bronze scimitar

            Item(1323),  //Iron scimitar

            Item(1325),  //Steel scimitar

            Item(1327),  //Black scimitar

            Item(1329),  //Mithril scimitar

            Item(1331),  //Adamant scimitar

            Item(1333),  //Rune scimitar

            Item(1335),  //Iron warhammer

            Item(1337),  //Bronze warhammer

            Item(1339),  //Steel warhammer

            Item(1341),  //Black warhammer

            Item(1343),  //Mithril warhammer

            Item(1345),  //Adamant warhammer

            Item(1347),  //Rune warhammer

            Item(1349),  //Iron axe

            Item(1351),  //Bronze axe

            Item(1353),  //Steel axe

            Item(1355),  //Mithril axe

            Item(1357),  //Adamant axe

            Item(1359),  //Rune axe

            Item(1361),  //Black axe

            Item(1363),  //Iron battleaxe

            Item(1365),  //Steel battleaxe

            Item(1367),  //Black battleaxe

            Item(1369),  //Mithril battleaxe

            Item(1371),  //Adamant battleaxe

            Item(1373),  //Rune battleaxe

            Item(1375),  //Bronze battleaxe

            Item(1379),  //Staff

            Item(1381),  //Staff of air

            Item(1383),  //Staff of water

            Item(1385),  //Staff of earth

            Item(1387),  //Staff of fire

            Item(1389),  //Magic staff

            Item(1419),  //Scythe

            Item(1420),  //Iron mace

            Item(1422),  //Bronze mace

            Item(1424),  //Steel mace

            Item(1426),  //Black mace

            Item(1428),  //Mithril mace

            Item(1430),  //Adamant mace

            Item(1432),  //Rune mace

            Item(1478),  //Amulet of accuracy

            Item(1540),  //Anti-dragon shield

            Item(1589),  //Picture

            Item(1635),  //Gold ring

            Item(1637),  //Sapphire ring

            Item(1639),  //Emerald ring

            Item(1641),  //Ruby ring

            Item(1643),  //Diamond ring

            Item(1654),  //Gold necklace

            Item(1656),  //Sapphire necklace

            Item(1658),  //Emerald necklace

            Item(1660),  //Ruby necklace

            Item(1662),  //Diamond necklace

            Item(1692),  //Gold amulet

            Item(1694),  //Sapphire amulet

            Item(1696),  //Emerald amulet

            Item(1698),  //Ruby amulet

            Item(1700),  //Diamond amulet

            Item(1716),  //Unblessed symbol

            Item(1718),  //Holy symbol

            Item(1725),  //Amulet of strength

            Item(1727),  //Amulet of magic

            Item(1729),  //Amulet of defence

            Item(1731),  //Amulet of power

            Item(1757),  //Brown apron

            Item(1949),  //Chef's hat

            Item(2402),  //Silverlight

            Item(2420),  //Picture

            Item(2422),  //Blue partyhat

            Item(2425),  //Picture

            Item(2480),  //Picture

            Item(2512),  //Picture

            Item(2513),  //Dragon chainbody

            Item(2583),  //Black platebody (t)

            Item(2585),  //Black platelegs (t)

            Item(2587),  //Black full helm(t)

            Item(2589),  //Black kiteshield (t)

            Item(2591),  //Black platebody (g)

            Item(2593),  //Black platelegs (g)

            Item(2595),  //Black full helm(g)

            Item(2597),  //Black kiteshield (g)

            Item(2599),  //Adam platebody (t)

            Item(2601),  //Adam platelegs (t)

            Item(2603),  //Adam kiteshield (t)

            Item(2605),  //Adam full helm(t)

            Item(2607),  //Adam platebody (g)

            Item(2609),  //Adam platelegs (g)

            Item(2611),  //Adam kiteshield (g)

            Item(2613),  //Adam full helm(g)

            Item(2615),  //Rune platebody (g)

            Item(2617),  //Rune platelegs (g)

            Item(2619),  //Rune full helm(g)

            Item(2621),  //Rune kiteshield (g)

            Item(2623),  //Rune platebody (t)

            Item(2625),  //Rune platelegs (t)

            Item(2627),  //Rune full helm (t)

            Item(2629),  //Rune kiteshield (t)

            Item(2653),  //Zamorak platebody

            Item(2655),  //Zamorak platelegs

            Item(2657),  //Zamorak full helm

            Item(2659),  //Zamorak kiteshield

            Item(2661),  //Saradomin platebody

            Item(2663),  //Saradomin platelegs

            Item(2665),  //Saradomin full helm

            Item(2667),  //Saradomin kiteshield

            Item(2669),  //Guthix platebody

            Item(2671),  //Guthix platelegs

            Item(2673),  //Guthix full helm

            Item(2675),  //Guthix kiteshield

            Item(2902),  //Gloves

            Item(2912),  //Gloves

            Item(2922),  //Gloves

            Item(2932),  //Gloves

            Item(2942),  //Gloves

            Item(3057),  //Mime mask

            Item(3058),  //Mime top

            Item(3059),  //Mime legs

            Item(3060),  //Mime gloves

            Item(3061),  //Mime boots

            Item(3472),  //Black plateskirt (t)

            Item(3473),  //Black plateskirt (g)

            Item(3474),  //Adam plateskirt (t)

            Item(3475),  //Adam plateskirt (g)

            Item(3476),  //Rune plateskirt (g)

            Item(3477),  //Rune plateskirt (t)

            Item(3478),  //Zamorak plateskirt

            Item(3479),  //Saradomin plateskirt

            Item(3480),  //Guthix plateskirt

            Item(3667),  //Picture

            Item(4000),  //Picture

            Item(4076),  //Picture

            Item(4178),  //Abyssal whip

            Item(4180),  //Dragon platelegs

            Item(4315),  //Team-1 cape

            Item(4317),  //Team-2 cape

            Item(4319),  //Team-3 cape

            Item(4321),  //Team-4 cape

            Item(4323),  //Team-5 cape

            Item(4325),  //Team-6 cape

            Item(4327),  //Team-7 cape

            Item(4329),  //Team-8 cape

            Item(4331),  //Team-9 cape

            Item(4333),  //Team-10 cape

            Item(4335),  //Team-11 cape

            Item(4337),  //Team-12 cape

            Item(4339),  //Team-13 cape

            Item(4341),  //Team-14 cape

            Item(4343),  //Team-15 cape

            Item(4345),  //Team-16 cape

            Item(4347),  //Team-17 cape

            Item(4349),  //Team-18 cape

            Item(4351),  //Team-19 cape

            Item(4353),  //Team-20 cape

            Item(4355),  //Team-21 cape

            Item(4357),  //Team-22 cape

            Item(4359),  //Team-23 cape

            Item(4361),  //Team-24 cape

            Item(4363),  //Team-25 cape

            Item(4365),  //Team-26 cape

            Item(4367),  //Team-27 cape

            Item(4369),  //Team-28 cape

            Item(4371),  //Team-29 cape

            Item(4373),  //Team-30 cape

            Item(4375),  //Team-31 cape

            Item(4377),  //Team-32 cape

            Item(4379),  //Team-33 cape

            Item(4381),  //Team-34 cape

            Item(4383),  //Team-35 cape

            Item(4385),  //Team-36 cape

            Item(4387),  //Team-37 cape

            Item(4389),  //Team-38 cape

            Item(4391),  //Team-39 cape

            Item(4393),  //Team-40 cape

            Item(4395),  //Team-41 cape

            Item(4397),  //Team-42 cape

            Item(4399),  //Team-43 cape

            Item(4401),  //Team-44 cape

            Item(4403),  //Team-45 cape

            Item(4405),  //Team-46 cape

            Item(4407),  //Team-47 cape

            Item(4409),  //Team-48 cape

            Item(4411),  //Team-49 cape

            Item(4413),  //Team-50 cape

            Item(4565),  //Basket of eggs

            Item(4566),  //Rubber chicken

            Item(5525),  //Tiara

            Item(5527),  //Air tiara

            Item(5529),  //Mind tiara

            Item(5531),  //Water tiara

            Item(5533),  //Body tiara

            Item(5535),  //Earth tiara

            Item(5537),  //Fire tiara

            Item(6180),  //Lederhosen top

            Item(6181),  //Lederhosen shorts

            Item(6182),  //Lederhosen hat

            Item(6184),  //Prince tunic

            Item(6185),  //Prince leggings

            Item(6186),  //Princess blouse

            Item(6187),  //Princess skirt

            Item(6188),  //Frog mask

            Item(6201),  //Picture

            Item(6203),  //Picture

            Item(6205),  //Picture

            Item(6207),  //Picture

            Item(6208),  //Man speak amulet

            Item(6210),  //Picture

            Item(6381),  //Picture

            Item(6654),  //Camo top

            Item(6655),  //Camo bottoms

            Item(6656),  //Camo helmet

            Item(6659),  //Camo helmet

            Item(6856),  //Bobble hat

            Item(6857),  //Bobble scarf

            Item(6858),  //Jester hat

            Item(6859),  //Jester scarf

            Item(6860),  //Tri-jester hat

            Item(6861),  //Tri-jester scarf

            Item(6862),  //Woolly hat

            Item(6863),  //Woolly scarf

            Item(6864),  //Marionette handle

            Item(6967),  //Dragon med helm

            Item(7332),  //Black shield(h1)

            Item(7334),  //Adamant shield(h1)

            Item(7336),  //Rune shield(h1)

            Item(7338),  //Black shield(h2)

            Item(7340),  //Adamant shield(h2)

            Item(7342),  //Rune shield(h2)

            Item(7344),  //Black shield(h3)

            Item(7346),  //Adamant shield(h3)

            Item(7348),  //Rune shield(h3)

            Item(7350),  //Black shield(h4)

            Item(7352),  //Adamant shield(h4)

            Item(7354),  //Rune shield(h4)

            Item(7356),  //Black shield(h5)

            Item(7358),  //Adamant shield(h5)

            Item(7360),  //Rune shield(h5)

            Item(7362),  //Studded body (g)

            Item(7364),  //Studded body (t)

            Item(7366),  //Studded chaps (g)

            Item(7368),  //Studded chaps (t)

            Item(7370),  //D'hide body(g)

            Item(7372),  //D'hide body (t)

            Item(7378),  //D'hide chaps (g)

            Item(7380),  //D'hide chaps (t)

            Item(7386),  //Blue skirt (g)

            Item(7388),  //Blue skirt (t)

            Item(7390),  //Wizard robe (g)

            Item(7392),  //Wizard robe (t)

            Item(7394),  //Wizard hat (g)

            Item(7396),  //Wizard hat (t)

            Item(7414),  //Paddle

            Item(7592),  //Zombie shirt

            Item(7593),  //Zombie trousers

            Item(7594),  //Zombie mask

            Item(7595),  //Zombie gloves

            Item(7596),  //Zombie boots

            Item(7672),  //Picture

            Item(7674),  //Perfect

            Item(7803),  //Yin yang amulet

            Item(7927),  //Easter ring

            Item(9005),  //Fancy boots

            Item(9006),  //Fighting boots

            Item(9013),  //Skull sceptre

            Item(9054),  //Red goblin mail

            Item(9056),  //Yellow goblin mail

            Item(9057),  //Green goblin mail

            Item(9058),  //Purple goblin mail

            Item(9059),  //Pink goblin mail

            Item(9665),  //Torch

            Item(9702),  //Stick

            Item(9703),  //Training sword

            Item(9704),  //Training shield

            Item(9705),  //Training bow

            Item(9706),  //Training arrows

            Item(9906),  //Ghost buster 500

            Item(9907),  //Ghost buster 500

            Item(9908),  //Ghost buster 500

            Item(9909),  //Ghost buster 500

            Item(9910),  //Ghost buster 500

            Item(9911),  //Ghost buster 500

            Item(9920),  //Jack lantern mask

            Item(9921),  //Skeleton boots

            Item(9922),  //Skeleton gloves

            Item(9923),  //Skeleton leggings

            Item(9924),  //Skeleton shirt

            Item(9925),  //Skeleton mask

            Item(10280),  //Willow comp bow

            Item(10286),  //Rune helm (h1)

            Item(10288),  //Rune helm (h2)

            Item(10290),  //Rune helm (h3)

            Item(10292),  //Rune helm (h4)

            Item(10294),  //Rune helm (h5)

            Item(10296),  //Adamant helm (h1)

            Item(10298),  //Adamant helm (h2)

            Item(10300),  //Adamant helm (h3)

            Item(10302),  //Adamant helm (h4)

            Item(10304),  //Adamant helm (h5)

            Item(10306),  //Black helm (h1)

            Item(10308),  //Black helm (h2)

            Item(10310),  //Black helm (h3)

            Item(10312),  //Black helm (h4)

            Item(10314),  //Black helm (h5)

            Item(10364),  //Strength amulet(t)

            Item(10366),  //Amulet of magic(t)

            Item(10501),  //Snowball

            Item(10507),  //Reindeer hat

            Item(10567),  //Picture

            Item(10569),  //Picture

            Item(10571),  //Picture

            Item(10573),  //Picture

            Item(10575),  //Picture

            Item(10577),  //Picture

            Item(10579),  //Picture

            Item(10629),  //Mime mask

            Item(10630),  //Princess blouse

            Item(10631),  //Zombie shirt

            Item(10632),  //Camo top

            Item(10633),  //Lederhosen top

            Item(10634),  //Shade robe

            Item(10665),  //Black shield(h1)

            Item(10666),  //Adamant shield(h1)

            Item(10667),  //Rune shield(h1)

            Item(10668),  //Black shield(h2)

            Item(10669),  //Adamant shield(h2)

            Item(10670),  //Rune shield(h2)

            Item(10671),  //Black shield(h3)

            Item(10672),  //Adamant shield(h3)

            Item(10673),  //Rune shield(h3)

            Item(10674),  //Black shield(h4)

            Item(10675),  //Adamant shield(h4)

            Item(10676),  //Rune shield(h4)

            Item(10677),  //Black shield(h5)

            Item(10678),  //Adamant shield(h5)

            Item(10679),  //Rune shield(h5)

            Item(10680),  //Studded body (g)

            Item(10681),  //Studded body (t)

            Item(10682),  //D'hide body(g)

            Item(10683),  //D'hide body (t)

            Item(10686),  //Wizard robe (g)

            Item(10687),  //Wizard robe (t)

            Item(10690),  //Black platebody (t)

            Item(10691),  //Black platebody (g)

            Item(10697),  //Adam platebody (t)

            Item(10698),  //Adam platebody (g)

            Item(10699),  //Black helm (h1)

            Item(10700),  //Black helm (h2)

            Item(10701),  //Black helm (h3)

            Item(10702),  //Black helm (h4)

            Item(10703),  //Black helm (h5)

            Item(10704),  //Rune helm (h1)

            Item(10705),  //Rune helm (h2)

            Item(10706),  //Rune helm (h3)

            Item(10707),  //Rune helm (h4)

            Item(10708),  //Rune helm (h5)

            Item(10709),  //Adamant helm (h1)

            Item(10710),  //Adamant helm (h2)

            Item(10711),  //Adamant helm (h3)

            Item(10712),  //Adamant helm (h4)

            Item(10713),  //Adamant helm (h5)

            Item(10736),  //Strength amulet(t)

            Item(10738),  //Amulet of magic(t)

            Item(10776),  //Zamorak platebody

            Item(10778),  //Saradomin plate

            Item(10780),  //Guthix platebody

            Item(10798),  //Rune platebody (g)

            Item(10800),  //Rune platebody (t)

            Item(11019),  //Chicken feet

            Item(11020),  //Chicken wings

            Item(11021),  //Chicken head

            Item(11022),  //Chicken legs

            Item(11165),  //Phoenix crossbow

            Item(11167),  //Phoenix crossbow

            Item(11789),  //Grim reaper hood

            Item(11790),  //Grim reaper hood

            Item(11951),  //Snowball

            Item(12629),  //Safety gloves

            Item(12634),  //Chocatrice cape

            Item(12645),  //Chocatrice cape

            Item(12844),  //Toy kite

            Item(12845),  //Stone of power

            Item(12846),  //Stone of power

            Item(12847),  //Stone of power

            Item(12848),  //Stone of power

            Item(12849),  //Stone of power

            Item(12860),  //Swordfish gloves

            Item(12863),  //Air runecrafting gloves

            Item(12864),  //Water runecrafting gloves

            Item(12865),  //Earth runecrafting gloves

            Item(12887),  //Druidic mage hood 100

            Item(12888),  //Druidic mage hood 80

            Item(12889),  //Druidic mage hood 60

            Item(12890),  //Druidic mage hood 40

            Item(12891),  //Druidic mage hood 20

            Item(12892),  //Druidic mage hood 0

            Item(12894),  //Druidic mage top 100

            Item(12895),  //Druidic mage top 80

            Item(12896),  //Druidic mage top 60

            Item(12897),  //Druidic mage top 40

            Item(12898),  //Druidic mage top 20

            Item(12899),  //Druidic mage top 0

            Item(12901),  //Druidic mage bottom 100

            Item(12902),  //Druidic mage bottom 80

            Item(12903),  //Druidic mage bottom 60

            Item(12904),  //Druidic mage bottom 40

            Item(12905),  //Druidic mage bottom 20

            Item(12906),  //Druidic mage bottom 0

            Item(12908),  //Adamant spikeshield 100

            Item(12909),  //Adamant spikeshield 80

            Item(12910),  //Adamant spikeshield 60

            Item(12911),  //Adamant spikeshield 40

            Item(12912),  //Adamant spikeshield 20

            Item(12913),  //Adamant spikeshield 0

            Item(12915),  //Adamant berserker shield 100

            Item(12916),  //Adamant berserker shield 80

            Item(12917),  //Adamant berserker shield 60

            Item(12918),  //Adamant berserker shield 40

            Item(12919),  //Adamant berserker shield 20

            Item(12920),  //Adamant berserker shield 0

            Item(12922),  //Rune spikeshield 100

            Item(12923),  //Rune spikeshield 80

            Item(12924),  //Rune spikeshield 60

            Item(12925),  //Rune spikeshield 40

            Item(12926),  //Rune spikeshield 20

            Item(12927),  //Rune spikeshield 0

            Item(12929),  //Rune berserker shield 100

            Item(12930),  //Rune berserker shield 80

            Item(12931),  //Rune berserker shield 60

            Item(12932),  //Rune berserker shield 40

            Item(12933),  //Rune berserker shield 20

            Item(12934),  //Rune berserker shield 0

            Item(12936),  //Green d'hide coif 100

            Item(12937),  //Green d'hide coif 80

            Item(12938),  //Green d'hide coif 60

            Item(12939),  //Green d'hide coif 40

            Item(12940),  //Green d'hide coif 20

            Item(12941),  //Green d'hide coif 0

            Item(12964),  //Combat hood 100

            Item(12965),  //Combat hood 80

            Item(12966),  //Combat hood 60

            Item(12967),  //Combat hood 40

            Item(12968),  //Combat hood 20

            Item(12969),  //Combat hood 0

            Item(12971),  //Combat robe top 100

            Item(12972),  //Combat robe top 80

            Item(12973),  //Combat robe top 60

            Item(12974),  //Combat robe top 40

            Item(12975),  //Combat robe top 20

            Item(12976),  //Combat robe top 0

            Item(12978),  //Combat robe bottom 100

            Item(12979),  //Combat robe bottom 80

            Item(12980),  //Combat robe bottom 60

            Item(12981),  //Combat robe bottom 40

            Item(12982),  //Combat robe bottom 20

            Item(12983),  //Combat robe bottom 0

            Item(12985),  //Bronze gauntlets

            Item(12986),  //Worn-out bronze gauntlets

            Item(12988),  //Iron gauntlets

            Item(12989),  //Worn-out iron gauntlets

            Item(12991),  //Steel gauntlets

            Item(12992),  //Worn-out steel gauntlets

            Item(12994),  //Black gauntlets

            Item(12995),  //Worn-out black gauntlets

            Item(12997),  //Mithril gauntlets

            Item(12998),  //Worn-out mithril gauntlets

            Item(13000),  //Adamant gauntlets

            Item(13001),  //Worn-out adamant gauntlets

            Item(13003),  //Rune gauntlets

            Item(13004),  //Worn-out rune gauntlets

            Item(13469),  //Rune axe

            Item(13471),  //Rune battleaxe

            Item(13473),  //Rune warhammer

            Item(13474),  //Rune longsword

            Item(13476),  //Rune scimitar

            Item(13480),  //Rune pickaxe

            Item(13482),  //Rune platebody

            Item(13483),  //Green d'hide body

            Item(13487),  //Rune platelegs

            Item(13489),  //Rune plateskirt

            Item(13491),  //Green d'hide chaps

            Item(13496),  //Rune full helm

            Item(13497),  //Green d'hide vamb

            Item(13507),  //Rune kiteshield

            Item(13523),  //Maple longbow

            Item(13524),  //Maple shortbow

            Item(13531),  //Red partyhat

            Item(13532),  //Yellow partyhat

            Item(13533),  //Blue partyhat

            Item(13534),  //Green partyhat

            Item(13535),  //Purple partyhat

            Item(13536),  //White partyhat

            Item(13537),  //Santa hat

            Item(13538),  //Green h'ween mask

            Item(13539),  //Blue h'ween mask

            Item(13540),  //Red h'ween mask

            Item(13541),  //Willow comp bow

            Item(13560),  //Explorer's ring 1

            Item(13561),  //Explorer's ring 2

            Item(13562),  //Explorer's ring 3

            Item(13613),  //Runecrafter hat

            Item(13614),  //Runecrafter robe

            Item(13615),  //Runecrafter hat

            Item(13616),  //Runecrafter hat

            Item(13617),  //Runecrafter skirt

            Item(13618),  //Runecrafter gloves

            Item(13619),  //Runecrafter robe

            Item(13620),  //Runecrafter hat

            Item(13621),  //Runecrafter hat

            Item(13622),  //Runecrafter skirt

            Item(13623),  //Runecrafter gloves

            Item(13624),  //Runecrafter robe

            Item(13625),  //Runecrafter hat

            Item(13626),  //Runecrafter hat

            Item(13627),  //Runecrafter skirt

            Item(13628),  //Runecrafter gloves

            Item(13630),  //Air talisman staff

            Item(13631),  //Mind talisman staff

            Item(13632),  //Water talisman staff

            Item(13633),  //Earth talisman staff

            Item(13634),  //Fire talisman staff

            Item(13635),  //Body talisman staff

            Item(13643),  //Yellow attractor

            Item(13644),  //Yellow repeller

            Item(13645),  //Green attractor

            Item(13646),  //Green repeller

            Item(13657),  //Runecrafter hat

            Item(13658),  //Runecrafter hat

            Item(13765),  //Rune dagger

            Item(13777),  //Rune sword

            Item(13778),  //Rune 2h sword

            Item(13780),  //Rune mace

            Item(13781),  //Rune chainbody

            Item(13783),  //Rune med helm

            Item(13787),  //Rune sq shield

            Item(13800),  //Rune platebody (g)

            Item(13801),  //Rune platelegs (g)

            Item(13802),  //Rune plateskirt (g)

            Item(13803),  //Rune full helm(g)

            Item(13804),  //Rune kiteshield (g)

            Item(13805),  //Rune platebody (t)

            Item(13806),  //Rune platelegs (t)

            Item(13807),  //Rune plateskirt (t)

            Item(13808),  //Rune full helm (t)

            Item(13809),  //Rune kiteshield (t)

            Item(13820),  //Zamorak platebody

            Item(13821),  //Zamorak platelegs

            Item(13822),  //Zamorak plateskirt

            Item(13823),  //Zamorak full helm

            Item(13824),  //Zamorak kiteshield

            Item(13825),  //Saradomin platebody

            Item(13826),  //Saradomin platelegs

            Item(13827),  //Saradomin plateskirt

            Item(13828),  //Saradomin full helm

            Item(13829),  //Saradomin kiteshield

            Item(13830),  //Guthix platebody

            Item(13831),  //Guthix platelegs

            Item(13832),  //Guthix plateskirt

            Item(13833),  //Guthix full helm

            Item(13834),  //Guthix kiteshield

            Item(13958),  //Corrupt dragon chainbody

            Item(13960),  //Corrupt dragon chainbody (deg)

            Item(13961),  //Corrupt dragon med helm

            Item(13963),  //Corrupt dragon med helm (deg)

            Item(13964),  //Corrupt dragon sq shield

            Item(13966),  //Corrupt dragon sq shield (deg)

            Item(13967),  //Corrupt dragon plateskirt

            Item(13969),  //Corrupt dragon plateskirt (deg)

            Item(13970),  //Corrupt dragon platelegs

            Item(13972),  //Corrupt dragon platelegs (deg)

            Item(13973),  //Corrupt dragon battleaxe

            Item(13975),  //C. dragon battleaxe (deg)

            Item(13976),  //Corrupt dragon dagger

            Item(13978),  //C. dragon dagger (deg)

            Item(13979),  //Corrupt dragon scimitar

            Item(13981),  //C. dragon scimitar (deg)

            Item(13982),  //Corrupt dragon longsword

            Item(13984),  //C. dragon longsword (deg)

            Item(13985),  //Corrupt dragon mace

            Item(13987),  //Corrupt dragon mace (deg)

            Item(13988),  //Corrupt dragon spear

            Item(13990),  //Corrupt dragon spear (deg)

            Item(14057),  //Broomstick

            Item(14076),  //Warlock top

            Item(14077),  //Warlock legs

            Item(14078),  //Witch top

            Item(14079),  //Witch skirt

            Item(14080),  //Witch cloak

            Item(14081),  //Warlock cloak

            Item(14086),  //Witch top

            Item(14087),  //Witch skirt

            Item(14088),  //Witch cloak

            Item(14595),  //Santa costume top

            Item(14596),  //Ice amulet

            Item(14599),  //Ice amulet

            Item(14600),  //Santa costume top

            Item(14601),  //Santa costume top

            Item(14602),  //Santa costume gloves

            Item(14603),  //Santa costume legs

            Item(14604),  //Santa costume legs

            Item(14605),  //Santa costume boots

            Item(14812),  //Black partyhat

            Item(14819),  //Rainbow partyhat

            Item(14873),  //Black santa hat

            Item(14874) //Inverted santa hat
        )
    )

    companion object {
        /**
         * Creates a new artificial intelligent player.
         * @return The AIPlayer object.
         */
        fun create(l: Location?): AIPlayer {
            return AIPlayer(l!!)
        }

        /**
         * Makes an artificial intelligent copy of the player.
         * @param player The player.
         * @param l The location the AIP should spawn on.
         * @return The artificial intelligent player with the same name, stats,
         * items, etc.
         */
        /**
         * Makes an artificial intelligent copy of the player.
         * @param player The player.
         * @return The artificial intelligent player with the same name, stats,
         * items, etc.
         */
        @JvmOverloads
        fun copy(player: Player, l: Location? = player.location): AIPlayer {
            val p = AIPlayer(l!!)
            p.getSkills().copy(player.getSkills())
            p.inventory.copy(player.inventory)
            p.equipment.copy(player.equipment)
            p.bank.copy(player.bank)
            p.appearance.copy(player.appearance)
            p.controler = player
            return p
        }

        fun RandomAfkPlayer(loc: Location?) {
            val bot = AIPlayer(loc!!)
            bot.appearance.gender = if (RandomFunction.random(3) == 1) Gender.FEMALE else Gender.MALE
            bot.getSkills().setStaticLevel(Skills.MAGIC, RandomFunction.getRandom(99))
            bot.getSkills().setStaticLevel(Skills.DEFENCE, RandomFunction.getRandom(99))
            bot.getSkills().setStaticLevel(Skills.ATTACK, RandomFunction.getRandom(99))
            bot.getSkills().setStaticLevel(Skills.STRENGTH, RandomFunction.getRandom(99))
            bot.getSkills().setStaticLevel(Skills.RANGE, RandomFunction.getRandom(99))
            bot.getSkills().setStaticLevel(Skills.HITPOINTS, RandomFunction.getRandom(99))
            bot.getSkills().updateCombatLevel()
            bot.appearance.sync()
        }

        fun immersiveSpawns() {
            // Edge
            RandomAfkPlayer(Location(3094, 3491))
            RandomAfkPlayer(Location(3094, 3493))
            RandomAfkPlayer(Location(3092, 3497))
            // GE
            RandomAfkPlayer(Location(3160, 3492))
            RandomAfkPlayer(Location(3161, 3491))
            RandomAfkPlayer(Location(3165, 3483))
            RandomAfkPlayer(Location(3168, 3485))
            RandomAfkPlayer(Location(3165, 3485))
            RandomAfkPlayer(Location(3164, 3493))
            // Varrock Ge bank
            RandomAfkPlayer(Location(3189, 3439))
            // Home Bank
            RandomAfkPlayer(Location(2099, 3918))
        }
    }
}