package org.jp.reima.form_matchup_table.domain.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Match {
    
    /** MatchId **/
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String matchId;
    /** マッチ募集開始時刻 **/
    private Date recruitmentData = new Date();
    /** チーム最大人数 **/
    private Integer maxTeamMemberNumber;
    /** フルメンバー人数 **/
    private Integer fullMemberNumber;
    /** マッチ開始時刻 **/
    private Date matchDate;
    
    /** チーム **/
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="teamId")
    private List<Team> teams = new ArrayList<>();
    
    /** リザーバー **/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name="reserverId")
    private Reserver reserver = new Reserver();

    /** 
     * マッチ参加者を登録する
     **/
    public void entry(String memberName) {
        reserver.entry(memberName);
    }
    
    /** 
     * マッチ参加者の登録を解除する
     **/
    public void cancelEntry(String memberName) {
        teams.forEach(team -> team.cancelEntry(memberName));
        reserver.cancelEntry(memberName);
    }
    
    /** 
     * チームを作成する
     **/
    public void createTeam(String teamName) {
        teams.add(new Team(teamName));
    }
    
    /**
     * チーム登録を解除する
     */
    public void removeTeam(String teamName) {
        Team removeTarget =
                teams.stream()
                     .filter(team -> Objects.equals(team.getTeamName(), teamName))
                     .collect(Collectors.toList())
                     .get(0);
        if(Objects.nonNull(removeTarget)) {
            teams.remove(removeTarget);
        }
    }
    
    /** 
     * チームを解散する
     **/
    public void breakTeamUp(String teamName) {
        Team removeTarget =
                teams.stream()
                     .filter(team -> Objects.equals(team.getTeamName(), teamName))
                     .collect(Collectors.toList())
                     .get(0);
        if(Objects.nonNull(removeTarget)) {
            List<String> cancelMembers = removeTarget.getMembers().stream().map(Member::getName).collect(Collectors.toList());
            reserver.entry(cancelMembers);
            removeTarget.cancelEntry(cancelMembers);
        }
     }
    
    /** 
     * チームを発表する
     **/
    public String showTeams() {
        return this.toString();
    }
    
    /** 
     * リザーバーをチームに割り当てる
     **/
    public void assignMembersToTeamsFromReserver() {
        Collections.shuffle(reserver.getMembers());
        IntStream.range(0, reserver.getMembers().size())
                .forEach(idx -> {
                    String reserverMemberName = reserver.getMembers().get(idx).getName();
                    teams.get(idx % teams.size()).entry(reserverMemberName);
                    reserver.cancelEntry(reserverMemberName);
                });
    }
    
    /** 
     * マッチを開始する
     **/    
    public void startMatch() {
        if(Objects.isNull(matchDate)) {
            matchDate = new Date();
        }
    }
}
