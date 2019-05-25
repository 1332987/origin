package com.youwan.common.dao;

import com.youwan.common.entity.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInformationDao extends JpaRepository<PersonalInformation, Long> {
}
