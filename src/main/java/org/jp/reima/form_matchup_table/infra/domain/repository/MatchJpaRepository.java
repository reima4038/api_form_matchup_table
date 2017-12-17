package org.jp.reima.form_matchup_table.infra.domain.repository;

import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchJpaRepository extends JpaRepository<Match, String>{

}
