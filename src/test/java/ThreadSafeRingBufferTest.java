import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ThreadSafeRingBufferTest {


    @Test
    public void testPutAndGetElement() {

        final Object object = new Object();
        final ThreadSafeRingBuffer ringBuffer = new ThreadSafeRingBuffer(1);
        ringBuffer.put(object);
        assertSame(object, ringBuffer.get());
    }

    @Test
    public void testRetrievalOrder() {

        final ThreadSafeRingBuffer ringBuffer = new ThreadSafeRingBuffer(3);
        ringBuffer.put(1);
        ringBuffer.put(2);
        ringBuffer.put(3);

        assertEquals(1, ringBuffer.get());
        assertEquals(2, ringBuffer.get());
        assertEquals(3, ringBuffer.get());


    }

    @Test
    public void testRetrievalOrderPassFullBuffer() {

        final ThreadSafeRingBuffer ringBuffer = new ThreadSafeRingBuffer(3);
        ringBuffer.put(6);
        ringBuffer.put(7);
        ringBuffer.put(8);

        assertEquals(6, ringBuffer.get());
        ringBuffer.put(9);

        assertEquals(7, ringBuffer.get());
        assertEquals(8, ringBuffer.get());
        assertEquals(9, ringBuffer.get());
    }

    @Test
    public void testWriterBlocks() throws InterruptedException {
        final int maxSize = 3;
        final ThreadSafeRingBuffer threadSafeRingBuffer = new ThreadSafeRingBuffer(maxSize);
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final Thread thread = new Thread(() -> {

                threadSafeRingBuffer.put(0);
                atomicInteger.incrementAndGet();
                threadSafeRingBuffer.put(1);
                atomicInteger.incrementAndGet();
                threadSafeRingBuffer.put(2);
                atomicInteger.incrementAndGet();
                threadSafeRingBuffer.put(3);
                atomicInteger.incrementAndGet();


        });

        thread.start();
//these sleeps are not a guarantee but most likely will achieve the effect
        Thread.sleep(1000);

        assertEquals(3, atomicInteger.get());

        assertEquals(0, threadSafeRingBuffer.get());
        //these sleeps are not a guarantee but most likely will achieve the effect
        Thread.sleep(1000);
        assertEquals(4, atomicInteger.get());
        assertEquals(1, threadSafeRingBuffer.get());
        assertEquals(2, threadSafeRingBuffer.get());
        assertEquals(3, threadSafeRingBuffer.get());
    }

    @Test
    public void testReaderBlocks() throws InterruptedException {
        final int maxSize = 3;
        final ThreadSafeRingBuffer threadSafeRingBuffer = new ThreadSafeRingBuffer(maxSize);
        final BlockingQueue<Object> result = new LinkedBlockingQueue<>(10);
        final Thread thread = new Thread(() -> {

                result.add(threadSafeRingBuffer.get());
                result.add(threadSafeRingBuffer.get());
                result.add(threadSafeRingBuffer.get());
                result.add(threadSafeRingBuffer.get());


        });

        thread.start();
//these sleeps are not a guarantee but most likely will achieve the effect
        Thread.sleep(1000);

        assertEquals(0, result.size());
        threadSafeRingBuffer.put(0);
        threadSafeRingBuffer.put(1);
        threadSafeRingBuffer.put(2);
        threadSafeRingBuffer.put(3);

        //these sleeps are not a guarantee but most likely will achieve the effect
        Thread.sleep(1000);
        assertEquals(4, result.size());
        assertEquals(0, (int) result.poll());
        assertEquals(1, (int) result.poll());
        assertEquals(2, (int) result.poll());
        assertEquals(3, (int) result.poll());


    }

}
