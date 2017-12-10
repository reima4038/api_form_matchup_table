package org.jp.reima.form_matchup_table.infra.domain.repository;

import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchJpaRepositoryAdapter implements MatchRepository {

    @Autowired
    MatchJpaRepository repos;
}
