package views;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorDespesa;
import dao.impl.exceptions.*;
import model.Categoria;
import model.Despesa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.Usuario;

public class DespesaViewController {

    private static DespesaViewController instance;

    private DespesaViewController() {}

    public static DespesaViewController getInstance() {

        if (instance == null) {
            instance = new DespesaViewController();
        }
        return instance;
    }

    public static void criarCategoria(Usuario usuario)
        throws DataDespesaInvalidaException, NumeroDespesaSelecionadaInvalidoException, NomeDespesaInvalidoException, CategoriaNulaException, DadosNaoPreenchidosException, NumeroDeCategoriaSelecionadaInvalidoException, DespesaNaoEncontradaException, NomeCategoriaInvalidoException, UsuarioJaCadastradoException, ValorDespesaInvalidoException, UsuarioNaoEncontradoException, MetaNaoEncontradaException, MetaJaCadastradaException {
        System.out.println();
        System.out.println("Digite o Nome da Categoria: ");

        Scanner sc = new Scanner(System.in);
        String auxCategoria = sc.nextLine();

        int indice = 1;

        try{
            usuario.addCategoria(auxCategoria);
            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);

            for (Categoria categoria : usuario.getCategorias()) {

                System.out.println("-" + indice + " " + categoria.getNome());
                indice++;
            }

        } catch(CategoriaJaCadastradaException e){

            String CSI = "\u001B[";

            System.out.println();
            System.out.print(CSI + "31" + "m");
            System.out.println(e.getMessage());
            System.out.print(CSI + "m");

        }
        LoginViewController.getInstance().execute(true);
    }

    public void execute(int value, Usuario usuario)
        throws DadosNaoPreenchidosException, CategoriaNulaException, DespesaNaoEncontradaException, ValorDespesaInvalidoException, NumeroDespesaSelecionadaInvalidoException, NomeCategoriaInvalidoException, DataDespesaInvalidaException, UsuarioJaCadastradoException, NumeroDeCategoriaSelecionadaInvalidoException, NomeDespesaInvalidoException, UsuarioNaoEncontradoException, MetaNaoEncontradaException, MetaJaCadastradaException {

        Scanner sc = new Scanner(System.in);
        int opcaoMenuP = 0;

        if (value == 1) {
            while (opcaoMenuP != 6) {

                System.out.println();
                System.out.println("Digite a Opção: ");
                System.out.println("1 - Adicionar Despesa;");
                System.out.println("2 - Remover Despesa;");
                System.out.println("3 - Alterar Despesa;");
                System.out.println("4 - Vizualizar Despesas;");
                System.out.println("5 - Gerar Grafico de Despesas;");
                System.out.println("6 - Voltar.");
                opcaoMenuP = Integer.parseInt(sc.nextLine());

                if (opcaoMenuP == 1) {

                    System.out.println("Insira os dados da nova despesa: ");
                    System.out.println("Nome: ");
                    String nomeDespesa = sc.nextLine();
                    System.out.println("Valor: ");
                    Double valorDespesa = Double.parseDouble(sc.nextLine());
                    System.out.println("Data (Formato: dd-MM-yyyy): ");
                    String dataDespesa = sc.nextLine();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate localDate = LocalDate.parse(dataDespesa, format);
                    System.out.println("Categoria: ");
                    List<Categoria> categoriaList = usuario.getCategorias();
                    for (int indice = 0; indice < categoriaList.size(); indice++) {
                        System.out
                            .println("-" + (indice + 1) + " " + categoriaList.get(indice).getNome());
                    }
                    int categoriaNomeDespesa = Integer.parseInt(sc.nextLine());
                    if (categoriaNomeDespesa > categoriaList.size() || categoriaNomeDespesa < 1) {

                        String CSI = "\u001B[";

                        System.out.println();
                        System.out.print(CSI + "31" + "m");
                        System.out.println("Categoria de Despesa Inválida");
                        System.out.print(CSI + "m");

                    } else {

                        if (nomeDespesa == null) {

                            String CSI = "\u001B[";

                            System.out.println();
                            System.out.print(CSI + "31" + "m");
                            System.out.println("Nome Inválido!!");
                            System.out.print(CSI + "m");

                        } else if (valorDespesa <= 0) {

                            String CSI = "\u001B[";

                            System.out.println();
                            System.out.print(CSI + "31" + "m");
                            System.out.println("Valor Inválido!!");
                            System.out.print(CSI + "m");

                        } else if (dataDespesa == null) {
                            String CSI = "\u001B[";

                            System.out.println();
                            System.out.print(CSI + "31" + "m");
                            System.out.println("Data Inválida!!");
                            System.out.print(CSI + "m");

                        } else {

                            ControladorDespesa controladorDespesa = ControladorDespesa
                                .getInstance(usuario.getRepositorioDespesa());
                            controladorDespesa.criarDespesa(nomeDespesa, valorDespesa, localDate,
                                usuario.getCategorias()
                                    .get(categoriaNomeDespesa - 1));
                            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
                            System.out.println("Despesa Criada com Sucesso!!");
                        }
                    }
                } else if (opcaoMenuP == 2) {

                    List<Categoria> categoriaList = usuario.getCategorias();

                    System.out.println("Informe Número da Categoria: ");

                    for (int indice = 0; indice < categoriaList.size(); indice++) {
                        System.out
                            .println("-" + (indice + 1) + " " + categoriaList.get(indice).getNome());
                    }

                    int numeroCategoriaSelecionada = Integer.parseInt(sc.nextLine());

                    if (numeroCategoriaSelecionada > categoriaList.size()
                        || numeroCategoriaSelecionada < 1) {

                        String CSI = "\u001B[";

                        System.out.println();
                        System.out.print(CSI + "31" + "m");
                        System.out.println("Categoria Inválida!!");
                        System.out.print(CSI + "m");
                    } else {

                        List<Despesa> despesasList = ControladorDespesa.getInstance(
                            usuario.getRepositorioDespesa())
                            .visualizarDespesasPorCategoria(
                                categoriaList.get(numeroCategoriaSelecionada - 1));

                        if (!despesasList.isEmpty()) {
                            System.out.println("Informe o Número da Despesa:");
                            for (int indice = 0; indice < despesasList.size(); indice++) {
                                System.out
                                    .println("-" + (indice + 1) + " " + despesasList.get(indice));
                            }
                            int numeroDespesaSelecionada = Integer.parseInt(sc.nextLine());
                            if (numeroDespesaSelecionada > despesasList.size()
                                || numeroDespesaSelecionada < 1) {
                                String CSI = "\u001B[";

                                System.out.println();
                                System.out.print(CSI + "31" + "m");
                                System.out.println("Despesa Inválida!!");
                                System.out.print(CSI + "m");

                            } else {

                                Despesa despesaSelecionada = despesasList
                                    .get(numeroDespesaSelecionada - 1);
                                System.out.println("Despesa Selecionada: ");
                                System.out.println(despesaSelecionada);
                                System.out.println("Deseja Continuar ? [s/1||n/2]");
                                int decisaoConfirmacao = Integer.parseInt(sc.nextLine());
                                if (decisaoConfirmacao == 1) {

                                    ControladorDespesa.getInstance(usuario.getRepositorioDespesa()).removerDespesa(
                                        despesasList.get(numeroDespesaSelecionada - 1).getOrdem());
                                    ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
                                    System.out.println("Despesa Removida com Sucesso!!");
                                } else if (decisaoConfirmacao == 2) {
                                    System.out.println("Operação Cancelada com Sucesso!!");
                                } else {
                                    System.out.println("Decisão Inválida! Operação Cancelada.");
                                }
                            }
                        } else {
                            System.out.println("Operação Cancelada.");
                        }
                    }
                } else if (opcaoMenuP == 3) {
                    List<Categoria> categoriaList = usuario.getCategorias();

                    System.out.println("Informe Número da Categoria: ");

                    for (int indice = 0; indice < categoriaList.size(); indice++) {
                        System.out
                            .println("-" + (indice + 1) + " " + categoriaList.get(indice).getNome());
                    }

                    int numeroCategoriaSelecionada = Integer.parseInt(sc.nextLine());

                    if (numeroCategoriaSelecionada > categoriaList.size()
                        || numeroCategoriaSelecionada < 1) {

                        String CSI = "\u001B[";

                        System.out.println();
                        System.out.print(CSI + "31" + "m");
                        System.out.println("Categoria Inválida!!");
                        System.out.print(CSI + "m");

                    }else if(categoriaList.isEmpty()){
                        System.out.println("Operação Cancelada!!");
                    } else {

                        List<Despesa> despesasList = ControladorDespesa.getInstance(
                            usuario.getRepositorioDespesa())
                            .visualizarDespesasPorCategoria(
                                categoriaList.get(numeroCategoriaSelecionada - 1));

                        if (!despesasList.isEmpty()) {
                            System.out.println("Informe o Número da Despesa:");
                            for (int indice = 0; indice < despesasList.size(); indice++) {
                                System.out
                                    .println("-" + (indice + 1) + " " + despesasList.get(indice));
                            }
                            int numeroDespesaSelecionada = Integer.parseInt(sc.nextLine());
                            if (numeroDespesaSelecionada > despesasList.size()
                                || numeroDespesaSelecionada < 1) {
                                String CSI = "\u001B[";

                                System.out.println();
                                System.out.print(CSI + "31" + "m");
                                System.out.println("Despesa Inválida!!");
                                System.out.print(CSI + "m");

                            } else {

                                Despesa despesaSelecionada = despesasList
                                    .get(numeroDespesaSelecionada - 1);
                                System.out.println("Despesa Selecionada: ");
                                System.out.println(despesaSelecionada);

                                System.out.println("Insira os dados para editar na despesa: ");
                                System.out.println("Nome: ");
                                String nomeDespesa = sc.nextLine();
                                System.out.println("Valor: ");
                                Double valorDespesa = Double.parseDouble(sc.nextLine());
                                System.out.println("Data (Formato: dd-MM-yyyy): ");
                                String dataDespesa = sc.nextLine();
                                DateTimeFormatter format = DateTimeFormatter
                                    .ofPattern("dd-MM-yyyy");
                                LocalDate localDate = LocalDate.parse(dataDespesa, format);
                                System.out.println("Categoria: ");
                                for (int indice = 0; indice < categoriaList.size(); indice++) {
                                    System.out.println(
                                        "-" + (indice + 1) + " " + categoriaList.get(indice)
                                            .getNome());
                                }
                                int categoriaNomeDespesa = Integer.parseInt(sc.nextLine());
                                if (categoriaNomeDespesa > categoriaList.size()
                                    || categoriaNomeDespesa < 1) {

                                    String CSI = "\u001B[";

                                    System.out.println();
                                    System.out.print(CSI + "31" + "m");
                                    System.out.println("Categoria de Despesa Inválida");
                                    System.out.print(CSI + "m");

                                } else {

                                    if (nomeDespesa == null) {

                                        String CSI = "\u001B[";

                                        System.out.println();
                                        System.out.print(CSI + "31" + "m");
                                        System.out.println("Nome Inválido!!");
                                        System.out.print(CSI + "m");

                                    } else if (valorDespesa <= 0) {

                                        String CSI = "\u001B[";

                                        System.out.println();
                                        System.out.print(CSI + "31" + "m");
                                        System.out.println("Valor Inválido!!");
                                        System.out.print(CSI + "m");

                                    } else if (dataDespesa == null) {
                                        String CSI = "\u001B[";

                                        System.out.println();
                                        System.out.print(CSI + "31" + "m");
                                        System.out.println("Data Inválida!!");
                                        System.out.print(CSI + "m");

                                    } else {

                                        System.out.println("Deseja Continuar ? [s/1||n/2]");
                                        int decisaoConfirmacao = Integer.parseInt(sc.nextLine());
                                        if (decisaoConfirmacao == 1) {

                                            ControladorDespesa controladorDespesa = ControladorDespesa
                                                .getInstance(usuario.getRepositorioDespesa());
                                            controladorDespesa
                                                .alterarDespesa(nomeDespesa,
                                                    despesaSelecionada.getOrdem(), valorDespesa,
                                                    localDate,
                                                    usuario
                                                        .getCategorias()
                                                        .get(categoriaNomeDespesa - 1));
                                            ControladorDadosPersistentes.getInstance().atualizarUsuario(usuario);
                                            System.out.println("Despesa Alterada com Sucesso!!");

                                        } else if (decisaoConfirmacao == 2) {
                                            System.out.println("Operação Cancelada com Sucesso!!");
                                        } else {
                                            System.out
                                                .println("Decisão Inválida! Operação Cancelada.");
                                        }
                                    }
                                }

                            }
                        } else {
                            System.out.println("Operação Cancelada.");
                        }
                    }
                } else if (opcaoMenuP == 4){
                    ControladorDespesa.getInstance(usuario.getRepositorioDespesa()).vizualizarDespesas();
                } else if (opcaoMenuP == 5){
                    if(usuario.getRepositorioDespesa().getDespesas().isEmpty()){

                        String CSI = "\u001B[";

                        System.out.println();
                        System.out.print(CSI + "31" + "m");
                        System.out.println("Não Existem Despesas Para Gerar o Arquivo");
                        System.out.print(CSI + "m");
                    } else {
                        ControladorDespesa.getInstance(usuario.getRepositorioDespesa()).gerarGrafico(usuario.getLogin());
                    }
                } else {
                    LoginViewController.getInstance().execute(true);
                }
            }
        } else {
            criarCategoria(usuario);
        }
    }

}