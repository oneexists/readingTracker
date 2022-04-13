package com.readingTracker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTracker.data.entity.ReadingList;

public interface ReadingListRepository extends JpaRepository<ReadingList, Long> {

}
