package com.xptorg.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xptorg.crud.entities.Client;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
