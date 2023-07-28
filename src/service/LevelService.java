package service;

import model.Player;
import model.Pokemon;

import java.util.List;
import java.util.Scanner;

public class LevelService {
    Scanner scanner = new Scanner(System.in);
    GameService gameService = new GameService();
    PokemonService pokemonService = new PokemonService();

    Pokemon winnerPlayerPokemon;

    //winner of level 1 choose pokemon
    public Pokemon selectPokemon(Player player) {
        //if player  has more than 1 pokemon
        if (player.getCharacter().getPokemonList().size() > 1) {
            int index = 1;
            int selectPokemon;
            System.out.println("Your pokemons: ");
            for (Pokemon pokemon : player.getCharacter().getPokemonList()) {
                System.out.println(index + ". " + pokemon.getName());
                index++;
            }
            System.out.println("Enter the number of the pokemon that you want to choose. ");
            selectPokemon = scanner.nextInt();
            //return the selected pokemon
            return player.getCharacter().getPokemonList().get(selectPokemon - 1);
        } else {
            //player has only 1 pokemon so it will return
            return player.getCharacter().getPokemonList().get(0);
        }
    }

    public void attack(Player attacker, Player defender, boolean isPokeSpecialAttack, boolean isCharSpecialAttack) {
        Pokemon secondLevelAttackingPokemon;
        Pokemon secondLevelDefendingPokemon;
        //attacker is level 1 winner
        if (attacker.isWinner()) {
            secondLevelDefendingPokemon = defender.getCharacter().getPokemonList().get(0);
            secondLevelAttackingPokemon = winnerPlayerPokemon;
            //if it will be zero, change to the next pokemon
            if (secondLevelAttackingPokemon.getHealth() - defender.getCharacter().getPokemonList().get(0).getDamage() <= 0) {
                secondLevelAttackingPokemon = attacker.getCharacter().getPokemonList().get(1);
                winnerPlayerPokemon = secondLevelAttackingPokemon;
            }

        } else {
            secondLevelDefendingPokemon = winnerPlayerPokemon;
            //if it will be zero, change to the next pokemon
            if (secondLevelDefendingPokemon.getHealth() - attacker.getCharacter().getPokemonList().get(0).getDamage() <= 0) {
                secondLevelDefendingPokemon = defender.getCharacter().getPokemonList().get(1);
                winnerPlayerPokemon = secondLevelDefendingPokemon;
            }
            secondLevelAttackingPokemon = attacker.getCharacter().getPokemonList().get(0);
        }


        boolean specialAttack = false;
        if (isPokeSpecialAttack && isCharSpecialAttack) {
            specialAttack = secondLevelAttackingPokemon.getSpecialPower().getRemainingRight() > 0
                    && attacker.getCharacter().getSpecialPower().getRemainingRight() > 0;
        } else if (isPokeSpecialAttack) {
            specialAttack = secondLevelAttackingPokemon.getSpecialPower().getRemainingRight() > 0;
        } else if (isCharSpecialAttack) {
            specialAttack = attacker.getCharacter().getSpecialPower().getRemainingRight() > 0;
        }
        int charRemainingRight = attacker.getCharacter().getSpecialPower().getRemainingRight();
        int damage = 0;
        if (specialAttack) {
            if (isPokeSpecialAttack && isCharSpecialAttack) {
                damage = secondLevelAttackingPokemon.specialAttack();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamege();
                attacker.getCharacter().getSpecialPower().setRemainingRight(charRemainingRight - 1);
            } else if (isPokeSpecialAttack) {
                damage = secondLevelAttackingPokemon.specialAttack();
            } else {
                damage = secondLevelAttackingPokemon.getDamage();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamege();
                attacker.getCharacter().getSpecialPower().setRemainingRight(charRemainingRight - 1);
            }
        } else {
            if (isPokeSpecialAttack || isCharSpecialAttack) {
                damage = 0;
            } else {
                damage = secondLevelAttackingPokemon.getDamage();
            }
        }
        secondLevelDefendingPokemon.setHealth(secondLevelDefendingPokemon.getHealth() - damage);

    }


    public void combat(Player attacker, Player defender) {
        //winner chose its pokemon
        System.out.println("Winner will choose pokemon");
        if (attacker.isWinner()) {
            winnerPlayerPokemon = selectPokemon(attacker);
        } else {
            winnerPlayerPokemon = selectPokemon(defender);
        }
        //weakest pokemon is added to loser
        pokemonService.addPokemontoLoser(attacker, defender);
        // first attack.

        // first attack. Decrease damage of attacking power if weather supports it.
        gameService.decreaseDamageAccordingToWeather(attacker);
        attack(attacker, defender, gameService.useSpecialPowerofCharacter(), gameService.useSpecialPowerofPokemon());
        System.out.println(defender.getName() + " attacks and its health is " + printWinnerPokemon(defender).getHealth());
        System.out.println("Health of " + attacker.getName() + " after attack is " + printWinnerPokemon(attacker).getHealth());
        // defender attacks now
        combatAfterFirstAttack(attacker, defender);
        // result of winner
        if (gameService.healthCheck(attacker)) {
            System.out.println(attacker.getName() + " won the game!");
            attacker.setWinner(true);
            //winner takes the pokemon of loser with fulled health.
            defender.getCharacter().getPokemonList().get(0).setHealth(100);
            attacker.getCharacter().getPokemonList().add(defender.getCharacter().getPokemonList().get(0));
            defender.getCharacter().getPokemonList().remove(0);
        } else if (gameService.healthCheck(defender)) {
            System.out.println(defender.getName() + " won the game!");
            //winner takes the pokemon of loser with fulled health.
            defender.setWinner(true);
            attacker.getCharacter().getPokemonList().get(0).setHealth(100);
            defender.getCharacter().getPokemonList().add(attacker.getCharacter().getPokemonList().get(0));
            attacker.getCharacter().getPokemonList().remove(0);
        } else {
            System.out.println("The game ended in a draw!");
        }
    }

    //used for after first attack. This method created since recursive only needed for recursive attacks.
    public void combatAfterFirstAttack(Player attacker, Player defender) {
        //if player attacks with previous pokemon and if its health <= 0
        if (gameService.healthCheck(defender)) {
            System.out.println(defender.getName() + " will attack");
            //Decrease damage of attacking power if weather supports it.
            gameService.decreaseDamageAccordingToWeather(attacker);
            attack(defender, attacker, false, false);
            System.out.println(defender.getName() + " attacks and its health is " + printWinnerPokemon(defender).getHealth());
            System.out.println("Health of " + attacker.getName() + " after attack is " + printWinnerPokemon(attacker).getHealth());
            combatAfterFirstAttack(defender, attacker);
        }

    }
    //in order to print winnerpokemon info.
    public Pokemon printWinnerPokemon(Player player) {
        if (player.isWinner()) {
            for (Pokemon pokemon : player.getCharacter().getPokemonList()) {
                if (pokemon.equals(winnerPlayerPokemon)) {
                    return pokemon;
                }else{
                    return winnerPlayerPokemon;
                }
            }
        } else {
            return player.getCharacter().getPokemonList().get(0);
        }
        return null;
    }


}
