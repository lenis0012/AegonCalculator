package nl.ltenwolde.calculatorapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.ltenwolde.calculatorapi.Operation;
import nl.ltenwolde.calculatorapi.model.CalculationRequest;
import nl.ltenwolde.calculatorapi.model.CalculationResponse;
import nl.ltenwolde.calculatorapi.model.SimpleEquation;
import nl.ltenwolde.calculatorapi.repository.EquationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unit")
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EquationRepository equationRepository;
    @Captor
    private ArgumentCaptor<Iterable<SimpleEquation>> equationsCaptor;

    @BeforeEach
    public void prepare() {
        when(equationRepository.saveAll(equationsCaptor.capture())).thenAnswer(invocationOnMock -> {
            Iterable<SimpleEquation> iterable = invocationOnMock.getArgument(0, Iterable.class);
            int nextId = 1;
            for(SimpleEquation equation : iterable) {
                equation.setId(nextId++);
            }
            return iterable;
        });
    }

    @Test
    void testCalculations() throws Exception {
        when(equationRepository.findAllByOrderByIdDesc()).thenAnswer(iom -> equationsCaptor.getAllValues().get(0));

        CalculationRequest request = new CalculationRequest();
        request.setExpressions(List.of(
                expr(1, 3, Operation.ADD),
                expr(3, 1, Operation.SUBTRACT),
                expr(3, 3, Operation.MULTIPLY),
                expr(3, 2, Operation.DIVIDE),
                expr(5, 0, Operation.DIVIDE)
        ));

        MvcResult mvcResult = mockMvc.perform(post("/equations")
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

        verify(equationRepository, times(1)).saveAll(any());
        verify(equationRepository, times(1)).findAllByOrderByIdDesc();
    }

    @Test
    void testGetAllFromRepository() throws Exception {
        SimpleEquation equation = new SimpleEquation(1, Operation.ADD, 1, 2, true);
        when(equationRepository.findAllByOrderByIdDesc()).thenReturn(List.of(equation));

        mockMvc.perform(get("/equations"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(new CalculationResponse(List.of(equation)))));

        verify(equationRepository, atLeastOnce()).findAllByOrderByIdDesc();
    }

    private CalculationRequest.SimpleExpression expr(int a, int b, Operation operation) {
        CalculationRequest.SimpleExpression expression = new CalculationRequest.SimpleExpression();
        expression.setInputOne(a);
        expression.setInputTwo(b);
        expression.setOperation(operation);
        return expression;
    }
}