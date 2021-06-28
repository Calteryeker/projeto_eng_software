package dao.impl;

import dao.IRepositorioDespesa;
import dao.impl.exceptions.DespesaNaoEncontradaException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import model.Despesa;
import model.Usuario;

public class RepositorioDespesa implements IRepositorioDespesa {

    private List<Despesa> despesas;
    private String path;

    public RepositorioDespesa(String path) {

        this.path = path;
        this.despesas = new ArrayList<>();

        Object elementsList = FileUtilRepository.readFile(this.path);
        if (elementsList != null && elementsList instanceof List<?>) {
            this.despesas = (List<Despesa>) elementsList;
        }
    }

    public Despesa criarDespesa(String nome, double valor, LocalDate data_criacao, Categoria categoria) {

        Despesa auxDespesa = new Despesa(nome, valor, data_criacao, categoria);

        auxDespesa.setOrdem(LocalDateTime.now().hashCode());

        despesas.add(auxDespesa);

        FileUtilRepository.saveFile(despesas, path);

        return auxDespesa;
    }

    public Despesa editarDespesa(String nome, int idDespesa, double valor, LocalDate data_criacao, Categoria categoria)
            throws DespesaNaoEncontradaException {

        int auxiliar = -1;
        Despesa altDespesa = null;

        for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getOrdem() == idDespesa) {
                auxiliar = i;
            }
        }

        if (auxiliar != -1) {
            altDespesa = new Despesa(nome, valor, data_criacao, categoria);
            despesas.set(auxiliar, altDespesa);
        } else {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!!");
        }

        FileUtilRepository.saveFile(despesas, path);

        return altDespesa;
    }

    public Despesa removerDespesa(int idDespesa) throws DespesaNaoEncontradaException {

        Despesa delDespesa = null;
        int auxiliar = -1;
        for (int i = 0; i < despesas.size(); i++) {
            if (despesas.get(i).getOrdem() == idDespesa) {
                auxiliar = i;
                delDespesa = despesas.get(i);
            }
        }
        if (auxiliar != -1) {
            despesas.remove(auxiliar);
        } else {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!!");
        }

        FileUtilRepository.saveFile(despesas, path);

        return delDespesa;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void visualizarDespesas() {

        for (Despesa x : despesas) {
            System.out.println(x);
        }
    }

    public void visualizarDespesaGrafico() {

    }

    public List<Despesa> visualizarDespesasPorCategoria(Categoria categoria) {

        List<Despesa> despesasListaAuxiliar = new ArrayList<>();
        for (Despesa x : despesas) {
            if (x.getCategoria().equals(categoria)) {
                despesasListaAuxiliar.add(x);
            }
        }

        if (despesasListaAuxiliar.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada");
        }
        return despesasListaAuxiliar;
    }

    public File gerarCSV(String path) {
        File csv = new File(path);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(csv))) {
            List<String> linhas = gerarLinhasCSV(despesas);
            for (String linha : linhas) {
                bufferedWriter.write(linha);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csv;
    }

    private List<String> gerarLinhasCSV(List<Despesa> despesas) {
        List<String> linhas = new ArrayList<>();
        for (Despesa despesa : despesas) {
            String linha = despesa.getOrdem() 
                + ", " + despesa.getNome() 
                + ", " + despesa.getValor() 
                + ", " + despesa.getData_criacao()
                + ", " + despesa.getCategoria().getIdCategoria()
                + ", " + despesa.getCategoria().getNome();
            linhas.add(linha);
        }
        return linhas;
    }

}
