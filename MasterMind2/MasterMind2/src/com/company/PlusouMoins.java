package com.company;


import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlusouMoins extends Game {
    protected String secretCode;
    Scanner sc = new Scanner(System.in);
    Integer turn = 1;
    Integer secret;
    Integer scale = 1;
    Integer i = -1;
    String reponse = "";
    String attempt;
    ArrayList<String> analyseAttempt = new ArrayList<>(getNbCase());
    ArrayList<String> secretString = new ArrayList(getNbCase());
    String p = "";
    String r = "";
    Integer aAttempt = 0;

    ArrayList<String> secretString2 = new ArrayList(getNbCase());
Integer secret2;
    protected String secretCode2;





    PlusouMoins(int nbCase, int nbTurn,int devMode) {
        super(nbCase, nbTurn, devMode);
    }
    @Override
    protected int[] randomCodeGenerator() {

        int[] randomCode = new int[getNbCase()];
        for (int i = 0; i < getNbCase(); i++) {
            randomCode[i] = (int) (10 * Math.random());
        }


        return randomCode;
    }


    public void SecretString() {
    }

    public void History() {
    }



    public void play() {
    }

}
