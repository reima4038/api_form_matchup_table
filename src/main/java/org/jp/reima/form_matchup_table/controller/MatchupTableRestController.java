package org.jp.reima.form_matchup_table.controller;

import org.jp.reima.form_matchup_table.api.MatchupTableApi;
import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchupTableRestController implements MatchupTableApi {
    
    @Autowired
    MatchRepository repos;
    
    @Override
    public ResponseEntity<String> createMatch() {
        Match match = Match.createMatch();
        match.setMaxTeamMemberNumber(5);
        return ResponseEntity.ok(repos.save(match));
    }

    @Override
    public ResponseEntity<String> entryMatch(String matchId, String memberName) {
        Match match = repos.findById(matchId);
        match.entry(memberName);
        repos.save(match);
        return ResponseEntity.ok("implemented not yet.");
    }

    @Override
    public ResponseEntity<String> leaveMatch(String matchId, String memberName) {
        Match match = repos.findById(matchId);
        match.cancelEntry(memberName);
        repos.save(match);
        return ResponseEntity.ok("implemented not yet.");
    }

    @Override
    public ResponseEntity<Match> findMatch(String matchId) {
        return ResponseEntity.ok(repos.findById(matchId));
    }

    @Override
    public ResponseEntity<Match> assignTeam(String matcheId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<Match> showTeams(String matcheId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startMatch(String matcheId) {
        // TODO Auto-generated method stub
        
    }

}
