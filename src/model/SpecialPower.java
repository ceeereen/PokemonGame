package model;

public class SpecialPower {
    private  String name;
    private int extraDamege;
    private int remainingRight;

    public SpecialPower(String name, int extraDamege, int remainingRight) {
        this.name = name;
        this.extraDamege = extraDamege;
        this.remainingRight = remainingRight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExtraDamege() {
        return extraDamege;
    }

    public void setExtraDamege(int extraDamege) {
        this.extraDamege = extraDamege;
    }

    public int getRemainingRight() {
        return remainingRight;
    }

    public void setRemainingRight(int remainingRight) {
        this.remainingRight = remainingRight;
    }

    @Override
    public String toString() {
        return "SpecialPower{" +
                "name='" + name + '\'' +
                ", extraDamege=" + extraDamege +
                ", remainingRight=" + remainingRight +
                '}';
    }
}
