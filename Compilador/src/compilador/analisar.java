/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Marcos Paulo
 */
public class analisar {

    public static void analisarLinha(String linha, int numeroLinha) {

        char[] expressao = linha.toCharArray();

        //TODO: VERIFICAR SE ESTÃO SENDO FECHADAS AS ASPAS OU CHAVES
        for (int i = 0; i < expressao.length; i++){

            if (String.valueOf(expressao[i]).matches("[a-zA-Z]")) {
                //comando ou erro
                String comando = "";
                do{

                    comando += expressao[i];
                    i++;
                    
                    if(expressao.length < i){
                       //Chama método de identificação
                      
                   } 
                }while(String.valueOf(expressao[i]).matches("[a-zA-Z]"));
                
                if(String.valueOf(expressao[i]).matches("[0-9]")){
                    //retorna erro
                }
                //Chama método de identificação
            }
            if (expressao[i] == '"') {
                do {

                    i++;
                    //acumular literal
                    if (expressao.length < i) {
                        //Criar objeto com erro
                    }
                } while (expressao[i] != '"');
                //Retornar regra de literal.
            }

            if (expressao[i] == '{') {
                do {

                    i++;
                    //acumular literal
                    if (expressao.length < i) {
                        //Criar objeto com erro
                    }
                } while (expressao[i] != '}');

            }
            
            if(expressao[i] == '#'){
                break;
            }

            switch (expressao[i]) {
                 
            }

        }
    }

}
