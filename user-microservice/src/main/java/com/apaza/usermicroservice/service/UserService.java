package com.apaza.usermicroservice.service;


import com.apaza.usermicroservice.entity.User;
import com.apaza.usermicroservice.feignClients.BikeFeignClient;
import com.apaza.usermicroservice.feignClients.CarFeignClient;
import com.apaza.usermicroservice.model.Bike;
import com.apaza.usermicroservice.model.Car;
import com.apaza.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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


    public Map<String , Object> getUserAndVehicles(int id){

        Map<String , Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            result.put("Mensaje" , "No existe el usuario");
            return result;
        }
        result.put("User" , user);

        List<Car> cars =    carFeignClient.getCar(id);
        if (cars.isEmpty())
            result.put("Cars", "Este usuario no tiene carro");
        else
            result.put("Cars", cars);

        List<Bike>bikes = bikeFeignClient.getBike(id);

        if (bikes.isEmpty())
            result.put("Bikes", "Este usuario no tiene BIKES");
        else
            result.put("Bikes", bikes);

        return result;


    }


}
