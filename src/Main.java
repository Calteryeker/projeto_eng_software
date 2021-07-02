
import dao.impl.exceptions.*;

import views.LoginViewController;
import views.MetasViewController;

public class Main {

    public static void main(String[] args)
            throws DadosNaoPreenchidosException, UsuarioJaCadastradoException, NumeroDespesaSelecionadaInvalidoException, NomeCategoriaInvalidoException, ValorDespesaInvalidoException, CategoriaNulaException, DataDespesaInvalidaException, NumeroDeCategoriaSelecionadaInvalidoException, DespesaNaoEncontradaException, NomeDespesaInvalidoException, MetaJaCadastradaException, MetaNaoEncontradaException {

        MetasViewController.getInstance().execute();

    }
}
