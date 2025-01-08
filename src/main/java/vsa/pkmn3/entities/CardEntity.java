package vsa.pkmn3.entities;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import vsa.pkmn3.models.AttackSkill;
import vsa.pkmn3.models.Card;
import vsa.pkmn3.models.EnergyType;
import vsa.pkmn3.models.PokemonStage;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"stage\"")
    private PokemonStage pokemonStage;

    @Column(name = "\"name\"")
    private String name;

    @Column(name = "\"hp\"")
    private int hp;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"pokemon_type\"")
    private EnergyType pokemonType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"evolves_from_id\"")
    private CardEntity evolvesFrom;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "\"attack_skills\"")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"weakness_type\"")
    private EnergyType weaknessType;

    @Enumerated(EnumType.STRING)
    @Column(name = "\"resistance_type\"")
    private EnergyType resistanceType;

    @Column(name = "\"retreat_cost\"")
    private String retreatCost;

    @Column(name = "\"game_set\"")
    private String gameSet;

    @Column(name = "\"regulation_mark\"")
    private char regulationMark;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "\"pokemon_owner_id\"")
    private StudentEntity pokemonOwner;

    @Column(name = "\"card_number\"")
    private String number;

    public static CardEntity convertFromCard(Card card) {
        CardEntityBuilder cardEntityBuilder = CardEntity.builder();

        cardEntityBuilder
                .id(card.getId())
                .pokemonStage(card.getPokemonStage())
                .name(card.getName())
                .hp(card.getHp())
                .pokemonType(card.getPokemonType())
                .skills(card.getSkills())
                .weaknessType(card.getWeaknessType())
                .retreatCost(card.getRetreatCost())
                .gameSet(card.getGameSet())
                .regulationMark(card.getRegulationMark())
                .number(card.getNumber());
                if (card.getEvolvesFrom() != null) {
                    cardEntityBuilder.evolvesFrom(CardEntity.convertFromCard(card.getEvolvesFrom()));
                }
                if (card.getResistanceType() != null) {
                    cardEntityBuilder.resistanceType(card.getResistanceType());
                }
                if (card.getPokemonOwner() != null) {
                    cardEntityBuilder.pokemonOwner(StudentEntity.convertFromStudent(card.getPokemonOwner()));
                }

        return cardEntityBuilder.build();
    }
}
