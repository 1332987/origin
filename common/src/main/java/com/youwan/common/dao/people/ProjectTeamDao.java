package com.youwan.common.dao.people;


import com.youwan.common.entity.people.ProjectTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamDao extends JpaRepository<ProjectTeam, Long> {
}
