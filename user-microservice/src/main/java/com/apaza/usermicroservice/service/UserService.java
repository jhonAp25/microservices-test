package com.apaza.usermicroservice.service;


import com.apaza.usermicroservice.entity.User;
import com.apaza.usermicroservice.feignClients.BikeFeignClient;
import com.apaza.usermicroservice.feignClients.CarFeignClient;
import com.apaza.usermicroservice.model.Bike;
import com.apaza.usermicroservice.model.Car;
import com.apaza.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarFeignClient carFeignClient;

    @Autowired
    BikeFeignClient bikeFeignClient;



    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public User save (User user){

        User userNew = userRepository.save(user);
        return userNew;
    }


    public Car saveCar(int userId, Car car) {

        car.setUserId(userId);
        Car carNew = carFeignClient.save( car);
        return carNew;
    }

    public Bike saveBike(int userId , Bike bike){
        bike.setUserId(userId);
        Bike bikenew = bikeFeignClient.save(bike);

        return bikenew;
    }


}
