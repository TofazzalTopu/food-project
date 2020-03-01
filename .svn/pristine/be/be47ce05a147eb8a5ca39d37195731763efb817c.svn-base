package com.docu.commons

import java.text.DecimalFormat

/**
 * Created by IntelliJ IDEA.
 * User: Zia
 * Date: 2/2/11
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class AmountToWords {
  private static final String[] tensNames = ["", " ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"]
  private static final String[] numNames = ["", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"]
  private static final String TRILLION = " trillion";
  private static final String BILLION = " billion";
  private static final String MILLION = " million";
  private static final String THOUSANDS = " thousand";
  private static final String HUNDREDS = " hundred";
  private static final String ZERO = " zero";
  private static final String ONE = " one";
  private static final String TWO = " two";
  private static final String THREE = " three";
  private static final String FOUR = " four";
  private static final String FIVE = " five";
  private static final String SIX = " six";
  private static final String SEVEN = " seven";
  private static final String EIGHT = " eight";
  private static final String NINE = " nine";
  private static final String ONLY = " only";
  private static final String AND = " and ";
  private static final String AMOUNT_MASK = "000000000000000.0000";
  private static final String MASK_BEFORE_POINTS = "000000000000000"
  private static final String ERROR_MESSAGE = "Error to convert amount in words";
  private static final String ENDS_WITH_ZERO = "0"
  private static final String BLANK_SPACE = ""
  private static final String SPLIT_CAR = "\\."

  private StringBuffer stringAmounts = null

  /**
   * Getting amounts in word
   * @param amount double amount
   * @param note String note name
   * @param coin String coin name
   * @return String amount in word
   */
  private String getAmountInWord(double amount, String note, String coin) {
    if (!note || note == BLANK_SPACE || !coin || coin == BLANK_SPACE) {return null}
    try {
      DecimalFormat df = new DecimalFormat(AMOUNT_MASK)
      String tempValue = df.format(amount)
      StringBuffer result = new StringBuffer()
      String[] stringAmount = new String[2]
      stringAmount = tempValue.split(SPLIT_CAR)
      if (stringAmount.length > 1 && Integer.parseInt(stringAmount[1]) > 0) {
        result.append(note)
        result.append(convertNoteAmountInWord(Long.parseLong(stringAmount[0])))
        result.append(AND)
        result.append(coin)
        result.append(convertDecimalPlacesInWord(stringAmount[1]))
        result.append(ONLY)
      }
      else {
        result.append(note)
        result.append(convertNoteAmountInWord(Long.parseLong(stringAmount[0])))
        result.append(ONLY)
      }
      return result.toString();
    } catch (Exception ex) {
      return ERROR_MESSAGE;
    }
  }
  /**
   * Getting amounts in word
   * @param amount String amount
   * @param note String note name
   * @param coin String coin name
   * @return String amount in word
   */
  private String getAmountInWord(String amount, String note, String coin) {
    try {
      String tempValue = amount;
      StringBuffer result = new StringBuffer();
      String[] stringAmount = new String[2];
      stringAmount = tempValue.split(SPLIT_CAR);
      if (stringAmount.length > 1 && Integer.parseInt(stringAmount[1]) > 0) {
        result.append(note)
        result.append(convertNoteAmountInWord(Long.parseLong(stringAmount[0])))
        result.append(AND)
        result.append(coin)
        result.append(convertDecimalPlacesInWord(stringAmount[1]))
        result.append(ONLY)
      }
      else {
        result.append(note)
        result.append(convertNoteAmountInWord(Long.parseLong(stringAmount[0])))
        result.append(ONLY)
      }
      return result.toString()
    } catch (Exception ex) {
      return ERROR_MESSAGE;
    }
  }
  /**
   * Convert note amount in words
   * @param number long value
   * @return String number in word
   */
  public String convertNoteAmountInWord(long number) {
    // 0 to 999 999 999 999 999
    StringBuffer result = new StringBuffer()
    if (number == 0) { return ZERO }
    String stringNumber = Long.toString(number)
    DecimalFormat df = new DecimalFormat(MASK_BEFORE_POINTS)
    stringNumber = df.format(number)
    int trillions = Integer.parseInt(stringNumber.substring(0, 3))
    int billions = Integer.parseInt(stringNumber.substring(3, 6))
    int millions = Integer.parseInt(stringNumber.substring(6, 9))
    int hundredThousands = Integer.parseInt(stringNumber.substring(9, 12))
    int thousands = Integer.parseInt(stringNumber.substring(12, 15))
    result.append(convertTrillionInWord(trillions))
    result.append(convertBillionInWord(billions))
    result.append(convertMillionInWord(millions))
    result.append(convertHundredThousandInWord(hundredThousands))
    result.append(convertLessThanOneThousand(thousands))
    return result.toString()
  }
  /**
   * convertNoteAmountInWord integer amount into words whose length is less than 1000
   * @param number int amount
   * @return String in words
   */
  private String convertLessThanOneThousand(int number) {
    String numberInWords = null
    if (number % 100 < 20) {
      numberInWords = numNames[number % 100]
      number /= 100
    } else {
      numberInWords = numNames[number % 10]
      number /= 10
      numberInWords = tensNames[number % 10] + numberInWords
      number /= 10
    }
    if (number == 0) return numberInWords
    return numNames[number] + HUNDREDS + numberInWords
  }
  /**
   * Convert trillion amount in words
   * @param trillions int amount
   * @return String in words
   */
  private String convertTrillionInWord(int trillions) {
    stringAmounts = new StringBuffer()
    switch (trillions) {
      case 0:
        stringAmounts.append(BLANK_SPACE)
        break
      case 1:
        stringAmounts.append(convertLessThanOneThousand(trillions))
        stringAmounts.append(TRILLION)
        break
      default:
        stringAmounts.append(convertLessThanOneThousand(trillions))
        stringAmounts.append(TRILLION)
    }
    return stringAmounts.toString()
  }
  /**
   * Convert billion amount in words
   * @param billions int amount
   * @return String in words
   */
  private String convertBillionInWord(int billions) {
    stringAmounts = new StringBuffer()
    switch (billions) {
      case 0:
        stringAmounts.append(BLANK_SPACE)
        break;
      case 1:
        stringAmounts.append(convertLessThanOneThousand(billions))
        stringAmounts.append(BILLION)
        break;
      default:
        stringAmounts.append(convertLessThanOneThousand(billions))
        stringAmounts.append(BILLION)
    }
    return stringAmounts.toString()
  }
  /**
   * Convert million amount in words
   * @param millions int amount
   * @return String in words
   */
  private String convertMillionInWord(int millions) {
    stringAmounts = new StringBuffer()
    switch (millions) {
      case 0:
        stringAmounts.append(BLANK_SPACE)
        break;
      case 1:
        stringAmounts.append(convertLessThanOneThousand(millions))
        stringAmounts.append(MILLION)
        break;
      default:
        stringAmounts.append(convertLessThanOneThousand(millions))
        stringAmounts.append(MILLION)
    }
    return stringAmounts.toString()
  }

  /**
   * Convert hundredThousands amount in words
   * @param hundredThousands int amount
   * @return String in words
   */
  private String convertHundredThousandInWord(int hundredThousands) {
    stringAmounts = new StringBuffer()
    switch (hundredThousands) {
      case 0:
        stringAmounts.append(BLANK_SPACE)
        break;
      case 1:
        stringAmounts.append(ONE)
        stringAmounts.append(THOUSANDS)
        break;
      default:
        stringAmounts.append(convertLessThanOneThousand(hundredThousands))
        stringAmounts.append(THOUSANDS)
    }
    return stringAmounts.toString();
  }

  /**
   * Convert amounts in words after points
   * @param decimalValue
   * @return String in words
   */
  private String convertDecimalPlacesInWord(String decimalValue) {
    for (int i = 0; i < 4; i++) {
      if (decimalValue.endsWith(ENDS_WITH_ZERO)) {
        decimalValue = decimalValue.substring(0, decimalValue.lastIndexOf(ENDS_WITH_ZERO))
      }
    }
    char[] digit = decimalValue.toCharArray()
    stringAmounts = new StringBuffer()
    for (int i = 0; i < digit.length; i++) {
      switch (digit[i]) {
        case '0':
          stringAmounts.append(ZERO);
          break;
        case '1':
          stringAmounts.append(ONE);
          break;
        case '2':
          stringAmounts.append(TWO);
          break;
        case '3':
          stringAmounts.append(THREE);
          break;
        case '4':
          stringAmounts.append(FOUR);
          break;
        case '5':
          stringAmounts.append(FIVE);
          break;
        case '6':
          stringAmounts.append(SIX);
          break;
        case '7':
          stringAmounts.append(SEVEN);
          break;
        case '8':
          stringAmounts.append(EIGHT);
          break;
        case '9':
          stringAmounts.append(NINE);
          break;
        default:
          break;
      }
    }
    return stringAmounts.toString();
  }
}
