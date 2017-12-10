package org.jp.reima.form_matchup_table.domain.repository;

import java.util.List;

import org.jp.reima.form_matchup_table.domain.resource.Match;

public interface MatchRepository {
    
    /**
     * すべてのマッチ情報を取得する
     */
    public List<Match> findAll();
    
    /**
     * マッチ情報を取得する
     */
    public Match findById(String matchId);
    
    /**
     *  マッチ情報を最新化する
     */
    public String save(Match match);

}
