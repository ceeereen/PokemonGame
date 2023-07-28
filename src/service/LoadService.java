package service;

import model.*;
import model.Character;

import java.util.ArrayList;

public class LoadService {
    public ArrayList<model.Character> loadCharacters() {
        SpecialPower strategy1 = new Strategy("Strategy", 4, 1);
        SpecialPower strategy2 = new Strategy("Strategy II", 3, 1);

        model.Character ash = new Ash("Ash", strategy1);
        model.Character ceren = new Brock("Brock", strategy2);

        ArrayList<Character> characterList = new ArrayList<>();
        characterList.add(ash);
        characterList.add(ceren);


        return characterList;
    }

    public ArrayList<Pokemon> loadPokemons() {
        SpecialPower electricty = new Electricty("Electricty", 3, 3);
        SpecialPower water = new Water("Water", 1, 3);
        SpecialPower fire = new Fire("Fire", 5, 3);
        SpecialPower earth = new Earth("Earth", 4, 3);

        Pokemon pokemon1 = new Pikachu("Pikachu", 100, 10, TypeEnum.ELECTRIC, electricty);
        Pokemon pokemon2 = new Squertel("Squirtle", 100, 8, TypeEnum.WATER, water);
        Pokemon pokemon3 = new Charmender("Charmender", 100, 12, TypeEnum.FIRE, fire);
        Pokemon pokemon4 = new Balbazar("Balbausar", 100, 7, TypeEnum.EARTH, earth);

        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(pokemon1);
        pokemonList.add(pokemon2);
        pokemonList.add(pokemon3);
        pokemonList.add(pokemon4);


        return pokemonList;
    }

    /*public ArrayList addPokemontoCharacterbyName(ArrayList<Pokemon> pokemonList, String name) {

        Character character = new Character();


        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equals(name)) {
                if (character.getPokemonList() != null) {
                    character.getPokemonList().add(pokemon);
                } else {
                    ArrayList<Pokemon> charPokemonList = new ArrayList<>();
                    charPokemonList.add(pokemon);
                    character.setPokemonList(charPokemonList);
                }
                return character.getPokemonList();
            } else {
                return null;
            }
        }
        return null;
    }*/


}
