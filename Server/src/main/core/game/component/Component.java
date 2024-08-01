package core.game.component;

import core.game.interaction.InterfaceListeners;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.link.InterfaceManager;
import core.network.packet.PacketRepository;
import core.network.packet.context.InterfaceContext;
import core.network.packet.outgoing.Interface;

public class Component {

    protected int id;

    protected final ComponentDefinition definition;

    protected CloseEvent closeEvent;

    protected ComponentPlugin plugin;

    private boolean hidden;

    public Component(int id) {
        this.id = id;
        this.definition = ComponentDefinition.forId(id);
        this.plugin = definition.getPlugin();
    }

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

    public boolean close(Player player) {
        return (closeEvent == null || closeEvent.close(player, this)) && InterfaceListeners.runClose(player, this);
    }

    public int getId() {
        return id;
    }

    public ComponentDefinition getDefinition() {
        return definition;
    }

    public CloseEvent getCloseEvent() {
        return closeEvent;
    }

    public Component setCloseEvent(CloseEvent closeEvent) {
        this.closeEvent = closeEvent;
        return this;
    }

    public static void setUnclosable(Player p, Component c) {
        p.setAttribute("close_c_", true);
        c.setCloseEvent(new CloseEvent() {
            @Override
            public boolean close(Player player, Component c) {
                return !player.getAttribute("close_c_", false);
            }
        });
    }

    public void setPlugin(ComponentPlugin plugin) {
        this.plugin = plugin;
    }

    public ComponentPlugin getPlugin() {
        if (plugin == null) {
            ComponentPlugin p = ComponentDefinition.forId(getId()).getPlugin();
            if ((plugin = p) != null) {
                return p;
            }
        }
        return plugin;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
