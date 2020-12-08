package nl.ltenwolde.calculatorapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.ltenwolde.calculatorapi.Operation;
import nl.ltenwolde.calculatorapi.model.CalculationRequest;
import nl.ltenwolde.calculatorapi.model.CalculationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SimpleCalculatorTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void subtract() throws Exception {
        CalculationRequest request = new CalculationRequest();
        request.setExpressions(List.of(
                expr(1, 3, Operation.ADD),
                expr(3, 1, Operation.SUBTRACT),
                expr(3, 3, Operation.MULTIPLY),
                expr(3, 2, Operation.DIVIDE),
                expr(5, 0, Operation.DIVIDE)
        ));

        MvcResult mvcResult = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        CalculationResponse res = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CalculationResponse.class);
        assertEquals(res.getEquations().get(0).getResult(), 4, "1 + 3 = 4");
        assertEquals(res.getEquations().get(1).getResult(), 2, "3 - 1 = 2");
        assertEquals(res.getEquations().get(2).getResult(), 9, "3 * 3  = 9");
        assertEquals(res.getEquations().get(3).getResult(), 1.5, "3 / 2 = 1.5");
        assertFalse(res.getEquations().get(4).isValid(), "divide by zero should be invalid");
    }

    private CalculationRequest.SimpleExpression expr(int a, int b, Operation operation) {
        CalculationRequest.SimpleExpression expression = new CalculationRequest.SimpleExpression();
        expression.setInputOne(a);
        expression.setInputTwo(b);
        expression.setOperation(operation);
        return expression;
    }
}