package org.example;

public class CO2Budget {
    public static int remainingYears(int initialBudget, int startingAnnualEmission, int annualChange) {
        int remaining = 0;
        int budget = initialBudget;
        int annualEmission = startingAnnualEmission;

        while (budget > 0) {
            if (annualEmission <= 0) {
                return Integer.MAX_VALUE;
            }

            budget -= annualEmission;
            remaining++;

            if (budget <= 0) {
                // Evita il loop infinito quando il budget diventa negativo
                break;
            }

            annualEmission += annualChange;
        }

        return remaining;
    }

    public static void main(String[] args) {
        // Esempio di utilizzo
        int initialBudget = 9;
        int startingAnnualEmission = 15;
        int annualChange = 0;

        int result = remainingYears(initialBudget, startingAnnualEmission, annualChange);
        System.out.println("Anni rimanenti: " + result);
    }
}
