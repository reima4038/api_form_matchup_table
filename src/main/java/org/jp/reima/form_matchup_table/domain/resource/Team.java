package org.jp.reima.form_matchup_table.domain.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class Team{
    
    /** TeamId **/
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String teamId;
    
    /** チーム名 **/
    private String teamName;
    
    /** メンバー **/
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="id")
    private List<TeamMember> TeamMembers;
    
    /** 登録する **/
    public void entry(String TeamMemberName) {
        if(Objects.isNull(TeamMembers)) {
            TeamMembers = new ArrayList<>();
        }
        TeamMember TeamMember = new TeamMember();
        TeamMember.setName(TeamMemberName);
        TeamMembers.add(TeamMember);
    }
    
    /** 登録する **/
    public void entry(List<String> TeamMemberNames) {
        TeamMemberNames.stream().forEach(TeamMemberName -> entry(TeamMemberName));
    }
    
    /** 登録解除する **/
    public void cancelEntry(String TeamMemberName) {
        TeamMember removeTarget = TeamMembers.stream()
                .filter(TeamMember -> Objects.equals(TeamMember.getName(), TeamMemberName))
                .collect(Collectors.toList())
                .get(0);
        TeamMembers.remove(removeTarget);
    }
    
    /** 登録解除する **/
    public void cancelEntry(List<String> TeamMemberNames) {
        TeamMemberNames.stream().forEach(TeamMemberName -> cancelEntry(TeamMemberName));
    }
    
    

}
