package org.rmnorbert.service.cli;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputScanner {
    public String getUserInput(String message) {
        System.out.println(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public Integer getNumericUserInput(String message) {
        Integer result = null;
        int maxAttempts = 3, attempts = 0;
        while (result == null && attempts < maxAttempts) {
            System.out.println(message);
            Scanner scanner = new Scanner(System.in);
            try {
                result = scanner.nextInt();
                if (result < 0) {
                    result = null;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input!");
                attempts++;
            }
        }
        return result == null ? -1 : result;
    }
}
