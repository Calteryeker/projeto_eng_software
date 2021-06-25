package views;

import controllers.ControladorCategoria;
import controllers.ControladorDespesa;
import dao.impl.RepositorioDespesa;
import dao.impl.exceptions.*;
import model.Categoria;
import model.Despesa;

import javax.naming.ldap.Control;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class DespesaViewController {

    private static DespesaViewController instance;

    private DespesaViewController() {

    }

    public static DespesaViewController getInstance() {

        if (instance == null) {
            instance = new DespesaViewController();
        }
        return instance;
    }

    public void execute() throws NomeDespesaInvalidoException, ValorDespesaInvalidoException, NomeCategoriaInvalidoException, DadosNaoPreenchidosException, DataDespesaInvalidaException, NumeroDeCategoriaSelecionadaInvalidoException, CategoriaNulaException, NumeroDespesaSelecionadaInvalidoException, DespesaNaoEncontradaException {

        Scanner sc = new Scanner(System.in);
        int opcaoMenuP = 0;

        while (opcaoMenuP != 4) {

            System.out.println();
            System.out.println("Digite a Opção: ");
            System.out.println("1 - Adicionar Despesa;");
            System.out.println("2 - Remover Despesa;");
            System.out.println("3 - Alterar Despesa;");
            System.out.println("4 - Voltar.");
            opcaoMenuP = Integer.parseInt(sc.nextLine());

            if (opcaoMenuP == 1) {

                System.out.println("Insira os dados da nova despesa: ");
                System.out.println("Nome: ");
                String nomeDespesa = sc.nextLine();
                System.out.println("Valor: ");
                Double valorDespesa = Double.parseDouble(sc.nextLine());
                System.out.println("Data dd/MM/yyyy: ");
                String dataDespesa = sc.nextLine();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy: ");
                LocalDate localDate = LocalDate.parse(dataDespesa, format);
                System.out.println("Categoria: ");
                List<Categoria> categoriaList = ControladorCategoria.getInstance().getCategorias();
                for (int indice = 0; indice < categoriaList.size(); indice++) {
                    System.out.println("-" + indice + " " + categoriaList.get(indice).getNome());
                }
                int categoriaNomeDespesa = Integer.parseInt(sc.nextLine());
                if (categoriaNomeDespesa > categoriaList.size() || categoriaNomeDespesa < 1) {
                    throw new NumeroDeCategoriaSelecionadaInvalidoException("Número de Categoria Inválido (Categoria inexistente)!!");
                } else {

                    if (nomeDespesa == null) {
                        throw new NomeDespesaInvalidoException("Nome Inválido!!");
                    } else if (valorDespesa <= 0) {
                        throw new ValorDespesaInvalidoException("Valor Inválido!!");
                    } else if (ControladorCategoria.getInstance().getCategorias().get(categoriaNomeDespesa) == null) {
                        throw new NomeCategoriaInvalidoException("Nome Para Categoria Inválido!!");
                    } else if (dataDespesa == null) {
                        throw new DataDespesaInvalidaException("Data Inválida!!");
                    } else {

                        ControladorDespesa controladorDespesa = ControladorDespesa.getInstance();
                        controladorDespesa.criarDespesa(nomeDespesa, valorDespesa, localDate, ControladorCategoria.getInstance().getCategorias().get(categoriaNomeDespesa));
                        System.out.println("Despesa Criada com Sucesso!!");
                    }
                }
            } else if (opcaoMenuP == 2) {

                List<Categoria> categoriaList = ControladorCategoria.getInstance().getCategorias();
                System.out.println("Informe Número da Categoria: ");
                for (int indice = 0; indice < categoriaList.size(); indice++) {
                    System.out.println("-" + indice + " " + categoriaList.get(indice).getNome());
                }
                int numeroCategoriaSelecionada = Integer.parseInt(sc.nextLine());
                if (numeroCategoriaSelecionada > categoriaList.size() || numeroCategoriaSelecionada < 1) {
                    throw new NumeroDeCategoriaSelecionadaInvalidoException("Número de Categoria Inválido (Categoria inexistente)!!");
                } else {

                    List<Despesa> despesasList = ControladorDespesa.getInstance().visualizarDespesasPorCategoria(categoriaList.get(numeroCategoriaSelecionada));
                    System.out.println("Informe o Número da Despesa:");
                    for (int indice = 0; indice < despesasList.size(); indice++) {
                        System.out.println("-" + indice + " " + despesasList.get(indice).getNome());
                    }
                    int numeroDespesaSelecionada = Integer.parseInt(sc.nextLine());
                    if (numeroDespesaSelecionada > despesasList.size() || numeroDespesaSelecionada < 1) {
                        throw new NumeroDespesaSelecionadaInvalidoException("Numero de Despesa Invalido (Despesa Inexitente)!!");
                    } else {

                        Despesa despesaSelecionada = despesasList.get(numeroDespesaSelecionada);
                        System.out.println("Despesa Selecionada: ");
                        System.out.println(despesaSelecionada.toString());
                        System.out.println("Deseja Continuar ? [s/1||n/2]");
                        int decisaoConfirmacao = Integer.parseInt(sc.nextLine());
                        if (decisaoConfirmacao == 1) {

                            ControladorDespesa.getInstance().removerDespesa(despesasList.get(numeroDespesaSelecionada).getOrdem());
                            System.out.println("Despesa Removida com Sucesso!!");
                        } else if (decisaoConfirmacao == 2) {
                            System.out.println("Operação Cancelada com Sucesso!!");
                        } else {
                            System.out.println("Decisão Inválida!!");
                        }
                    }
                }
            } else if (opcaoMenuP == 3) {


            }
        }
    }
}