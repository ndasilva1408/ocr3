package com.company;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

public class ChallengeModeM extends MasterMind {

    final Logger logger = LogManager.getLogger();


    ChallengeModeM(int nbCase, int nbTurn, int nbColor, int devMode) {
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


        System.out.println("Bienvenue dans le mode de jeu challenge , dans ce mode venez découvrir la combinaison secrète du Maître du jeu !");

        if (getDevMode() == 1) {
            System.out.println("[DevMode]");
            logger.debug("Nb Tour " + getNbTurn() + " nbCase " + getNbCase() + " nbColor" + getNbColor());
        }


        setCodeToBeFound(randomCodeGenerator());

        StringBuilder strNum = new StringBuilder();

        for (int num : getCodeToBeFound()) {
            strNum.append(num);                     //
        }
        secret = Integer.parseInt(strNum.toString());

        if (getDevMode() == 1) {
            System.out.println("[DevMode]");
            logger.debug("secret = " + secret);
        }


        secretCode = secret.toString();


    }


    @Override
    public void play() {


        for (turn = 0; turn < getNbTurn(); turn++) {
            System.out.println("Essai n°(" + (turn + 1) + " /" + getNbTurn() + "):   ");
            String attempt = sc2.next();
            aAttempt=Integer.parseInt(attempt);

                if (attempt.length() != getNbCase()) {
                    logger.error("Votre tentative doit faire " + getNbCase() + " chiffre de longueur.");
                    attempt="";

                    for(i=0;i<getNbCase();i++){
                        attempt+=1;
                    }
                    aAttempt=Integer.parseInt(attempt);

                    System.out.println("Si vous vous trompez votre essai deviens: "+aAttempt);

                }





            analyseAttempt.add(attempt);

            boolean flag = attempt.equals(this.secretCode);

            if (flag) {
                System.out.println("Bravo tu as découvert le code secret du Maître du jeu en seulement " + (turn + 1) + " essais !");
                this.endGame();
            } else {

                while (scale != getNbCase()) {    //loop that decomposes, analyzes and returns the response of each attempt


                    int tab[] = filltab(aAttempt);
                    setCodeProposal(tab);
                    String tmp = "";
                    for (int i = secretString.size() - 1; i >= 0; i--)
                        tmp += secretString.get(i);


                    validation(getCodeToBeFound(), getCodeProposal());

                    System.out.println(getValidation()[1] + " présents " + getValidation()[0] + " bien placés.");

                    analyse[0] = 0;
                    analyse[1] = 0;
                    break;

                }
            }

        }
        if (turn == getNbTurn()) {
            System.out.println("Quel dommage n'avez pas trouvé le code secret ! La bonne réponse était : " + secret);

        }

    }
}