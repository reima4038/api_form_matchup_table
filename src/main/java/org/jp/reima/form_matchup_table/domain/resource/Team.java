package org.jp.reima.form_matchup_table.domain.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Team {
    
    /** TeamId **/
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String teamId;
    
    /** チーム名 **/
    private final String teamName;
    
    /** メンバー **/
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="memberId")
    private List<Member> members = new ArrayList<>();
    
    /** 登録する **/
    public void entry(String memberName) {
        members.add(new Member(memberName));
    }
    
    /** 登録する **/
    public void entry(List<String> memberNames) {
        memberNames.stream().forEach(memberName -> entry(memberName));
    }
    
    /** 登録解除する **/
    public void cancelEntry(String memberName) {
        Member removeTarget = members.stream()
                .filter(member -> Objects.equals(member.getName(), memberName))
                .collect(Collectors.toList())
                .get(0);
        members.remove(removeTarget);
    }
    
    /** 登録解除する **/
    public void cancelEntry(List<String> memberNames) {
        memberNames.stream().forEach(memberName -> cancelEntry(memberName));
    }
    
    

}
