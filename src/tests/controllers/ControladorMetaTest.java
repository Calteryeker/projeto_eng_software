package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
import dao.impl.exceptions.MetaNaoEncontradaException;
import model.Meta;
import model.Usuario;

public class ControladorMetaTest {
   
    private ControladorMeta controller;
    private LocalDate date = LocalDate.now();
    private Usuario user;

    @BeforeEach
    public void setUp() {
        user = new Usuario("Danilo", "danilo", "danilo_pass");
        controller = ControladorMeta.getInstance(user.getRepositorioMeta());
        
    }

    @Test
    @DisplayName("Teste Criacao de meta!")
    public void testeCriarMeta() throws MetaJaCadastradaException, DadosNaoPreenchidosException{
        Meta aux = controller.criarMeta(1000, "Lucro Loja", date);
        assertTrue(user.getRepositorioMeta().getMetas().contains(aux));

    }

    @Test
    @DisplayName("Meta com valor inválido")
    public void testeCriarMetaDadosInv(){
        assertThrows(DadosNaoPreenchidosException.class ,() -> {
            controller.criarMeta(-100, "Lucro Loja", date);
        });
       
    }

    @Test
    @DisplayName("Meta com data inválida")
    public void testeCriarMetaDataInv(){
        assertThrows(DadosNaoPreenchidosException.class ,() -> {
        controller.criarMeta(100, "Lucro Loja", LocalDate.parse("01/02/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } );
    }

   
    @Test
    @DisplayName("Criar meta duplicada")
    public void testeMetaJaCriada(){
        assertThrows(MetaJaCadastradaException.class ,() -> {
            controller.criarMeta(1000, "Lucro Loja", date);
            controller.criarMeta(1000, "Lucro Loja", date);
        } );
    }

    @Test
    @DisplayName("Alterar meta")
    public void testeAlterarMeta() throws MetaJaCadastradaException, DadosNaoPreenchidosException, MetaNaoEncontradaException{
        controller.criarMeta(1000, "Lucro Loja", date);
        Meta antiga = new Meta(1000, "Lucro Loja", date);
        Meta obtida = controller.alterarMeta(1200, "Lucro", date);
        assertNotEquals(obtida, antiga);
        
    }

    @Test
    @DisplayName("Alterar meta que não existe")
    public void testeAltMetaNaoExiste(){
        assertThrows(MetaNaoEncontradaException.class, () -> {controller.alterarMeta(1200, "Lucro", date);});
    }

    @Test
    @DisplayName("Remover meta")
    public void testeRemoveMeta() throws MetaJaCadastradaException, DadosNaoPreenchidosException, MetaNaoEncontradaException{
        Meta criada = controller.criarMeta(1000, "Lucro Loja", date);
        Meta deletada = controller.removerMeta(date.getMonthValue());
        assertEquals(criada, deletada);
    }

    @Test
    @DisplayName("Remover meta não criada")
    public void testeRemoveMetaNaoCriada() throws MetaNaoEncontradaException{
       assertThrows(MetaNaoEncontradaException.class, () -> {
            controller.removerMeta(date.getMonthValue());
       });
    }

    @AfterEach
    public void tearDown(){
        File file = new File("localstorage/" + user.getLogin() + "_" + "metas" + ".ser");
        file.delete();
    }

}
