package com.mygdx.global;

/**
 * Package sent to Server when user first logs in at loginScreen.
 * Contains userId of the Player. This is to be mapped to its Connection.
 */
public class PlayerJoinServerEvent {
    public String userId;
    public PlayerJoinServerEvent(){};

}
