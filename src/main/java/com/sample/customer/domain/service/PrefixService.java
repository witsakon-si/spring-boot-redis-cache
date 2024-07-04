package com.sample.customer.domain.service;

import com.sample.customer.domain.exception.DataNotFoundException;
import com.sample.customer.domain.model.Prefix;
import com.sample.customer.domain.repository.PrefixRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PrefixService {

    private final PrefixRepository prefixRepository;

    public PrefixService(PrefixRepository prefixRepository) {
        this.prefixRepository = prefixRepository;
    }

    public Prefix getPrefix(Long prefixId) throws DataNotFoundException {
        return prefixRepository.findByIdAndActive(prefixId, true).orElseThrow(()
                -> new DataNotFoundException("prefix id {" + prefixId + "} doesn't exist."));
    }

}
