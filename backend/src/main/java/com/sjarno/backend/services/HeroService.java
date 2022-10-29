package com.sjarno.backend.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjarno.backend.api.rest.client.configurations.HeroServiceClient;
import com.sjarno.backend.models.responses.GenericResponse;
import com.sjarno.backend.models.responses.HeroDto;
import com.sjarno.backend.models.responses.HeroesListDto;

/**
 * Domain specific logic here.
  */
@Service
public class HeroService {
    
    @Autowired
    private HeroServiceClient heroServiceClient;

    public GenericResponse<Map<String, String>> getStatus() {
        return this.heroServiceClient.getStatus();
    }
    public GenericResponse<String> getTitle() {
        return this.heroServiceClient.getTitle();
    }
    public GenericResponse<HeroesListDto> getAllHeroes() {
        return this.heroServiceClient.getAllHeroes();
    }
    public GenericResponse<HeroDto> getHeroById(Long id) {
        return this.heroServiceClient.getHeroById(id);
    }
    public GenericResponse<HeroDto> updateHero(HeroDto hero) {
        return this.heroServiceClient.updateHero(hero);
    }
    public GenericResponse<HeroDto> createHero(HeroDto heroDto) {
        return this.heroServiceClient.createHero(heroDto);
    }
    public GenericResponse<HeroDto> deleteHeroById(Long id) {
        return this.heroServiceClient.deleteHero(id);
    }
    public GenericResponse<HeroesListDto> findHeroesByName(String name) {
        return this.heroServiceClient.findHeroesByName(name);
    }
}
