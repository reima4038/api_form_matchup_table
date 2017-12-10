package org.jp.reima.form_matchup_table.api;

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
    public ResponseEntity<String> joinMatch(@PathVariable("id") String matcheId, @PathVariable("name") String memberName);
    
    /**
     * マッチから退出する
     * @return memberId
     */
    @DeleteMapping("matches/{id}/members/{name}")
    public ResponseEntity<String> leaveMatch(@PathVariable("id") String matcheId, @PathVariable("name") String memberName);
    
    /**
     * マッチ情報を参照する
     * @return match data (json format)
     */
    @GetMapping("matches/{id}")
    public ResponseEntity<String> findMatch(@PathVariable("id") String matcheId);
    
    /**
     * チームにメンバを割り当てる
     * @return matchup infomation (json format)
     */
    @GetMapping("matches/{id}/teams/assign")
    public ResponseEntity<String> assignTeam(@PathVariable("id") String matcheId);

    /**
     * チームを発表する
     * @return matchup infomation (json format)
     */
    @GetMapping("matches/{id}/teams")
    public ResponseEntity<String> showTeams(@PathVariable("id") String matcheId);
    
    /**
     * マッチを開始する
     */
    @GetMapping("matches/{id}/start")
    public void startMatch(@PathVariable("id") String matcheId);
}
