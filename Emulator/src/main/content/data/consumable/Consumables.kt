package content.data.consumable

import content.data.consumable.effects.*
import cfg.consts.Items
import core.game.consumable.*
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.Skills
import core.game.world.update.flag.context.Animation
import core.tools.minutesToTicks
import core.tools.secondsToTicks

/**
 * Represents a repository of active consumables in the framework.
 */
enum class Consumables {
    /**
     * The Bat Shish food.
     */
    BAT_SHISH(Food(intArrayOf(Items.BAT_SHISH_10964), HealingEffect(2))),

    /**
     * The Fingers food.
     */
    FINGERS(Food(intArrayOf(Items.FINGERS_10965), HealingEffect(2))),

    /**
     * The Coated Frog Legs food.
     */
    COATED_FROG_LEGS(Food(intArrayOf(Items.COATED_FROGS_LEGS_10963), HealingEffect(2))),

    /**
     * The Cooked meat food.
     */
    COOKED_MEAT(Food(intArrayOf(Items.COOKED_MEAT_2142), HealingEffect(3))),

    /**
     * The Shrimps food.
     */
    SHRIMPS(Food(intArrayOf(Items.SHRIMPS_315), HealingEffect(3))),

    /**
     * The Cooked Chicken food.
     */
    COOKED_CHICKEN(Food(intArrayOf(Items.COOKED_CHICKEN_2140), HealingEffect(3))),

    /**
     * The Cooked Rabbit food.
     */
    COOKED_RABBIT(Food(intArrayOf(Items.COOKED_RABBIT_3228), HealingEffect(5))),

    /**
     * The Anchovies food.
     */
    ANCHOVIES(Food(intArrayOf(Items.ANCHOVIES_319), HealingEffect(1))),

    /**
     * The Sardine food.
     */
    SARDINE(Food(intArrayOf(Items.SARDINE_325), HealingEffect(4))),

    /**
     * The Poison Karambwan.
     */
    POISON_KARAMBWAN(Food(intArrayOf(Items.POISON_KARAMBWAN_3146), PoisonKarambwanEffect())),

    /**
     * The Ugthanki meat food.
     */
    UGTHANKI_MEAT(Food(intArrayOf(Items.UGTHANKI_MEAT_1861), HealingEffect(3))),

    /**
     * The Herring food.
     */
    HERRING(Food(intArrayOf(Items.HERRING_347), HealingEffect(5))),

    /**
     * The Mackerel food.
     */
    MACKEREL(Food(intArrayOf(Items.MACKEREL_355), HealingEffect(6))),

    /**
     * The Roast Bird meat food.
     */
    ROAST_BIRD_MEAT(Food(intArrayOf(Items.ROAST_BIRD_MEAT_9980), HealingEffect(6))),

    /**
     * The Thin Snail meat food.
     */
    THIN_SNAIL_MEAT(Food(intArrayOf(Items.THIN_SNAIL_MEAT_3369), HealingEffect(5))),

    /**
     * The Trout food.
     */
    TROUT(Food(intArrayOf(Items.TROUT_333), HealingEffect(7))),

    /**
     * The Spider On Stick food.
     */
    SPIDER_ON_STICK(Food(intArrayOf(Items.SPIDER_ON_STICK_6297), HealingEffect(7))),

    /**
     * The Spider On Shaft food.
     */
    SPIDER_ON_SHAFT(Food(intArrayOf(Items.SPIDER_ON_SHAFT_6299), HealingEffect(7))),

    /**
     * The Roast Rabbit.
     */
    ROAST_RABBIT(Food(intArrayOf(Items.ROAST_RABBIT_7223), HealingEffect(7))),

    /**
     * The Lean Snail meat food.
     */
    LEAN_SNAIL_MEAT(Food(intArrayOf(Items.LEAN_SNAIL_MEAT_3371), HealingEffect(8))),

    /**
     * The Cod.
     */
    COD(Food(intArrayOf(Items.COD_339), HealingEffect(7))),

    /**
     * The Pike.
     */
    PIKE(Food(intArrayOf(Items.PIKE_351), HealingEffect(8))),

    /**
     * The Roast Beast meat food.
     */
    ROAST_BEAST_MEAT(Food(intArrayOf(Items.ROAST_BEAST_MEAT_9988), HealingEffect(8))),

    /**
     * The Cooked Crab meat food.
     */
    COOKED_CRAB_MEAT(
        Food(
            intArrayOf(
                Items.COOKED_CRAB_MEAT_7521,
                Items.COOKED_CRAB_MEAT_7523,
                Items.COOKED_CRAB_MEAT_7524,
                Items.COOKED_CRAB_MEAT_7525,
                Items.COOKED_CRAB_MEAT_7526
            ), HealingEffect(2)
        )
    ),

    /**
     * The Fat Snail food.
     */
    FAT_SNAIL(Food(intArrayOf(Items.FAT_SNAIL_MEAT_3373), HealingEffect(9))),

    /**
     * The Salmon food.
     */
    SALMON(Food(intArrayOf(Items.SALMON_329), HealingEffect(9))),

    /**
     * The Slimy Eel food.
     */
    SLIMY_EEL(Food(intArrayOf(Items.COOKED_SLIMY_EEL_3381), HealingEffect(6))),

    /**
     * The Tuna food.
     */
    TUNA(Food(intArrayOf(Items.TUNA_361), HealingEffect(10))),

    /**
     * The Cooked Karambwan food.
     */
    COOKED_KARAMBWAN(Food(intArrayOf(Items.COOKED_KARAMBWAN_3144), HealingEffect(18)), true),

    /**
     * The Cooked Chompy food.
     */
    COOKED_CHOMPY(Food(intArrayOf(Items.COOKED_CHOMPY_2878), HealingEffect(10))),

    /**
     * The Rainbow Fish food.
     */
    RAINBOW_FISH(Food(intArrayOf(Items.RAINBOW_FISH_10136), HealingEffect(11))),

    /**
     * The Cave Eel food.
     */
    CAVE_EEL(Food(intArrayOf(Items.CAVE_EEL_5003), HealingEffect(7))),

    /**
     * The Caviar food.
     */
    CAVIAR(Food(intArrayOf(Items.CAVIAR_11326), HealingEffect(5))),

    /**
     * The Lobster food.
     */
    LOBSTER(Food(intArrayOf(Items.LOBSTER_379), HealingEffect(12))),

    /**
     * The Cooked Jubbly food.
     */
    COOKED_JUBBLY(Food(intArrayOf(Items.COOKED_JUBBLY_7568), HealingEffect(15))),

    /**
     * The Bass food.
     */
    BASS(Food(intArrayOf(Items.BASS_365), HealingEffect(13))),

    /**
     * The Swordfish food.
     */
    SWORDFISH(Food(intArrayOf(Items.SWORDFISH_373), HealingEffect(14))),

    /**
     * The Lava Eel food.
     */
    LAVA_EEL(Food(intArrayOf(Items.LAVA_EEL_2149), HealingEffect(14))),

    /**
     * The Monkfish food.
     */
    MONKFISH(Food(intArrayOf(Items.MONKFISH_7946), HealingEffect(16))),

    /**
     * The Shark food.
     */
    SHARK(Food(intArrayOf(Items.SHARK_385), HealingEffect(20))),

    /**
     * The Sea Turtle food.
     */
    SEA_TURTLE(Food(intArrayOf(Items.SEA_TURTLE_397), HealingEffect(21))),

    /**
     * The Manta Ray food.
     */
    MANTA_RAY(Food(intArrayOf(Items.MANTA_RAY_391), HealingEffect(22))),

    /**
     * The Karambwanji food.
     */
    KARAMBWANJI(Food(intArrayOf(Items.KARAMBWANJI_3151), HealingEffect(3))),

    /**
     * The Stuffed Snake food.
     */
    STUFFED_SNAKE(
        Food(
            intArrayOf(Items.STUFFED_SNAKE_7579),
            HealingEffect(20),
            "You eat the stuffed snake-it's quite a meal! It tastes like chicken."
        )
    ),

    /**
     * The Crayfish food.
     */
    CRAYFISH(Food(intArrayOf(Items.CRAYFISH_13433), HealingEffect(2))),

    /**
     * The Giant Frog Legs food.
     */
    GIANT_FROG_LEGS(Food(intArrayOf(Items.GIANT_FROG_LEGS_4517), HealingEffect(6))),

    /**
     * The Bread food.
     */
    BREAD(Food(intArrayOf(Items.BREAD_2309), HealingEffect(5))),

    /**
     * The Baguette food.
     */
    BAGUETTE(Food(intArrayOf(Items.BAGUETTE_6961), HealingEffect(6))),

    /**
     * The Triangle Sandwich food.
     */
    TRIANGLE_SANDWICH(Food(intArrayOf(Items.TRIANGLE_SANDWICH_6962), HealingEffect(6))),

    /**
     * The Square Sandwich food.
     */
    SQUARE_SANDWICH(Food(intArrayOf(Items.SQUARE_SANDWICH_6965), HealingEffect(6))),

