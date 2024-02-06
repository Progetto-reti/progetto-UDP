package ciao;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.statistics.Histogram;
import net.jqwik.api.statistics.Statistics;
import net.jqwik.api.statistics.StatisticsReport;
import org.example.CO2Budget;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CO2BudgetTest {
    private final CO2Budget cd = new CO2Budget();
    @Property
    @StatisticsReport(format = Histogram.class)
    void budgetIsZero(@ForAll("invalidBudget") int budget,
                      @ForAll @IntRange(min = 0) int startingAnnual,
                      @ForAll int annualChange) {

        int remainingYears = cd.remainingYears(budget, startingAnnual, annualChange);
        Statistics.collect(remainingYears == 0);
        assertEquals(0, remainingYears);
    }


    @Property
    @StatisticsReport(format = Histogram.class)
    public void optimalSituation(
            @ForAll @IntRange(min = 0) int initialBudget,
            @ForAll @IntRange(min = 0) int startingAnnual,
            @ForAll int annualChange
    ) {
        int remainder = CO2Budget.remainingYears(initialBudget,startingAnnual,annualChange);
        Statistics.collect( remainder > 0); // Raccoglie statistiche sul risultato

    }


        @Property
        @StatisticsReport(format = Histogram.class)
        @Report(Reporting.GENERATED)
        void annualChangeIsZero(
                @ForAll @IntRange(min = 1, max = 10) int remainingYears,
                @ForAll @IntRange(min = 1, max = 100) int startingAnnual

        ) {

            int initialBudget = startingAnnual * remainingYears ;
            Statistics.collect(remainingYears);
            assertEquals(remainingYears, CO2Budget.remainingYears(initialBudget, startingAnnual, 0));
        }

    @Property
    @StatisticsReport(format = Histogram.class)
    @Report(Reporting.GENERATED)
    void withResume(
            @ForAll @IntRange(min = 1, max = 100) int remainingYears,
            @ForAll @IntRange(min = 5, max = Integer.MAX_VALUE / 1000) int startingAnnual,
            @ForAll @IntRange(min = 0, max = 4) int remainder
    ) {

        int initialBudget = startingAnnual * remainingYears - remainder;
        Statistics.collect(remainingYears );
        assertEquals(remainingYears, CO2Budget.remainingYears(initialBudget, startingAnnual, 0));
    }


        @Property(afterFailure = AfterFailureMode.RANDOM_SEED)
        @StatisticsReport(format = Histogram.class)
        @Report(Reporting.GENERATED)
        public void increasingAnnualChangeCanOnlyDecreaseRemainingYears(
                @ForAll @IntRange (min = 1,max = 20) int initialBudget,
                @ForAll @IntRange (min = 1, max = 40) int startingAnnual,
                @ForAll @IntRange (min = 0,max = 40) int annualChange,
                @ForAll @IntRange(min = 1, max = 50) int increase
        ) {


            int remaining = CO2Budget.remainingYears(initialBudget, startingAnnual, annualChange);
            int remainingWithIncreasedAnnualChange = CO2Budget.remainingYears(initialBudget, startingAnnual, annualChange + increase);

            Statistics.collect(remaining > remainingWithIncreasedAnnualChange);
        }

        @Property
        @StatisticsReport(format = Histogram.class)
        public void decreasingAnnualChangeCanOnlyIncreaseRemainingYears(
                @ForAll @IntRange(min = 0, max = 20) int initialBudget,
                @ForAll @IntRange (min = 0, max= 40) int startingAnnual,
                @ForAll @IntRange (min = -40, max = 40) int annualChange,
                @ForAll @IntRange(min = 1, max = 50) int decrease
        ) {


            int remaining = CO2Budget.remainingYears(initialBudget, startingAnnual, annualChange);
            int remainingWithDecreaseAnnualChange = CO2Budget.remainingYears(initialBudget, startingAnnual, annualChange - decrease);
            Statistics.collect(remaining < remainingWithDecreaseAnnualChange);
        }


    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void annualEmissionIsntValid(
            @ForAll @IntRange(min = 1, max = 10000) int initialBudget,
            @ForAll ("invalidAnnualEmission") int startingAnnualEmission,
            @ForAll @IntRange(min = 1, max = 10000) int annualChange

    ) {

        int result = CO2Budget.remainingYears(initialBudget, startingAnnualEmission, annualChange);

        boolean expectedResult = result == Integer.MAX_VALUE;
        Statistics.collect(expectedResult);

        Assertions.assertEquals(Integer.MAX_VALUE, result);
    }



    @Provide
    Arbitrary<Integer> invalidBudget() {
        return Arbitraries.integers().between(Integer.MIN_VALUE, 0); // Genera interi compresi tra il valore minimo e 0
    }

    @Provide
    Arbitrary<Integer> invalidAnnualEmission() {
        return Arbitraries.integers().between(Integer.MIN_VALUE, 0); // Genera interi compresi tra il valore minimo e 0
    }
}
