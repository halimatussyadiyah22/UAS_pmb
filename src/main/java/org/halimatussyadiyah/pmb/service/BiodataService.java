package org.halimatussyadiyah.pmb.service;


import org.halimatussyadiyah.pmb.domain.Biodata;
import org.halimatussyadiyah.pmb.domain.Card;
import org.halimatussyadiyah.pmb.domain.Stats;
import org.springframework.data.domain.Page;

public interface BiodataService {

    // Customer functions
    Biodata createBiodata(Biodata biodata);

    Biodata updateBiodata(Biodata biodata);

    Page<Biodata> getBiodata(int page, int size);

    Iterable<Biodata> getBiodata();
    Iterable<Card> getCards();

    Biodata getBiodata(Long id);

    Page<Biodata> searchBiodata(String name, int page, int size);

    // Invoice functions
    Card createCard(Card card);

    Page<Card> getCards(int page, int size);

    void addCardToBiodata(Long id, Card card);

    Card getCard(Long id);


    Stats getStats();
}
