package org.jp.reima.form_matchup_table.domain.resource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
public class Member {
    
    /** MemberId **/
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String memberId;
    
    /** 名前 **/
    private final String name;

}
