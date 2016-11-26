package se301.monstergame.model;

public class Monster {
 
    private long id;
     
    private String monstername;
     
    private long health;
     
    private long attack;
     
    public Monster(){
    }
     
    public Monster(long id, String monstername, long health, long attack){
        this.id = id;
        this.monstername = monstername;
        this.health = health;
        this.attack = attack;
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public String getMonstername() {
        return monstername;
    }
 
    public void setMonstername(String monstername) {
        this.monstername = monstername;
    }
 
    public long getHealth() {
        return health;
    }
 
    public void setHealth(long health) {
        this.health = health;
    }
 
    public long getAttack() {
        return attack;
    }
 
    public void setAttack(long attack) {
        this.attack = attack;
    }
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Monster))
            return false;
        Monster other = (Monster) obj;
        if (id != other.id)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "Monster [id=" + id + ", monstername=" + monstername + ", health=" + health
                + ", attack=" + attack + "]";
    }

}