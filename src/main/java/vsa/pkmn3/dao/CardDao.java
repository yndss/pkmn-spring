package vsa.pkmn3.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import vsa.pkmn3.entities.CardEntity;
import vsa.pkmn3.models.Card;
import vsa.pkmn3.repository.CardEntityRepository;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDao {
    private final CardEntityRepository cardEntityRepository;

    @SneakyThrows
    public List<Card> findAll() {
        return cardEntityRepository.findAll()
                .stream()
                .map(Card::convertToCard)
                .toList();
    }

    @SneakyThrows
    public Card findByName(String name) {
        return Card.convertToCard(cardEntityRepository.findByName(name).orElseThrow(
                () -> new UserPrincipalNotFoundException("User with name " + name + " not found")
        ));
    }

    @SneakyThrows
    public Card findById(UUID id) {
        return Card.convertToCard(cardEntityRepository.findById(id).orElseThrow(
                () -> new UserPrincipalNotFoundException("User with id " + id + " not found")
        ));
    }

    @SneakyThrows
    public Card findByOwner(String firstName, String surName, String familyName) {
        return Card.convertToCard(cardEntityRepository.findByPokemonOwner_FirstNameAndPokemonOwner_SurNameAndPokemonOwner_FamilyName(firstName, surName, familyName).orElseThrow(
                () -> new UserPrincipalNotFoundException("User with FIO " + firstName + ", " + surName + ", " + familyName + " not found")
        ));
    }

    @SneakyThrows
    public Card save(Card card) {
        return Card.convertToCard(cardEntityRepository.save(CardEntity.convertFromCard(card)));
    }

    public boolean cardExists(Card card) {
        return cardEntityRepository.existsByName(card.getName());
    }
}
