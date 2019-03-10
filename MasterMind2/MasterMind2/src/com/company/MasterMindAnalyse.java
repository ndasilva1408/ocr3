package com.company;


import resources.DevModeException;
import resources.NbColorException;

import java.io.InputStream;
import java.util.Properties;

public class MasterMindAnalyse {

    Integer [] combination ;
    boolean active = true;
    Integer combilenght;




    MasterMindAnalyse(Integer combinaison[],int nbCase) {

        combilenght=nbCase;
        combination = combinaison;



    }































    public Game getProperties() {
        Properties prop = new Properties();
        String resources = "resources/config.properties";

        int nbColor2=0;
        int nbTurn2 =0;
        int nbCase2=0;
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


            if (nbColor2 < 4 || nbColor2 > 10)
                throw new NbColorException();
            if (nbTurn2 < 1 || nbCase2 < 1)
                throw new NullPointerException();
            if (devMode2 > 1)  // 0 ou 1  a changer
                throw new DevModeException();



            gameconf.setNbCase(nbCase2);
            gameconf.setNbColor(nbColor2);
            gameconf.setNbTurn(nbTurn2);
            gameconf.setDevMode(devMode2);


        } catch (Exception e) {
        }
        return gameconf;
    }





}