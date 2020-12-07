package nl.ltenwolde.calculatorapi.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SimpleCalculatorTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({ // Test cases, format: input_1,input_2,output
            "1,1,2",
            "1,0,1",
            "0,0,0",
            "1,-1,0",
            "-3,-3,-6"
    })
    void add(int input1, int input2, String expectedOutput) throws Exception {
        mockMvc.perform(get("/add?a={a}&b={b}", input1, input2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedOutput));
    }

    @ParameterizedTest
    @CsvSource({ // Test cases, format: input_1,input_2,output
            "1,1,0",
            "1,0,1",
            "0,0,0",
            "1,-1,2",
            "-3,-3,6",
    })
    void subtract(int input1, int input2, String expectedOutput) throws Exception {
        mockMvc.perform(get("/subtract?a={a}&b={b}", input1, input2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedOutput));
    }

    @ParameterizedTest
    @CsvSource({ // Test cases, format: input_1,input_2,output
            "1,1,1",
            "1,0,0",
            "0,0,0",
            "1,-1,-1",
            "-3,-3,9",
            "2,-4,-8"
    })
    void multiply(int input1, int input2, String expectedOutput) throws Exception {
        mockMvc.perform(get("/multiply?a={a}&b={b}", input1, input2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedOutput));
    }

    @ParameterizedTest
    @CsvSource({ // Test cases, format: input_1,input_2,output
            "1,1,1",
            "1,-1,-1",
            "-3,-2,1.5",
    })
    void divide(int input1, int input2, String expectedOutput) throws Exception {
        mockMvc.perform(get("/divide?a={a}&b={b}", input1, input2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedOutput));
    }

    /*
     * Unhappy paths
     */

    @Test
    void divideByZeroShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/divide?a={a}&b={b}", 5, 0))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(Matchers.containsString("Can't divide by zero")));
    }
}