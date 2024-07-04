package com.sample.customer.domain.repository;

import com.sample.customer.domain.model.Prefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PrefixRepository extends JpaRepository<Prefix, Long>, JpaSpecificationExecutor<Prefix> {

    Optional<Prefix> findByIdAndActive(long id, boolean isActive);

}
