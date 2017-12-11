package org.jp.reima.form_matchup_table.domain.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Match{
    /** MatchId **/
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String matchId;
    /** マッチ募集開始時刻 **/
    private Date recruitmentData = new Date();
    /** チーム最大人数 **/
    private Integer maxTeamMemberNumber;
    /** マッチ開始時刻 **/
    private Date matchDate;
    
    /** チーム **/
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Team> teams;
    
    /** リザーバー **/
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Reserver reserver;
    
    /**
     * マッチを作成する
     */
    public static Match createMatch() {
        Match match = new Match();
        match.setTeams(new ArrayList<>());
        Team radiant = new Team();
        radiant.setTeamName("radiant");
        match.getTeams().add(radiant);
        Team dire = new Team();
        dire.setTeamName("dire");
        match.getTeams().add(dire);
        return match;
    }
    
    /** 
     * マッチ参加者を登録する
     **/
    public void entry(String memberName) {
        if(Objects.isNull(reserver)) {
            reserver = new Reserver();
        }
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
        if(Objects.isNull(teams)) {
            teams = new ArrayList<>();
        }
        Team team = new Team();
        team.setTeamName(teamName);
        teams.add(team);
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
            List<String> cancelMembers = removeTarget.getTeamMembers().stream().map(TeamMember::getName).collect(Collectors.toList());
            reserver.entry(cancelMembers);
            removeTarget.cancelEntry(cancelMembers);
        }
     }
    
    /** 
     * チームを発表する
     **/
    public List<Team> showTeams() {
        return teams;
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
                });
        reserver.allCancel();
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
