package myArvoreBinaria;

public class NodoDado {

  String dado;
  int quantNodo;
  int balanceamento;
  NodoDado esq;
  NodoDado dir;
  NodoDado pai;
  
  public NodoDado(String info) {
    this.dado = info;
    this.esq = null;
    this.dir = null;
    this.quantNodo = 1; 
  }
  
  public String getDado() {
    return dado;
  }
  
  public void setDado(String dado) {
    this.dado = dado;
  }
  
  public int getQuantNodo() {
    return quantNodo;
  }

  public void addQuantNodo() {
    this.quantNodo++;
  }

  public NodoDado getEsq() {
    return esq;
  }
  public NodoDado getDir() {
    return dir;
  }
  public NodoDado getPai() {
    return pai;
  }
  public void setEsq(NodoDado esq) {
    this.esq = esq;
  }
  public void setDir(NodoDado dir) {
    this.dir = dir;
  }
  public void setPai(NodoDado pai) {
    this.pai = pai;
  }
  public int getBalanceamento() {
    return balanceamento;
  }
  public void setBalanceamento(int balanceamento) {
    this.balanceamento = balanceamento;
  }
}