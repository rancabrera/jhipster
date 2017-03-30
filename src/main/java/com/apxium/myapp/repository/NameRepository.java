package com.apxium.myapp.repository;

import com.apxium.myapp.domain.Name;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Name entity.
 */
@SuppressWarnings("unused")
public interface NameRepository extends JpaRepository<Name,Long> {

}
