package com.apaza.usermicroservice.controller;


import com.apaza.usermicroservice.entity.User;
import com.apaza.usermicroservice.model.Bike;
import com.apaza.usermicroservice.model.Car;
import com.apaza.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping()
    public ResponseEntity<List<User>> getAll(){
        List<User> userList = userService.getAll();
        if (userList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> save (@RequestBody User user){
        User userNew = userService.save(user);

        return ResponseEntity.ok(userNew);
    }

    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar (@PathVariable("userId") int userId , @RequestBody Car  car){
        if (userService.getUserById(userId) == null )
            return ResponseEntity.notFound().build();
        Car carnew = userService.saveCar(userId, car);
        return ResponseEntity.ok(carnew);
    }

    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike (@PathVariable("userId") int userId , @RequestBody Bike bike){
        if(userService.getUserById(userId) == null )
            return ResponseEntity.notFound().build();
        Bike bikenew = userService.saveBike(userId, bike);
        return ResponseEntity.ok(bikenew);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String , Object>> getCars(@PathVariable("userId") int userId) {
        Map<String, Object> result= userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }


}
