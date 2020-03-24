import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadSafeRingBuffer {
    /*
    // 1. FIFO
// 2. Ring buffer, use all space in bufferHow ring buffer works:
e.g.
new buffer, size = 3,
- empty buffer _,_,_
- get -> no data, buffer is empty, throw exception
- put 6 ->     6,_,_
- put 7 ->     6,7,_
- put 8 ->     6,7,8
- put 9 -> buffer is full, throw exception
- get -> 6     _,7,8
- put 9 ->    9,7,8
- get -> 7     9,_,8



// 3. blocking when no free place (on put) or data (on get)
// 4. Thread safe


*/
    private int currentSize = 0;
    private int currentWritePos = 0;
    private int currentReadPos = 0;
    private final Object lock = new Object();
    private static final Logger logger = LoggerFactory.getLogger(ThreadSafeRingBuffer.class);

    private final Object[] data;
    private final long waitTimeoutInMillis;

    //can put nulls/get nulls
    public ThreadSafeRingBuffer(int maxBufSize) {
        this(maxBufSize, 200);
    }

    private ThreadSafeRingBuffer(final int maxBufSize, final long timeout) {
        this.waitTimeoutInMillis = timeout;
        data = new Object[maxBufSize];
    }

    boolean isFull() {
        return currentSize == data.length;
    }

    public void put(Object object) {
        synchronized (lock) {
            while (isFull()) {
                try {
                    lock.wait(waitTimeoutInMillis);
                } catch (final InterruptedException e) {
                    logger.error("Thread interrupted.", e);
                    Thread.currentThread().interrupt();
                }
            }
            data[currentWritePos] = object;
            currentWritePos = (currentWritePos + 1) % data.length;
            currentSize++;
            lock.notifyAll();
        }
    }

    boolean isEmpty() {
        return currentSize == 0;
    }

    public Object get() {
        final Object read;
        synchronized (lock) {
            while (isEmpty()) {
                try {
                    lock.wait(waitTimeoutInMillis);
                } catch (final InterruptedException e) {
                    logger.error("Thread interrupted.", e);
                    Thread.currentThread().interrupt();
                }
            }
            read = data[currentReadPos];
            currentReadPos = (currentReadPos + 1) % data.length;
            currentSize--;
            lock.notifyAll();
        }

        return read;
    }

}





