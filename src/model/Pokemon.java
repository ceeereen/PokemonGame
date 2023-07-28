package model;

public class Pokemon {
    private String name;
    private int health;
    private int damage;
    private TypeEnum typeEnum;

    private SpecialPower specialPower;


    public Pokemon(String name, int health, int damage, TypeEnum typeEnum, SpecialPower specialPower) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.typeEnum = typeEnum;
        this.specialPower = specialPower;
    }

    public int specialAttack(){
        if(this.specialPower.getRemainingRight()>0){
            this.specialPower.setRemainingRight(this.specialPower.getRemainingRight()-1);
            return this.damage + this.specialPower.getExtraDamege();
        }else{
            System.out.println("There is no right");
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public SpecialPower getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(SpecialPower specialPower) {
        this.specialPower = specialPower;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                ", typeEnum=" + typeEnum +
                ", specialPower=" + specialPower +
                '}';
    }
}
