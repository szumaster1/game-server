package core.net;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Server socket connection.
 * @author Emperor
 */
public final class ServerSocketConnection {

    private final Selector selector; // Selector for handling channels

    private final ServerSocketChannel channel; // Server socket channel

    private final SocketChannel socket; // Socket channel for client connections

    /**
     * Instantiates a new Server socket connection.
     *
     * @param selector the selector
     * @param channel  the channel
     */
    public ServerSocketConnection(Selector selector, ServerSocketChannel channel) {
        this.selector = selector;
        this.channel = channel;
        this.socket = null; // Initialize socket as null for server connections
    }

    /**
     * Instantiates a new Server socket connection.
     *
     * @param selector the selector
     * @param channel  the channel
     */
    public ServerSocketConnection(Selector selector, SocketChannel channel) {
        this.selector = selector;
        this.socket = channel; // Assign socket channel for client connections
        this.channel = null; // Initialize server socket channel as null
    }

    /**
     * Checks if the connection is from a client.
     *
     * @return true if the connection is from a client, false otherwise
     */
    public boolean isClient() {
        return socket != null; // Returns true if the socket is not null (client connection)
    }

    /**
     * Gets the selector associated with the connection.
     *
     * @return the selector
     */
    public Selector getSelector() {
        return selector; // Returns the selector used in the connection
    }

    /**
     * Gets the server socket channel.
     *
     * @return the server socket channel
     */
    public ServerSocketChannel getChannel() {
        return channel; // Returns the server socket channel
    }

    /**
     * Gets the socket channel for client connections.
     *
     * @return the socket channel
     */
    public SocketChannel getSocket() {
        return socket; // Returns the socket channel for client connections
    }

}
