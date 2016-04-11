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

    public static ArrayList<retornoLexico> analisarLinha(String caminhoArquivo) {
        ArrayList<retornoLexico> retornoLexico = new ArrayList<retornoLexico>();

        try {

            int numeroLinha = 0;
            FileReader arquivo = new FileReader(caminhoArquivo);
            BufferedReader arquivoLido = new BufferedReader(arquivo);

            String linhaParaAnalisar = arquivoLido.readLine();

            while (linhaParaAnalisar != null) {
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

                                retornoLexico.add(retorno);
                                break;
                            }
                        } while ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[a-zA-Z]"));

                        if ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[0-9]")) {
                            //retorna erro
                            retornoLexico retorno = new retornoLexico();
                            retorno.Token = "ERRO LÉXICO - PALAVRA RESERVADA NÃO ENCONTRADA. ";
                        }
                        //Chama método de identificação
                    }
                    
                    
                    
                    
                    if ((expressao.length - 1) >= i && expressao[i] == '"') {
                        boolean encontrou = false;
                        int j;
                        retornoLiteralmente();
                         System.out.println(i + "   " + expressao[i]);
                        for (j = i+1 ; j < expressao.length; j++) {
                            if (expressao[j] != '"') {
                                continue;
                            } else {
                                i = j;
                                retornoLiteralmente();                        
                                encontrou = true;
                                break;
                            }
                        }
                        i = j-1;
                        do {
                            linhaParaAnalisar = arquivoLido.readLine();
                            if (linhaParaAnalisar != null) {
                                numeroLinha++;
                                if (!linhaParaAnalisar.contains("\"")) {
                                    linhaParaAnalisar = arquivoLido.readLine();
                                    if (linhaParaAnalisar == null) {
                                        retornoLexico retorno = new retornoLexico();
                                        retorno.Token = "NÃO FOI ENCERRADO O LITERAL.";
                                        retornoLexico.add(retorno);
                                        return retornoLexico;
                                    }
                                } else {
                                    encontrou = true;
                                    expressao = linhaParaAnalisar.toCharArray();
                                    i = linhaParaAnalisar.indexOf('"');
                                    retornoLiteralmente();
                                    System.out.println(i + "   " + expressao[i]);
     
                                }
                            } else {
                                retornoLexico retorno = new retornoLexico();
                                retorno.Token = "NÃO FOI ENCERRADO O LITERAL.";
                                retornoLexico.add(retorno);

                                return retornoLexico;
                            }

                        } while (!encontrou);

                    }

                    if ((expressao.length - 1) >= i && expressao[i] == '{') {
                        boolean encontrou = false;
                        int j;
                        for (j = 0; j < expressao.length; j++) {
                            if (expressao[j] != '}') {
                                continue;
                            } else {
                                i = j;
                                encontrou = true;
                                break;
                            }
                        }
                        i = j;
                        do {
                            linhaParaAnalisar = arquivoLido.readLine();
                            if (linhaParaAnalisar != null) {
                                numeroLinha++;
                                if (!linhaParaAnalisar.contains("}")) {
                                    linhaParaAnalisar = arquivoLido.readLine();
                                    if (linhaParaAnalisar == null) {
                                        retornoLexico retorno = new retornoLexico();
                                        retorno.Token = "NÃO FOI ENCERRADO O COMENTÁRIO DE BLOCO.";
                                        retornoLexico.add(retorno);

                                        return retornoLexico;
                                    }
                                } else {
                                    encontrou = true;
                                    expressao = linhaParaAnalisar.toCharArray();
                                    i = linhaParaAnalisar.indexOf('}');
                                }
                            } else {
                                retornoLexico retorno = new retornoLexico();
                                retorno.Token = "NÃO FOI ENCERRADO O COMENTÁRIO DE BLOCO.";
                                retornoLexico.add(retorno);

                                return retornoLexico;
                            }

                        } while (!encontrou);

                    }
                    if ((expressao.length - 1) >= i && expressao[i] == '#') {
                        break;
                    }

                }

                linhaParaAnalisar = arquivoLido.readLine();
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        //TODO: VERIFICAR SE ESTÃO SENDO FECHADAS AS ASPAS OU CHAVES
        return retornoLexico;
    }

    private static retornoLexico identificarPalavraReservada(String palavra) {
        retornoLexico retorno = new retornoLexico();
        switch (palavra) {

            case "PROGRAM":
                retorno.Codigo = enumCodigo.PROGRAM.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "LABEL":
                retorno.Codigo = enumCodigo.LABEL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CONST":
                retorno.Codigo = enumCodigo.CONST.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "VAR":
                retorno.Codigo = enumCodigo.VAR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "PROCEDURE":
                retorno.Codigo = enumCodigo.PROCEDURE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "BEGIN":
                retorno.Codigo = enumCodigo.BEGIN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "END":
                retorno.Codigo = enumCodigo.END.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "INT":
                retorno.Codigo = enumCodigo.INT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ARRAY":
                retorno.Codigo = enumCodigo.ARRAY.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OF":
                retorno.Codigo = enumCodigo.OF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CALL":
                retorno.Codigo = enumCodigo.CALL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "GOTO":
                retorno.Codigo = enumCodigo.GOTO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "IF":
                retorno.Codigo = enumCodigo.IF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "THEN":
                retorno.Codigo = enumCodigo.THEN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ELSE":
                retorno.Codigo = enumCodigo.ELSE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WHILE":
                retorno.Codigo = enumCodigo.WHILE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "DO":
                retorno.Codigo = enumCodigo.DO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "REPEAT":
                retorno.Codigo = enumCodigo.REPEAT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "UNTIL":
                retorno.Codigo = enumCodigo.UNTIL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "READLN":
                retorno.Codigo = enumCodigo.READLN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WRITELN":
                retorno.Codigo = enumCodigo.WRITELN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OR":
                retorno.Codigo = enumCodigo.OR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "AND":
                retorno.Codigo = enumCodigo.AND.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "NOT":
                retorno.Codigo = enumCodigo.NOT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "FOR":
                retorno.Codigo = enumCodigo.FOR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "TO":
                retorno.Codigo = enumCodigo.TO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CASE":
                retorno.Codigo = enumCodigo.CASE.getCodigo();
                retorno.Token = palavra;
                return retorno;
        }

        return new retornoLexico();
    }

    private static retornoLexico retornoLiteralmente() {
        retornoLexico retorno = new retornoLexico();
        retorno.Codigo = enumCodigo.LITERAL.getCodigo();
        retorno.Token = "\"";
        return retorno;
    }

}
