package views;

import controllers.ControladorLogin;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Usuario;

public class UsuarioMainPageController implements Initializable {

  @FXML private Button backBt;
  @FXML private Button startShoppingBt;
  @FXML private Button shoppingHistoryBt;
  @FXML private Button cureServiceBt;
  @FXML private Text welcomeTxt;

  private Usuario usuarioLogado;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    usuarioLogado = ControladorLogin.getInstance().getLoggedUser();

    if (usuarioLogado != null) {
      initializeWelcomeTxt();
    }
  }

  // UI events

  public void onBackBtAction(ActionEvent event) {

    ControladorLogin.getInstance().logout();

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void onCureServiceBtAction(ActionEvent event) {

    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DespesasListView.fxml"));
      Parent newPage = loader.load();
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void onStartShoppingBtAction(ActionEvent event) {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DespesasListView.fxml"));
      Parent newPage = loader.load();
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void onShoppingHistoryBtAction(ActionEvent event) {
    try {

      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/main/java/views/OrderReportView.fxml"));
      Parent newPage = loader.load();
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  // Initialization methods

  private void initializeWelcomeTxt() {

    String newText = welcomeTxt.getText();

    newText = newText.replace("{name}", usuarioLogado.getNome());

    welcomeTxt.setText(newText);
  }
}
