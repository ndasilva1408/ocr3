package com.company;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

public class VersusModeP extends PlusouMoins {


    VersusModeP(int nbCase, int nbTurn, int devMode) {
        super(nbCase, nbTurn, devMode);
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
        System.out.println("Bienvenue dans le mode duel ! Trouvez le code secret de l'ordinateur avant qu'il ne trouve le vôtre !!");
        System.out.println("Joueur à vous de determiner votre code secret !");


        /**Player's secret code
         * @param secret
         * @param secretCode
         * @param secretString
         */

        secret = sc.nextInt();
        secretCode = secret.toString();
        while (secretCode.length() != getNbCase()){
            logger.error ("Le code secret doit être de " + getNbCase() + " chiffres !");
            secret = sc.nextInt();
            secretCode = secret.toString();
        }



        for (scale = -1; scale == getNbCase(); scale++) {

            secretString.add(i + 1, (Integer.toString(secret).substring(scale - 1, scale)));
            scale = scale + 1;


        }

            if (getDevMode() == 1) {

                logger.debug("[DevMode] Code secret du joueur = " + secret);
            }


        /**Computer's secret code
         * @param secretCode2
         * @param secretString2
         * @param secret2
         */
        System.out.println("Ordinateur , c'est à vous.");
        do {
            setCodeToBeFound(randomCodeGenerator());


            StringBuilder strNum = new StringBuilder();

            for (int num : getCodeToBeFound()) {
                strNum.append(num);
            }
            secret2 = Integer.parseInt(strNum.toString());
        }
        while (secret2.longValue() == getNbCase());

        if (getDevMode() == 1) {
            logger.debug("[DevMode] Code secret de l'ordinateur = " + secret2);
        }


        secretCode2 = secret2.toString();
        secretString2.add(secretCode2);


    }

    @Override
    public void play() {

        int value[] = new int[getNbCase()];
        Integer vmax[] = new Integer[getNbCase()];
        Integer vmin[] = new Integer[getNbCase()];
        for (int i = 0; i < vmax.length; i++) {
            vmax[i] = 9;
        }
        for (int j = 0; j < vmin.length; j++) {
            vmin[j] = 0;
        }


        for (turn = 1; turn < getNbTurn(); turn++) {

            /**
             * Player's turn
             *
             */
            {
                System.out.println("Essai du joueur  n°(" + turn + "/" + getNbTurn() + ") : ");

                attempt = sc.next();
                if (attempt.length() != getNbCase()) {
                    logger.error("L'essai doit-être de " + getNbCase() + " chiffres!");
                    attempt = sc.next();
                }

                if (attempt.equals(this.secretCode2)) {

                    System.out.println("Bravo tu as découvert le code secret du Maître du jeu en seulement " + turn + " essais !");
                    endGame();

                } else {
                    scale = 1;
                    i = 0;

                    while (scale != getNbCase() + 1) {    // boucle qui decompose , analyse et renvoit la reponse de chaque tentative


                        analyseAttempt.add(i, (attempt.substring(scale - 1, scale)));


                        int sString = Integer.parseInt(secretCode2.substring(scale - 1, scale));// On récupère et transforme les donnés decortique  en string  pour en faire des int

                        int aAttempt = Integer.parseInt(analyseAttempt.get(0));              // afin de les comparer



                        if (aAttempt == sString) {
                            p = "=";
                        } else if (aAttempt > sString) {
                            p = "-";
                        } else if (aAttempt < sString) {
                            p = "+";
                        }

                        reponse = reponse + p;
                        scale++;

                    }
                    analyseAttempt.clear();
                    System.out.println("Proposition " + attempt + "-> Réponse : " + reponse);


                }


            }
            /**
             *
             *  Computer's turn
             */


            Guess guess = new Guess();

            boolean flag = (aAttempt.equals(secret));

            if (flag) {
                System.out.println("L'ordinateur a gagné ! Il a trouvé votre combinaison secrète en " + (turn) + " essais !");
                attempt = "";
                for (i = 0; i < getNbCase(); i++) {
                    attempt += 1;
                }
                aAttempt = Integer.parseInt(attempt);
                endGame();
            } else {


                System.out.println("Essai de l'ordinateur n°(" + turn + "/" + getNbTurn() + ")");
                reponse = "";
                attempt = "";


                for (int i = 0; i < value.length; i++) {

                    guess.guesser(vmin[i], vmax[i]);
                    value[i] = guess.getTries();
                }


                int[] tab = filltab(secret);

                String tmp = "";
                String tmax = "";
                String tmin = "";

                for (int j = 0; j < value.length; j++) {
                    tmp += value[j];
                    tmax += vmax[j];
                    tmin += vmin[j];

                }
                aAttempt = Integer.parseInt(tmp);
                System.out.println(aAttempt);


                int[] sol = filltab(Integer.parseInt(tmp));

                i = 0;


                for (int i = 0; i < value.length; i++) {
                    guess.setMax(vmax[i]);
                    guess.setMin(vmin[i]);

                    System.out.println("Code secret: "+tab[i] + "  | Proposition de l'ordinateur -> " + sol[i]);

                    if (tab[i] == sol[i]) {
                        r += "=";
                    } else if (tab[i] > sol[i]) {
                        r += "+";
                    } else if (tab[i] < sol[i]) {
                        r += "-";
                    }
                    guess.update(r);
                    vmin[i] = guess.getMin();
                    vmax[i] = guess.getMax();
                    reponse = reponse + r;
                    r = "";

                }

                System.out.println("Réponse du joueur :" + reponse);
                reponse = "";


                analyseAttempt.clear();

            }


    if (turn == getNbTurn()) {
        System.out.println("Quel dommage n'avez pas trouvé le code secret ! La bonne réponse était : " + secret2);
        System.out.println("Mais l'ordinateur non plus n'a pas réussi a trouver votre code secret , qui était : "+secret);

    }

        }
    }


}
