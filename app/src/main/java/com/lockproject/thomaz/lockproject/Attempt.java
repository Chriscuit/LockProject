package com.lockproject.thomaz.lockproject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by christophergill on 12/19/17.
 */

public class Attempt {

    Boolean correct;
    public String correct_pattern;
    public String input_pattern;
    public double accuracy_unord;
//    public double accuracy_ord;
    public Boolean match_length;
    public Date currentTime;

    Attempt(Boolean correct,
            String correct_pattern,
            String input_pattern) {

        this.correct = correct;
        this.correct_pattern = correct_pattern;
        this.input_pattern = input_pattern;
//        this.accuracy_ord = calcAccOrd(correct_pattern, input_pattern);
        this.accuracy_unord = calcAccUnord(correct_pattern, input_pattern);
        this.match_length = checkLengthMatch(correct_pattern, input_pattern);
        this.currentTime = Calendar.getInstance().getTime();
    }

//    private double calcAccOrd(String correct_pattern, String input_pattern) {
//
//        char[] cp_arr = correct_pattern.toCharArray();
//        char[] ip_arr = input_pattern.toCharArray();
//
//        int score = 0;
//
//        for(int i = 0; i < ip_arr.length; i++) {
//
//            for(int j = 0; j < cp_arr.length &&; j++) {
//
//                if(ip_arr[i] == cp_arr[j]) {
//                    score
//                }
//            }
//
//        }
//    }

    private double calcAccUnord(String correct_pattern, String input_pattern) {

        char[] cp_arr = correct_pattern.toCharArray();
        char[] ip_arr = input_pattern.toCharArray();

        Set<Character> cp_set = new HashSet<>();
        for(Character c : cp_arr) {
            cp_set.add(c);
        }

        Set<Character> ip_set = new HashSet<>();
        for(Character c : ip_arr) {
            ip_set.add(c);
        }

        ip_set.retainAll(ip_set);

        Double correct_nodes = new Double(ip_set.size());

        Double soln_size = new Double(cp_set.size());

        return correct_nodes/soln_size;
    }

    private Boolean checkLengthMatch(String correct_pattern, String input_pattern) {

        if(correct_pattern.length() == input_pattern.length()) return true;
        else return false;
    }

}
