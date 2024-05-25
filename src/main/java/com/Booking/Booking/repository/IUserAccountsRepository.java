package com.Booking.Booking.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.UserAccounts;


@Repository
public interface IUserAccountsRepository extends JpaRepository<UserAccounts, Integer> {
	UserAccounts findByUserName(String userName);

	@Query("SELECT acc FROM UserAccounts acc WHERE LOWER(acc.userName) LIKE LOWER(:searchValue) ORDER BY acc.userName")
	List<UserAccounts> search(Pageable pagination, @Param("searchValue") String searchValue);

	@Query("SELECT COUNT(acc) FROM UserAccounts acc WHERE LOWER(acc.userName) LIKE LOWER(:searchValue)")
	int rowCount(@Param("searchValue") String searchValue);
	
	@Query("SELECT acc from UserAccounts acc WHERE acc.user.id = :userId")
	UserAccounts findbyUserId(int userId);
	
	
}
