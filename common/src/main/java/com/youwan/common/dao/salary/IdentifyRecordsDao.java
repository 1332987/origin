package com.youwan.common.dao.salary;


import com.youwan.common.entity.salary.IdentifyRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifyRecordsDao extends JpaRepository<IdentifyRecords, Long> {
}
