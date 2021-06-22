import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DadosNaoPreenchidosException, UsuarioCadastradoException, SenhaIncorretaException, UsuarioNaoEncontradoException {

        ControladorLogin login = new ControladorLogin();
        ControladorDadosPersistentes dadosPersistentes = new ControladorDadosPersistentes();
        Scanner sc = new Scanner(System.in);

        int opcao = 0;

        while (opcao != 3) {

            System.out.println("Opção: ");
            int decisao = sc.nextInt();
            sc.nextLine();

            if (decisao == 1) {

                System.out.println("Nome: ");
                String nome = sc.nextLine();
                System.out.println("Login: ");
                String username = sc.nextLine();
                System.out.println("Senha: ");
                String senha = sc.nextLine();
                dadosPersistentes.getInstance().cadastrarUsuario(nome, username, senha);

            } else if (decisao == 2) {

                System.out.println("Login: ");
                String username = sc.nextLine();
                System.out.println("Senha: ");
                String senha = sc.nextLine();
                login.getInstance().login(username,senha);
            }

        }
        sc.close();
    }

}


