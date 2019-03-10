package com.company;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;

public class DefenseModeP extends PlusouMoins {

    final Logger logger = LogManager.getLogger();

    DefenseModeP(int nbCase, int nbTurn, int devMode) {
        super(nbCase, nbTurn, devMode);
    }

    /**
     * @turn= permet de limiter la partie a 12 essais et met fin a la boucle
     * @attempt= int representant  tous les essais
     * @game = partie en cours
     * @difficulty = represente la difficulté de la partie actuelle
     */


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

        secretCode = sc.next();
        while (secretCode.length() != getNbCase()) {
            logger.error("Le code secret doit être de " + getNbCase() + " chiffres !");
            secretCode = sc.next();
        }

        secret = Integer.parseInt(secretCode);


        for (scale = -1; scale == getNbCase(); scale++) {

            secretString.add(i + 1, (Integer.toString(secret).substring(scale - 1, scale)));
            scale = scale + 1;


        }

        if (getDevMode() == 1) {
            logger.debug("[DevMode] Secret = " + secret);
        }

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


        Guess guess = new Guess();


        for (turn = 1; turn < getNbTurn() + 1; turn++) {
            i = 0;
            guess.setMax(vmax[i]);
            guess.setMin(vmin[i]);


            boolean flag = (aAttempt.equals(secret));

            if (flag) {
                System.out.println("L'ordinateur a gagné ! Il a trouvé votre combinaison secrète en " + (turn - 1) + " essais !");
                attempt = "";
                for (i = 0; i < getNbCase(); i++) {
                    attempt += 1;
                }
                aAttempt = Integer.parseInt(attempt);
                endGame();
            } else {


                System.out.println("Essai n°(" + turn + "/" + getNbTurn() + ")");
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


                int[] sol = filltab(Integer.parseInt(tmp));

                i = 0;


                for (int i = 0; i < value.length; i++) {
                    guess.setMax(vmax[i]);
                    guess.setMin(vmin[i]);


                    System.out.println("Proposition de l'ordinateur: " + sol[i] + "    (Solution: " + tab[i] + ")");
                    System.out.println("Veuillez donner votre réponse a l'ordinateur : ");
                    r = sc.next();
                    guess.update(r);
                    vmin[i] = guess.getMin();
                    vmax[i] = guess.getMax();
                    if (getDevMode() == 1) {
                        logger.debug("[DevMode] valeur Mini =" + vmin[i]);

                        logger.debug("[DevMode] valeur Max =" + vmax[i]);
                    }
                    r = "";

                }


                analyseAttempt.clear();

            }

        }
    }
}






