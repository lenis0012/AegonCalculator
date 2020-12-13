package nl.ltenwolde.calculatorapi.model;

import nl.ltenwolde.calculatorapi.Operation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equations")
public class SimpleEquation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int inputOne;
    @Enumerated(EnumType.STRING)
    private Operation operation;
    private int inputTwo;
    private double result;
    private boolean valid;
    private LocalDateTime createdAt = LocalDateTime.now();

    public SimpleEquation() {
    }

    public SimpleEquation(int inputOne, Operation operation, int inputTwo, double result, boolean valid) {
        this.inputOne = inputOne;
        this.operation = operation;
        this.inputTwo = inputTwo;
        this.result = result;
        this.valid = valid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
