package views;

import controllers.ControladorMeta;
import dao.impl.RepositorioMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import model.Meta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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


    public Meta criarMeta(double valor, String descricao, LocalDate data) throws DadosNaoPreenchidosException, MetaJaCadastradaException {
        return ControladorMeta.getInstance().criarMeta(valor, descricao, data);
    }

    public Meta removerMeta(LocalDate data) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {
        return ControladorMeta.getInstance().removerMeta(data);
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {
        return ControladorMeta.getInstance().alterarMeta(valor, descricao, data);
    }

    public void visualizarMetas() {
        ControladorMeta.getInstance().visualizarHistoricoDeMetas();
    }

    public Meta exibirMenuAdicionarMetas(Scanner scanner) throws DadosNaoPreenchidosException, MetaJaCadastradaException {

        System.out.println("Digite um valor para a nova meta: ");
        double valorMeta = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Digite a descrição da nova meta(opcional): ");
        String descricaoMeta = scanner.nextLine();
        System.out.println("Digite a data da nova meta: dd-MM-yyyy");
        String dataMeta = scanner.nextLine();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dataMeta, format);

        return criarMeta(valorMeta, descricaoMeta, localDate);
    }

    public Meta exibirMenuRemoverMetas(Scanner scanner) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {

        System.out.println("Digite a Data Onde se Encontra a Meta Para Remoção: dd-MM-yyyy");
        String dataMeta = scanner.nextLine();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dataMeta, format);

        return removerMeta(localDate);
    }

    public Meta exibirMenuAlterarMeta(Scanner scanner) throws MetaNaoEncontradaException, DadosNaoPreenchidosException {

        System.out.println("Digite Um Novo Valor Para a Meta: ");
        double valorMeta = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Digite Uma Nova descrição Para a Meta(opcional): ");
        String descricaoMeta = scanner.nextLine();
        System.out.println("Digite a Data da Meta que Será Alterada: dd-MM-yyyy");
        String dataMeta = scanner.nextLine();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dataMeta, format);

        return alterarMeta(valorMeta, descricaoMeta, localDate);

    }


    public void execute() throws DadosNaoPreenchidosException, MetaJaCadastradaException, MetaNaoEncontradaException {

        Scanner sc = new Scanner(System.in);
        int opcaoMenuP = 0;

        while (opcaoMenuP != 5) {

            System.out.println();
            System.out.println("Digite a Opção: ");
            System.out.println("1 - Criar Nova Meta;");
            System.out.println("2 - Remover Uma Meta;");
            System.out.println("3 - Alterar Uma Meta;");
            System.out.println("4 - Visualizar Metas.");

            opcaoMenuP = Integer.parseInt(sc.nextLine());
            Meta metaAuxiliar = null;

            if (opcaoMenuP == 1) {
                metaAuxiliar = exibirMenuAdicionarMetas(sc);
            } else if (opcaoMenuP == 2) {
                metaAuxiliar = exibirMenuRemoverMetas(sc);

            } else if (opcaoMenuP == 3) {
                metaAuxiliar = exibirMenuAlterarMeta(sc);
            } else if (opcaoMenuP == 4) {
                visualizarMetas();
            }

        }
        sc.close();
    }


}

