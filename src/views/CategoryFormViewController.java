package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.CategoriaJaCadastradaException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import main.java.views.util.Alerts;
import views.listeners.IDataChangeListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryFormViewController implements Initializable {

    @FXML private TextField categoryNameTxt;
    private List<IDataChangeListener> listeners;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario = ControladorLogin.getInstance().getLoggedUser();
        listeners = new ArrayList<>();
    }

    public void onSaveBtAction(ActionEvent event)
    {
        if (categoryNameTxt.getText().isEmpty())
        {
            Alerts.showAlert("Erro", null, "O nome não pode ser vazio.", Alert.AlertType.ERROR);
        } else {

            try
            {
                usuario.addCategoria(categoryNameTxt.getText());
                ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

                for (IDataChangeListener listener : listeners) {
                    listener.onDataChanged();
                }

                ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
            } catch (CategoriaJaCadastradaException e) {
                Alerts.showAlert("Erro", null, "Esse nome de categoria já está cadastrado.", Alert.AlertType.ERROR);
            } catch (UsuarioNaoEncontradoException e) {
                Alerts.showAlert("Erro", null, "Usuário não encontrado.", Alert.AlertType.ERROR);
            }
        }
    }

    public void onCancelBtAction(ActionEvent event)
    {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    public void subscribeDataChangeListener(IDataChangeListener listener) {
        listeners.add(listener);
    }
}
