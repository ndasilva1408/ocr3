package com.company;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resources.NbColorException;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class GameLoop {


    final Logger logger = LogManager.getLogger();
    PlusouMoins gameP;
    MasterMind gameM;

    Game game;
    String gameType;



    GameLoop() {
    }



    public  Game runGame() {

        this.selectGame();

        switch (gameType) {
            case ("MasterMind"):
                this.selectModeM();
                game=gameM;
                gameM.SecretString();
                gameM.play();

            case ("Plus ou Moins"):
                this.selectModeP();
                game=gameP;
                gameP.SecretString();
                gameP.play();

        }


        return game;
    }


    public Game selectGame() {
        System.out.println("Veuillez choisir votre type de partie : 1-Master Mind, 2-Plus ou Moins, ");

        Scanner sc = new Scanner(System.in);
        try {
            int val = sc.nextInt();


            if (val == 1) {
                System.out.println("Vous avez choisis le mode Mastermind!");
                gameType = "MasterMind";
                return game = new   MasterMind(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getNbColor(),getProperties().getDevMode());

            } else if (val == 2) {
                System.out.println("Vous avez choisis le mode Plus ou Moins!");
                gameType = "Plus ou Moins";
                return game = new PlusouMoins(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getDevMode());


            } else {
               logger.error("Valeur incorrecte ");
                return selectGame();
            }
        } catch (InputMismatchException e) {
          logger.error("Veuillez n'entrer que des chiffres. ");
            return selectGame();
        }

    }

    MasterMind selectModeM() {
        logger.info("Veuillez choisir votre type de partie : 1-Defense, 2-Challenge, 3-Duel");

        Scanner sc = new Scanner(System.in);
        try {
            int val = sc.nextInt();


            if (val == 1) {
                System.out.println("Vous avez choisis le mode defense!");
                return gameM = new DefenseModeM(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getNbColor(),getProperties().getDevMode());
            } else if (val == 2) {
                System.out.println("Vous avez choisis le mode challenge!");
                return gameM = new ChallengeModeM(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getNbColor(),getProperties().getDevMode());
            } else if (val == 3) {
                System.out.println("Vous avez choisis le mode duel!");
                return gameM = new VersusModeM(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getNbColor(),getProperties().getDevMode());

            } else {
               logger.error("Valeur incorrecte ");
                return selectModeM();
            }
        } catch (InputMismatchException e) {
           logger.error("Veuillez n'entrer que des chiffres. ");
            return selectModeM();
        }


    }


    PlusouMoins selectModeP() {
        System.out.println("Veuillez choisir votre type de partie : 1-Defense, 2-Challenge, 3-Duel");

        Scanner sc = new Scanner(System.in);
        try {
            int val = sc.nextInt();


            if (val == 1) {
                System.out.println("Vous avez choisis le mode defense!");
                return gameP = new DefenseModeP(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getDevMode());
            } else if (val == 2) {
                System.out.println("Vous avez choisis le mode challenge!");
                return gameP = new ChallengeModeP(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getDevMode());
            } else if (val == 3) {
                System.out.println("Vous avez choisis le mode duel!");
                return gameP = new VersusModeP(getProperties().getNbCase(),getProperties().getNbTurn(),getProperties().getDevMode());

            } else {
               logger.error("Valeur incorrecte ");
                return selectModeP();
            }
        } catch (InputMismatchException e) {
           logger.error("Veuillez n'entrer que des chiffres. ");
            return selectModeP();
        }


    }



    public Game getProperties() {

        Properties prop = new Properties();
        String resources = "resources/config.properties";

        int nbColor2=4;
        int nbTurn2 =10;
        int nbCase2=4;
        int devMode2=0 ;
        Game gameconf= new Game(nbTurn2,nbCase2,nbColor2,devMode2);



        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(resources);

            if (inputStream == null)
                System.out.println("le stream n'as pas été charger");

            prop.load(inputStream);


            String nbColor = prop.getProperty("nbColor");
            String nbTurn = prop.getProperty("nbTurn");
            String nbCase = prop.getProperty("nbCase");
            String devMode = prop.getProperty("devMode");

            nbColor2 = Integer.parseInt(nbColor);
            nbTurn2 = Integer.parseInt(nbTurn);
            nbCase2 = Integer.parseInt(nbCase);
            devMode2 = Integer.parseInt(devMode);


            if (nbColor2 < 4 || nbColor2 > 11){
                logger.error("Désolé nbColor doit être compris entre 4 et 10!");
                throw new NbColorException();}

            if (nbTurn2 < 1 || nbCase2 < 1)
                logger.error("Désolé nbTurn ne peut être nul.");
            if (devMode2 > 1)  // 0 ou 1
                logger.error("Désolé devMode n'a que deux valeurs possible. -1 pour ON -0 pour OFF ");


gameconf.setNbCase(nbCase2);
gameconf.setNbColor(nbColor2);
gameconf.setNbTurn(nbTurn2);
gameconf.setDevMode(devMode2);




        } catch (Exception e) {
        }
    return gameconf;
    }}
