package com.company;


import java.util.*;



public class MasterMind extends Game {
    protected String secretCode;
    Scanner sc = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);
    Integer turn = 1;
    Integer secret = 0;
    Integer scale = 1;
    Integer i = -1;
    ArrayList<String> analyseAttempt = new ArrayList<>(getNbCase());
    ArrayList<String> secretString = new ArrayList(getNbCase());
    Integer aAttempt = 0;
    int[] analyse = new int[2];
    Integer secret2;
    protected String secretCode2;
    ArrayList<String> secretString2 = new ArrayList(getNbCase());
    ArrayList<MasterMindAnalyse> combinationList = new ArrayList<>();
    Integer bplace;
    Integer present;
    HashMap mapDuoSupp = new HashMap<String, String>();
    String duo;
    HashMap mapTrioSupp = new HashMap<String, String>();
    String trio;
    HashMap mapDuo = new HashMap<String, String>();
    HashMap mapTrio = new HashMap<String, String>();
    HashMap mapQuatro = new HashMap<String, String>();
    String[] duoArray = new String[100];
    String[] trioArray = new String[1000];
    String[] quatroArray = new String[10000];
    HashSet keepNb = new HashSet<Integer>();
    Integer[] combination = new Integer[getNbCase()];
    String quatuor;
    HashMap mapQuatroSupp = new HashMap<String, String>();
    protected Integer nbCombinations;


    MasterMind(int nbCase, int nbTurn, int nbColor, int devMode) {
        super(nbCase, nbTurn, nbColor, devMode);
    }


    public void SecretString() {
    }

    public void History() {
    }

    public void play() {

    }


    public int[] getValidation() {

        return analyse;
    }


    /**
     * All methods use for MasterMind's  logic
     */

    protected int[] validation(int[] codeToBeFound, int[] codeProposal) {


        int[] copyCodeToBeFound = Arrays.copyOf(codeToBeFound, getNbCase());

        for (int i = 0; i < getNbCase(); i++) {
            if (codeProposal[i] == copyCodeToBeFound[i]) {
                analyse[0]++;
            } else {
                for (int k = 0; k < getNbCase(); k++) {
                    if ((codeProposal[i] == copyCodeToBeFound[k]) && (i != k) && (codeProposal[k] != copyCodeToBeFound[k])) {
                        analyse[1]++;
                        copyCodeToBeFound[k] = -1;         //Pour eviter les cas de deux chiffres identiques et donc les compter 2 fois.

                        break;


                    }
                }
            }
        }

        return analyse;
    }


    protected void cleanMap(HashMap mapDuo) {


        Set<String> mySet = new HashSet<String>();

        for (
                Iterator itr = mapDuo.entrySet().iterator(); itr.hasNext(); ) {
            Map.Entry<String, String> entrySet = (Map.Entry) itr.next();

            String value = entrySet.getValue();

            if (!mySet.add(value)) {
                itr.remove();
            }
        }
    }

    protected String[] fillArray(String[] Array) {                        //Methode qui rempli les tableau de duo/trio/quatuor de toute leur possibilités
        for (int i = 0; i < Array.length; i++) {
            Array[i] = String.valueOf(i);
        }
        return Array;
    }


    protected Integer[] fillingPoolCombinations(Integer[] combination) {  //methode qui fait toute les combinaisons

        Integer[] nextTab = copyTab(combination);

        for (int i = getNbCase() - 1; i > -1; i--) {

            if (combination[i] != getNbColor() - 1) {
                nextTab[i] = (combination[i] + 1);
                break;
            } else nextTab[i] = 0;


        }

        return nextTab;
    }

    Integer[] copyTab(Integer[] combi) {
        Integer[] ret = new Integer[combi.length];
        for (int i = 0; i < combi.length; i++) {
            ret[i] = combi[i];
        }
        return ret;
    }


    void whosGood() {

        if (bplace == getCodeToBeFound().length) {
            System.out.println("Dommage ! L'ordinateur a trouvé votre code secret en " + turn + " essais !");
            endGame();
        }

        if (bplace == 0 && present == 0) {                                                                                //check toute les combi. Celle qui sont pas bonne , passe false
            for (int i = 0; i < combinationList.size(); i++) {
                for (int j = 0; j < combinationList.get(i).combination.length; j++) {
                    for (int k = 0; k < getCodeToBeFound().length; k++) {
                        if (combinationList.get(i).combination[j] == combinationList.get(0).combination[k]) {
                            combinationList.get(i).active = false;

                        }
                    }
                }
            }
        }


        if (turn > 1) {
            /**        Tout le code qui suit
             *                 =
             *           bplace+present
             *       donne une resultat pair
             * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
             */
            if ((bplace + present) % 2 == 0) {


                for (int j = 0; j < (combinationList.get(0).combination.length) - 2; j++) {
                    trio = String.valueOf(combinationList.get(0).combination[j]) + (combinationList.get(0).combination[j + 1]) + (combinationList.get(0).combination[j + 2]);
                    mapTrioSupp.put(j, trio);
                }


                for (int i = 0; i < combinationList.size(); i++) {                                                       //Trio enregistre comme impossible , supprimer des listes.
                    for (int m = 0; m < (combinationList.get(0).combination.length) - 2; m++) {

                        if (mapTrioSupp.containsValue(String.valueOf(combinationList.get(i).combination[m]) + (combinationList.get(i).combination[m + 1]) + (combinationList.get(i).combination[m + 2]))) {

                            combinationList.get(i).active = false;
                        }

                    }


                }


            }
            /**
             * si bplace+ present =1
             * supprime tous les duo
             *
             */

            else if ((bplace + present) == 1) {
                for (int j = 0; j < (combinationList.get(0).combination.length) - 1; j++) {
                    duo = String.valueOf(combinationList.get(0).combination[j]) + (combinationList.get(0).combination[j + 1]);
                    mapDuoSupp.put(j, duo);
                }


                for (int i = 0; i < combinationList.size(); i++) {                                     //Duo enregistre comme impossible , supprimer des listes.
                    for (int m = 0; m < (combinationList.get(0).combination.length) - 1; m++) {

                        if (mapDuoSupp.containsValue(String.valueOf(combinationList.get(i).combination[m]) + (combinationList.get(i).combination[m + 1]))) {

                            combinationList.get(i).active = false;
                        }

                    }


                }

            }
            /**
             * Pour le reste, supprimer les trio /
             * Quatuor et +  si jamais la combinaison est assez longue
             *
             */
            else if (getNbCase() >= 5 && bplace + present == 3) {
                for (int j = 0; j < (combinationList.get(0).combination.length) - 3; j++) {

                    quatuor = String.valueOf(combinationList.get(0).combination[j]) + (combinationList.get(0).combination[j + 1]) + (combinationList.get(0).combination[j + 2]) + (combinationList.get(0).combination[j + 3]);
                    mapQuatroSupp.put(j, quatuor);

                }
                for (int i = 0; i < combinationList.size(); i++) {
                    for (int m = 0; m < (combinationList.get(0).combination.length) - 3; m++) {

                        if (mapQuatroSupp.containsValue(String.valueOf(combinationList.get(i).combination[m]) + (combinationList.get(i).combination[m + 1]) + (combinationList.get(i).combination[m + 2]) + (combinationList.get(i).combination[m + 3]))) {

                            combinationList.get(i).active = false;
                        }

                    }


                }

            } else {
                for (int j = 0; j < (combinationList.get(0).combination.length) - 2; j++) {

                    trio = String.valueOf(combinationList.get(0).combination[j]) + (combinationList.get(0).combination[j + 1]) + (combinationList.get(0).combination[j + 2]);
                    mapTrioSupp.put(j, trio);

                }
                for (int i = 0; i < combinationList.size(); i++) {
                    for (int m = 0; m < (combinationList.get(0).combination.length) - 2; m++) {

                        if (mapTrioSupp.containsValue(String.valueOf(combinationList.get(i).combination[m]) + (combinationList.get(i).combination[m + 1]) + (combinationList.get(i).combination[m + 2]))) {

                            combinationList.get(i).active = false;
                        }

                    }


                }

            }


            if ((bplace + present) > 0) {
                for (i = 0; i < getCodeToBeFound().length; i++) {
                    for (int j = 0; j < getCodeToBeFound().length; j++) {
                        if (combinationList.get(0).combination[j] == getCodeToBeFound()[i]) {

                            keepNb.add(combinationList.get(0).combination[j]);
                        }
                    }

                }
            }


            /**
             * Quand keepNb a stocker tous les chiffre , supprime toute les autres possibilités
             *
             */

            if (keepNb.size() == getCodeToBeFound().length) {

                for (i = 0; i < combinationList.size(); i++) {
                    List tmp = Arrays.asList(combinationList.get(i).combination);
                    int count = 0;
                    for (Iterator<Integer> it = keepNb.iterator(); it.hasNext(); ) {
                        Integer tst = it.next();
                        if (tmp.contains(tst)) {
                            count++;
                        } else count--;
                    }
                    if (count != getNbCase()) {
                        combinationList.get(i).active = false;

                    }


                }
            }


        }
        /** Maintenant si bplace+present = impair
         * pour bplace+present = 1
         * -> Rajouter tous les duo a duosupp , et donc supprimer tout ces duo.
         *
         * pour bplace+present=3ou+
         * ->enregistrer les trio dans keeptrio
         */


    }


    void removeUselessCombination() {
        if (getDevMode() == 1) {
            System.out.println("[DevMode] nombre de combinaison avant suppression:  " + combinationList.size());
        }
        combinationList.get(0).active = false;                                                        //Degage toutes les active=false et set du coup la prochaine combinaison
        ArrayList<MasterMindAnalyse> toreplace = new ArrayList<MasterMindAnalyse>();
        for (int i = 0; i < combinationList.size(); i++) {
            if (combinationList.get(i).active) {
                toreplace.add(combinationList.get(i));


            }
        }
        combinationList = toreplace;


        for (Integer i = 0; i < mapDuoSupp.size(); i++) {                                           //Tri les duo utilisable et ceux useless
            for (int k = 0; k < mapDuo.size(); k++) {
                if (mapDuoSupp.get(i) == mapDuo.get(k)) {
                    mapDuoSupp.put(mapDuoSupp.size(), mapDuo.get(k));
                    mapDuo.remove(k);
                    cleanMap((mapDuoSupp));

                }
            }
        }


        for (Integer i = 0; i < mapTrioSupp.size(); i++) {                                             //pareil pour trio
            for (int k = 0; k < mapTrio.size(); k++) {
                if (mapTrioSupp.get(i) == mapTrio.get(k)) {
                    mapTrioSupp.put(mapTrioSupp.size(), mapTrio.get(k));
                    cleanMap(mapTrio);
                    cleanMap(mapTrioSupp);
                    mapTrio.remove(k);
                }

            }
        }
        for (Integer i = 0; i < mapQuatroSupp.size(); i++) {                                             //pareil pour quatuor
            for (int k = 0; k < mapQuatro.size(); k++) {
                if (mapQuatroSupp.get(i) == mapQuatro.get(k)) {
                    mapQuatroSupp.put(mapQuatroSupp.size(), mapQuatro.get(k));
                    cleanMap(mapQuatro);
                    cleanMap(mapQuatroSupp);
                    mapQuatro.remove(k);
                }

            }
        }
        if (getDevMode() == 1) {
        System.out.println("[DevMode] Nombre de combinaison après suppression:  " + combinationList.size());}
    }


    protected void HowManyGoods() {


        System.out.println("Combien de bien placé ?");
        bplace = sc.nextInt();
        System.out.println("Très bien , et combien de présent ?");
        present = sc.nextInt();


    }

    protected void creationCombinationList() {
        nbCombinations = (int) Math.pow(getNbColor(), getNbCase());               // nbre de combinaison possible.


        //1-
        for (int i = 0; i < getNbCase(); i++) {
            combination[i] = 0;
        }

        MasterMindAnalyse combi2 = new MasterMindAnalyse(combination, getNbCase());

        combinationList.add(combi2);


        for (int j = 0; j < nbCombinations; j++) {
            // long startTime = System.nanoTime();                   //Utilisé pour connaitre le temps de réalisation de chaque boucle.
            combination = fillingPoolCombinations(combination);
            MasterMindAnalyse toAd = new MasterMindAnalyse(combination, getNbCase());
            combinationList.add(toAd);
            //  long endTime = System.nanoTime();
            //long duration = (endTime - startTime);
            //System.out.println(duration);
        }


        for (i = 0; i < trioArray.length; i++) {                                               // Pour tte les combi enregistre tt les trio
            fillArray(trioArray);
            mapTrio.put(i, trioArray[i]);


        }

        for (i = 0; i < quatroArray.length; i++) {                                               // Pour tte les combi enregistre tt les trio
            fillArray(quatroArray);
            mapQuatro.put(i, quatroArray[i]);


        }


        for (i = 0; i < duoArray.length; i++) {                                                      //Pour tte les combinaisons enregistre tous les duo
            fillArray(duoArray);
            mapDuo.put(i, duoArray[i]);
        }


        cleanMap(mapDuo);
        cleanMap(mapTrio);
        cleanMap(mapQuatro);


    }


}