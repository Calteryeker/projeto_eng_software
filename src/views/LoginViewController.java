package views;

import controllers.ControladorCategoria;
import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DataDespesaInvalidaException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import dao.impl.exceptions.NomeCategoriaInvalidoException;
import dao.impl.exceptions.NomeDespesaInvalidoException;
import dao.impl.exceptions.NumeroDeCategoriaSelecionadaInvalidoException;
import dao.impl.exceptions.NumeroDespesaSelecionadaInvalidoException;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import dao.impl.exceptions.ValorDespesaInvalidoException;
import model.Usuario;

import java.util.Scanner;

public class LoginViewController {

    private static LoginViewController instance;

    private LoginViewController() {

    }

    public static LoginViewController getInstance() {

        if (instance == null) {
            instance = new LoginViewController();
        }
        return instance;
    }

    public static boolean Login() {

        boolean logado = false;

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("Digite o Login: ");
        String username = sc.nextLine();
        System.out.println("Digite a Senha: ");
        String senha = sc.nextLine();

        try {

            ControladorLogin.getInstance().login(username, senha);
            System.out.println();
            System.out.println("Login efetuado com sucesso!!");
            logado = true;

        } catch (UsuarioNaoEncontradoException e) {

            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");

        } catch (SenhaIncorretaException e) {

            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");
        }

        return logado;
    }

    public static void Cadastro()
            throws UsuarioJaCadastradoException, DadosNaoPreenchidosException {

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("Digite o Nome: ");
        String nome = sc.nextLine();
        System.out.println("Digite o Login: ");
        String username = sc.nextLine();
        System.out.println("Digite a Senha: ");
        String senha = sc.nextLine();

        ControladorDadosPersistentes.getInstance().cadastrarUsuario(nome, username, senha);
    }

    public void execute(boolean logado)
        throws UsuarioJaCadastradoException, DadosNaoPreenchidosException, NumeroDespesaSelecionadaInvalidoException, NumeroDeCategoriaSelecionadaInvalidoException, NomeCategoriaInvalidoException, ValorDespesaInvalidoException, CategoriaNulaException, DataDespesaInvalidaException, DespesaNaoEncontradaException, NomeDespesaInvalidoException {

        Scanner sc = new Scanner(System.in);

        //Auxiliares
        int opcaoMenuP = 0;
        int opcaoMenu2 = 0;
        int opcaoMenuDes = 0;
        int opcaoMenuCat = 0;
        int opcaoMenuMet = 0;
        int aux = 0;
        boolean admDespesas = false;
        boolean admMetas = false;
        boolean admCategorias = false;
        Usuario usuarioLogado = new Usuario();

        while (opcaoMenuP != 3) {

            if(!logado) {
                System.out.println();
                System.out.println("Digite a Opção: ");
                System.out.println("1 - Cadastrar Novo Usuario;");
                System.out.println("2 - Logar;");
                System.out.println("3 - Sair do Sistema.");

                opcaoMenuP = sc.nextInt();

                sc.nextLine();

                if (opcaoMenuP == 1) {
                    Cadastro();
                } else if (opcaoMenuP == 2) {
                    logado = Login();
                } else {
                    return;
                }
            }

            while (logado) {

                if (aux == 0) {
                    usuarioLogado = ControladorLogin.getInstance().getLoggedUser();
                    aux = 1;
                    opcaoMenuP = 0;
                }

                System.out.println();
                System.out.println("Digite a Opção: ");
                System.out.println("1 - Administrar Despesas;");
                System.out.println("2 - Criar Uma Categoria;");
                System.out.println("3 - Administrar Metas;");
                System.out.println("4 - Logout.");

                opcaoMenu2 = sc.nextInt();

                sc.nextLine();

                if (opcaoMenu2 == 1) {


                    if(ControladorCategoria.getInstance().getCategorias() == null || ControladorCategoria.getInstance().getCategorias().isEmpty()) {

                        String CSI = "\u001B[";

                        System.out.println();
                        System.out.print(CSI + "31" + "m");
                        System.out.println("Não Existem Categorias No Sistema Para Administrar Despesas, Operação Cancelada");
                        System.out.print(CSI + "m");

                    } else{
                        DespesaViewController.getInstance().execute(1);
                    }
                } else if (opcaoMenu2 == 2) {
                        DespesaViewController.getInstance().execute(2);
                } else if (opcaoMenu2 == 3) {

                } else if (opcaoMenu2 == 4) {
                    aux = 0;
                    logado = false;
                }

            }

        }
        sc.close();
    }


}


