package org.halimatussyadiyah.pmb.repository;

import org.halimatussyadiyah.pmb.domain.Biodata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BiodataRepository extends PagingAndSortingRepository<Biodata, Long>, ListCrudRepository<Biodata, Long> {
    Page<Biodata> findByNamaContainingOrderByNama(String nama, Pageable pageable);
}

