/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;

import adt.DoublyLinkedList;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class TestDoubly {

    public static void main(String[] args) {
        DoublyLinkedList<String> dl = new DoublyLinkedList<>();
        dl.addLast("sohai");
        dl.delLast();
        dl.addLast("sadffsdew");
        dl.addLast("sohesrgstrehgai");
        System.out.println(dl.getLast());
    }
}
