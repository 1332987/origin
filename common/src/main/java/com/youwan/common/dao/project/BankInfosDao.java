package com.youwan.common.dao.project;


import com.youwan.common.entity.project.BankInfos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankInfosDao extends JpaRepository<BankInfos, Long> {
}
