import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)  // create an instance of MathUtilsTest for every method'
                                                    // this is the default. it make no sense to write it!
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)     // this create only an instance of mathUtilsTest!
@DisplayName("When running MathUtils")              // in this case beforeAll and afterAll don't need to be statics!
class MathUtilsTest {

    TestInfo testInfo;
    TestReporter testReporter;
    MathUtils mathUtils;

    //@BeforeAll      // static: no need to have an instance to run the method,
                    // before all means before creating an instance of the class!! 
    //static void beforeAll() {
    //    System.out.println("It must be the beginning");
    //}

    @BeforeEach
    void Init (TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter  = testReporter;
        mathUtils = new MathUtils();
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " + testInfo.getTags());
        //System.out.println("init a new instance of MathUtils");
    }

    //@AfterEach
    //void cleanAfterEach(){
    //    System.out.println("Clean after each!");
    //}
    //@AfterAll
    //static void afterAll () {
    //    System.out.println("Last step: Clean up!");
    //}



    @DisplayName("ADD method")
    @Nested
    @Tag("Math")
    class AddTest {

        @Test
        void addPositive() {
            int expected = 2;
            int actual = mathUtils.add(1, 1);
            assertEquals(expected, actual, "the add method should add two numbers");
        }

        @Test
        void addNegative() {
            int expected = -2;
            int actual = mathUtils.add(-1, -1);
            assertEquals(expected, actual, "the add method should add two numbers");
        }

    }


    //@Test
    @Tag("Math")
    @RepeatedTest(3) // instead of @Test
    //You CAN pass a parameter to get some mehods
    void calculateCircleAreaTest(RepetitionInfo repetitionInfo) {

        int repNr = repetitionInfo.getCurrentRepetition();
        System.out.println("Repetion number: " + repNr);
        if (repNr == 2)
            System.out.println("Nice, that's the second one!!");

        int expected = 113;
        int actual = mathUtils.calculateCircleArea(6);
        assertEquals(expected,actual, "the  method should calculate the area of a circle");

        // if you want less code just:
        //assertEquals(113,mathUtils.calculateCircleArea(6),
        //        "the  method should calculate the area of a circle");

    }

    @Tag("Math")
    @Test               // instead of @Nested
    @DisplayName("Multiply Metod")
    void multiplyTest() {
        assertAll(
            () -> assertEquals(4, mathUtils.multiply(2,2)),
            () -> assertEquals(0, mathUtils.multiply(0,8)),
            () -> assertEquals(-2, mathUtils.multiply(2,-1))
        );
    }

    @Tag("Math")
    @Test
    void divTest() {
        assertThrows(ArithmeticException.class, () -> mathUtils.div(1, 0),
                () -> "That should throw an AritmeticException!");
        //we used the lambda to avoid a the computing of the expression
        //that makes sense if the expression is heavy
        //the lambda delegates the work to a method. It's cheaper.

    }

    @Nested
    @DisplayName("Test that can get disabled")
    class TestThatCanBeDisabled {

        @Test
        @DisplayName("Show Disable function")
        @Disabled
        void disableTest() {
            fail("This test should be disabled");
        }

        @Test
        @DisplayName("Show enable on OS")
        @EnabledOnOs(OS.LINUX)
            // @EnabledOnJre() works in the same way
        void enableOn() {
            System.out.println("This test should be disabled if you don't have linux");
        }

        @Test
        @Disabled
            // should that be unnecessary?
        void showassumetrue() {
            boolean value_to_be_true = false;
            assumeTrue(value_to_be_true);
            System.out.println("This test should be disabled if the argument in assume true is false");
        }
    }


}