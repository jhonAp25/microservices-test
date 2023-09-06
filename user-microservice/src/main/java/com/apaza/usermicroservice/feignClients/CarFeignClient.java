package com.apaza.usermicroservice.feignClients;


import com.apaza.usermicroservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "cars-microservice" , url = "http://localhost:8002" , path = "/car")
public interface CarFeignClient {

    @PostMapping()
    Car save(@RequestBody  Car car);

    @GetMapping("/byuser/{userId}")
    List<Car> getCar (@PathVariable("userId") int userId );


}
