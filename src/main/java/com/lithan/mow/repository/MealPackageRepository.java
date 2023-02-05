package com.lithan.mow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.MealPackage;

public interface MealPackageRepository extends JpaRepository<MealPackage, Long> {

  List<MealPackage> findByFrozen(boolean frozen);
  List<MealPackage> findByFrozenAndActive(boolean frozen, boolean active);
}
