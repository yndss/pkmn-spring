package vsa.pkmn3.service;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import vsa.pkmn3.dao.CardDao;
import vsa.pkmn3.dao.StudentDao;
import vsa.pkmn3.models.Card;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardDao cardDao;

    private final StudentDao studentDao;


    @Override
    public List<Card> getAllCards() {
        return cardDao.findAll();
    }

    @Override
    public Card getCardByName(String name) {
        return cardDao.findByName(name);
    }

    @Override
    public Card getCardById(UUID id) {
        return cardDao.findById(id);
    }

    @Override
    public Card getCardByOwner(String firstName, String surName, String familyName) {
        return cardDao.findByOwner(firstName, surName, familyName);
    }

    @Override
    public Card saveCard(Card card) {
        if (cardDao.cardExists(card)) {
            throw new DuplicateKeyException("Card with name " + card.getName() + " already exists");
        }
        return cardDao.save(card);
    }
}
