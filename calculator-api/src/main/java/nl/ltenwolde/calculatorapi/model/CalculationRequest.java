package nl.ltenwolde.calculatorapi.model;

import nl.ltenwolde.calculatorapi.Operation;

import java.util.List;

public class CalculationRequest {
    private List<SimpleExpression> expressions;

    public List<SimpleExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<SimpleExpression> expressions) {
        this.expressions = expressions;
    }

    public static final class SimpleExpression {
        private int inputOne;
        private Operation operation;
        private int inputTwo;

        public int getInputOne() {
            return inputOne;
        }

        public void setInputOne(int inputOne) {
            this.inputOne = inputOne;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public int getInputTwo() {
            return inputTwo;
        }

        public void setInputTwo(int inputTwo) {
            this.inputTwo = inputTwo;
        }
    }
}
