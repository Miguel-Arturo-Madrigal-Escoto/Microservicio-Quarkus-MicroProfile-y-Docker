package com.madrigal.controller;

import com.madrigal.model.Pokemon;
import org.eclipse.microprofile.faulttolerance.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Path("/pokemons")
@Produces(MediaType.APPLICATION_JSON)
public class PokemonController {

    List<Pokemon> pokemonList = new ArrayList<>();
    Logger LOGGER = Logger.getLogger("logger-app");

    //metodo para obtener los pokemon
    @GET
    //@Timeout(value = 5)
    //@Retry(maxRetries = 4)
    //@CircuitBreaker(failureRatio = 0.1, delay = 15000L)
    //@Bulkhead(value = 1)
    @Fallback(fallbackMethod = "getPersonFallbackList")
    public List<Pokemon> getPokemonList(){
        LOGGER.info("Ejecutando pokemon list");
        doFail();
        //doWait();

        var pikachu = new Pokemon(1, "Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
        var chamander = new Pokemon(-2, "Chamander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png");
        var bulbasaur = new Pokemon(-1, "Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");

        return List.of(pikachu, chamander, bulbasaur);
    }

    public List<Pokemon> getPersonFallbackList(){
        var poke = new Pokemon(-1, "Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");

        return List.of(poke);
    }

    public void doWait(){
        Random random = new Random();
        try{
            LOGGER.info("Haciendo un sleep");
            Thread.sleep(random.nextInt(10 + 4) * 1000);
        } catch (Exception ex){

        }

    }

    public void doFail(){
        Random random = new Random();
        if (random.nextBoolean()){
            LOGGER.warning("Se produce una falla");
            throw new RuntimeException("La implementación ha fallado");
        }
    }

}
