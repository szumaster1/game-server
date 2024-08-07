package core.game.component;

import core.game.interaction.InterfaceListeners;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.InterfaceManager;
import core.network.packet.PacketRepository;
import core.network.packet.context.InterfaceContext;
import core.network.packet.outgoing.Interface;

/**
 * Component.
 */
public class Component {

    /**
     * The Id.
     */
    protected int id;

    /**
     * The Definition.
     */
    protected final ComponentDefinition definition;

    /**
     * The Close event.
     */
    protected CloseEvent closeEvent;

    /**
     * The Plugin.
     */
    protected ComponentPlugin plugin;

    private boolean hidden;

    /**
     * Instantiates a new Component.
     *
     * @param id the id
     */
    public Component(int id) {
        this.id = id;
        this.definition = ComponentDefinition.forId(id);
        this.plugin = definition.getPlugin();
    }

    /**
     * Open.
     *
     * @param player the player
     */
    public void open(Player player) {
        InterfaceManager manager = player.getInterfaceManager();
        InterfaceListeners.runOpen(player, this);
        if (definition == null) {
            PacketRepository.send(Interface.class, new InterfaceContext(player, manager.getWindowPaneId(), manager.getDefaultChildId(), getId(), false));
            if (plugin != null) {
                plugin.open(player, this);
            }
            return;
        }
        if (definition.getType() == InterfaceType.WINDOW_PANE) {
            return;
        }
        if (definition.getType() == InterfaceType.TAB) {
            PacketRepository.send(Interface.class, new InterfaceContext(player, definition.getWindowPaneId(manager.isResizable()), definition.getChildId(manager.isResizable()) + definition.getTabIndex(), getId(), definition.isWalkable()));
            if (plugin != null) {
                plugin.open(player, this);
            }
            return;
        }
        PacketRepository.send(Interface.class, new InterfaceContext(player, definition.getWindowPaneId(manager.isResizable()), definition.getChildId(manager.isResizable()), getId(), definition.isWalkable()));
        if (plugin != null) {
            plugin.open(player, this);
        }
    }

    /**
     * Close boolean.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean close(Player player) {
        return (closeEvent == null || closeEvent.close(player, this)) && InterfaceListeners.runClose(player, this);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets definition.
     *
     * @return the definition
     */
    public ComponentDefinition getDefinition() {
        return definition;
    }

    /**
     * Gets close event.
     *
     * @return the close event
     */
    public CloseEvent getCloseEvent() {
        return closeEvent;
    }

    /**
     * Sets close event.
     *
     * @param closeEvent the close event
     * @return the close event
     */
    public Component setCloseEvent(CloseEvent closeEvent) {
        this.closeEvent = closeEvent;
        return this;
    }

    /**
     * Sets unclosable.
     *
     * @param p the p
     * @param c the c
     */
    public static void setUnclosable(Player p, Component c) {
        p.setAttribute("close_c_", true);
        c.setCloseEvent(new CloseEvent() {
            @Override
            public boolean close(Player player, Component c) {
                return !player.getAttribute("close_c_", false);
            }
        });
    }

    /**
     * Sets plugin.
     *
     * @param plugin the plugin
     */
    public void setPlugin(ComponentPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets plugin.
     *
     * @return the plugin
     */
    public ComponentPlugin getPlugin() {
        if (plugin == null) {
            ComponentPlugin p = ComponentDefinition.forId(getId()).getPlugin();
            if ((plugin = p) != null) {
                return p;
            }
        }
        return plugin;
    }

    /**
     * Is hidden boolean.
     *
     * @return the boolean
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets hidden.
     *
     * @param hidden the hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
