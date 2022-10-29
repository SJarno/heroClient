package com.sjarno.backend.api.rest.client.configurations;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sjarno.backend.api.rest.client.services.WebClientConfiguration;
import com.sjarno.backend.models.responses.GenericResponse;
import com.sjarno.backend.models.responses.HeroDto;
import com.sjarno.backend.models.responses.HeroServiceResponse;
import com.sjarno.backend.models.responses.HeroesListDto;

/**
 * Call to client service layer and mappings here.
 */
@Service
public class HeroServiceClient {

    private final String CLIENT_STATUS;
    private final String CLIENT_MAIN_TITLE;
    private final String CLIENT_ALL_HEROES;
    private final String CLIENT_HERO_BY_ID;
    private final String CLIENT_UPDATE_HERO;
    private final String CLIENT_CREATE_HERO;
    private final String CLIENT_DELETE_HERO;
    private final String CLIENT_FIND_BY_NAME;
    private String[] whiteList;

    @Autowired
    private ModelMapper mapper;

    public HeroServiceClient(EndpointConfiguration endpointConfiguration) {
        whiteList = endpointConfiguration.getWhitelist();
        this.CLIENT_STATUS = whiteList[0];
        this.CLIENT_MAIN_TITLE = whiteList[1];
        this.CLIENT_ALL_HEROES = whiteList[2];
        this.CLIENT_HERO_BY_ID = whiteList[3];
        this.CLIENT_UPDATE_HERO = whiteList[4];
        this.CLIENT_CREATE_HERO = whiteList[5];
        this.CLIENT_DELETE_HERO = whiteList[6];
        this.CLIENT_FIND_BY_NAME = whiteList[7];
    }

    @Autowired
    private WebClientConfiguration webClient;

    public GenericResponse<Map<String, String>> getStatus() {
        // log.info(String.format("Get status from service == {0}", CLIENT_STATUS));
        HeroServiceResponse<?> clientResponse = this.webClient.getHeroesResponse(this.CLIENT_STATUS).block();
        Map<String, String> map = mapper.map(clientResponse.getBody(), Map.class);
        GenericResponse<Map<String, String>> response = new GenericResponse<>() {
            {
                // String responseBody = (String) clientResponse.getBody();
                setStatus(HttpStatus.OK.toString());
                setType("Client response");
                setBody(map);
                setMessage("Response from Hero service");
            }
        };
        // GenericResponse<Map<String, String>> response =
        // mapper.map(clientResponse.getBody(), GenericResponse.class);
        return response;

    }

    public GenericResponse<String> getTitle() {

        HeroServiceResponse<?> serviceResponse = this.webClient.getHeroesResponse(this.CLIENT_MAIN_TITLE).block();
        GenericResponse<String> response = new GenericResponse<>() {
            {
                String title = mapper.map(serviceResponse.getBody(), String.class);
                setStatus(HttpStatus.OK.toString());
                setBody(title);
                setType("Backend response");
                setMessage(String.format("Response from hero service"));
            }
        };
        return response;

    }

    public GenericResponse<HeroesListDto> getAllHeroes() {
        HeroServiceResponse<?> serviceResponse = this.webClient.getHeroesResponse(this.CLIENT_ALL_HEROES).block();
        GenericResponse<HeroesListDto> response = new GenericResponse<>() {
            {
                HeroesListDto heroes = mapper.map(serviceResponse.getBody(), HeroesListDto.class);
                setStatus(HttpStatus.OK.toString());
                setBody(heroes);
                setType("Backend response");
                setMessage(String.format("The response from client == %s", serviceResponse.getMessage()));
            }
        };
        return response;

    }

    public GenericResponse<HeroDto> getHeroById(Long id) {
        HeroServiceResponse<?> serviceResponse = this.webClient.getHeroesResponse(this.CLIENT_HERO_BY_ID + id).block();
        GenericResponse<HeroDto> response = new GenericResponse<>() {
            {
                HeroDto hero = mapper.map(serviceResponse.getBody(), HeroDto.class);
                setStatus(serviceResponse.getStatus());
                setBody(hero);
                setType("Service response");
                setMessage("Response from service == " + serviceResponse.getMessage());
            }

        };

        return response;
    }

    public GenericResponse<HeroDto> updateHero(HeroDto hero) {
        HeroServiceResponse<?> serviceResponse = this.webClient.updateHero(this.CLIENT_UPDATE_HERO, hero).block();
        GenericResponse<HeroDto> response = new GenericResponse<>() {
            {
                HeroDto hero = mapper.map(serviceResponse.getBody(), HeroDto.class);
                setStatus(serviceResponse.getStatus());
                setBody(hero);
                setType("Service response");
                setMessage("Response from service == " + serviceResponse.getMessage());
            }
        };
        return response;

    }

    public GenericResponse<HeroDto> createHero(HeroDto heroDto) {
        HeroServiceResponse<?> serviceResponse = this.webClient.createHero(this.CLIENT_CREATE_HERO, heroDto).block();
        GenericResponse<HeroDto> response = new GenericResponse<>() {
            {
                HeroDto heroDto = mapper.map(serviceResponse.getBody(), HeroDto.class);
                setStatus(serviceResponse.getStatus());
                setBody(heroDto);
                setType("Service response");
                setMessage("Response from service == " + serviceResponse.getMessage());
            }
        };
        return response;
    }

    public GenericResponse<HeroDto> deleteHero(Long id) {
        HeroServiceResponse<?> serviceResponse = this.webClient.deleteHero(this.CLIENT_DELETE_HERO + id).block();
        GenericResponse<HeroDto> response = new GenericResponse<>() {
            {
                HeroDto heroDto = mapper.map(serviceResponse.getBody(), HeroDto.class);
                setStatus(serviceResponse.getStatus());
                setBody(heroDto);
                setType("Service response");
                setMessage("Response from service == " + serviceResponse.getMessage());
            }
        };
        return response;
    }

    public GenericResponse<HeroesListDto> findHeroesByName(String name) {
        HeroServiceResponse<?> serviceResponse = this.webClient.findHeroesByName(this.CLIENT_FIND_BY_NAME, name)
                .block();
        GenericResponse<HeroesListDto> response = new GenericResponse<>() {
            {
                HeroesListDto heroes = mapper.map(serviceResponse.getBody(), HeroesListDto.class);
                setStatus(serviceResponse.getStatus());
                setBody(heroes);
                setType("Backend response");
                setMessage(String.format("The response from service == %s", serviceResponse.getMessage()));
            }
        };
        return response;
    }

}
