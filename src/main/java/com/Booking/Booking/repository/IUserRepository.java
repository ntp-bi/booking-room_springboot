package com.Booking.Booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer>{

	@Query("SELECT u FROM Users u WHERE u.id <> 1 AND  LOWER(u.fullName) like LOWER(:searchValue) ORDER BY u.fullName")
	List<Users> search(Pageable pagination, String searchValue);
	
	@Query("SELECT count(u) FROM Users u WHERE u.id <> 1 AND LOWER(u.fullName) like LOWER(:searchValue)")
	int getRowCount(String searchValue);
	Users getUserById(int userId);
}
