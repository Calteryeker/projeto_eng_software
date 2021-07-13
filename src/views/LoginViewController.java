package views;

import controllers.ControladorLogin;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.views.util.Alerts;
import model.Usuario;

public class LoginViewController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button loginBt;
  @FXML private Button cadastroBt;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @FXML
  public void onLoginBtAction(ActionEvent event) {
    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {

      Alerts.showAlert(
          "Não foi possivel logar",
          null,
          "Campos usuario e senha precisam ser preenchidos",
          AlertType.ERROR);
    } else {

      try {
        boolean logged =
            ControladorLogin.getInstance().login(usernameField.getText(), passwordField.getText());

        if (logged) {

          Usuario usuarioLogado = ControladorLogin.getInstance().getLoggedUser();

          logar();
        }
      } catch (UsuarioNaoEncontradoException e) {
        Alerts.showAlert("Não foi possivel logar", null, e.getMessage(), AlertType.INFORMATION);
      } catch (SenhaIncorretaException e) {
        Alerts.showAlert("Não foi possivel logar", null, e.getMessage(), AlertType.INFORMATION);
      }
    }
  }

  @FXML
  public void onCadastroBtAction(ActionEvent actionEvent) {
    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/Cadastro.fxml"));
      passwordField.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  private void logar() {
    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/UsuarioMainPage.fxml"));
      passwordField.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
