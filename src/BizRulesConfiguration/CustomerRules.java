/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class CustomerRules extends ActorRules {

    private List<String> custTypes;
    private List<String> bankAccProviders;

    public CustomerRules() {
        custTypes = new ArrayList<>();
        bankAccProviders = new ArrayList<>();
        statuses = new ArrayList<>();

        custTypes.add("Individual");
        custTypes.add("Organization");

        bankAccProviders.add("HSBC Bank");
        bankAccProviders.add("Public Bank");
        bankAccProviders.add("May Bank");
        bankAccProviders.add("OCBC Bank");
        bankAccProviders.add("Alliance Bank");
        bankAccProviders.add("Affin Bank");
        bankAccProviders.add("ICBC Bank");
        bankAccProviders.add("RHB");
        bankAccProviders.add("Hong Leong");

        statuses.add("Active");
        statuses.add("Inactive");
    }

    public List<String> getCustTypes() {
        return custTypes;
    }

    public List<String> getBankAccProviders() {
        return bankAccProviders;
    }
}
