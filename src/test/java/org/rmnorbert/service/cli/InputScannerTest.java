package org.rmnorbert.service.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputScannerTest {

    private InputScanner inputScanner;

    @BeforeEach
    public void setUp() {
        inputScanner = new InputScanner();
    }

    @Test
    void getUserInput_ReturnsUserInput_WhenMessageProvided() {
        String message = "Enter a test message:";
        String simulatedInput = "Test input";

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        String userInput = inputScanner.getUserInput(message);

        assertEquals(simulatedInput, userInput);
    }

    @Test
    void getNumericUserInput_WithValidInputShouldReturnInput() {
        String message = "Enter a valid number:";
        String simulatedInput = "42";

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Integer userInput = inputScanner.getNumericUserInput(message);
        assertEquals(42, userInput);
    }

    @Test
    void getNumericUserInput_MaxAttemptsReachedWithInvalidInputShouldReturnMinusOne() {
        String message = "Enter a valid number:";
        String simulatedInput = "-1";

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        Integer userInput = inputScanner.getNumericUserInput(message);
        assertEquals(-1, userInput);
    }

    @AfterEach
    public void cleanUpEach() {
        System.setIn(System.in);
    }
}