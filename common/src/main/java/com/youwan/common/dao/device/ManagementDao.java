package com.youwan.common.dao.device;


import com.youwan.common.entity.device.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementDao extends JpaRepository<Management, Long> {
}
