package content.data.consumables

import content.data.consumables.effects.*
import core.api.consts.Items
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
     * Bat Shish
     *
     * @constructor Bat Shish
     */
    BAT_SHISH(Food(intArrayOf(Items.BAT_SHISH_10964), HealingEffect(2))),

    /**
     * Fingers
     *
     * @constructor Fingers
     */
    FINGERS(Food(intArrayOf(Items.FINGERS_10965), HealingEffect(2))),

    /**
     * Coated Frog Legs
     *
     * @constructor Coated Frog Legs
     */
    COATED_FROG_LEGS(Food(intArrayOf(Items.COATED_FROGS_LEGS_10963), HealingEffect(2))),

    /**
     * Cooked Meat
     *
     * @constructor Cooked Meat
     */
    COOKED_MEAT(Food(intArrayOf(Items.COOKED_MEAT_2142), HealingEffect(3))),

    /**
     * Shrimps
     *
     * @constructor Shrimps
     */
    SHRIMPS(Food(intArrayOf(Items.SHRIMPS_315), HealingEffect(3))),

    /**
     * Cooked Chicken
     *
     * @constructor Cooked Chicken
     */
    COOKED_CHICKEN(Food(intArrayOf(Items.COOKED_CHICKEN_2140), HealingEffect(3))),

    /**
     * Cooked Rabbit
     *
     * @constructor Cooked Rabbit
     */
    COOKED_RABBIT(Food(intArrayOf(Items.COOKED_RABBIT_3228), HealingEffect(5))),

    /**
     * Anchovies
     *
     * @constructor Anchovies
     */
    ANCHOVIES(Food(intArrayOf(Items.ANCHOVIES_319), HealingEffect(1))),

    /**
     * Sardine
     *
     * @constructor Sardine
     */
    SARDINE(Food(intArrayOf(Items.SARDINE_325), HealingEffect(4))),

    /**
     * Poison Karambwan
     *
     * @constructor Poison Karambwan
     */
    POISON_KARAMBWAN(Food(intArrayOf(Items.POISON_KARAMBWAN_3146), PoisonKarambwanEffect())),

    /**
     * Ugthanki Meat
     *
     * @constructor Ugthanki Meat
     */
    UGTHANKI_MEAT(Food(intArrayOf(Items.UGTHANKI_MEAT_1861), HealingEffect(3))),

    /**
     * Herring
     *
     * @constructor Herring
     */
    HERRING(Food(intArrayOf(Items.HERRING_347), HealingEffect(5))),

    /**
     * Mackerel
     *
     * @constructor Mackerel
     */
    MACKEREL(Food(intArrayOf(Items.MACKEREL_355), HealingEffect(6))),

    /**
     * Roast Bird Meat
     *
     * @constructor Roast Bird Meat
     */
    ROAST_BIRD_MEAT(Food(intArrayOf(Items.ROAST_BIRD_MEAT_9980), HealingEffect(6))),

    /**
     * Thin Snail Meat
     *
     * @constructor Thin Snail Meat
     */
    THIN_SNAIL_MEAT(Food(intArrayOf(Items.THIN_SNAIL_MEAT_3369), HealingEffect(5))),

    /**
     * Trout
     *
     * @constructor Trout
     */
    TROUT(Food(intArrayOf(Items.TROUT_333), HealingEffect(7))),

    /**
     * Spider On Stick
     *
     * @constructor Spider On Stick
     */
    SPIDER_ON_STICK(Food(intArrayOf(Items.SPIDER_ON_STICK_6297), HealingEffect(7))),

    /**
     * Spider On Shaft
     *
     * @constructor Spider On Shaft
     */
    SPIDER_ON_SHAFT(Food(intArrayOf(Items.SPIDER_ON_SHAFT_6299), HealingEffect(7))),

    /**
     * Roast Rabbit
     *
     * @constructor Roast Rabbit
     */
    ROAST_RABBIT(Food(intArrayOf(Items.ROAST_RABBIT_7223), HealingEffect(7))),

    /**
     * Lean Snail Meat
     *
     * @constructor Lean Snail Meat
     */
    LEAN_SNAIL_MEAT(Food(intArrayOf(Items.LEAN_SNAIL_MEAT_3371), HealingEffect(8))),

    /**
     * Cod
     *
     * @constructor Cod
     */
    COD(Food(intArrayOf(Items.COD_339), HealingEffect(7))),

    /**
     * Pike
     *
     * @constructor Pike
     */
    PIKE(Food(intArrayOf(Items.PIKE_351), HealingEffect(8))),

    /**
     * Roast Beast Meat
     *
     * @constructor Roast Beast Meat
     */
    ROAST_BEAST_MEAT(Food(intArrayOf(Items.ROAST_BEAST_MEAT_9988), HealingEffect(8))),

    /**
     * Cooked Crab Meat
     *
     * @constructor Cooked Crab Meat
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
     * Fat Snail
     *
     * @constructor Fat Snail
     */
    FAT_SNAIL(Food(intArrayOf(Items.FAT_SNAIL_MEAT_3373), HealingEffect(9))),

    /**
     * Salmon
     *
     * @constructor Salmon
     */
    SALMON(Food(intArrayOf(Items.SALMON_329), HealingEffect(9))),

    /**
     * Slimy Eel
     *
     * @constructor Slimy Eel
     */
    SLIMY_EEL(Food(intArrayOf(Items.COOKED_SLIMY_EEL_3381), HealingEffect(6))),

    /**
     * Tuna
     *
     * @constructor Tuna
     */
    TUNA(Food(intArrayOf(Items.TUNA_361), HealingEffect(10))),

    /**
     * Cooked Karambwan
     *
     * @constructor Cooked Karambwan
     */
    COOKED_KARAMBWAN(Food(intArrayOf(Items.COOKED_KARAMBWAN_3144), HealingEffect(18)), true),

    /**
     * Cooked Chompy
     *
     * @constructor Cooked Chompy
     */
    COOKED_CHOMPY(Food(intArrayOf(Items.COOKED_CHOMPY_2878), HealingEffect(10))),

    /**
     * Rainbow Fish
     *
     * @constructor Rainbow Fish
     */
    RAINBOW_FISH(Food(intArrayOf(Items.RAINBOW_FISH_10136), HealingEffect(11))),

    /**
     * Cave Eel
     *
     * @constructor Cave Eel
     */
    CAVE_EEL(Food(intArrayOf(Items.CAVE_EEL_5003), HealingEffect(7))),

    /**
     * Caviar
     *
     * @constructor Caviar
     */
    CAVIAR(Food(intArrayOf(Items.CAVIAR_11326), HealingEffect(5))),

    /**
     * Lobster
     *
     * @constructor Lobster
     */
    LOBSTER(Food(intArrayOf(Items.LOBSTER_379), HealingEffect(12))),

    /**
     * Cooked Jubbly
     *
     * @constructor Cooked Jubbly
     */
    COOKED_JUBBLY(Food(intArrayOf(Items.COOKED_JUBBLY_7568), HealingEffect(15))),

    /**
     * Bass
     *
     * @constructor Bass
     */
    BASS(Food(intArrayOf(Items.BASS_365), HealingEffect(13))),

    /**
     * Swordfish
     *
     * @constructor Swordfish
     */
    SWORDFISH(Food(intArrayOf(Items.SWORDFISH_373), HealingEffect(14))),

    /**
     * Lava Eel
     *
     * @constructor Lava Eel
     */
    LAVA_EEL(Food(intArrayOf(Items.LAVA_EEL_2149), HealingEffect(14))),

    /**
     * Monkfish
     *
     * @constructor Monkfish
     */
    MONKFISH(Food(intArrayOf(Items.MONKFISH_7946), HealingEffect(16))),

    /**
     * Shark
     *
     * @constructor Shark
     */
    SHARK(Food(intArrayOf(Items.SHARK_385), HealingEffect(20))),

    /**
     * Sea Turtle
     *
     * @constructor Sea Turtle
     */
    SEA_TURTLE(Food(intArrayOf(Items.SEA_TURTLE_397), HealingEffect(21))),

    /**
     * Manta Ray
     *
     * @constructor Manta Ray
     */
    MANTA_RAY(Food(intArrayOf(Items.MANTA_RAY_391), HealingEffect(22))),

    /**
     * Karambwanji
     *
     * @constructor Karambwanji
     */
    KARAMBWANJI(Food(intArrayOf(Items.KARAMBWANJI_3151), HealingEffect(3))),

    /**
     * Stuffed Snake
     *
     * @constructor Stuffed Snake
     */
    STUFFED_SNAKE(
        Food(
            intArrayOf(Items.STUFFED_SNAKE_7579),
            HealingEffect(20),
            "You eat the stuffed snake-it's quite a meal! It tastes like chicken."
        )
    ),

    /**
     * Crayfish
     *
     * @constructor Crayfish
     */
    CRAYFISH(Food(intArrayOf(Items.CRAYFISH_13433), HealingEffect(2))),

    /**
     * Giant Frog Legs
     *
     * @constructor Giant Frog Legs
     */
    GIANT_FROG_LEGS(Food(intArrayOf(Items.GIANT_FROG_LEGS_4517), HealingEffect(6))),

    /**
     * Bread
     *
     * @constructor Bread
     */
    BREAD(Food(intArrayOf(Items.BREAD_2309), HealingEffect(5))),

    /**
     * Baguette
     *
     * @constructor Baguette
     */
    BAGUETTE(Food(intArrayOf(Items.BAGUETTE_6961), HealingEffect(6))),

    /**
     * Triangle Sandwich
     *
     * @constructor Triangle Sandwich
     */
    TRIANGLE_SANDWICH(Food(intArrayOf(Items.TRIANGLE_SANDWICH_6962), HealingEffect(6))),

    /**
     * Square Sandwich
     *
     * @constructor Square Sandwich
     */
    SQUARE_SANDWICH(Food(intArrayOf(Items.SQUARE_SANDWICH_6965), HealingEffect(6))),

    /**
     * Seaweed Sandwich
     *
     * @constructor Seaweed Sandwich
     */
    SEAWEED_SANDWICH(
        FakeConsumable(
            Items.SEAWEED_SANDWICH_3168,
            arrayOf("You really, really do not want to eat that.")
        )
    ),

    /**
     * Frogburger
     *
     * @constructor Frogburger
     */
    FROGBURGER(Food(intArrayOf(Items.FROGBURGER_10962), HealingEffect(2))),

    /**
     * Ugthanki Kebab
     *
     * @constructor Ugthanki Kebab
     */
    UGTHANKI_KEBAB(Food(intArrayOf(Items.UGTHANKI_KEBAB_1883), UgthankiKebabEffect())),

    /**
     * Ugthanki Kebab Smelling
     *
     * @constructor Ugthanki Kebab Smelling
     */
    UGTHANKI_KEBAB_SMELLING(Food(intArrayOf(Items.UGTHANKI_KEBAB_1885), SmellingUgthankiKebabEffect())),

    /**
     * Kebab
     *
     * @constructor Kebab
     */
    KEBAB(Food(intArrayOf(Items.KEBAB_1971), KebabEffect())),

    /**
     * Super Kebab
     *
     * @constructor Super Kebab
     */
    SUPER_KEBAB(Food(intArrayOf(Items.SUPER_KEBAB_4608), SuperKebabEffect())),

    /**
     * Redberry Pie
     *
     * @constructor Redberry Pie
     */
    REDBERRY_PIE(
        HalfableFood(
            intArrayOf(Items.REDBERRY_PIE_2325, Items.HALF_A_REDBERRY_PIE_2333, Items.PIE_DISH_2313),
            HealingEffect(5)
        )
    ),

    /**
     * Meat Pie
     *
     * @constructor Meat Pie
     */
    MEAT_PIE(
        HalfableFood(
            intArrayOf(Items.MEAT_PIE_2327, Items.HALF_A_MEAT_PIE_2331, Items.PIE_DISH_2313),
            HealingEffect(6)
        )
    ),

    /**
     * Apple Pie
     *
     * @constructor Apple Pie
     */
    APPLE_PIE(
        HalfableFood(
            intArrayOf(Items.APPLE_PIE_2323, Items.HALF_AN_APPLE_PIE_2335, Items.PIE_DISH_2313),
            HealingEffect(7)
        )
    ),

    /**
     * Garden Pie
     *
     * @constructor Garden Pie
     */
    GARDEN_PIE(
        HalfableFood(
            intArrayOf(Items.GARDEN_PIE_7178, Items.HALF_A_GARDEN_PIE_7180, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.FARMING, 3.0, 0.0))
        )
    ),

    /**
     * Fish Pie
     *
     * @constructor Fish Pie
     */
    FISH_PIE(
        HalfableFood(
            intArrayOf(Items.FISH_PIE_7188, Items.HALF_A_FISH_PIE_7190, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.FISHING, 3.0, 0.0))
        )
    ),

    /**
     * Admiral Pie
     *
     * @constructor Admiral Pie
     */
    ADMIRAL_PIE(
        HalfableFood(
            intArrayOf(Items.ADMIRAL_PIE_7198, Items.HALF_AN_ADMIRAL_PIE_7200, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(8), SkillEffect(Skills.FISHING, 5.0, 0.0))
        )
    ),

    /**
     * Wild Pie
     *
     * @constructor Wild Pie
     */
    WILD_PIE(
        HalfableFood(
            intArrayOf(Items.WILD_PIE_7208, Items.HALF_A_WILD_PIE_7210, Items.PIE_DISH_2313),
            MultiEffect(SkillEffect(Skills.SLAYER, 5.0, 0.0), SkillEffect(Skills.RANGE, 4.0, 0.0), HealingEffect(11))
        )
    ),

    /**
     * Summer Pie
     *
     * @constructor Summer Pie
     */
    SUMMER_PIE(
        HalfableFood(
            intArrayOf(Items.SUMMER_PIE_7218, Items.HALF_A_SUMMER_PIE_7220, Items.PIE_DISH_2313),
            MultiEffect(HealingEffect(11), SkillEffect(Skills.AGILITY, 5.0, 0.0), EnergyEffect(10))
        )
    ),

    /**
     * Stew
     *
     * @constructor Stew
     */
    STEW(Food(intArrayOf(2003, 1923), HealingEffect(11))),

    /**
     * Spicy Stew
     *
     * @constructor Spicy Stew
     */
    SPICY_STEW(Food(intArrayOf(7479, 1923), HealingEffect(11))),

    /**
     * Curry
     *
     * @constructor Curry
     */
    CURRY(Food(intArrayOf(2011, 1923), HealingEffect(19))),

    /**
     * Banana Stew
     *
     * @constructor Banana Stew
     */
    BANANA_STEW(Food(intArrayOf(4016, 1923), HealingEffect(11))),

    /**
     * Plain Pizza
     *
     * @constructor Plain Pizza
     */
    PLAIN_PIZZA(HalfableFood(intArrayOf(2289, 2291), HealingEffect(7))),

    /**
     * Meat Pizza
     *
     * @constructor Meat Pizza
     */
    MEAT_PIZZA(HalfableFood(intArrayOf(2293, 2295), HealingEffect(8))),

    /**
     * Anchovy Pizza
     *
     * @constructor Anchovy Pizza
     */
    ANCHOVY_PIZZA(HalfableFood(intArrayOf(2297, 2299), HealingEffect(9))),

    /**
     * White Pearl
     *
     * @constructor White Pearl
     */
    WHITE_PEARL(
        HalfableFood(
            intArrayOf(4485, 4486),
            HealingEffect(2),
            "You eat the white pearl and spit out the seed when you're done. Mmm, tasty."
        )
    ),

    /**
     * Pineapple Pizza
     *
     * @constructor Pineapple Pizza
     */
    PINEAPPLE_PIZZA(HalfableFood(intArrayOf(2301, 2303), HealingEffect(11))),

    /**
     * Cake
     *
     * @constructor Cake
     */
    CAKE(
        Cake(
            intArrayOf(1891, 1893, 1895),
            HealingEffect(4),
            "You eat part of the cake.",
            "You eat some more cake.",
            "You eat the slice of cake."
        )
    ),

    /**
     * Chocolate Cake
     *
     * @constructor Chocolate Cake
     */
    CHOCOLATE_CAKE(
        Cake(
            intArrayOf(1897, 1899, 1901),
            HealingEffect(5),
            "You eat part of the chocolate cake.",
            "You eat some more of the chocolate cake.",
            "You eat the slice of cake."
        )
    ),

    /**
     * Rock Cake
     *
     * @constructor Rock Cake
     */
    ROCK_CAKE(Food(intArrayOf(2379), RockCakeEffect(), "The rock cake resists all attempts to eat it.")),

    /**
     * Cave Nightshade
     *
     * @constructor Cave Nightshade
     */
    CAVE_NIGHTSHADE(
        Food(
            intArrayOf(Items.CAVE_NIGHTSHADE_2398),
            DamageEffect(15.0, false),
            "Ahhhh! What have I done!"
        )
    ),

    /**
     * Dwarven Rock Cake
     *
     * @constructor Dwarven Rock Cake
     */
    DWARVEN_ROCK_CAKE(
        Food(
            intArrayOf(7510, 7510),
            DwarvenRockCakeEffect(),
            "Ow! You nearly broke a tooth!",
            "The rock cake resists all attempts to eat it."
        )
    ),

    /**
     * Hot Dwarven Rock Cake
     *
     * @constructor Hot Dwarven Rock Cake
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
     * Cooked Fishcake
     *
     * @constructor Cooked Fishcake
     */
    COOKED_FISHCAKE(Food(intArrayOf(7530), HealingEffect(11))),

    /**
     * Mint Cake
     *
     * @constructor Mint Cake
     */
    MINT_CAKE(Food(intArrayOf(9475), EnergyEffect(50))),

    /**
     * Potato
     *
     * @constructor Potato
     */
    POTATO(Food(intArrayOf(1942), HealingEffect(1), "You eat the potato. Yuck!")),

    /**
     * Baked Potato
     *
     * @constructor Baked Potato
     */
    BAKED_POTATO(Food(intArrayOf(6701), HealingEffect(2))),

    /**
     * Poisoned Cheese
     *
     * @constructor Poisoned Cheese
     */
    POISONED_CHEESE(
        FakeConsumable(
            Items.POISONED_CHEESE_6768,
            arrayOf("Ummm... let me think about this one.....No! That would be stupid.")
        )
    ),

    /**
     * Poison Chalice
     *
     * @constructor Poison Chalice
     */
    POISON_CHALICE(Drink(intArrayOf(Items.POISON_CHALICE_197, Items.COCKTAIL_GLASS_2026), PoisonChaliceEffect())),

    /**
     * Spicy Sauce
     *
     * @constructor Spicy Sauce
     */
    SPICY_SAUCE(Food(intArrayOf(7072, 1923), HealingEffect(2))),

    /**
     * Locust Meat
     *
     * @constructor Locust Meat
     */
    LOCUST_MEAT(
        Food(
            intArrayOf(Items.LOCUST_MEAT_9052),
            HealingEffect(3),
            "Juices spurt into your mouth as you chew. It's tastier than it looks."
        )
    ),

    /**
     * Chilli Con Carne
     *
     * @constructor Chilli Con Carne
     */
    CHILLI_CON_CARNE(Food(intArrayOf(7062, 1923), HealingEffect(5))),

    /**
     * Scrambled Egg
     *
     * @constructor Scrambled Egg
     */
    SCRAMBLED_EGG(Food(intArrayOf(7078, 1923), HealingEffect(5))),

    /**
     * Egg And Tomato
     *
     * @constructor Egg And Tomato
     */
    EGG_AND_TOMATO(Food(intArrayOf(7064, 1923), HealingEffect(8))),

    /**
     * Sweet Corn
     *
     * @constructor Sweet Corn
     */
    SWEET_CORN(Food(intArrayOf(5988), MultiEffect(HealingEffect(1), PercentageHealthEffect(10)))),

    /**
     * Sweetcorn Bowl
     *
     * @constructor Sweetcorn Bowl
     */
    SWEETCORN_BOWL(Food(intArrayOf(7088, 1923), MultiEffect(HealingEffect(1), PercentageHealthEffect(10)))),

    /**
     * Potato With Butter
     *
     * @constructor Potato With Butter
     */
    POTATO_WITH_BUTTER(Food(intArrayOf(6703), HealingEffect(7))),

    /**
     * Chilli Potato
     *
     * @constructor Chilli Potato
     */
    CHILLI_POTATO(Food(intArrayOf(7054), HealingEffect(14))),

    /**
     * Fried Onions
     *
     * @constructor Fried Onions
     */
    FRIED_ONIONS(Food(intArrayOf(7084, 1923), HealingEffect(5))),

    /**
     * Fried Mushrooms
     *
     * @constructor Fried Mushrooms
     */
    FRIED_MUSHROOMS(Food(intArrayOf(7082, 1923), HealingEffect(5))),

    /**
     * Black Mushroom
     *
     * @constructor Black Mushroom
     */
    BLACK_MUSHROOM(
        FakeConsumable(
            Items.BLACK_MUSHROOM_4620,
            arrayOf("Eugh! It tastes horrible, and stains your fingers black.")
        )
    ),

    /**
     * Potato With Cheese
     *
     * @constructor Potato With Cheese
     */
    POTATO_WITH_CHEESE(Food(intArrayOf(6705), HealingEffect(16))),

    /**
     * Egg Potato
     *
     * @constructor Egg Potato
     */
    EGG_POTATO(Food(intArrayOf(7056), HealingEffect(11))),

    /**
     * Mushrooms And Onions
     *
     * @constructor Mushrooms And Onions
     */
    MUSHROOMS_AND_ONIONS(Food(intArrayOf(7066, 1923), HealingEffect(11))),

    /**
     * Mushroom Potato
     *
     * @constructor Mushroom Potato
     */
    MUSHROOM_POTATO(Food(intArrayOf(7058), HealingEffect(20))),

    /**
     * Tuna And Corn
     *
     * @constructor Tuna And Corn
     */
    TUNA_AND_CORN(Food(intArrayOf(7068, 1923), HealingEffect(13))),

    /**
     * Tuna Potato
     *
     * @constructor Tuna Potato
     */
    TUNA_POTATO(Food(intArrayOf(7060), HealingEffect(22))),

    /**
     * Onion
     *
     * @constructor Onion
     */
    ONION(Food(intArrayOf(1957), HealingEffect(2), "It's always sad to see a grown man/woman cry.")),

    /**
     * Cabbage
     *
     * @constructor Cabbage
     */
    CABBAGE(Food(intArrayOf(1965), HealingEffect(2), "You eat the cabbage. Yuck!")),

    /**
     * Draynor Cabbage
     *
     * @constructor Draynor Cabbage
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
     * Rotten Apple
     *
     * @constructor Rotten Apple
     */
    ROTTEN_APPLE(Food(intArrayOf(1984), HealingEffect(2), "It's rotten, you spit it out")),

    /**
     * Evil Turnip
     *
     * @constructor Evil Turnip
     */
    EVIL_TURNIP(Food(intArrayOf(12134, 12136, 12138), HealingEffect(6))),

    /**
     * Spinach Roll
     *
     * @constructor Spinach Roll
     */
    SPINACH_ROLL(Food(intArrayOf(1969), HealingEffect(2))),

    /**
     * Pot Of Cream
     *
     * @constructor Pot Of Cream
     */
    POT_OF_CREAM(Food(intArrayOf(2130), HealingEffect(1), "You eat the cream. You get some on your nose.")),

    /**
     * Cheese
     *
     * @constructor Cheese
     */
    CHEESE(Food(intArrayOf(1985), HealingEffect(2))),

    /**
     * Chocolatey Milk
     *
     * @constructor Chocolatey Milk
     */
    CHOCOLATEY_MILK(Drink(intArrayOf(1977, 1925), HealingEffect(4))),

    /**
     * Banana
     *
     * @constructor Banana
     */
    BANANA(Food(intArrayOf(1963), HealingEffect(2))),

    /**
     * Sliced Banana
     *
     * @constructor Sliced Banana
     */
    SLICED_BANANA(Food(intArrayOf(3162), HealingEffect(2))),

    /**
     * Red Banana
     *
     * @constructor Red Banana
     */
    RED_BANANA(
        Food(
            intArrayOf(7572),
            HealingEffect(5),
            "You eat the red banana. It's tastier than your average banana."
        )
    ),

    /**
     * Sliced Red Banana
     *
     * @constructor Sliced Red Banana
     */
    SLICED_RED_BANANA(Food(intArrayOf(7574), HealingEffect(5), "You eat the sliced red banana. Yum.")),

    /**
     * Orange
     *
     * @constructor Orange
     */
    ORANGE(Food(intArrayOf(2108), HealingEffect(2))),

    /**
     * Orange Chunks
     *
     * @constructor Orange Chunks
     */
    ORANGE_CHUNKS(Food(intArrayOf(2110), HealingEffect(2))),

    /**
     * Orange Slices
     *
     * @constructor Orange Slices
     */
    ORANGE_SLICES(Food(intArrayOf(2112), HealingEffect(2))),

    /**
     * Papaya Fruit
     *
     * @constructor Papaya Fruit
     */
    PAPAYA_FRUIT(Food(intArrayOf(5972), HealingEffect(2))),

    /**
     * Tenti Pineapple
     *
     * @constructor Tenti Pineapple
     */
    TENTI_PINEAPPLE(FakeConsumable(1851, arrayOf("Try using a knife to slice it into pieces."))),

    /**
     * Pineapple
     *
     * @constructor Pineapple
     */
    PINEAPPLE(FakeConsumable(2114, arrayOf("Try using a knife to slice it into pieces."))),

    /**
     * Pineapple Chunks
     *
     * @constructor Pineapple Chunks
     */
    PINEAPPLE_CHUNKS(Food(intArrayOf(2116), HealingEffect(2))),

    /**
     * Pineapple Ring
     *
     * @constructor Pineapple Ring
     */
    PINEAPPLE_RING(Food(intArrayOf(2118), HealingEffect(2))),

    /**
     * Dwellberries
     *
     * @constructor Dwellberries
     */
    DWELLBERRIES(Food(intArrayOf(2126), HealingEffect(2))),

    /**
     * Jangerberries
     *
     * @constructor Jangerberries
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
     * Strawberry
     *
     * @constructor Strawberry
     */
    STRAWBERRY(Food(intArrayOf(5504), MultiEffect(HealingEffect(1), PercentageHealthEffect(6)))),

    /**
     * Tomato
     *
     * @constructor Tomato
     */
    TOMATO(Food(intArrayOf(1982), HealingEffect(2))),

    /**
     * Watermelon
     *
     * @constructor Watermelon
     */
    WATERMELON(FakeConsumable(5982, arrayOf("You can't eat it whole; maybe you should cut it up."))),

    /**
     * Watermelon Slice
     *
     * @constructor Watermelon Slice
     */
    WATERMELON_SLICE(Food(intArrayOf(5984), PercentageHealthEffect(5))),

    /**
     * Lemon
     *
     * @constructor Lemon
     */
    LEMON(Food(intArrayOf(2102), HealingEffect(2))),

    /**
     * Lemon Chunks
     *
     * @constructor Lemon Chunks
     */
    LEMON_CHUNKS(Food(intArrayOf(2104), HealingEffect(2))),

    /**
     * Lemon Slices
     *
     * @constructor Lemon Slices
     */
    LEMON_SLICES(Food(intArrayOf(2106), HealingEffect(2))),

    /**
     * Lime
     *
     * @constructor Lime
     */
    LIME(Food(intArrayOf(2120), HealingEffect(2))),

    /**
     * Lime Chunks
     *
     * @constructor Lime Chunks
     */
    LIME_CHUNKS(Food(intArrayOf(2122), HealingEffect(2))),

    /**
     * Lime Slices
     *
     * @constructor Lime Slices
     */
    LIME_SLICES(Food(intArrayOf(2124), HealingEffect(2))),

    /**
     * Peach
     *
     * @constructor Peach
     */
    PEACH(Food(intArrayOf(6883), HealingEffect(8))),

    /**
     * White Tree Fruit
     *
     * @constructor White Tree Fruit
     */
    WHITE_TREE_FRUIT(Food(intArrayOf(6469), MultiEffect(RandomEnergyEffect(5, 10), HealingEffect(3)))),

    /**
     * Strange Fruit
     *
     * @constructor Strange Fruit
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
     * Toad Crunchies
     *
     * @constructor Toad Crunchies
     */
    TOAD_CRUNCHIES(Food(intArrayOf(2217), HealingEffect(12))),

    /**
     * Premade Td Crunch
     *
     * @constructor Premade Td Crunch
     */
    PREMADE_TD_CRUNCH(Food(intArrayOf(2243), HealingEffect(12))),

    /**
     * Spicy Crunchies
     *
     * @constructor Spicy Crunchies
     */
    SPICY_CRUNCHIES(Food(intArrayOf(2213), HealingEffect(7))),

    /**
     * Premade Sy Crunch
     *
     * @constructor Premade Sy Crunch
     */
    PREMADE_SY_CRUNCH(Food(intArrayOf(2241), HealingEffect(7))),

    /**
     * Worm Crunchies
     *
     * @constructor Worm Crunchies
     */
    WORM_CRUNCHIES(Food(intArrayOf(2205), HealingEffect(8))),

    /**
     * Premade Wm Crunc
     *
     * @constructor Premade Wm Crunc
     */
    PREMADE_WM_CRUNC(Food(intArrayOf(2237), HealingEffect(8))),

    /**
     * Chocchip Crunchies
     *
     * @constructor Chocchip Crunchies
     */
    CHOCCHIP_CRUNCHIES(Food(intArrayOf(2209), HealingEffect(7))),

    /**
     * Premade Ch Crunch
     *
     * @constructor Premade Ch Crunch
     */
    PREMADE_CH_CRUNCH(Food(intArrayOf(2239), HealingEffect(7))),

    /**
     * Fruit Batta
     *
     * @constructor Fruit Batta
     */
    FRUIT_BATTA(Food(intArrayOf(2277), HealingEffect(11))),

    /**
     * Premade Frt Batta
     *
     * @constructor Premade Frt Batta
     */
    PREMADE_FRT_BATTA(Food(intArrayOf(2225), HealingEffect(11))),

    /**
     * Toad Batta
     *
     * @constructor Toad Batta
     */
    TOAD_BATTA(Food(intArrayOf(2255), HealingEffect(11))),

    /**
     * Premade Td Batta
     *
     * @constructor Premade Td Batta
     */
    PREMADE_TD_BATTA(Food(intArrayOf(2221), HealingEffect(11))),

    /**
     * Worm Batta
     *
     * @constructor Worm Batta
     */
    WORM_BATTA(Food(intArrayOf(2253), HealingEffect(11))),

    /**
     * Premade Wm Batta
     *
     * @constructor Premade Wm Batta
     */
    PREMADE_WM_BATTA(Food(intArrayOf(2219), HealingEffect(11))),

    /**
     * Vegetable Batta
     *
     * @constructor Vegetable Batta
     */
    VEGETABLE_BATTA(Food(intArrayOf(2281), HealingEffect(11))),

    /**
     * Premade Veg Batta
     *
     * @constructor Premade Veg Batta
     */
    PREMADE_VEG_BATTA(Food(intArrayOf(2227), HealingEffect(11))),

    /**
     * Cheese And Tomatoes Batta
     *
     * @constructor Cheese And Tomatoes Batta
     */
    CHEESE_AND_TOMATOES_BATTA(Food(intArrayOf(2259), HealingEffect(11))),

    /**
     * Premade Ct Batta
     *
     * @constructor Premade Ct Batta
     */
    PREMADE_CT_BATTA(Food(intArrayOf(2223), HealingEffect(11))),

    /**
     * Worm Hole
     *
     * @constructor Worm Hole
     */
    WORM_HOLE(Food(intArrayOf(2191), HealingEffect(12))),

    /**
     * Premade Worm Hole
     *
     * @constructor Premade Worm Hole
     */
    PREMADE_WORM_HOLE(Food(intArrayOf(2233), HealingEffect(12))),

    /**
     * Veg Ball
     *
     * @constructor Veg Ball
     */
    VEG_BALL(Food(intArrayOf(2195), HealingEffect(12))),

    /**
     * Premade Veg Ball
     *
     * @constructor Premade Veg Ball
     */
    PREMADE_VEG_BALL(Food(intArrayOf(2235), HealingEffect(12))),

    /**
     * Tangled Toads Legs
     *
     * @constructor Tangled Toads Legs
     */
    TANGLED_TOADS_LEGS(Food(intArrayOf(2187), HealingEffect(15))),

    /**
     * Premade Ttl
     *
     * @constructor Premade Ttl
     */
    PREMADE_TTL(Food(intArrayOf(2231), HealingEffect(15))),

    /**
     * Chocolate Bomb
     *
     * @constructor Chocolate Bomb
     */
    CHOCOLATE_BOMB(Food(intArrayOf(2195), HealingEffect(15))),

    /**
     * Premade Choc Bomb
     *
     * @constructor Premade Choc Bomb
     */
    PREMADE_CHOC_BOMB(Food(intArrayOf(2229), HealingEffect(15))),

    /**
     * Toad Legs
     *
     * @constructor Toad Legs
     */
    TOAD_LEGS(Food(intArrayOf(2152), HealingEffect(3), "You eat the toad's legs. They're a bit chewy.")),

    /**
     * King Worm
     *
     * @constructor King Worm
     */
    KING_WORM(Food(intArrayOf(2162), HealingEffect(2))),

    /**
     * Asgoldian Ale
     *
     * @constructor Asgoldian Ale
     */
    ASGOLDIAN_ALE(
        FakeConsumable(
            7508,
            arrayOf("I don't think I'd like gold in beer thanks. Leave it for the dwarves.")
        )
    ),

    /**
     * Asgarnian Ale
     *
     * @constructor Asgarnian Ale
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
     * Asgarnian Ale Keg
     *
     * @constructor Asgarnian Ale Keg
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
     * Asgarnian Ale M
     *
     * @constructor Asgarnian Ale M
     */
    ASGARNIAN_ALE_M(
        Drink(
            intArrayOf(5739, 1919),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * Asgarnian Ale M Keg
     *
     * @constructor Asgarnian Ale M Keg
     */
    ASGARNIAN_ALE_M_KEG(
        Drink(
            intArrayOf(5865, 5863, 5861, 5859, 5769),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0)),
            Animation(2289)
        )
    ),

    /**
     * Axemans Folly
     *
     * @constructor Axemans Folly
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
     * Axemans Folly Keg
     *
     * @constructor Axemans Folly Keg
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
     * Axemans Folly M
     *
     * @constructor Axemans Folly M
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
     * Axemans Folly M Keg
     *
     * @constructor Axemans Folly M Keg
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
     * Bandits Brew
     *
     * @constructor Bandits Brew
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
     * Beer
     *
     * @constructor Beer
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
     * Beer Tankard
     *
     * @constructor Beer Tankard
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
     * Keg Of Beer
     *
     * @constructor Keg Of Beer
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
     * Chefs Delight
     *
     * @constructor Chefs Delight
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
     * Chefs Delight Keg
     *
     * @constructor Chefs Delight Keg
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
     * Chefs Delight M
     *
     * @constructor Chefs Delight M
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
     * Chefs Delight M Keg
     *
     * @constructor Chefs Delight M Keg
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
     * Cider
     *
     * @constructor Cider
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
     * Cider Keg
     *
     * @constructor Cider Keg
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
     * Mature Cider
     *
     * @constructor Mature Cider
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
     * Cider M Keg
     *
     * @constructor Cider M Keg
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
     * Dragon Bitter
     *
     * @constructor Dragon Bitter
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
     * Dragon Bitter Keg
     *
     * @constructor Dragon Bitter Keg
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
     * Dragon Bitter M
     *
     * @constructor Dragon Bitter M
     */
    DRAGON_BITTER_M(
        Drink(
            intArrayOf(5745, 1919),
            MultiEffect(HealingEffect(2), SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * Dragon Bitter M Keg
     *
     * @constructor Dragon Bitter M Keg
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
     * Dwarven Stout
     *
     * @constructor Dwarven Stout
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
     * Dwarven Stout Keg
     *
     * @constructor Dwarven Stout Keg
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
     * Dwarven Stout M
     *
     * @constructor Dwarven Stout M
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
     * Dwarven Stout M Keg
     *
     * @constructor Dwarven Stout M Keg
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
     * Greenmans Ale
     *
     * @constructor Greenmans Ale
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
     * Greenmans Ale Keg
     *
     * @constructor Greenmans Ale Keg
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
     * Greenmans Ale M
     *
     * @constructor Greenmans Ale M
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
     * Greenmans Ale M Keg
     *
     * @constructor Greenmans Ale M Keg
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
     * Grog
     *
     * @constructor Grog
     */
    GROG(
        Drink(
            intArrayOf(1915, 1919),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -6.0, 0.0))
        )
    ),

    /**
     * Moonlight Mead
     *
     * @constructor Moonlight Mead
     */
    MOONLIGHT_MEAD(
        Drink(
            intArrayOf(2955, 1919),
            HealingEffect(4),
            "It tastes like something just died in your mouth."
        )
    ),

    /**
     * Moonlight Mead Keg
     *
     * @constructor Moonlight Mead Keg
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
     * Moonlight Mead M
     *
     * @constructor Moonlight Mead M
     */
    MOONLIGHT_MEAD_M(Drink(intArrayOf(5749, 1919), HealingEffect(6))),

    /**
     * Moonlight Mead M Keg
     *
     * @constructor Moonlight Mead M Keg
     */
    MOONLIGHT_MEAD_M_KEG(Drink(intArrayOf(5897, 5895, 5893, 5891, 5769), HealingEffect(6), Animation(2289))),

    /**
     * Slayers Respite
     *
     * @constructor Slayers Respite
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
     * Slayers Respite Keg
     *
     * @constructor Slayers Respite Keg
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
     * Slayers Respite M
     *
     * @constructor Slayers Respite M
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
     * Slayers Respite M Keg
     *
     * @constructor Slayers Respite M Keg
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
     * Wizards Mind Bomb
     *
     * @constructor Wizards Mind Bomb
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
     * Mature Wmb
     *
     * @constructor Mature Wmb
     */
    MATURE_WMB(Drink(intArrayOf(5741, 1919), MatureWmbEffect())),

    /**
     * Fruit Blast
     *
     * @constructor Fruit Blast
     */
    FRUIT_BLAST(Drink(intArrayOf(2084, 2026), HealingEffect(9))),

    /**
     * Premade Fr Blast
     *
     * @constructor Premade Fr Blast
     */
    PREMADE_FR_BLAST(Drink(intArrayOf(2034, 2026), HealingEffect(9))),

    /**
     * Crafted Fr Blast
     *
     * @constructor Crafted Fr Blast
     */
    CRAFTED_FR_BLAST(Drink(intArrayOf(9514, 2026), HealingEffect(9))),

    /**
     * Pineapple Punch
     *
     * @constructor Pineapple Punch
     */
    PINEAPPLE_PUNCH(Drink(intArrayOf(2048, 2026), HealingEffect(9), "You drink the cocktail. It tastes great.")),

    /**
     * Premade P Punch
     *
     * @constructor Premade P Punch
     */
    PREMADE_P_PUNCH(Drink(intArrayOf(2036, 2026), HealingEffect(9), "You drink the cocktail. It tastes great.")),

    /**
     * Crafted P Punch
     *
     * @constructor Crafted P Punch
     */
    CRAFTED_P_PUNCH(Drink(intArrayOf(9512, 2026), HealingEffect(9))),

    /**
     * Wizard Blizzard
     *
     * @constructor Wizard Blizzard
     */
    WIZARD_BLIZZARD(
        Drink(
            intArrayOf(2054, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Premade Wiz Blzd
     *
     * @constructor Premade Wiz Blzd
     */
    PREMADE_WIZ_BLZD(
        Drink(
            intArrayOf(2040, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Crafted Wiz Blzd
     *
     * @constructor Crafted Wiz Blzd
     */
    CRAFTED_WIZ_BLZD(
        Drink(
            intArrayOf(9508, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Short Green Guy
     *
     * @constructor Short Green Guy
     */
    SHORT_GREEN_GUY(
        Drink(
            intArrayOf(2080, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * Premade Sgg
     *
     * @constructor Premade Sgg
     */
    PREMADE_SGG(
        Drink(
            intArrayOf(2038, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * Crafted Sgg
     *
     * @constructor Crafted Sgg
     */
    CRAFTED_SGG(
        Drink(
            intArrayOf(9510, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 4.0, 0.0), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * Drunk Dragon
     *
     * @constructor Drunk Dragon
     */
    DRUNK_DRAGON(
        Drink(
            intArrayOf(2092, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Premade Dr Dragon
     *
     * @constructor Premade Dr Dragon
     */
    PREMADE_DR_DRAGON(
        Drink(
            intArrayOf(2032, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Crafted Dr Dragon
     *
     * @constructor Crafted Dr Dragon
     */
    CRAFTED_DR_DRAGON(
        Drink(
            intArrayOf(9516, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Choc Saturday
     *
     * @constructor Choc Saturday
     */
    CHOC_SATURDAY(
        Drink(
            intArrayOf(2074, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Premade Choc Sdy
     *
     * @constructor Premade Choc Sdy
     */
    PREMADE_CHOC_SDY(
        Drink(
            intArrayOf(2030, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Crafted Choc Sdy
     *
     * @constructor Crafted Choc Sdy
     */
    CRAFTED_CHOC_SDY(
        Drink(
            intArrayOf(9518, 2026),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 7.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Blurberry Special
     *
     * @constructor Blurberry Special
     */
    BLURBERRY_SPECIAL(
        Drink(
            intArrayOf(2064, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Premade Blurb Sp
     *
     * @constructor Premade Blurb Sp
     */
    PREMADE_BLURB_SP(
        Drink(
            intArrayOf(2028, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Crafted Blurb Sp
     *
     * @constructor Crafted Blurb Sp
     */
    CRAFTED_BLURB_SP(
        Drink(
            intArrayOf(9520, 2026),
            MultiEffect(HealingEffect(6), SkillEffect(Skills.STRENGTH, 6.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Karamjan Rum
     *
     * @constructor Karamjan Rum
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
     * Braindeath Rum
     *
     * @constructor Braindeath Rum
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
     * Rum Trouble Brewing Red
     *
     * @constructor Rum Trouble Brewing Red
     */
    RUM_TROUBLE_BREWING_RED(
        Drink(
            intArrayOf(8940, 8940),
            TroubleBrewingRumEffect("Oh gods! It tastes like burning!"),
            Animation(9605)
        )
    ),

    /**
     * Rum Trouble Brewing Blue
     *
     * @constructor Rum Trouble Brewing Blue
     */
    RUM_TROUBLE_BREWING_BLUE(
        Drink(
            intArrayOf(8941, 8941),
            TroubleBrewingRumEffect("My Liver! My Liver is melting!"),
            Animation(9604)
        )
    ),

    /**
     * Vodka
     *
     * @constructor Vodka
     */
    VODKA(
        Drink(
            intArrayOf(2015),
            MultiEffect(HealingEffect(2), SkillEffect(Skills.ATTACK, -4.0, 0.0), SkillEffect(Skills.STRENGTH, 4.0, 0.0))
        )
    ),

    /**
     * Gin
     *
     * @constructor Gin
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
     * Brandy
     *
     * @constructor Brandy
     */
    BRANDY(Drink(intArrayOf(2021), MultiEffect(HealingEffect(5), SkillEffect(Skills.ATTACK, 4.0, 0.0)))),

    /**
     * Whisky
     *
     * @constructor Whisky
     */
    WHISKY(
        Drink(
            intArrayOf(2017),
            MultiEffect(HealingEffect(5), SkillEffect(Skills.STRENGTH, 3.0, 0.0), SkillEffect(Skills.ATTACK, -4.0, 0.0))
        )
    ),

    /**
     * Bottle Of Wine
     *
     * @constructor Bottle Of Wine
     */
    BOTTLE_OF_WINE(
        Drink(
            intArrayOf(7919, 7921),
            MultiEffect(HealingEffect(14), SkillEffect(Skills.ATTACK, -3.0, 0.0))
        )
    ),

    /**
     * Jug Of Wine
     *
     * @constructor Jug Of Wine
     */
    JUG_OF_WINE(Drink(intArrayOf(1993, 1935), MultiEffect(HealingEffect(11), SkillEffect(Skills.ATTACK, -2.0, 0.0)))),

    /**
     * Half Full Wine Jug
     *
     * @constructor Half Full Wine Jug
     */
    HALF_FULL_WINE_JUG(Drink(intArrayOf(1989, 1935), HealingEffect(7))),

    /**
     * Jug Of Bad Wine
     *
     * @constructor Jug Of Bad Wine
     */
    JUG_OF_BAD_WINE(Drink(intArrayOf(1991, 1935), SkillEffect(Skills.ATTACK, -3.0, 0.0))),

    /**
     * Cup Of Tea
     *
     * @constructor Cup Of Tea
     */
    CUP_OF_TEA(
        Drink(
            intArrayOf(712, 1980),
            MultiEffect(HealingEffect(3), SkillEffect(Skills.ATTACK, 3.0, 0.0)),
            "Aaah, nothing like a nice cuppa tea!"
        )
    ),

    /**
     * Cup Of Tea Nettle
     *
     * @constructor Cup Of Tea Nettle
     */
    CUP_OF_TEA_NETTLE(Drink(intArrayOf(4242, 1980), EnergyEffect(10))),

    /**
     * Cup Of Tea Milky Nettle
     *
     * @constructor Cup Of Tea Milky Nettle
     */
    CUP_OF_TEA_MILKY_NETTLE(Drink(intArrayOf(4243, 1980), EnergyEffect(10))),

    /**
     * Nettle Water
     *
     * @constructor Nettle Water
     */
    NETTLE_WATER(Drink(intArrayOf(4237, 1923), HealingEffect(1))),

    /**
     * Nettle Tea
     *
     * @constructor Nettle Tea
     */
    NETTLE_TEA(Drink(intArrayOf(4239, 1923), NettleTeaEffect())),

    /**
     * Nettle Tea Milky
     *
     * @constructor Nettle Tea Milky
     */
    NETTLE_TEA_MILKY(Drink(intArrayOf(4240, 1923), NettleTeaEffect())),

    /**
     * Nettle Tea Porcelain
     *
     * @constructor Nettle Tea Porcelain
     */
    NETTLE_TEA_PORCELAIN(Drink(intArrayOf(4245, 4244), NettleTeaEffect())),

    /**
     * Nettle Tea Milky Porcelain
     *
     * @constructor Nettle Tea Milky Porcelain
     */
    NETTLE_TEA_MILKY_PORCELAIN(Drink(intArrayOf(4246, 4244), NettleTeaEffect())),

    /**
     * Cup Of Tea Clay
     *
     * @constructor Cup Of Tea Clay
     */
    CUP_OF_TEA_CLAY(
        Drink(
            intArrayOf(7730, 7728),
            SkillEffect(Skills.CONSTRUCTION, 1.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * Cup Of Tea Clay Milky
     *
     * @constructor Cup Of Tea Clay Milky
     */
    CUP_OF_TEA_CLAY_MILKY(Drink(intArrayOf(7731, 7728), SkillEffect(Skills.CONSTRUCTION, 1.0, 0.0))),

    /**
     * Cup Of Tea White
     *
     * @constructor Cup Of Tea White
     */
    CUP_OF_TEA_WHITE(
        Drink(
            intArrayOf(7733, 7732),
            SkillEffect(Skills.CONSTRUCTION, 2.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * Cup Of Tea White Milky
     *
     * @constructor Cup Of Tea White Milky
     */
    CUP_OF_TEA_WHITE_MILKY(Drink(intArrayOf(7734, 7732), SkillEffect(Skills.CONSTRUCTION, 2.0, 0.0))),

    /**
     * Cup Of Tea Gold
     *
     * @constructor Cup Of Tea Gold
     */
    CUP_OF_TEA_GOLD(
        Drink(
            intArrayOf(7736, 7735),
            SkillEffect(Skills.CONSTRUCTION, 3.0, 0.0),
            "You feel refreshed and ready for more building."
        )
    ),

    /**
     * Cup Of Tea Gold Milky
     *
     * @constructor Cup Of Tea Gold Milky
     */
    CUP_OF_TEA_GOLD_MILKY(Drink(intArrayOf(7737, 7735), SkillEffect(Skills.CONSTRUCTION, 3.0, 0.0))),

    /**
     * Chocolate Bar
     *
     * @constructor Chocolate Bar
     */
    CHOCOLATE_BAR(Food(intArrayOf(1973), HealingEffect(3))),

    /**
     * Purple Sweets
     *
     * @constructor Purple Sweets
     */
    PURPLE_SWEETS(Food(intArrayOf(4561), HealingEffect(0))),

    /**
     * Purple Sweets Stackable
     *
     * @constructor Purple Sweets Stackable
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
     * Field Ration
     *
     * @constructor Field Ration
     */
    FIELD_RATION(Food(intArrayOf(7934), HealingEffect(10))),

    /**
     * Roll
     *
     * @constructor Roll
     */
    ROLL(Food(intArrayOf(6963), HealingEffect(6))),

    /**
     * Tchiki Monkey Nuts
     *
     * @constructor Tchiki Monkey Nuts
     */
    TCHIKI_MONKEY_NUTS(Food(intArrayOf(7573), HealingEffect(5), "You eat the Tchiki monkey nuts. They taste nutty.")),

    /**
     * Tchiki Monkey Paste
     *
     * @constructor Tchiki Monkey Paste
     */
    TCHIKI_MONKEY_PASTE(
        Food(
            intArrayOf(7575),
            HealingEffect(5),
            "You eat the Tchiki monkey nut paste. It sticks to the roof of your mouth."
        )
    ),

    /**
     * Oomlie Wrap
     *
     * @constructor Oomlie Wrap
     */
    OOMLIE_WRAP(
        Food(
            intArrayOf(Items.COOKED_OOMLIE_WRAP_2343),
            MultiEffect(HealingEffect(14), AchievementEffect(DiaryType.KARAMJA, 2, 2))
        )
    ),

    /**
     * Roe
     *
     * @constructor Roe
     */
    ROE(Food(intArrayOf(11324), HealingEffect(3))),

    /**
     * Equa Leaves
     *
     * @constructor Equa Leaves
     */
    EQUA_LEAVES(Food(intArrayOf(2128), HealingEffect(1), "You eat the leaves; chewy but tasty.")),

    /**
     * Choc Ice
     *
     * @constructor Choc Ice
     */
    CHOC_ICE(Food(intArrayOf(6794), HealingEffect(6))),

    /**
     * Edible Seaweed
     *
     * @constructor Edible Seaweed
     */
    EDIBLE_SEAWEED(Food(intArrayOf(403), HealingEffect(4))),

    /**
     * Frog Spawn
     *
     * @constructor Frog Spawn
     */
    FROG_SPAWN(Food(intArrayOf(5004), RandomHealthEffect(3, 7), "You eat the frogspawn. Yuck.")),

    /**
     * Pumpkin
     *
     * @constructor Pumpkin
     */
    PUMPKIN(Food(intArrayOf(1959), HealingEffect(14))),

    /**
     * Easter Egg
     *
     * @constructor Easter Egg
     */
    EASTER_EGG(Food(intArrayOf(1961), HealingEffect(14))),

    /**
     * Strength
     *
     * @constructor Strength
     */
    STRENGTH(Potion(intArrayOf(113, 115, 117, 119), SkillEffect(Skills.STRENGTH, 3.0, 0.1))),

    /**
     * Attack
     *
     * @constructor Attack
     */
    ATTACK(Potion(intArrayOf(2428, 121, 123, 125), SkillEffect(Skills.ATTACK, 3.0, 0.1))),

    /**
     * Defence
     *
     * @constructor Defence
     */
    DEFENCE(Potion(intArrayOf(2432, 133, 135, 137), SkillEffect(Skills.DEFENCE, 3.0, 0.1))),

    /**
     * Ranging
     *
     * @constructor Ranging
     */
    RANGING(Potion(intArrayOf(2444, 169, 171, 173), SkillEffect(Skills.RANGE, 4.0, 0.1))),

    /**
     * Magic
     *
     * @constructor Magic
     */
    MAGIC(Potion(intArrayOf(3040, 3042, 3044, 3046), SkillEffect(Skills.MAGIC, 4.0, 0.1))),

    /**
     * Super Strength
     *
     * @constructor Super Strength
     */
    SUPER_STRENGTH(Potion(intArrayOf(2440, 157, 159, 161), SkillEffect(Skills.STRENGTH, 5.0, 0.15))),

    /**
     * Super Attack
     *
     * @constructor Super Attack
     */
    SUPER_ATTACK(Potion(intArrayOf(2436, 145, 147, 149), SkillEffect(Skills.ATTACK, 5.0, 0.15))),

    /**
     * Super Defence
     *
     * @constructor Super Defence
     */
    SUPER_DEFENCE(Potion(intArrayOf(2442, 163, 165, 167), SkillEffect(Skills.DEFENCE, 5.0, 0.15))),

    /**
     * Antipoison
     *
     * @constructor Antipoison
     */
    ANTIPOISON(Potion(intArrayOf(2446, 175, 177, 179), AddTimerEffect("poison:immunity", secondsToTicks(90)))),

    /**
     * Antipoison
     *
     * @constructor Antipoison
     */
    ANTIPOISON_(Potion(intArrayOf(5943, 5945, 5947, 5949), AddTimerEffect("poison:immunity", minutesToTicks(9)))),

    /**
     * Antipoison
     *
     * @constructor Antipoison
     */
    ANTIPOISON__(Potion(intArrayOf(5952, 5954, 5956, 5958), AddTimerEffect("poison:immunity", minutesToTicks(12)))),

    /**
     * Super Antip
     *
     * @constructor Super Antip
     */
    SUPER_ANTIP(Potion(intArrayOf(2448, 181, 183, 185), AddTimerEffect("poison:immunity", minutesToTicks(6)))),

    /**
     * Relicym
     *
     * @constructor Relicym
     */
    RELICYM(
        Potion(
            intArrayOf(4842, 4844, 4846, 4848),
            MultiEffect(SetAttributeEffect("disease:immunity", 300), RemoveTimerEffect("disease"))
        )
    ),

    /**
     * Agility
     *
     * @constructor Agility
     */
    AGILITY(Potion(intArrayOf(3032, 3034, 3036, 3038), SkillEffect(Skills.AGILITY, 3.0, 0.0))),

    /**
     * Hunter
     *
     * @constructor Hunter
     */
    HUNTER(Potion(intArrayOf(9998, 10000, 10002, 10004), SkillEffect(Skills.HUNTER, 3.0, 0.0))),

    /**
     * Restore
     *
     * @constructor Restore
     */
    RESTORE(Potion(intArrayOf(2430, 127, 129, 131), RestoreEffect(10.0, 0.3))),

    /**
     * Sara Brew
     *
     * @constructor Sara Brew
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
     * Summoning
     *
     * @constructor Summoning
     */
    SUMMONING(
        Potion(
            intArrayOf(12140, 12142, 12144, 12146),
            MultiEffect(RestoreSummoningSpecial(), SummoningEffect(7.0, 0.25))
        )
    ),

    /**
     * Combat
     *
     * @constructor Combat
     */
    COMBAT(
        Potion(
            intArrayOf(9739, 9741, 9743, 9745),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, .1), SkillEffect(Skills.ATTACK, 3.0, .1))
        )
    ),

    /**
     * Energy
     *
     * @constructor Energy
     */
    ENERGY(Potion(intArrayOf(3008, 3010, 3012, 3014), MultiEffect(EnergyEffect(10)))),

    /**
     * Fishing
     *
     * @constructor Fishing
     */
    FISHING(Potion(intArrayOf(2438, 151, 153, 155), SkillEffect(Skills.FISHING, 3.0, 0.0))),

    /**
     * Prayer
     *
     * @constructor Prayer
     */
    PRAYER(Potion(intArrayOf(2434, 139, 141, 143), PrayerEffect(7.0, 0.25))),

    /**
     * Super Resto
     *
     * @constructor Super Resto
     */
    SUPER_RESTO(
        Potion(
            intArrayOf(3024, 3026, 3028, 3030),
            MultiEffect(RestoreEffect(8.0, 0.25, true), PrayerEffect(8.0, 0.25), SummoningEffect(8.0, 0.25))
        )
    ),

    /**
     * Zammy Brew
     *
     * @constructor Zammy Brew
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
     * Antifire
     *
     * @constructor Antifire
     */
    ANTIFIRE(Potion(intArrayOf(2452, 2454, 2456, 2458), SetAttributeEffect("fire:immune", 600, true))),

    /**
     * Guth Rest
     *
     * @constructor Guth Rest
     */
    GUTH_REST(
        Potion(
            intArrayOf(4417, 4419, 4421, 4423),
            MultiEffect(RemoveTimerEffect("poison"), EnergyEffect(5), HealingEffect(5))
        )
    ),

    /**
     * Magic Ess
     *
     * @constructor Magic Ess
     */
    MAGIC_ESS(Potion(intArrayOf(11491, 11489), SkillEffect(Skills.MAGIC, 3.0, 0.0))),

    /**
     * Sanfew
     *
     * @constructor Sanfew
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
     * Super Energy
     *
     * @constructor Super Energy
     */
    SUPER_ENERGY(Potion(intArrayOf(3016, 3018, 3020, 3022), EnergyEffect(20))),

    /**
     * Blamish Oil
     *
     * @constructor Blamish Oil
     */
    BLAMISH_OIL(FakeConsumable(1582, arrayOf("You know... I'd really rather not."))),

    /**
     * Prayermix
     *
     * @constructor Prayermix
     */
    PRAYERMIX(BarbarianMix(intArrayOf(11465, 11467), MultiEffect(PrayerEffect(7.0, 0.25), HealingEffect(6)))),

    /**
     * Zammy Mix
     *
     * @constructor Zammy Mix
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
     * Att Mix
     *
     * @constructor Att Mix
     */
    ATT_MIX(
        BarbarianMix(
            intArrayOf(11429, 11431),
            MultiEffect(SkillEffect(Skills.ATTACK, 3.0, 0.1), HealingEffect(3))
        )
    ),

    /**
     * Antip Mix
     *
     * @constructor Antip Mix
     */
    ANTIP_MIX(
        BarbarianMix(
            intArrayOf(11433, 11435),
            MultiEffect(AddTimerEffect("poison:immunity", secondsToTicks(90)), HealingEffect(3))
        )
    ),

    /**
     * Relic Mix
     *
     * @constructor Relic Mix
     */
    RELIC_MIX(
        BarbarianMix(
            intArrayOf(11437, 11439),
            MultiEffect(RemoveTimerEffect("disease"), SetAttributeEffect("disease:immunity", 300), HealingEffect(3))
        )
    ),

    /**
     * Str Mix
     *
     * @constructor Str Mix
     */
    STR_MIX(
        BarbarianMix(
            intArrayOf(11443, 11441),
            MultiEffect(SkillEffect(Skills.STRENGTH, 3.0, 0.1), HealingEffect(3))
        )
    ),

    /**
     * Resto Mix
     *
     * @constructor Resto Mix
     */
    RESTO_MIX(BarbarianMix(intArrayOf(11449, 11451), MultiEffect(RestoreEffect(10.0, 0.3), HealingEffect(3)))),

    /**
     * Super Resto Mix
     *
     * @constructor Super Resto Mix
     */
    SUPER_RESTO_MIX(
        BarbarianMix(
            intArrayOf(11493, 11495),
            MultiEffect(RestoreEffect(8.0, 0.25), PrayerEffect(8.0, 0.25), SummoningEffect(8.0, 0.25), HealingEffect(6))
        )
    ),

    /**
     * Energy Mix
     *
     * @constructor Energy Mix
     */
    ENERGY_MIX(BarbarianMix(intArrayOf(11453, 11455), MultiEffect(EnergyEffect(10), HealingEffect(6)))),

    /**
     * Def Mix
     *
     * @constructor Def Mix
     */
    DEF_MIX(
        BarbarianMix(
            intArrayOf(11457, 11459),
            MultiEffect(SkillEffect(Skills.DEFENCE, 3.0, 0.1), HealingEffect(6))
        )
    ),

    /**
     * Agil Mix
     *
     * @constructor Agil Mix
     */
    AGIL_MIX(
        BarbarianMix(
            intArrayOf(11461, 11463),
            MultiEffect(SkillEffect(Skills.AGILITY, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * Combat Mix
     *
     * @constructor Combat Mix
     */
    COMBAT_MIX(
        BarbarianMix(
            intArrayOf(11445, 11447),
            MultiEffect(SkillEffect(Skills.ATTACK, 3.0, 0.1), SkillEffect(Skills.STRENGTH, 3.0, 0.1), HealingEffect(6))
        )
    ),

    /**
     * Super Att Mix
     *
     * @constructor Super Att Mix
     */
    SUPER_ATT_MIX(
        BarbarianMix(
            intArrayOf(11469, 11471),
            MultiEffect(SkillEffect(Skills.ATTACK, 5.0, 0.15), HealingEffect(6))
        )
    ),

    /**
     * Fish Mix
     *
     * @constructor Fish Mix
     */
    FISH_MIX(
        BarbarianMix(
            intArrayOf(11477, 11479),
            MultiEffect(SkillEffect(Skills.FISHING, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * Super Energy Mix
     *
     * @constructor Super Energy Mix
     */
    SUPER_ENERGY_MIX(BarbarianMix(intArrayOf(11481, 11483), MultiEffect(EnergyEffect(20), HealingEffect(6)))),

    /**
     * Hunting Mix
     *
     * @constructor Hunting Mix
     */
    HUNTING_MIX(
        BarbarianMix(
            intArrayOf(11517, 11519),
            MultiEffect(SkillEffect(Skills.HUNTER, 3.0, 0.0), HealingEffect(6))
        )
    ),

    /**
     * Super Str Mix
     *
     * @constructor Super Str Mix
     */
    SUPER_STR_MIX(
        BarbarianMix(
            intArrayOf(11485, 11487),
            MultiEffect(SkillEffect(Skills.STRENGTH, 5.0, 0.15), HealingEffect(6))
        )
    ),

    /**
     * Antidote Plus Mix
     *
     * @constructor Antidote Plus Mix
     */
    ANTIDOTE_PLUS_MIX(
        BarbarianMix(
            intArrayOf(11501, 11503),
            MultiEffect(AddTimerEffect("poison:immunity", minutesToTicks(9)), RandomHealthEffect(3, 7))
        )
    ),

    /**
     * Antip Supermix
     *
     * @constructor Antip Supermix
     */
    ANTIP_SUPERMIX(
        BarbarianMix(
            intArrayOf(11473, 11475),
            MultiEffect(AddTimerEffect("poison:immunity", minutesToTicks(6)), RandomHealthEffect(3, 7))
        )
    ),

    /**
     * Sc Prayer
     *
     * @constructor Sc Prayer
     */
    SC_PRAYER(Potion(intArrayOf(14207, 14209, 14211, 14213, 14215), PrayerEffect(7.0, 0.25))),

    /**
     * Sc Energy
     *
     * @constructor Sc Energy
     */
    SC_ENERGY(Potion(intArrayOf(14217, 14219, 14221, 14223, 14225), EnergyEffect(20))),

    /**
     * Sc Attack
     *
     * @constructor Sc Attack
     */
    SC_ATTACK(Potion(intArrayOf(14227, 14229, 14231, 14233, 14235), SkillEffect(Skills.ATTACK, 3.0, 0.2))),

    /**
     * Sc Strength
     *
     * @constructor Sc Strength
     */
    SC_STRENGTH(Potion(intArrayOf(14237, 14239, 14241, 14243, 14245), SkillEffect(Skills.STRENGTH, 3.0, 0.2))),

    /**
     * Sc Range
     *
     * @constructor Sc Range
     */
    SC_RANGE(Potion(intArrayOf(14247, 14249, 14251, 14253, 14255), SkillEffect(Skills.RANGE, 3.0, 0.1))),

    /**
     * Sc Defence
     *
     * @constructor Sc Defence
     */
    SC_DEFENCE(Potion(intArrayOf(14257, 14259, 14261, 14263, 14265), SkillEffect(Skills.DEFENCE, 3.0, 0.1))),

    /**
     * Sc Magic
     *
     * @constructor Sc Magic
     */
    SC_MAGIC(Potion(intArrayOf(14267, 14269, 14271, 14273, 14275), SkillEffect(Skills.MAGIC, 3.0, 0.1))),

    /**
     * Sc Summoning
     *
     * @constructor Sc Summoning
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
                    for (pot in consumable.consumable.getIds()) {
                        potions.add(pot)
                    }
                }
            }
        }
    }
}
