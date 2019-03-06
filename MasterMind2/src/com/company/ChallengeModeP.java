package com.company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

public class ChallengeModeP extends PlusouMoins {

    final Logger logger = LogManager.getLogger();



    ChallengeModeP(int nbCase, int nbTurn, int devMode) {
        super(nbCase, nbTurn, devMode);
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

        setCodeToBeFound(randomCodeGenerator());

        StringBuilder strNum = new StringBuilder();

        for (int num : getCodeToBeFound()) {
            strNum.append(num);
        }
        secret = Integer.parseInt(strNum.toString());

        if (getDevMode() == 1) {
            logger.debug("secret = " + secret);
        }


        secretCode = secret.toString();
        secretString.add(secretCode);


    }


    @Override
    public void History() {

    }




    @Override
    public void play() {


        for (turn = 0; turn < getNbTurn(); turn++) {


            System.out.println("Essai n°(" + (turn + 1) + "/" + getNbTurn() + "):   ");
            String attempt = sc.next();

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
                endGame();
            } else {

                while (scale != getNbCase()) {    //loop that decomposes, analyzes and returns the response of each attempt


                    reponse = "";


                    int[] tab = filltab(aAttempt);
                    String tmp = "";
                    for (int i = secretString.size() - 1; i >= 0; i--)
                        tmp += secretString.get(i);


                    int[] sol = filltab(Integer.parseInt(tmp));

                    for (int i = 0; i < getNbCase(); i++) {
                        if(getDevMode()==1){
                        System.out.println("[DevMode]   Proposition: "+tab[i] + " -> Secret:" + sol[i]);}
                        if (tab[i] == sol[i]) {
                            r += "=";
                        } else if (tab[i] > sol[i]) {
                            r += "-";
                        } else if (tab[i] < sol[i]) {
                            r += "+";
                        }
                        reponse += r;

                        r = "";

                    }
                    System.out.println("Proposition " + attempt + "-> Réponse : " + reponse);
                    scale = scale + 1;
                    break;
                }
            }

            if (turn == getNbTurn()) {
                System.out.println("Quel dommage n'avez pas trouvé le code secret ! La bonne réponse était : " + secret);
            }
        }

    }
}