package org.lskk.lumen.helpdesk.lapor;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ceefour on 02/08/2016.
 */
//@Repository
public interface LaporCaseRepository extends ElasticsearchRepository<LaporCase, String> {
}
