package com.mygdx.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import com.google.firebase.FirebaseApp;
import com.mygdx.game.callbacks.AuthResultCallback;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.UserPlayerHandler;
import com.mygdx.game.interfaces.AuthService;

import java.util.concurrent.CompletableFuture;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import com.mygdx.game.callbacks.PlayerCallback;

public class LoginTest {

    @Mock
    private AuthService authService;

    @Captor
    private ArgumentCaptor<AuthResultCallback> callbackCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUp() throws Exception {
        CompletableFuture<Boolean> signUpResult = new CompletableFuture<>();

        // Mocking authService.signUp method
        doAnswer(invocation -> {
            // Capture the callback provided to signUp
            AuthResultCallback callback = invocation.getArgument(2); // Index 2 for the callback
            // Simulate successful signup
            callback.onSuccess();
            return null;
        }).when(authService).signUp(anyString(), anyString(), any(AuthResultCallback.class));

        // Call the signup method (replace with actual test values)
        String testEmail = "testaccount@gmail.com";
        String testPassword = "saas234dafdu123she";
        authService.signUp(testEmail, testPassword, new AuthResultCallback() {
            @Override
            public void onSuccess() {
                signUpResult.complete(true);
            }

            @Override
            public void onFailure(Exception exception) {
                signUpResult.complete(false);
            }
        });
        // Ensure signup was successful
        assertTrue("Signup should succeed", signUpResult.get());
    }

    @Test
    public void testSignIn_Success() throws Exception {
        CompletableFuture<Boolean> signInResult = new CompletableFuture<>();

        // Mocking authService.signIn method for success scenario
        doAnswer(invocation -> {
            // Capture the AuthResultCallback passed to signIn
            AuthResultCallback callback = invocation.getArgument(2); // Index 2 for the callback
            // Simulate successful signIn
            callback.onSuccess();
            return null;
        }).when(authService).signIn(anyString(), anyString(), any(AuthResultCallback.class));

        // Call the signIn method (replace with actual test values)
        String testEmail = "tester@gmail.com";
        String testPassword = "saas234dafdu123she";

        // Perform the signIn operation
        authService.signIn(testEmail, testPassword, new AuthResultCallback() {
            @Override
            public void onSuccess() {
                signInResult.complete(true);
            }

            @Override
            public void onFailure(Exception exception) {
                signInResult.complete(false);
            }
        });

        // Ensure signIn was successful
        assertTrue("Sign in should succeed", signInResult.get());

        // Verify interactions with AuthResultCallback
        verify(authService).signIn(eq(testEmail), eq(testPassword), any(AuthResultCallback.class));
    }

    @Test
    public void testSignIn_Failure() throws Exception {
        CompletableFuture<Boolean> signInResult = new CompletableFuture<>();

        // Mocking authService.signIn method for failure scenario
        doAnswer(invocation -> {
            // Capture the AuthResultCallback passed to signIn
            AuthResultCallback callback = invocation.getArgument(2); // Index 2 for the callback
            // Simulate signIn failure
            callback.onFailure(new Exception("Authentication failed"));
            return null;
        }).when(authService).signIn(anyString(), anyString(), any(AuthResultCallback.class));

        // Call the signIn method (replace with actual test values)
        String testEmail = "invalidemail..com";
        String testPassword = "invalidPassword";

        // Perform the signIn operation
        authService.signIn(testEmail, testPassword, new AuthResultCallback() {
            @Override
            public void onSuccess() {
                signInResult.complete(true);
            }

            @Override
            public void onFailure(Exception exception) {
                signInResult.complete(false);
            }
        });

        // Ensure signIn failed
        assertFalse("Sign in should fail", signInResult.get());

        // Verify interactions with AuthResultCallback (failure scenario)
        verify(authService).signIn(eq(testEmail), eq(testPassword), any(AuthResultCallback.class));
    }
}
