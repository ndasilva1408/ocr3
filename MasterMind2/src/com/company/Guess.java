package com.company;

public class Guess {
    private Integer max;
    private Integer min;
    private Integer tries;







    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }


    public Integer getMax() {
        return max;
    }

    public Integer getMin() {
        return min;
    }

    Guess() {

    }



    public void update(String r) {
        if (r.equals("=")) {
            setMax(this.max);
            setMin(this.min);
        }
        if (r.equals("-")) {
            this.max = ((int) Math.round(((min + max) / 2.0)));

        }
        if (r.equals("+")) {
            this.min = ((int) Math.round(((min + max) / 2.0)));

        }
    }

    public Integer getTries() {
        return tries;
    }

    public int guesser(int min, int max) {
        tries = (int) Math.round(((min + max) / 2.0));
        return tries;

    }


}
