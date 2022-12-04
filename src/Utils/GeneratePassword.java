/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class GeneratePassword {
    // main() method start

    public static void main(String args[]) {

        // call generateSecurePassword() method to generate random password using RandomStringUtils
        String pass = generateSecurePassword();

        // print RandomStringUtils password
        System.out.println("Password generated by RandomStringUtils class is:" + pass);

    }

    // create generateSecurePassword() method that find the secure 10 digit password and returns it to the main() method
    public static String generateSecurePassword() {

        // generate a string of upper case letters having length 2
        String upperCaseStr = RandomStringUtils.random(2, 65, 90, true, true);

        // generate a string of lower case letters having length 2
        String lowerCaseStr = RandomStringUtils.random(2, 97, 122, true, true);

        // generate a string of numeric letters having length 2
        String numbersStr = RandomStringUtils.randomNumeric(2);

        // generate a string of special chars having length 2
        String specialCharStr = RandomStringUtils.random(2, 33, 47, false, false);

        // generate a string of alphanumeric letters having length 2
        String totalCharsStr = RandomStringUtils.randomAlphanumeric(2);

        // concatenate all the strings into a single one
        String demoPassword = upperCaseStr.concat(lowerCaseStr)
                .concat(numbersStr)
                .concat(specialCharStr)
                .concat(totalCharsStr);

        // create a list of Char that stores all the characters, numbers and special characters
        List<Character> listOfChar = demoPassword.chars()
                .mapToObj(data -> (char) data)
                .collect(Collectors.toList());

        // use shuffle() method of the Collections to shuffle the list elements
        Collections.shuffle(listOfChar);

        //generate a random string(secure password) by using list stream() method and collect() method
        String password = listOfChar.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        // return RandomStringGenerator password to the main() method
        return password;
    }
}