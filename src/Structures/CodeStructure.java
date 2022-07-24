/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structures;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CodeStructure {

    private String name;
    private String year; // last two digit of year
    private String month;// two digit of month
    private String seqNum;

    public CodeStructure() {
        this("", "", "", "");
    }

    public CodeStructure(String name, String year, String month, String seqNum) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.seqNum = seqNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public static CodeStructure strToStruct(String ID) {
        CodeStructure splittedID = new CodeStructure();
        int currentIndex = 0;

        for (int i = 0; i < ID.length(); i++) {

            if (!Character.isDigit(ID.charAt(i))) {
                splittedID.name += ID.charAt(i);
            } else {
                currentIndex = i;
                break;
            }
        }

        for (int i = 0; i < 2; i++) {
            splittedID.year += ID.charAt(currentIndex);
            currentIndex++;
        }

        for (int i = 0; i < 2; i++) {
            splittedID.month += ID.charAt(currentIndex);
            currentIndex++;
        }

        //skip the '-'
        currentIndex++;

        while (currentIndex < ID.length()) {
            splittedID.seqNum += ID.charAt(currentIndex);
            currentIndex++;
        }

        return splittedID;
    }

    public static String structToStr(CodeStructure ID) {
        return ID.name + ID.year + ID.month + "-" + ID.seqNum;
    }

}
