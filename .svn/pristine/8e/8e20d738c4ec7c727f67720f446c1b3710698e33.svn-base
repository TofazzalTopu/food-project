package com.docu.commons

import java.text.NumberFormat
import java.text.DecimalFormat
import org.springframework.stereotype.Component
import java.text.DecimalFormatSymbols;

/**
 * Created by IntelliJ IDEA.
 * User: Shompa
 * Date: 5/31/11
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("commonUtil")
class CommonUtil {
    public static String formatNumber(double number) {
        if (number == 0.0) {
            return "0.00"
        }
        NumberFormat formatter = new DecimalFormat("#.00");
        return formatter.format(number)
    }

    public static String formatCurrency(double number, String decimalSeparator = '.', String thousandSeparator = ',') {
        if (number == 0.0) {
            return "0.00"
        }
        NumberFormat formatter = new DecimalFormat("###" + thousandSeparator + "##0" + decimalSeparator + "00");
        return formatter.format(number)
    }

    public static String formatCurrencyFriendly(double number, String decimalSeparator = '.', String thousandSeparator = ',') {
        String formattedCurrency = ""
        if (number > -1) {
            formattedCurrency = CommonUtil.formatCurrency(number)
        }
        else {
            formattedCurrency = "(${CommonUtil.formatCurrency(-1 * number)})"
        }
        return formattedCurrency
    }

    public static String formatCurrencyFriendlyWithOrWithoutDecimalPoint(double number){
        NumberFormat formatter = (java.lang.Math.ceil(java.lang.Math.abs(number))-java.lang.Math.abs(number))>0 ? new DecimalFormat("###,###,###.00;(###,###,###.00)") : new DecimalFormat("###,###,###;(###,###,###)")
        return formatter.format(number)
    }

    public static String formatCurrencyFriendlyRoundValue(double number){
        NumberFormat formatter = new DecimalFormat("###,###,###;(###,###,###)")
        return formatter.format(java.lang.Math.round(number))
    }

    public static double formatStringToDouble(String number) {
        if (!number) {
            return 0.0d
        }
        String strNum = number.replace(",", "")
        double dValue = Double.parseDouble(strNum)
        return dValue
    }


    public static String getOnlyNumerics(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    public static String getOnlyString(String str) {

        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if (!Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    public static String camelCaseToSentence(String str) {
        return str.toCharArray().collect {if (it.isUpperCase()) { " ${it}" } else {it}}.join()
    }
    
    public static String formatStringNumber(long number){
        String numberStr = CommonConstants.EMPTY_STRING
        switch (number) {
            case 1:
                numberStr = "1st";
                break;
            case 2:
                numberStr = "2nd";
                break;
            case 3:
                numberStr = "3rd"
                break;
            default:
                numberStr = number.toString() + "th Year"
        }
        return numberStr
    }

    public static String formatCurrencyForPAR(double number, String decimalSeparator = '.', String thousandSeparator = ',') {
        if (number == 0.0) {
            return "0.0000"
        }
        NumberFormat formatter = new DecimalFormat("###" + thousandSeparator + "##0" + decimalSeparator + "0000");
        return formatter.format(number)
    }
}