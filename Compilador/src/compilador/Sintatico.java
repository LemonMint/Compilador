/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author MarcosPaulo
 */
public class Sintatico {

    private Sintatico() {
    }

    public static boolean analiasarSintaticamente(List<RetornoLexico> tokens) throws Exception {//throws SintaticoException {
        //Iniciando analise
        System.out.println("---------- Início da Análise Sintática ----------");
        //Declaramos uma fila, onde ficarão armazenados todos os tokens
        Queue<RetornoLexico> fila = new ArrayDeque<>(tokens);
        //Esta é a pilha usada para realizar a analise dos tokens
        Stack<Integer> stack = new Stack<>();
        //Adicionando o token 52 que é fim de arquivo (&)
        stack.push(52);
        //Adicionando no topo da pilha o codigo 53 que é o simbolo inicial(PROGRAMA)
        stack.push(53);
        //int que receberá o topo da pilha
        int topo;
        /*Inicialização do prmeiro token da fila. Note que que ele não está sendo
         neste momento. Isto foi usado, pois estava acontecendo um erro onde o Token 
         da fila, mesmo utilizando o comando .pool() não removia o elemento do topo da fila.*/
        RetornoLexico token = fila.peek();
        int ciclos = 0;
        do {
            //Apenas para controle 
            System.out.println("* Ciclo:       " + (ciclos++));
            System.out.println("* Pilha:     " + stack + " ⟵");
            System.out.println("* Fila:      " + fila);

            //Variável que recebe o topo da pilha e ao mesmo tempo remove ele (comando .pop()).
            topo = stack.pop();

            if (topo <= 51) {
                //Se o código for inferior ou igual a 51, então será terminal
                if (topo == token.Codigo) {
                    System.out.println("    ⨂ Removidos: TopoFila e TopoPilha: " + token.Codigo + " == " + topo);
                    //Aqui o topo da fila está sendo removido
                    fila.remove();
                    token = fila.peek();

                    continue;
                } else {
                    throw new Exception("ERRO topo da pilha e token são diferentes " + topo + " <> " + token);
                    //throw new SintaticoException("ERRO topo da pilha e token são diferentes " + topo + " <> " + token);
                }
            } else {
                /*Se não é terminal deve-se encontrar a regra relacionada 
                 ao não-terminal e substituir o não-terminal pela regra*/

                //Busca a regra de acordo com o topo da fila e o token
                List<Integer> regras = getRegra(topo, token.Codigo);

                if (regras != null) {
                    System.out.println("▶  Regra adicionada: " + regras);
                    for (int i = regras.size() - 1; i >= 0; i--) {
                        stack.push(regras.get(i));
                    }
                } else {
                    //Se não for encontrada nenhuma regra
                    //throw new SintaticoException("ERRO: nenhuma regra foi encontrada M(" + topo + "," + token + ")");
                    throw new Exception("ERRO: nenhuma regra foi encontrada M(" + topo + "," + token + ")");
                }
            }
        } while (topo != 50);
        return true;
    }

    /**
     * Retorna a regra encontrada na Matriz de Parsing
     *
     * @param nTerminal
     * @param terminal
     * @return Uma Lista com as regras ou null se nada for encontrado.
     */
    private static List getRegra(int nTerminal, int terminal) {
        List<Integer> regras = null;
        for (MatrizAnalise matrizAnalise : MatrizAnalise.values()) {
            regras = matrizAnalise.getRegra(nTerminal, terminal);

            if (regras != null) {
                break;
            }
        }
        return regras;
    }
}
