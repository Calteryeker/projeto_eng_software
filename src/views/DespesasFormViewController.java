package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.views.util.Alerts;
import model.Categoria;
import model.Despesa;
import model.Usuario;
import views.listeners.IDataChangeListener;

public class DespesasFormViewController implements Initializable, IDataChangeListener {

  @FXML private Button confirmProductBt;
  @FXML private Button backBt;
  @FXML private Button addCategorieBt;
  @FXML private TextField nameProductField;
  @FXML private TextField descriptionProductField;
  @FXML private TextField valueUnitProductField;
  @FXML private ComboBox<Categoria> categoriesCB;
  private Despesa product;
  private Usuario usuario;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    usuario = ControladorLogin.getInstance().getLoggedUser();

    ObservableList<Categoria> categoriasOS = FXCollections.observableArrayList(usuario.getCategorias());
    categoriesCB.setItems(categoriasOS);
  }

  public void onConfirmProductBt(ActionEvent event) {

    if (product == null) {
      try {

        if (nameProductField.getText().trim().isEmpty()
            || descriptionProductField.getText().trim().isEmpty()
            || valueUnitProductField.getText().trim().isEmpty()
            || categoriesCB.getSelectionModel().getSelectedItem() == null
            || categoriesCB.getSelectionModel().getSelectedItem().getNome().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText().replace(',', '.')) > 0) {

            Categoria categoria = categoriesCB.getSelectionModel().getSelectedItem();

            ControladorDespesa.getInstance(usuario.getRepositorioDespesa())
                .criarDespesa(
                    nameProductField.getText(),
                    Double.parseDouble(descriptionProductField.getText().replace(',', '.')),
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
            || categoriesCB.getSelectionModel().getSelectedItem().getNome().isEmpty()) {
          Alerts.showAlert("Error", null, "The fields must be filled", AlertType.ERROR);
        } else {

          if (Double.parseDouble(descriptionProductField.getText().replace(',', '.')) > 0) {

            Categoria categoria = new Categoria(categoriesCB.getSelectionModel().getSelectedItem().getNome());

            ControladorDespesa.getInstance(usuario.getRepositorioDespesa())
                .alterarDespesa(
                    nameProductField.getText(),
                    product.getOrdem(),
                    Double.parseDouble(descriptionProductField.getText().replace(',', '.')),
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


  public void onAddCategorieBtAction(ActionEvent event)
  {

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CategoryFormView.fxml"));
      Parent newPage = loader.load();

      CategoryFormViewController controller = loader.getController();
      controller.subscribeDataChangeListener(this);

      Stage stage = new Stage();
      stage.setTitle("Criar categoria");
      stage.setScene(new Scene(newPage));
      stage.show();
    }
    catch (Exception e) {
      System.out.println("Error");
    }
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

    String valor = "" + product.getValor();
    descriptionProductField.setText(valor);

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String data = product.getData_criacao().format(format);
    valueUnitProductField.setText(String.valueOf(data));

    usuario = ControladorLogin.getInstance().getLoggedUser();
    ObservableList<Categoria> categoriasOS = FXCollections.observableArrayList(usuario.getCategorias());
    categoriesCB.setItems(categoriasOS);

    categoriesCB.setValue(product.getCategoria());
  }

  public void setProduct(Despesa product) {
    this.product = product;
  }

  @Override
  public void onDataChanged() {
    ObservableList<Categoria> categoriasOS = FXCollections.observableArrayList(usuario.getCategorias());
    categoriesCB.setItems(categoriasOS);

    if (product != null) {
      categoriesCB.setValue(product.getCategoria());
    }
  }
}
