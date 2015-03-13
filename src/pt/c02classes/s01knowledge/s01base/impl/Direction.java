package pt.c02classes.s01knowledge.s01base.impl;

public enum Direction {

    NORTE("norte"),
    LESTE("leste"),
    SUL("sul"),
    OESTE("oeste");

    private String dir;

    private Direction(String dir){
        this.dir = dir;
    }

    public String getStr() {

        return dir;
    }

    public Direction proximo() {

        return values()[(this.ordinal() + 1) % values().length];
    }

    public Direction anterior() {

        return values()[(this.ordinal() + values().length - 1) % values().length];
    }

    public Direction revert() {

        return values()[(this.ordinal() + 2) % values().length];
    }

    public static Direction from(String direction) {
        switch (direction) {
            case "norte": return NORTE;
            case "leste": return LESTE;
            case "sul": return SUL;
            case "oeste": return OESTE;
            default: throw new IllegalArgumentException("direction");
        }
    }
}
