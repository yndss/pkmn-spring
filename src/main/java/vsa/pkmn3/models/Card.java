package vsa.pkmn3.models;

import lombok.*;
import vsa.pkmn3.entities.CardEntity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private PokemonStage pokemonStage;

    private String name;

    private int hp;

    private EnergyType pokemonType;

    private Card evolvesFrom;

    private List<AttackSkill> skills;

    private EnergyType weaknessType;

    private EnergyType resistanceType;

    private String retreatCost;

    private String gameSet;

    private char regulationMark;

    private Student pokemonOwner;

    private String number;

    @Override
    public String toString() {
        return "Card{" +
                "pokemonStage=" + pokemonStage +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", pokemonType=" + pokemonType +
                ", evolvesFrom=" + evolvesFrom +
                ", skills=" + skills +
                ", weaknessType=" + weaknessType +
                ", resistanceType=" + resistanceType +
                ", retreatCost='" + retreatCost + '\'' +
                ", gameSet='" + gameSet + '\'' +
                ", regulationMark=" + regulationMark +
                ", pokemonOwner=" + pokemonOwner +
                ", number=" + number +
                '}';
    }

    public static Card convertToCard(CardEntity cardEntity) {
        Card.CardBuilder cardBuilder = Card.builder();

        cardBuilder
                .id(cardEntity.getId())
                .pokemonStage(cardEntity.getPokemonStage())
                .name(cardEntity.getName())
                .hp(cardEntity.getHp())
                .pokemonType(cardEntity.getPokemonType())
                .skills(cardEntity.getSkills())
                .weaknessType(cardEntity.getWeaknessType())
                .retreatCost(cardEntity.getRetreatCost())
                .gameSet(cardEntity.getGameSet())
                .regulationMark(cardEntity.getRegulationMark())
                .number(cardEntity.getNumber());
        if (cardEntity.getEvolvesFrom() != null) {
            cardBuilder.evolvesFrom(Card.convertToCard(cardEntity.getEvolvesFrom()));
        }
        if (cardEntity.getResistanceType() != null) {
            cardBuilder.resistanceType(cardEntity.getResistanceType());
        }
        if (cardEntity.getPokemonOwner() != null) {
            cardBuilder.pokemonOwner(Student.convertToStudent(cardEntity.getPokemonOwner()));
        }
        return cardBuilder.build();
    }
}
