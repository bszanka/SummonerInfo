package gg.test;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.common.Season;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.apache.log4j.BasicConfigurator;


public class Main {

//    private static MatchHistory filterMatchHistory(final Summoner summoner) {
//        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).withQueues(Queue.RANKED).withSeasons(Season.getLatest()).get();
//        return matchHistory;
//    }


    public static void main(final String[] args) {
        // Orianna framework git: https://github.com/meraki-analytics/orianna
        // RIOT API kulcs beállítása alternatív módszer: https://orianna.readthedocs.io/en/latest/configuring-orianna.html
        // Logolás bekapcsolása:
        BasicConfigurator.configure();
        String key = "RGAPI-760c5258-a109-419d-9a9d-5734e13b3189";
        Orianna.setRiotAPIKey(key);
        String name = "balazs337";
        String champ = "Varus";
        Region region = Region.EUROPE_NORTH_EAST;
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
        final Champion champion = Champion.named(champ).withRegion(region).get();
        final ChampionMastery cm = summoner.getChampionMastery(champion);
//        System.out.println("Champion ID: " + cm.getChampion().getId());
        System.out.println("Mastery points: " + cm.getPoints());
        System.out.println("Mastery level: " + cm.getLevel());
        System.out.println("Points until next level: " + cm.getPointsUntilNextLevel());

        // ChampionMasteries cms = ChampionMasteries.forSummoner(summoner).get();
        final ChampionMasteries cms = summoner.getChampionMasteries();
//        System.out.println(cms.get(3).getPoints());
//        System.out.println(cms.find(champion.getName()).getPoints());

        System.out.println(summoner.getName() + " has mastery level 6 or higher on:");
        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
        for(final ChampionMastery mastery : pro) {
            System.out.println(mastery.getChampion().getName());
        }


//        // RATE LIMIT EXCEEDED :(((
//        final HashSet<String> unpulledAccountIds = new HashSet<>();
//        unpulledAccountIds.add(summoner.getAccountId());
//        final HashSet<String> pulledAccountIds = new HashSet<>();
//
//        final HashSet<Long> unpulledMatchIds = new HashSet<>();
//        final HashSet<Long> pulledMatchIds = new HashSet<>();
//
//        while(!unpulledAccountIds.isEmpty()) {
//            // Get a new summoner from our list of unpulled summoners and pull their match history
//            final String newAccountId = unpulledAccountIds.iterator().next();
//            final Summoner newSummoner = Summoner.withId(newAccountId).withRegion(region).get();
//            final MatchHistory matches = filterMatchHistory(newSummoner);
//            for(final Match match : matches) {
//                if(!pulledMatchIds.contains(match.getId())) {
//                    unpulledMatchIds.add(match.getId());
//                }
//            }
//            unpulledAccountIds.remove(newAccountId);
//            pulledAccountIds.add(newAccountId);
//
//            while(!unpulledMatchIds.isEmpty()) {
//                // Get a random match from our list of matches
//                final long newMatchId = unpulledMatchIds.iterator().next();
//                final Match newMatch = Match.withId(newMatchId).withRegion(region).get();
//                for(final Participant p : newMatch.getParticipants()) {
//                    if(!pulledAccountIds.contains(p.getSummoner().getAccountId()) && !unpulledAccountIds.contains(p.getSummoner().getAccountId())) {
//                        unpulledAccountIds.add(p.getSummoner().getId());
//                    }
//                }
//                // The above lines will trigger the match to load its data by iterating over all the participants.
//                // If you have a database in your datapipeline, the match will automatically be stored in it.
//                unpulledMatchIds.remove(newMatchId);
//                pulledMatchIds.add(newMatchId);
//            }
//        }
    }
}