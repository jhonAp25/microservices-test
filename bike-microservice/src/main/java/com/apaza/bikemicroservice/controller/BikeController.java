package com.apaza.bikemicroservice.controller;


import com.apaza.bikemicroservice.entity.Bike;
import com.apaza.bikemicroservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    BikeService carsService;


    @GetMapping()
    public ResponseEntity<List<Bike>> getAll(){
        List<Bike> userList = carsService.getAll();
        if (userList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getById(@PathVariable("id") int id){
        Bike bike = carsService.getUserById(id);
        if(bike == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bike);
    }

    @PostMapping()
    public ResponseEntity<Bike> save (@RequestBody Bike cars){
        Bike carsNew = carsService.save(cars);

        return ResponseEntity.ok(carsNew);
    }

    @GetMapping("/{byuser}/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId){

        List<Bike>bikes = carsService.byUserID(userId);
        if(bikes == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bikes);
    }

}
