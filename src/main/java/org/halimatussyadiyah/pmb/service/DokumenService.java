package org.halimatussyadiyah.pmb.service;

import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.springframework.data.domain.Page;

public interface DokumenService {
    Dokumen createDokumen(Dokumen dokumen);

    Dokumen updateDokumen(Dokumen dokumen);

    Page<Dokumen> getDokumens(int page, int size);

    Iterable<Dokumen> getDokumens();

    Dokumen getDokumen(Long id);

    Page<Dokumen> searchDokumens(String name, int page, int size);

}
