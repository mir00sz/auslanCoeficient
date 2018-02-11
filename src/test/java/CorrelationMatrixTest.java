import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CorrelationMatrixTest {

    private List<String[]> templateLines;

    @Before
    public void setUp() {
        templateLines = new ArrayList<>();
        String[] firstLine = {"", "alpha", "beta", "gamma"};
        String[] secondLine = {"alpha", "0.3", "0.43", "0.9"};
        String[] thirdLine = {"beta", "0.4", "0.4", "0.11"};
        String[] fourthLine = {"gamma", "0.3", "0.2", "0.1"};
        templateLines.add(firstLine);
        templateLines.add(secondLine);
        templateLines.add(thirdLine);
        templateLines.add(fourthLine);
    }


    @Test
    public void convertMatrixToListOfLines() throws Exception {
        List<String> headerColumn = new ArrayList<>(Arrays.asList("alpha", "beta", "gamma"));
        List<String> headerLine = new ArrayList<>(Arrays.asList("alpha", "beta", "gamma"));

        double[][] matrix = {{0.3, 0.43, 0.9}, {0.4, 0.4, 0.11}, {0.3, 0.2, 0.1}};
        CorrelationMatrix correlationMatrix = CorrelationMatrix.aCorrelationMatrix()
                .withLineTitles(headerLine)
                .withColumnTitles(headerColumn)
                .withChannel(1)
                .build();
        correlationMatrix.setMatrix(matrix);

        List<String[]> lines = correlationMatrix.convertMatrixToListOfLines();

        for (String[] line : lines) {
            assertArrayEquals(line, templateLines.get(lines.indexOf(line)));
        }
    }


    @Test
    public void shouldProperlyAddCorrelation() throws Exception {
        String firstLineTitle = "FirstLine";
        String secondLineTitle = "SecondLine";
        String thirdLineTitle = "ThirdLine";

        String firstColumnTitle = "FirstColumn";
        String secondColumnTitle = "SecondColumn";
        String thirdColumnTitle = "ThirdColumn";

        List<String> lineTitles = Arrays.asList(firstLineTitle,secondLineTitle,thirdLineTitle);
        List<String> columnTitles=Arrays.asList(firstColumnTitle,secondColumnTitle,thirdColumnTitle);

        CorrelationMatrix correlationMatrix = CorrelationMatrix.aCorrelationMatrix()
                .withLineTitles(lineTitles)
                .withColumnTitles(columnTitles)
                .build();

        correlationMatrix.addCorrelation(firstColumnTitle, firstLineTitle, .1);
        correlationMatrix.addCorrelation(secondColumnTitle,firstLineTitle,.2);
        correlationMatrix.addCorrelation(thirdColumnTitle,firstLineTitle,.3);

        correlationMatrix.addCorrelation(firstColumnTitle,secondLineTitle,.4);
        correlationMatrix.addCorrelation(secondColumnTitle,secondLineTitle,.5);
        correlationMatrix.addCorrelation(thirdColumnTitle,secondLineTitle,.6);

        correlationMatrix.addCorrelation(firstColumnTitle,thirdLineTitle,.7);
        correlationMatrix.addCorrelation(secondColumnTitle,thirdLineTitle,.8);
        correlationMatrix.addCorrelation(thirdColumnTitle,thirdLineTitle,.9);

        CorrelationMatrix correlationMatrix2 = CorrelationMatrix.aCorrelationMatrix()
                .withLineTitles(Arrays.asList(firstLineTitle,secondLineTitle,thirdLineTitle))
                .withColumnTitles(Arrays.asList(firstColumnTitle,secondColumnTitle,thirdColumnTitle))
                .build();

        double[][] mm = {{.1,.2,.3}, {.4,.5,.6}, {.7,.8,.9}};
        correlationMatrix2.setMatrix(mm);
        correlationMatrix2.setLineTitles(lineTitles);
        correlationMatrix2.setColumnTitles(columnTitles);

        assertEquals("Should be: "
                + System.lineSeparator()
                + correlationMatrix2.toString()
                +System.lineSeparator()
                + "But there is: "
                + correlationMatrix.toString(),

                correlationMatrix,correlationMatrix2);

    }
}