package tests.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControladorDadosPersistentes;
import controllers.ControladorLogin;
import dao.impl.exceptions.DadosNaoPreenchidosException;
import dao.impl.exceptions.SenhaIncorretaException;
import dao.impl.exceptions.UsuarioJaCadastradoException;
import dao.impl.exceptions.UsuarioNaoEncontradoException;
import model.Usuario;

public class ControladorLoginTest {

    private ControladorLogin loginController;
    private ControladorDadosPersistentes userController;
    private Usuario signedUpUser;

    @BeforeEach
    public void setUp() throws DadosNaoPreenchidosException, UsuarioJaCadastradoException {
        loginController = ControladorLogin.getInstance();
        userController = ControladorDadosPersistentes.getInstance();
        userController.cadastrarUsuario("Rodrigo", "rodrigo", "rodrigo_pass");
        List<Usuario> users = userController.getDadosPersistentes().getUsuarios();
        for (Usuario user : users) {
            if (user.getNome() == "Rodrigo" && user.getLogin() == "rodrigo") {
                signedUpUser = user;
            }
        }
    }

    @Test
    @DisplayName("Login with signed up user should work")
    public void testLoginSucceeds() throws SenhaIncorretaException, UsuarioNaoEncontradoException {
        String login = loginController.login(signedUpUser.getLogin(), signedUpUser.getSenha());
        Usuario loggedUser = loginController.getLoggedUser();
        assertEquals(signedUpUser.getNome(), loggedUser.getNome());
        assertEquals(signedUpUser.getLogin(), loggedUser.getLogin());
        assertEquals(signedUpUser.getSenha(), loggedUser.getSenha());
        assertNotNull(login);
    }

    @Test
    @DisplayName("Login with invalid should throw SenhaIncorretaException")
    public void testLoginWithInvalidPasswordFails() {
        Exception exception = assertThrows(SenhaIncorretaException.class, () -> {
            loginController.login(signedUpUser.getLogin(), "wrong_pass");
        });

        assertEquals("Senha Incorreta!!", exception.getMessage());
    }

    @Test
    @DisplayName("Login with a non existent user should throw UsuarioNaoEncontradoException")
    public void testLoginWithNonSignedUpUser() {
        Exception exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
            loginController.login("unkown_login", "unkown_pass");
        });

        assertEquals("Usuario não encontrado!!", exception.getMessage());
    }

    @Test
    @DisplayName("Get instance of the logged user shouldn't be null")
    public void testGetActiveUser() throws SenhaIncorretaException, UsuarioNaoEncontradoException {
        String login = loginController.login(signedUpUser.getLogin(), signedUpUser.getSenha());
        Usuario loggedUser = loginController.getLoggedUser();

        assertEquals(signedUpUser.getNome(), loggedUser.getNome());
        assertEquals(signedUpUser.getLogin(), loggedUser.getLogin());
        assertEquals(signedUpUser.getSenha(), loggedUser.getSenha());
        assertNotNull(login);
    }
}
