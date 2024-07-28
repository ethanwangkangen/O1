package com.mygdx.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.DarwinsDuel;
import com.mygdx.game.MyClient;
import com.mygdx.game.interfaces.AuthService;
import com.mygdx.game.screens.LoginScreen;
import com.mygdx.game.screens.PetChangeScreen;

public class DarwinsDuelTest {
    private DarwinsDuel game;
    private AuthService authServiceMock;
    @BeforeEach
    public void setUp() {
        game = new DarwinsDuel();
        authServiceMock = mock(AuthService.class);
        // Mock Gdx dependencies
        Gdx.gl = mock(GL20.class);
        Gdx.app = mock(Application.class);
        when(Gdx.app.getType()).thenReturn(Application.ApplicationType.Desktop);
    }

    @Test
    public void testInitialState() {
        assertEquals(DarwinsDuel.GameState.SPLASH, DarwinsDuel.gameState, "Initial game state should be SPLASH.");
    }

    @Test
    public void testLoadTextures() {
        // Assuming TextureHandler.getInstance().loadTextures() has an observable effect
        assertTrue(game.loadTextures(), "Textures should load");
        // Verify that textures are loaded correctly
    }


}