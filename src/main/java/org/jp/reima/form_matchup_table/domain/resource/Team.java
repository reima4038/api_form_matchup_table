package org.jp.reima.form_matchup_table.domain.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Team {

    /** TeamId **/
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String teamId;

    /** チーム名 **/
    private String teamName;

    /** メンバー **/
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<TeamMember> teamMembers;

    /** 登録する **/
    public void entry(String teamMemberName) {
        if (Objects.isNull(teamMembers)) {
            teamMembers = new ArrayList<>();
        }
        TeamMember TeamMember = new TeamMember();
        TeamMember.setName(teamMemberName);
        teamMembers.add(TeamMember);
    }

    /** 登録する **/
    public void entry(List<String> teamMemberNames) {
        teamMemberNames.stream().forEach(TeamMemberName -> entry(TeamMemberName));
    }

    /** 登録解除する **/
    public void cancelEntry(String teamMemberName) {
        if (Objects.nonNull(teamMembers)) {
            List<TeamMember> removeTargets = teamMembers.stream()
                    .filter(teamMember -> Objects.equals(teamMember.getName(), teamMemberName))
                    .collect(Collectors.toList());
            if (removeTargets.size() > 0) {
                teamMembers.remove(removeTargets.get(0));
            }
        }
    }

    /** 登録解除する **/
    public void cancelEntry(List<String> TeamMemberNames) {
        TeamMemberNames.stream().forEach(TeamMemberName -> cancelEntry(TeamMemberName));
    }

    /** 役割を設定する **/
    public void setRoles(List<String> roles) {
        Collections.shuffle(roles);
        IntStream.range(0, roles.size()).forEach(idx -> {
            if(teamMembers.size() > idx) {
                teamMembers.get(idx).setRole(roles.get(idx));
            }
        });
    }

}
