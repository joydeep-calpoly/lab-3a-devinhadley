package lab_3a;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieTicketPriceCalculatorTest {
    private MovieTicketPriceCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new MovieTicketPriceCalculator(
                LocalTime.of(10, 0),
                LocalTime.of(14, 0),
                12,
                65
        );
    }

    @Test
    public void testConstructorValidTimes() {
        assertDoesNotThrow(() -> new MovieTicketPriceCalculator(LocalTime.of(9, 0), LocalTime.of(12, 0), 12, 65));
    }

    @Test
    public void testConstructorInvalidTimes() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new MovieTicketPriceCalculator(LocalTime.of(15, 0), LocalTime.of(12, 0), 12, 65);
        });
        assertEquals("matinee start time cannot come after end time", exception.getMessage());
    }

    @Test
    public void testConstructorNullStart() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new MovieTicketPriceCalculator(null, LocalTime.of(12, 0), 12, 65);
        });
        assertEquals("matinee start time cannot be null", exception.getMessage());
    }

    @Test
    public void testConstructorNullEnd() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new MovieTicketPriceCalculator(LocalTime.of(15, 0),null, 12, 65);
        });
        assertEquals("matinee end time cannot be null", exception.getMessage());
    }

    @Test
    public void testComputePriceMatineeChild() {
        int price = calculator.computePrice(LocalTime.of(11, 0), 10);
        assertEquals(2400 - 300, price);  // Matinee price - child discount
    }

    @Test
    public void testComputePriceMatineeSenior() {
        int price = calculator.computePrice(LocalTime.of(11, 0), 70);
        assertEquals(2400 - 400, price);
    }

    @Test
    public void testComputePriceMatineeNoDiscount() {
        int price = calculator.computePrice(LocalTime.of(11, 0), 30);
        assertEquals(2400, price);
    }

    @Test
    public void testComputePriceNonMatineeChild() {
        int price = calculator.computePrice(LocalTime.of(15, 0), 10);
        assertEquals(2700 - 300, price);
    }

    @Test
    public void testComputePriceNonMatineeSenior() {
        int price = calculator.computePrice(LocalTime.of(15, 0), 70);
        assertEquals(2700 - 400, price);
    }

    @Test
    public void testComputePriceNonMatineeNoDiscount() {
        int price = calculator.computePrice(LocalTime.of(15, 0), 30);
        assertEquals(2700, price);
    }

    @Test
    public void testComputePriceBoundaryStartMatinee() {
        int price = calculator.computePrice(LocalTime.of(10, 0), 30);
        assertEquals(2400, price);
    }

    @Test
    public void testComputePriceBoundaryEndMatinee() {
        int price = calculator.computePrice(LocalTime.of(14, 0), 30);
        assertEquals(2700, price);
    }

    @Test
    public void testComputeDiscountChild() {
        int discount = calculator.computeDiscount(10);
        assertEquals(300, discount);
    }

    @Test
    public void testComputeDiscountSenior() {
        int discount = calculator.computeDiscount(70);
        assertEquals(400, discount);
    }

    @Test
    public void testComputeDiscountNoDiscount() {
        int discount = calculator.computeDiscount(30);
        assertEquals(0, discount);
    }

    @Test
    public void testComputeDiscountBoundaryMaxChildAge() {
        int discount = calculator.computeDiscount(12);
        assertEquals(300, discount);
    }

    @Test
    public void testComputeDiscountBoundaryMinSeniorAge() {
        int discount = calculator.computeDiscount(65);
        assertEquals(400, discount);
    }

}
