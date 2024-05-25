package com.Booking.Booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.RoomTypes;

@Repository
public interface IRoomTypeRepository extends JpaRepository<RoomTypes, Integer>{
	
	@Query("SELECT r FROM RoomTypes r WHERE LOWER(r.typeName) like LOWER(:searchValue) ORDER BY r.typeName")
	List<RoomTypes> search(Pageable pagination, String searchValue);
	
	@Query("SELECT count(r) FROM RoomTypes r WHERE LOWER(r.typeName) like LOWER(:searchValue)")
	int getRowCount(String searchValue);

	@Query("SELECT r FROM RoomTypes r WHERE r.id <> :id ")
    List<RoomTypes> findAllRoomType(int id);


}
