package gg.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class SummonerEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String champion;

    @Column(nullable=false)
    private Long masteryPoints;

    @Column(nullable=false)
    private int masteryLevel;

    public SummonerEntity() {
    }

    public SummonerEntity(String name, String champion, Long masteryPoints, int masteryLevel) {
        this.name = name;
        this.champion = champion;
        this.masteryPoints = masteryPoints;
        this.masteryLevel = masteryLevel;
    }
}
