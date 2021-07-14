package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import main.java.views.util.Alerts;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DateFilterViewController implements Initializable {

    @FXML private DatePicker initialDateDp;
    @FXML private DatePicker finalDateDp;
    @FXML private Button filterBt;
    @FXML private Button cancelBt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onFilterBtAction(ActionEvent event) {

        if (initialDateDp.getValue() == null || finalDateDp.getValue() == null) {
            Alerts.showAlert("Erro", null, "Escolha as datas!", Alert.AlertType.ERROR);
        } else {
            ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
        }
    }

    public void onCancelBtAction(ActionEvent event) {
        setInitialDate(null);
        setFinalDate(null);
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    public LocalDate getInitialDate() {
        return initialDateDp.getValue();
    }

    public void setInitialDate(LocalDate initialDate) {
        initialDateDp.setValue(initialDate);
    }

    public void setFinalDate(LocalDate finalDate) {
        finalDateDp.setValue(finalDate);
    }

    public LocalDate getFinalDate() {
        return finalDateDp.getValue();
    }
}
