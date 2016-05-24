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
        EquationParser equationParser = new EquationParser("((3+5)*4)-3");
        String parsedString = equationParser.parse();

        assertEquals(parsedString, "29");
    }

    @Test
    public void testEquator_withMoreComplexEquation() throws ZeroStringException {
        EquationParser equationParser = new EquationParser("(3*(3+4)+3)*5");
        String parsedString = equationParser.parse();

        assertEquals(parsedString, "120");
    }

    @Test
    public void testEquator_withAnotherComplexEquation() throws ZeroStringException {
        String equation = "4-1+(8*1/(4-2))";
        EquationParser equationParser = new EquationParser(equation);

        String parse = equationParser.parse();
        assertEquals(parse, "7");
    }

    @Test(expected = ZeroStringException.class)
    public void testEquator_withComplexEquation_shouldThrowZeroValueException() throws ZeroStringException {
        EquationParser equationParser = new EquationParser("(3+2*(1/(4-4)))");
        equationParser.parse();
    }
}
