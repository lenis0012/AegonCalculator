package nl.ltenwolde.calculatorapi.repository;

import nl.ltenwolde.calculatorapi.model.SimpleEquation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquationRepository extends CrudRepository<SimpleEquation, Integer> {

    List<SimpleEquation> findAllByOrderByIdDesc();
}
