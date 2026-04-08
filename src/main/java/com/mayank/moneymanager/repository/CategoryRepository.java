package com.mayank.moneymanager.repository;

import com.mayank.moneymanager.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    //select * from tbl_categories where profile_id = ?1
    List<CategoryEntity> findByProfileId(Long profileId);

    //select * from tbl_categories where id = ?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long ProfileId);

    //select * from tbl_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfileId(String type, Long ProfileId);

    Boolean existsByNameAndProfileId(String name, Long profileId);
}
