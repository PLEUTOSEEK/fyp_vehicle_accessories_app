/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class removeSpace {

    public static void main(String[] args) {
        String myString = "0 13-951 9680";
        String result = myString.replaceAll("\\s", "");
        System.out.println(result);
    }
}
