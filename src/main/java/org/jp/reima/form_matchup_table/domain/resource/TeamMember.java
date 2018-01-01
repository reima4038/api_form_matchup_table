package org.jp.reima.form_matchup_table.domain.resource;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TeamMember{

    /** MemberId **/
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String memberId;
    
    /** 名前 **/
    private String name;
    
    /** 役割 **/
    private String role;
}
