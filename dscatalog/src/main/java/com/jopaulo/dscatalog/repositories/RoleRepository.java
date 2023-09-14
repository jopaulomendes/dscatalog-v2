package com.jopaulo.dscatalog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jopaulo.dscatalog.entities.Role;
import com.jopaulo.dscatalog.entities.User;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
