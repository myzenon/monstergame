package se301.monstergame.controller;
  
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
  
}