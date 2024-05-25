package com.Booking.Booking.repository;

import com.Booking.Booking.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HistoryOfTeacherRepository extends JpaRepository<History, Integer> {

    @Query("SELECT d FROM history d WHERE d.userId =:userId")
    List<History> findByUserId(int userId);

    @Query("SELECT d FROM history d " +
            "WHERE d.userId =:userId AND d.id=:detailId")
    Optional<History> findByDetailId(int userId, int detailId);

    @Query("SELECT d FROM history d " +
            "WHERE d.userId =:id AND (:status IS NULL OR d.status = :status) " +
            "AND (d.fullName LIKE %:name% OR d.eventName LIKE %:name% OR d.roomName LIKE %:name% OR d.typeName LIKE %:name%  OR d.description LIKE %:name%) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    Page<History> searchAllByStatusAndKeyAndDate(@Param("id") Integer id,
                                                 @Param("status") Integer status,
                                                 @Param("name") String name,
                                                 @Param("startDate") Date startDate,
                                                 @Param("endDate") Date endDate,
                                                 Pageable pageable);

    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE d.userId = :id " +
            "AND (:status IS NULL OR d.status = :status) " +
            "AND (d.fullName LIKE %:key% OR d.eventName LIKE %:key% OR d.roomName LIKE %:key% OR d.typeName LIKE %:key% OR d.description LIKE %:key%)" +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    int CountOfHistoryByStatusAndKeyAndDate(Integer id, Integer status, String key, Date startDate,Date endDate);

    @Query("SELECT d FROM history d " +
            "WHERE d.userId =:id AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    Page<History> searchAllByStatusAndDateOfTeacher(@Param("id") Integer id,
                                                    @Param("status") Integer status,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate,
                                                    Pageable pageable);

    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE d.userId = :id " +
            "AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR d.reserveTime >= :startDate) " +
            "AND (:endDate IS NULL OR d.endTime <= :endDate)")
    int CountOfHistoryByDateAndStatus(int id, Integer status, Date startDate, Date endDate);



    @Query("SELECT d FROM history d " +
            "WHERE d.userId = :userId " +
            "AND (:startDate IS NULL OR d.reserveTime <= :startDate AND d.endTime >= :startDate) " +
            "AND (d.fullName LIKE %:key% OR d.eventName LIKE %:key% OR d.roomName LIKE %:key% OR d.typeName LIKE %:key% OR d.description LIKE %:key%) " +
            "AND (:status IS NULL OR d.status = :status)")
    Page<History> searchAllByStatusAndKeyAndDate1(int userId, Integer status, String key, Date startDate, Pageable pageable);


    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE d.userId = :userId " +
            "AND (:startDate IS NULL OR (d.reserveTime <= :startDate AND d.endTime >= :startDate)) " +
            "AND (d.fullName LIKE %:key% OR d.eventName LIKE %:key% OR d.roomName LIKE %:key% OR d.typeName LIKE %:key% OR d.description LIKE %:key%) " +
            "AND (:status IS NULL OR d.status = :status)")
    int CountOfHistoryByStatusAndKeyAndDate1(int userId, String key ,Integer status, Date startDate);


    @Query("SELECT d FROM history d " +
            "WHERE d.userId =:userId AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR (d.reserveTime <= :startDate AND d.endTime >= :startDate)) ")
    Page<History> searchAllByStatusAndDateOfTeacher1(int userId, Integer status, Date startDate, Pageable pageable);


    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE d.userId = :userId " +
            "AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR (d.reserveTime <= :startDate AND d.endTime >= :startDate)) ")
    int CountOfHistoryByDateAndStatus1(int userId, Integer status, Date startDate);


    @Query("SELECT d FROM history d " +
            "WHERE d.userId = :userId " +
            "AND (:key IS NULL OR d.fullName LIKE %:key% OR d.eventName LIKE %:key% OR d.roomName LIKE %:key% OR d.typeName LIKE %:key% OR d.description LIKE %:key%) " +
            "AND (:status IS NULL OR d.status = :status)")
    Page<History> searchAllByStatusOfTeacher(int userId ,Integer status,String key, Pageable pageable);


    @Query("SELECT COUNT(*) FROM history d " +
            "WHERE d.userId = :userId " +
            "AND (:key IS NULL OR d.fullName LIKE %:key% OR d.eventName LIKE %:key% OR d.roomName LIKE %:key% OR d.typeName LIKE %:key% OR d.description LIKE %:key%) " +
            "AND (:status IS NULL OR d.status = :status)")
    int CountOfHistoryByStatusAndKey(int userId, Integer status, String key);
}
