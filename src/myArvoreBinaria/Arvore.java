package myArvoreBinaria;

public class Arvore {
  static NodoDado raiz;
  
  public static void main(String[] args) {

    // Verificacao de balancemento 
    // https://www.youtube.com/watch?v=Au-6c55J90c

    //convertTextToNodes("hello world");
    
    convertTextToNodes(
        "Do not go gentle into that good night,\r\n" + 
        "Old age should burn and rave at close of day;\r\n" + 
        "Rage, rage against the dying of the light.\r\n" + 
        "\r\n" + 
        "Though wise men at their end know dark is right,\r\n" + 
        "Because their words had forked no lightning they\r\n" + 
        "Do not go gentle into that good night.\r\n" + 
        "\r\n" + 
        "Good men, the last wave by, crying how bright\r\n" + 
        "Their frail deeds might have danced in a green bay,\r\n" + 
        "Rage, rage against the dying of the light.\r\n" + 
        "\r\n" + 
        "Wild men who caught and sang the sun in flight,\r\n" + 
        "And learn, too late, they grieved it on its way,\r\n" + 
        "Do not go gentle into that good night.\r\n" + 
        "\r\n" + 
        "Grave men, near death, who see with blinding sight\r\n" + 
        "Blind eyes could blaze like meteors and be gay,\r\n" + 
        "Rage, rage against the dying of the light.\r\n" + 
        "\r\n" + 
        "And you, my father, there on the sad height,\r\n" + 
        "Curse, bless, me now with your fierce tears, I pray.\r\n" + 
        "Do not go gentle into that good night.\r\n" + 
        "Rage, rage against the dying of the light.");
    
    print("Resultados Desbalanceados:");
    printArvoreOrdenada();
    
    print("\n\nResultados Balanceados: ");
    verificarBalanceamento(raiz);
    printArvoreOrdenada();
    
  }
  private static void convertTextToNodes(String txt) {
    String[] txtArray =  txt.toUpperCase().split("[-., \\n\\r\\s]+");
     
    for (String txtNode : txtArray)  
    {
      insere(txtNode);
    }
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
  
  // Print all
  static void printArvoreOrdenada() {
    print("\nLista Ordenada:(no[balanceamento][quantidade])\n");
    imprime_Ordenado(raiz);
    
    /*
    print("\nLista Pre-Ordenada:\n");
    imprime_PreOrdenado(raiz);

    print("\nLista Pos-Ordenada:\n");
    imprime_PosOrdenado(raiz);
    */
  }
  
  // Ordenado
  private static void imprime_Ordenado(NodoDado no) {
    if(no == null)
      return;
    
    imprime_Ordenado(no.getEsq());
    print(" | " + no.getDado() + "["+ no.getBalanceamento() + "][" + no.getQuantNodo() + "]");
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
    int ALFABETICAMENTE_MENOR = -1;    
    int ALFABETICAMENTE_MAIOR = 1;
    // defina uma raiz
    if(raiz == null)
       raiz = aInserir;
 
    // Se raiz ja foi definido
    else {
      // Verifica se o novo nodo é menor q o atual
      if(aInserir.getDado().compareToIgnoreCase(aComparar.getDado()) <= ALFABETICAMENTE_MENOR) {
        if(aComparar.getEsq() == null) {
          aComparar.setEsq(aInserir);
          aInserir.setPai(aComparar);
          verificarBalanceamento(aComparar);
        }
        else 
          insereAVL(aComparar.getEsq(),aInserir);
      }
      // Verifica se o novo nodo é maior q o atual
      else if(aInserir.getDado().compareToIgnoreCase(aComparar.getDado()) >= ALFABETICAMENTE_MAIOR){
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
  
  public static void insere(String txt) {
    insereAVL(raiz,new NodoDado(txt));
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
