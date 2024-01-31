package org.halimatussyadiyah.pmb.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halimatussyadiyah.pmb.domain.Biodata;
import org.halimatussyadiyah.pmb.domain.Card;
import org.halimatussyadiyah.pmb.domain.Stats;
import org.halimatussyadiyah.pmb.repository.BiodataRepository;
import org.halimatussyadiyah.pmb.repository.CardRepository;
import org.halimatussyadiyah.pmb.rowmapper.StatsRowMapper;
import org.halimatussyadiyah.pmb.service.BiodataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import static org.halimatussyadiyah.pmb.query.BiodataQuery.STATS_QUERY;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BiodataServiceImpl implements BiodataService {
    private final BiodataRepository biodataRepository;
    private final CardRepository cardRepository;
    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public Biodata createBiodata(Biodata biodata) {
        biodata.setCreatedAt(new Date());
        return biodataRepository.save(biodata);
    }

    @Override
    public Biodata updateBiodata(Biodata biodata) {
        return biodataRepository.save(biodata);
    }

    @Override
    public Page<Biodata> getBiodata(int page, int size) {
        return biodataRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Iterable<Biodata> getBiodata() {
        return biodataRepository.findAll();
    }

    @Override
    public Iterable<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public Biodata getBiodata(Long id) {
        return biodataRepository.findById(id).get();
    }

    @Override
    public Page<Biodata> searchBiodata(String nama, int page, int size) {
        return biodataRepository.findByNamaContainingOrderByNama(nama, of(page, size));
    }

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Page<Card> getCards(int page, int size) {
        return cardRepository.findAll(of(page, size));
    }

    @Override
    public void addCardToBiodata(Long id, Card card) {
        Biodata biodata= biodataRepository.findById(id).get();
        card.setBiodata(biodata);
        cardRepository.save(card);
    }

    @Override
    public Card getCard(Long id) {
        return cardRepository.findById(id).get();
    }

    @Override
    public Stats getStats() {
        return jdbc.queryForObject(STATS_QUERY, Map.of(), new StatsRowMapper());
    }

}
