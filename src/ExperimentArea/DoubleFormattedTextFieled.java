/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExperimentArea;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.function.UnaryOperator;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class DoubleFormattedTextFieled extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("test");
        HBox root = new HBox();

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                    }
                }

                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        TextField tfDouble = new TextField();
        tfDouble.setTextFormatter(new TextFormatter<>(filter));

        root.getChildren().add(tfDouble);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
