import java.util.concurrent.atomic.AtomicInteger;

public class RingBuffer {
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
*/
    private int currentSize = 0;
    private int currentWritePos = 0;
    private int currentReadPos = 0;

    private final Object[] data;

    //can put nulls/get nulls
    public RingBuffer(int maxBufSize) {
        data = new Object[maxBufSize];
    }

    public void put(Object object) {
        validateSize();

        data[currentWritePos] = object;
        currentWritePos = (currentWritePos + 1) % data.length;
        currentSize++;

    }

    private void validateSize() {
        if (currentSize >= data.length) {
            throw new IllegalStateException(String.format("Buffer is full. Current size: %d, Max size: %d", currentSize, data.length));
        }

    }

    public Object get() {
        validateGet();

        final Object read = data[currentReadPos];
        currentReadPos = (currentReadPos + 1) % data.length;
        currentSize--;
        return read;
    }

    private void validateGet() {
        if (currentSize == 0) {

            throw new IllegalStateException("Buffer is empty");
        }
    }
}





