package org.monopoly.Model.Players;

import org.monopoly.Exceptions.*;
import org.monopoly.Model.Banker;
import org.monopoly.Model.Cards.ColorGroup;
import org.monopoly.Model.Cards.TitleDeedCards;
import org.monopoly.Model.Dice;
import org.monopoly.Model.GameBoard;
import org.monopoly.Model.Monopoly;

import java.util.ArrayList;

/**
 * Abstract class for the different types of players in the Monopoly game.
 * @author walshj05
 * Modified by: crevelings (4/9/25)
 * 4/9/25: Added methods to match up both player inheritors
 */
public abstract class Player {
    private final String name;
    private int position;
    private int balance;
    private boolean inJail;
    private final Token token;
    private final ArrayList<String> propertiesOwned;
    private final ArrayList<String> propertiesMortgaged;
    private final ArrayList<Monopoly> monopolies;
    private final ArrayList<ColorGroup> colorGroups;
    private final ArrayList<String> cards;
    private int jailTurns;

    /**
     * Constructor for the Player class.
     * @param name the name of the player
     * @param token the token of the player
     */
    public Player(String name, Token token) {
        this.name = name;
        this.token = token;
        this.position = 0;
        this.balance = 1500;
        this.inJail = false;
        this.propertiesOwned = new ArrayList<>();
        this.propertiesMortgaged = new ArrayList<>();
        this.monopolies = new ArrayList<>();
        this.colorGroups = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.jailTurns = 0;
    }

    /**
     * Getters and Setters
     * @author walshj05
     */
    public String getName(){
        return name;
    };

