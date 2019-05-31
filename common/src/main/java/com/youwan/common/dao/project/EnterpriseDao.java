package com.youwan.common.dao.project;


import com.youwan.common.entity.project.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseDao extends JpaRepository<Enterprise, Long> {
}
