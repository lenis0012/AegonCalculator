package nl.ltenwolde.calculatorapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class SimpleCalculator {

    @GetMapping("add")
    public double add(int a, int b) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("subtract")
    public double subtract(int a, int b) {
        throw new UnsupportedOperationException();

    }

    @GetMapping("multiply")
    public double multiply(int a, int b) {
        throw new UnsupportedOperationException();

    }

    @GetMapping("divide")
    public double divide(int a, int b) {
        throw new UnsupportedOperationException();
    }
}
