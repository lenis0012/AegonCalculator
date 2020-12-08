package nl.ltenwolde.calculatorapi.model;

import nl.ltenwolde.calculatorapi.Operation;

import java.util.List;

public class CalculationResponse {
    private final List<SimpleEquation> equations;

    public CalculationResponse(List<SimpleEquation> equations) {
        this.equations = equations;
    }

    public List<SimpleEquation> getEquations() {
        return equations;
    }

    public static class SimpleEquation {
        private final int inputOne;
        private final Operation operation;
        private final int inputTwo;
        private final double result;
        private final boolean valid;

        public SimpleEquation(int inputOne, Operation operation, int inputTwo, double result, boolean valid) {
            this.inputOne = inputOne;
            this.operation = operation;
            this.inputTwo = inputTwo;
            this.result = result;
            this.valid = valid;
        }

        public int getInputOne() {
            return inputOne;
        }

        public Operation getOperation() {
            return operation;
        }

        public int getInputTwo() {
            return inputTwo;
        }

        public double getResult() {
            return result;
        }

        public boolean isValid() {
            return valid;
        }
    }
}
