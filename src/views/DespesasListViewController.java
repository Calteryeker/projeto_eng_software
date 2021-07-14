package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import controllers.ControladorLogin;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.views.util.Alerts;
import model.Despesa;
import model.Usuario;

public class DespesasListViewController implements Initializable {

  @FXML private Button buttonNewProduct;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private Button buttonBack;
  @FXML private Button saveCsvBt;
  @FXML private TableView<Despesa> tableViewProduct;
  @FXML private TableColumn<Despesa, String> tableColumnNameProduct;
  @FXML private TableColumn<Despesa, String> tableColumnDescription;
  @FXML private TableColumn<Despesa, String> tableColumnPrice;
  @FXML private TableColumn<Despesa, String> tableColumnPrice2;
  private List<Despesa> productList;
  private ObservableList<Despesa> ProductObservableList;
  private Usuario usuarioLogado;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    initializeNodes();
    usuarioLogado = ControladorLogin.getInstance().getLoggedUser();
    ControladorDespesa.getInstance(usuarioLogado.getRepositorioDespesa());
    updateTableView();
  }

  private void initializeNodes() {

    tableColumnNameProduct.setCellValueFactory(new PropertyValueFactory<>("nome"));
    tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("valor"));
    tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
    tableColumnPrice2.setCellValueFactory(new PropertyValueFactory<>("categoria"));
  }

  private void updateTableView() {

    productList = ControladorDespesa.getRepositorioDespesa().getDespesas();

    ProductObservableList = FXCollections.observableArrayList(productList);
    tableViewProduct.setItems(ProductObservableList);
  }

  @FXML
  public void onButtonNewProductAction(ActionEvent event) {

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/DespesaFormView.fxml"));
      buttonNewProduct.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  @FXML
  public void onButtonUpdateAction() {

    try {

      if (!tableViewProduct.getSelectionModel().isEmpty()) {

        try {

          FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DespesaFormView.fxml"));
          Parent newPage = loader.load();
          DespesasFormViewController controller = loader.getController();
          controller.setProduct(tableViewProduct.getSelectionModel().getSelectedItem());
          controller.fillFields();

          buttonNewProduct.getScene().setRoot(newPage);
        } catch (Exception e) {
          System.out.println("Error");
        }
      } else {
        throw new IOException("Select the field to be updated");
      }
    } catch (IOException e) {
      Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
    }
  }

  @FXML
  public void onButtonDeleteAction() {

    try {

      if (tableViewProduct.getSelectionModel().getSelectedItem().getOrdem() != 0) {

        Optional<ButtonType> result =
            Alerts.showConfirmation(
                "Confirmation", "Você tem certeza que deseja remover a Despesa selecionada?");
        if (result.isPresent() && result.get() == ButtonType.OK) {

          ControladorDespesa.getInstance(usuarioLogado.getRepositorioDespesa())
              .removerDespesa(tableViewProduct.getSelectionModel().getSelectedItem().getOrdem());
          ControladorDadosPersistentes.getInstance().atualizarUsuario(usuarioLogado);
          updateTableView();
        }
      }
    } catch (Exception e) {
      Alerts.showAlert("Error", null, "Select the field to be deleted", AlertType.ERROR);
    }
  }

  @FXML
  public void onSaveCsvBtAction(ActionEvent event) {
    ControladorDespesa.getInstance(usuarioLogado.getRepositorioDespesa()).gerarGrafico("gráfico");
    Alerts.showAlert("Sucesso", null, "O arquivo foi salvo com sucesso!", AlertType.INFORMATION);
  }

  @FXML
  public void onButtonBackAction() {

    try {

      Parent newPage = FXMLLoader.load(getClass().getResource("/views/UsuarioMainPage.fxml"));
      buttonBack.getScene().setRoot(newPage);
    } catch (Exception e) {
      System.out.println("Error");
    }
  }
}
