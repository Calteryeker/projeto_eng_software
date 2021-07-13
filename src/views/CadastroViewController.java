package views;

import controllers.ControladorDadosPersistentes;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
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

public class CadastroViewController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private TextField nomeField;
  @FXML private PasswordField passwordField;
  @FXML private Button cadastroBt;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {}

  @FXML
  public void onCadastroBtAction(ActionEvent event) {
    if (usernameField.getText().isBlank()
        || passwordField.getText().isBlank()
        || nomeField.getText().isBlank()) {

      Alerts.showAlert(
          "Não foi possivel cadastrar", null, "Campos precisam ser preenchidos", AlertType.ERROR);
    } else {

      try {
        ControladorDadosPersistentes.getInstance()
            .cadastrarUsuario(
                nomeField.getText(), usernameField.getText(), passwordField.getText());

        Alerts.showAlert("Sucesso", null, "Cadastro concluido", AlertType.INFORMATION);

        try {
          Parent newPage = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
          passwordField.getScene().setRoot(newPage);
        } catch (Exception e) {
          System.out.println("Error");
        }

      } catch (UsuarioJaCadastradoException e) {
        Alerts.showAlert("Não foi possivel cadastrar", null, e.getMessage(), AlertType.INFORMATION);
      } catch (DadosNaoPreenchidosException e) {
        e.printStackTrace();
      }
    }
  }
}
