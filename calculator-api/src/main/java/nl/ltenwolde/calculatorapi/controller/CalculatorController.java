package nl.ltenwolde.calculatorapi.controller;

import nl.ltenwolde.calculatorapi.CalculationException;
import nl.ltenwolde.calculatorapi.model.CalculationRequest;
import nl.ltenwolde.calculatorapi.model.CalculationResponse;
import nl.ltenwolde.calculatorapi.model.SimpleEquation;
import nl.ltenwolde.calculatorapi.repository.EquationRepository;
import nl.ltenwolde.calculatorapi.service.SimpleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RequestMapping("/equations")
@RestController
public class CalculatorController {
    private final SimpleCalculator simpleCalculator;
    private final EquationRepository equationRepository;

    @Autowired
    public CalculatorController(SimpleCalculator simpleCalculator, EquationRepository equationRepository) {
        this.simpleCalculator = simpleCalculator;
        this.equationRepository = equationRepository;
    }

    @PostMapping("")
    public CalculationResponse post(@RequestBody CalculationRequest request) {
        var equations = request.getExpressions().stream()
                .map(expression -> {
                    try {
                        double result = simpleCalculator.calculate(expression.getInputOne(), expression.getOperation(), expression.getInputTwo());
                        return new SimpleEquation(
                                // Input expression
                                expression.getInputOne(), expression.getOperation(), expression.getInputTwo(),
                                // Result
                                result, true
                        );
                    } catch (CalculationException e) {
                        return new SimpleEquation(
                                // Input expression
                                expression.getInputOne(), expression.getOperation(), expression.getInputTwo(),
                                // Result
                                0.0, false
                        );
                    }
                }).collect(Collectors.toList());

        equationRepository.saveAll(equations);

        if(equations.stream().noneMatch(SimpleEquation::isValid) &&
                request.getExpressions().size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No calculations were successful");
        }

        // Return the results of the expressions, ordered
        return new CalculationResponse(equationRepository.findAllByOrderByIdDesc());
    }

    @GetMapping("")
    public CalculationResponse getAll() {
        return new CalculationResponse(equationRepository.findAllByOrderByIdDesc());
    }
}
