package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.views.util.Alerts;
import model.Categoria;
import model.Despesa;
import model.Usuario;

public class DespesasFormViewController implements Initializable {

  @FXML private Button confirmProductBt;
  @FXML private Button backBt;
  @FXML private TextField nameProductField;
  @FXML private TextField descriptionProductField;
  @FXML private TextField valueUnitProductField;
  @FXML private TextField valueUnitProductField2;
  private Despesa product;
  private Usuario usuario;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    usuario = ControladorLogin.getInstance().getLoggedUser();
  }

  public void onConfirmProductBt(ActionEvent event) {

    if (product == null) {
      try {

        if (nameProductField.getText().trim().isEmpty()
            || descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()
            || valueUnitProductField2.getText().trim().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText()) > 0) {

            Categoria categoria = new Categoria(valueUnitProductField2.getText());

            ControladorDespesa.getInstance(usuario.getRepositorioDespesa())
                .criarDespesa(
                    nameProductField.getText(),
                    Double.parseDouble(descriptionProductField.getText()),
                    LocalDate.parse(
                        valueUnitProductField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    categoria);

            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

            Alerts.showAlert("Sucesso", null, "Despesa Inserida", AlertType.INFORMATION);
            goToProductList();
          } else {
            Alerts.showAlert("Erro", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }
      } catch (DadosNaoPreenchidosException | UsuarioNaoEncontradoException e) {
        Alerts.showAlert("Erro", null, e.getMessage(), AlertType.ERROR);
      }
    } else {

      try {

        if (nameProductField.getText().trim().isEmpty()
            || descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()
            || valueUnitProductField2.getText().trim().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText()) > 0) {

            Categoria categoria = new Categoria(valueUnitProductField2.getText());

            ControladorDespesa.getInstance(usuario.getRepositorioDespesa())
                .alterarDespesa(
                    nameProductField.getText(),
                    product.getOrdem(),
                    Double.parseDouble(descriptionProductField.getText()),
                    LocalDate.parse(
                        valueUnitProductField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    categoria);

            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

            Alerts.showAlert("Sucesso", null, "Despesa Atualizada", AlertType.INFORMATION);
            goToProductList();
          } else {
            Alerts.showAlert("Erro", null, "Price must be greater than zero", AlertType.ERROR);
          }
        }
      } catch (DadosNaoPreenchidosException
          | UsuarioNaoEncontradoException
          | DespesaNaoEncontradaException e) {
        Alerts.showAlert("Erro", null, e.getMessage(), AlertType.ERROR);
      }
    }
  }

  public void onBackBtAction(ActionEvent action) {
    goToProductList();
  }

  public void goToProductList() {

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/DespesasListView.fxml"));
      backBt.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public void fillFields() {

    if (product == null) {
      throw new IllegalStateException("Product was null.");
    }

    nameProductField.setText(product.getNome());

    DecimalFormat df = new DecimalFormat("0");
    String valor = df.format(product.getValor());
    descriptionProductField.setText(valor);

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String data = product.getData_criacao().format(format);
    valueUnitProductField.setText(String.valueOf(data));

    valueUnitProductField2.setText(product.getCategoria().getNome());
  }

  public void setProduct(Despesa product) {
    this.product = product;
  }
}
