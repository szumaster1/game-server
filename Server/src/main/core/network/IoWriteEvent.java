package core.network;

import java.nio.channels.CancelledKeyException;

/**
 * Handles a writing event.
 * @author Emperor
 */
public abstract class IoWriteEvent implements Runnable {

    /**
     * The I/O session.
     */
    private final IoSession session;

    /**
     * The buffer.
     */
    private final Object context;

    /**
     * Instantiates a new Io write event.
     *
     * @param session The session.
     * @param context The write event context.
     */
    public IoWriteEvent(IoSession session, Object context) {
        this.session = session;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            write(session, context); // Call the write method with session and context parameters.
        } catch (Throwable t) {
            if (!(t instanceof CancelledKeyException)) {
                t.printStackTrace(); // Print the stack trace if an exception occurs.
            }
            session.disconnect(); // Disconnect the session in case of an exception.
        }
    }

    /**
     * Writes the data.
     * @param session The session.
     * @param context The write event context.
     */
    public abstract void write(IoSession session, Object context);

}
