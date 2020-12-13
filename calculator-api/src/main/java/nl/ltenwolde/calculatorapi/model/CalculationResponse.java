package nl.ltenwolde.calculatorapi.model;

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

}
