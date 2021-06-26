
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DataDespesaInvalidaException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import dao.impl.exceptions.NomeCategoriaInvalidoException;
import dao.impl.exceptions.NomeDespesaInvalidoException;
import dao.impl.exceptions.NumeroDeCategoriaSelecionadaInvalidoException;
import dao.impl.exceptions.NumeroDespesaSelecionadaInvalidoException;
import dao.impl.exceptions.UsuarioJaCadastradoException;

import dao.impl.exceptions.ValorDespesaInvalidoException;

import views.LoginViewController;

public class Main {

    public static void main(String[] args)
        throws DadosNaoPreenchidosException, UsuarioJaCadastradoException, NumeroDespesaSelecionadaInvalidoException, NomeCategoriaInvalidoException, ValorDespesaInvalidoException, CategoriaNulaException, DataDespesaInvalidaException, NumeroDeCategoriaSelecionadaInvalidoException, DespesaNaoEncontradaException, NomeDespesaInvalidoException {

        LoginViewController.getInstance().execute(false);

    }
}
