package org.jp.reima.form_matchup_table.domain.resource.pattern;

import java.util.ArrayList;

import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.jp.reima.form_matchup_table.domain.resource.Team;

public class MatchPatterns {
    
    public static Match Dota2() {
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
