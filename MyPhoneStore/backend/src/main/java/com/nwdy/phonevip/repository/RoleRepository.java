package com.nwdy.phonevip.repository;

import com.nwdy.phonevip.model.Role;
import com.nwdy.phonevip.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
