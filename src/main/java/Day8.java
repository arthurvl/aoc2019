import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {
    static int countLayersOfSize(int width, int height, String input) {
        return input.length()/(width*height);
    }

    public static int[][][] splitIntoLayersOfSize(int width, int height, String input) {
        int layerCount = countLayersOfSize(width,height,input);
        int[][][] layers = new int[layerCount][height][width];
        int charPos = 0;
        for (int l = 0; l < layerCount; l++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0 ; x < width; x++) {
                    layers[l][y][x] = Integer.valueOf(input.substring(charPos, charPos+1));
                    charPos++;
                }
            }

        }
        return layers;
    }

    public static long countDigits(int digit, int[] line) {
        return Arrays.stream(line).filter(d -> digit == d).count();
    }

    public static long countDigits(int digit, int[][] layer) {
        return Arrays.stream(layer).mapToLong(line -> countDigits(digit, line)).sum();
    }

    public static int[][] findLayerWithFewest(int digit, int[][][] layers) {
        int[][] foundLayer = new int[0][];
        long minCount = Long.MAX_VALUE;

        for(int[][] layer : Arrays.asList(layers)) {
            long digitCount = countDigits(digit,layer);
            if (digitCount < minCount) {
                foundLayer = layer;
                minCount = digitCount;
            }
        }

        return foundLayer;
    }

    public static void main(String[] args) {
        List<String> input = new BufferedReader(
                new InputStreamReader(
                        Day1.class.getResourceAsStream("day8/input"))).lines().collect(Collectors.toList());

        int[][][] layers = splitIntoLayersOfSize(25,6, input.get(0));

        int[][] smallestLayer = findLayerWithFewest(0, layers);

        System.out.println(countDigits(1,smallestLayer) * countDigits(2, smallestLayer));

        int[][] collapsedLayer = collapse(layers);

        for(int y = 0; y < 6; y++) {
            for (int x = 0; x < 25; x++) {
                System.out.print(collapsedLayer[y][x] == 1 ? "X" : " ");
            }
            System.out.println();
        }
    }

    public static int[][] collapse(int[][] topLayer, int[][] bottomLayer) {
        int[][] collapsedLayer = new int[topLayer.length][topLayer[0].length];
        for (int y = 0; y < topLayer.length; y++) {
            for (int x = 0; x < topLayer[0].length; x++) {
                collapsedLayer[y][x] = topLayer[y][x] != 2 ? topLayer[y][x] : bottomLayer[y][x];
            }
        }
        return collapsedLayer;
    }

    public static int[][] collapse(int[][][] layers) {
        int[][] collapsedLayer = layers[0];
        for (int[][] layer : Arrays.stream(layers).skip(1).collect(Collectors.toList())) {
            collapsedLayer = collapse(collapsedLayer, layer);
        }
        return collapsedLayer;
    }
}
