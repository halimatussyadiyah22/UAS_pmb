package org.halimatussyadiyah.pmb.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.halimatussyadiyah.pmb.repository.DokumenRepository;
import org.halimatussyadiyah.pmb.service.DokumenService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DokumenServiceImpl implements DokumenService {
    private final DokumenRepository dokumenRepository;
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Dokumen createDokumen(Dokumen dokumen) {
        return dokumenRepository.save(dokumen);
    }

    @Override
    public Dokumen updateDokumen(Dokumen dokumen) {
        return dokumenRepository.save(dokumen);
    }

    @Override
    public Page<Dokumen> getDokumens(int page, int size) {
        return dokumenRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Iterable<Dokumen> getDokumens() {
        return dokumenRepository.findAll();
    }

    @Override
    public Dokumen getDokumen(Long id) {
        return dokumenRepository.findById(id).get();
    }
    @Override
    public Page<Dokumen> searchDokumens(String name, int page, int size) {
        return dokumenRepository.findByIdContaining(name, of(page, size));
    }
}
