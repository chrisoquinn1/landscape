import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;

public class RingBufferTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test
    public void testEmptyBufferThrowsException(){
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Buffer is empty");
        final RingBuffer ringBuffer = new RingBuffer(1);
        ringBuffer.get();
    }

    @Test
    public void testPlaceInFullBuffer(){
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Buffer is full. Current size: 0, Max size: 0");
        final RingBuffer ringBuffer = new RingBuffer(0);
        ringBuffer.put(new Object());
    }

    @Test
    public void testPutAndGetElement(){

        final Object object = new Object();
        final RingBuffer ringBuffer = new RingBuffer(1);
        ringBuffer.put(object);
        assertSame(object, ringBuffer.get());
    }

    @Test
    public void testRetrievalOrder(){

        final RingBuffer ringBuffer = new RingBuffer(3);
        ringBuffer.put(1);
        ringBuffer.put(2);
        ringBuffer.put(3);

        assertEquals(1, ringBuffer.get());
        assertEquals(2, ringBuffer.get());
        assertEquals(3, ringBuffer.get());


    }

    @Test
    public void testRetrievalOrderPassFullBuffer(){

        final RingBuffer ringBuffer = new RingBuffer(3);
        ringBuffer.put(6);
        ringBuffer.put(7);
        ringBuffer.put(8);

        assertEquals(6,ringBuffer.get());
        ringBuffer.put(9);

        assertEquals(7,ringBuffer.get());
        assertEquals(8,ringBuffer.get());
        assertEquals(9,ringBuffer.get());



    }

}
