package nl.ltenwolde.calculatorapi.model;

import nl.ltenwolde.calculatorapi.Operation;

import java.util.List;

public class CalculationResponse {
    private List<SimpleEquation> equations;

    private CalculationResponse() {}

    public CalculationResponse(List<SimpleEquation> equations) {
        this.equations = equations;
    }

    public List<SimpleEquation> getEquations() {
        return equations;
    }

    public static class SimpleEquation {
        private int inputOne;
        private Operation operation;
        private int inputTwo;
        private double result;
        private boolean valid;

        private SimpleEquation() {}

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
