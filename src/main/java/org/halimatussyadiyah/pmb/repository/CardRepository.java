package org.halimatussyadiyah.pmb.repository;

import org.halimatussyadiyah.pmb.domain.Card;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardRepository extends PagingAndSortingRepository<Card, Long>, ListCrudRepository<Card, Long> {
}
