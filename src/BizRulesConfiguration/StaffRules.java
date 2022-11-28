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
public class StaffRules extends ActorRules {

    private List<String> accStatuses;
    private List<String> roles;
    private List<String> empTypes;

    /*
        public StaffRules() {
        accStatuses = StaffRulesDAO.getAccStatuses();
        roles = StaffRulesDAO.getRoles();
        empTypes = StaffRulesDAO.getEmpTypes();
    }
     */
    public StaffRules() {
        races = new ArrayList<>();
        religions = new ArrayList<>();
        nationalities = new ArrayList<>();
        honorifics = new ArrayList<>();
        maritalStatuses = new ArrayList<>();
        genders = new ArrayList<>();
        statuses = new ArrayList<>();
        accStatuses = new ArrayList<>();
        roles = new ArrayList<>();
        empTypes = new ArrayList<>();

        races.add("Chinese");
        races.add("Malays");
        races.add("Indians");
        races.add("Other");

        religions.add("Buddhist");
        religions.add("Muslim");
        religions.add("Christian");
        religions.add("Hindu");
        religions.add("Other");

        nationalities.add("Malaysian");

        honorifics.add("Mr.");
        honorifics.add("Mrs.");
        honorifics.add("Ms.");

        maritalStatuses.add("Single");
        maritalStatuses.add("Married");
        maritalStatuses.add("Widowed");
        maritalStatuses.add("Separated");
        maritalStatuses.add("Divorced ");
        maritalStatuses.add("Other");

        genders.add("Male");
        genders.add("Female");
        genders.add("Other");

        statuses.add("Active");
        statuses.add("Resigned");

        accStatuses.add("Active");
        accStatuses.add("Freezed");

        roles.add("User");
        roles.add("Salesman");
        roles.add("Warehouseman");
        roles.add("Accountant");
        roles.add("Administrator");

        empTypes.add("Full-Time");
        empTypes.add("Part-Time");

    }

    public List<String> getAccStatuses() {
        return accStatuses;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getEmpTypes() {
        return empTypes;
    }

}
