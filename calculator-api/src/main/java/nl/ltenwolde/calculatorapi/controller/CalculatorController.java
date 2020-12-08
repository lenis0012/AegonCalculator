package nl.ltenwolde.calculatorapi.controller;

import nl.ltenwolde.calculatorapi.CalculationException;
import nl.ltenwolde.calculatorapi.model.CalculationRequest;
import nl.ltenwolde.calculatorapi.model.CalculationResponse;
import nl.ltenwolde.calculatorapi.service.SimpleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RequestMapping("/")
@RestController
public class CalculatorController {
    private final SimpleCalculator simpleCalculator;

    @Autowired
    public CalculatorController(SimpleCalculator simpleCalculator) {
        this.simpleCalculator = simpleCalculator;
    }

    @PostMapping("calculate")
    public CalculationResponse calculate(@RequestBody CalculationRequest request) {
        var equations = request.getExpressions().stream()
                .map(expression -> {
                    try {
                        double result = simpleCalculator.calculate(expression.getInputOne(), expression.getOperation(), expression.getInputTwo());
                        return new CalculationResponse.SimpleEquation(
                                // Input expression
                                expression.getInputOne(), expression.getOperation(), expression.getInputTwo(),
                                // Result
                                result, true
                        );
                    } catch (CalculationException e) {
                        return new CalculationResponse.SimpleEquation(
                                // Input expression
                                expression.getInputOne(), expression.getOperation(), expression.getInputTwo(),
                                // Result
                                0.0, false
                        );
                    }
                }).collect(Collectors.toList());

        if(equations.stream().noneMatch(CalculationResponse.SimpleEquation::isValid) &&
                request.getExpressions().size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No calculations were successful");
        }

        return new CalculationResponse(equations);
    }
}
