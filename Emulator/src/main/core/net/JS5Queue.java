package core.net;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Handles the JS5 queue for a session.
 * @author Techdaan
 */
public final class JS5Queue {

    /**
     * The constant RUNNING.
     */
    public static AtomicBoolean RUNNING = new AtomicBoolean(true);

    private static final Js5QueueHandler handler = new Js5QueueHandler();

    private static class Js5QueueHandler extends Thread {
        private final LinkedBlockingDeque<Js5Request> requests = new LinkedBlockingDeque<>();

        @Override
        public void run() {
            while (RUNNING.get()) {
                try {
                    Js5Request request = requests.take();

                    JS5Queue queue = request.queue;

                    if (queue.session.isActive()) {
                        queue.session.write(new int[]{request.index, request.archive, request.priority ? 1 : 0});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static {
        try {
            handler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Js5Request {
        private final JS5Queue queue;
        private final int index;
        private final int archive;
        private final boolean priority;

        /**
         * Constructs a new JS-5 request.
         *
         * @param queue    the queue
         * @param index    the index
         * @param archive  the archive
         * @param priority the priority
         */
        public Js5Request(JS5Queue queue, int index, int archive, boolean priority) {
            this.queue = queue;
            this.index = index;
            this.archive = archive;
            this.priority = priority;
        }
    }

    /**
     * The I/O session.
     */
    private final IoSession session;

    /**
     * Constructs a new JS-5 queue.
     *
     * @param session the session
     */
    public JS5Queue(IoSession session) {
        this.session = session;
    }

    /**
     * Queues a JS-5 request.
     * Timers:
     * 1000ms queue up files for this long
     * 100ms  release a file from the queue
     *
     * @param container    The container.
     * @param archive      The archive.
     * @param highPriority If the request is high priority.
     */
    public void queue(int container, int archive, boolean highPriority) {
        Js5Request request = new Js5Request(this, container, archive, highPriority);
        handler.requests.add(request);
    }

    /**
     * Gets session.
     *
     * @return the session
     */
    public IoSession getSession() {
        return session;
    }


}
