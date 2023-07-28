package service;

import model.Player;
import model.Pokemon;

import java.util.ArrayList;

public class PokemonService {
    LoadService loadService = new LoadService();

    public Pokemon selectWeakestPokemon(Player player1, Player player2) {

        //list of pokemons which dont belong to players
        ArrayList<Pokemon> pokemonList = returnPokemonList(player1, player2);
        //deafault
        Pokemon weakestPokemon = pokemonList.get(0);

        for (int i = 1; i < pokemonList.size(); i++) {
            Pokemon currentPokemon = pokemonList.get(i);
            if (currentPokemon.getDamage() < weakestPokemon.getDamage()) {
                // update weakest pokemon
                weakestPokemon = currentPokemon;
            }
        }
        return weakestPokemon;
    }
    //returns pokemonlist that is exlusive for players
    public ArrayList returnPokemonList(Player player1, Player player2) {

        ArrayList<Pokemon> pokemonList = loadService.loadPokemons();
        ArrayList<Pokemon> player1PokemonList = new ArrayList<>();
        ArrayList<Pokemon> player2PokemonList = new ArrayList<>();

        if (!(player1.getCharacter().getPokemonList().isEmpty()) && player1.getCharacter().getPokemonList() != null) {
            player1PokemonList = player1.getCharacter().getPokemonList();
        }
        if (!(player2.getCharacter().getPokemonList().isEmpty()) && player2.getCharacter().getPokemonList() != null) {
            player2PokemonList = player2.getCharacter().getPokemonList();
        }

        pokemonList.removeAll(player1PokemonList);
        pokemonList.removeAll(player2PokemonList);

        return pokemonList;
    }

    //loser of level 1 gets weakest pokemon
    public void addPokemontoLoser(Player player1, Player player2) {
        //player1 loser
        if (!player1.isWinner()) {
            player1.getCharacter().getPokemonList().add(selectWeakestPokemon(player1, player2));
        } else {
            //player2 loser
            player2.getCharacter().getPokemonList().add(selectWeakestPokemon(player1, player2));
        }
    }





}
