package com.example.nexashare.Helper;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    public boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    public boolean isEqualPassword(String pass1,String pass2){
        Boolean match =false;
        if(pass1.equals(pass2)){
            match =false;
        }
        return match;

    }
    public static boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

//    public boolean isValidPhoneNumber(String phoneNumber) {
//        // Remove any non-digit characters
//        phoneNumber = phoneNumber.replaceAll("\\D", "");
//
//        // Check if the phone number is exactly 10 digits and starts with "07" or "01"
//        return phoneNumber.length() == 10 && (phoneNumber.startsWith("07") || phoneNumber.startsWith("01"));
//    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if phone number starts with '0' and has exactly 10 digits
        return phoneNumber != null && phoneNumber.startsWith("0") && phoneNumber.length() == 10 && phoneNumber.matches("\\d+");
    }
}
