package com.mygdx.global;

public class ChangePetEvent {
    public ChangePetEvent() {
    }

    public Pet pet;
    public String id;

    public enum Pet {
        PET1,
        PET2,
        PET3
    }


}
