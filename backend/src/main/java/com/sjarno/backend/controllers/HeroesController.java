package com.sjarno.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjarno.backend.models.responses.GenericResponse;
import com.sjarno.backend.models.responses.HeroDto;
import com.sjarno.backend.models.responses.HeroesListDto;
import com.sjarno.backend.services.HeroService;

@RequestMapping("/api/heroes")
@RestController
public class HeroesController {
    
    @Autowired
    private HeroService heroService;

    @GetMapping("/all-heroes")
    public GenericResponse<HeroesListDto> getAllHeroes() {
        return this.heroService.getAllHeroes();
    }
    @GetMapping("/hero/{id}")
    public GenericResponse<HeroDto> getHeroById(@PathVariable Long id) {
        return this.heroService.getHeroById(id);
    }
    @PutMapping("/update")
    public GenericResponse<HeroDto> updateHero(@RequestBody HeroDto heroDto) {
        return this.heroService.updateHero(heroDto);
    }
    @PostMapping("/create")
    public GenericResponse<HeroDto> createHero(@RequestBody HeroDto heroDto) {
        return this.heroService.createHero(heroDto);
    }
    @DeleteMapping("/delete/{id}")
    public GenericResponse<HeroDto> deleteHeroById(@PathVariable Long id) {
        return this.heroService.deleteHeroById(id);
    }
    @GetMapping("/find-by-name")
    public GenericResponse<HeroesListDto> findHeroesByName(@RequestParam String name) {
        return this.heroService.findHeroesByName(name);
    }
}
