package tests.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorMeta;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.MetaJaCadastradaException;
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
        controller.criarMeta(-100, "Lucro Loja", LocalDate.parse("01/02/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

}
