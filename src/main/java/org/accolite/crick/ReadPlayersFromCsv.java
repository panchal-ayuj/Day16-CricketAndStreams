package org.accolite.crick;

import org.accolite.crick.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ReadPlayersFromCsv {

    public static void main(String[] args) throws IOException, ParseException {
        // Path to your CSV file
        String csvFile = "players.csv";

        // Create an empty list to store players
        List<Player> players = new ArrayList<>();

        // Read the CSV file line by line
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ReadPlayersFromCsv.class.getClassLoader().getResourceAsStream(csvFile)))) {
            // Skip the header row (assuming there is one)
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                // Create a new Player object and populate it with data from the columns
                Player player = new Player();
                player.setCountry(columns[1].trim());
                player.setPlayerName(columns[0].trim());
                player.setDateOfBirth(columns[2].trim()); // Assuming date format

                // Extract numbers from remaining columns and assign to relevant fields
                player.setRunsScored(Integer.parseInt(columns[3].trim()));
                player.setMatchesPlayed(Integer.parseInt(columns[4].trim()));
                player.setWicketsTaken(Integer.parseInt(columns[5].trim()));
                player.setHighestScore(Integer.parseInt(columns[6].trim()));
                player.setAverage(Double.parseDouble(columns[7].trim()));
                player.setStrikeRate(Double.parseDouble(columns[8].trim()));

                // Add the player object to the list
                players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Highest scorer in odi cricket");

        Optional<Player> playerWithHighestScore = players.stream().sorted((player1, player2) -> ((Integer)player2.getHighestScore()).compareTo((Integer)player1.getHighestScore())).findFirst();
        System.out.println(playerWithHighestScore);

        System.out.println("2nd Highest scorer in odi cricket");

        players.stream().sorted((player1, player2) -> ((Integer)player2.getHighestScore()).compareTo((Integer)player1.getHighestScore())).skip(2).findAny().ifPresent(e-> System.out.println(e));

        System.out.println("Player with the least runs scored");



        Optional<Player> playerWithLeastRunsScored = players.stream().sorted((player1, player2) -> ((Integer)player1.getRunsScored()).compareTo(player2.getRunsScored())).findFirst();
        System.out.println(playerWithLeastRunsScored);


        System.out.println("Oldest player");
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        Optional<Player> oldestPlayer = players.stream().min(Comparator.comparing( player -> {
            try{
                Date date = format.parse(player.getDateOfBirth());
                return date.getTime();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }));
        System.out.println(oldestPlayer);

//        try {
//            // Parse the date string
//            System.out.println(LocalDate.parse(players.get(4).getDateOfBirth(), formatter) );
//
//        } catch (Exception e) {
//            // Handle the exception, e.g., print an error message
//            System.out.println("Error parsing date: " + e.getMessage());
//            e.printStackTrace();
//        }



        Map<String, List<Player>> playersByDecade = playerByDecade(players);

        // Print the result
        playersByDecade.forEach((decade, playerList) -> {
            System.out.println("Decade: " + decade);
            playerList.forEach(player -> System.out.println("  " + player.getPlayerName()));
        });

        System.out.println("top scorer in each decade");

        playersByDecade.forEach((decade, playerList) -> {
            System.out.println("Decade: " + decade);
            playerList.stream().sorted((player1, player2) -> ((Integer)player2.getHighestScore()).compareTo((Integer)player1.getHighestScore())).findAny().ifPresent(e-> System.out.println(e));
        });

        System.out.println("Highest strikerate in odi cricket");

        Optional<Player> playerWithHighestStr = players.stream().sorted((player1, player2) -> ((Double)player2.getStrikeRate()).compareTo((Double)player1.getStrikeRate())).findFirst();
        System.out.println(playerWithHighestStr);

        System.out.println("Highest wickettaker in odi cricket");

        Optional<Player> playerWithHighestwickets = players.stream().sorted((player1, player2) -> Double.valueOf(player2.getWicketsTaken()/player2.getMatchesPlayed()).compareTo(Double.valueOf(player1.getWicketsTaken()/player1.getMatchesPlayed()))).findFirst();
        System.out.println(playerWithHighestwickets);

        System.out.println("Indian players");
        players.stream().filter(player -> player.getCountry().equals("India")).forEach(player -> System.out.println(player));


        // Print the list of players
        System.out.println("List of players:");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public static Map<String,List<Player>> playerByDecade(List<Player> players) throws ParseException {
        Map<String,List<Player>> playersByDecade =
        players.stream()
                .collect(Collectors.groupingBy(player -> {
                    try {
                        return player.getDecade();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }));

        return playersByDecade;
    }
}
