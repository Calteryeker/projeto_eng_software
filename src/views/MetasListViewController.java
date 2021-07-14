package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import controllers.ControladorLogin;
import dao.impl.exceptions.MetaNaoEncontradaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Meta;
import model.Usuario;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MetasListViewController implements Initializable {

    @FXML private Button buttonBack;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonDelete;
    @FXML private TableView<Meta> tableViewMetas;
    @FXML private TableColumn<Meta, LocalDate> tableColumnCreationDate;
    @FXML private TableColumn<Meta, LocalDate> tableColumnValue;
    @FXML private TableColumn<Meta, LocalDate> tableColumnDescription;

    private Usuario usuarioLogado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeNodes();

        usuarioLogado = ControladorLogin.getInstance().getLoggedUser();
        ControladorDespesa.getInstance(usuarioLogado.getRepositorioDespesa());
        updateTableView();
    }

    public void onButtonBackAction(ActionEvent event) {
        try {

            Parent newPage = FXMLLoader.load(getClass().getResource("/views/UsuarioMainPage.fxml"));
            buttonBack.getScene().setRoot(newPage);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void onButtonDeleteAction(ActionEvent event) {
        Meta m = tableViewMetas.getSelectionModel().getSelectedItem();

        if (m == null ) {
            main.java.views.util.Alerts.showAlert("Erro", null, "Selecione a meta para ser deletada!", Alert.AlertType.ERROR);
        } else if (m.getValor() == 0){
            main.java.views.util.Alerts.showAlert("Erro", null, "Selecione uma meta válida!", Alert.AlertType.ERROR);
        } else {

            try {
                usuarioLogado.getRepositorioMeta().removerMeta(m.getData_criacao().getMonthValue());
                ControladorDadosPersistentes.getInstance().atualizarUsuario(usuarioLogado);
            } catch(MetaNaoEncontradaException e) {
                main.java.views.util.Alerts.showAlert("Erro", null, "Meta não encontrada!", Alert.AlertType.ERROR);
            } catch (UsuarioNaoEncontradoException e) {
                main.java.views.util.Alerts.showAlert("Erro", null, "Usuário não encontrado!", Alert.AlertType.ERROR);
            }
        }
    }

    private void initializeNodes() {
        tableColumnCreationDate.setCellValueFactory(new PropertyValueFactory<>("data_criacao"));
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    private void updateTableView() {

        List<Meta> listMetas = new ArrayList<>();

        for (int i = LocalDate.now().getMonthValue(); i < 13; i++) {
            Meta aux = null;

            for (Meta meta : usuarioLogado.getRepositorioMeta().getMetas()) {
                if (meta.getData_criacao().getMonthValue() == i) {
                    aux = meta;
                    break;
                }
            }

            if (aux != null) {
                listMetas.add(aux);
            } else {
                LocalDate d = LocalDate.now();
                d = d.withMonth(i);
                listMetas.add(new Meta(0, "Não há metas neste mês", d));
            }
        }

        ObservableList<Meta> metasObservableList = FXCollections.observableArrayList(listMetas);
        tableViewMetas.setItems(metasObservableList);
    }
}
