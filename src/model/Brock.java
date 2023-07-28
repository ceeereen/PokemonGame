package model;

import java.util.ArrayList;

public class Brock extends Character{
    public Brock(String name, SpecialPower specialPower, ArrayList<Pokemon> pokemonList) {
        super(name, specialPower, pokemonList);
    }

    public Brock(String name, SpecialPower specialPower){
        super(name,specialPower);
    }
}
