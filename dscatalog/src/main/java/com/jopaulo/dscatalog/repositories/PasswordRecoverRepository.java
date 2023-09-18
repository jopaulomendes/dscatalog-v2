package com.jopaulo.dscatalog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jopaulo.dscatalog.entities.PasswordRecover;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

}
