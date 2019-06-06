package com.youwan.common.dao.device;


import com.youwan.common.entity.device.ManagementDev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementDevDao extends JpaRepository<ManagementDev, Long> {
    public ManagementDev findByIp(String ip);
}
