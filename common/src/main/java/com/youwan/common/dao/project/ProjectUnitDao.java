package com.youwan.common.dao.project;


import com.youwan.common.entity.project.ProjectUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUnitDao extends JpaRepository<ProjectUnit, Long> {
}