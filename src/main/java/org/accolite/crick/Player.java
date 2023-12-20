package org.accolite.crick;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Player {

	private String country;
    private String playerName;
    private String dateOfBirth;
    private int runsScored;
    private int matchesPlayed;
    private int wicketsTaken;
    private int highestScore;
    private double average;
    private double strikeRate;

    // Getters and setters...

    @Override
    public String toString() {
        return "Player{" +
                "country='" + country + '\'' +
                ", playerName='" + playerName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", runsScored=" + runsScored +
                ", matchesPlayed=" + matchesPlayed +
                ", wicketsTaken=" + wicketsTaken +
                ", highestScore=" + highestScore +
                ", average=" + average +
                ", strikeRate=" + strikeRate +
                '}';
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getDateOfBirth() {
		return dateOfBirth.replaceAll("\"", "");
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getRunsScored() {
		return runsScored;
	}

	public void setRunsScored(int runsScored) {
		this.runsScored = runsScored;
	}

	public int getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public int getWicketsTaken() {
		return wicketsTaken;
	}

	public void setWicketsTaken(int wicketsTaken) {
		this.wicketsTaken = wicketsTaken;
	}

	public int getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(double strikeRate) {
		this.strikeRate = strikeRate;
	}

	DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

	public String getDecade() throws ParseException {
        int decade = 0;
        try {
            Date date = format.parse(this.getDateOfBirth());
            int year = date.getYear();
            decade = (year / 10) * 10;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return decade + "s";
    }
    
}
