package org.jp.reima.form_matchup_table.domain.resource.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.jp.reima.form_matchup_table.domain.resource.Team;

public class MatchPatterns {
    
    public static List<String> dota2Roles() {
        return Arrays.asList("1st", "2nd", "3rd", "4th", "5th");
    }
    
    public static Match dota2() {
        Match match = new Match();
        match.setTeams(new ArrayList<>());
        
        Team radiant = new Team();
        radiant.setTeamName("Radiant");
        match.getTeams().add(radiant);
        
        Team dire = new Team();
        dire.setTeamName("Dire");
        match.getTeams().add(dire);
        
        return match;
    }
}
