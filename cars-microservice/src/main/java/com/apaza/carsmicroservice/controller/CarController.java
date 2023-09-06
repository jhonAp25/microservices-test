package com.apaza.carsmicroservice.controller;



import com.apaza.carsmicroservice.entity.Car;
import com.apaza.carsmicroservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carsService;


    @GetMapping()
    public ResponseEntity<List<Car>> getAll(){
        List<Car> userList = carsService.getAll();
        if (userList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable("id") int id){
        Car car = carsService.getUserById(id);
        if(car == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car);
    }

    @PostMapping()
    public ResponseEntity<Car> save (@RequestBody Car car){
        Car carsNew = carsService.save(car);

        return ResponseEntity.ok(carsNew);
    }

    @GetMapping("/{byuser}/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int userId){

        List<Car>cars = carsService.byUserID(userId);
        if(cars == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cars);
    }

}
