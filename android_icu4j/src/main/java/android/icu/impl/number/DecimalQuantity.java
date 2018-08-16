/* GENERATED SOURCE. DO NOT MODIFY. */
// © 2017 and later: Unicode, Inc. and others.
// License & terms of use: http://www.unicode.org/copyright.html#License
package android.icu.impl.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.FieldPosition;

import android.icu.impl.StandardPlural;
import android.icu.text.PluralRules;
import android.icu.text.UFieldPosition;

/**
 * An interface representing a number to be processed by the decimal formatting pipeline. Includes
 * methods for rounding, plural rules, and decimal digit extraction.
 *
 * <p>By design, this is NOT IMMUTABLE and NOT THREAD SAFE. It is intended to be an intermediate
 * object holding state during a pass through the decimal formatting pipeline.
 *
 * <p>Implementations of this interface are free to use any internal storage mechanism.
 *
 * <p>TODO: Should I change this to an abstract class so that logic for min/max digits doesn't need
 * to be copied to every implementation?
 * @hide Only a subset of ICU is exposed in Android
 */
public interface DecimalQuantity extends PluralRules.IFixedDecimal {
  /**
   * Sets the minimum and maximum integer digits that this {@link DecimalQuantity} should generate.
   * This method does not perform rounding.
   *
   * @param minInt The minimum number of integer digits.
   * @param maxInt The maximum number of integer digits.
   */
  public void setIntegerLength(int minInt, int maxInt);

  /**
   * Sets the minimum and maximum fraction digits that this {@link DecimalQuantity} should generate.
   * This method does not perform rounding.
   *
   * @param minFrac The minimum number of fraction digits.
   * @param maxFrac The maximum number of fraction digits.
   */
  public void setFractionLength(int minFrac, int maxFrac);

  /**
   * Rounds the number to a specified interval, such as 0.05.
   *
   * <p>If rounding to a power of ten, use the more efficient {@link #roundToMagnitude} instead.
   *
   * @param roundingInterval The increment to which to round.
   * @param mathContext The {@link MathContext} to use if rounding is necessary. Undefined behavior
   *     if null.
   */
  public void roundToIncrement(BigDecimal roundingInterval, MathContext mathContext);

  /**
   * Rounds the number to a specified magnitude (power of ten).
   *
   * @param roundingMagnitude The power of ten to which to round. For example, a value of -2 will
   *     round to 2 decimal places.
   * @param mathContext The {@link MathContext} to use if rounding is necessary. Undefined behavior
   *     if null.
   */
  public void roundToMagnitude(int roundingMagnitude, MathContext mathContext);

  /**
   * Rounds the number to an infinite number of decimal points. This has no effect except for
   * forcing the double in {@link DecimalQuantity_AbstractBCD} to adopt its exact representation.
   */
  public void roundToInfinity();

  /**
   * Multiply the internal value.
   *
   * @param multiplicand The value by which to multiply.
   */
  public void multiplyBy(BigDecimal multiplicand);

  /**
   * Scales the number by a power of ten. For example, if the value is currently "1234.56", calling
   * this method with delta=-3 will change the value to "1.23456".
   *
   * @param delta The number of magnitudes of ten to change by.
   */
  public void adjustMagnitude(int delta);

  /**
   * @return The power of ten corresponding to the most significant nonzero digit.
   * @throws ArithmeticException If the value represented is zero.
   */
  public int getMagnitude() throws ArithmeticException;

  /** @return Whether the value represented by this {@link DecimalQuantity} is zero. */
  public boolean isZero();

  /** @return Whether the value represented by this {@link DecimalQuantity} is less than zero. */
  public boolean isNegative();

  /** @return Whether the value represented by this {@link DecimalQuantity} is infinite. */
  @Override
  public boolean isInfinite();

  /** @return Whether the value represented by this {@link DecimalQuantity} is not a number. */
  @Override
  public boolean isNaN();

  /** @return The value contained in this {@link DecimalQuantity} approximated as a double. */
  public double toDouble();

  public BigDecimal toBigDecimal();

  public void setToBigDecimal(BigDecimal input);

  public int maxRepresentableDigits();

  // TODO: Should this method be removed, since DecimalQuantity implements IFixedDecimal now?
  /**
   * Computes the plural form for this number based on the specified set of rules.
   *
   * @param rules A {@link PluralRules} object representing the set of rules.
   * @return The {@link StandardPlural} according to the PluralRules. If the plural form is not in
   *     the set of standard plurals, {@link StandardPlural#OTHER} is returned instead.
   */
  public StandardPlural getStandardPlural(PluralRules rules);

  /**
   * Gets the digit at the specified magnitude. For example, if the represented number is 12.3,
   * getDigit(-1) returns 3, since 3 is the digit corresponding to 10^-1.
   *
   * @param magnitude The magnitude of the digit.
   * @return The digit at the specified magnitude.
   */
  public byte getDigit(int magnitude);

  /**
   * Gets the largest power of ten that needs to be displayed. The value returned by this function
   * will be bounded between minInt and maxInt.
   *
   * @return The highest-magnitude digit to be displayed.
   */
  public int getUpperDisplayMagnitude();

  /**
   * Gets the smallest power of ten that needs to be displayed. The value returned by this function
   * will be bounded between -minFrac and -maxFrac.
   *
   * @return The lowest-magnitude digit to be displayed.
   */
  public int getLowerDisplayMagnitude();

  /**
   * Returns the string in "plain" format (no exponential notation) using ASCII digits.
   */
  public String toPlainString();

  /**
   * Like clone, but without the restrictions of the Cloneable interface clone.
   *
   * @return A copy of this instance which can be mutated without affecting this instance.
   */
  public DecimalQuantity createCopy();

  /**
   * Sets this instance to be equal to another instance.
   *
   * @param other The instance to copy from.
   */
  public void copyFrom(DecimalQuantity other);

  /** This method is for internal testing only. */
  public long getPositionFingerprint();

  /**
   * If the given {@link FieldPosition} is a {@link UFieldPosition}, populates it with the fraction
   * length and fraction long value. If the argument is not a {@link UFieldPosition}, nothing
   * happens.
   *
   * @param fp The {@link UFieldPosition} to populate.
   */
  public void populateUFieldPosition(FieldPosition fp);
}
