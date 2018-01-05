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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jp.reima.form_matchup_table.domain.resource.pattern.MatchPatterns;

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
    private Date recruitmentDate = new Date();
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
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Reserver> reservers;
    
    /**
     * マッチを作成する
     */
    public static Match createMatch() {
        return MatchPatterns.dota2();
    }
    
    /** 
     * マッチ参加者を登録する
     **/
    public void entry(String memberName) {
        if(Objects.isNull(reservers)) {
            reservers = new ArrayList<>();
        }
        if(notExistsMember(memberName)) {
            reservers.add(Reserver.entry(memberName));
        }
    }
    
    /**
     * 参加者が存在するか確認する
     * @param memberName
     * @return true 存在する
     *         false 存在しない
     */
    public boolean existsMember(String memberName) {
        if(Objects.isNull(reservers) ||
                Objects.isNull(teams) ||
                reservers.size() == 0 ||
                teams.size() == 0) {
            return false;
        }
        long reserversCount =
                reservers.stream()
                        .filter(reserver -> reserver.getName().equals(memberName))
                        .count();
        long teamsCount = 
                teams.stream()
                    .flatMap(team -> team.getTeamMembers().stream())
                    .filter(teamMember -> teamMember.getName().equals(memberName))
                    .count();
        return (reserversCount + teamsCount > 0) ? true : false;
    }
    
    /**
     * 参加者が存在しないことを確認する
     * @param memberName
     * @return true 存在しない
     *         false 存在する
     **/
    public boolean notExistsMember(String memberName) {
        return !existsMember(memberName);
    }
    
    /** 
     * マッチ参加者の登録を解除する
     **/
    public void cancelEntry(String memberName) {
        if(Objects.nonNull(teams)) {
            teams.forEach(team -> team.cancelEntry(memberName));
        }
        if(Objects.nonNull(reservers)){
            List<Reserver> removeTargets = reservers.stream()
                    .filter(member -> Objects.equals(member.getName(), memberName))
                    .collect(Collectors.toList());
            removeTargets.stream()
                .forEach(target-> reservers.remove(target));
        }
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
        List<Team> removeTargets =
                teams.stream()
                     .filter(team -> Objects.equals(team.getTeamName(), teamName))
                     .collect(Collectors.toList());
        if(removeTargets.size() > 0) {
            teams.remove(removeTargets.get(0));
        }
    }
    
    /** 
     * チームを解散する
     **/
    public void breakTeamUp(String teamName) {
        List<Team> removeTargets =
                teams.stream()
                     .filter(team -> Objects.equals(team.getTeamName(), teamName))
                     .collect(Collectors.toList());
        if(removeTargets.size() > 0) {
            Team removeTarget = removeTargets.get(0);
            List<String> cancelMembers = removeTarget.getTeamMembers().stream().map(TeamMember::getName).collect(Collectors.toList());
            cancelMembers.stream().map(Reserver::entry).forEach(member -> reservers.add(member));
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
        Collections.shuffle(reservers);
        List<Reserver> removeTargets = new ArrayList<>();
        IntStream.range(0, reservers.size())
                .forEach(idx -> {
                    Reserver reserver = reservers.get(idx);
                    Team entryTarget = teams.get(idx % teams.size());
                    if(entryTarget.getTeamMembers().size() < maxTeamMemberNumber) {
                        entryTarget.entry(reserver.getName());
                        removeTargets.add(reserver);
                    }
                });
        teams.stream()
            .forEach(team -> team.setRoles(MatchPatterns.dota2Roles()));
        reservers.removeAll(removeTargets);
    }
    
    /** 
     * チームメンバーの割当てを再度行う
     **/
    public void reassignMembersToTeams() {
        teams.stream()
            .map(team -> team.getTeamName())
            .forEach(teamName -> this.breakTeamUp(teamName));
        this.assignMembersToTeamsFromReserver();
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
