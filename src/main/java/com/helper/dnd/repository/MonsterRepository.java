package com.helper.dnd.repository;

import com.helper.dnd.model.Monster;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface MonsterRepository
    extends JpaRepository<Monster, Long>, JpaSpecificationExecutor<Monster> {
  Optional<Monster> findByName(String name);
}
