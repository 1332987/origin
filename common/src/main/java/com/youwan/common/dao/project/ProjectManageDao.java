package com.youwan.common.dao.project;


import com.youwan.common.entity.project.ProjectManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectManageDao extends JpaRepository<ProjectManage, Long> {
}
