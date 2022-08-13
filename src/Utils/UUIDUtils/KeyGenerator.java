/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.UUIDUtils;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class KeyGenerator {

    public static String next() {
        return UlidCreator.getUlid().toString();
    }
}
