package com.apaza.carsmicroservice.service;


import com.apaza.carsmicroservice.entity.Car;
import com.apaza.carsmicroservice.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarsRepository carsRepository;

    public List<Car> getAll(){
        return carsRepository.findAll();
    }

    public Car getUserById(int id){
        return carsRepository.findById(id).orElse(null);
    }

    public Car save (Car user){
        Car carNew = carsRepository.save(user);
        return carNew;
    }

    public List<Car> byUserID(int userId){
        return carsRepository.findByUserId(userId);
    }


}
