/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Entity.Customer;
import Entity.CustomerInquiry;
import Entity.DeliveryOrder;
import Entity.Invoice;
import Entity.Quotation;
import Entity.Receipt;
import Entity.ReturnDeliveryNote;
import Entity.SalesOrder;
import Entity.Staff;
import Entity.TransferOrder;
import PassObjs.BasicObjs;
import Service.CustomerInquiryService;
import Service.CustomerService;
import Service.QuotationService;
import Service.StaffService;
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
                new DateFilter<>("Required Delivery Date", quot -> quot.getRequiredDeliveryDate()),
                new DoubleFilter<>("Gross", quot -> quot.getGross().doubleValue()),
                new DoubleFilter<>("Discount", quot -> quot.getDiscount().doubleValue()),
                new DoubleFilter<>("Sub Total", quot -> quot.getSubTotal().doubleValue()),
                new DoubleFilter<>("Nett", quot -> quot.getNett().doubleValue())
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
                            switchScene("View/Staff_UI.fxml", passObjs, BasicObjs.forward);
                        }
                        rowSelected.clear();
                    }
                }
            }
        });

        System.out.println("done go through function");
    }

    private void forSalesOrder() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void forTransferOrder() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void forDeliveryOrder() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void forReturnDeliveryNote() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void forInvoice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void forPayment() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
