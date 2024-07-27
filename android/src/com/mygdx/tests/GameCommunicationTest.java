package com.mygdx.tests;

import com.mygdx.game.interfaces.GameCommunication;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class GameCommunicationTest {

    @Mock
    private GameCommunication mockGameCommunication;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOnEnemyInfoReceived() {
        // Call the method on the mock
        mockGameCommunication.onEnemyInfoReceived("12345");

        // Verify that the method was called with the correct parameter
        verify(mockGameCommunication).onEnemyInfoReceived("12345");
    }

    @Test
    public void testOnQuitMapActivity() {
        // Call the method on the mock
        mockGameCommunication.onQuitMapActivity();

        // Verify that the method was called
        verify(mockGameCommunication).onQuitMapActivity();
    }

    @Test
    public void testOnNPCReqReceived() {
        // Call the method on the mock
        mockGameCommunication.onNPCReqReceived();

        // Verify that the method was called
        verify(mockGameCommunication).onNPCReqReceived();
    }

    @Test
    public void testOnAttributeActivity() {
        // Call the method on the mock
        mockGameCommunication.onAttributeActivity();

        // Verify that the method was called
        verify(mockGameCommunication).onAttributeActivity();
    }
}
