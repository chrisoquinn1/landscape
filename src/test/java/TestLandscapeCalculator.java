import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class TestLandscapeCalculator {
    private final LandscapeCalculator landscapeCalculator = new LandscapeCalculator();

    @Test
    public void testSampleGiven() {


        assertEquals(9, landscapeCalculator.calculateWaterAmount(new int[]{5, 2, 3, 4, 5, 4, 0, 3, 1}));
    }

    @Test
    public void testDescending() {


        assertEquals(10, landscapeCalculator.calculateWaterAmount(new int[]{5, 4, 3, 2, 1, 5}));
    }

    @Test
    public void testMultipleDescending() {


        assertEquals(39, landscapeCalculator.calculateWaterAmount(new int[]{0, 6, 4, 4, 2, 2, 1, 1, 1, 0, 0, 6, 0, 0, 0}));
    }

    @Test
    public void testMultipleAscending() {


        assertEquals(39, landscapeCalculator.calculateWaterAmount(new int[]{0, 6, 0, 0, 1, 1, 1, 2, 2, 4, 4, 6, 0, 0, 0}));
    }

    @Test
    public void testSpike() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{0,0,4,30000,5,0}));
    }

    @Test
    public void testAscending() {


        assertEquals(10, landscapeCalculator.calculateWaterAmount(new int[]{5, 1, 2, 3, 4, 5}));
    }

    @Test
    public void testDoubleYouShape() {


        assertEquals(26, landscapeCalculator.calculateWaterAmount(new int[]{6, 0, 0, 4, 0, 0, 6}));
    }

    @Test
    public void testPiano() {


        assertEquals(3, landscapeCalculator.calculateWaterAmount(new int[]{1, 0, 1, 0, 1, 0, 1}));
    }

    @Test
    public void testHillsAndValleys() {


        assertEquals(15, landscapeCalculator.calculateWaterAmount(new int[]{6, 2, 5, 6, 1, 2, 1, 2, 4, 0}));
    }

    @Test
    public void testHillsAscendToNothing() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{0, 1, 2}));
    }

    @Test
    public void testHillsDescendToNothing() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{2, 1, 0}));
    }

    @Test
    public void testDescendingLongestAndHighest() {

        final int[] landscape = new int[LandscapeCalculator.LARGEST_LENGTH];
        landscape[LandscapeCalculator.LARGEST_LENGTH - 1] = LandscapeCalculator.LARGEST_HEIGHT;
        final int newLength = landscape.length - 1;
        for (int i = 0; i < newLength; i++) {
            landscape[i] = LandscapeCalculator.LARGEST_HEIGHT - i;
        }

        assertEquals(511952001, landscapeCalculator.calculateWaterAmount(landscape));
    }

    @Test
    public void testLargestPossiblePit() {


        final int[] landscape = new int[LandscapeCalculator.LARGEST_LENGTH];
        landscape[0] = LandscapeCalculator.LARGEST_HEIGHT;
        landscape[LandscapeCalculator.LARGEST_LENGTH - 1] = LandscapeCalculator.LARGEST_HEIGHT;
        assertEquals(1023936000, landscapeCalculator.calculateWaterAmount(landscape));
    }

    @Test
    public void testNoPit() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{5, 5, 5}));
    }

    @Test
    public void testDifferentValleys() {


        assertEquals(17, landscapeCalculator.calculateWaterAmount(new int[]{4, 3, 2, 1, 2, 3, 4, 2, 0, 2, 4, 1, 0, 0, 0}));
    }

    @Test
    public void testNoPitNoHeight() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{0, 0, 0}));
    }

    @Test
    public void testMiddle() {


        assertEquals(4, landscapeCalculator.calculateWaterAmount(new int[]{5, 5, 5, 5, 1, 5, 5, 5, 5}));
    }

    @Test
    public void testSmallFirstHill() {


        assertEquals(1, landscapeCalculator.calculateWaterAmount(new int[]{0, 0, 0, 5, 6, 5, 6}));
    }

    @Test
    public void testNoPitNoHeightMaxLength() {


        final int[] landscape = new int[LandscapeCalculator.LARGEST_LENGTH];
        Arrays.fill(landscape, 1);

        assertEquals(0, landscapeCalculator.calculateWaterAmount(landscape));
    }

    @Test
    public void testNotValidLandscapeNegativeHeights() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{0, -1, 0}));
    }

    @Test
    public void testNotValidLandscapeTooHigh() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[]{0, 0, LandscapeCalculator.LARGEST_HEIGHT + 1}));
    }

    @Test
    public void testNotValidLandscapeTooLong() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[LandscapeCalculator.LARGEST_LENGTH + 1]));
    }

    @Test
    public void testNotValidLandscapeTooShort() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[0]));
    }

    @Test
    public void testNotValidLandscapeTooShort2() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[1]));
    }

    @Test
    public void testNotValidLandscapeTooShort3() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(new int[2]));
    }

    @Test
    public void testNotValidLandscapeNull() {


        assertEquals(0, landscapeCalculator.calculateWaterAmount(null));
    }
}
