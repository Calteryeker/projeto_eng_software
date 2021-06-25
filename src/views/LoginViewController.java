package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
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

    public void execute() throws UsuarioJaCadastradoException, DadosNaoPreenchidosException {

        Scanner sc = new Scanner(System.in);

        //Auxiliares
        int opcaoMenuP = 0;
        int opcaoMenu2 = 0;
        int opcaoMenuDes = 0;
        int opcaoMenuCat = 0;
        int opcaoMenuMet = 0;
        int aux = 0;
        boolean logado = false;
        boolean admDespesas = false;
        boolean admMetas = false;
        boolean admCategorias = false;
        Usuario usuarioLogado = new Usuario();

        while (opcaoMenuP != 3) {

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
                System.out.println("2 - Administrar Categorias;");
                System.out.println("3 - Administrar Metas;");
                System.out.println("4 - Logout.");

                opcaoMenu2 = sc.nextInt();

                sc.nextLine();

                if (opcaoMenu2 == 1) {

                    admDespesas = true;

                    while (admDespesas) {
                        System.out.println();
                        System.out.println("Digite a Opção: ");
                        System.out.println("1 - Adicionar Despesa;");
                        System.out.println("2 - Atualizar Despesa;");
                        System.out.println("3 - Remover Despesa;");
                        System.out.println("4 - Voltar.");

                        opcaoMenuDes = sc.nextInt();

                        sc.nextLine();
                    }
                } else if (opcaoMenu2 == 2) {

                } else if (opcaoMenu2 == 3) {

                } else if (opcaoMenu2 == 4) {
                    opcaoMenu2 = 0;
                    aux = 0;
                    logado = false;
                }

            }

        }
        sc.close();
    }


}


