package myArvoreBinaria;

import java.util.ArrayList;
import java.util.Scanner;

public class Arvore {
  static Scanner in = new Scanner(System.in);
  
  // Arvore AVL
  static NodoDado raiz;
  
  // Lista de textos
  static ArrayList<String> arrTextos = new ArrayList<String>();
  static String textoSelecionado;
  static int textoSelecionadoIndex;
  static int nPalavras = 0;
  static int nPalavrasTotal;
  static boolean achou = false;
  
  public static void main(String[] args) {

    menu();

    print("\n\nResultados Balanceados: ");
    printArvoreOrdenada();
    
  }
  //-------------------------------------------------------------------------//
  //                              MENU                                       //
  //-------------------------------------------------------------------------//
  enum menu{
    MENU,
    //GERAL
    GERAL_INSERIR_TEXTO,
    GERAL_SELECIONAR_TEXTO,
    //UNICO
    UNICO_VERIFICA_EXISTENCIA_DE_PALAVRA,
    UNICO_REMOVER_PALAVRA,
    UNICO_CONTA_N_PALAVRAS,
    UNICO_IMPRIME_AZ,
    UNICO_IMPRIME_PREFIXADA,
    UNICO_REMOVE_TEXTO,
    //TODOS
    TODOS_IMPRIME_AZ,
    TODOS_CONTA_N_PALAVRAS,
    TODOS_IMPRIME_PREFIXADO,
  }
  
  private static void menu() {
   
   boolean continuar = true;
   menu handler = menu.MENU;

   while(continuar)
   {
     switch(handler)
     {
       case MENU:
         print("\n\nTEXTO SELECIONADO [" +  (textoSelecionado == null ? "NONE" : textoSelecionado)+ "]"
             + "\n\nSelecione uma das opcoes:"
             + "\n1 - Incluir Textos"
             + "\n2 - Selecionar texto"
             + "\n\nOPERACOES NO TEXTO ATUAL:"
             + "\n3 - Verifica existencia de palavra"
             + "\n4 - Remover palavra do texto"
             + "\n5 - Conta o numero de palavras do texto"
             + "\n6 - Exibe palavras em ordem alfabetica"
             + "\n7 - Imprime lista pré-fixada"
             + "\n8 - Remover texto"
             + ""
             + "\n\nOPERACOES EM TODOS OS TEXTOS:"
             + "\n9  - Exibe palavras em ordem alfabetica"
             + "\n10 - Conta o numero de palavras em todos os textos"
             + "\n11 - Imprime listas pré-fixadas"
             + ""
             + "");
         
         handler = menu.values()[in.nextInt()];
         in.nextLine();
         break;
       case GERAL_INSERIR_TEXTO:
         inserir_texto();
         
         handler = menu.MENU;
         break;
       case GERAL_SELECIONAR_TEXTO:
         selecionar_texto();
         
         handler = menu.MENU;
         break;
       case UNICO_VERIFICA_EXISTENCIA_DE_PALAVRA:
         pensquisa_por_palavra();
         
         handler = menu.MENU;
         break;
       case UNICO_REMOVER_PALAVRA:
         print("Digite a palavra a ser removida");
         
         remover_uma_palavra(in.nextLine());
         
         handler = menu.MENU;
         break;
       case UNICO_CONTA_N_PALAVRAS:
         print("Numero de palavras no texto atual: " + nPalavras);
         
         handler = menu.MENU;
         break;
       case UNICO_IMPRIME_AZ:
         //TODO
         handler = menu.MENU;
         break;
       case UNICO_IMPRIME_PREFIXADA:
         imprime_PreOrdenado(raiz);
         
         handler = menu.MENU;
         break;
       case UNICO_REMOVE_TEXTO:
         remover_texto();
         
         handler = menu.MENU;
         break;
       case TODOS_IMPRIME_AZ:
       //TODO
         handler = menu.MENU;
         break;
       case TODOS_CONTA_N_PALAVRAS:
         print("Numero de palavras nos textos carregados nesta seccao: " + nPalavrasTotal);
         
         handler = menu.MENU;
         break;
       case TODOS_IMPRIME_PREFIXADO:
         //TODO
         
         handler = menu.MENU;
         break;
     
     default:
       handler = menu.MENU;
       break;
     } 
   }
  }


  //-------------------------------------------------------------------------//
  //                      METODOS DE PESQUISA                                //
  //-------------------------------------------------------------------------//
  
  private static void pensquisa_por_palavra() {
    print("Digite a palavra que deseja pesqusar");
    String pesquisar = in.nextLine();
    
    pesquisaAVL(pesquisar,raiz);
  }
  
  private static boolean pesquisaAVL(String pesquisar,NodoDado aComparar) {
    int ALFABETICAMENTE_MENOR = -1;    
    int ALFABETICAMENTE_MAIOR = 1;
    achou = false;
    
    if(raiz == null) {
      print("Nenhum nodo nesta arvore."); 
      return false;
    }
    else {
      // Verifica se o novo nodo é menor q o atual
      if(pesquisar.compareToIgnoreCase(aComparar.getDado()) <= ALFABETICAMENTE_MENOR) {
        if(aComparar.getEsq() == null) {
          print("Nao encontrado");
          return false;
        }
        else 
          pesquisaAVL(pesquisar,aComparar.getEsq());
      }
      // Verifica se o novo nodo é maior q o atual
      else if(pesquisar.compareToIgnoreCase(aComparar.getDado()) >= ALFABETICAMENTE_MAIOR){
        if(aComparar.getDir() == null) {
          print("Nao encontrado");
          return false;
        }
        else
          pesquisaAVL(pesquisar,aComparar.getDir());
      }
      else {
        print("\n" + aComparar.getQuantNodo() + " ocorrencias\n");
        achou = true;
        return true;
      }
    }
    return false;
  }
  //-------------------------------------------------------------------------//
  //                    METODOS DE BALANCEMENTO                              //
  //-------------------------------------------------------------------------//

