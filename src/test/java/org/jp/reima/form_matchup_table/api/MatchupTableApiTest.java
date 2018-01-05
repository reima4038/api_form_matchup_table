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
        String id = repos.save(new Match());
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
        
    }
    /**
     * マッチを検索する
     * @return memberId
     */    
    @Test
    public void マッチを検索する() {
        
    }
    
    /**
     * チームにメンバを割り当てる
     * @return match-up information (json format)
     */
    @Test
    public void チームにメンバを割り当てる() {
        
    }
    
    /**
     * チームにメンバを振り分けなおす
     * @return match-up information (json format)
     */
    @Test
    public void チームにメンバを振り分けなおす() {
        
    }
    
    /**
     * チームを発表する
     * @return match-up information (json format)
     */
    @Test
    public void チームを発表する() {
        
    }
    
    /**
     * マッチを開始する
     */
    @Test
    public void マッチを開始する() {
        
    }
}
