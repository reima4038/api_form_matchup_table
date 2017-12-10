package org.jp.reima.form_matchup_table.controller;

import org.jp.reima.form_matchup_table.api.MatchupTableApi;
import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchupTableRestController implements MatchupTableApi {
    
    @Autowired
    MatchRepository repos;
    
    @Override
    public ResponseEntity<String> createMatch() {
        return null;
    }

    @Override
    public ResponseEntity<String> joinMatch(String matcheId, String memberName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> leaveMatch(String matcheId, String memberName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> findMatch(String matcheId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> assignTeam(String matcheId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<String> showTeams(String matcheId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startMatch(String matcheId) {
        // TODO Auto-generated method stub
        
    }

}
