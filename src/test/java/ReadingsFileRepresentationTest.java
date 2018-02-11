import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class ReadingsFileRepresentationTest {


    @Test
    public void shouldReadMatrixCorrectly() {
        Path testFilePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("alive-1.tsd")).getFile());
        ReadingsFileRepresentation readingsFileRepresentation = new ReadingsFileRepresentation(testFilePath);
        double[][] readings = readingsFileRepresentation.getReadings();
        assertTrue("Expected number of rows is: " +
                        ReadingsFileRepresentation.MAX_NUMBER_OF_LINES +
                        " but there is " +
                        readings.length,
                readings.length == ReadingsFileRepresentation.MAX_NUMBER_OF_LINES);

        assertTrue("Expected number of columns is: "
                + ReadingsFileRepresentation.NUMBER_OF_CHANNELS
                + " but there is: " + readings[0].length, readings[0].length == ReadingsFileRepresentation.NUMBER_OF_CHANNELS);
    }


    @Test
    public void shouldTransformMatrixOfStringsIntoMatrixOfDoubles() {
        String[][] matrixOfStrings = new String[][]{
                new String[]{"1", "2", "3"},
                new String[]{"4", "5", "6"},
        };

        ReadingsFileRepresentation readingsFileRepresentation = new ReadingsFileRepresentation();
        double[][] matrixOfDoubles = readingsFileRepresentation.convertStringMatrixToDoubleArray(matrixOfStrings);
        for (int i = 0; i < matrixOfDoubles.length; i++) {
            for (int j = 0; j < matrixOfDoubles[i].length; j++) {
                assertTrue(Double.parseDouble(matrixOfStrings[i][j]) == matrixOfDoubles[i][j]);
            }
        }

    }

    @Test
    public void shouldReturnArrayRepresentingAColumnOfMatrix() {
        double[][] matrixOfReadings = new double[][]{
                new double[]{.1, .2, .3},
                new double[]{.4, .5, .6},
        };
        ReadingsFileRepresentation readingsFileRepresentation = new ReadingsFileRepresentation();
        readingsFileRepresentation.setReadings(matrixOfReadings);
        double[] res = readingsFileRepresentation.getColumn(1);
        assertArrayEquals(res, new double[]{.2, .5}, 2);
    }

}