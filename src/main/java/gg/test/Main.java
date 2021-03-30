package gg.test;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class Main {
    public static void main(final String[] args) {
        // API git: https://github.com/meraki-analytics/orianna
        // RIOT API kulcs beállítása alternatív módszer: https://orianna.readthedocs.io/en/latest/configuring-orianna.html
        // Logolás bekapcsolása:
        //BasicConfigurator.configure();
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
        System.out.println(cms.get(3).getPoints());
        System.out.println(cms.find(champion.getName()).getPoints());

        System.out.println(summoner.getName() + " has mastery level 6 or higher on:");
        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
        for(final ChampionMastery mastery : pro) {
            System.out.println(mastery.getChampion().getName());
        }
    }
}