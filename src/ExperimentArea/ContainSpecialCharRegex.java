/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ContainSpecialCharRegex {

    public static void main(String[] args) {
        String str = "D12A";
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println("Contain Special");
        } else {
            System.out.println("Not Contain Special");
        }
        System.out.println("^(?=.*\\d).+$");
    }
}
