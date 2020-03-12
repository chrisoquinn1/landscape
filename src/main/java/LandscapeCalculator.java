import java.util.Arrays;
import java.util.Stack;

class LandscapeCalculator {
    final static int LARGEST_HEIGHT = 32_000;
    final static int LARGEST_LENGTH = LARGEST_HEIGHT;

    /**
     *
     * @param landscape landscape
     * @return amount of water landscape can hold
     */
    int calculateWaterAmount(int[] landscape) {

        final int result;
        if (validLandscape(landscape)) {
            result = calculateWaterAmountHelper(landscape);
        } else {
            result = 0;
        }
        return result;

    }

    /**
     *
     * @param landscape valid landscape
     * @return amount of water landscape can hold
     */
    private int calculateWaterAmountHelper(final int[] landscape) {
        final Stack<Coordinate> stack = new Stack<>();


        final int result;
        final int positionOfFirstHillStart = getStartOfFirstValidHill(landscape);
        if (positionOfFirstHillStart >= 0) {

            int totalWater = 0;
            stack.push(new Coordinate(positionOfFirstHillStart, landscape[positionOfFirstHillStart]));
            for (int i = positionOfFirstHillStart + 1; i < landscape.length; i++) {

                final int currentHeight = landscape[i];

                final Coordinate previousHill = stack.peek();
                final int previousHillHeight = previousHill.getHeight();

                if (currentHeight > previousHillHeight) {
                    totalWater += handleCurrentHillIsHigherThanPrevious(stack, currentHeight, i);
                } else if (currentHeight < previousHillHeight) {
                    stack.push(new Coordinate(i, currentHeight));
                } else {
                    stack.pop();
                    stack.push(new Coordinate(i, currentHeight));
                }


            }
            result = totalWater;
        } else {
            result = 0;
        }

        return result;

    }

    /**
     *
     * @param stack stack with previous hills and pits
     * @param currentHeight current height of current hill
     * @param position current position in the landscape
     * @return amount of water from pit when the current hill is higher than the hill before the pit
     */
    private int handleCurrentHillIsHigherThanPrevious(final Stack<Coordinate> stack, final int currentHeight, final int position) {
        final Coordinate pit = stack.pop();
        int totalWater = 0;
        final Coordinate hillBeforePit = stack.peek();

        final int hillBeforePitHeight = hillBeforePit.getHeight();

        if (hillBeforePitHeight < currentHeight) {

            totalWater += getWaterFromPreviousHills(stack, pit, new Coordinate(position, currentHeight));

        } else {
            final int currentHeightAbovePit = currentHeight - pit.getHeight();
            final int distanceToPreviousHill = pit.getPosition() - hillBeforePit.getPosition();
            totalWater += currentHeightAbovePit * distanceToPreviousHill;
            if (hillBeforePitHeight == currentHeight) {
                stack.pop(); //discard same height hill

            }

        }

        stack.push(new Coordinate(position, currentHeight));
        return totalWater;
    }

    /**
     *
     * @param stack stack with previous hills and pits
     * @param pit current pit
     * @param currentHill current hill
     * @return water from previous hills that form a pit with current hill
     */
    private int getWaterFromPreviousHills(final Stack<Coordinate> stack,
                                          Coordinate pit,
                                          final Coordinate currentHill) {
        int totalWater = 0;
        do {
            final Coordinate hillBeforePit = stack.pop();
            final int currentHeightAbovePit = hillBeforePit.getHeight() - pit.getHeight();
            final int distanceToPreviousHill = currentHill.getPosition() - hillBeforePit.getPosition() - 1;
            totalWater += currentHeightAbovePit * distanceToPreviousHill;
            pit = hillBeforePit;

        } while (!stack.empty() && stack.peek().getHeight() <= currentHill.getHeight());


        return totalWater;
    }

    /**
     *
     * @param landscape landscape
     * @return start of first valid hill. A hill is valid if it has a successive hill of less height
     */
    private int getStartOfFirstValidHill(final int[] landscape) {
        int position = -1;
        final int newLength = landscape.length - 1;
        for (int i = 0; i < newLength; i++) {

            final int currentHeight = landscape[i];
            final int nextHeight = landscape[i + 1];
            if (currentHeight > 0 && nextHeight < currentHeight) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     *
     * @param landscape landscape
     * @return true if landscape is valid
     */
    private boolean validLandscape(final int[] landscape) {
        return landscape != null
                && landscape.length >= 3
                && landscape.length <= LARGEST_LENGTH
                && isAllNonNegative(landscape)
                && !violatesMaxHeight(LARGEST_HEIGHT, landscape);

    }

    /**
     *
     * @param maxHeight max height allowed
     * @param landscape landscape
     * @return true if violates max height
     */
    private boolean violatesMaxHeight(final int maxHeight, final int[] landscape) {
        return Arrays.stream(landscape).filter(num -> num > maxHeight).findAny().isPresent();
    }

    /**
     *
     * @param landscape landscape
     * @return true if all heights are non-negative
     */
    private boolean isAllNonNegative(final int[] landscape) {
        return !Arrays.stream(landscape).filter(num -> num < 0).findAny().isPresent();
    }
}
