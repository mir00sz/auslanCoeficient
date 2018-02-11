import java.nio.file.Paths;

public class CoeffApplication {


    public static void main(String[] args) throws Exception {
        CorrelationCoefficientProcessor correlationCoefficientProcessor = new CorrelationCoefficientProcessor();

        correlationCoefficientProcessor
                .process(Paths.get("/home/mirek/auslanData/data/tctodd1"));


    }
}
