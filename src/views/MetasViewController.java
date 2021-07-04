package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorMeta;
import dao.impl.RepositorioMeta;
import dao.impl.exceptions.CategoriaNulaException;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.DataDespesaInvalidaException;
import dao.impl.exceptions.DespesaNaoEncontradaException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import dao.impl.exceptions.NomeCategoriaInvalidoException;
import dao.impl.exceptions.NomeDespesaInvalidoException;
import dao.impl.exceptions.NumeroDeCategoriaSelecionadaInvalidoException;
import dao.impl.exceptions.NumeroDespesaSelecionadaInvalidoException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import dao.impl.exceptions.ValorDespesaInvalidoException;
import model.Meta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.Usuario;

public class MetasViewController {

    private static MetasViewController instance;


    private MetasViewController() {}

    public static MetasViewController getInstance() {

        if (instance == null) {
            instance = new MetasViewController();
        }
        return instance;
    }


    public Meta criarMeta(double valor, String descricao, LocalDate data, Usuario usuario)
        throws DadosNaoPreenchidosException, UsuarioNaoEncontradoException {

        try {
            Meta meta = ControladorMeta.getInstance(usuario.getRepositorioMeta())
                .criarMeta(valor, descricao, data);
            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
            return meta;
        } catch (MetaJaCadastradaException e){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");

            return null;
        }
    }

    public Meta removerMeta(int mes, Usuario usuario)
        throws UsuarioNaoEncontradoException {
        try {
            Meta meta = ControladorMeta.getInstance(usuario.getRepositorioMeta()).removerMeta(mes);
            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
            return meta;
        }catch (MetaNaoEncontradaException e){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");

            return null;
        }
    }

    public Meta alterarMeta(double valor, String descricao, LocalDate data, Usuario usuario)
        throws DadosNaoPreenchidosException, UsuarioNaoEncontradoException {
        try {
            Meta meta = ControladorMeta.getInstance(usuario.getRepositorioMeta())
                .alterarMeta(valor, descricao, data);
            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
            return meta;
        }catch (MetaNaoEncontradaException e){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");

            return null;
        }
    }

    public void visualizarMetas(Usuario usuario) {
        ControladorMeta.getInstance(usuario.getRepositorioMeta()).visualizarHistoricoDeMetas();
    }

    public Meta exibirMenuAdicionarMetas(Scanner scanner,Usuario usuario)
        throws DadosNaoPreenchidosException, UsuarioNaoEncontradoException {


        System.out.println("Digite o Mês Da Nova Meta:");
        int mesMeta = scanner.nextInt();
        scanner.nextLine();

        if(mesMeta < LocalDate.now().getMonthValue() || mesMeta > 12){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Mês invalido");
            System.out.print(CSI + "m");

            return null;
        }

        System.out.println("Digite um valor para a nova meta: ");
        double valorMeta = scanner.nextDouble();
        scanner.nextLine();

        if(valorMeta <= 0){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Valor invalido");
            System.out.print(CSI + "m");

            return null;
        }


        System.out.println("Digite a descrição da nova meta(opcional): ");
        String descricaoMeta = scanner.nextLine();


        LocalDate localDate = LocalDate.now().withMonth(mesMeta);

        return criarMeta(valorMeta, descricaoMeta, localDate, usuario);
    }

    public Meta exibirMenuRemoverMetas(Scanner scanner, Usuario usuario)
        throws UsuarioNaoEncontradoException {

        System.out.println("Digite o Mês Onde se Encontra a Meta Para Remoção:");
        int mesMeta = scanner.nextInt();
        scanner.nextLine();

        if(mesMeta < LocalDate.now().getMonthValue() || mesMeta > 12){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Mês invalido");
            System.out.print(CSI + "m");

            return null;
        }

        return removerMeta(mesMeta,usuario);
    }

    public Meta exibirMenuAlterarMeta(Scanner scanner, Usuario usuario)
        throws  DadosNaoPreenchidosException, UsuarioNaoEncontradoException {

        visualizarMetas(usuario);

        System.out.println("Digite o Mês Da Meta a ser alterada:");
        int mesMeta = scanner.nextInt();
        scanner.nextLine();

        if(mesMeta < LocalDate.now().getMonthValue() || mesMeta > 12){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Mês invalido");
            System.out.print(CSI + "m");

            return null;
        }

        System.out.println("Digite Um Novo Valor Para a Meta: ");
        double valorMeta = scanner.nextDouble();
        scanner.nextLine();

        if (valorMeta <= 0){
            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println("Valor invalido");
            System.out.print(CSI + "m");

            return null;
        }

        System.out.println("Digite Uma Nova descrição Para a Meta(opcional): ");
        String descricaoMeta = scanner.nextLine();
        LocalDate localDate = LocalDate.now().withMonth(mesMeta);

        return alterarMeta(valorMeta, descricaoMeta, localDate, usuario);

    }


    public void execute(Usuario usuario)
        throws DadosNaoPreenchidosException, MetaJaCadastradaException, MetaNaoEncontradaException, DataDespesaInvalidaException, NumeroDespesaSelecionadaInvalidoException, NomeDespesaInvalidoException, CategoriaNulaException, UsuarioNaoEncontradoException, NumeroDeCategoriaSelecionadaInvalidoException, DespesaNaoEncontradaException, NomeCategoriaInvalidoException, UsuarioJaCadastradoException, ValorDespesaInvalidoException {

        Scanner sc = new Scanner(System.in);
        int opcaoMenuP = 0;

        while (opcaoMenuP != 5) {

            System.out.println();
            System.out.println("Digite a Opção: ");
            System.out.println("1 - Criar Nova Meta;");
            System.out.println("2 - Remover Uma Meta;");
            System.out.println("3 - Alterar Uma Meta;");
            System.out.println("4 - Visualizar Metas.");
            System.out.println("5 - Voltar");

            opcaoMenuP = Integer.parseInt(sc.nextLine());
            Meta metaAuxiliar = null;

            if (opcaoMenuP == 1) {
                metaAuxiliar = exibirMenuAdicionarMetas(sc,usuario);
            } else if (opcaoMenuP == 2) {
                metaAuxiliar = exibirMenuRemoverMetas(sc,usuario);

            } else if (opcaoMenuP == 3) {
                metaAuxiliar = exibirMenuAlterarMeta(sc,usuario);
            } else if (opcaoMenuP == 4) {
                visualizarMetas(usuario);
            }

        }
        sc.close();
    }


}

