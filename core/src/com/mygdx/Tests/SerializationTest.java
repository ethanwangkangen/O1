package com.mygdx.Tests;

import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.MeowmadAli;
import com.mygdx.game.entities.Player;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.mygdx.global.JoinRequestEvent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;

public class SerializationTest {

    public static void main(String[] args) {
        // Create a test object
        JoinRequestEvent testObject = new JoinRequestEvent(/* Initialize with test data */);

        // Serialize the test object
        byte[] serializedData = serialize(testObject);

        // Deserialize the serialized data
        JoinRequestEvent deserializedObject =  deserialize(serializedData);

        // Verify if deserialization was successful
        if (deserializedObject != null && deserializedObject.equals(testObject)) {
            System.out.println("Serialization and deserialization successful!");
        } else {
            System.out.println("Serialization or deserialization failed.");
        }
    }

    private static byte[] serialize(JoinRequestEvent obj) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);

            output.close();
            return byteArrayOutputStream.toByteArray();

    }

    private static JoinRequestEvent deserialize(byte[] data) {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = new Kryo();
            JoinRequestEvent obj = kryo.readObject(input, JoinRequestEvent.class);
            input.close();
            return obj;
    }
}