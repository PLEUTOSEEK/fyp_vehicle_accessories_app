/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Tee Zhuo Xuan
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Angelina Oon
 * @param <T>
 */
public class DoublyLinkedList<T extends Comparable<T>> implements ListInterface<T> {

    private Node head;
    private Node tail;
    private int length;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    private class Node<T extends Comparable<T>> {

        private Node prev;
        private T element;
        private Node next;

        public Node() {
            this(null);
        }

        public Node(T element) {
            this.prev = null;
            this.element = element;
            this.next = null;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;

        }
    }

    //Interface
    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    //utility
    private void incLength() {
        this.length += 1;
    }

    //utility
    private void decLength() {
        this.length -= 1;
    }

    //Interface
    @Override
    public boolean addFirst(T element) {
        Node newNode = new Node(element);

        if (isEmpty()) {
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head.setPrev(newNode);
        }
        this.head = newNode;
        incLength();

        return true;
    }

    //Interface
    @Override
    public boolean addLast(T element) {
        Node newNode = new Node(element);

        if (isEmpty()) {
            this.head = newNode;
        } else {
            newNode.setPrev(this.tail);
            this.tail.setNext(newNode);
        }
        this.tail = newNode;
        incLength();

        return true;
    }

    @Override
    public boolean addUnique(T element) {
        if (indexOf(element) == -1) {
            addLast(element);
            return true;
        }
        return false;
    }

    //Interface
    @Override
    public boolean addAt(T element, int givenPos) {
        if (givenPos >= 1 && givenPos <= length + 1) {

            if (givenPos == 1) {
                return addFirst(element);
            } else if (givenPos == this.length + 1) {
                return addLast(element);
            } else {
                Node newNode = new Node(element);

                Node current = loopThroughMiddle(givenPos);

                newNode.setNext(current);
                newNode.setPrev(current.getPrev());

                current.getPrev().setNext(newNode);
                current.setPrev(newNode);

                incLength();

                return true;
            }

        }

        return false;
    }

    //Interface
    @Override
    public boolean replaceFirst(T element) {

        if (!isEmpty()) {
            Node newNode = new Node(element);
            if (this.length == 1) {
                this.tail = newNode;
            } else {
                Node current = this.head;

                newNode.setNext(current.getNext());
                current.getNext().setPrev(newNode);
            }

            this.head = newNode;
            return true;
        }
        return false;
    }

    //Interface
    @Override
    public boolean replaceLast(T element) {
        if (!isEmpty()) {

            Node newNode = new Node(element);
            if (this.length == 1) {
                this.head = newNode;
            } else {
                Node current = this.tail;

                newNode.setPrev(current.getPrev());
                current.getPrev().setNext(newNode);
            }

            this.tail = newNode;
            return true;
        }

        return false;
    }

    //Interface
    @Override
    public boolean replaceAt(T element, int givenPos) {
        if (givenPos >= 1 && givenPos <= length) {

            if (givenPos == 1) {
                return replaceFirst(element);
            } else if (givenPos == this.length) {
                return replaceLast(element);
            } else {
                Node newNode = new Node(element);

                Node current = loopThroughMiddle(givenPos);

                newNode.setPrev(current.getPrev());
                newNode.setNext(current.getNext());

                current.getPrev().setNext(newNode);
                current.getNext().setPrev(newNode);
                return true;
            }

        }
        return false;
    }

    //Interface
    @Override
    public boolean delFirst() {
        if (!isEmpty()) {
            this.head = this.head.getNext();

            if (this.head == null) {
                this.tail = null;
            } else {
                this.head.setPrev(null);
            }

            decLength();
            return true;
        }
        return false;
    }

    //Interface
    @Override
    public boolean delLast() {
        if (!isEmpty()) {
            this.tail = this.tail.getPrev();

            if (this.tail == null) {
                this.head = null;
            } else {
                this.tail.setNext(null);
            }

            decLength();
            return true;
        }
        return false;
    }

