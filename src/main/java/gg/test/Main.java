package gg.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

public class Main {

    public static void main(String[] args) throws RiotApiException, IOException {


        String key = "RGAPI-4ef814bf-bb74-4f8f-bc13-e33c597b2372";
        Platform platform = Platform.EUNE;

//        Path file = FileSystems.getDefault().getPath(args[0]);
//        Charset charset = Charset.forName("US-ASCII");
//
//        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
//            String line = null;
//            if ((line = reader.readLine()) != null)
//                key = line;
//        }

        ApiConfig config = new ApiConfig().setKey(key);
        RiotApi api = new RiotApi(config);
        Summoner summoner = api.getSummonerByName(platform, args[0]);
        MatchList matchList = api.getMatchListByAccountId(platform, summoner.getAccountId());

        System.out.println("Total Games in requested match list: " + matchList.getTotalGames());

//        // We can now iterate over the match list to access the data
//        if (matchList.getMatches() != null) {
//            for (MatchReference match : matchList.getMatches()) {
//                System.out.println("GameID: " + match.getGameId());
//            }
//        }
        System.out.println("Name: " + summoner.getName());
        System.out.println("Summoner ID: " + summoner.getId());
        System.out.println("Account ID: " + summoner.getAccountId());
        System.out.println("PUUID: " + summoner.getPuuid());
        System.out.println("Summoner Level: " + summoner.getSummonerLevel());
        System.out.println("Profile Icon ID: " + summoner.getProfileIconId());
    }
}