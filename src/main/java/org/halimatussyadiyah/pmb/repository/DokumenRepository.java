package org.halimatussyadiyah.pmb.repository;

import org.halimatussyadiyah.pmb.domain.Card;
import org.halimatussyadiyah.pmb.domain.Dokumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DokumenRepository extends PagingAndSortingRepository<Dokumen, Long>, ListCrudRepository<Dokumen, Long> {
    
}
