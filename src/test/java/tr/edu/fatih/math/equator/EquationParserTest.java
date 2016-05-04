package tr.edu.fatih.math.equator;

import org.junit.Test;
import tr.edu.fatih.math.equator.exception.ZeroStringException;

import static org.junit.Assert.assertEquals;

public class EquationParserTest {

    @Test
    public void testEquator_withSampleEquation() throws ZeroStringException {
        EquationParser equationParser = new EquationParser("5+2*12");
        String parsedString = equationParser.parse();

        assertEquals(parsedString, "29");
    }

    @Test
    public void testEquator_withComplexEquation() throws ZeroStringException {
        EquationParser equationParser = new EquationParser("(3+2*(1*(3+4+5)))");
        String parsedString = equationParser.parse();

        assertEquals(parsedString, "27");
    }

    @Test(expected = ZeroStringException.class)
    public void testEquator_withComplexEquation_shouldThrowZeroValueException() throws ZeroStringException {
        EquationParser equationParser = new EquationParser("(3+2*(1/(4-4)))");


        System.out.println(equationParser.parse());
    }
}
