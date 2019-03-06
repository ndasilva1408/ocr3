package com.company;

import java.util.Scanner;

public class Game {
    private int nbCase;
    private int[] codeToBeFound = new int[nbCase];
    private int[] codeProposal;
    private int nbColor;
    private int devMode;
    private int nbTurn;


    // _____________________________________________________________________________________________________________________
    //CONSTRUCTOR//


    /**
     * Constructor for game 1
     *
     * @param nbCase number of digits
     * @param nbTurn max number of guesses allowed
     */
    Game(int nbCase, int nbTurn, int devMode) {
        this.nbCase = nbCase;
        this.nbTurn = nbTurn;
        this.devMode = devMode;
    }


    /**
     * Constructor for game 2
     *
     * @param nbCase  number of digits
     * @param nbTurn  max number of guesses allowed
     * @param nbColor nb of values that each digit can take
     */
    Game(int nbCase, int nbTurn, int nbColor, int devMode) {
        this.nbCase = nbCase;
        this.nbTurn = nbTurn;
        this.nbColor = nbColor;
        this.devMode = devMode;
    }


    public void SecretString() {
    }

    public void History() {
        System.out.println("Voici le récapitulatif de tes essais:");
    }

    public void play() {
    }


    // GETTER SETTER


    public int getNbCase() {
        return nbCase;
    }

    public void setNbCase(int nbCase) {
        this.nbCase = nbCase;
    }

    public int getNbColor() {
        return nbColor;
    }

    public void setNbColor(int nbColor) {
        this.nbColor = nbColor;
    }

    public int getNbTurn() {
        return nbTurn;
    }

    public void setNbTurn(int nbTurn) {
        this.nbTurn = nbTurn;
    }

    public int getDevMode() {
        return devMode;
    }

    public void setDevMode(int devMode) {
        this.devMode = devMode;
    }

    public int[] getCodeToBeFound() {
        return codeToBeFound;
    }

    public void setCodeToBeFound(int[] codeToBeFound) {
        this.codeToBeFound = codeToBeFound;
    }


    public int[] getCodeProposal() {
        return codeProposal;
    }

    public void setCodeProposal(int[] codeProposal) {
        this.codeProposal = codeProposal;
    }


    protected int[] randomCodeGenerator() {                                        //Methode qui genere le code secret de l'ordinateur.
        int[] randomCode = new int[getNbCase()];
        for (int i = 0; i < getNbCase(); i++) {
            randomCode[i] = (int) (getNbColor() * Math.random());
        }
        return randomCode;
    }

    protected void endGame() {
        System.out.println("Partie terminée ! Que souhaitez vous faire ? 1-Rejouer au même jeu. 2-Lancer un autre jeu. 3-Quitter l'application.");
        Scanner sc = new Scanner(System.in);
        int selectorEnding = sc.nextInt();


        while ((selectorEnding != 1 && selectorEnding != 2 && selectorEnding != 3)) {
            System.out.println("Pouvez vous répétez ? Choisissez 1, 2 ou 3: ");
            selectorEnding = sc.nextInt();

        }

        switch (selectorEnding) {
            case 1:
                this.play();
            case 2:
                GameLoop game = new GameLoop();
                game.getProperties();
                game.runGame();
                break;
            case 3:
                System.out.println("Adios !");
                System.exit(0);
        }

    }
    protected int[] filltab(Integer aAttempt) {
        int[] ret = new int[getNbCase()];
        String tmp = aAttempt.toString();
        for (int i = 0; i < getNbCase(); i++) {
            ret[i] = Integer.parseInt(tmp.substring(i, i + 1));
        }

        return ret;


    }




}



