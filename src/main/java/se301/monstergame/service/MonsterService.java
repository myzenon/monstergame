package se301.monstergame.service;
 
import java.util.List;

import se301.monstergame.model.Monster; 
 
public interface MonsterService {
     
	Monster findById(long id);
     
	Monster findByName(String name);
     
    void saveMonster(Monster monster);
     
    void updateMonster(Monster monster);
     
    void deleteMonsterById(long id);
 
    List<Monster> findAllMonsters(); 
     
    void deleteAllMonsters();
     
    public boolean doesMonsterExist(Monster monster);
     
}