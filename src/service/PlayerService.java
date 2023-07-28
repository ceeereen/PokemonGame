package service;

import model.Character;
import model.Player;

import java.util.Random;

public class PlayerService {
    /*
       private String name;
    private Character character;
    private  boolean isWinner;
     */
    public Player createPlayer(String name, Character character){
        return new Player(name,character);

    }

    public int selectPlayer(){
        Random random = new Random();
        int randomNum = random.nextInt(1,3);
        return randomNum;
    }
}
