package tr.edu.fatih.math.equator;

import tr.edu.fatih.math.equator.exception.ZeroStringException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationParser {

    private static final Pattern UNNECESSARY_PARENTHESES = Pattern.compile("\\(([\\d]+)\\)");
    private static final Pattern PARENTHESES_REGEX = Pattern.compile("\\(([\\d]+[\\+\\-\\*/]?)*\\)");
    private static final Pattern FIRST_LEVEL_MATH_OPERATION_REGEX = Pattern.compile("([\\d]+)([\\*\\/])([\\d]+)");
    private static final Pattern SECOND_LEVEL_MATH_OPERATION_REGEX = Pattern.compile("([\\d]+)([\\+\\-])([\\d]+)");

    private static final String ADDITION_SIGN = "+";
    private static final String SUBTRACT_SIGN = "-";
    private static final String MULTIPLY_SIGN = "*";
    private static final String DIVIDE_SIGN = "/";

    private static final String ZERO = "0";

    private String equation;

    public EquationParser(String equation) {
        this.equation = equation;
    }

    public String parse() throws ZeroStringException {
        do {
            removeUnnecessaryParentheses();
            parseParentheses();
        } while (getParentheses() != null);

        return equation;
    }

    private String parseParentheses() throws ZeroStringException {
        String parentheses = getParentheses();

        if (parentheses != null) {
            String calculatedValue = calculate(parentheses);
            equation.replace(parentheses, calculatedValue);
        }

        removeUnnecessaryParentheses();
        return calculate(equation);
    }

    private String getParentheses() {
        Matcher matcher = PARENTHESES_REGEX.matcher(equation);

        if (matcher.find()) {
            if (matcher.start() < matcher.end()) {
                return equation.substring(matcher.start() + 1, matcher.end() - 1);
            }
        }

        return null;
    }

    private void removeUnnecessaryParentheses() {
        equation = removeUnnecessaryParentheses(equation);
    }

    private String removeUnnecessaryParentheses(String equation) {
        Matcher matcher = UNNECESSARY_PARENTHESES.matcher(equation);

        if (matcher.find()) {
            equation = equation.replace(matcher.group(), matcher.group(1));
        }

        return equation;
    }

    private String calculate(String equation) throws ZeroStringException {
        String calculatedValue = "";
        equation = removeUnnecessaryParentheses(equation);
        Matcher matcher = FIRST_LEVEL_MATH_OPERATION_REGEX.matcher(equation);

        if (matcher.find()) {
            String matched = matcher.group();
            if (matcher.group(2).equals(MULTIPLY_SIGN)) {
                calculatedValue = String.valueOf(Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(3)));
                this.equation = this.equation.replace(matched, calculatedValue);
            }

            if (matcher.group(2).equals(DIVIDE_SIGN)) {
                calculatedValue = checkSecondValueIsZeroElseDivide(matcher.group(1), matcher.group(3));
                this.equation = this.equation.replace(matched, calculatedValue);
            }

            removeUnnecessaryParentheses();
            return calculate(equation.replace(matched, calculatedValue));
        }

        matcher = SECOND_LEVEL_MATH_OPERATION_REGEX.matcher(equation);
        if (matcher.find()) {
            String matched = matcher.group();
            if (matcher.group(2).equals(ADDITION_SIGN)) {
                calculatedValue = String.valueOf(Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(3)));
                this.equation = this.equation.replace(matched, calculatedValue);
            }

            if (matcher.group(2).equals(SUBTRACT_SIGN)) {
                calculatedValue = String.valueOf(Integer.parseInt(matcher.group(1)) - Integer.parseInt(matcher.group(3)));
                this.equation = this.equation.replace(matched, calculatedValue);
            }

            removeUnnecessaryParentheses();
            return calculate(equation.replace(matched, calculatedValue));
        }

        return equation;
    }

    private String checkSecondValueIsZeroElseDivide(String firstValue, String secondValue) throws ZeroStringException {
        if (secondValue.equals(ZERO)) {
            throw new ZeroStringException();
        }

        return String.valueOf(Integer.parseInt(firstValue) / Integer.parseInt(secondValue));
    }
}