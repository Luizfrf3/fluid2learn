package pt.c02classes.s01knowledge.s01base.impl;

public class Posicao {
    public int x = 0;
    public int y = 0;
    public Posicao(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Posicao seAndasse(Direction dir){
        Posicao resp = new Posicao(x, y);
        resp.x += (dir.ordinal() % 2) * ((dir.ordinal() < 2) ? 1 : -1);
        resp.y += ((dir.ordinal() + 1) % 2) * ((dir.ordinal() < 1) ? 1: -1);
        return resp;
    }
    public void anda(Direction dir){
        this.x += (dir.ordinal() % 2) * ((dir.ordinal() < 2) ? 1 : -1);
        this.y += ((dir.ordinal() + 1) % 2) * ((dir.ordinal() < 1) ? 1: -1);
    }
}