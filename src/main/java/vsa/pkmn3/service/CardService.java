package vsa.pkmn3.service;



import vsa.pkmn3.models.Card;

import java.util.List;
import java.util.UUID;

public interface CardService {

    List<Card> getAllCards();

    Card getCardByName(String name);

    Card getCardById(UUID id);

    Card getCardByOwner(String firstName, String surName, String familyName);

    Card saveCard(Card card);
}
