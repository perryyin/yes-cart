package org.yes.cart.util;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.*;

/**
 * MoneyUtils test.
 * <p/>
 * User: dogma
 * Date: Jan 15, 2011
 * Time: 11:47:04 PM
 */
public class MoneyUtilsTest {

    /* -- IsFirstBiggerThanSecond ----------------------------------- */
    @Test
    public void testNotNullIsNullSafe() {
        assertEquals("Must return zero if bot arguments are null", BigDecimal.ZERO, MoneyUtils.notNull(null, null));
    }

    @Test
    public void testNotNullWithNull() {
        assertEquals("Must return ifNull (10) for null value", BigDecimal.TEN, MoneyUtils.notNull(null, BigDecimal.TEN));
    }

    @Test
    public void testNotNullWithThree() {
        assertEquals("Must return value (3) for non-null value", new BigDecimal(3), MoneyUtils.notNull(new BigDecimal(3), BigDecimal.TEN));
    }


    /* -- IsFirstBiggerThanSecond ----------------------------------- */

    @Test
    public void testIsFirstNullBiggerThanSecondNulls() {
        assertFalse("Two nulls must give a false", MoneyUtils.isFirstBiggerThanSecond(null, null));
    }

    @Test
    public void testIsFirstNullBiggerThanSecondZero() {
        assertFalse("Null is not bigger than zero", MoneyUtils.isFirstBiggerThanSecond(null, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstZeroBiggerThanSecondNull() {
        assertTrue("Zero is bigger than null", MoneyUtils.isFirstBiggerThanSecond(BigDecimal.ZERO, null));
    }

    @Test
    public void testIsFirstZeroBiggerThanSecondTen() {
        assertFalse("zero is not bigger than ten", MoneyUtils.isFirstBiggerThanSecond(BigDecimal.ZERO, BigDecimal.TEN));
    }

    @Test
    public void testIsFirstTenBiggerThanSecondZero() {
        assertTrue("ten is bigger than zero", MoneyUtils.isFirstBiggerThanSecond(BigDecimal.TEN, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstTenBiggerThanSecondTen() {
        assertFalse("ten is not bigger than ten", MoneyUtils.isFirstBiggerThanSecond(BigDecimal.TEN, new BigDecimal(10.00)));
    }

    /* -- IsFirstBiggerThanOrEqualToSecond ----------------------------------- */

    @Test
    public void testIsFirstNullBiggerThanOrEqualToSecondNulls() {
        assertFalse("Two nulls must give a false", MoneyUtils.isFirstBiggerThanOrEqualToSecond(null, null));
    }

    @Test
    public void testIsFirstNullBiggerThanOrEqualToSecondZero() {
        assertFalse("Null is not bigger than or equal to zero", MoneyUtils.isFirstBiggerThanOrEqualToSecond(null, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstZeroBiggerThanOrEqualToSecondNull() {
        assertTrue("Zero is bigger than or equal to null", MoneyUtils.isFirstBiggerThanOrEqualToSecond(BigDecimal.ZERO, null));
    }

    @Test
    public void testIsFirstZeroBiggerThanOrEqualToSecondTen() {
        assertFalse("zero is not bigger than or equal to ten", MoneyUtils.isFirstBiggerThanOrEqualToSecond(BigDecimal.ZERO, BigDecimal.TEN));
    }

    @Test
    public void testIsFirstTenBiggerThanOrEqualToSecondZero() {
        assertTrue("ten is bigger than or equal to zero", MoneyUtils.isFirstBiggerThanOrEqualToSecond(BigDecimal.TEN, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstTenBiggerThanOrEqualToSecondTen() {
        assertTrue("ten is bigger than or equal to ten", MoneyUtils.isFirstBiggerThanOrEqualToSecond(BigDecimal.TEN, BigDecimal.ZERO));
    }

    /* -- isFirstEqualToSecond ----------------------------------- */
    @Test
    public void testIsFirstNullEqualToSecondNulls() {
        assertFalse("Two nulls must give a false", MoneyUtils.isFirstEqualToSecond(null, null));
    }

    @Test
    public void testIsFirstNullEqualToSecondZero() {
        assertFalse("Null is not equal to zero", MoneyUtils.isFirstEqualToSecond(null, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstZeroEqualToSecondNull() {
        assertFalse("Zero is not equal to null", MoneyUtils.isFirstEqualToSecond(BigDecimal.ZERO, null));
    }

    @Test
    public void testIsFirstZeroEqualToSecondTen() {
        assertFalse("zero is not equal to ten", MoneyUtils.isFirstEqualToSecond(BigDecimal.ZERO, BigDecimal.TEN));
    }

    @Test
    public void testIsFirstTenEqualToSecondZero() {
        assertFalse("ten is not equal to zero", MoneyUtils.isFirstEqualToSecond(BigDecimal.TEN, BigDecimal.ZERO));
    }

    @Test
    public void testIsFirstTenEqualToSecondTen() {
        assertTrue("ten is equal to ten", MoneyUtils.isFirstEqualToSecond(BigDecimal.TEN, new BigDecimal("10.0000")));
    }


}