  private static void selecionar_texto() {
    int index = 0;
    
    print("\nSelecione um dos textos:\n");
    for(String txt : arrTextos) 
    {
      print(index++ + " - ");

      print(txt.replace("\r","").replace("\n","").substring(0,Math.min(50,txt.length())));
        
      print("...\n");
   }
    print("\n\n");
    print("Selecione um dos textos");
    int Selecionado = in.nextInt();

    
    textoSelecionado = arrTextos.get(Selecionado).replace("\r","").replace("\n","").substring(0,Math.min(20,arrTextos.get(Selecionado).length()));
    textoSelecionadoIndex = Selecionado;
    
    // Reseta Arvore
    raiz = null;
    nPalavras = 0;
    
    convertTextToNodes(arrTextos.get(Selecionado));
  }


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

  
  //-------------------------------------------------------------------------//
  //                    METODOS DE INSERCAO                                  //
  //-------------------------------------------------------------------------//
  
  private static void inserir_texto() {
   
    arrTextos.add("Do not go gentle into that good night,\r\n" + 
        "Old age should burn and rave at close of day\r\n" + 
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
  
    arrTextos.add("Hello, world?");
    

  }

  
  private static void convertTextToNodes(String txt) {
    String[] txtArray = txt.toUpperCase().split("[-., \\n\\r\\s]+");
     
    for (String txtNode : txtArray)  
    {
      insere(txtNode);
      nPalavras++;
    }
   
    nPalavrasTotal += nPalavras;
  }
  
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
  
  private static void remover_texto() {
    print("Digite o texto a ser removido:");
    String aRemover = in.nextLine();
    
    Remover_nodos_em_texto(aRemover);
    
  }
  
  private static void Remover_nodos_em_texto(String txt) {
    int naoEncontradas = 0, nRemovidas = 0;
    
    String[] txtArray = txt.toUpperCase().split("[-., \\n\\r\\s]+");
    
    for (String txtNode : txtArray)  
    {
      pesquisaAVL(txtNode,raiz);
      if(achou) {
        remover_uma_palavra(txtNode);
        nRemovidas++;
      }
      else
        naoEncontradas++;
    }
    print(nRemovidas + " palavras removidas\n"
        + naoEncontradas + " nao encontrada para remocao\n");
    
    nPalavras -= nRemovidas;
  }
  
  public static void remover_uma_palavra(String txt) {
    removerAVL(raiz, txt);
  }
  
  public static void removerAVL(NodoDado atual, String removerN) {
    int ALFABETICAMENTE_MENOR = -1;    
    int ALFABETICAMENTE_MAIOR = 1;
    int ALFABETICAMENTE_IGUAL = 0;
    
    if (atual == null) 
      return;
  
    else {
      if (atual.getDado().compareToIgnoreCase(removerN) <= ALFABETICAMENTE_MENOR)
        removerAVL(atual.getEsq(), removerN);
      
      else if (atual.getDado().compareToIgnoreCase(removerN) >= ALFABETICAMENTE_MAIOR)  
        removerAVL(atual.getDir(), removerN);
      
      else if (atual.getDado().compareToIgnoreCase(removerN) == ALFABETICAMENTE_IGUAL)
        removerNoEncontrado(atual);
      
      }
  }
  
  public static void removerNoEncontrado(NodoDado aRemover) {
      NodoDado removido;
      NodoDado herdeiro;
      
      if (aRemover.getEsq() == null || aRemover.getDir() == null) {
          if (aRemover.getPai() == null) {
              raiz = null;
              aRemover = null;
              return;
          }
          removido = aRemover;
      } else {
          removido = sucessor(aRemover);
          aRemover.setDado(removido.getDado());
      }
     
      
      if (removido.getEsq() != null) 
          herdeiro = removido.getEsq();
      else
          herdeiro = removido.getDir();
      
      if (herdeiro != null)
          herdeiro.setPai(removido.getPai());
      
      if (removido.getPai() == null)
          raiz = herdeiro;
      
      else {
          if (removido == removido.getPai().getEsq()) 
              removido.getPai().setEsq(herdeiro);
          else
              removido.getPai().setDir(herdeiro);
          
          verificarBalanceamento(removido.getPai());
      }
      
      removido = null;
  }
  
  public static NodoDado sucessor(NodoDado q) {
    if (q.getDir() != null) {
      NodoDado r = q.getDir();
      
      while (r.getEsq() != null) {
         r = r.getEsq();
      }
      return r;
    } else {
      NodoDado p = q.getPai();
      
      while (p != null && q == p.getDir()) {
        q = p;
        p = q.getPai();
      }
      
      return p;
    }
  }
  
  //-------------------------------------------------------------------------//
  //                   METODOS DE AUXILIARES                                 //
  //-------------------------------------------------------------------------//
  public static void print(Object... args) {
    System.out.print(args[0]);
  }
} 
