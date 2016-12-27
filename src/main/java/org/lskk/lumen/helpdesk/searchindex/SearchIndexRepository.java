package org.lskk.lumen.helpdesk.searchindex;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ceefour on 26/12/2016.
 */
public interface SearchIndexRepository extends JpaRepository<SearchIndex, String> {
}
