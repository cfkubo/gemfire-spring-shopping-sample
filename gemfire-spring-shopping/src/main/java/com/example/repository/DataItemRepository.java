package com.example.repository;

import com.example.model.DataItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataItemRepository extends JpaRepository<DataItem, Long> {
}