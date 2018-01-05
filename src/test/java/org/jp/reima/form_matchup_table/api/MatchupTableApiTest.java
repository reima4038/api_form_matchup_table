package org.jp.reima.form_matchup_table.api;

import org.jp.reima.form_matchup_table.domain.repository.MatchRepository;
import org.jp.reima.form_matchup_table.domain.resource.Match;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MatchupTableApiTest {
    private final int STATUS_CODE_SUCCESS = 200;

    private static final String BASE_URI = "http://localhost/form-matchup-tables/";
    @LocalServerPort
    private int port;
    
    @Autowired
    MatchRepository repos;
    
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }
    
    /**
     * マッチを作成する
     * @return matchId
     */
    @Test
    public void マッチを作成する() {
        final String uri = "matches/create";
        RestAssured
                .given()
                .when()
                    .post(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * マッチに参加する
     * @return memberId
     */
    @Test
    public void マッチに参加する() {
        final String uri = "matches/{id}/members/{name}";
        
        // given
        String id = repos.save(Match.createMatch());
        String player = "testPlayer";
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                    .pathParam("name", player)
                .when()
                    .put(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * マッチから退出する
     * @return memberId
     */
    @Test
    public void マッチから退出する() {
        final String uri = "matches/{id}/members/{name}";
        
        // given
        Match match = Match.createMatch();
        String player = "testPlayer";
        match.entry(player);
        String id = repos.save(match);
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                    .pathParam("name", player)
                .when()
                    .delete(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    /**
     * マッチを検索する
     * @return memberId
     */    
    @Test
    public void マッチを検索する() {
        final String uri = "matches/{id}";
                
        // given
        Match match = Match.createMatch();
        String id = repos.save(match);
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .get(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * チームにメンバを割り当てる
     * @return match-up information (json format)
     */
    @Test
    public void チームにメンバを割り当てる() {
        final String uri = "matches/{id}/teams/assign";
        
        // given
        Match match = Match.createMatch();
        String player = "testPlayer";
        match.entry(player);
        String id = repos.save(match);
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .put(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * チームにメンバを振り分けなおす
     * @return match-up information (json format)
     */
    @Test
    public void チームにメンバを振り分けなおす() {
        final String uri = "matches/{id}/teams/reassign";
        
        // given
        Match match = Match.createMatch();
        String player = "testPlayer";
        match.entry(player);
        String id = repos.save(match);
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .put(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * チームを発表する
     * @return match-up information (json format)
     */
    @Test
    public void チームを発表する() {
        final String uri = "matches/{id}/teams";
        
        // given
        String id = repos.save(Match.createMatch());
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .get(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
    
    /**
     * マッチを開始する
     */
    @Test
    public void マッチを開始する() {
        final String uri = "matches/{id}/start";
        
        // given
        Match match = Match.createMatch();
        String id = repos.save(match);
        
        // when
        RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .put(uri)
                .then()
                    .statusCode(STATUS_CODE_SUCCESS);
    }
}
