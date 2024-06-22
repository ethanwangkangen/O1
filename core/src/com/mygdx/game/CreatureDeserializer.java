package com.mygdx.game;

import com.mygdx.game.entities.BadLogic;
import com.mygdx.game.entities.Creature;
import com.mygdx.game.entities.CrocLesnar;
import com.mygdx.game.entities.Froggy;
import com.mygdx.game.entities.MeowmadAli;

import java.util.Map;

public class CreatureDeserializer {
    public static Creature deserialize(Map<String, Object> data) {
        String type = (String) data.get("type");
        Creature creature = null;

        switch (type) {
            case "MeowmadAli":
                creature = new MeowmadAli();
                break;
            case "CrocLesnar":
                creature = new CrocLesnar();
                break;
            case "Froggy":
                creature = new Froggy();
                break;
            case "BadLogic":
                creature = new BadLogic();
                break;
        }

        if (creature != null) {
            // Set other properties of creature from data
        }

        return creature;
    }
}
