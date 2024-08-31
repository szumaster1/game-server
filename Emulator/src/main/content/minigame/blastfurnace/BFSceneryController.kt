package content.minigame.blastfurnace

import core.api.animateScenery
import core.api.getScenery
import core.api.replaceScenery
import core.game.world.map.Location

/**
 * Blast Furnace mini-game scenery controller.
 * @author Ceikry
 */
class BFSceneryController {
    /**
     * Update breakable.
     *
     * @param potPipeBroken Indicates if the pot pipe is broken
     * @param pumpPipeBroken Indicates if the pump pipe is broken
     * @param beltBroken Indicates if the belt is broken
     * @param cogBroken Indicates if the cog is broken
     */
    fun updateBreakable(potPipeBroken: Boolean, pumpPipeBroken: Boolean, beltBroken: Boolean, cogBroken: Boolean) {
        val beltObj = getScenery(beltGearRight)!! // Get the scenery object for the belt gear on the right
        val gearObj = getScenery(cogRightLoc)!! // Get the scenery object for the cog on the right
        val potPipe = getScenery(potPipeLoc)!! // Get the scenery object for the pot pipe
        val pumpPipe = getScenery(pumpPipeLoc)!! // Get the scenery object for the pump pipe

        // Check if the pot pipe is broken and replace it with the broken version if necessary
        if (potPipeBroken && potPipe.id != BROKEN_POT_PIPE) replaceScenery(potPipe, BROKEN_POT_PIPE, -1) // Replace with broken pot pipe
        else if (!potPipeBroken && potPipe.id == BROKEN_POT_PIPE) replaceScenery(potPipe, DEFAULT_POT_PIPE, -1) // Replace with default pot pipe

        // Check if the pump pipe is broken and replace it with the broken version if necessary
        if (pumpPipeBroken && pumpPipe.id != BROKEN_PUMP_PIPE) replaceScenery(pumpPipe, BROKEN_PUMP_PIPE, -1) // Replace with broken pump pipe
        else if (!pumpPipeBroken && pumpPipe.id == BROKEN_PUMP_PIPE) replaceScenery(pumpPipe, DEFAULT_PUMP_PIPE, -1) // Replace with default pump pipe

        // Check if the belt is broken and replace it with the broken version if necessary
        if (beltBroken && beltObj.id != BROKEN_BELT) replaceScenery(beltObj, BROKEN_BELT, -1) // Replace with broken belt
        else if (!beltBroken && beltObj.id == BROKEN_BELT) replaceScenery(beltObj, DEFAULT_BELT, -1) // Replace with default belt

        // Check if the cog is broken and replace it with the broken version if necessary
        if (cogBroken && gearObj.id != BROKEN_COG) replaceScenery(gearObj, BROKEN_COG, -1) // Replace with broken cog
        else if (!cogBroken && gearObj.id == BROKEN_COG) replaceScenery(gearObj, DEFAULT_COG, -1) // Replace with default cog
    }

    /**
     * Update animations.
     *
     * @param pedaling Indicates if the player is pedaling.
     * @param beltBroken Indicates if the belt is broken.
     * @param cogBroken Indicates if the cog is broken.
     */
    fun updateAnimations(pedaling: Boolean, beltBroken: Boolean, cogBroken: Boolean) {
        val belt1 = getScenery(belt1Loc)!! // Get the scenery object for belt 1
        val belt2 = getScenery(belt2Loc)!! // Get the scenery object for belt 2
        val belt3 = getScenery(belt3Loc)!! // Get the scenery object for belt 3
        val beltGearLeft = getScenery(beltGearLeft)!! // Get the scenery object for the left belt gear
        val beltGearRight = getScenery(beltGearRight)!! // Get the scenery object for the right belt gear
        val cogLeft = getScenery(cogLeftLoc)!! // Get the scenery object for the cog on the left
        val cogRight = getScenery(cogRightLoc)!! // Get the scenery object for the cog on the right
        val cogCenter = getScenery(centralGearLoc)!! // Get the scenery object for the central gear

        // Set the animation for the belts and gears based on the player pedaling and the state of the belt and cog
        val beltAnim = if (pedaling && !beltBroken && !cogBroken) BELT_ANIM else -1 // Determine belt animation
        val gearAnim = if (pedaling && !beltBroken && !cogBroken) GEAR_ANIM else -1 // Determine gear animation

        // Animate the scenery objects
        animateScenery(belt1, beltAnim) // Animate belt 1
        animateScenery(belt2, beltAnim) // Animate belt 2
        animateScenery(belt3, beltAnim) // Animate belt 3
        animateScenery(beltGearLeft, gearAnim) // Animate left belt gear
        animateScenery(beltGearRight, gearAnim) // Animate right belt gear
        animateScenery(cogLeft, gearAnim) // Animate left cog
        animateScenery(cogRight, gearAnim) // Animate right cog
        animateScenery(cogCenter, gearAnim) // Animate central cog
    }

