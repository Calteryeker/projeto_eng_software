package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import controllers.ControladorMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Categoria;
import model.Meta;
import model.Usuario;

public class MetaFormViewController implements Initializable {

  @FXML private Button confirmProductBt;
  @FXML private Button backBt;
  @FXML private TextField nameProductField;
  @FXML private TextField descriptionProductField;
  @FXML private TextField valueUnitProductField;
  private Meta product;
  private Usuario usuario;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    usuario = ControladorLogin.getInstance().getLoggedUser();

    ObservableList<Categoria> categoriasOS =
        FXCollections.observableArrayList(usuario.getCategorias());
  }

  public void onConfirmProductBt(ActionEvent event) {

    if (product.getValor() == 0) {
      try {

        if (descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()) {
          main.java.views.util.Alerts.showAlert(
              "Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText().replace(',', '.')) > 0) {

            ControladorMeta.getInstance(usuario.getRepositorioMeta())
                .criarMeta(
                    Double.parseDouble(descriptionProductField.getText().replace(',', '.')),
                    nameProductField.getText(),
                    LocalDate.parse(
                        valueUnitProductField.getText(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

            main.java.views.util.Alerts.showAlert(
                "Sucesso", null, "Meta Inserida", AlertType.INFORMATION);
            goToProductList();
          } else {
            main.java.views.util.Alerts.showAlert(
                "Erro", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }
      } catch (UsuarioNaoEncontradoException
          | MetaJaCadastradaException
          | DadosNaoPreenchidosException e) {
        main.java.views.util.Alerts.showAlert("Erro", null, e.getMessage(), AlertType.ERROR);
      }
    } else {

      try {

        if (descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()) {
          main.java.views.util.Alerts.showAlert(
              "Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText().replace(',', '.')) > 0) {

            ControladorMeta.getInstance(usuario.getRepositorioMeta())
                .alterarMeta(
                    Double.parseDouble(descriptionProductField.getText().replace(',', '.')),
                    nameProductField.getText(),
                    LocalDate.parse(
                        valueUnitProductField.getText(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

            main.java.views.util.Alerts.showAlert(
                "Sucesso", null, "Meta Atualizada", AlertType.INFORMATION);
            goToProductList();
          } else {
            main.java.views.util.Alerts.showAlert(
                "Erro", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }
      } catch (UsuarioNaoEncontradoException
          | MetaNaoEncontradaException
          | DadosNaoPreenchidosException e) {
        main.java.views.util.Alerts.showAlert("Erro", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }

  public void onBackBtAction(ActionEvent action) {
    goToProductList();
  }

  public void goToProductList() {

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/MetasListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void fillFields() {

    if (product == null) {
      throw new IllegalStateException("Product was null.");
    }

    if (product.getValor() == 0) {
      String valor = "" + product.getValor();
      descriptionProductField.setText(valor);

      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      String data = product.getData_criacao().format(format);
      valueUnitProductField.setText(String.valueOf(data));
    } else {
      String valor = "" + product.getValor();
      descriptionProductField.setText(valor);

      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      String data = product.getData_criacao().format(format);
      valueUnitProductField.setText(String.valueOf(data));

      nameProductField.setText(product.getDescricao());
    }
  }

  public void setProduct(Meta product) {
    this.product = product;
  }
}
