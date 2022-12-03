/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BizRulesConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class WarehouseRules {

    public enum RDNStatus {
        NEW,
        APPROVED,
        REJECTED,
        RETURNING,
        RETURNED
    }

    public enum PSStatus {
        NOT_YET_REFERED,
        REFERED
    }

    public enum DOStatus {
        NEW,
        DELIVERING,
        DELIVERED
    }

    public enum TOStatus {
        NEW,
        TRANSFERING,
        TRANSFERRED
    }

    private List<String> refTypeForTO;

    private List<String> shipmentTerms;

    private List<DOStatus> doStatuses;

    private List<TOStatus> toStatuses;

    private List<RDNStatus> rdnStatuses;

    public WarehouseRules() {
        shipmentTerms = new ArrayList<>();
        refTypeForTO = new ArrayList<>();

        shipmentTerms.add("EXW");
        shipmentTerms.add("DDP");

        doStatuses = Arrays.asList(DOStatus.values());
        toStatuses = Arrays.asList(TOStatus.values());
        rdnStatuses = Arrays.asList(RDNStatus.values());

        refTypeForTO.add("Sales Order (SO)");
        refTypeForTO.add("Return Delivery Note (RDN)");
    }

    public List<String> getShipmentTerms() {
        return shipmentTerms;
    }

    public void setShipmentTerms(List<String> shipmentTerms) {
        this.shipmentTerms = shipmentTerms;
    }

    public List<DOStatus> getDoStatuses() {
        return doStatuses;
    }

    public void setDoStatuses(List<DOStatus> doStatuses) {
        this.doStatuses = doStatuses;
    }

    public List<String> getRefTypeForTO() {
        return refTypeForTO;
    }

    public void setRefTypeForTO(List<String> refTypeForTO) {
        this.refTypeForTO = refTypeForTO;
    }

    public List<TOStatus> getToStatuses() {
        return toStatuses;
    }

    public void setToStatuses(List<TOStatus> toStatuses) {
        this.toStatuses = toStatuses;
    }

    public List<RDNStatus> getRdnStatuses() {
        return rdnStatuses;
    }

    public void setRdnStatuses(List<RDNStatus> rdnStatuses) {
        this.rdnStatuses = rdnStatuses;
    }
}
