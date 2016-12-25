package org.lskk.lumen.helpdesk.twitter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ceefour on 25/12/2016.
 */
public interface TwitterAuthzRepository extends JpaRepository<TwitterAuthz, Integer> {

    List<TwitterAuthz> findAllByAppId(String appId);
}