    /**
     * The Seaweed Sandwich fake food.
     */
    SEAWEED_SANDWICH(
        FakeConsumable(
            Items.SEAWEED_SANDWICH_3168,
            arrayOf("You really, really do not want to eat that.")
        )
    ),

    /**
     * The Frogburger food.
     */
    FROGBURGER(Food(intArrayOf(Items.FROGBURGER_10962), HealingEffect(2))),

    /**
     * The Ugthanki Kebab food.
     */
    UGTHANKI_KEBAB(Food(intArrayOf(Items.UGTHANKI_KEBAB_1883), UgthankiKebabEffect())),

    /**
     * The Ugthanki Kebab Smelling food.
     */
    UGTHANKI_KEBAB_SMELLING(Food(intArrayOf(Items.UGTHANKI_KEBAB_1885), SmellingUgthankiKebabEffect())),

    /**
     * The Kebab food.
     */
    KEBAB(Food(intArrayOf(Items.KEBAB_1971), KebabEffect())),

    /**
     * The Super Kebab food.
     */
    SUPER_KEBAB(Food(intArrayOf(Items.SUPER_KEBAB_4608), SuperKebabEffect())),

    /**
     * The Redberry pie half able food.
     */
    REDBERRY_PIE(
        HalfableFood(
            intArrayOf(Items.REDBERRY_PIE_2325, Items.HALF_A_REDBERRY_PIE_2333, Items.PIE_DISH_2313),
            HealingEffect(5)
        )
    ),

    /**
     * The Meat pie half able food.
     */
    MEAT_PIE(
        HalfableFood(
            intArrayOf(Items.MEAT_PIE_2327, Items.HALF_A_MEAT_PIE_2331, Items.PIE_DISH_2313),
            HealingEffect(6)
        )
    ),

    /**
     * The Apple pie half able food.
     */
    APPLE_PIE(
        HalfableFood(
            intArrayOf(Items.APPLE_PIE_2323, Items.HALF_AN_APPLE_PIE_2335, Items.PIE_DISH_2313),
            HealingEffect(7)
        )
    ),

