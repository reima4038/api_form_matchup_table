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
    private List<Member> members;
    
    /** 登録する **/
    public void entry(String memberName) {
        if(Objects.isNull(members)) {
            members = new ArrayList<>();
        }
        Member member = new Member();
        member.setName(memberName);
        members.add(member);
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
