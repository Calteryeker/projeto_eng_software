
import java.time.LocalDate;
import java.util.Map;

import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DataDespesaInvalidaException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.NomeCategoriaInvalidoException;
import dao.impl.exceptions.NomeDespesaInvalidoException;
import dao.impl.exceptions.NumeroDeCategoriaSelecionadaInvalidoException;
import dao.impl.exceptions.NumeroDespesaSelecionadaInvalidoException;
import dao.impl.exceptions.UsuarioJaCadastradoException;

import dao.impl.exceptions.ValorDespesaInvalidoException;
import model.Categoria;
import model.Despesa;
import model.Meta;
import model.Usuario;
import views.LoginViewController;
import views.MetasViewController;

public class Main {

    public static void main(String[] args)
        throws DadosNaoPreenchidosException, UsuarioJaCadastradoException, NumeroDespesaSelecionadaInvalidoException, NomeCategoriaInvalidoException, ValorDespesaInvalidoException, CategoriaNulaException, DataDespesaInvalidaException, NumeroDeCategoriaSelecionadaInvalidoException, DespesaNaoEncontradaException, NomeDespesaInvalidoException, MetaJaCadastradaException {
         /*
        //LoginViewController.getInstance().execute(false);
        Usuario usuario = new Usuario("João", "joao", "senha_joao");
        usuario.addCategoria("Casa");

        
        for (Map.Entry<String,Categoria> categoria : usuario.getCategorias().entrySet()) {
            System.out.println(categoria.getKey() + " - " + categoria.getValue());
        }

        usuario.getRepositorioDespesa().criarDespesa(
            "detergente", 100d, LocalDate.now(), usuario.getCategorias().get("Casa"));
        usuario.getRepositorioMeta().criarMeta(1000d, null, LocalDate.now());

        for (Despesa despesa : usuario.getRepositorioDespesa().getDespesas()) {
            System.out.println(despesa);
        }

        for (Meta meta: usuario.getRepositorioMeta().getMetas()) {
            System.out.println(meta);
        }
        */
        Usuario usuarioAtivo = null;

        usuarioAtivo = LoginViewController.execute();

        [...]
    }
}
