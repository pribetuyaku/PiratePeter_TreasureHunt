package model;

/**
 *
 * @author Priscilla Betuyaku
 */
public class Player {

    private String namePlayer;
    private String surPlayer;
    private int agePlayer;
    private int piratePoints;
    private int digPoints;

    //methods to set Player's data, name, surname, age
    public void setAgePlayer(int agePlayer) {
        this.agePlayer = agePlayer;
    }

    public void setPiratePoints(int piratePoints) {
        this.piratePoints = piratePoints;
    }

    public void setDigPoints(int digPoints) {
        this.digPoints = digPoints;
    }

    //methods to access Player's data, name, surname, age
    public int getAgePlayer() {
        return agePlayer;
    }

    public int getPiratePoints() {
        return piratePoints;
    }

    public int getDigPoints() {
        return digPoints;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public String getSurPlayer() {
        return surPlayer;
    }

    public void setSurPlayer(String surPlayer) {
        this.surPlayer = surPlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String toString() {
        return this.namePlayer + " " + this.surPlayer + "," + this.piratePoints + "," + this.digPoints;
    }

}
