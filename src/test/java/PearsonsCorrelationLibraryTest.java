import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.junit.Test;

public class PearsonsCorrelationLibraryTest {




    @Test
    public void shouldReturnPearsonCorrelation(){
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        double[] d = {0.0,0.0,0.0};
        double[] d1 = {.2,.3,.4};
        double result = pearsonsCorrelation.correlation(d,d1);
        System.out.println(result);


    }
}
