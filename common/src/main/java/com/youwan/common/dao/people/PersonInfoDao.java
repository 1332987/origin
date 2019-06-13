package com.youwan.common.dao.people;


import com.youwan.common.entity.people.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, Long> {
}
