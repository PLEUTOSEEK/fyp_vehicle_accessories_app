/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ItemDAO;
import Entity.Item;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ItemService {

    public static List<Item> getItemsByCIID(String code) {
        return ItemDAO.getItemsByCode(code);
    }

    public static List<Item> getItemByQuotID(String code) {
        return ItemDAO.getItemsByCode(code);
    }

    public static Collection<? extends Item> getItemBySOID(String code) {
        return ItemDAO.getItemsByCode(code);
    }

    public static List<Item> getItemByTOID(String code) {
        return ItemDAO.getItemsByCode(code);
    }

    public static List<Item> getItemByRDNID(String code) {
        return ItemDAO.getItemsByCode(code);
    }

    public static List<Item> getReturnableItemsBySO(String code) {
        return ItemDAO.getReturnableItemsBySO(code);
    }

}
