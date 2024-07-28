/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.raretag.mawa.generic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tebogom
 */
public class Conversion {

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String dateToString(Date date) {
        String stringDate = "";

        if (date != null) {
            stringDate = formatter.format(date);
        }
        return stringDate;
    }

    public static Date stringToDate(String date) {
        Date returnDate = null;
        try {
            returnDate = formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(Conversion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnDate;
    }

    public static Date addMonthsToDate(Date date, Integer months) {

//        String dt = "2008-01-01";  // Start date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months);  // number of months to add
        return c.getTime();
    }

    public static Date addDaysToDate(Date date, Integer days) {

//        String dt = "2008-01-01";  // Start date
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);  // number of days to add
        return c.getTime();
    }

    public static String getGenderFromID(String idNumber) {
        String gender = "";
        if (Integer.parseInt(idNumber.substring(7, 8)) <= 4) {
            gender = "G001";
        } else if ((Integer.parseInt(idNumber.substring(7, 8)) > 4)) {
            gender = "G002";
        }

        return gender;
    }

    public static BigDecimal stringToBigDecimal(String value) {
        value = value.replace(",", "");
        return new BigDecimal(value);
    }
    public static String stringToBigDecimal(BigDecimal value) {        
        return value.toString();
    }

    public static Integer stringToInteger(String value) {
        value = value.replace(",", "");
        return new Integer(value);
    }

    public static String getPartnerName(za.raretag.mawa.entities.Partner partner) {
        String name = "";
        if (partner != null) {
            if (partner.getName1() != null) {
                name = name.concat(partner.getName1());
            }
            if (partner.getName2() != null) {
                name = name + " " + partner.getName2();
            }
            if (partner.getName3() != null) {
                name = name + " " + partner.getName3();
            }
        }else{
            name = "Not set";
        }
        return name;
    }

    public static boolean validateIDNumber(String idNumber) {

        boolean correct = true;
//        Date tempDate = new Date(idNumber.substring(0, 2), idNumber.substring(2, 4), idNumber.substring(4, 6));
//        var id_date = tempDate.getDate();
//        var id_month = tempDate.getMonth();
//        var id_year = tempDate.getFullYear();
//        var fullDate = id_date + "-" + (id_month + 1) + "-" + id_year;
//        if (!((tempDate.getYear() == = idNumber.substring(0, 2)) && (id_month == = idNumber.substring(2, 4) - 1) && (id_date == = idNumber.substring(4, 6)))) {
//            mawa.showMessage("E", "ID Number is invalid");
//            correct = false;
//            return;
//        }
//
//        // get the gender
//        var genderCode = idNumber.substring(6, 10);
//        var gender = parseInt(genderCode) < 5000 ? "Female" : "Male";
//        // get country ID for citzenship
//        var citzenship = parseInt(idNumber.substring(10, 11)) == = 0 ? "Yes" : "No";
//        // apply Luhn formula for check-digits
//        var tempTotal = 0;
//        var checkSum = 0;
//        var multiplier = 1;
//        for (var i = 0; i < 13; ++i) {
//            tempTotal = parseInt(idNumber.charAt(i)) * multiplier;
//            if (tempTotal > 9) {
//                tempTotal = parseInt(tempTotal.toString().charAt(0)) + parseInt(tempTotal.toString().charAt(1));
//            }
//            checkSum = checkSum + tempTotal;
//            multiplier = (multiplier % 2 == = 0) ? 1 : 2;
//        }
//        if ((checkSum % 10) != = 0) {
//            mawa.showMessage("E", "ID Number is invalid");
//            correct = false;
//            return;
//        }
//        ;
//        // if no error found, hide the error message

        return correct;
    }
}