    /**
     * Update stove.
     *
     * @param temp the temperature of the stove.
     */
    fun updateStove(temp: Int) {
        val stoveObj = getScenery(stoveLoc)!! // Get the scenery object for the stove

        // Check the temperature and replace the stove with the appropriate version
        if (temp >= 67 && stoveObj.id != STOVE_HOT) replaceScenery(stoveObj, STOVE_HOT, -1) // Replace with hot stove
        else if (temp in 34..66 && stoveObj.id != STOVE_WARM) replaceScenery(stoveObj, STOVE_WARM, -1) // Replace with warm stove
        else if (temp in 0..33 && stoveObj.id != STOVE_COLD) replaceScenery(stoveObj, STOVE_COLD, -1) // Replace with cold stove
    }

    /**
     * Reset all scenery.
     */
    fun resetAllScenery() {
        val beltObj = getScenery(beltGearRight)!! // Get the scenery object for the belt gear on the right
        val gearObj = getScenery(cogRightLoc)!! // Get the scenery object for the cog on the right
        val potPipe = getScenery(potPipeLoc)!! // Get the scenery object for the pot pipe
        val pumpPipe = getScenery(pumpPipeLoc)!! // Get the scenery object for the pump pipe
        val stoveObj = getScenery(stoveLoc)!! // Get the scenery object for the stove

        // Reset all the scenery objects to their default versions
        replaceScenery(gearObj, DEFAULT_COG, -1) // Reset cog to default
        replaceScenery(beltObj, DEFAULT_BELT, -1) // Reset belt to default
        replaceScenery(pumpPipe, DEFAULT_PUMP_PIPE, -1) // Reset pump pipe to default
        replaceScenery(potPipe, DEFAULT_POT_PIPE, -1) // Reset pot pipe to default
        replaceScenery(stoveObj, STOVE_COLD, -1) // Reset stove to cold
    }

    companion object {
        val belt1Loc = Location(1943, 4967, 0) // Location for belt 1
        val belt2Loc = Location(1943, 4966, 0) // Location for belt 2
        val belt3Loc = Location(1943, 4965, 0) // Location for belt 3
        var potPipeLoc = Location(1943, 4961, 0) // Location for pot pipe
        var pumpPipeLoc = Location(1947, 4961, 0) // Location for pump pipe
        var cogLeftLoc = Location(1945, 4965, 0) // Location for left cog
        var cogRightLoc = Location(1945, 4967, 0) // Location for right cog
        var beltGearLeft = Location(1944, 4965, 0) // Location for left belt gear
        var beltGearRight = Location(1944, 4967, 0) // Location for right belt gear
        var centralGearLoc = Location(1945, 4966, 0) // Location for central gear
        var stoveLoc = Location(1948, 4963, 0) // Location for stove

        val DEFAULT_BELT = 9102 // Default belt ID
        val BROKEN_BELT = 9103 // Broken belt ID
        val DEFAULT_COG = 9104 // Default cog ID
        val BROKEN_COG = 9105 // Broken cog ID
        val DEFAULT_POT_PIPE = 9116 // Default pot pipe ID
        val BROKEN_POT_PIPE = 9117 // Broken pot pipe ID
        val DEFAULT_PUMP_PIPE = 9120 // Default pump pipe ID
        val BROKEN_PUMP_PIPE = 9121 // Broken pump pipe ID
        val STOVE_COLD = 9085 // Cold stove ID
        val STOVE_WARM = 9086 // Warm stove ID
        val STOVE_HOT = 9087 // Hot stove ID
        val BELT_ANIM = 2435 // Belt animation ID
        val GEAR_ANIM = 2436 // Gear animation ID
    }
}
