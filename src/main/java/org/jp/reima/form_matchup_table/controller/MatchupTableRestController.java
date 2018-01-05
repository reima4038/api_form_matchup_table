package org.jp.reima.form_matchup_table.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jp.reima.form_matchup_table.api.MatchupTableApi;
import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.jp.reima.form_matchup_table.domain.resource.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchupTableRestController implements MatchupTableApi {
    
    @Autowired
    MatchRepository repos;
    
    @Override
    public ResponseEntity<String> createMatch() {
        Match match = Match.createMatch();
        match.setMaxTeamMemberNumber(5);
        repos.save(match);
        return ResponseEntity.ok(match.getMatchId());
    }

    @Override
    public ResponseEntity<Match> entryMatch(@PathVariable("id") String matchId, @PathVariable("name") String memberName) {
        Match match = repos.findById(matchId);
        match.entry(memberName);
        repos.save(match);
        return ResponseEntity.ok(match);
    }

    @Override
    public ResponseEntity<Match> leaveMatch(@PathVariable("id") String matchId, @PathVariable("name") String memberName) {
        Match match = repos.findById(matchId);
        match.cancelEntry(memberName);
        repos.save(match);
        return ResponseEntity.ok(match);
    }

    @Override
    public ResponseEntity<Match> findMatch(@PathVariable("id") String matchId) {
        return ResponseEntity.ok(repos.findById(matchId));
    }

    @Override
    public ResponseEntity<Match> assignTeam(@PathVariable("id") String matchId) {
        Match match = repos.findById(matchId);
        match.assignMembersToTeamsFromReserver();
        repos.save(match);
        return ResponseEntity.ok(match);
    }
    
    @Override
    public ResponseEntity<Match> reassignTeam(@PathVariable("id") String matchId) {
        Match match = repos.findById(matchId);
        match.reassignMembersToTeams();
        repos.save(match);
        return ResponseEntity.ok(match);
    }

    @Override
    public ResponseEntity<List<Team>> showTeams(@PathVariable("id") String matchId) {
        return ResponseEntity.ok(repos.findById(matchId).showTeams());
    }

    @Override
    public ResponseEntity<Match> startMatch(@PathVariable("id") String matchId) {
        Match match = repos.findById(matchId);
        match.startMatch();
        return ResponseEntity.ok(match);
    }


}
