package nl.ltenwolde.calculatorapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/")
@RestController
public class SimpleCalculator {

    @GetMapping("add")
    public double add(int a, int b) {
        return a + b;
    }

    @GetMapping("subtract")
    public double subtract(int a, int b) {
        return a - b;
    }

    @GetMapping("multiply")
    public double multiply(int a, int b) {
        return a * b;
    }

    @GetMapping("divide")
    public double divide(int a, int b) {
        if(b == 0) {
            // Dividing by zero is not possible, handle gracefully by returning a 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't divide by zero");
        }

        return a / (double) b;
    }
}
