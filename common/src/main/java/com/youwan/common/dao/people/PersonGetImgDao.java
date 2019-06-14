package com.youwan.common.dao.people;


import com.youwan.common.entity.people.PeopleGetImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonGetImgDao extends JpaRepository<PeopleGetImg, Long> {
}
