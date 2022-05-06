package com.ketul.repo;

import com.ketul.module.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}