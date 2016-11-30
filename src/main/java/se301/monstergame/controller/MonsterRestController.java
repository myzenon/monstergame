package se301.monstergame.controller;
  
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import se301.monstergame.model.Monster;
import se301.monstergame.service.MonsterService;
  
@RestController
public class MonsterRestController {
  
    @Autowired
    MonsterService monsterService;  //Service which will do all data retrieval/manipulation work
  
     
    //-------------------Retrieve All Monsters--------------------------------------------------------
      
    @RequestMapping(value = "/monster/", method = RequestMethod.GET)
    public ResponseEntity<List<Monster>> listAllMonsters() {
        List<Monster> monsters = monsterService.findAllMonsters();
        if(monsters.isEmpty()){
            return new ResponseEntity<List<Monster>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Monster>>(monsters, HttpStatus.OK);
    }
  
  
     
    //-------------------Retrieve Single Monster--------------------------------------------------------
      
    @RequestMapping(value = "/monster/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monster> getMonster(@PathVariable("id") long id) {
        System.out.println("Fetching Monster with id " + id);
        Monster monster = monsterService.findById(id);
        if (monster == null) {
            System.out.println("Monster with id " + id + " not found");
            return new ResponseEntity<Monster>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Monster>(monster, HttpStatus.OK);
    }
  
      
      
    //-------------------Create a Monster--------------------------------------------------------
      
    @RequestMapping(value = "/monster/", method = RequestMethod.POST)
    public ResponseEntity<Void> createMonster(@RequestBody Monster monster,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Monster " + monster.getMonstername());
  
        if (monsterService.doesMonsterExist(monster)) {
            System.out.println("A Monster with name " + monster.getMonstername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        monsterService.saveMonster(monster);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/monster/{id}").buildAndExpand(monster.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
     
      
    //------------------- Update a Monster --------------------------------------------------------
      
    @RequestMapping(value = "/monster/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Monster> updateMonster(@PathVariable("id") long id, @RequestBody Monster monster) {
        System.out.println("Updating Monster " + id);
          
        Monster currentMonster = monsterService.findById(id);
          
        if (currentMonster==null) {
            System.out.println("Monster with id " + id + " not found");
            return new ResponseEntity<Monster>(HttpStatus.NOT_FOUND);
        }
  
        currentMonster.setMonstername(monster.getMonstername());
        currentMonster.setHealth(monster.getHealth());
        currentMonster.setAttack(monster.getAttack());
          
        monsterService.updateMonster(currentMonster);
        return new ResponseEntity<Monster>(currentMonster, HttpStatus.OK);
    }
  
     
     
    //------------------- Delete a Monster --------------------------------------------------------
      
    @RequestMapping(value = "/monster/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Monster> deleteMonster(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Monster with id " + id);
  
        Monster monster = monsterService.findById(id);
        if (monster == null) {
            System.out.println("Unable to delete. Monster with id " + id + " not found");
            return new ResponseEntity<Monster>(HttpStatus.NOT_FOUND);
        }
  
        monsterService.deleteMonsterById(id);
        return new ResponseEntity<Monster>(HttpStatus.NO_CONTENT);
    }
  
      
     
    //------------------- Delete All Monsters --------------------------------------------------------
      
    @RequestMapping(value = "/monster/", method = RequestMethod.DELETE)
    public ResponseEntity<Monster> deleteAllMonsters() {
        System.out.println("Deleting All Monsters");
  
        monsterService.deleteAllMonsters();
        return new ResponseEntity<Monster>(HttpStatus.NO_CONTENT);
    }


    //------------------- Battle Monsters : Option 1 -------------------------------------------------

    @PostMapping(value = "/monster/battle/")
    public ResponseEntity<Monster> battleMonster(@RequestBody List<Monster> monsters) {

        Monster monster1 = monsterService.findById(monsters.get(0).getId());
        if (monster1== null) {
            System.out.println("Monster with id " + monsters.get(0).getId() + " not found");
            return new ResponseEntity<Monster>(HttpStatus.NOT_FOUND);
        }

        Monster monster2 = monsterService.findById(monsters.get(1).getId());
        if (monster2== null) {
            System.out.println("Monster with id " + monsters.get(1).getId() + " not found");
            return new ResponseEntity<Monster>(HttpStatus.NOT_FOUND);
        }

        Monster firstAtk, secondAtk;


        if((((int) Math.random() *2) + 1) == 1) {
            firstAtk = monster1;
            secondAtk = monster2;
        }
        else {
            firstAtk = monster2;
            secondAtk = monster1;
        }
        long monster1Health = firstAtk.getHealth();
        long monster2Health = secondAtk.getHealth();

        while(true) {
            monster2Health -= firstAtk.getAttack();
            System.out.println(firstAtk.getMonstername() + " Attack " + secondAtk.getMonstername() + " | Health : " + monster2Health);
            if(monster2Health <= 0) {
                monster2Health = 0;
                break;
            }
            monster1Health -= secondAtk.getAttack();
            System.out.println(secondAtk.getMonstername() + " Attack " + firstAtk.getMonstername() + " | Health : " + monster1Health);
            if(monster1Health <= 0) {
                monster1Health = 0;
                break;
            }
        }

        Monster winMonster;
        if(monster1Health == 0) {
            winMonster = secondAtk;
        }
        else {
            winMonster = firstAtk;
        }

        return new ResponseEntity<Monster>(winMonster, HttpStatus.OK);
    }
  
}