package com.Booking.Booking.repository;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Details;



@Repository
public interface IDetailRepository extends JpaRepository<Details, Integer> {
	@Query("SELECT d FROM details d WHERE "
			+ "(LOWER(d.room.roomName) LIKE CONCAT('%', LOWER(:roomname), '%') OR :roomname IS NULL) AND "
			+ "(d.room.roomType.id = :typeid OR :typeid IS NULL) AND "
			+ "(d.status = :detailstatus OR :detailstatus IS NULL) AND "
			+ "(LOWER(d.user.fullName) LIKE CONCAT('%', LOWER(:username), '%') OR :username IS NULL) AND "
			+ "((d.reserveTime <= :bookingtime AND d.endTime >= :bookingtime) OR :bookingtime IS NULL)")
	List<Details> searchRoomDetail(Pageable pageable, @Param("typeid") Integer typeid,
								   @Param("roomname") String roomname, @Param("username") String username,
								   @Param("bookingtime") Date bookingtime, @Param("detailstatus") Integer detailstatus);

	//	Page<Details> findByStatus(Integer status, Pageable pageable);
	List<Details> findByStatus(Integer status, Pageable pageable);

	// kiểm tra xem phòng đã có người đặt hay chưa
	@Query("SELECT d FROM details d WHERE " + "(d.room.id = :roomid) AND " + "(d.status = 2)"
			+ "AND ((d.reserveTime <= :reserveTime AND d.endTime >= :reserveTime) "
			+ "OR (d.reserveTime <= :endTime AND d.endTime >= :endTime) "
			+ "OR (d.reserveTime >= :reserveTime AND d.endTime <= :endTime) "
			+ "OR (d.reserveTime < :reserveTime AND d.endTime > :endTime))")
	Details checkBookingRoom(@Param("roomid") Integer roomid, @Param("reserveTime") Date reserveTime,
							 @Param("endTime") Date endTime);

	// hiển thị lịch trình đặt phòng của mỗi teacher
	@Query("SELECT d FROM details d WHERE " + "(d.user.id = :userid) AND " + "(d.status = 2)")
	List<Details> calendarBooking(@Param("userid") int userid);

	@Query("SELECT d FROM details d WHERE d.status = 1 AND d.room.id = :roomId "
			+ "AND ((d.reserveTime <= :reserveTime AND d.endTime >= :reserveTime) "
			+ "OR (d.reserveTime <= :endTime AND d.endTime >= :endTime) "
			+ "OR (d.reserveTime >= :reserveTime AND d.endTime <= :endTime) "
			+ "OR (d.reserveTime < :reserveTime AND d.endTime > :endTime))")
	List<Details> findOtherBookingsInSameTimeSlot(@Param("roomId") Integer roomId,
												  @Param("reserveTime") Date reserveTime, @Param("endTime") Date endTime);

	// đếm số lượng người đặt 1 phòng trong ngày

	@Query("SELECT COUNT(d.room.id) FROM details d " + "WHERE d.room.id = :roomid " + "AND d.status = 2 "
			+ "AND DATE(d.reserveTime) <= DATE(:timecheck) " + "AND DATE(d.endTime) >= DATE(:timecheck) "
			+ "GROUP BY d.room.id")
	Integer quantityUserBookingRoomOfDate(@Param("roomid") Integer roomid, @Param("timecheck") Date timecheck);

	@Query("SELECT COUNT(d) FROM details d")
	int rowCount();

	@Query("SELECT COUNT(d) FROM details d WHERE d.status = 1")
	int rowCountNonAccept();

	@Query("SELECT COUNT(d) FROM details d WHERE "
			+ "(LOWER(d.room.roomName) LIKE CONCAT('%', LOWER(:roomname), '%') OR :roomname IS NULL) AND "
			+ "(d.room.roomType.id = :typeid OR :typeid IS NULL) AND "
			+ "(d.status = :detailstatus OR :detailstatus IS NULL) AND "
			+ "(LOWER(d.user.fullName) LIKE CONCAT('%', LOWER(:username), '%') OR :username IS NULL) AND "
			+ "((d.reserveTime <= :bookingtime AND d.endTime >= :bookingtime) OR :bookingtime IS NULL)")
	int rowCount(@Param("typeid") Integer typeid,
				 @Param("roomname") String roomname,	@Param("username") String username,
				 @Param("bookingtime") Date bookingtime, @Param("detailstatus") Integer detailstatus);

	// những cái dưới này của thống kê không cần để ý

	@Query("SELECT COUNT(d.id) " + "FROM details d " + "WHERE d.status = 1 OR d.status = 2 AND "
			+ "(DAY(d.reserveTime) = :day OR :day IS NULL) " + "AND (MONTH(d.reserveTime) = :month OR :month IS NULL) "
			+ "AND (YEAR(d.reserveTime) = :year OR :year IS NULL)")
	Integer quantityRoomBookingOfDate(@Param("day") String day, @Param("month") String month,
									  @Param("year") String year);

	@Query("SELECT COUNT(d.event.id) FROM details d " + "WHERE d.event.id > 1 " + "AND (d.status = 1 OR d.status = 2) "
			+ "AND (DAY(d.reserveTime) = :day OR :day IS NULL) "
			+ "AND (MONTH(d.reserveTime) = :month OR :month IS NULL) "
			+ "AND (YEAR(d.reserveTime) = :year OR :year IS NULL)")
	Integer quantityEventOfDate(@Param("day") String day, @Param("month") String month, @Param("year") String year);


	@Transactional
	@Modifying
	@Query("DELETE FROM details d WHERE d.id = :detailId")
	int deleteHistoryDetailById(@Param("detailId") int detailId);
}
