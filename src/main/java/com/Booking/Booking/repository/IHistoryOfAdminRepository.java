package com.Booking.Booking.repository;

import com.Booking.Booking.entity.History;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IHistoryOfAdminRepository extends JpaRepository<History, Integer> {
    Page<History> findAll(Pageable pageable);

    @Query("SELECT d FROM history d " +
            "WHERE status != 1 AND (:status IS NULL OR d.status = :status) " +
            "AND (d.eventName LIKE %:key% OR d.roomName LIKE %:key% " +
            "OR d.fullName LIKE %:key% OR d.typeName LIKE %:key%  OR d.description LIKE %:key%) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    Page<History> searchAllByStatusAndNameAndDateRange(@Param("status") Integer status, @Param("key") String key,
                                                       @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                       Pageable pageable);


    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE status != 1 AND (:status IS NULL OR d.status = :status) " +
            "AND (d.eventName LIKE %:name% OR d.roomName LIKE %:name% OR d.fullName LIKE %:name% " +
            "OR d.typeName LIKE %:name%  OR d.description LIKE %:name%) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) AND (:endDate IS NULL OR d.endTime <= :endDate)")
    int countOfHistoryBookingRoom(@Param("status") Integer status, @Param("name") String key, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query("SELECT d FROM history d " +
            "WHERE status != 1 AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    Page<History> searchAllByStatusAndDateRange(Integer status, Date startDate, Date endDate, Pageable pageable);


    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE status != 1 AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) AND (:endDate IS NULL OR d.endTime <= :endDate)")
    int CountOfHistoryNotKey(Integer status, Date startDate, Date endDate);


    //Thống kê theo năm
    @Query("SELECT d.eventId FROM history d WHERE d.eventId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year GROUP BY d.eventId")
    List<Integer> getEvenByYear(Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.eventId > 0 AND d.status = 3 AND YEAR(d.reserveTime)=:year")
    int countOfEventByYear(Integer year);

    @Query("SELECT d.roomId FROM history d WHERE d.roomId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year GROUP BY d.roomId")
    List<Integer> getRoomID(Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.roomId > 0 AND d.status = 3 AND YEAR(d.reserveTime)=:year")
    int countOfRoom(Integer year);

    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year AND d.status =3 "  +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND status = 3 GROUP BY room_id " +
            "ORDER BY COUNT(*) DESC LIMIT 1)", nativeQuery = true)
    List<Integer> theMostRoomTypeBookingIDByYear(@Param("year") Integer year);

    @Query("SELECT h FROM history h WHERE h.roomId = :roomId " +
            "AND h.id = (SELECT MAX(h2.id) FROM history h2 WHERE h2.roomId = :roomId)")
    History findByRoomId(Integer roomId);


    @Query("SELECT COUNT(*) AS room_count FROM history d WHERE YEAR(d.reserveTime) = :year AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count DESC LIMIT 1")
    Integer TheMostCountOfRoomTypeOfBookingByYear(Integer year);


    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year AND d.status = 3 " +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND status = 3 " +
            "GROUP BY room_id ORDER BY COUNT(*) ASC LIMIT 1)", nativeQuery = true)
    List<Integer> theLeastRoomTypeBookingIdByYear(Integer year);


    @Query("SELECT COUNT(*) AS room_count FROM history d WHERE YEAR(d.reserveTime) = :year AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count ASC LIMIT 1")
    Integer TheLeastCountOfRoomTypeOfBookingByYear(Integer year);


    @Query("SELECT d.userId FROM history d WHERE d.userId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year GROUP BY d.userId")
    List<Integer> getTeacherByYear(Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 3 AND YEAR(d.reserveTime) = :year ")
    int CountOfBookingRoomByYear(Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 4 AND YEAR(d.reserveTime) = :year ")
    int CountOfReturnBookingRoomByYear(Integer year);

    ///Thống kê theo tháng
    @Query("SELECT d.eventId FROM history d WHERE d.eventId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month GROUP BY d.eventId")
    List<Integer> getEvenByMonth(Integer month, Integer year);

    @Query("SELECT d.roomId FROM history d WHERE d.roomId > 0 " +
            "AND d.status = 3 AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month GROUP BY d.roomId")
    List<Integer> getRoomTypeByMonth(Integer month, Integer year);

    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year AND MONTH(d.reserve_time)=:month AND d.status =3 "  +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND MONTH(reserve_time)=:month AND status = 3 GROUP BY room_id " +
            "ORDER BY COUNT(*) DESC LIMIT 1)", nativeQuery = true)
    List<Integer> theMostRoomTypeBookingIDByMonth(Integer month, Integer year);

    @Query("SELECT COUNT(*) AS room_count FROM history d WHERE YEAR(d.reserveTime) = :year AND MONTH(d.reserveTime)=:month AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count DESC LIMIT 1")
    Integer TheMostCountOfRoomTypeOfBookingByMonth(Integer month, Integer year);

    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year AND MONTH(d.reserve_time)=:month AND d.status =3 "  +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND MONTH(reserve_time)=:month AND status = 3 GROUP BY room_id " +
            "ORDER BY COUNT(*) ASC LIMIT 1)", nativeQuery = true)
    List<Integer> theLeastRoomTypeBookingIdByMonth(Integer month, Integer year);

    @Query("SELECT COUNT(*) AS room_count FROM history d WHERE YEAR(d.reserveTime) = :year AND MONTH(d.reserveTime)=:month AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count ASC LIMIT 1")
    Integer TheLeastCountOfRoomTypeOfBookingByMonth(Integer month, Integer year);

    @Query("SELECT d.userId FROM history d WHERE d.userId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month GROUP BY d.userId")
    List<Integer> getUserIdByMonth(Integer month, Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 3 AND YEAR(d.reserveTime) = :year AND MONTH(d.reserveTime)=:month ")
    int CountOfBookingRoomByMonth(Integer month, Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 4 AND YEAR(d.reserveTime) = :year AND MONTH(d.reserveTime)=:month ")
    int CountOfReturnBookingRoomByMonth(Integer month, Integer year);


    ///Thống kê theo ngày
    @Query("SELECT d.eventId FROM history d WHERE d.eventId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month AND DAY(d.reserveTime)=:day" +
            " GROUP BY d.eventId")
    List<Integer> getEvenByDay(Integer day, Integer month, Integer year);


    @Query("SELECT d.roomId FROM history d WHERE d.roomId > 0 " +
            "AND d.status = 3 AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month AND DAY(d.reserveTime)=:day" +
            " GROUP BY d.roomId")
    List<Integer> getRoomTypeByDay(Integer day, Integer month, Integer year);

    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year AND d.status =3 AND MONTH(d.reserve_time) = :month AND DAY(d.reserve_time) =:day "  +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND MONTH(reserve_time) =:month AND status = 3 AND DAY(reserve_time) =:day GROUP BY room_id " +
            "ORDER BY COUNT(*) DESC LIMIT 1)", nativeQuery = true)
    List<Integer> theMostRoomTypeBookingIDByDay(Integer day, Integer month, Integer year);

    @Query("SELECT COUNT(*) AS room_count FROM history d WHERE YEAR(d.reserveTime) = :year AND MONTH(d.reserveTime)=:month " +
            "AND DAY(d.reserveTime)=:day AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count DESC LIMIT 1")
    Integer TheMostCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year);

    @Query(value = "SELECT d.room_id FROM history d WHERE YEAR(d.reserve_time) = :year " +
            "AND MONTH(d.reserve_time) =:month AND DAY(d.reserve_time) =:day AND d.status = 3 " +
            "GROUP BY d.room_id HAVING COUNT(*) = (SELECT COUNT(*) " +
            "FROM history WHERE YEAR(reserve_time) = :year AND MONTH(reserve_time) =:month AND DAY(reserve_time) =:day AND status = 3 " +
            "GROUP BY room_id ORDER BY COUNT(*) ASC LIMIT 1)", nativeQuery = true)
    List<Integer> theLeastRoomTypeBookingIdByDay(Integer day, Integer month, Integer year);


    @Query("SELECT COUNT(*) AS room_count FROM history d " +
            "WHERE YEAR(d.reserveTime) =:year AND MONTH(d.reserveTime) =:month AND DAY(d.reserveTime) =:day AND d.status =3 " +
            "GROUP BY d.roomId ORDER BY room_count ASC LIMIT 1")
    Integer TheLeastCountOfRoomTypeOfBookingByDay(Integer day, Integer month, Integer year);


    @Query("SELECT d.userId  FROM history d WHERE d.userId > 0 AND d.status = 3 " +
            "AND YEAR(d.reserveTime)=:year AND MONTH(d.reserveTime)=:month AND DAY(d.reserveTime)=:day GROUP BY d.userId")
    List<Integer> getTeacherByDay(Integer day, Integer month, Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 3 AND YEAR(d.reserveTime) = :year" +
            " AND MONTH(d.reserveTime) =:month AND DAY(d.reserveTime) =:day ")
    int CountOfBookingRoomByDay(Integer day, Integer month, Integer year);

    @Query("SELECT COUNT(*) FROM history d WHERE d.status = 4 AND YEAR(d.reserveTime) =:year" +
            " AND MONTH(d.reserveTime) =:month AND DAY(d.reserveTime) =:day ")
    int CountOfReturnBookingRoomByDay(Integer day, Integer month, Integer year);

    @Transactional
    @Modifying
    @Query("DELETE FROM history d WHERE d.id = :detailId")
    int deleteHistoryDetailById(@Param("detailId") int detailId);

//    @Query("SELECT d.id FROM history d WHERE d.status = 1 AND d.roomId =:id " +
//            "AND ((d.reserveTime <= :reserveTime AND d.endTime >= :reserveTime) " +
//            "OR (d.reserveTime <= :endTime AND d.endTime >= :endTime))")
//    List<Integer> theRoomBooking(int id ,Date reserveTime, Date endTime);

}
