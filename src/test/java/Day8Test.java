import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day8Test {
    @Test
    public void countsLayers() {
        int layerCount = Day8.countLayersOfSize(3,2,"123456789012");

        assertEquals(2,layerCount);
    }

    @Test
    public void splitsToLayers() {
        int[][][] layers = Day8.splitIntoLayersOfSize(3,2,"123456789012");

        int[][][] expected = new int[][][] {{{1,2,3},{4,5,6}},{{7,8,9},{0,1,2}}};

        assertArrayEquals(expected,layers);
    }

    @Test
    public void countsDigitsInLine() {
        int[] line = new int[] {1,1,1,1,1,1,0,0,0,0,0,0,2,2,2};

        long actualOnes = Day8.countDigits(1,line);
        long actualTwos = Day8.countDigits(2,line);
        long actualZeros = Day8.countDigits(0,line);

        assertEquals(6,actualOnes);
        assertEquals(3,actualTwos);
        assertEquals(6,actualZeros);
    }

    @Test
    public void countsDigitsInLayer() {
        int[][] layer = new int [][]{{1,1,1},{1,1,0},{0,0,1},{0,0,0},{2,2,2}};

        long actualOnes = Day8.countDigits(1,layer);
        long actualTwos = Day8.countDigits(2,layer);
        long actualZeros = Day8.countDigits(0,layer);

        assertEquals(6,actualOnes);
        assertEquals(3,actualTwos);
        assertEquals(6,actualZeros);
    }

    @Test
    public void findsSmallestLayer() {
        int[][][] layers = Day8.splitIntoLayersOfSize(3,2,"123456789012");

        int[][] expected = new int [][] {{1,2,3},{4,5,6}};

        int[][] actual = Day8.findLayerWithFewest(0, layers);

        assertArrayEquals(expected,actual);
    }

    @Test
    public void collapsesTwoLayers() {
        int[][] topLayer = new int[][] {{0,2},{2,2}};
        int[][] bottomLayer = new int[][] {{1,1},{1,0}};

        int[][] collapsed = Day8.collapse(topLayer, bottomLayer);

        int[][] expected = new int [][] {{0,1},{1,0}};

        assertArrayEquals(expected, collapsed);
    }

    @Test
    public void collapsesAllLayers() {
        int[][][] layers = Day8.splitIntoLayersOfSize(2,2, "0222112222120000");

        int[][] expected = new int[][] {{0,1},{1,0}};

        int[][] collapsed = Day8.collapse(layers);

        assertArrayEquals(expected,collapsed);
    }
}