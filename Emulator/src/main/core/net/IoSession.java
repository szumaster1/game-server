package core.net;

import core.auth.AuthResponse;
import core.cache.crypto.ISAACPair;
import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.ClientInfo;
import core.game.world.repository.Repository;
import core.net.producer.HSEventProducer;
import core.net.producer.LoginEventProducer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a connected I/O session.
 * @author Emperor
 */
public class IoSession {

    private static final EventProducer HANDSHAKE_PRODUCER = new HSEventProducer();

    private final SelectionKey key;

    /**
     * The Isaac input opcode.
     */
    public int isaacInputOpcode = 0;

    private final ExecutorService service;

    private EventProducer producer = HANDSHAKE_PRODUCER;

    private List<ByteBuffer> writingQueue = new ArrayList<>(20);

    private ByteBuffer readingQueue;

    private Lock writingLock = new ReentrantLock();

    private ISAACPair isaacPair;

    private int nameHash;

    private long serverKey;

    private int js5Encryption;

    private Object object;

    private boolean active = true;

    private long lastPing;

    private final String address;

    private final JS5Queue js5Queue;

    private ClientInfo clientInfo;

    /**
     * The Associated username.
     */
    public String associatedUsername;

    /**
     * Constructs a new Io session.
     *
     * @param key     the key
     * @param service the service
     */
    public IoSession(SelectionKey key, ExecutorService service) {
        this.key = key;
        this.service = service;
        this.address = getRemoteAddress().replaceAll("/", "").split(":")[0];
        this.js5Queue = new JS5Queue(this);
    }

    /**
     * Write.
     *
     * @param context the context
     */
    public void write(Object context) {
        write(context, false);
    }

    /**
     * Write.
     *
     * @param context the context
     * @param instant the instant
     */
    public void write(Object context, boolean instant) {
        if (context == null) {
            throw new IllegalStateException("Invalid writing context!");
        }
        if (!(context instanceof AuthResponse) && producer instanceof LoginEventProducer) {
            // new Throwable().printStackTrace();
            return;
        }
        if (instant) {
            producer.produceWriter(this, context).run();
            return;
        }
        service.execute(producer.produceWriter(this, context));
    }

    /**
     * Queue.
     *
     * @param buffer the buffer
     */
    public void queue(ByteBuffer buffer) {
        try {
            writingLock.tryLock(1000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            writingLock.unlock();
        }
        writingQueue.add(buffer);
        writingLock.unlock();
        write();
    }

    /**
     * Write.
     */
    public void write() {
        if (!key.isValid()) {
            disconnect();
            return;
        }
        try {
            writingLock.tryLock(1000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            writingLock.unlock();
            return;
        }
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            while (!writingQueue.isEmpty()) {
                ByteBuffer buffer = writingQueue.get(0);
                channel.write(buffer);
                if (buffer.hasRemaining()) {
                    key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                    break;
                }
                writingQueue.remove(0);
            }
        } catch (IOException e) {
            disconnect();
        }
        writingLock.unlock();
    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        try {
            if (!active) {
                return;
            }
            active = false;
            key.cancel();
            SocketChannel channel = (SocketChannel) key.channel();
            channel.socket().close();
            if (getPlayer() != null) {
                try {
                    getPlayer().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            object = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets remote address.
     *
     * @return the remote address
     */
    public String getRemoteAddress() {
        try {
            return ((SocketChannel) key.channel()).getRemoteAddress().toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Gets producer.
     *
     * @return the producer
     */
    public EventProducer getProducer() {
        return producer;
    }

    /**
     * Sets producer.
     *
     * @param producer the producer
     */
    public void setProducer(EventProducer producer) {
        this.producer = producer;
    }

    /**
     * Gets reading queue.
     *
     * @return the reading queue
     */
    public ByteBuffer getReadingQueue() {
        synchronized (this) {
            return readingQueue;
        }
    }

    /**
     * Sets reading queue.
     *
     * @param readingQueue the reading queue
     */
    public void setReadingQueue(ByteBuffer readingQueue) {
        synchronized (this) {
            this.readingQueue = readingQueue;
        }
    }

    /**
     * Gets writing lock.
     *
     * @return the writing lock
     */
    public Lock getWritingLock() {
        return writingLock;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public SelectionKey getKey() {
        return key;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets js 5 encryption.
     *
     * @return the js 5 encryption
     */
    public int getJs5Encryption() {
        return js5Encryption;
    }

    /**
     * Sets js 5 encryption.
     *
     * @param js5Encryption the js 5 encryption
     */
    public void setJs5Encryption(int js5Encryption) {
        this.js5Encryption = js5Encryption;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        if (object == null) {
            object = Repository.getPlayerByName(associatedUsername);
        }
        return object instanceof Player ? ((Player) object) : null;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Sets object.
     *
     * @param object the object
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Gets last ping.
     *
     * @return the last ping
     */
    public long getLastPing() {
        return lastPing;
    }

    /**
     * Sets last ping.
     *
     * @param lastPing the last ping
     */
    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    /**
     * Gets name hash.
     *
     * @return the name hash
     */
    public int getNameHash() {
        return nameHash;
    }

    /**
     * Sets name hash.
     *
     * @param nameHash the name hash
     */
    public void setNameHash(int nameHash) {
        this.nameHash = nameHash;
    }

    /**
     * Gets server key.
     *
     * @return the server key
     */
    public long getServerKey() {
        return serverKey;
    }

    /**
     * Sets server key.
     *
     * @param serverKey the server key
     */
    public void setServerKey(long serverKey) {
        this.serverKey = serverKey;
    }

    /**
     * Gets isaac pair.
     *
     * @return the isaac pair
     */
    public ISAACPair getIsaacPair() {
        return isaacPair;
    }

    /**
     * Sets isaac pair.
     *
     * @param isaacPair the isaac pair
     */
    public void setIsaacPair(ISAACPair isaacPair) {
        this.isaacPair = isaacPair;
    }

    /**
     * Gets js 5 queue.
     *
     * @return the js 5 queue
     */
    public JS5Queue getJs5Queue() {
        return js5Queue;
    }

    /**
     * Gets client info.
     *
     * @return the client info
     */
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    /**
     * Sets client info.
     *
     * @param clientInfo the client info
     */
    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

}
