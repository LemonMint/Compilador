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
public class Analisar1 {

    public static ArrayList<RetornoLexico12> analisarLinha(String caminhoArquivo) {
        ArrayList<RetornoLexico12> retornoLexico = new ArrayList<RetornoLexico12>();

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
                        String comando = String.valueOf(expressao[i]);
                        do {
                            if(i == expressao.length-1)
                                break;
                            if (String.valueOf(expressao[i]).matches("[a-zA-Z]")) {
                                i++;
                                comando += expressao[i];
                            }
                            //Chama método de identificação
                            RetornoLexico12 retorno = identificarPalavraReservada(comando);
                            if (retorno.Codigo != 0) {
                                retorno.LinhaArquivo = numeroLinha;
                                retornoLexico.add(retorno);
                                break;
                            }
                        } while ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[a-zA-Z]"));

                        if ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[0-9]")) {
                            //retorna erro
                            RetornoLexico12 retorno = new RetornoLexico12();
                            retorno.Token = "ERRO LÉXICO - PALAVRA RESERVADA NÃO ENCONTRADA. ";
                        }
                        //Chama método de identificação
                    }

                    //LITERAL
                    if ((expressao.length - 1) >= i && expressao[i] == '"') {
                        boolean encontrou = false;
                        String acumulador = "";
                        int j;
                        for (j = i + 1; j < expressao.length; j++) {
                            if (expressao[j] != '"') {
                                acumulador += expressao[j];
                                continue;
                            } else {
                                i = j;
                                encontrou = true;
                                break;
                            }
                        }
                        i = j - 1;
                        if (encontrou) {
                            retornoLexico.add(retornoLiteralmente("\"" + acumulador + "\"", numeroLinha));
                            break;
                        }
                        do {
                            linhaParaAnalisar = arquivoLido.readLine();
                            if (linhaParaAnalisar != null) {
                                numeroLinha++;
                                if (!linhaParaAnalisar.contains("\"")) {
                                    linhaParaAnalisar = arquivoLido.readLine();
                                    acumulador += linhaParaAnalisar;
                                    if (linhaParaAnalisar == null) {
                                        RetornoLexico12 retorno = new RetornoLexico12();
                                        retorno.Token = "NÃO FOI ENCERRADO O LITERAL.";
                                        retornoLexico.add(retorno);
                                        return retornoLexico;
                                    }
                                } else {
                                    encontrou = true;
                                    expressao = linhaParaAnalisar.toCharArray();
                                    for (int k = 0; k < expressao.length; k++) {
                                        if (expressao[k] != '"') {
                                            acumulador += expressao[k];
                                        }
                                    }
                                    i = linhaParaAnalisar.indexOf('"');
                                    retornoLexico.add(retornoLiteralmente("\"" + acumulador + "\"", numeroLinha));

                                }
                            } else {
                                RetornoLexico12 retorno = new RetornoLexico12();
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
                        if (encontrou) {
                            break;
                        }
                        do {
                            linhaParaAnalisar = arquivoLido.readLine();
                            if (linhaParaAnalisar != null) {
                                numeroLinha++;
                                if (!linhaParaAnalisar.contains("}")) {
                                    linhaParaAnalisar = arquivoLido.readLine();
                                    if (linhaParaAnalisar == null) {
                                        RetornoLexico12 retorno = new RetornoLexico12();
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
                                RetornoLexico12 retorno = new RetornoLexico12();
                                retorno.Token = "NÃO FOI ENCERRADO O COMENTÁRIO DE BLOCO.";
                                retornoLexico.add(retorno);

                                return retornoLexico;
                            }

                        } while (!encontrou);

                    }
                    if ((expressao.length - 1) >= i && expressao[i] == '#') {
                        break;
                    }

                    if ((expressao.length - 1) >= i && expressao[i] == '@') {
                        String acumulador = "";
                        do {
                            acumulador += expressao[i];
                            i++;
                        } while ((expressao.length - 1) >= i && !Character.isWhitespace(expressao[i]));
                        RetornoLexico12 retorno = new RetornoLexico12();
                        retorno.Codigo = EnumCodigo1.IDENTIFICADOR.getCodigo();
                        retorno.LinhaArquivo = numeroLinha;
                        retorno.Token = acumulador;
                        retornoLexico.add(retorno);
                    }

                    if ((expressao.length - 1) >= i) {
                        RetornoLexico12 retorno = null;
                        retorno = retornoSimboloSimples(expressao[i]);
                        if (retorno != null) {
                            retorno.LinhaArquivo = numeroLinha;
                            retornoLexico.add(retorno);
                        } else {
                            String acumularSimboloDuplo = "";
                            String AcumularSimboloUnico = Character.toString(expressao[i]);
                            acumularSimboloDuplo = Character.toString(expressao[i]);
                            if (expressao.length - 2 >= i) {
                                acumularSimboloDuplo += Character.toString(expressao[i + 1]);
                                retorno = retornoSimboloDuplo(acumularSimboloDuplo);
                                if (retorno != null) {
                                    retorno.LinhaArquivo = numeroLinha;
                                    retornoLexico.add(retorno);
                                    i = i + 2;
                                } else {
                                    retorno = retornoSimboloDuploUnico(AcumularSimboloUnico);

                                    if (retorno != null) {
                                        retorno.LinhaArquivo = numeroLinha;
                                        retornoLexico.add(retorno);
                                    }
                                }

                            } else {
                                retorno = retornoSimboloDuploUnico(AcumularSimboloUnico);

                                if (retorno != null) {
                                    retorno.LinhaArquivo = numeroLinha;
                                    retornoLexico.add(retorno);
                                }
                            }
                        }
                    }

                    /*
                     if ((expressao.length - 1) >= i) {
                     RetornoLexico12 retorno = null;
                     String acumularSimboloDuplo = "";
                     acumularSimboloDuplo = Character.toString(expressao[i]);
                     if (expressao.length - 2 >= i) {
                     acumularSimboloDuplo += Character.toString(expressao[i + 1]);
                     retorno = retornoSimboloDuplo(acumularSimboloDuplo);
                     }
                     }*/
                }

                linhaParaAnalisar = arquivoLido.readLine();
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        RetornoLexico12 finalArquivo = new RetornoLexico12();
        finalArquivo.Codigo = EnumCodigo1.FINALARQUIVO.getCodigo();
        finalArquivo.Token = "$";

        return retornoLexico;
    }

    private static RetornoLexico12 identificarPalavraReservada(String palavra) {
        RetornoLexico12 retorno = new RetornoLexico12();
        switch (palavra) {

            case "PROGRAM":
                retorno.Codigo = EnumCodigo1.PROGRAM.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "LABEL":
                retorno.Codigo = EnumCodigo1.LABEL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CONST":
                retorno.Codigo = EnumCodigo1.CONST.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "VAR":
                retorno.Codigo = EnumCodigo1.VAR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "PROCEDURE":
                retorno.Codigo = EnumCodigo1.PROCEDURE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "BEGIN":
                retorno.Codigo = EnumCodigo1.BEGIN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "END":
                retorno.Codigo = EnumCodigo1.END.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "INT":
                retorno.Codigo = EnumCodigo1.INT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ARRAY":
                retorno.Codigo = EnumCodigo1.ARRAY.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OF":
                retorno.Codigo = EnumCodigo1.OF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CALL":
                retorno.Codigo = EnumCodigo1.CALL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "GOTO":
                retorno.Codigo = EnumCodigo1.GOTO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "IF":
                retorno.Codigo = EnumCodigo1.IF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "THEN":
                retorno.Codigo = EnumCodigo1.THEN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ELSE":
                retorno.Codigo = EnumCodigo1.ELSE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WHILE":
                retorno.Codigo = EnumCodigo1.WHILE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "DO":
                retorno.Codigo = EnumCodigo1.DO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "REPEAT":
                retorno.Codigo = EnumCodigo1.REPEAT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "UNTIL":
                retorno.Codigo = EnumCodigo1.UNTIL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "READLN":
                retorno.Codigo = EnumCodigo1.READLN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WRITELN":
                retorno.Codigo = EnumCodigo1.WRITELN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OR":
                retorno.Codigo = EnumCodigo1.OR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "AND":
                retorno.Codigo = EnumCodigo1.AND.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "NOT":
                retorno.Codigo = EnumCodigo1.NOT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "FOR":
                retorno.Codigo = EnumCodigo1.FOR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "TO":
                retorno.Codigo = EnumCodigo1.TO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CASE":
                retorno.Codigo = EnumCodigo1.CASE.getCodigo();
                retorno.Token = palavra;
                return retorno;
        }

        return new RetornoLexico12();
    }

    private static RetornoLexico12 retornoLiteralmente(String literalmente, int linha) {
        RetornoLexico12 retorno = new RetornoLexico12();
        retorno.LinhaArquivo = linha;
        retorno.Codigo = EnumCodigo1.LITERAL.getCodigo();
        retorno.Token = literalmente;
        return retorno;
    }

    private static RetornoLexico12 retornoSimboloSimples(char simbolo) {
        RetornoLexico12 retorno = new RetornoLexico12();
        switch (simbolo) {
            case '+':
                retorno.Codigo = EnumCodigo1.SOMA.getCodigo();
                retorno.Token = "+";
                return retorno;
            case '-':
                retorno.Codigo = EnumCodigo1.SUBTRACAO.getCodigo();
                retorno.Token = "-";
                return retorno;
            case '*':
                retorno.Codigo = EnumCodigo1.MULTIPLICACAO.getCodigo();
                retorno.Token = "*";
                return retorno;
            case '/':
                retorno.Codigo = EnumCodigo1.DIVISAO.getCodigo();
                retorno.Token = "/";
                return retorno;
            case '[':
                retorno.Codigo = EnumCodigo1.ABRECOLCHETE.getCodigo();
                retorno.Token = "[";
                return retorno;
            case ']':
                retorno.Codigo = EnumCodigo1.FECHACOLCHETE.getCodigo();
                retorno.Token = "]";
                return retorno;
            case '(':
                retorno.Codigo = EnumCodigo1.ABREPARENTESE.getCodigo();
                retorno.Token = "(";
                return retorno;
            case ')':
                retorno.Codigo = EnumCodigo1.FECHAPARENTESE.getCodigo();
                retorno.Token = ")";
                return retorno;
            case ';':
                retorno.Codigo = EnumCodigo1.PONTOVIRGULA.getCodigo();
                retorno.Token = ";";
                return retorno;
            case '=':
                retorno.Codigo = EnumCodigo1.IGUAL.getCodigo();
                retorno.Token = "=";
                return retorno;

        }
        return null;
    }

    private static RetornoLexico12 retornoSimboloDuploUnico(String simbolos) {
        RetornoLexico12 retorno = new RetornoLexico12();
        switch (simbolos) {
            case ">":
                retorno.Codigo = EnumCodigo1.MAIOR.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            case ":":
                retorno.Codigo = EnumCodigo1.DOISPONTOS.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<":
                retorno.Codigo = EnumCodigo1.MENOR.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case ".":
                retorno.Codigo = EnumCodigo1.PONTO.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            default:
                return null;
        }
    }

    private static RetornoLexico12 retornoSimboloDuplo(String simbolos) {
        RetornoLexico12 retorno = new RetornoLexico12();
        switch (simbolos) {
            case ":=":
                retorno.Codigo = EnumCodigo1.ATRIBUICAODUPLO.getCodigo();
                retorno.Token = ":=";
                return retorno;

            case ">=":
                retorno.Codigo = EnumCodigo1.MAIORIGUAL.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<=":
                retorno.Codigo = EnumCodigo1.MENORIGUAL.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<>":
                retorno.Codigo = EnumCodigo1.DIFERENTEDE.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "..":
                retorno.Codigo = EnumCodigo1.PONTOSSEGUIDOS.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            default:
                return null;
        }
    }

}
