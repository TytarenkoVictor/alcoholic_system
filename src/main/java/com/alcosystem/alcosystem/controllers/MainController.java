package com.alcosystem.alcosystem.controllers;

import com.alcosystem.alcosystem.models.Action;
import com.alcosystem.alcosystem.models.AlcoPerDepartment;
import com.alcosystem.alcosystem.models.Alcoholic;
import com.alcosystem.alcosystem.models.Bed;
import com.alcosystem.alcosystem.models.BedAveragePasses;
import com.alcosystem.alcosystem.models.BedColorQuantity;
import com.alcosystem.alcosystem.models.Drink;
import com.alcosystem.alcosystem.models.DrinkQuantity;
import com.alcosystem.alcosystem.models.Inspector;
import com.alcosystem.alcosystem.repository.MainRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private final MainRepository repository;

    public MainController(MainRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/request1")
    public List<Inspector> request1(Long alcoid, Long quantity, String from, String to) {
        System.out.println("alcoid=" + alcoid + " quantity=" + quantity + " from=" + from + " to=" + to);
        return repository.request1(alcoid, quantity, from, to);
    }

    @GetMapping("/request2")
    public List<Bed> request2(Long alcoid, String from, String to) {
        return repository.request2(alcoid, from, to);
    }

    @GetMapping("/request3")
    public List<Alcoholic> request3(Long Inspectorid, Long quantity, String from, String to) {
        return repository.request3(Inspectorid, quantity, from, to);
    }

    @GetMapping("/request4")
    public List<Bed> request4(Long alcoid, String from, String to) {
        return repository.request4(alcoid, from, to);
    }

    @GetMapping("/request5")
    public List<Inspector> request5(Long alcoid) {
        return repository.request5(alcoid);
    }

    @GetMapping("/request6")
    public List<Inspector> request6(Long quantity, String from, String to) {
        return repository.request6(quantity, from, to);
    }

    @GetMapping("/request7")
    public List<Alcoholic> request7(Long quantity, String from, String to) {
        return repository.request7(quantity, from, to);
    }

    @GetMapping("/request8")
    public List<Action> request8(Long alcoid, Long inspectorid, String from, String to) {
        return repository.request8(alcoid, inspectorid, from, to);
    }

    @GetMapping("/request9")
    public List<DrinkQuantity> request9(Long alcoid, Long quantity, String from, String to) {
        return repository.request9(alcoid, quantity, from, to);
    }

    @GetMapping("/request10")
    public List<Map<String, Long>> request10() {
        return Collections.singletonList(repository.request10());
    }

    @GetMapping("/request11")
    public List<BedAveragePasses> request11(Long inspectorid, String from, String to) {
        return repository.request11(inspectorid, from, to);
    }

    @GetMapping("/request12")
    public List<DrinkQuantity> request12(Long alcoid, String from, String to) {
        return repository.request12(alcoid, from, to);
    }

    @GetMapping("/request13")
    public List<AlcoPerDepartment> request13(String from, String to) {
        return repository.request13(from, to);
    }

    @GetMapping("/request14")
    public List<BedColorQuantity> request14() {
        return repository.request14();
    }

    @PostMapping("/updateDrink")
    public List<Drink> updateDrink(Long drinkId, String drinkName, Integer degree) {
        Drink drink = new Drink(drinkId, drinkName, degree);
        return repository.updateDrink(drink);
    }

}