    /**
     * The Garden pie half able food.
     */
    GARDEN_PIE(
        HalfableFood(
            intArrayOf(Items.GARDEN_PIE_7178, Items.HALF_A_GARDEN_PIE_7180, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.FARMING, 3.0, 0.0))
        )
    ),

    /**
     * The Fish pie half able food.
     */
    FISH_PIE(
        HalfableFood(
            intArrayOf(Items.FISH_PIE_7188, Items.HALF_A_FISH_PIE_7190, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.FISHING, 3.0, 0.0))
        )
    ),

    /**
     * The Admiral pie half able food.
     */
    ADMIRAL_PIE(
        HalfableFood(
            intArrayOf(Items.ADMIRAL_PIE_7198, Items.HALF_AN_ADMIRAL_PIE_7200, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(8), SkillEffect(Skills.FISHING, 5.0, 0.0))
        )
    ),

    /**
     * The Wild pie half able food.
     */
    WILD_PIE(
        HalfableFood(
            intArrayOf(Items.WILD_PIE_7208, Items.HALF_A_WILD_PIE_7210, Items.PIE_DISH_2313),
            MultiEffect(SkillEffect(Skills.SLAYER, 5.0, 0.0), SkillEffect(Skills.RANGE, 4.0, 0.0), HealingEffect(11))
        )
    ),

    /**
     * The Summer pie half able food.
     */
    SUMMER_PIE(
        HalfableFood(
            intArrayOf(Items.SUMMER_PIE_7218, Items.HALF_A_SUMMER_PIE_7220, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(11), SkillEffect(Skills.AGILITY, 5.0, 0.0), EnergyEffect(10))
        )
    ),

    /**
     * The Stew food.
     */
    STEW(Food(intArrayOf(Items.STEW_2003, Items.BOWL_1923), HealingEffect(11))),

    /**
     * The Spicy Stew food.
     */
    SPICY_STEW(Food(intArrayOf(Items.SPICY_STEW_7479, Items.BOWL_1923), HealingEffect(11))),

    /**
     * The Curry food.
     */
    CURRY(Food(intArrayOf(Items.CURRY_2011, Items.BOWL_1923), HealingEffect(19))),

    /**
     * The Banana Stew food.
     */
    BANANA_STEW(Food(intArrayOf(Items.BANANA_STEW_4016, Items.BOWL_1923), HealingEffect(11))),

    /**
     * The Plain Pizza half able food.
     */
    PLAIN_PIZZA(HalfableFood(intArrayOf(Items.PLAIN_PIZZA_2289, Items.HALF_PLAIN_PIZZA_2291), HealingEffect(7))),

    /**
     * The Meat Pizza half able food.
     */
    MEAT_PIZZA(HalfableFood(intArrayOf(Items.MEAT_PIZZA_2293, Items.HALF_MEAT_PIZZA_2295), HealingEffect(8))),

    /**
     * The Anchovy Pizza half able food.
     */
    ANCHOVY_PIZZA(HalfableFood(intArrayOf(Items.ANCHOVY_PIZZA_2297, Items.HALF_ANCHOVY_PIZZA_2299), HealingEffect(9))),

    /**
     * The White Pearl half able food.
     */
    WHITE_PEARL(HalfableFood(intArrayOf(Items.WHITE_PEARL_4485, Items.WHITE_PEARL_SEED_4486), HealingEffect(2), "You eat the white pearl and spit out the seed when you're done. Mmm, tasty.")),

    /**
     * The Pineapple Pizza half able food.
     */
    PINEAPPLE_PIZZA(HalfableFood(intArrayOf(Items.PINEAPPLE_PIZZA_2301, Items.HALF_PAPPLE_PIZZA_2303), HealingEffect(11))),

    /**
     * The Cake food.
     */
    CAKE(Cake(intArrayOf(Items.CAKE_1891, Items.TWO_THIRDS_CAKE_1893, Items.SLICE_OF_CAKE_1895), HealingEffect(4), "You eat part of the cake.", "You eat some more cake.", "You eat the slice of cake.")),

    /**
     * The Chocolate cake food.
     */
    CHOCOLATE_CAKE(Cake(intArrayOf(Items.CHOCOLATE_CAKE_1897, Items.TWO_THIRDS_CHOCOLATE_CAKE_1899, Items.CHOCOLATE_SLICE_1901), HealingEffect(5), "You eat part of the chocolate cake.", "You eat some more of the chocolate cake.", "You eat the slice of cake.")),

    /**
     * The Rock Cake.
     */
    ROCK_CAKE(Food(intArrayOf(Items.ROCK_CAKE_2379), RockCakeEffect(), "The rock cake resists all attempts to eat it.")),

    /**
     * The Cave Nightshade food.
     */
    CAVE_NIGHTSHADE(Food(intArrayOf(Items.CAVE_NIGHTSHADE_2398), DamageEffect(15.0, false), "Ahhhh! What have I done!")),

    /**
     * The Dwarven Rock cake food.
     */
    DWARVEN_ROCK_CAKE(Food(intArrayOf(7510, 7510), DwarvenRockCakeEffect(), "Ow! You nearly broke a tooth!", "The rock cake resists all attempts to eat it.")),

    /**
     * The Hot Dwarven Rock cake food.
     */
    HOT_DWARVEN_ROCK_CAKE(
        Food(
            intArrayOf(7509, 7509),
            DwarvenRockCakeEffect(),
            "Ow! You nearly broke a tooth!",
            "The rock cake resists all attempts to eat it."
        )
    ),

    /**
     * The Cooked Fishcake food.
     */
    COOKED_FISHCAKE(Food(intArrayOf(7530), HealingEffect(11))),

    /**
     * The Mint Cake food.
     */
    MINT_CAKE(Food(intArrayOf(9475), EnergyEffect(50))),

    /**
     * The Potato food.
     */
    POTATO(Food(intArrayOf(1942), HealingEffect(1), "You eat the potato. Yuck!")),

    /**
     * The Baked Potato food.
     */
    BAKED_POTATO(Food(intArrayOf(6701), HealingEffect(2))),

    /**
     * The Poisoned Cheese fake food.
     */
    POISONED_CHEESE(
        FakeConsumable(
            Items.POISONED_CHEESE_6768,
            arrayOf("Ummm... let me think about this one.....No! That would be stupid.")
        )
    ),

    /**
     * The Poison Chalice drink.
     */
    POISON_CHALICE(Drink(intArrayOf(Items.POISON_CHALICE_197, Items.COCKTAIL_GLASS_2026), PoisonChaliceEffect(), "You drink the strange green liquid.")),

    /**
     * The Spicy Sauce food.
     */
    SPICY_SAUCE(Food(intArrayOf(7072, 1923), HealingEffect(2))),

    /**
     * The Locust meat food.
     */
    LOCUST_MEAT(
        Food(
            intArrayOf(Items.LOCUST_MEAT_9052),
            HealingEffect(3),
            "Juices spurt into your mouth as you chew. It's tastier than it looks."
        )
    ),

    /**
     * The chilli con carne food.
     */
    CHILLI_CON_CARNE(Food(intArrayOf(7062, 1923), HealingEffect(5))),

    /**
     * The scrambled egg food.
     */
    SCRAMBLED_EGG(Food(intArrayOf(7078, 1923), HealingEffect(5))),

    /**
     * The egg And tomato food.
     */
    EGG_AND_TOMATO(Food(intArrayOf(7064, 1923), HealingEffect(8))),

    /**
     * The sweet corn food.
     */
    SWEET_CORN(Food(intArrayOf(5988), MultiEffect(HealingEffect(1), PercentageHealthEffect(10)))),

    /**
     * The sweetcorn bowl food.
     */
    SWEETCORN_BOWL(Food(intArrayOf(7088, 1923), MultiEffect(HealingEffect(1), PercentageHealthEffect(10)))),

    /**
     * The potato with butter food.
     */
    POTATO_WITH_BUTTER(Food(intArrayOf(6703), HealingEffect(7))),

    /**
     * The Chilli Potato food.
     */
    CHILLI_POTATO(Food(intArrayOf(7054), HealingEffect(14))),

    /**
     * The Fried Onions food.
     */
    FRIED_ONIONS(Food(intArrayOf(7084, 1923), HealingEffect(5))),

    /**
     * The Fried Mushrooms food.
     */
    FRIED_MUSHROOMS(Food(intArrayOf(7082, 1923), HealingEffect(5))),

    /**
     * The Black Mushroom fake food.
     */
    BLACK_MUSHROOM(
        FakeConsumable(
            Items.BLACK_MUSHROOM_4620,
            arrayOf("Eugh! It tastes horrible, and stains your fingers black.")
        )
    ),

    /**
     * The Potato With Cheese food.
     */
    POTATO_WITH_CHEESE(Food(intArrayOf(6705), HealingEffect(16))),

    /**
     * The Egg Potato food.
     */
    EGG_POTATO(Food(intArrayOf(7056), HealingEffect(11))),

    /**
     * The Mushrooms And Onions food.
     */
    MUSHROOMS_AND_ONIONS(Food(intArrayOf(7066, 1923), HealingEffect(11))),

    /**
     * The Mushroom Potato food.
     */
    MUSHROOM_POTATO(Food(intArrayOf(7058), HealingEffect(20))),

    /**
     * The Tuna And Corn food.
     */
    TUNA_AND_CORN(Food(intArrayOf(7068, 1923), HealingEffect(13))),

    /**
     * The Tuna Potato food.
     */
    TUNA_POTATO(Food(intArrayOf(7060), HealingEffect(22))),

    /**
     * The Onion food.
     */
    ONION(Food(intArrayOf(1957), HealingEffect(2), "It's always sad to see a grown man/woman cry.")),

    /**
     * The Cabbage food.
     */
    CABBAGE(Food(intArrayOf(1965), HealingEffect(2), "You eat the cabbage. Yuck!")),

    /**
     * The Draynor Cabbage food.
     */
    DRAYNOR_CABBAGE(
        Food(
            intArrayOf(1967),
            DraynorCabbageEffect(),
            "You eat the cabbage.",
            "It seems to taste nicer than normal."
        )
    ),

    /**
     * The Rotten Apple food.
     */
    ROTTEN_APPLE(Food(intArrayOf(1984), HealingEffect(2), "It's rotten, you spit it out")),

    /**
     * The Evil Turnip food.
     */
    EVIL_TURNIP(Food(intArrayOf(12134, 12136, 12138), HealingEffect(6))),

    /**
     * The Spinach Roll food.
     */
    SPINACH_ROLL(Food(intArrayOf(1969), HealingEffect(2))),

    /**
     * The Pot Of Cream food.
     */
    POT_OF_CREAM(Food(intArrayOf(2130), HealingEffect(1), "You eat the cream. You get some on your nose.")),

    /**
     * The Cheese food.
     */
    CHEESE(Food(intArrayOf(1985), HealingEffect(2))),

    /**
     * The Chocolatey Milk food.
     */
    CHOCOLATEY_MILK(Drink(intArrayOf(1977, 1925), HealingEffect(4))),

    /**
     * The Banana food.
     */
    BANANA(Food(intArrayOf(1963), HealingEffect(2))),

    /**
     * The Sliced Banana food.
     */
    SLICED_BANANA(Food(intArrayOf(3162), HealingEffect(2))),

    /**
     * The Red Banana food.
     */
    RED_BANANA(
        Food(
            intArrayOf(7572),
            HealingEffect(5),
            "You eat the red banana. It's tastier than your average banana."
        )
    ),

    /**
     * The Sliced Red Banana food.
     */
    SLICED_RED_BANANA(Food(intArrayOf(7574), HealingEffect(5), "You eat the sliced red banana. Yum.")),

    /**
     * The Orange food.
     */
    ORANGE(Food(intArrayOf(2108), HealingEffect(2))),

    /**
     * The Orange Chunks food.
     */
    ORANGE_CHUNKS(Food(intArrayOf(2110), HealingEffect(2))),

    /**
     * The Orange Slices food.
     */
    ORANGE_SLICES(Food(intArrayOf(2112), HealingEffect(2))),

    /**
     * The Papaya Fruit food.
     */
    PAPAYA_FRUIT(Food(intArrayOf(5972), HealingEffect(2))),

    /**
     * The Tenti Pineapple fake food.
     */
    TENTI_PINEAPPLE(FakeConsumable(1851, arrayOf("Try using a knife to slice it into pieces."))),

    /**
     * The Pineapple fake food.
     */
    PINEAPPLE(FakeConsumable(2114, arrayOf("Try using a knife to slice it into pieces."))),

    /**
     * The Pineapple Chunks food.
     */
    PINEAPPLE_CHUNKS(Food(intArrayOf(2116), HealingEffect(2))),

    /**
     * The Pineapple ring food.
     */
    PINEAPPLE_RING(Food(intArrayOf(2118), HealingEffect(2))),

    /**
     * The Dwellberries food.
     */
    DWELLBERRIES(Food(intArrayOf(2126), HealingEffect(2))),

    /**
     * The Jangerberries food.
     */
    JANGERBERRIES(
        Food(
            intArrayOf(247),
            MultiEffect(
                SkillEffect(Skills.ATTACK, 2.0, 0.0),
                SkillEffect(Skills.STRENGTH, 1.0, 0.0),
                PrayerEffect(1.0, 0.0),
                SkillEffect(Skills.DEFENCE, -1.0, 0.0)
            ),
            "You eat the jangerberries.",
            "They taste very bitter."
        )
    ),

    /**
     * The Strawberry food.
     */
    STRAWBERRY(Food(intArrayOf(5504), MultiEffect(HealingEffect(1), PercentageHealthEffect(6)))),

    /**
     * The Tomato food.
     */
    TOMATO(Food(intArrayOf(1982), HealingEffect(2))),

    /**
     * The Watermelon fake food.
     */
    WATERMELON(FakeConsumable(5982, arrayOf("You can't eat it whole; maybe you should cut it up."))),

    /**
     * The Watermelon Slice food.
     */
    WATERMELON_SLICE(Food(intArrayOf(5984), PercentageHealthEffect(5))),

    /**
     * The Lemon food.
     */
    LEMON(Food(intArrayOf(2102), HealingEffect(2))),

    /**
     * The Lemon Chunks food.
     */
    LEMON_CHUNKS(Food(intArrayOf(2104), HealingEffect(2))),

    /**
     * The Lemon Slices food.
     */
    LEMON_SLICES(Food(intArrayOf(2106), HealingEffect(2))),

    /**
     * The Lime food.
     */
    LIME(Food(intArrayOf(2120), HealingEffect(2))),

    /**
     * The Lime Chunks food.
     */
    LIME_CHUNKS(Food(intArrayOf(2122), HealingEffect(2))),

    /**
     * The Lime Slices food.
     */
    LIME_SLICES(Food(intArrayOf(2124), HealingEffect(2))),

    /**
     * The Peach food.
     */
    PEACH(Food(intArrayOf(6883), HealingEffect(8))),

    /**
     * The White Tree Fruit food.
     */
    WHITE_TREE_FRUIT(Food(intArrayOf(6469), MultiEffect(RandomEnergyEffect(5, 10), HealingEffect(3)))),

    /**
     * The Strange Fruit food.
     */
    STRANGE_FRUIT(
        Food(
            intArrayOf(464),
            MultiEffect(RemoveTimerEffect("poison"), EnergyEffect(30)),
            "You eat the fruit.",
            "It tastes great; some of your run energy is restored!"
        )
    ),

    /**
     * The Toad Crunchies food.
     */
    TOAD_CRUNCHIES(Food(intArrayOf(2217), HealingEffect(12))),

    /**
     * The Premade Td Crunch food.
     */
    PREMADE_TD_CRUNCH(Food(intArrayOf(2243), HealingEffect(12))),

    /**
     * The Spicy Crunchies food.
     */
    SPICY_CRUNCHIES(Food(intArrayOf(2213), HealingEffect(7))),

    /**
     * The Premade Sy Crunch food.
     */
    PREMADE_SY_CRUNCH(Food(intArrayOf(2241), HealingEffect(7))),

    /**
     * The Worm Crunchies food.
     */
    WORM_CRUNCHIES(Food(intArrayOf(2205), HealingEffect(8))),

    /**
     * The Premade Wm Crunc food.
     */
    PREMADE_WM_CRUNC(Food(intArrayOf(2237), HealingEffect(8))),

    /**
     * The Chocchip Crunchies food.
     */
    CHOCCHIP_CRUNCHIES(Food(intArrayOf(2209), HealingEffect(7))),

    /**
     * The Premade Ch Crunch food.
     */
    PREMADE_CH_CRUNCH(Food(intArrayOf(2239), HealingEffect(7))),

    /**
     * The Fruit Batta food.
     */
    FRUIT_BATTA(Food(intArrayOf(2277), HealingEffect(11))),

    /**
     * The Premade Frt Batta food.
     */
    PREMADE_FRT_BATTA(Food(intArrayOf(2225), HealingEffect(11))),

    /**
     * The Toad Batta food.
     */
    TOAD_BATTA(Food(intArrayOf(2255), HealingEffect(11))),

    /**
     * The Premade Td Batta food.
     */
    PREMADE_TD_BATTA(Food(intArrayOf(2221), HealingEffect(11))),

    /**
     * The Worm Batta food.
     */
    WORM_BATTA(Food(intArrayOf(2253), HealingEffect(11))),

    /**
     * The Premade Wm Batta food.
     */
    PREMADE_WM_BATTA(Food(intArrayOf(2219), HealingEffect(11))),

    /**
     * The Vegetable Batta food.
     */
    VEGETABLE_BATTA(Food(intArrayOf(2281), HealingEffect(11))),

    /**
     * The Premade Veg Batta food.
     */
    PREMADE_VEG_BATTA(Food(intArrayOf(2227), HealingEffect(11))),

    /**
     * The Cheese And Tomatoes Batta food.
     */
    CHEESE_AND_TOMATOES_BATTA(Food(intArrayOf(2259), HealingEffect(11))),

    /**
     * The Premade Ct Batta food.
     */
    PREMADE_CT_BATTA(Food(intArrayOf(2223), HealingEffect(11))),

    /**
     * The Worm Hole food.
     */
    WORM_HOLE(Food(intArrayOf(2191), HealingEffect(12))),

    /**
     * The Premade Worm Hole food.
     */
    PREMADE_WORM_HOLE(Food(intArrayOf(2233), HealingEffect(12))),

    /**
     * The Veg Ball food.
     */
    VEG_BALL(Food(intArrayOf(2195), HealingEffect(12))),

    /**
     * The Premade Veg Ball food.
     */
    PREMADE_VEG_BALL(Food(intArrayOf(2235), HealingEffect(12))),

    /**
     * The Tangled Toads Legs food.
     */
    TANGLED_TOADS_LEGS(Food(intArrayOf(2187), HealingEffect(15))),

    /**
     * The Premade Ttl food.
     */
    PREMADE_TTL(Food(intArrayOf(2231), HealingEffect(15))),

    /**
     * The Chocolate Bomb food.
     */
    CHOCOLATE_BOMB(Food(intArrayOf(2195), HealingEffect(15))),

    /**
     * The Premade Choc Bomb food.
     */
    PREMADE_CHOC_BOMB(Food(intArrayOf(2229), HealingEffect(15))),

    /**
     * The Toad Legs food.
     */
    TOAD_LEGS(Food(intArrayOf(2152), HealingEffect(3), "You eat the toad's legs. They're a bit chewy.")),

    /**
     * The King Worm food.
     */
    KING_WORM(Food(intArrayOf(2162), HealingEffect(2))),

    /**
     * The Asgoldian Ale fake drink.
     */
    ASGOLDIAN_ALE(
        FakeConsumable(
            7508,
            arrayOf("I don't think I'd like gold in beer thanks. Leave it for the dwarves.")
        )
    ),

    /**
     * The Asgarnian Ale drink.
     */
    ASGARNIAN_ALE(
        Drink(
            intArrayOf(1905, 1919),
            MultiEffect(
                HealingEffect(2),
                SkillEffect(Skills.STRENGTH, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            ),
            "You drink the ale. You feel slightly reinvigorated...",
            "...and slightly dizzy too."
        )
    ),

    /**
     * The Asgarnian Ale Keg drink.
     */
    ASGARNIAN_ALE_KEG(
        Drink(
            intArrayOf(5785, 5783, 5781, 5779, 5769),
            MultiEffect(SkillEffect(Skills.STRENGTH, 2.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0)),
            Animation(2289),
            "You drink the ale. You feel slightly reinvigorated...",
            "...and slightly dizzy too."
        )
    ),

    /**
     * The Asgarnian Ale M drink.
     */
    ASGARNIAN_ALE_M(
        Drink(
            intArrayOf(5739, 1919),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * The Asgarnian Ale M Keg drink.
     */
    ASGARNIAN_ALE_M_KEG(
        Drink(
            intArrayOf(5865, 5863, 5861, 5859, 5769),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0)),
            Animation(2289)
        )
    ),

    /**
     * The Axemans Folly drink.
     */
    AXEMANS_FOLLY(
        Drink(
            intArrayOf(5751, 1919),
            MultiEffect(
                SkillEffect(Skills.WOODCUTTING, 1.0, 0.0),
                HealingEffect(1),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0),
                SkillEffect(Skills.ATTACK, -3.0, 0.0)
            )
        )
    ),

    /**
     * The Axemans Folly Keg drink.
     */
    AXEMANS_FOLLY_KEG(
        Drink(
            intArrayOf(5825, 5823, 5821, 5819, 5769),
            MultiEffect(
                SkillEffect(Skills.WOODCUTTING, 1.0, 0.0),
                HealingEffect(1),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0),
                SkillEffect(Skills.ATTACK, -3.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Axemans Folly M drink.
     */
    AXEMANS_FOLLY_M(
        Drink(
            intArrayOf(5753, 1919),
            MultiEffect(
                SkillEffect(Skills.WOODCUTTING, 2.0, 0.0),
                HealingEffect(2),
                SkillEffect(Skills.STRENGTH, -4.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            )
        )
    ),

    /**
     * The Axemans Folly M Keg drink.
     */
    AXEMANS_FOLLY_M_KEG(
        Drink(
            intArrayOf(5905, 5903, 5901, 5899, 5769),
            MultiEffect(
                SkillEffect(Skills.WOODCUTTING, 2.0, 0.0),
                HealingEffect(2),
                SkillEffect(Skills.STRENGTH, -4.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Bandits Brew drink.
     */
    BANDITS_BREW(
        Drink(
            intArrayOf(4627, 1919),
            MultiEffect(
                SkillEffect(Skills.THIEVING, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, 1.0, 0.0),
                SkillEffect(Skills.STRENGTH, -1.0, 0.0),
                SkillEffect(Skills.DEFENCE, -6.0, 0.0),
                HealingEffect(1)
            ),
            "You drink the beer. You feel slightly reinvigorated...",
            "...and slightly dizzy too."
        )
    ),

    /**
     * The Beer drink.
     */
    BEER(
        Drink(
            intArrayOf(1917, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.STRENGTH, 0.0, 0.04),
                SkillEffect(Skills.ATTACK, 0.0, -0.07)
            ),
            "You drink the beer. You feel slightly reinvigorated...",
            "...and slightly dizzy too."
        )
    ),

    /**
     * The Beer Tankard drink.
     */
    BEER_TANKARD(
        Drink(
            intArrayOf(3803, 3805),
            MultiEffect(SkillEffect(Skills.ATTACK, -9.0, 0.0), SkillEffect(Skills.STRENGTH, 4.0, 0.0)),
            "You quaff the beer. You feel slightly reinvigorated...",
            "...but very dizzy too."
        )
    ),

    /**
     * The Keg Of Beer drink.
     */
    KEG_OF_BEER(
        Drink(
            intArrayOf(3801),
            KegOfBeerEffect(),
            Animation(1330),
            "You chug the keg. You feel reinvigorated...",
            "...but extremely drunk too."
        )
    ),

    /**
     * The Chefs Delight drink.
     */
    CHEFS_DELIGHT(
        Drink(
            intArrayOf(5755, 1919),
            MultiEffect(
                SkillEffect(Skills.COOKING, 1.0, 0.05),
                HealingEffect(1),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            )
        )
    ),

    /**
     * The Chefs Delight Keg drink.
     */
    CHEFS_DELIGHT_KEG(
        Drink(
            intArrayOf(5833, 5831, 5829, 5827, 5769),
            MultiEffect(
                SkillEffect(Skills.COOKING, 1.0, 0.05),
                HealingEffect(1),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Chefs Delight M drink.
     */
    CHEFS_DELIGHT_M(
        Drink(
            intArrayOf(5757, 1919),
            MultiEffect(
                SkillEffect(Skills.COOKING, 2.0, 0.05),
                HealingEffect(2),
                SkillEffect(Skills.ATTACK, -3.0, 0.0),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0)
            )
        )
    ),

    /**
     * The Chefs Delight M Keg drink.
     */
    CHEFS_DELIGHT_M_KEG(
        Drink(
            intArrayOf(5913, 5911, 5909, 5907, 5769),
            MultiEffect(
                SkillEffect(Skills.COOKING, 2.0, 0.05),
                HealingEffect(2),
                SkillEffect(Skills.ATTACK, -3.0, 0.0),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Cider drink.
     */
    CIDER(
        Drink(
            intArrayOf(5763, 1919),
            MultiEffect(
                MultiEffect(
                    HealingEffect(2),
                    SkillEffect(Skills.FARMING, 1.0, 0.0),
                    SkillEffect(Skills.ATTACK, -2.0, 0.0),
                    SkillEffect(Skills.STRENGTH, -2.0, 0.0)
                )
            )
        )
    ),

    /**
     * The Cider Keg drink.
     */
    CIDER_KEG(
        Drink(
            intArrayOf(5849, 5847, 5845, 5843, 5769),
            MultiEffect(
                MultiEffect(
                    HealingEffect(2),
                    SkillEffect(Skills.FARMING, 1.0, 0.0),
                    SkillEffect(Skills.ATTACK, -2.0, 0.0),
                    SkillEffect(Skills.STRENGTH, -2.0, 0.0)
                )
            ),
            Animation(2289)
        )
    ),

    /**
     * The Mature Cider drink.
     */
    MATURE_CIDER(
        Drink(
            intArrayOf(5765, 1919),
            MultiEffect(
                MultiEffect(
                    HealingEffect(2),
                    SkillEffect(Skills.FARMING, 2.0, 0.0),
                    SkillEffect(Skills.ATTACK, -5.0, 0.0),
                    SkillEffect(Skills.STRENGTH, -5.0, 0.0)
                )
            )
        )
    ),

    /**
     * The Cider M Keg drink.
     */
    CIDER_M_KEG(
        Drink(
            intArrayOf(5929, 5927, 5925, 5923, 5769),
            MultiEffect(
                MultiEffect(
                    HealingEffect(2),
                    SkillEffect(Skills.FARMING, 2.0, 0.0),
                    SkillEffect(Skills.ATTACK, -5.0, 0.0),
                    SkillEffect(Skills.STRENGTH, -5.0, 0.0)
                )
            ),
            Animation(2289)
        )
    ),

    /**
     * The Dragon Bitter drink.
     */
    DRAGON_BITTER(
        Drink(
            intArrayOf(1911, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.STRENGTH, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            ),
            "You drink the Dragon Bitter. You feel slightly reinvigorated...",
            "...and slightly dizzy too."
        )
    ),

    /**
     * The Dragon Bitter Keg drink.
     */
    DRAGON_BITTER_KEG(
        Drink(
            intArrayOf(5809, 5807, 5805, 5803, 5769),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.STRENGTH, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Dragon Bitter M drink.
     */
    DRAGON_BITTER_M(
        Drink(
            intArrayOf(5745, 1919),
            MultiEffect(HealingEffect(2), SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * The Dragon Bitter M Keg drink.
     */
    DRAGON_BITTER_M_KEG(
        Drink(
            intArrayOf(5889, 5887, 5885, 5883, 5769),
            MultiEffect(
                HealingEffect(2),
                SkillEffect(Skills.STRENGTH, 3.0, 0.0),
                SkillEffect(Skills.ATTACK, -6.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Dwarven Stout drink.
     */
    DWARVEN_STOUT(
        Drink(
            intArrayOf(1913, 1919),
            MultiEffect(
                SkillEffect(Skills.MINING, 1.0, 0.0),
                SkillEffect(Skills.SMITHING, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0),
                SkillEffect(Skills.DEFENCE, -2.0, 0.0),
                HealingEffect(1)
            ),
            "You drink the Dwarven Stout. It tastes foul.",
            "It tastes pretty strong too."
        )
    ),

    /**
     * The Dwarven Stout Keg drink.
     */
    DWARVEN_STOUT_KEG(
        Drink(
            intArrayOf(5777, 5775, 5773, 5771, 5769),
            MultiEffect(
                SkillEffect(Skills.MINING, 1.0, 0.0),
                SkillEffect(Skills.SMITHING, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0),
                SkillEffect(Skills.DEFENCE, -2.0, 0.0),
                HealingEffect(1)
            ),
            Animation(2289),
            "You drink the Dwarven Stout. It tastes foul.",
            "It tastes pretty strong too."
        )
    ),

    /**
     * The Dwarven Stout M drink.
     */
    DWARVEN_STOUT_M(
        Drink(
            intArrayOf(5747, 1919),
            MultiEffect(
                SkillEffect(Skills.MINING, 2.0, 0.0),
                SkillEffect(Skills.SMITHING, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -7.0, 0.0),
                SkillEffect(Skills.STRENGTH, -7.0, 0.0),
                SkillEffect(Skills.DEFENCE, -7.0, 0.0),
                HealingEffect(1)
            )
        )
    ),

    /**
     * The Dwarven Stout M Keg drink.
     */
    DWARVEN_STOUT_M_KEG(
        Drink(
            intArrayOf(5857, 5855, 5853, 5851, 5769),
            MultiEffect(
                SkillEffect(Skills.MINING, 2.0, 0.0),
                SkillEffect(Skills.SMITHING, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -7.0, 0.0),
                SkillEffect(Skills.STRENGTH, -7.0, 0.0),
                SkillEffect(Skills.DEFENCE, -7.0, 0.0),
                HealingEffect(1)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Greenmans Ale drink.
     */
    GREENMANS_ALE(
        Drink(
            intArrayOf(1909, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.HERBLORE, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, -3.0, 0.0),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0),
                SkillEffect(Skills.DEFENCE, -3.0, 0.0)
            )
        )
    ),

    /**
     * The Greenmans Ale Keg drink.
     */
    GREENMANS_ALE_KEG(
        Drink(
            intArrayOf(5793, 5791, 5789, 5787, 5769),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.HERBLORE, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, -3.0, 0.0),
                SkillEffect(Skills.STRENGTH, -3.0, 0.0),
                SkillEffect(Skills.DEFENCE, -3.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Greenmans Ale M drink.
     */
    GREENMANS_ALE_M(
        Drink(
            intArrayOf(5743, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.HERBLORE, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            )
        )
    ),

    /**
     * The Greenmans Ale M Keg drink.
     */
    GREENMANS_ALE_M_KEG(
        Drink(
            intArrayOf(5873, 5871, 5869, 5867, 5769),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.HERBLORE, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Grog drink.
     */
    GROG(
        Drink(
            intArrayOf(1915, 1919),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * The Moonlight Mead drink.
     */
    MOONLIGHT_MEAD(
        Drink(
            intArrayOf(2955, 1919),
            HealingEffect(4),
            "It tastes like something just died in your mouth."
        )
    ),

    /**
     * The Moonlight Mead Keg drink.
     */
    MOONLIGHT_MEAD_KEG(
        Drink(
            intArrayOf(5817, 5815, 5813, 5811, 5769),
            HealingEffect(4),
            Animation(2289),
            "It tastes like something just died in your mouth."
        )
    ),

    /**
     * The Moonlight Mead M drink.
     */
    MOONLIGHT_MEAD_M(Drink(intArrayOf(5749, 1919), HealingEffect(6))),

    /**
     * The Moonlight Mead M Keg drink.
     */
    MOONLIGHT_MEAD_M_KEG(Drink(intArrayOf(5897, 5895, 5893, 5891, 5769), HealingEffect(6), Animation(2289))),

    /**
     * The Slayers Respite drink.
     */
    SLAYERS_RESPITE(
        Drink(
            intArrayOf(5759, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.SLAYER, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            )
        )
    ),

    /**
     * The Slayers Respite Keg drink.
     */
    SLAYERS_RESPITE_KEG(
        Drink(
            intArrayOf(5841, 5839, 5837, 5835, 5769),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.SLAYER, 2.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Slayers Respite M drink.
     */
    SLAYERS_RESPITE_M(
        Drink(
            intArrayOf(5761, 1919),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.SLAYER, 4.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            )
        )
    ),

    /**
     * The Slayers Respite M Keg drink.
     */
    SLAYERS_RESPITE_M_KEG(
        Drink(
            intArrayOf(5841, 5839, 5837, 5835, 5769),
            MultiEffect(
                HealingEffect(1),
                SkillEffect(Skills.SLAYER, 4.0, 0.0),
                SkillEffect(Skills.ATTACK, -2.0, 0.0),
                SkillEffect(Skills.STRENGTH, -2.0, 0.0)
            ),
            Animation(2289)
        )
    ),

    /**
     * The Wizards Mind Bomb drink.
     */
    WIZARDS_MIND_BOMB(
        Drink(
            intArrayOf(1907, 1919),
            WizardsMindBombEffect(),
            "You drink the Wizard's Mind Bomb.",
            "You feel very strange."
        )
    ),

    /**
     * The Mature Wmb drink.
     */
    MATURE_WMB(Drink(intArrayOf(5741, 1919), MatureWmbEffect())),

    /**
     * The Fruit Blast drink.
     */
    FRUIT_BLAST(Drink(intArrayOf(2084, 2026), HealingEffect(9))),

    /**
     * The Premade Fr Blast drink.
     */
    PREMADE_FR_BLAST(Drink(intArrayOf(2034, 2026), HealingEffect(9))),

    /**
     * The Crafted Fr Blast drink.
     */
    CRAFTED_FR_BLAST(Drink(intArrayOf(9514, 2026), HealingEffect(9))),

    /**
     * The Pineapple Punch drink.
     */
    PINEAPPLE_PUNCH(Drink(intArrayOf(2048, 2026), HealingEffect(9), "You drink the cocktail. It tastes great.")),

    /**
     * The Premade P Punch drink.
     */
    PREMADE_P_PUNCH(Drink(intArrayOf(2036, 2026), HealingEffect(9), "You drink the cocktail. It tastes great.")),

    /**
     * The Crafted P Punch drink.
     */
    CRAFTED_P_PUNCH(Drink(intArrayOf(9512, 2026), HealingEffect(9))),

    /**
     * The Wizard Blizzard drink.
     */
    WIZARD_BLIZZARD(
        Drink(
            intArrayOf(2054, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Premade Wiz Blzd drink.
     */
    PREMADE_WIZ_BLZD(
        Drink(
            intArrayOf(2040, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Crafted Wiz Blzd drink.
     */
    CRAFTED_WIZ_BLZD(
        Drink(
            intArrayOf(9508, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Short Green Guy drink.
     */
    SHORT_GREEN_GUY(
        Drink(
            intArrayOf(2080, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * The Premade Sgg drink.
     */
    PREMADE_SGG(
        Drink(
            intArrayOf(2038, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * The Crafted Sgg drink.
     */
    CRAFTED_SGG(
        Drink(
            intArrayOf(9510, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * The Drunk Dragon drink.
     */
    DRUNK_DRAGON(
        Drink(
            intArrayOf(2092, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Premade Dr Dragon drink.
     */
    PREMADE_DR_DRAGON(
        Drink(
            intArrayOf(2032, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Crafted Dr Dragon drink.
     */
    CRAFTED_DR_DRAGON(
        Drink(
            intArrayOf(9516, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Choc Saturday drink.
     */
    CHOC_SATURDAY(
        Drink(
            intArrayOf(2074, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Premade Choc Sdy drink.
     */
    PREMADE_CHOC_SDY(
        Drink(
            intArrayOf(2030, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Crafted Choc Sdy drink.
     */
    CRAFTED_CHOC_SDY(
        Drink(
            intArrayOf(9518, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Blurberry Special drink.
     */
    BLURBERRY_SPECIAL(
        Drink(
            intArrayOf(2064, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Premade Blurb Sp drink.
     */
    PREMADE_BLURB_SP(
        Drink(
            intArrayOf(2028, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Crafted Blurb Sp drink.
     */
    CRAFTED_BLURB_SP(
        Drink(
            intArrayOf(9520, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Karamjan Rum drink.
     */
    KARAMJAN_RUM(
        Drink(
            intArrayOf(431),
            MultiEffect(
                HealingEffect(5),
                SkillEffect(Skills.STRENGTH, 5.0, 0.0),
                SkillEffect(Skills.ATTACK, -4.0, 0.0)
            ),
            Animation(1194)
        )
    ),

    /**
     * The Braindeath Rum drink.
     */
    BRAINDEATH_RUM(
        Drink(
            intArrayOf(7157),
            MultiEffect(
                SkillEffect(Skills.DEFENCE, 0.0, -0.1),
                SkillEffect(Skills.ATTACK, 0.0, -0.05),
                SkillEffect(Skills.PRAYER, 0.0, -0.05),
                SkillEffect(Skills.RANGE, 0.0, -0.05),
                SkillEffect(Skills.MAGIC, 0.0, -0.05),
                SkillEffect(Skills.HERBLORE, 0.0, -0.05),
                SkillEffect(Skills.STRENGTH, 3.0, 0.0),
                SkillEffect(Skills.MINING, 1.0, 0.0)
            ),
            "With a sense of impending doom you drink the 'rum'. You try very hard not to die."
        )
    ),

    /**
     * The Rum Trouble Brewing Red drink.
     */
    RUM_TROUBLE_BREWING_RED(
        Drink(
            intArrayOf(8940, 8940),
            TroubleBrewingRumEffect("Oh gods! It tastes like burning!"),
            Animation(9605)
        )
    ),

    /**
     * The Rum Trouble Brewing Blue drink.
     */
    RUM_TROUBLE_BREWING_BLUE(
        Drink(
            intArrayOf(8941, 8941),
            TroubleBrewingRumEffect("My Liver! My Liver is melting!"),
            Animation(9604)
        )
    ),

    /**
     * The Vodka drink.
     */
    VODKA(
        Drink(
            intArrayOf(2015),
            MultiEffect(HealingEffect(2), SkillEffect(Skills.ATTACK, -4.0, 0.0), SkillEffect(Skills.STRENGTH, 4.0, 0.0))
        )
    ),

    /**
     * The Gin drink.
     */
    GIN(
        Drink(
            intArrayOf(2019),
            MultiEffect(
                SkillEffect(Skills.STRENGTH, 1.0, 0.0),
                SkillEffect(Skills.ATTACK, 4.0, 0.0),
                RandomHealthEffect(3, 4)
            )
        )
    ),

    /**
     * The Brandy drink.
     */
    BRANDY(Drink(intArrayOf(2021), MultiEffect(HealingEffect(5), SkillEffect(Skills.ATTACK, 4.0, 0.0)))),

    /**
     * The Whisky drink.
     */
    WHISKY(
        Drink(
            intArrayOf(2017),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * The Bottle Of Wine drink.
     */
    BOTTLE_OF_WINE(
        Drink(
            intArrayOf(7919, 7921),
            MultiEffect(HealingEffect(14), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * The Jug Of Wine drink.
     */
    JUG_OF_WINE(Drink(intArrayOf(1993, 1935), MultiEffect(HealingEffect(11), SkillEffect(Skills.ATTACK, -2.0, 0.0)))),

    /**
     * The Half Full Wine Jug drink.
     */
    HALF_FULL_WINE_JUG(Drink(intArrayOf(1989, 1935), HealingEffect(7))),

    /**
     * The Jug Of Bad Wine drink.
     */
    JUG_OF_BAD_WINE(Drink(intArrayOf(1991, 1935), SkillEffect(Skills.ATTACK, -3.0, 0.0))),

    /**
     * The Cup Of Tea drink.
     */
    CUP_OF_TEA(
        Drink(
            intArrayOf(712, 1980),
            MultiEffect(HealingEffect(3), SkillEffect(Skills.ATTACK, 3.0, 0.0)),
            "Aaah, nothing like a nice cuppa tea!"
        )
    ),

    /**
     * The Cup Of Tea Nettle drink.
     */
    CUP_OF_TEA_NETTLE(Drink(intArrayOf(4242, 1980), EnergyEffect(10))),

    /**
     * The Cup Of Tea Milky Nettle drink.
     */
    CUP_OF_TEA_MILKY_NETTLE(Drink(intArrayOf(4243, 1980), EnergyEffect(10))),

    /**
     * The Nettle Water drink.
     */
    NETTLE_WATER(Drink(intArrayOf(4237, 1923), HealingEffect(1))),

    /**
     * The Nettle Tea drink.
     */
    NETTLE_TEA(Drink(intArrayOf(4239, 1923), NettleTeaEffect())),

    /**
     * The Nettle Tea Milky drink.
     */
    NETTLE_TEA_MILKY(Drink(intArrayOf(4240, 1923), NettleTeaEffect())),

    /**
     * The Nettle Tea Porcelain drink.
     */
    NETTLE_TEA_PORCELAIN(Drink(intArrayOf(4245, 4244), NettleTeaEffect())),

    /**
     * The Nettle Tea Milky Porcelain drink.
     */
    NETTLE_TEA_MILKY_PORCELAIN(Drink(intArrayOf(4246, 4244), NettleTeaEffect())),

    /**
     * The Cup Of Tea Clay drink.
     */
    CUP_OF_TEA_CLAY(
        Drink(
            intArrayOf(7730, 7728),
            SkillEffect(Skills.CONSTRUCTION, 1.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * The Cup Of Tea Clay Milky drink.
     */
    CUP_OF_TEA_CLAY_MILKY(Drink(intArrayOf(7731, 7728), SkillEffect(Skills.CONSTRUCTION, 1.0, 0.0))),

    /**
     * The Cup Of Tea White drink.
     */
    CUP_OF_TEA_WHITE(
        Drink(
            intArrayOf(7733, 7732),
            SkillEffect(Skills.CONSTRUCTION, 2.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * The Cup Of Tea White Milky drink.
     */
    CUP_OF_TEA_WHITE_MILKY(Drink(intArrayOf(7734, 7732), SkillEffect(Skills.CONSTRUCTION, 2.0, 0.0))),

    /**
     * The Cup Of Tea Gold drink.
     */
    CUP_OF_TEA_GOLD(
        Drink(
            intArrayOf(7736, 7735),
            SkillEffect(Skills.CONSTRUCTION, 3.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * The Cup Of Tea Gold Milky drink.
     */
    CUP_OF_TEA_GOLD_MILKY(Drink(intArrayOf(7737, 7735), SkillEffect(Skills.CONSTRUCTION, 3.0, 0.0))),

    /**
     * The Chocolate Bar food.
     */
    CHOCOLATE_BAR(Food(intArrayOf(1973), HealingEffect(3))),

    /**
     * The Purple Sweets food.
     */
    PURPLE_SWEETS(Food(intArrayOf(4561), HealingEffect(0))),

    /**
     * The Purple Sweets Stackable
     */
    PURPLE_SWEETS_STACKABLE(
        Food(
            intArrayOf(10476),
            MultiEffect(EnergyEffect(10), RandomHealthEffect(1, 3)),
            "The sugary goodness heals some energy.",
            "The sugary goodness is yummy."
        )
    ),

    /**
     * The Field Ration food.
     */
    FIELD_RATION(Food(intArrayOf(7934), HealingEffect(10))),

    /**
     * The Roll food.
     */
    ROLL(Food(intArrayOf(6963), HealingEffect(6))),

    /**
     * The Tchiki Monkey Nuts food.
     */
    TCHIKI_MONKEY_NUTS(Food(intArrayOf(7573), HealingEffect(5), "You eat the Tchiki monkey nuts. They taste nutty.")),

    /**
     * The Tchiki Monkey Paste food.
     */
    TCHIKI_MONKEY_PASTE(
        Food(
            intArrayOf(7575),
            HealingEffect(5),
            "You eat the Tchiki monkey nut paste. It sticks to the roof of your mouth."
        )
    ),

    /**
     * The Oomlie Wrap food.
     */
    OOMLIE_WRAP(
        Food(
            intArrayOf(Items.COOKED_OOMLIE_WRAP_2343),
            MultiEffect(HealingEffect(14), AchievementEffect(DiaryType.KARAMJA, 2, 2))
        )
    ),

    /**
     * The Roe food.
     */
    ROE(Food(intArrayOf(11324), HealingEffect(3))),

    /**
     * The Equa Leaves food.
     */
    EQUA_LEAVES(Food(intArrayOf(2128), HealingEffect(1), "You eat the leaves; chewy but tasty.")),

    /**
     * The Choc Ice food.
     */
    CHOC_ICE(Food(intArrayOf(6794), HealingEffect(6))),

    /**
     * The Edible Seaweed food.
     */
    EDIBLE_SEAWEED(Food(intArrayOf(403), HealingEffect(4))),

    /**
     * The Frog Spawn food.
     */
    FROG_SPAWN(Food(intArrayOf(5004), RandomHealthEffect(3, 7), "You eat the frogspawn. Yuck.")),

    /**
     * The Pumpkin food.
     */
    PUMPKIN(Food(intArrayOf(1959), HealingEffect(14))),

    /**
     * The Easter Egg.
     */
    EASTER_EGG(Food(intArrayOf(1961), HealingEffect(14))),

    /**
     * The Strength potion.
     */
    STRENGTH(Potion(intArrayOf(113, 115, 117, 119), SkillEffect(Skills.STRENGTH, 3.0, 0.1))),

    /**
     * The Attack potion.
     */
    ATTACK(Potion(intArrayOf(2428, 121, 123, 125), SkillEffect(Skills.ATTACK, 3.0, 0.1))),

    /**
     * The Defence potion.
     */
    DEFENCE(Potion(intArrayOf(2432, 133, 135, 137), SkillEffect(Skills.DEFENCE, 3.0, 0.1))),

    /**
     * The Ranging potion.
     */
    RANGING(Potion(intArrayOf(2444, 169, 171, 173), SkillEffect(Skills.RANGE, 4.0, 0.1))),

    /**
     * The Magic potion.
     */
    MAGIC(Potion(intArrayOf(3040, 3042, 3044, 3046), SkillEffect(Skills.MAGIC, 4.0, 0.1))),

    /**
     * The Super Strength potion.
     */
    SUPER_STRENGTH(Potion(intArrayOf(2440, 157, 159, 161), SkillEffect(Skills.STRENGTH, 5.0, 0.15))),

    /**
     * The Super Attack potion.
     */
    SUPER_ATTACK(Potion(intArrayOf(2436, 145, 147, 149), SkillEffect(Skills.ATTACK, 5.0, 0.15))),

    /**
     * The Super Defence potion.
     */
    SUPER_DEFENCE(Potion(intArrayOf(2442, 163, 165, 167), SkillEffect(Skills.DEFENCE, 5.0, 0.15))),

    /**
     * The Antipoison potion.
     */
    ANTIPOISON(Potion(intArrayOf(2446, 175, 177, 179), AddTimerEffect("poison:immunity", secondsToTicks(90)))),

    /**
     * The Antipoison potion.
     */
    ANTIPOISON_(Potion(intArrayOf(5943, 5945, 5947, 5949), AddTimerEffect("poison:immunity", minutesToTicks(9)))),

    /**
     * The Antipoison potion.
     */
    ANTIPOISON__(Potion(intArrayOf(5952, 5954, 5956, 5958), AddTimerEffect("poison:immunity", minutesToTicks(12)))),

    /**
     * The Super Antipoison.
     */
    SUPER_ANTIP(Potion(intArrayOf(2448, 181, 183, 185), AddTimerEffect("poison:immunity", minutesToTicks(6)))),

    /**
     * The Relicym potion.
     */
    RELICYM(
        Potion(
            intArrayOf(4842, 4844, 4846, 4848),
            MultiEffect(SetAttributeEffect("disease:immunity", 300), RemoveTimerEffect("disease"))
        )
    ),

    /**
     * The Agility potion.
     */
    AGILITY(Potion(intArrayOf(3032, 3034, 3036, 3038), SkillEffect(Skills.AGILITY, 3.0, 0.0))),

    /**
     * The Hunter potion.
     */
    HUNTER(Potion(intArrayOf(9998, 10000, 10002, 10004), SkillEffect(Skills.HUNTER, 3.0, 0.0))),

    /**
     * The Restore potion.
     */
    RESTORE(Potion(intArrayOf(2430, 127, 129, 131), RestoreEffect(10.0, 0.3))),

    /**
     * The Saradomin brew potion.
     */
    SARA_BREW(
        Potion(
            intArrayOf(6685, 6687, 6689, 6691),
            MultiEffect(
                PercentHeal(0, .15),
                SkillEffect(Skills.ATTACK, 0.0, -0.10),
                SkillEffect(Skills.STRENGTH, 0.0, -0.10),
                SkillEffect(Skills.MAGIC, 0.0, -0.10),
                SkillEffect(Skills.RANGE, 0.0, -0.10),
                SkillEffect(Skills.DEFENCE, 0.0, 0.25)
            )
        )
    ),

    /**
     * The Summoning potion.
     */
    SUMMONING(
        Potion(
            intArrayOf(12140, 12142, 12144, 12146),
            MultiEffect(RestoreSummoningSpecial(), SummoningEffect(7.0, 0.25))
        )
    ),

    /**
     * The Combat potion.
     */
    COMBAT(
        Potion(
            intArrayOf(9739, 9741, 9743, 9745),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, .1), SkillEffect(Skills.ATTACK, 3.0, .1))
        )
    ),

    /**
     * The Energy potion.
     */
    ENERGY(Potion(intArrayOf(3008, 3010, 3012, 3014), MultiEffect(EnergyEffect(10)))),

    /**
     * The Fishing potion.
     */
    FISHING(Potion(intArrayOf(2438, 151, 153, 155), SkillEffect(Skills.FISHING, 3.0, 0.0))),

    /**
     * The Prayer potion.
     */
    PRAYER(Potion(intArrayOf(2434, 139, 141, 143), PrayerEffect(7.0, 0.25))),

    /**
     * The Super Restore potion.
     */
    SUPER_RESTO(
        Potion(
            intArrayOf(3024, 3026, 3028, 3030),
            MultiEffect(RestoreEffect(8.0, 0.25, true), PrayerEffect(8.0, 0.25), SummoningEffect(8.0, 0.25))
        )
    ),

    /**
     * The Zammy Brew potion.
     */
    ZAMMY_BREW(
        Potion(
            intArrayOf(2450, 189, 191, 193),
            MultiEffect(
                DamageEffect(10.0, true),
                SkillEffect(Skills.ATTACK, 0.0, 0.25),
                SkillEffect(Skills.STRENGTH, 0.0, 0.15),
                SkillEffect(Skills.DEFENCE, 0.0, -0.1),
                RandomPrayerEffect(0, 10)
            )
        )
    ),

    /**
     * The Antifire potion.
     */
    ANTIFIRE(Potion(intArrayOf(2452, 2454, 2456, 2458), SetAttributeEffect("fire:immune", 600, true))),

    /**
     * The Guth Rest potion.
     */
    GUTH_REST(
        Potion(
            intArrayOf(4417, 4419, 4421, 4423),
            MultiEffect(RemoveTimerEffect("poison"), EnergyEffect(5), HealingEffect(5))
        )
    ),

    /**
     * The Magic Ess potion.
     */
    MAGIC_ESS(Potion(intArrayOf(11491, 11489), SkillEffect(Skills.MAGIC, 3.0, 0.0))),

    /**
     * The Sanfew potion.
     */
    SANFEW(
        Potion(
            intArrayOf(10925, 10927, 10929, 10931),
            MultiEffect(
                RestoreEffect(8.0, 0.25, true),
                AddTimerEffect("poison:immunity", secondsToTicks(90), RemoveTimerEffect("disease"))
            )
        )
    ),

    /**
     * The Super Energy potion.
     */
    SUPER_ENERGY(Potion(intArrayOf(3016, 3018, 3020, 3022), EnergyEffect(20))),

    /**
     * The Blamish Oil.
     */
    BLAMISH_OIL(FakeConsumable(1582, arrayOf("You know... I'd really rather not."))),

    /**
     * The Prayer mix.
     */
    PRAYERMIX(BarbarianMix(intArrayOf(11465, 11467), MultiEffect(PrayerEffect(7.0, 0.25), HealingEffect(6)))),

    /**
     * The Zammy Mix.
     */
    ZAMMY_MIX(
        BarbarianMix(
            intArrayOf(11521, 11523),
            MultiEffect(
                DamageEffect(10.0, true),
                SkillEffect(Skills.ATTACK, 0.0, 0.15),
                SkillEffect(Skills.STRENGTH, 0.0, 0.25),
                SkillEffect(Skills.DEFENCE, 0.0, -0.1),
                RandomPrayerEffect(0, 10)
            )
        )
    ),

    /**
     * The Att Mix.
     */
    ATT_MIX(
        BarbarianMix(
            intArrayOf(11429, 11431),
            MultiEffect(SkillEffect(Skills.ATTACK, 3.0, 0.1), HealingEffect(3))
        )
    ),

    /**
     * The Antip Mix.
     */
    ANTIP_MIX(
        BarbarianMix(
            intArrayOf(11433, 11435),
            MultiEffect(AddTimerEffect("poison:immunity", secondsToTicks(90)), HealingEffect(3))
        )
    ),

    /**
     * The Relic Mix.
     */
    RELIC_MIX(
        BarbarianMix(
            intArrayOf(11437, 11439),
            MultiEffect(RemoveTimerEffect("disease"), SetAttributeEffect("disease:immunity", 300), HealingEffect(3))
        )
    ),

    /**
     * The Str Mix.
     */
    STR_MIX(
        BarbarianMix(
            intArrayOf(11443, 11441),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.1), HealingEffect(3))
        )
    ),

    /**
     * The Resto Mix.
     */
    RESTO_MIX(BarbarianMix(intArrayOf(11449, 11451), MultiEffect(RestoreEffect(10.0, 0.3), HealingEffect(3)))),

    /**
     * The Super Resto Mix.
     */
    SUPER_RESTO_MIX(
        BarbarianMix(
            intArrayOf(11493, 11495),
            MultiEffect(RestoreEffect(8.0, 0.25), PrayerEffect(8.0, 0.25), SummoningEffect(8.0, 0.25), HealingEffect(6))
        )
    ),

    /**
     * The Energy Mix.
     */
    ENERGY_MIX(BarbarianMix(intArrayOf(11453, 11455), MultiEffect(EnergyEffect(10), HealingEffect(6)))),

    /**
     * The Def Mix.
     */
    DEF_MIX(
        BarbarianMix(
            intArrayOf(11457, 11459),
            MultiEffect(SkillEffect(Skills.DEFENCE, 3.0, 0.1), HealingEffect(6))
        )
    ),

    /**
     * The Agil Mix.
     */
    AGIL_MIX(
        BarbarianMix(
            intArrayOf(11461, 11463),
            MultiEffect(SkillEffect(Skills.AGILITY, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * The Combat Mix.
     */
    COMBAT_MIX(
        BarbarianMix(
            intArrayOf(11445, 11447),
            MultiEffect(SkillEffect(Skills.ATTACK, 3.0, 0.1), SkillEffect(Skills.STRENGTH, 3.0, 0.1), HealingEffect(6))
        )
    ),

    /**
     * The Super Att Mix.
     */
    SUPER_ATT_MIX(
        BarbarianMix(
            intArrayOf(11469, 11471),
            MultiEffect(SkillEffect(Skills.ATTACK, 5.0, 0.15), HealingEffect(6))
        )
    ),

    /**
     * The Fish Mix.
     */
    FISH_MIX(
        BarbarianMix(
            intArrayOf(11477, 11479),
            MultiEffect(SkillEffect(Skills.FISHING, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * The Super Energy Mix.
     */
    SUPER_ENERGY_MIX(BarbarianMix(intArrayOf(11481, 11483), MultiEffect(EnergyEffect(20), HealingEffect(6)))),

    /**
     * The Hunting Mix.
     */
    HUNTING_MIX(
        BarbarianMix(
            intArrayOf(11517, 11519),
            MultiEffect(SkillEffect(Skills.HUNTER, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * The Super Str Mix.
     */
    SUPER_STR_MIX(
        BarbarianMix(
            intArrayOf(11485, 11487),
            MultiEffect(SkillEffect(Skills.STRENGTH, 5.0, 0.15), HealingEffect(6))
        )
    ),

    /**
     * The Antidote Plus Mix.
     */
    ANTIDOTE_PLUS_MIX(
        BarbarianMix(
            intArrayOf(11501, 11503),
            MultiEffect(AddTimerEffect("poison:immunity", minutesToTicks(9)), RandomHealthEffect(3, 7))
        )
    ),

    /**
     * The Antip Supermix.
     */
    ANTIP_SUPERMIX(
        BarbarianMix(
            intArrayOf(11473, 11475),
            MultiEffect(AddTimerEffect("poison:immunity", minutesToTicks(6)), RandomHealthEffect(3, 7))
        )
    ),

    /**
     * The Sc Prayer potion.
     */
    SC_PRAYER(Potion(intArrayOf(14207, 14209, 14211, 14213, 14215), PrayerEffect(7.0, 0.25))),

    /**
     * The Sc Energy potion.
     */
    SC_ENERGY(Potion(intArrayOf(14217, 14219, 14221, 14223, 14225), EnergyEffect(20))),

    /**
     * The Sc Attack potion.
     */
    SC_ATTACK(Potion(intArrayOf(14227, 14229, 14231, 14233, 14235), SkillEffect(Skills.ATTACK, 3.0, 0.2))),

    /**
     * The Sc Strength potion.
     */
    SC_STRENGTH(Potion(intArrayOf(14237, 14239, 14241, 14243, 14245), SkillEffect(Skills.STRENGTH, 3.0, 0.2))),

    /**
     * The Sc Range potion.
     */
    SC_RANGE(Potion(intArrayOf(14247, 14249, 14251, 14253, 14255), SkillEffect(Skills.RANGE, 3.0, 0.1))),

    /**
     * The Sc Defence potion.
     */
    SC_DEFENCE(Potion(intArrayOf(14257, 14259, 14261, 14263, 14265), SkillEffect(Skills.DEFENCE, 3.0, 0.1))),

    /**
     * The Sc Magic potion.
     */
    SC_MAGIC(Potion(intArrayOf(14267, 14269, 14271, 14273, 14275), SkillEffect(Skills.MAGIC, 3.0, 0.1))),

    /**
     * The Sc Summoning potion.
     */
    SC_SUMMONING(Potion(intArrayOf(14277, 14279, 14281, 14283, 14285), SummoningEffect(7.0, 0.25)));

    @JvmField
    val consumable: Consumable
    var isIgnoreMainClock: Boolean = false

    constructor(consumable: Consumable) {
        this.consumable = consumable
    }

    constructor(consumable: Consumable, isIgnoreMainClock: Boolean) {
        this.consumable = consumable
        this.isIgnoreMainClock = isIgnoreMainClock
    }

    companion object {
        var consumables: HashMap<Int, Consumables> = HashMap()
        var potions: ArrayList<Int> = ArrayList()

        @JvmStatic
        fun getConsumableById(itemId: Int): Consumables? {
            return consumables[itemId]
        }

        fun add(consumable: Consumables) {
            for (id in consumable.consumable.ids) {
                consumables.putIfAbsent(id, consumable)
            }
        }

        init {
            for (consumable in values()) {
                add(consumable)
                if (consumable.consumable is Potion) {
                    for (pot in consumable.consumable.ids) {
                        potions.add(pot)
                    }
                }
            }
        }
    }
}
