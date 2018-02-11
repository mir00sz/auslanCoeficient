import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CorrelationMatrix {

    private List<String> lineTitles;
    private List<String> columnTitles;
    private double[][] matrix;
    private int channelNumber;


    public List<String[]> convertMatrixToListOfLines() {
        List<String[]> lines = new ArrayList<>();

        String[] header = createHeader();
        lines.add(header);

        for (int i = 0; i < matrix.length; i++) {

            double[] matrixLine = matrix[i];
            List<String> line = new ArrayList<>();
            line.add(columnTitles.get(i));

            for (double val : matrixLine) {
                String strVal = String.valueOf(val);
                line.add(strVal);
            }
            lines.add(line.toArray(new String[line.size()]));
        }
        return lines;

    }

    private String[] createHeader() {
        List<String> header = new ArrayList<>();
        header.add("");
        header.addAll(lineTitles);
        return header.toArray(new String[header.size()]);
    }

    public static CorrelationMatrixBuilder aCorrelationMatrix() {
        return new CorrelationMatrixBuilder();
    }

    //getters and setters
    private List<String> getLineTitles() {
        return lineTitles;
    }

    public void setLineTitles(List<String> lineTitles) {
        this.lineTitles = lineTitles;
    }

    private List<String> getColumnTitles() {
        return columnTitles;
    }

    public void setColumnTitles(List<String> columnTitles) {
        this.columnTitles = columnTitles;
    }

    private double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) throws MatrixDimensionsException {


        if (matrix.length != lineTitles.size() ) {
            throw new MatrixDimensionsException("Wrong matrix Size Exception. Expected size:"
                    + System.lineSeparator()
                    + "[" + lineTitles.size() + "]" + "[" + columnTitles.size() + "]."
                    + System.lineSeparator()
                    + "But found:"
                    + System.lineSeparator()
                    + "[" + matrix.length + "]" + "[" + matrix[0].length + "]");
        }
        this.matrix = matrix;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    private void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public void addCorrelation(String columnTitle, String lineTitle, double correlation) {
        matrix[lineTitles.indexOf(lineTitle)][columnTitles.indexOf(columnTitle)] = correlation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorrelationMatrix that = (CorrelationMatrix) o;
        return getChannelNumber() == that.getChannelNumber() &&
                Objects.equals(getLineTitles(), that.getLineTitles()) &&
                Objects.equals(getColumnTitles(), that.getColumnTitles()) &&
                Arrays.deepEquals(getMatrix(), that.getMatrix());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getLineTitles(), getColumnTitles(), getChannelNumber());
        result = 31 * result + Arrays.hashCode(getMatrix());
        return result;
    }

    @Override
    public String toString() {
        return "CorrelationMatrix{" +
                "matrix=" + Arrays
                .stream(matrix)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator())) +
                '}';
    }

    static class CorrelationMatrixBuilder {
        private List<String> lineTitles;
        private List<String> columnTitles;
        int channelNumber;


        private CorrelationMatrixBuilder() {
        }

        public CorrelationMatrixBuilder withLineTitles(List<String> header) {
            this.lineTitles = header;
            return this;
        }

        public CorrelationMatrixBuilder withColumnTitles(List<String> headerColumn) {
            this.columnTitles = headerColumn;
            return this;
        }

        public CorrelationMatrixBuilder withChannel(int channel) {
            this.channelNumber = channel;
            return this;
        }

        public CorrelationMatrix build() throws MatrixDimensionsException {
            CorrelationMatrix correlationMatrix = new CorrelationMatrix();
            correlationMatrix.setChannelNumber(this.channelNumber);
            correlationMatrix.setColumnTitles(this.columnTitles);
            correlationMatrix.setLineTitles(this.lineTitles);
            correlationMatrix.setMatrix(new double[lineTitles.size()][columnTitles.size()]);
            return correlationMatrix;
        }

    }
}
