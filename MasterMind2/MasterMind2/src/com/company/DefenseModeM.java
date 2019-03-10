package com.company;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefenseModeM extends MasterMind {


    final Logger logger = LogManager.getLogger();


    DefenseModeM(int nbCase, int nbTurn, int nbColor, int devMode) {
        super(nbCase, nbTurn, nbColor, devMode);
    }


    @Override
    public void SecretString() {
        try {
            System.out.println("Voulez vous activer le mode developpeur ? 0- Non 1-Oui (par defaut devmode sera off.)");
            setDevMode(sc.nextInt());

            if (getDevMode() > 1) {
                setDevMode(0);
            }

        } catch (InputMismatchException e) {
            logger.error("Veuillez n'entrer que des chiffres. ");
            setDevMode(0);

        }


        System.out.println("Bienvenue dans le mode de jeu défense,ici l'ordinateur devra découvrir votre code secret en " + getNbTurn() + " essais !");
        System.out.println("Veuillez determiner votre code secret ! Bonne chance");

            secretCode=sc.next();

            if(secretCode.length() != getNbCase()){
            logger.error("Le code secret doit être de " + getNbCase() + " chiffres !");
                secretCode="";

                for(i=0;i<getNbCase();i++){
                    secretCode+=1;
                }
                secret=Integer.parseInt(secretCode);
                System.out.println("Si vous vous trompez votre essai deviens: "+ secretCode);
            }

        secret = Integer.parseInt(secretCode);



        for (scale = -1; scale == getNbCase(); scale++) {

            secretString.add(i + 1, (Integer.toString(secret).substring(scale - 1, scale)));
            scale = scale + 1;


        }
        if (getDevMode() == 1) {
            logger.debug("[DevMode]  Secret =" + secret);
        }

    }

    @Override
    public void play() {

        setCodeToBeFound(filltab(secret));

        creationCombinationList();
        for (turn = 1; turn < getNbTurn() + 1; turn++) {
            if (getDevMode() == 1) {
            logger.debug("[DevMode]  Nombre de combinaison possible restante " + combinationList.size());}

            System.out.println("Tentative " + turn + "/" + getNbTurn() + ":" + Arrays.toString(combinationList.get(0).combination));

            HowManyGoods();
            whosGood();
            removeUselessCombination();

            bplace = 0;
            present = 0;


            if (turn == getNbTurn()) {
                System.out.println("(Victoire ! L'ordinateur n'a pas trouvé votre code secret ! La bonne réponse était : " + secret);
                endGame();
            }

            if (bplace == getNbCase()) {
                System.out.println("Perdu ! L'ordinateur a trouvé votre code secret en " + turn + " tour. Le code était " + secret);
                endGame();
            }
        }
    }


}