    public Token getToken() {
        return token;
    }

    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return balance;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }

    /**
     * Gets the properties owned by the player.
     *
     * Developed by: shifmans
     */
    public ArrayList<String> getPropertiesOwned() {
        return propertiesOwned;
    }

    /**
     * Gets the properties that are mortgaged.
     * @return Properties that are mortgaged.
     *
     * Developed by: shifmans
     */
    public ArrayList<String> getPropertiesMortgaged() {
        return propertiesMortgaged;
    }

    /**
     * Gets the monopolies a player has.
     * @return Monopolies a player has.
     *
     * Developed by: shifmans
     */
    public ArrayList<Monopoly> getMonopolies() {
        return monopolies;
    }



    /**
     * Moves player a certain number of spaces
     * Also checks if they are in jail or not
     * @param spaces num spaces moved
     * @author walshj05
     */
    public void move(int spaces) {
        if (!inJail) {
            GameBoard.getInstance().removeToken(this.token, position); // Remove token from old position
            position = (spaces + position) % 40; // Move the player
            GameBoard.getInstance().addToken(this.token, position);
        }
    }

    /**
     * Puts player in jail
     * @author walshj05
     */
    public void goToJail() {
        inJail = true;
        position = 10;
    }

    /** 
     * Checks to see if the player is in jail
     * @return boolean
     * @author walshj05
     */
    public boolean isInJail() {
        return inJail;
    }

    /** 
     * Releases player from jail
     * @author walshj05
     */
    public void releaseFromJail() {
        inJail = false;
        jailTurns = 0;
    }

    /** 
     * Checks if the player has a monopoly
     *
     * @return boolean
     * @author walshj05
     */
    public boolean hasMonopoly(ColorGroup colorGroup) {
        for (Monopoly monopoly : monopolies) {
            if (monopoly.getColorGroup() == colorGroup) {
                return true;
            }
        }
        return false;
    }

    /** 
     * Adds a certain amount to the player's balance
     *
     * @param amount int amount
     * @author walshj05
     */
    public void addToBalance(int amount) {
        this.balance += amount;
    }

    /** 
     * Checks if the player has a certain property
     *
     * @param property String
     * @return boolean
     * @author walshj05
     */
    public boolean hasProperty(String property) {
        return propertiesOwned.contains(property);
    }

    /** 
     * Adds a card to the player's hand
     *
     * @param card String
     * @author walshj05
     */
    public void addCard(String card) {
        cards.add(card);
    }

    /** 
     * Removes a card from the player's hand
     *
     * @param card String
     * @author walshj05
     */
    public void removeCard(String card) {
        cards.remove(card);
    }

    /** 
     * Checks if the player has a certain community chest card
     *
     * @param card String
     * @return boolean
     * @author walshj05
     */
    public boolean hasCard(String card) {
        return cards.contains(card);
    }

    /** 
     * Checks if the player is bankrupt
     *
     * @return boolean
     * @author walshj05
     */
    public boolean isBankrupt() {
        return balance == 0;
    }

    /**
     * Returns a string representation of the player
     * @return String
     * @author walshj05
     */
    @Override
    public String toString() {
        return name + " (Token: " + token.getName() + ")";
    }

    /** 
     * Updates the monopolies of the player
     *
     * @author walshj05
     */
    public void updateMonopolies() {
        Banker banker = Banker.getInstance();
        banker.checkForMonopolies(propertiesOwned, monopolies, colorGroups);
        for (Monopoly monopoly : monopolies) {
            if (!colorGroups.contains(monopoly.getColorGroup())) {
                colorGroups.add(monopoly.getColorGroup());
            }
        }
    }
    /** 
     * Gives the number of hotels the player owns
     * @return numHotels
     * @author crevelings and walshj05
     */
    public int getNumHotels() {
        int numHotels = 0;
        for (Monopoly monopoly : monopolies) {
            int[] buildings = monopoly.getBuildings();
            for (int i = 0; i < buildings.length; i++) {
                if (buildings[i] == 5) {
                    numHotels++;
                }
            }
        }
        return numHotels;
    }

    /**
     * Gets the number of turns the player has been in jail
     *
     * @return int
     * @author walshj05
     */
    public int getJailTurns() {
        return jailTurns;
    }

    /** 
     * Gives the number of houses the player owns
     * @return numHouses
     * @author crevelings and walshj05
     */
    public int getNumHouses() {
        int numHouses = 0;
        for (Monopoly monopoly : monopolies) {
            int[] buildings = monopoly.getBuildings();
            for (int i = 0; i < buildings.length; i++) {
                if (buildings[i] > 0 && buildings[i] < 5) {
                    numHouses += buildings[i];
                }
            }
        }
        return numHouses;
    }

    public abstract void takeTurn(Dice dice);

    public abstract void purchaseProperty(String property, int price) throws InsufficientFundsException;

    public abstract void mortgageProperty(String property, int mortgageCost) throws NoSuchPropertyException;

    public abstract void sellProperty(String property, int propertyCost) throws NoSuchPropertyException;

    public abstract void subtractFromBalance(int amount);

    /**
     * Resets the number of turns the player has been in jail
     *
     * @author walshj05
     */
    public void resetJailTurns() {
        this.jailTurns = 0;
    }

    /**
     * Increments the number of turns the player has been in jail
     *
     * @author walshj05
     */
    public void incrementJailTurns() { //todo we can remove this once we make the jail process
        this.jailTurns++;
    }

    public ArrayList<ColorGroup> getColorGroups() {
        return colorGroups;
    }

    /**
     * Gets the colorgroup of a given property
     * @param property String property name
     * @return ColorGroup
     */
    public ColorGroup getColorGroupOfProperty(String property) {
        return TitleDeedCards.getInstance().getProperty(property).getColorGroup();
    }

    public int getPriceOfProperty(String property){
        return TitleDeedCards.getInstance().getProperty(property).getPrice();
    }

    public abstract void buyHouse(String propertyName, ColorGroup colorGroup, int price) throws InsufficientFundsException, RuntimeException;
    public abstract void sellHouse(String propertyName, ColorGroup colorGroup) throws NoSuchPropertyException;
    public abstract void buyHotel(String propertyName, ColorGroup colorGroup, int price) throws InsufficientFundsException, RuntimeException;
    public abstract void sellHotel(String propertyName, ColorGroup colorGroup) throws NoSuchPropertyException;
}