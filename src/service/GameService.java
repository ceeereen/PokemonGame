package service;

import model.Player;
import model.Pokemon;
import model.WeatherEnum;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameService {

    LoadService loadService = new LoadService();
    Scanner scanner = new Scanner(System.in);


    public void attack(Player attacker, Player defender, boolean isPokeSpecialAttack, boolean isCharSpecialAttack) {
        Pokemon attackingPokemon = attacker.getCharacter().getPokemonList().get(0);
        Pokemon defendingPokemon = defender.getCharacter().getPokemonList().get(0);


        boolean specialAttack = false;
        if (isPokeSpecialAttack && isCharSpecialAttack) {
            specialAttack = attackingPokemon.getSpecialPower().getRemainingRight() > 0
                    && attacker.getCharacter().getSpecialPower().getRemainingRight() > 0;
        } else if (isPokeSpecialAttack) {
            specialAttack = attackingPokemon.getSpecialPower().getRemainingRight() > 0;
        } else if (isCharSpecialAttack) {
            specialAttack = attacker.getCharacter().getSpecialPower().getRemainingRight() > 0;
        }
        int charRemainingRight = attacker.getCharacter().getSpecialPower().getRemainingRight();
        int damage = 0;

        //level 1
        if (specialAttack) {
            if (isPokeSpecialAttack && isCharSpecialAttack) {
                damage = attackingPokemon.specialAttack();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamege();
                attacker.getCharacter().getSpecialPower().setRemainingRight(charRemainingRight - 1);
            } else if (isPokeSpecialAttack) {
                damage = attackingPokemon.specialAttack();
            } else {
                damage = attackingPokemon.getDamage();
                damage += attacker.getCharacter().getSpecialPower().getExtraDamege();
                attacker.getCharacter().getSpecialPower().setRemainingRight(charRemainingRight - 1);
            }
        } else {
            if (isPokeSpecialAttack || isCharSpecialAttack) {
                damage = 0;
            } else {
                damage = attackingPokemon.getDamage();
            }
        }

        defendingPokemon.setHealth(defendingPokemon.getHealth() - damage);


    }

    //health check for pokemon in order to determine winner
    public Boolean healthCheck(Player player) {
        LevelService levelService = new LevelService();

        if (player.getCharacter().getPokemonList() != null && !player.getCharacter().getPokemonList().isEmpty()) {
            if (!(player.isWinner())) {
                if (player.getCharacter().getPokemonList().get(0).getHealth() > 0) {
                    System.out.println("Game continues.");
                    return true;
                } else {
                    System.out.println(player.toString());
                    System.out.println(player.getName() + " lost the game");
                    return false;
                }
            } else {
                if (player.getCharacter().getPokemonList().get(1).getHealth() > 0) {
                    System.out.println("Game continues.");
                    return true;
                } else {
                    System.out.println(player.toString());
                    System.out.println(player.getName() + " lost the game");
                    return false;
                }
            }
        } else {
            System.out.println("Pokemon isn't assigned.");
        }
        return null;
    }

    //starts the combat
    public void combat(Player attacker, Player defender) {
        // first attack. Decrease damage of attacking power if weather supports it.
        decreaseDamageAccordingToWeather(attacker);
        attack(attacker, defender, false, false);
        System.out.println(attacker.getName() + " attacks and its health is " + attacker.getCharacter().getPokemonList().get(0).getHealth());
        System.out.println("Health of " + defender.getName() + " after attack is " + defender.getCharacter().getPokemonList().get(0).getHealth());
        // defender attacks now
        combatAfterFirstAttack(attacker, defender);
        // winner of result
        if (healthCheck(attacker)) {
            System.out.println(attacker.getName() + " won the game!");
            attacker.setWinner(true);
            //winner takes the pokemon of loser with fulled health.
            defender.getCharacter().getPokemonList().get(0).setHealth(100);
            attacker.getCharacter().getPokemonList().add(defender.getCharacter().getPokemonList().get(0));
            defender.getCharacter().getPokemonList().remove(0);
        } else if (healthCheck(defender)) {
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
        if (healthCheck(defender)) {
            System.out.println(defender.getName() + " will attack");
            //Decrease damage of attacking power if weather supports it.
            decreaseDamageAccordingToWeather(attacker);
            attack(defender, attacker, false, false);
            System.out.println(defender.getName() + " attacks and its health is " + defender.getCharacter().getPokemonList().get(0).getHealth());
            System.out.println("Health of " + attacker.getName() + " after attack is " + attacker.getCharacter().getPokemonList().get(0).getHealth());
            combatAfterFirstAttack(defender, attacker);

        }
    }

    //return boolean value in order to use in attack() function
    public boolean useSpecialPowerofCharacter() {
        int choice;
        boolean isUseable;
        System.out.println("Do you want to use special power of Character ? ");
        System.out.println("Press 1 to use CHARACTER'S special power");
        System.out.println("Press 0 to NOT USE special power");

        choice = scanner.nextInt();
        if (choice == 1) {
            return true;
        } else {
            return false;
        }
    }

    //return boolean value in order to use in attack() function
    public boolean useSpecialPowerofPokemon() {
        int choice;
        boolean isUseable;
        System.out.println("Do you want to use special power of Pokemon ? ");
        System.out.println("Press 1 to use POKEMON'S special power");
        System.out.println("Press 0 to NOT USE special power");

        choice = scanner.nextInt();
        if (choice == 1) {
            return true;
        } else {
            return false;
        }
    }

    //randomly generate weather condition
    public WeatherEnum generateWeatherConditions() {
        Random random = new Random();
        //array of WeatherEnum values
        WeatherEnum[] weatherConditions = WeatherEnum.values();//size 4

        //generate between WeatherConditions length and 0
        int randomNum = random.nextInt(0, weatherConditions.length);
        return weatherConditions[randomNum];
    }

    //decrease damage of attacker
    public void decreaseDamageAccordingToWeather(Player player) {
        //randomly generated weatherCondition type of WeatherEnum
        WeatherEnum weatherCondition = generateWeatherConditions();
        //loop over pokemonList of player in order to find attacking pokemon.
        for (Pokemon pokemon : player.getCharacter().getPokemonList()) {
            //decrease attack of attackingPokemon
            decreaseDamageBasedOnWeather(pokemon, weatherCondition);
        }

    }

    //set decrease amounts according to weather
    private void decreaseDamageBasedOnWeather(Pokemon pokemon, WeatherEnum weatherCondition) {
        //decrease amount
        int damageDecrease = 2;

        if (weatherCondition.equals(WeatherEnum.HOT)) {
            if (pokemon.getName().equals("Squirtle")) {
                System.out.println("Weather is hot, bad news for " + pokemon.getName());
                decreasePokemonDamage(pokemon, damageDecrease, "Weather is hot as hell.");
            } else {
                //if weather is hot and pokemon isn't Squirtle, damage can not be decreased.
                System.out.println(WeatherEnum.printWeatherCondition(weatherCondition) + ", does not effect " + pokemon.getName());
            }
        } else if (weatherCondition.equals(WeatherEnum.RAINY)) {
            if (pokemon.getName().equals("Pikachu")) {
                System.out.println("Weather is rainy, bad news for " + pokemon.getName());
                decreasePokemonDamage(pokemon, damageDecrease, "Rain starts. Bad news for Pikachu.");
            } else {
                //if weather is rainy and pokemon isn't Pikachu, damage can not be decreased.
                System.out.println(WeatherEnum.printWeatherCondition(weatherCondition) + ", does not effect " + pokemon.getName());
            }
        } else if (weatherCondition.equals(WeatherEnum.SNOWY)) {
            if (pokemon.getName().equals("Charmender")) {
                System.out.println("Weather is snowy, bad news for " + pokemon.getName());
                decreasePokemonDamage(pokemon, damageDecrease, "Snow started.");
            } else {
                //if weather is snowy and pokemon isn't Charmender, damage can not be decreased.
                System.out.println(WeatherEnum.printWeatherCondition(weatherCondition) + ", does not effect " + pokemon.getName());
            }
        } else {
            if (pokemon.getName().equals("Balbausar")) {
                System.out.println("Weather is stormy, bad news for " + pokemon.getName());
                decreasePokemonDamage(pokemon, damageDecrease, "Storm started.");
            } else {
                //if weather is snowy and pokemon isn't Balbausar, damage can not be decreased.
                System.out.println(WeatherEnum.printWeatherCondition(weatherCondition) + ", does not effect " + pokemon.getName());
            }
        }
    }

    private void decreasePokemonDamage(Pokemon pokemon, int damageDecrease, String message) {
        //if pokemon's damage is under the level of damageDecrease amount set its damage, damageDecrease rate. And dont change.
        if (pokemon.getDamage() <= damageDecrease) {
            pokemon.setDamage(damageDecrease);
            System.out.println("Damage of " + pokemon.getName() + " is at minimum: " + damageDecrease);
            //continue to decreasing damage since pokemon's damage is above level.
        } else {
            System.out.println("Pre-damage: " + pokemon.getDamage());
            pokemon.setDamage(pokemon.getDamage() - damageDecrease);
            System.out.println("After: " + pokemon.getDamage());
            System.out.println(pokemon.getName() + "'s damage is decreased " + damageDecrease + " point since weather conditions.");
        }
    }


}

