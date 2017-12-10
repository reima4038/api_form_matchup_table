package org.jp.reima.form_matchup_table.infra.domain.repository;

import java.util.List;

import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchJpaRepositoryAdapter implements MatchRepository {

    @Autowired
    MatchJpaRepository repos;

    @Override
    public List<Match> findAll() {
        return repos.findAll();
    }

    @Override
    public Match findById(String matchId) {
        return repos.findOne(matchId);
    }

    @Override
    public String save(Match match) {
        return repos.save(match).getMatchId();
    }
}
