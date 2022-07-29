/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Quotation;
import Service.QuotationService;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class EntityOverviewCONTR implements Initializable {

    @FXML
    private MFXTableView<?> tblVw;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // draw table here
        setupTable();
        tblVw.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        forQuotation();
    }

    private void forQuotation() {
        //1
        MFXTableColumn<Quotation> quotIDCol = new MFXTableColumn<>("Quotation ID", true, Comparator.comparing(quot -> quot.getCode()));
        MFXTableColumn<Quotation> ciCol = new MFXTableColumn<>("Cusrtomer Inquiry ID", true, Comparator.comparing(quot -> quot.getCI() == null ? "" : quot.getCI().getCode()));
        MFXTableColumn<Quotation> refTypeCol = new MFXTableColumn<>("Reference Type", true, Comparator.comparing(quot -> quot.getReferenceType()));
        MFXTableColumn<Quotation> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(quot -> quot.getReference()));
        MFXTableColumn<Quotation> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(quot -> quot.getBillToCust().getCustID()));
        MFXTableColumn<Quotation> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(quot -> quot.getBillToCust().getName()));
        MFXTableColumn<Quotation> deliverToIDCol = new MFXTableColumn<>("Deliver To Customer ID", true, Comparator.comparing(quot -> quot.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<Quotation> deliverToNmCol = new MFXTableColumn<>("Deliver To Customer Name", true, Comparator.comparing(quot -> quot.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<Quotation> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(quot -> quot.getSalesPerson().getStaffID()));
        MFXTableColumn<Quotation> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(quot -> quot.getSalesPerson().getName()));
        MFXTableColumn<Quotation> quotValDtCol = new MFXTableColumn<>("Quotation Validity Date", true, Comparator.comparing(quot -> quot.getQuotValidityDate()));
        MFXTableColumn<Quotation> reqDlvrDtCol = new MFXTableColumn<>("Required Delivery Date", true, Comparator.comparing(quot -> quot.getRequiredDeliveryDate()));
        MFXTableColumn<Quotation> grossDtCol = new MFXTableColumn<>("Gross", true, Comparator.comparing(quot -> quot.getGross()));
        MFXTableColumn<Quotation> discDtCol = new MFXTableColumn<>("Discount", true, Comparator.comparing(quot -> quot.getDiscount()));
        MFXTableColumn<Quotation> subTtlDtCol = new MFXTableColumn<>("Sub Total", true, Comparator.comparing(quot -> quot.getSubTotal()));
        MFXTableColumn<Quotation> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(quot -> quot.getNett()));

        //2
        quotIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCode()));
        ciCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCI() == null ? "" : quot.getCI().getCode()));
        refTypeCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReferenceType()));
        refCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReference()));
        billToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson().getName()));
        quotValDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getQuotValidityDate()));
        reqDlvrDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getRequiredDeliveryDate()));
        grossDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getGross()));
        discDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDiscount()));
        subTtlDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSubTotal()));
        nettDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getNett()));

        //3
        ((MFXTableView<Quotation>) tblVw).getTableColumns().addAll(quotIDCol,
                ciCol,
                refTypeCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                quotValDtCol,
                reqDlvrDtCol,
                grossDtCol,
                discDtCol,
                subTtlDtCol,
                nettDtCol);

        //4
        ((MFXTableView<Quotation>) tblVw).getFilters().addAll(
                new StringFilter<>("Quotation ID", quot -> quot.getCode()),
                new StringFilter<>("Customer Inquiry ID", quot -> quot.getCI().getCode()),
                new StringFilter<>("Reference Type", quot -> quot.getReferenceType()),
                new StringFilter<>("Reference", quot -> quot.getReference()),
                new StringFilter<>("Bill To Customer ID", quot -> quot.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", quot -> quot.getBillToCust().getName()),
                new StringFilter<>("Deliver To Customer ID", quot -> quot.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Customer Name", quot -> quot.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", quot -> quot.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", quot -> quot.getSalesPerson().getName()),
                new StringFilter<>("Quotation Validity Date", quot -> quot.getQuotValidityDate().toString()),
                new StringFilter<>("Required Delivery Date", quot -> quot.getRequiredDeliveryDate().toString()),
                new DoubleFilter<>("Gross", quot -> quot.getGross().doubleValue()),
                new DoubleFilter<>("Discount", quot -> quot.getDiscount().doubleValue()),
                new DoubleFilter<>("Sub Total", quot -> quot.getSubTotal().doubleValue()),
                new DoubleFilter<>("Nett", quot -> quot.getNett().doubleValue())
        );

        //5
        List<Quotation> quots = QuotationService.getAllQuotation();
        System.out.println(quots.get(0).getRequiredDeliveryDate());

        //6
        ((MFXTableView<Quotation>) tblVw).setItems(FXCollections.observableList(quots));
        System.out.println("done go through function");
    }

}
