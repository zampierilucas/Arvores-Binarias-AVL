package myArvoreBinaria;

public class Arvore {
  static NodoDado raiz;
  
  public static void main(String[] args) {

    // Verificacao de balancemento 
    // https://www.youtube.com/watch?v=Au-6c55J90c

    insere(1);
    insere(2);
    insere(3);
    insere(10);
    insere(4);
    insere(5);
    insere(9);
    insere(7);
    insere(8);
    insere(6);
    
    
    print("Resultados Desbalanceados:\n");
    print("Lista Ordenada:\n");
    imprime_Ordenado(raiz);

    print("\nLista Pre-Ordenada:\n");
    imprime_PreOrdenado(raiz);

    print("\nLista Pos-Ordenada:\n");
    imprime_PosOrdenado(raiz);
    
    verificarBalanceamento(raiz);
    
    print("\n\nResultados Balanceados");
    print("\nLista Ordenada:\n");
    imprime_Ordenado(raiz);

    print("\nLista Pre-Ordenada:\n");
    imprime_PreOrdenado(raiz);

    print("\nLista Pos-Ordenada:\n");
    imprime_PosOrdenado(raiz);
    
  }
  

  //-------------------------------------------------------------------------//
  //                    METODOS DE BALANCEMENTO                              //
  //-------------------------------------------------------------------------//
  
  // Verifica balancemento da arvore
  private static void verificarBalanceamento(NodoDado no) {
    
    setBalanceamento(no);
    int balanceamento = no.getBalanceamento();
    

    if (balanceamento == 2) {

      if (altura(no.getEsq().getEsq()) >= altura(no.getEsq().getDir())) 
          no = rotacaoDireita(no);

      else
          no = duplaRotacaoEsquerdaDireita(no);

    }
    else if (balanceamento == -2) {
      if (altura(no.getDir().getDir()) >= altura(no.getDir().getEsq()))
          no = rotacaoEsquerda(no);

      else 
          no = duplaRotacaoDireitaEsquerda(no);
      
    }

    if (no.getPai() != null)
        verificarBalanceamento(no.getPai());
     else 
        raiz = no;
    
  }
  
  // Define Balancemento de um nodo
  private static void setBalanceamento(NodoDado no) {
    no.setBalanceamento(altura(no.getEsq()) - altura(no.getDir()));  
  }

  // Verifica altura apartir de um nodo
  private static int altura(NodoDado no) {
    if(no == null)
      return -1;
    
    else if(no.getEsq() == null && no.getDir() == null)
      return 0;
    
    else if(no.getEsq() == null) 
      return 1 + altura(no.getDir());

    else if(no.getDir() == null)
      return 1 + altura(no.getEsq());
    
    else
      return 1 + Math.max( altura(no.getDir()), altura(no.getEsq()));
  }
  
  
  //-------------------------------------------------------------------------//
  //                     METODOS DE ROTACAO                                  //
  //-------------------------------------------------------------------------//
  
  public static NodoDado duplaRotacaoEsquerdaDireita(NodoDado inicial) {
    inicial.setEsq(rotacaoEsquerda(inicial.getEsq()));
    
    return rotacaoDireita(inicial);
  }

  public static NodoDado duplaRotacaoDireitaEsquerda(NodoDado inicial) {
    inicial.setDir(rotacaoDireita(inicial.getDir()));
  
    return rotacaoEsquerda(inicial);
  }

  public static NodoDado rotacaoEsquerda(NodoDado inicial) {

    NodoDado direita = inicial.getDir();
    direita.setPai(inicial.getPai());

    inicial.setDir(direita.getEsq());

    if (inicial.getDir() != null) {
        inicial.getDir().setPai(inicial);
    }

    direita.setEsq(inicial);
    inicial.setPai(direita);

    // se no nao for raiz
    if (direita.getPai() != null) {

        if (direita.getPai().getDir() == inicial) 
            direita.getPai().setDir(direita);
        
        else if (direita.getPai().getEsq() == inicial)
            direita.getPai().setEsq(direita);
        
    }

    setBalanceamento(inicial);
    setBalanceamento(direita);

    return direita;
  }

  public static NodoDado rotacaoDireita(NodoDado inicial) {

    NodoDado esquerda = inicial.getEsq();
    esquerda.setPai(inicial.getPai());

    inicial.setEsq(esquerda.getDir());

    if (inicial.getEsq() != null)
        inicial.getEsq().setPai(inicial);
    

    esquerda.setDir(inicial);
    inicial.setPai(esquerda);

    // se no nao for raiz
    if (esquerda.getPai() != null) {
        if (esquerda.getPai().getDir() == inicial) 
            esquerda.getPai().setDir(esquerda);
        
        else if (esquerda.getPai().getEsq() == inicial)
            esquerda.getPai().setEsq(esquerda);
        
    }

    setBalanceamento(inicial);
    setBalanceamento(esquerda);

    return esquerda;
  }


  //-------------------------------------------------------------------------//
  //                    METODOS DE IMPRESSAO                                 //
  //-------------------------------------------------------------------------//
  
  // Ordenado
  private static void imprime_Ordenado(NodoDado no) {
    if(no == null)
      return;
    
    imprime_Ordenado(no.getEsq());
    print(" | " + no.getDado()+"["+no.getBalanceamento()+"]");
    imprime_Ordenado(no.getDir());
  }
  
  // Pre-Ordenado
  private static void imprime_PreOrdenado(NodoDado no) {
    if(no == null)
      return;
    
    print(" | " + no.getDado()+"["+no.getBalanceamento()+"]");
    imprime_PreOrdenado(no.getEsq());
    imprime_PreOrdenado(no.getDir());   
  }

  // Pos-Ordenado
  private static void imprime_PosOrdenado(NodoDado no) {
    if(no == null)
      return;
    
    imprime_PosOrdenado(no.getEsq());
    imprime_PosOrdenado(no.getDir());
    print(" | " + no.getDado()+"["+no.getBalanceamento()+"]");
  }

  
  //-------------------------------------------------------------------------//
  //                    METODOS DE INSERCAO                                  //
  //-------------------------------------------------------------------------//
  public static void insereAVL(NodoDado aComparar, NodoDado aInserir) {
        
    // defina uma raiz
    if(raiz == null)
       raiz = aInserir;
 
    // Se raiz ja foi definido
    else {
      // Verifica se o novo nodo é menor q o atual
      if(aInserir.getDado() < aComparar.getDado()) {
        if(aComparar.getEsq() == null) {
          aComparar.setEsq(aInserir);
          aInserir.setPai(aComparar);
          verificarBalanceamento(aComparar);
        }
        else 
          insereAVL(aComparar.getEsq(),aInserir);
      }
      // Verifica se o novo nodo é maior q o atual
      else if(aInserir.getDado() > aComparar.getDado()){
        if(aComparar.getDir() == null) {
          aComparar.setDir(aInserir);
          aInserir.setPai(aComparar);
          verificarBalanceamento(aComparar);
        }
        else
          insereAVL(aComparar.getDir(),aInserir);
      }
      // Novo nodo é igual ao atual
      else {
        aComparar.addQuantNodo();
      }
    }
  }
  
  public static void insere(int i) {
    insereAVL(raiz,new NodoDado(i));
  }
  
  //-------------------------------------------------------------------------//
  //                    METODOS DE REMOCAO                                   //
  //-------------------------------------------------------------------------//
  
  //TODO
  
  //-------------------------------------------------------------------------//
  //                   METODOS DE AUXILIARES                                 //
  //-------------------------------------------------------------------------//
  public static void print(Object... args) {
    System.out.print(args[0]);
  }
} 
