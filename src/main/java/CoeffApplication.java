import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CoeffApplication {


    public static void main(String[] args) throws Exception {


        Stream<Path> files = Files.list(Paths.get(args[0]));
        CorrelationCoefficientProcessor correlationCoefficientProcessor = new CorrelationCoefficientProcessor(Paths.get(args[1]));

        files.forEach(x -> {
            try {
                correlationCoefficientProcessor.process(x);
            } catch (IOException | MatrixDimensionsException e) {
                e.printStackTrace();
            }
        });


    }
}
