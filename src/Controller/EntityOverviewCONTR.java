/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.DeliveryOrder;
import Entity.Invoice;
import Entity.PackingSlip;
import Entity.Quotation;
import Entity.Receipt;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.CustomerInquiryService;
import Service.CustomerService;
import Service.DeliveryOrderService;
import Service.InvoiceService;
import Service.PackingSlipService;
import Service.QuotationService;
import Service.RDNService;
import Service.ReceiptService;
import Service.SalesOrderService;
import Service.StaffService;
import Service.TransferOrderService;
import Utils.DateFilter;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tee Zhuo Xuan
 */
public class EntityOverviewCONTR implements Initializable, BasicCONTRFunc {

    private static List<String> rowSelected = new ArrayList<>();

    private BasicObjs passObj;

    @FXML
    private MFXTableView<?> tblVw;
    @FXML
    private MFXCircleToggleNode btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // draw table here

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                receiveData();
                setupTable();
                tblVw.autosizeColumnsOnInitialization();
            }
        });
    }

    private void setupTable() {

        Object entity = this.passObj.getObj();

        if (entity instanceof Staff) {
            forStaff();
        } else if (entity instanceof Customer) {
            forCustomer();
        } else if (entity instanceof CustomerInquiry) {
            forCustomerInquiry();
        } else if (entity instanceof Quotation) {
            forQuotation();
        } else if (entity instanceof SalesOrder) {
            forSalesOrder();
        } else if (entity instanceof TransferOrder) {
            forTransferOrder();
        } else if (entity instanceof PackingSlip) {
            forPackingSlip();
        } else if (entity instanceof DeliveryOrder) {
            forDeliveryOrder();
        } else if (entity instanceof ReturnDeliveryNote) {
            forReturnDeliveryNote();
        } else if (entity instanceof Invoice) {
            forInvoice();
        } else if (entity instanceof Receipt) {
            forPayment();
        }
    }

    private void forStaff() {

        // Staff ID
        MFXTableColumn<Staff> staffIDCol = new MFXTableColumn<>("Staff ID", true, Comparator.comparing(staff -> staff.getStaffID()));
        // Name
        MFXTableColumn<Staff> staffNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(staff -> staff.getName()));
        // Mobile No
        MFXTableColumn<Staff> mobNoCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(staff -> staff.getContact().getMobileNo()));
        // Office Phone Number
        MFXTableColumn<Staff> offPhNoCol = new MFXTableColumn<>("Office Phone No.", true, Comparator.comparing(staff -> staff.getContact().getOffPhNo()));
        // Occupation
        MFXTableColumn<Staff> occupationCol = new MFXTableColumn<>("Occupation", true, Comparator.comparing(staff -> staff.getOccupation()));
        // Role
        MFXTableColumn<Staff> roleCol = new MFXTableColumn<>("Role", true, Comparator.comparing(staff -> staff.getRole()));

        // Staff ID
        staffIDCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getStaffID()));
        // Name
        staffNmCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getName()));
        // Mobile No
        mobNoCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getContact().getMobileNo()));
        // Office Phone Number
        offPhNoCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getContact().getOffPhNo()));
        // Occupation
        occupationCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getOccupation()));
        // Role
        roleCol.setRowCellFactory(staff -> new MFXTableRowCell<>(s -> s.getRole()));

        ((MFXTableView<Staff>) tblVw).getTableColumns().addAll(
                staffIDCol,
                staffNmCol,
                mobNoCol,
                offPhNoCol,
                occupationCol,
                roleCol);

        ((MFXTableView<Staff>) tblVw).getFilters().addAll(
                new StringFilter<>("Staff ID", staff -> staff.getStaffID()),
                new StringFilter<>("Name", staff -> staff.getName()),
                new StringFilter<>("Mobile No.", staff -> staff.getContact().getMobileNo()),
                new StringFilter<>("Office Phone No.", staff -> staff.getContact().getOffPhNo()),
                new StringFilter<>("Occupation", staff -> staff.getOccupation()),
                new StringFilter<>("Role", staff -> staff.getRole())
        );

        List<Staff> staffs = StaffService.getAllStaff();

        ((MFXTableView<Staff>) tblVw).setItems(FXCollections.observableList(staffs));

        ((MFXTableView<Staff>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Staff>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Staff staff = (((MFXTableView<Staff>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(staff.getStaffID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            System.out.println(staff.getStaffID());
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(staff);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/Staff_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

    }

    private void forCustomer() {
        // Customer ID
        MFXTableColumn<Customer> custIDCol = new MFXTableColumn<>("Customer ID", true, Comparator.comparing(cust -> cust.getCustID()));
        // Name
        MFXTableColumn<Customer> custNmCol = new MFXTableColumn<>("Name", true, Comparator.comparing(cust -> cust.getName()));
        // Email
        MFXTableColumn<Customer> emailCol = new MFXTableColumn<>("Email", true, Comparator.comparing(cust -> cust.getContact().getEmail()));
        // Mobile Number
        MFXTableColumn<Customer> mobNoCol = new MFXTableColumn<>("Mobile No.", true, Comparator.comparing(cust -> cust.getContact().getMobileNo()));
        // Bill To Address
        MFXTableColumn<Customer> billTo = new MFXTableColumn<>("Bill To Address", true, Comparator.comparing(cust -> cust.getBillToAddr().getLocationName()));

        // Customer ID
        custIDCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getCustID()));
        // Name
        custNmCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getName()));
        // Email
        emailCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getContact().getEmail()));
        // Mobile Number
        mobNoCol.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getContact().getMobileNo()));
        // Bill To Address
        billTo.setRowCellFactory(customer -> new MFXTableRowCell<>(cust -> cust.getBillToAddr().getLocationName()));

        ((MFXTableView<Customer>) tblVw).getTableColumns().addAll(
                custIDCol,
                custNmCol,
                emailCol,
                mobNoCol,
                billTo
        );

        ((MFXTableView<Customer>) tblVw).getFilters().addAll(
                new StringFilter<>("Customer ID", cust -> cust.getCustID()),
                new StringFilter<>("Name", cust -> cust.getName()),
                new StringFilter<>("Email", cust -> cust.getContact().getEmail()),
                new StringFilter<>("Mobile No.", cust -> cust.getContact().getMobileNo()),
                new StringFilter<>("Bill To Address", cust -> cust.getBillToAddr().getLocationName())
        );

        List<Customer> customers = CustomerService.getAllCustomers();

        ((MFXTableView<Customer>) tblVw).setItems(FXCollections.observableList(customers));

        ((MFXTableView<Customer>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Customer>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Customer customer = (((MFXTableView<Customer>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(customer.getCustID());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            System.out.println(customer.getCustID());
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(customer);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/Customer_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forCustomerInquiry() {
        MFXTableColumn<CustomerInquiry> ciIDCol = new MFXTableColumn<>("CustomerInquiry ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getCode()));
        MFXTableColumn<CustomerInquiry> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(customerInquiry -> customerInquiry.getReference()));
        MFXTableColumn<CustomerInquiry> billToIDCol = new MFXTableColumn<>("Bill To customer ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getCustID()));
        MFXTableColumn<CustomerInquiry> billToNmCol = new MFXTableColumn<>("Bill To customer Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getName()));
        MFXTableColumn<CustomerInquiry> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<CustomerInquiry> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<CustomerInquiry> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getStaffID()));
        MFXTableColumn<CustomerInquiry> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getName()));
        MFXTableColumn<CustomerInquiry> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(customerInquiry -> customerInquiry.getNett()));
        MFXTableColumn<CustomerInquiry> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(customerInquiry -> customerInquiry.getStatus()));

        ciIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getCode()));
        refCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getReference()));
        billToIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getBillToCust() == null ? "" : c.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getBillToCust() == null ? "" : c.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getDeliverToCust() == null ? "" : c.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getDeliverToCust() == null ? "" : c.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getSalesPerson() == null ? "" : c.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getSalesPerson() == null ? "" : c.getSalesPerson().getName()));
        nettDtCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getNett()));
        statusCol.setRowCellFactory(customerInquiry -> new MFXTableRowCell<>(c -> c.getStatus()));

        ((MFXTableView<CustomerInquiry>) tblVw).getTableColumns().addAll(
                ciIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                nettDtCol,
                statusCol);

        ((MFXTableView<CustomerInquiry>) tblVw).getFilters().addAll(
                new StringFilter<>("Customer Inquiry ID", customerInquiry -> customerInquiry.getCode()),
                new StringFilter<>("Reference", customerInquiry -> customerInquiry.getReference()),
                new StringFilter<>("Bill To Customer ID", customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", customerInquiry -> customerInquiry.getBillToCust() == null ? "" : customerInquiry.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Collector Name", customerInquiry -> customerInquiry.getDeliverToCust() == null ? "" : customerInquiry.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", customerInquiry -> customerInquiry.getSalesPerson() == null ? "" : customerInquiry.getSalesPerson().getName()),
                new DoubleFilter<>("Nett", customerInquiry -> customerInquiry.getNett().doubleValue()),
                new StringFilter<>("Status", customerInquiry -> customerInquiry.getStatus())
        );

        //5
        List<CustomerInquiry> customerInquiries = CustomerInquiryService.getAllCustomerInquiry();

        //6
        ((MFXTableView<CustomerInquiry>) tblVw).setItems(FXCollections.observableList(customerInquiries));
        //7
        ((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    CustomerInquiry customerInquiry = (((MFXTableView<CustomerInquiry>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(customerInquiry.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(customerInquiry);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/CustomerInquiry_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forQuotation() {
        //1
//1

        MFXTableColumn<Quotation> quotIDCol = new MFXTableColumn<>("Quotation ID", true, Comparator.comparing(quot -> quot.getCode()));
        MFXTableColumn<Quotation> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(quot -> quot.getReference()));
        MFXTableColumn<Quotation> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()));
        MFXTableColumn<Quotation> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()));
        MFXTableColumn<Quotation> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<Quotation> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<Quotation> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()));
        MFXTableColumn<Quotation> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()));
        MFXTableColumn<Quotation> quotValDtCol = new MFXTableColumn<>("Quotation Validity Date", true, Comparator.comparing(quot -> quot.getQuotValidityDate()));
        MFXTableColumn<Quotation> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(quot -> quot.getNett()));
        MFXTableColumn<Quotation> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(quot -> quot.getStatus()));

        //2
        quotIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getCode()));
        refCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getReference()));
        billToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()));
        quotValDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getQuotValidityDate()));
        nettDtCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getNett()));
        statusCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(quot -> quot.getStatus()));

        //3
        ((MFXTableView<Quotation>) tblVw).getTableColumns().addAll(quotIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                quotValDtCol,
                nettDtCol,
                statusCol);

        //4
        ((MFXTableView<Quotation>) tblVw).getFilters().addAll(
                new StringFilter<>("Quotation ID", quot -> quot.getCode()),
                new StringFilter<>("Reference", quot -> quot.getReference()),
                new StringFilter<>("Bill To Customer ID", quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", quot -> quot.getBillToCust() == null ? "" : quot.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Customer Name", quot -> quot.getDeliverToCust() == null ? "" : quot.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", quot -> quot.getSalesPerson() == null ? "" : quot.getSalesPerson().getName()),
                new StringFilter<>("Quotation Validity Date", quot -> quot.getQuotValidityDate().toString()),
                new DoubleFilter<>("Nett", quot -> quot.getNett().doubleValue()),
                new StringFilter<>("Status", quot -> quot.getStatus())
        );

        //5
        List<Quotation> quots = QuotationService.getAllQuotation();

        //6
        ((MFXTableView<Quotation>) tblVw).setItems(FXCollections.observableList(quots));
        //7
        ((MFXTableView<Quotation>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<Quotation>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    Quotation quotation = (((MFXTableView<Quotation>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(quotation.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(quotation);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/Quotations_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

    }

    private void forSalesOrder() {
        //1

        MFXTableColumn<SalesOrder> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(so -> so.getCode()));
        MFXTableColumn<SalesOrder> refCol = new MFXTableColumn<>("Reference", true, Comparator.comparing(so -> so.getReference()));
        MFXTableColumn<SalesOrder> billToIDCol = new MFXTableColumn<>("Bill To Customer ID", true, Comparator.comparing(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()));
        MFXTableColumn<SalesOrder> billToNmCol = new MFXTableColumn<>("Bill To Customer Name", true, Comparator.comparing(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()));
        MFXTableColumn<SalesOrder> deliverToIDCol = new MFXTableColumn<>("Deliver To ID", true, Comparator.comparing(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()));
        MFXTableColumn<SalesOrder> deliverToNmCol = new MFXTableColumn<>("Deliver To Collector Name", true, Comparator.comparing(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()));
        MFXTableColumn<SalesOrder> salesPersonIDCol = new MFXTableColumn<>("Sales Person ID", true, Comparator.comparing(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()));
        MFXTableColumn<SalesOrder> salesPersonNmCol = new MFXTableColumn<>("Sales Person Name", true, Comparator.comparing(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()));
        MFXTableColumn<SalesOrder> nettDtCol = new MFXTableColumn<>("Nett", true, Comparator.comparing(so -> so.getNett()));
        MFXTableColumn<SalesOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(so -> so.getStatus()));

        //2
        soIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getCode()));
        refCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getReference()));
        billToIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()));
        billToNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()));
        deliverToIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()));
        deliverToNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()));
        salesPersonIDCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()));
        salesPersonNmCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()));
        nettDtCol.setRowCellFactory(salesOrder -> new MFXTableRowCell<>(so -> so.getNett()));
        statusCol.setRowCellFactory(quotation -> new MFXTableRowCell<>(so -> so.getStatus()));

        //3
        ((MFXTableView<SalesOrder>) tblVw).getTableColumns().addAll(
                soIDCol,
                refCol,
                billToIDCol,
                billToNmCol,
                deliverToIDCol,
                deliverToNmCol,
                salesPersonIDCol,
                salesPersonNmCol,
                nettDtCol,
                statusCol
        );

        //4
        ((MFXTableView<SalesOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Sales Order ID", so -> so.getCode()),
                new StringFilter<>("Reference", so -> so.getReference()),
                new StringFilter<>("Bill To Customer ID", so -> so.getBillToCust() == null ? "" : so.getBillToCust().getCustID()),
                new StringFilter<>("Bill To Customer Name", so -> so.getBillToCust() == null ? "" : so.getBillToCust().getName()),
                new StringFilter<>("Deliver To ID", so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getCollectAddrID()),
                new StringFilter<>("Deliver To Collector Name", so -> so.getDeliverToCust() == null ? "" : so.getDeliverToCust().getPerson().getName()),
                new StringFilter<>("Sales Person ID", so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getStaffID()),
                new StringFilter<>("Sales Person Name", so -> so.getSalesPerson() == null ? "" : so.getSalesPerson().getName()),
                new DoubleFilter<>("Nett", so -> so.getNett().doubleValue()),
                new StringFilter<>("Status", so -> so.getStatus())
        );

        //5
        List<SalesOrder> salesOrders = SalesOrderService.getAllSalesOrders();

        //6
        ((MFXTableView<SalesOrder>) tblVw).setItems(FXCollections.observableList(salesOrders));
        //7
        ((MFXTableView<SalesOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<SalesOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    SalesOrder salesOrder = (((MFXTableView<SalesOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(salesOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(salesOrder);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/SalesOrder_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forTransferOrder() {
        // TO ID
        MFXTableColumn<TransferOrder> toIDCol = new MFXTableColumn<>("Transfer Order ID", true, Comparator.comparing(to -> to.getCode()));
        // PIC ID
        MFXTableColumn<TransferOrder> picIDCol = new MFXTableColumn<>("PIC ID", true, Comparator.comparing(to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()));
        // PIC Name
        MFXTableColumn<TransferOrder> picNmCol = new MFXTableColumn<>("PIC Name", true, Comparator.comparing(to -> to.getPIC() == null ? "" : to.getPIC().getName()));
        // Doc. Ref. ID
        MFXTableColumn<TransferOrder> docRefIDCol = new MFXTableColumn<>("Doc. Ref. ID", true, Comparator.comparing(to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()));
        // Status
        MFXTableColumn<TransferOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(to -> to.getStatus()));

        // TO ID
        toIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getCode()));
        // PIC ID
        picIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()));
        // PIC Name
        picNmCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getPIC() == null ? "" : to.getPIC().getName()));
        // Doc. Ref. ID
        docRefIDCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()));
        // Status
        statusCol.setRowCellFactory(transferOrder -> new MFXTableRowCell<>(to -> to.getStatus()));

        ((MFXTableView<TransferOrder>) tblVw).getTableColumns().addAll(
                toIDCol,
                picIDCol,
                picNmCol,
                statusCol
        );

        ((MFXTableView<TransferOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Transfer Order ID", to -> to.getCode()),
                new StringFilter<>("PIC ID", to -> to.getPIC() == null ? "" : to.getPIC().getStaffID()),
                new StringFilter<>("PIC Name", to -> to.getPIC() == null ? "" : to.getPIC().getName()),
                new StringFilter<>("Doc. Ref. ID", to -> to.getReqTypeRef() == null ? "" : to.getReqTypeRef() instanceof SalesOrder ? ((SalesOrder) to.getReqTypeRef()).getCode() : ((ReturnDeliveryNote) to.getReqTypeRef()).getCode()),
                new StringFilter<>("Status", to -> to.getStatus())
        );

        List<TransferOrder> transferOrders = TransferOrderService.getAllTO();

        //6
        ((MFXTableView<TransferOrder>) tblVw).setItems(FXCollections.observableList(transferOrders));
        //7
        ((MFXTableView<TransferOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<TransferOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    TransferOrder transferOrder = (((MFXTableView<TransferOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(transferOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(transferOrder);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/TransferOrder_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forPackingSlip() {
        // PS ID
        // TO ID
        // Status
        // Created Date

        // PS ID
        MFXTableColumn<PackingSlip> psCol = new MFXTableColumn<>("Packing Slip ID", true, Comparator.comparing(ps -> ps.getCode()));
        // TO ID
        MFXTableColumn<PackingSlip> toCol = new MFXTableColumn<>("Transfer Order ID", true, Comparator.comparing(ps -> ps.getTO() == null ? "" : ps.getTO().getCode()));
        // Status
        MFXTableColumn<PackingSlip> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(ps -> ps.getStatus()));
        // Created Date
        MFXTableColumn<PackingSlip> createdDtCol = new MFXTableColumn<>("Created Date", true, Comparator.comparing(ps -> ps.getCreatedDate()));

        // PS ID
        psCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getCode()));
        // TO ID
        toCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getTO() == null ? "" : ps.getTO().getCode()));
        // Status
        statusCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getStatus()));
        // Created Date
        createdDtCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(ps -> ps.getCreatedDate()));

        ((MFXTableView<PackingSlip>) tblVw).getTableColumns().addAll(
                psCol,
                toCol,
                statusCol,
                createdDtCol
        );

        ((MFXTableView<PackingSlip>) tblVw).getFilters().addAll(
                new StringFilter<>("Packing Slip ID", ps -> ps.getCode()),
                new StringFilter<>("Transfer Order ID", ps -> ps.getTO() == null ? "" : ps.getTO().getCode()),
                new StringFilter<>("Status", ps -> ps.getStatus()),
                new DateFilter<>("Created Date", ps -> ps.getCreatedDate())
        );

        List<PackingSlip> packingSlips = PackingSlipService.getAllPackingSlips();

        ((MFXTableView<PackingSlip>) tblVw).setItems(FXCollections.observableList(packingSlips));

        ((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    DeliveryOrder deliveryOrder = (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(deliveryOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(deliveryOrder);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/PackingSlip_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forDeliveryOrder() {
        //DO ID
        //SO ID
        //Delivery By
        //Delivery Date
        //Status

        //DO ID
        MFXTableColumn<DeliveryOrder> doIDCol = new MFXTableColumn<>("Delivery Order ID", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getCode()));
        //SO ID
        MFXTableColumn<DeliveryOrder> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()));
        //Delivery By
        MFXTableColumn<DeliveryOrder> deliveryByCol = new MFXTableColumn<>("Deliver By", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()));
        //Delivery Date
        MFXTableColumn<DeliveryOrder> deliveryDtCol = new MFXTableColumn<>("Delivery Date", true, Comparator.comparing(deliveryOrder -> deliveryOrder.getDeliveryDate()));
        //Status
        MFXTableColumn<DeliveryOrder> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(delveryOrder -> delveryOrder.getStatus()));

        //DO ID
        doIDCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getCode()));
        //SO ID
        soIDCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()));
        //Delivery By
        deliveryByCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()));
        //Delivery Date
        deliveryDtCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getDeliveryDate()));
        //Status
        statusCol.setRowCellFactory(dOrder -> new MFXTableRowCell<>(deliveryOrder -> deliveryOrder.getStatus()));

        ((MFXTableView<DeliveryOrder>) tblVw).getTableColumns().addAll(
                doIDCol,
                soIDCol,
                deliveryByCol,
                deliveryDtCol,
                statusCol
        );

        ((MFXTableView<DeliveryOrder>) tblVw).getFilters().addAll(
                new StringFilter<>("Delivery Order ID", deliveryOrder -> deliveryOrder.getCode()),
                new StringFilter<>("Sales Order ID", deliveryOrder -> deliveryOrder.getSo() == null ? "" : deliveryOrder.getSo().getCode()),
                new StringFilter<>("Deliver By", deliveryOrder -> deliveryOrder.getDeliveryBy() == null ? "" : deliveryOrder.getDeliveryBy().getStaffID()),
                new DateFilter<>("Delivery Date", deliveryOrder -> deliveryOrder.getDeliveryDate()),
                new StringFilter<>("Status", deliveryOrder -> deliveryOrder.getStatus())
        );

        List<DeliveryOrder> deliveryOrders = DeliveryOrderService.getAllDOs();

        ((MFXTableView<DeliveryOrder>) tblVw).setItems(FXCollections.observableList(deliveryOrders));

        ((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    DeliveryOrder deliveryOrder = (((MFXTableView<DeliveryOrder>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(deliveryOrder.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(deliveryOrder);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/DeliveryOrder_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    private void forReturnDeliveryNote() {
        // RDN ID
        MFXTableColumn<ReturnDeliveryNote> rdnIDCol = new MFXTableColumn<>("RDN ID", true, Comparator.comparing(rdn -> rdn.getCode()));
        // So Ref.
        MFXTableColumn<ReturnDeliveryNote> soRefCol = new MFXTableColumn<>("So Ref.", true, Comparator.comparing(rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()));
        // Collect Back From Location Name
        MFXTableColumn<ReturnDeliveryNote> cllctBckFrLocationCol = new MFXTableColumn<>("Collect Back From Location", true, Comparator.comparing(rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()));
        // Status
        MFXTableColumn<ReturnDeliveryNote> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(rdn -> rdn.getStatus()));

        // RDN ID
        rdnIDCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getCode()));
        // So Ref.
        soRefCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()));
        // Collect Back From Location Name
        cllctBckFrLocationCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()));
        // Status
        statusCol.setRowCellFactory(returnDeliveryNote -> new MFXTableRowCell<>(rdn -> rdn.getStatus()));

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getTableColumns().addAll(
                rdnIDCol,
                soRefCol,
                cllctBckFrLocationCol,
                statusCol
        );

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getFilters().addAll(
                new StringFilter<>("RDN ID", rdn -> rdn.getCode()),
                new StringFilter<>("So Ref.", rdn -> rdn.getSO() == null ? "" : rdn.getSO().getCode()),
                new StringFilter<>("Collect Back From Location", rdn -> rdn.getCollBckFr() == null ? "" : rdn.getCollBckFr().getAddr().getLocationName()),
                new StringFilter<>("Status", rdn -> rdn.getStatus())
        );

        List<ReturnDeliveryNote> returnDeliveryNotes = RDNService.getAllRDN();

        //6
        ((MFXTableView<ReturnDeliveryNote>) tblVw).setItems(FXCollections.observableList(returnDeliveryNotes));
        //7
        ((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    ReturnDeliveryNote returnDeliveryNote = (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(returnDeliveryNote.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(returnDeliveryNote);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/ReturnDeliveryNote_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

    }

    private void forInvoice() {

        // INV_ID
        MFXTableColumn<Invoice> invIDCol = new MFXTableColumn<>("Invoice ID", true, Comparator.comparing(invoice -> invoice.getCode()));
        // SO ID
        MFXTableColumn<Invoice> soIDCol = new MFXTableColumn<>("Sales Order ID", true, Comparator.comparing(invoice -> invoice.getSO() == null ? "" : invoice.getSO().getCode()));
        // Total Payable
        MFXTableColumn<Invoice> ttlPybleCol = new MFXTableColumn<>("Total Payable", true, Comparator.comparing(invoice -> invoice.getTtlPayable()));
        // Status
        MFXTableColumn<Invoice> statusCol = new MFXTableColumn<>("Status", true, Comparator.comparing(invoice -> invoice.getStatus()));
        //Created Date
        MFXTableColumn<Invoice> createdDtCol = new MFXTableColumn<>("Created Date", true, Comparator.comparing(invoice -> invoice.getCreatedDate()));

        // INV_ID
        invIDCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getCode()));
        // SO ID
        soIDCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getSO() == null ? "" : inv.getSO().getCode()));
        // Total Payable
        ttlPybleCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getTtlPayable()));
        // Status
        statusCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getStatus()));
        //Created Date
        createdDtCol.setRowCellFactory(invoice -> new MFXTableRowCell<>(inv -> inv.getCreatedDate()));

        ((MFXTableView<Invoice>) tblVw).getTableColumns().addAll(
                invIDCol,
                soIDCol,
                ttlPybleCol,
                statusCol,
                createdDtCol
        );

        ((MFXTableView<Invoice>) tblVw).getFilters().addAll(
                new StringFilter<>("Invoice ID", invoice -> invoice.getCode()),
                new StringFilter<>("Sales Order ID", invoice -> invoice.getSO() == null ? "" : invoice.getSO().getCode()),
                new DoubleFilter<>("Total Payable", invoice -> invoice.getTtlPayable().doubleValue()),
                new StringFilter<>("Status", invoice -> invoice.getStatus()),
                new DateFilter<>("Created Date", invoice -> invoice.getCreatedDate())
        );

        List<Invoice> invoices = InvoiceService.getAllInvoice();

        //6
        ((MFXTableView<Invoice>) tblVw).setItems(FXCollections.observableList(invoices));
        //7
        ((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    ReturnDeliveryNote returnDeliveryNote = (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(returnDeliveryNote.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(returnDeliveryNote);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/Invoice_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

    }

    private void forPayment() {
        //RCPT ID

        //INV ID
        //Paid Amount
        //Created Date
        //RCPT ID
        MFXTableColumn<Receipt> rcptIDCol = new MFXTableColumn<>("Receipt ID", true, Comparator.comparing(receipt -> receipt.getCode()));
        //INV ID
        MFXTableColumn<Receipt> invIDCol = new MFXTableColumn<>("Invoice ID", true, Comparator.comparing(receipt -> receipt.getINV() == null ? "" : receipt.getINV().getCode()));
        //Paid Amount
        MFXTableColumn<Receipt> paidAmtCol = new MFXTableColumn<>("Paid Amount", true, Comparator.comparing(receipt -> receipt.getPaidAmt()));
        //Created Date
        MFXTableColumn<Receipt> createdDtCol = new MFXTableColumn<>("Created Date", true, Comparator.comparing(receipt -> receipt.getCreatedDate()));

        //RCPT ID
        rcptIDCol.setRowCellFactory(rcpt -> new MFXTableRowCell<>(receipt -> receipt.getCode()));
        //INV ID
        invIDCol.setRowCellFactory(rcpt -> new MFXTableRowCell<>(receipt -> receipt.getINV() == null ? "" : receipt.getINV().getCode()));
        //Paid Amount
        paidAmtCol.setRowCellFactory(rcpt -> new MFXTableRowCell<>(receipt -> receipt.getPaidAmt()));
        //Created Date
        createdDtCol.setRowCellFactory(rcpt -> new MFXTableRowCell<>(receipt -> receipt.getCreatedDate()));

        ((MFXTableView<Receipt>) tblVw).getTableColumns().addAll(
                rcptIDCol,
                invIDCol,
                paidAmtCol,
                createdDtCol
        );

        ((MFXTableView<Receipt>) tblVw).getFilters().addAll(
                new StringFilter<>("Receipt ID", receipt -> receipt.getCode()),
                new StringFilter<>("Invoice ID", receipt -> receipt.getINV() == null ? "" : receipt.getINV().getCode()),
                new DoubleFilter<>("Paid Amount", receipt -> receipt.getPaidAmt().doubleValue()),
                new DateFilter<>("Created Date", receipt -> receipt.getCreatedDate())
        );

        List<Receipt> receipts = ReceiptService.getAllReceipt();

        ((MFXTableView<Receipt>) tblVw).setItems(FXCollections.observableList(receipts));

        ((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().selectionProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().size() != 0) {
                    ReturnDeliveryNote returnDeliveryNote = (((MFXTableView<ReturnDeliveryNote>) tblVw).getSelectionModel().getSelectedValues().get(0));
                    rowSelected.add(returnDeliveryNote.getCode());

                    if (rowSelected.size() == 2) {
                        if (rowSelected.get(0).equals(rowSelected.get(1))) {
                            BasicObjs passObjs = new BasicObjs();
                            passObjs.setObj(returnDeliveryNote);
                            passObjs.setCrud(BasicObjs.read);
                            switchScene("View/Payment_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });
    }

    @FXML
    private void goBackPrevious(MouseEvent event) {
        switchScene(passObj.getFxmlPaths().getLast().toString(), new BasicObjs(), BasicObjs.back);
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
        try {
            // Step 4
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlPath)); // Example: "View/HomePage_UI.fxml"
            // Step 5
            stage.setUserData(sendData(passObj, direction));
            // Step 6
            Scene scene = new Scene(root);
            stage.setScene(scene);
            // Step 7
            stage.show();

        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    @Override
    public BasicObjs sendData(BasicObjs passObj, String direction) {
        switch (direction) {
            //send data to after scene
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/EntityOverview_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());

        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnBack.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();

            switch (passObj.getPassDirection()) {
                //receive data from after scene;
                case BasicObjs.back:
                    if (passObj.getFxmlPaths().getLength() != 0) {
                        passObj.getFxmlPaths().delLast();
                    }
                    break;
            }
        } else {
            passObj = new BasicObjs();
        }
    }

    @Override
    public void inputValidation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean clearAllFieldsValue() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
