package com.Booking.Booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Rooms;

@Repository
public interface IRoomRepository extends JpaRepository<Rooms, Integer>{

	@Query("SELECT r FROM rooms r WHERE "
			+ "(LOWER(r.roomName) LIKE CONCAT('%', LOWER(:roomname), '%') OR :roomname IS NULL) AND "
			+ "(r.status = :status OR :status IS NULL) AND "
			+ "(r.roomType.id = :typeid OR :typeid IS NULL) AND "
			+ "(r.countOfSeats >= :countofseats OR :countofseats IS NULL)")
	List<Rooms> searchRoom(Pageable pageable,
						   @Param("status") Integer status,
						   @Param("typeid") Integer typeid,
						   @Param("countofseats") Integer countofseats,
						   @Param("roomname") String roomname);

	@Query("SELECT r FROM rooms r WHERE r.id = :roomId")
	Rooms findByRoomId(Integer roomId);

	//kiểm tra xem đã có phòng này hay chưa (nghiệp vụ thêm phòng)
	@Query("SELECT r FROM rooms r WHERE "
			+ "r.roomName = :roomname "
			+ "AND r.roomType.id = :typeid")
	Rooms findByRoomNameAndTypeId(@Param("roomname") String roomname, @Param("typeid") Integer typeid);


	@Query("SELECT COUNT(r) FROM rooms r WHERE "
			+ "(LOWER(r.roomName) LIKE CONCAT('%', LOWER(:roomname), '%') OR :roomname IS NULL) AND "
			+ "(r.status = :status OR :status IS NULL) AND "
			+ "(r.roomType.id = :typeid OR :typeid IS NULL) AND "
			+ "(r.countOfSeats >= :countofseats OR :countofseats IS NULL)")
	int rowcount(@Param("status") Integer status,
				 @Param("typeid") Integer typeid,
				 @Param("countofseats") Integer countofseats,
				 @Param("roomname") String roomname);

	@Query("SELECT COUNT(r) FROM rooms r")
	int rowCount();


}
