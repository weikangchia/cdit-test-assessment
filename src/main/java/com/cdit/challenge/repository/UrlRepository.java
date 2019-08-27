package com.cdit.challenge.repository;

import com.cdit.challenge.model.UrlShortener;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UrlRepository extends JpaRepository<UrlShortener, UUID>, JpaSpecificationExecutor<UrlShortener> {

}
