import model.Character;
import model.Player;
import model.Pokemon;
import service.GameService;
import service.LevelService;
import service.LoadService;
import service.PlayerService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoadService loadService = new LoadService();
        PlayerService playerService = new PlayerService();
        ArrayList<Character> characterList = loadService.loadCharacters();
        ArrayList<Pokemon> pokemonList = loadService.loadPokemons();
        GameService gameService = new GameService();
        LevelService levelService = new LevelService();


        Player player1 = new Player("Ceren");
        Player player2 = new Player("Murat");

        while (true) {
            System.out.println("Welcome to Pokemon!");
            int selectGameStatus;
            System.out.println("Type 1 for to start the game: ");
            System.out.println("Type 2 for to finish the game");
            selectGameStatus = scanner.nextInt();
            // start the game
            if (selectGameStatus == 1) {
                System.out.println("Character List:");
                int index = 1;
                for (Character character : characterList) {
                    System.out.println(index + ". " + character); // Sıra numarasını yazdırma
                    index++;
                }
                int selectChar;
                //returns the number of player to choose character first
                if (playerService.selectPlayer() == 1) {
                    //player1 choose first
                    System.out.println(player1.getName() + " chooses character first.");
                    System.out.println("Type the number of character that you want to choose.");
                    selectChar = scanner.nextInt();
                    player1 = playerService.createPlayer(player1.getName(), characterList.get(selectChar - 1));
                    characterList.remove(selectChar - 1);
                    player2 = playerService.createPlayer(player2.getName(), characterList.get(0));
                } else {
                    //player2 choose first
                    System.out.println(player2.getName() + " chooses character first.");
                    System.out.println("Type the number of character that you want to choose.");
                    selectChar = scanner.nextInt();
                    player2 = playerService.createPlayer(player2.getName(), characterList.get(selectChar - 1));
                    characterList.remove(selectChar - 1);
                    player1 = playerService.createPlayer(player1.getName(), characterList.get(0));
                }
                int pokemonIndex = 1;
                System.out.println("Pokemon List:");
                for (Pokemon pokemon : pokemonList) {
                    System.out.println(pokemonIndex + ". " + pokemon); // Sıra numarasını yazdırma
                    pokemonIndex++;
                }
                int selectPokemon;
                if (playerService.selectPlayer() == 1) {
                    //player1 choose first
                    System.out.println(player1.getName() + " chooses pokemon first.");
                    System.out.println("Type the number of pokemon that you want to choose.");
                    selectPokemon = scanner.nextInt();
                    player1.getCharacter().getPokemonList().add(pokemonList.get(selectPokemon - 1));
                    pokemonList.remove(selectPokemon - 1);
                    System.out.println(player2.getName() + " chooses now.");
                    System.out.println("Pokemon List:");
                    int secondaryPokemonindex = 1;
                    for (Pokemon pokemon : pokemonList) {
                        System.out.println(secondaryPokemonindex + ". " + pokemon); // Sıra numarasını yazdırma
                        secondaryPokemonindex++;
                    }
                    System.out.println("Type the number of pokemon that you want to choose.");
                    selectPokemon = scanner.nextInt();
                    player2.getCharacter().getPokemonList().add(pokemonList.get(selectPokemon - 1));
                    pokemonList.remove(pokemonList.get(selectPokemon - 1));

                } else {
                    //player2 choose first
                    System.out.println(player2.getName() + " chooses pokemon first.");
                    System.out.println("Type the number of pokemon that you want to choose.");
                    selectPokemon = scanner.nextInt();
                    player2.getCharacter().getPokemonList().add(pokemonList.get(selectPokemon - 1));
                    pokemonList.remove(selectPokemon - 1);
                    System.out.println(player1.getName() + " chooses now.");
                    System.out.println("Pokemon List:");
                    int secondaryPokemonindex = 1;
                    for (Pokemon pokemon : pokemonList) {
                        System.out.println(secondaryPokemonindex + ". " + pokemon); // row number
                        secondaryPokemonindex++;
                    }
                    System.out.println("Type the number of pokemon that you want to choose.");
                    selectPokemon = scanner.nextInt();
                    player1.getCharacter().getPokemonList().add(pokemonList.get(selectPokemon - 1));
                }
                System.out.println("*******");
                System.out.println(player1.toString());
                System.out.println(player2.toString());
                //decides who attacks first
                if(playerService.selectPlayer()==1){
                    System.out.println(player1.getName()+" will attack first.");
                    gameService.combat(player1,player2);
                }else{
                    System.out.println(player2.getName() + " will attack first.");
                    gameService.combat(player2,player1);
                }
                System.out.println("*******");
                System.out.println(player1.getCharacter().getPokemonList());
                System.out.println(player2.getCharacter().getPokemonList());
                //level 2 combat starts
                //loser aattacks first
                if(player1.isWinner()){
                    System.out.println("Level 2 starts.");
                    levelService.combat(player2,player1);
                }else{
                    System.out.println("Level 2 starts.");
                    levelService.combat(player1,player2);
                }
                System.out.println("*******");
                System.out.println(player1.getCharacter().getPokemonList());
                System.out.println(player2.getCharacter().getPokemonList());
                break;
            //end the game
            } else {
                break;
            }

        }

    }


}