    //Interface
    @Override
    public boolean delAt(int givenPos) {
        //givenPos is start from 1

        if (givenPos >= 1 && givenPos <= length) {

            if (givenPos == 1) {
                return delFirst();
            } else if (givenPos == this.length) {
                return delLast();
            } else {
                Node current = loopThroughMiddle(givenPos);

                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());

                decLength();
                return true;
            }

        }

        return false;
    }

    //Interface
    @Override
    public Object getFirst() {
        //givenPos is start from 1

        if (!isEmpty()) {
            return this.head.element;
        }

        return null;
    }

    //Interface
    @Override
    public Object getLast() {
        //givenPos is start from 1

        if (!isEmpty()) {
            return this.tail.element;
        }

        return null;
    }

    //Interface
    @Override
    public T getAt(int givenPos) {
        //givenPos is start from 1

        if (givenPos >= 1 && givenPos <= length) {

            if (givenPos == 1) {

                return (T) this.head.element;

            } else if (givenPos == this.length) {

                return (T) this.tail.element;

            } else {

                Node current = loopThroughMiddle(givenPos);

                return (T) current.element;

            }

        }

        return null;
    }

    // Utility - if the index is first DON'T use this utility method, if index is last then can
    private Node loopThroughMiddle(int givenPos) {

        if (givenPos > 1 && givenPos <= length) {
            Node current = null;
            if ((this.length - givenPos) >= (givenPos)) { // if more near biginning then forard search
                current = this.head;
                for (int i = 2; i <= givenPos; i++) {
                    current = current.getNext();
                }
            } else {
                current = this.tail;
                for (int i = length - 1; i >= givenPos; i--) { // if more near the end then backward search
                    current = current.getPrev();
                }
            }
            return current;
        }

        return null;
    }

    // Interface
    @Override
    public boolean joinFirst(ListInterface otherList) {
        if (otherList.equals(this)) { // when self join self

            DoublyLinkedList<T> newList = new DoublyLinkedList<>();
            newList.joinFirst(otherList);

            joinFirst(newList);

            return true;
        }

        if (otherList instanceof DoublyLinkedList) {
            DoublyLinkedList newList = (DoublyLinkedList) otherList;

            /*shallow copy
            if (!isEmpty()) {
                if (!newList.isEmpty()) {

                    newList.getTail().setNext(this.head);
                    this.head.setPrev(newList.getTail());
                    this.head = newList.getHead();
                    this.length += newList.getLength();

                }

            } else {

                this.head = newList.getHead();
                this.tail = newList.getTail();
                this.length = newList.getLength();

            }
             */
            // deep copy
            if (!newList.isEmpty()) {
                Node current = newList.getTail();
                while (current != null) {
                    this.addFirst((T) current.getElement());
                    current = current.getPrev();
                }
                return true;
            }

        }
        return false;
    }

    public boolean deepCopy(ListInterface otherList) {
        clear();
        if (otherList instanceof DoublyLinkedList) {
            DoublyLinkedList newList = (DoublyLinkedList) otherList;

            if (!newList.isEmpty()) {
                Node current = newList.getHead();
                while (current != null) {
                    this.addFirst((T) current.getElement());
                    current = current.getNext();
                }
                return true;
            }
        }
        return false;
    }

    // Interface
    @Override
    public boolean joinLast(ListInterface otherList) {
        if (otherList.equals(this)) { // when self join self

            DoublyLinkedList<T> newList = new DoublyLinkedList<>();
            newList.joinLast(otherList);

            joinLast(newList);

            return true;
        }

        if (otherList instanceof DoublyLinkedList) {
            DoublyLinkedList newList = (DoublyLinkedList) otherList;
            /*
            if (!isEmpty()) {
                if (!newList.isEmpty()) {
                     shallowCopy
                    newList.getHead().setPrev(this.tail);
                    this.tail.setNext(newList.getHead());
                    this.tail = newList.getTail();
                    this.length += newList.getLength();


                }
            } else {
                this.head = newList.getHead();
                this.tail = newList.getTail();
                this.length = newList.getLength();
            }
             */

            //deep copy
            if (!newList.isEmpty()) {
                Node current = newList.getHead();
                while (current != null) {
                    this.addLast((T) current.getElement());
                    current = current.getNext();
                }
                return true;
            }
        }
        return false;
    }

    // Interface
    @Override
    public boolean joinAt(ListInterface otherList, int givenPos) {
        if (otherList.equals(this)) { // when self join self

            DoublyLinkedList<T> newList = new DoublyLinkedList<>();
            newList.joinLast(otherList);

            joinAt(newList, givenPos);

            return true;
        }

        if (givenPos >= 1 && givenPos <= length + 1) {

            if (otherList instanceof DoublyLinkedList) {
                DoublyLinkedList newList = (DoublyLinkedList) otherList;
                /*shallow copy
                if (givenPos == 1) {
                    joinFirst(newList);
                } else if (givenPos == this.length) {
                    joinLast(newList);
                } else {

                    if (!newList.isEmpty()) {

                        Node current = loopThroughMiddle(givenPos);
                        newList.getTail().setNext(current);
                        newList.getHead().setPrev(current.getPrev());

                        current.setPrev(newList.getTail());
                        current.getPrev().setNext(newList.getHead());

                    }

                }
                 */

                //deep copy
                if (givenPos == 1) {
                    return joinFirst(newList);
                } else if (givenPos == this.length + 1) {
                    return joinLast(newList);
                } else {

                    if (!newList.isEmpty()) {

                        Node newListCurrent = newList.getTail();
                        while (newListCurrent != null) {
                            addAt((T) newListCurrent.getElement(), givenPos);
                            newListCurrent = newListCurrent.getPrev();
                        }
                        return true;
                    }

                }

            }
        }

        return false;
    }

    //Interface
    @Override
    public int indexOf(T element) {
        Node current = this.head;
        int counter = 1;

        while (current != null) {

            if (current.getElement().equals(element)) {
                return counter;
            }

            current = current.getNext();
            counter += 1;
        }

        return -1;
    }

    //Interface
    @Override
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    public void quickSort() {
        quickSort(this, 1, length);
    }

    public void quickSort(DoublyLinkedList<T> linkedList, int lb, int ub) {

        // start (if less than equal pivot, move forward
        // end (if greater than pivot, move backward
        // both stop then perform swap with end and start.
        // start index is greater than end, then dont swap.
        /////end swap with pivot (at this point, partition is successfully created
        if (lb < ub) {

            int pivotIndex = partition(this, lb, ub);

            quickSort(this, lb, pivotIndex - 1);
            //System.out.println(lb + " " + ub + " " + pivotIndex);
            quickSort(this, pivotIndex + 1, ub);
        }
    }

    public int partition(DoublyLinkedList<T> linkedList, int lb, int ub) {
        T pivot = linkedList.getAt(lb);
        int start = lb;
        int end = ub;

        while (start < end) {
            while (start <= ub && linkedList.getAt(start).compareTo(pivot) <= 0) {

                start++;
            }

            while (end >= lb && linkedList.getAt(end).compareTo(pivot) > 0) {
                end--;
            }

            if (start < end) {
                swap(linkedList, start, end);
            }
        }
        swap(linkedList, lb, end);
        return end;
    }

    public void swap(DoublyLinkedList<T> linkedList, int first, int sec) {
        T element = linkedList.getAt(first);

        linkedList.replaceAt(linkedList.getAt(sec), first);
        linkedList.replaceAt(element, sec);
    }

    @Override
    public void printForward() {
        Node current = this.head;
        while (current != null) {
            System.out.print("[" + current.getElement() + "], ");
            current = current.getNext();
        }
        System.out.println();
    }

    @Override
    public void printBackward() {
        Node current = this.tail;
        while (current != null) {
            System.out.print("[" + current.getElement() + "], ");
            current = current.getPrev();
        }
        System.out.println();
    }

    public T[] toArray(T[] array) {

        Node current = this.head;
        if (current != null) {

            int index = 0;

            while (current != null) {
                try {
                    array[index++] = ((T) current.element);
                    current = current.getNext();
                } catch (Exception e) {
                    break;
                }
            }
            return (T[]) array;
        } else {
            return null;
        }

    }

}
