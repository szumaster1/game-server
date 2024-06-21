package core.network.packet.context;

import core.game.node.entity.player.Player;
import core.game.world.map.Location;
import core.game.world.update.flag.context.Graphic;
import core.network.packet.Context;

/**
 * The packet context for the positioned graphic packet.
 * @author Emperor
 */
public final class PositionedGraphicContext implements Context {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The graphics.
	 */
	private final Graphic graphic;

	/**
	 * The location.
	 */
	private final Location location;

	public int sceneX, sceneY;
	public int offsetX, offsetY;

	/**
	 * Constructs a new {@code PositionedGraphicContext} {@code Object}.
	 * @param player The player.
	 * @param graphic The graphic to display on the given location.
	 * @param location The location to display the graphic on.
	 */
	public PositionedGraphicContext(Player player, Graphic graphic, Location location, int offsetX, int offsetY) {
		this.player = player;
		this.graphic = graphic;
		this.location = location;
		this.sceneX = location.getSceneX(player.getPlayerFlags().getLastSceneGraph());
		this.sceneY = location.getSceneY(player.getPlayerFlags().getLastSceneGraph());
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the graphic
	 */
	public Graphic getGraphic() {
		return graphic;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

}
