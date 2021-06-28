package views;

import dao.impl.RepositorioMeta;
import model.Meta;

import java.time.LocalDate;

public class MetasViewController {

    private static MetasViewController instance;

    private MetasViewController() {

    }

    public static MetasViewController getInstance() {

        if (instance == null) {
            instance = new MetasViewController();
        }
        return instance;
    }


    public void criarMeta(double valor, LocalDate data) {
        //Chamando função do ControladorMetas de criação de meta

    }

    public void removerMeta() {
        //Chamando função do ControladorMetas de remoção de meta
    }

    public void alterarMeta() {
        //Chamando função do ControladorMetas de alteração de meta
    }

    public void visualizarMetas() {
        //Chamando função do ControladorMetas de lista de metas
    }

    public void execute(double valor, LocalDate data) {

    }


}

