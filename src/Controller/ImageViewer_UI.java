/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Entity.Staff;
import PassObjs.BasicObjs;
import Service.GeneralRulesService;
import Service.ImageViewerService;
import Utils.ImageUtils;
import adt.DoublyLinkedList;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCircleToggleNode;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 *
 * @author Tee Zhuo Xuan
 */
public class ImageViewer_UI implements Initializable, BasicCONTRFunc {

    @FXML
    private MFXCircleToggleNode btnNext;
    @FXML
    private MFXCircleToggleNode btnPrevious;
    @FXML
    private MFXButton btnRemove;
    @FXML
    private MFXButton btnUploadImg;

    private BasicObjs passObj;

    private ImageViewerService imageViewerService = new ImageViewerService();

    private DoublyLinkedList<String> images = new DoublyLinkedList<>();
    @FXML
    private ImageView imgDocs;
    @FXML
    private MFXButton btnDone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                receiveData();
                autoClose();
                String imgStrs = (String) passObj.getObj();
                images = ImageUtils.splitImgStr(imgStrs);
                imageViewerService.setImages(images);

                try {
                    imgDocs.setImage(imageViewerService.getFirst(images));
                } catch (IOException ex) {
                    Logger.getLogger(ImageViewer_UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void autoClose() {
        Duration delay1 = Duration.seconds(GeneralRulesService.getSessionTimeOut());
        PauseTransition transitionAlert = new PauseTransition(delay1);
        this.passObj.setLoginStaff(new Staff());
        transitionAlert.setOnFinished(evt -> switchScene("View/Login_UI.fxml", passObj, BasicObjs.back));
        transitionAlert.setCycleCount(1);

        this.btnDone.getScene().addEventFilter(InputEvent.ANY, evt -> transitionAlert.playFromStart());

    }

    @FXML
    private void goBackPrevious(MouseEvent event) throws IOException {
        this.imgDocs.setImage(imageViewerService.getPrev(images));
    }

    @FXML
    private void goForward(MouseEvent event) throws IOException {
        this.imgDocs.setImage(imageViewerService.getNext(images));
    }

    @FXML
    private void removeCurrentImage(MouseEvent event) throws IOException {
        imageViewerService.delCurrent();
        this.imgDocs.setImage(imageViewerService.getCurrent(images));
    }

    @FXML
    private void uploadImage(MouseEvent event) {
        if (event.isPrimaryButtonDown() == true) {

            try {
                Stage stage = (Stage) btnUploadImg.getScene().getWindow();

                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files (*.png)", "*.png");

                // create a File chooser
                FileChooser fil_chooser = new FileChooser();
                fil_chooser.getExtensionFilters().add(filter);

                // get the file selected
                File file = fil_chooser.showOpenDialog(stage);

                if (file != null) {

                    BufferedImage bi = ImageIO.read(file);

                    imageViewerService.addLast(ImageUtils.byteToEncodedStr(ImageUtils.bufferedImgToByte(bi)));
                    Image img = SwingFXUtils.toFXImage(bi, null);
                    this.imgDocs.setImage(imageViewerService.getLast(images));

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void saveImages(MouseEvent event) {
        Stage stage = (Stage) btnDone.getScene().getWindow();
        BasicObjs passObj = new BasicObjs();
        passObj.setObj(ImageUtils.concatImgStr(images));
        System.out.println("saveImages - " + passObj.getObj().toString());
        stage.setUserData(passObj);
        stage.close();
    }

    @Override
    public void inputValidation() {
        // Unused
        return;
    }

    @Override
    public boolean clearAllFieldsValue() {
        // Unused
        return true;
    }

    @Override
    public ButtonType alertDialog(Alert.AlertType alertType, String title, String headerTxt, String contentTxt) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        alert.setContentText(contentTxt);

        alert.showAndWait();
        return alert.getResult();
    }

    @Override
    public void switchScene(String fxmlPath, BasicObjs passObj, String direction) {
        // Step 3
        Stage stage = (Stage) this.btnDone.getScene().getWindow();
        //stage.close();
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
            case BasicObjs.forward:
                passObj.getFxmlPaths().addLast("View/ImageViewer_UI.fxml");
                break;
        }
        passObj.setPassDirection(direction);
        passObj.setLoginStaff(this.passObj.getLoginStaff());
        return passObj;
    }

    @Override
    public void receiveData() {
        // Step 1
        Stage stage = (Stage) btnDone.getScene().getWindow();
        // Step 2
        if (stage.getUserData() != null) {
            passObj = (BasicObjs) stage.getUserData();
        } else {
            passObj = new BasicObjs();
        }
    }

    @Override
    public void quitWindow(String title, String headerTxt, String contentTxt) {
//Unused
    }

}
