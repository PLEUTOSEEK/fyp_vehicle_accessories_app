/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class DataResult {

    private final List<String> columnNames;
    private final List<List<Object>> data;

    public DataResult(List<String> columnNames, List<List<Object>> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getNumColumns() {
        return columnNames.size();
    }

    public String getColumnName(int index) {
        return columnNames.get(index);
    }

    public int getNumRows() {
        return data.size();
    }

    public Object getData(int column, int row) {
        return data.get(row).get(column);
    }

    public List<List<Object>> getData() {
        return data;
    }
}
