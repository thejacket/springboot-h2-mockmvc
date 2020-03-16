package com.thejacket.springbootH2Mockmvc.service;

import com.thejacket.springbootH2Mockmvc.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionService extends JpaRepository<Mission, Integer> {
}
