import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReadingsFileRepresentation implements Comparable<ReadingsFileRepresentation> {

    private Path filePath;
    private double[][] readings;
    public static final int MAX_NUMBER_OF_LINES = 136; //the value is read by me and it is fixed
    public static final int NUMBER_OF_CHANNELS = 22;
    private static final String INPUT_SEPARATOR = "\t";


    public ReadingsFileRepresentation() {

    }

    public ReadingsFileRepresentation(Path filePath) {
        this.filePath = filePath;

        try (Stream<String> lines = Files.lines(filePath)) {
            List<String> linesWithZeros = fullFillWithZeroLinesIfNeeded(
                    lines.collect(Collectors.toList()));
            String[][] linesArray = linesWithZeros.stream().map(x -> x.split(INPUT_SEPARATOR)).toArray(String[][]::new);
            this.readings = convertStringMatrixToDoubleArray(linesArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<String> fullFillWithZeroLinesIfNeeded(List<String> allLines) {
        int allLinesSize = allLines.size();
        if (allLinesSize < MAX_NUMBER_OF_LINES) {

            for (int i = 0; i < MAX_NUMBER_OF_LINES - allLinesSize; i++) {
                String emptyLine = createLineOfZeros();
                allLines.add(emptyLine);
            }
        }
        return allLines;

    }

    private String createLineOfZeros() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_CHANNELS; i++) {
            stringBuilder.append("0.0" + INPUT_SEPARATOR);
        }
        return stringBuilder.toString().trim();
    }

    public double[][] convertStringMatrixToDoubleArray(String[][] stringsMatrix) {
        int numberOfRows = stringsMatrix.length;
        int numberOfColumns = stringsMatrix[0].length;
        double[][] matrixOfDoubles = new double[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                matrixOfDoubles[i][j] = Double.parseDouble(stringsMatrix[i][j]);
            }
        }
        return matrixOfDoubles;
    }


    public double[][] getReadings() {
        return readings;
    }

    public Path getFilePath() {
        return filePath;
    }

    public double[] getColumn(int column) {
        double[] columnArray = new double[readings.length];
        IntStream.range(0, readings.length)
                .forEach(idx -> columnArray[idx]
                        = readings[idx][column]);
        return columnArray;
    }

    @Override
    public int compareTo(ReadingsFileRepresentation o) {
        return this.filePath.toString().compareTo(o.filePath.toString());
    }

    public void setReadings(double[][] readings) {
        this.readings = readings;
    }
}

