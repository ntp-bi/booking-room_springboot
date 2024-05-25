package com.Booking.Booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Booking.Booking.entity.Roles;

@Repository
public interface IRoleRepository extends JpaRepository<Roles, Integer> {
	Roles findByRoleName(String roleName);
}
