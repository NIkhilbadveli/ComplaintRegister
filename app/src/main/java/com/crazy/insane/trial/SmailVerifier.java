package com.crazy.insane.trial;

import android.util.Log;

public class SmailVerifier {

    public boolean verify(String email){
        if(email.length() != 28)
            return false;
        String check = email.substring(9, email.length());
        int flag = 0;
        if(check.equals("@smail.iitpkd.ac.in"))
            flag = 1;
        else
            flag = 0;
        if(flag == 0) {
            Log.v("SmailVerifier","first case");
            return false;
        }
        String check1 = email.substring(2, 7);
        if(check1.equals("15010")||check1.equals("16010"))
            flag = 1;
        else
            flag = 0;
        if(flag == 0) {
            Log.v("SmailVerifier","second case");

            return false;
        }
        String s = email.substring(0,2);
        int num = Integer.parseInt(s);
        if(num >= 10 && num <= 13)
            flag = 1;
        else
            flag = 0;
        if(flag == 0) {
            Log.v("SmailVerifier","third case");

            return false;
        }
        String s1 = email.substring(7,9);
        int num1 = Integer.parseInt(s1);
        if(num1 >= 01 && num1 <= 40)
            flag = 1;
        else
            flag = 0;
        if(flag == 0) {
            Log.v("SmailVerifier","fourth case");

            return false;
        }
        else {
            Log.v("SmailVerifier","True");
            return true;
        }
    };
}
