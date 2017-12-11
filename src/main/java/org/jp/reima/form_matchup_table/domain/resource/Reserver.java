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
public class Reserver {
    /** ReserverId **/
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String reserverId;
    
    private String name;
    
    public static Reserver entry(String name) {
        Reserver reserver = new Reserver();
        reserver.setName(name);
        return reserver;
    };
}
