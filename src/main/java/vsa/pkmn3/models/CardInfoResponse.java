package vsa.pkmn3.models;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CardInfoResponse {
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

    private String imageUrl;

    @Override
    public String toString() {
        return "CardInfoResponse{" +
                "id=" + id +
                ", pokemonStage=" + pokemonStage +
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
                ", number='" + number + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static CardInfoResponse convertToCardWithImage(Card card, String imageUrl) {
        CardInfoResponseBuilder cardInfoResponseBuilder = CardInfoResponse.builder();

        cardInfoResponseBuilder
                .pokemonStage(card.getPokemonStage())
                .name(card.getName())
                .hp(card.getHp())
                .pokemonType(card.getPokemonType())
                .skills(card.getSkills())
                .weaknessType(card.getWeaknessType())
                .retreatCost(card.getRetreatCost())
                .gameSet(card.getGameSet())
                .regulationMark(card.getRegulationMark())
                .number(card.getNumber())
                .resistanceType(card.getResistanceType())
                .evolvesFrom(card.getEvolvesFrom())
                .pokemonOwner(card.getPokemonOwner());
        return cardInfoResponseBuilder.build();
    }
}
