package com.apaza.carsmicroservice.repository;


import com.apaza.carsmicroservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarsRepository extends JpaRepository<Car,Integer> {

    List<Car> findByUserId(int userId);


}

