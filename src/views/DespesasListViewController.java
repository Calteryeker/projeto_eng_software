package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import controllers.ControladorLogin;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.views.util.Alerts;
import model.Despesa;
import model.Usuario;

public class DespesasListViewController implements Initializable {

  @FXML private Button buttonNewProduct;
  @FXML private Button buttonUpdate;
  @FXML private Button buttonDelete;
  @FXML private Button buttonBack;
  @FXML private Button saveCsvBt;
  @FXML private Button dateFilterBt;
  @FXML private TableView<Despesa> tableViewProduct;
  @FXML private TableColumn<Despesa, String> tableColumnNameProduct;
  @FXML private TableColumn<Despesa, Despesa> tableColumnDescription;
  @FXML private TableColumn<Despesa, Despesa> tableColumnPrice;
  @FXML private TableColumn<Despesa, String> tableColumnPrice2;
  private List<Despesa> productList;
  private ObservableList<Despesa> ProductObservableList;
  private LocalDate initialDate;
  private LocalDate finalDate;
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

    tableColumnPrice.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    tableColumnPrice.setCellFactory(param -> new TableCell<>() {

      final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      @Override
      protected void updateItem(Despesa obj, boolean empty) {
        super.updateItem(obj, empty);

        if (obj == null) {
          setGraphic(null);
          return;
        }

        setGraphic(new Text(formatter.format(obj.getData_criacao())));
      }
    });


    tableColumnDescription.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    tableColumnDescription.setCellFactory(param -> new TableCell<>() {

      @Override
      protected void updateItem(Despesa obj, boolean empty) {
        super.updateItem(obj, empty);

        if (obj == null) {
          setGraphic(null);
          return;
        }

        DecimalFormat df = new DecimalFormat("R$ 0.00");
        setGraphic(new Text(df.format(obj.getValor())));
      }
    });
  }

  private void updateTableView() {

    productList = ControladorDespesa.getRepositorioDespesa().getDespesas();

    if (initialDate != null && finalDate != null) {
      productList = productList.stream()
              .filter(m -> m.getData_criacao().isAfter(initialDate.minusDays(1)) && m.getData_criacao().isBefore(finalDate))
              .collect(Collectors.toList());
    }

    ProductObservableList = FXCollections.observableArrayList(productList);
    tableViewProduct.setItems(ProductObservableList);

    tableViewProduct.refresh();
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
  public void onDateFilterBtAction(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DateFilterView.fxml"));
      Parent newPage = loader.load();

      DateFilterViewController controller = loader.getController();

      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setResizable(false);
      stage.setTitle("Escolha as datas");
      stage.setScene(new Scene(newPage));

      controller.setInitialDate(initialDate);
      controller.setFinalDate(finalDate);

      stage.showAndWait();

      initialDate = controller.getInitialDate();
      finalDate = controller.getFinalDate();

      updateTableView();
    } catch (Exception e) {
      System.out.println("Error");
    }
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
