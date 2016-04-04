/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Marcos Paulo
 */
public class analisar {

    ArrayList<retornoLexico> retornoLexico = new ArrayList<retornoLexico>();
    public static void analisarLinha(String caminhoArquivo) {
        try {
            int numeroLinha = 0;
            FileReader arquivo = new FileReader(caminhoArquivo);
            BufferedReader arquivoLido = new BufferedReader(arquivo);
            String linhaParaAnalisar = arquivoLido.readLine();

            while (linhaParaAnalisar != null) {
                System.out.printf("%s\n", linhaParaAnalisar);
                numeroLinha++;
                char[] expressao = linhaParaAnalisar.toCharArray();

                for (int i = 0; i < expressao.length; i++) {

                    if (String.valueOf(expressao[i]).matches("[a-zA-Z]")) {
                        //comando ou erro
                        String comando = "";
                        do {

                            comando += expressao[i];
                            i++;

                            if (expressao.length <= i) {
                                //Chama método de identificação
                                retornoLexico retorno = identificarPalavraReservada(comando);
                                retorno.LinhaArquivo = numeroLinha;
                                break;
                            }
                        } while ((expressao.length-1) > i && String.valueOf(expressao[i]).matches("[a-zA-Z]"));

                        if ((expressao.length-1) > i && String.valueOf(expressao[i]).matches("[0-9]")) {
                            //retorna erro
                        }
                        //Chama método de identificação
                    }
                    if ((expressao.length-1) > i && expressao[i] == '"') {
                        do {

                            i++;
                            //acumular literal
                            if (expressao.length < i) {
                                //Criar objeto com erro
                            }
                        } while (expressao[i] != '"');
                        //Retornar regra de literal.
                    }

                    if ((expressao.length-1) > i && expressao[i] == '{') {
                        do {

                            i++;
                            //acumular literal
                            if (expressao.length < i) {
                                //Criar objeto com erro
                            }
                        } while (expressao[i] != '}');

                    }

                    if ((expressao.length-1) > i && expressao[i] == '#') {
                        break;
                    }

                    switch (expressao[i]) {

                    }

                }
                
                linhaParaAnalisar = arquivoLido.readLine();
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        //TODO: VERIFICAR SE ESTÃO SENDO FECHADAS AS ASPAS OU CHAVES
    }
    
    private static retornoLexico identificarPalavraReservada(String palavra){
    
        if(palavra.equals("PROGRAM")){
            retornoLexico retorno =  new retornoLexico();
            retorno.Codigo = 01;
            retorno.Token = palavra;
            return retorno;
        }
    
        return new retornoLexico();
    }

}
