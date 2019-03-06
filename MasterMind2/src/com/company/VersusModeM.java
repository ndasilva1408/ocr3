package com.company;

import java.util.Arrays;
import java.util.InputMismatchException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class VersusModeM extends MasterMind {


    VersusModeM(int nbCase, int nbTurn, int nbColor, int devMode) {
        super(nbCase, nbTurn, nbColor, devMode);
    }

    final Logger logger = LogManager.getLogger();

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
        /**Player's part
         *
         * @param secret2
         * @param secretCode2
         * @param secretString2
         */
        System.out.println("Bienvenue dans le mode duel ! Trouvez le code secret de l'ordinateur avant qu'il ne trouve le vôtre !!");
        System.out.println("Joueur à vous de determiner votre code secret !");

        secretCode2 = sc.next();
        secret2 = Integer.valueOf(secretCode2);

        while (secretCode2.length() != getNbCase()) {
           logger.error("Le code secret doit être de " + getNbCase() + " chiffres !");
            secretCode2 = sc.next();
            secret2 = Integer.valueOf(secretCode2);

        }


        for (scale = -1; scale == getNbCase(); scale++) {

            secretString2.add(i + 1, (secretCode2.substring(scale - 1, scale)));
            scale = scale + 1;


        }
        if (getDevMode() == 1) {
           logger.debug("[DevMode] Code secret du joueur = " + secret2);

        }


        /**
         * Computer's Part
         *
         * @param secret
         * @param secretCode
         * @param secretString
         *
         */
        System.out.println("Ordinateur , c'est à vous.");
        do {
            setCodeToBeFound(randomCodeGenerator());


            StringBuilder strNum = new StringBuilder();

            for (int num : getCodeToBeFound()) {
                strNum.append(num);
            }
            secret = Integer.parseInt(strNum.toString());
        }
        while (secret.longValue() == getNbCase());

        if (getDevMode() == 1) {
            logger.debug("[DevMode] Code secret de l'ordinateur = " + secret);
        }


        secretCode = secret.toString();
        secretString.add(secretCode);


    }


    @Override
    public void play() {
        for (turn = 0; turn < getNbTurn(); turn++) {
        /**
         * Player's Part
         *
         */
            System.out.println("Essai du joueur n°(" + (turn + 1) + " /" + getNbTurn() + "):   ");

            aAttempt = sc.nextInt();

            String attempt = aAttempt.toString();

            analyseAttempt.add(attempt);

            boolean flag = attempt.equals(secretCode);

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
                    analyse[0]=0;
                    analyse[1]=0;


                    break;
                }
            }



        /**
         * Computer's Part
         *
         *
         */

            System.out.println("Tour de l'ordi");

           setCodeToBeFound(filltab(secret2));

            creationCombinationList();

             logger.debug("Combination lenght " + combinationList.size());

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
            }

    }}

