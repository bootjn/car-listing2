package com.example.demo;



import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CateRepository cateRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", cateRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newCar(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("categories", cateRepository.findAll());
        return "form";
    }

    @PostMapping("/add")
    public String processCar(@ModelAttribute("car") Car car, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Picture is empty");
            return "carform";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            car.setPictureUrl(uploadResult.get("url").toString());
            carRepository.save(car);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }

    @GetMapping("/addCat")
    public String newType(Model model){
        model.addAttribute("cate", new Cate());
        return "cateform";
    }

    @PostMapping("/addCat")
    public String processCategory(@ModelAttribute("cate") Cate cate){
        cateRepository.save(cate);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model) {
        model.addAttribute("car", carRepository.findById(id));
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id) {
        carRepository.deleteById(id);
        return "redirect:/";
    }


    @RequestMapping("/detailcategory/{id}")
    public String showCategory (@PathVariable("id") long id, Model model){
        model.addAttribute("cate", cateRepository.findById(id).get());
        return "form";
    }

    @RequestMapping("/updatecategory/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("cate", cateRepository.findById(id));
        return "cateform";
    }

    @RequestMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        cateRepository.deleteById(id);
        return "redirect:/";
    }


}
