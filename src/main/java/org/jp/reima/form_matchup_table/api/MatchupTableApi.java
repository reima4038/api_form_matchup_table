package org.jp.reima.form_matchup_table.api;

import java.util.List;

import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.jp.reima.form_matchup_table.domain.resource.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/form-matchup-tables/")
public interface MatchupTableApi {
    
    /**
     * マッチを作成する
     * @return matchId
     */
    @PostMapping("matches/create")
    public ResponseEntity<String> createMatch();
    
    /**
     * マッチに参加する
     * @return memberId
     */
    @PutMapping("matches/{id}/members/{name}")
    public ResponseEntity<Match> entryMatch(@PathVariable("id") String matchId, @PathVariable("name") String memberName);
    
    /**
     * マッチから退出する
     * @return memberId
     */
    @DeleteMapping("matches/{id}/members/{name}")
    public ResponseEntity<Match> leaveMatch(@PathVariable("id") String matchId, @PathVariable("name") String memberName);
    
    /**
     * マッチ情報を参照する
     * @return match data (json format)
     */
    @GetMapping("matches/{id}")
    public ResponseEntity<Match> findMatch(@PathVariable("id") String matchId);
    
    /**
     * チームにメンバを割り当てる
     * @return match-up information (json format)
     */
    @PutMapping("matches/{id}/teams/assign")
    public ResponseEntity<Match> assignTeam(@PathVariable("id") String matchId);
    
    /**
     * チームにメンバを振り分けなおす
     * @return match-up information (json format)
     */
    @PutMapping("matches/{id}/teams/reassign")
    public ResponseEntity<Match> reassignTeam(@PathVariable("id") String matchId);

    /**
     * チームを発表する
     * @return match-up information (json format)
     */
    @GetMapping("matches/{id}/teams")
    public ResponseEntity<List<Team>> showTeams(@PathVariable("id") String matchId);
    
    /**
     * マッチを開始する
     */
    @PutMapping("matches/{id}/start")
    public ResponseEntity<Match> startMatch(@PathVariable("id") String matchId);
}
