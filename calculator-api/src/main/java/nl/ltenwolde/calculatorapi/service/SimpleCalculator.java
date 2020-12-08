package nl.ltenwolde.calculatorapi.service;

import nl.ltenwolde.calculatorapi.CalculationException;
import nl.ltenwolde.calculatorapi.Operation;
import org.springframework.stereotype.Service;

@Service
public class SimpleCalculator {

    public double calculate(int a, Operation operation, int b) {
        switch (operation) {
            case ADD:
                return add(a, b);
            case SUBTRACT:
                return subtract(a, b);
            case MULTIPLY:
                return multiply(a, b);
            case DIVIDE:
                return divide(a, b);
        }

        throw new CalculationException("Unsupported mathematical operation " + operation);
    }

    public double add(int a, int b) {
        return a + b;
    }

    public double subtract(int a, int b) {
        return a - b;
    }

    public double multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if(b == 0) {
            // Dividing by zero is not possible, handle gracefully by returning a 400
            throw new CalculationException("Can't divide by zero");
        }

        return a / (double) b;
    }
}
