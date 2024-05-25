package com.Booking.Booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Events;


@Repository
public interface IEventRepository extends JpaRepository<Events, Integer> {
	@Query("SELECT e FROM Events e WHERE LOWER(e.eventName) like LOWER(:searchValue) ORDER BY e.eventName")
	List<Events> search(Pageable pagination, String searchValue);
	
	@Query("SELECT count(e) FROM Events e WHERE LOWER(e.eventName) like LOWER(:searchValue)")
	int getRowCount(String searchValue);
	
	@Query("SELECT e FROM Events e WHERE e.id = :eventId")
	Events findByEventId(int eventId);

	@Query("SELECT e FROM Events e WHERE e.id <> :id")
    List<Events> findEventName(int id);


}
