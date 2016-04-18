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
public class Analisar {

    public static ArrayList<RetornoLexico1> analisarLinha(String caminhoArquivo) {
        ArrayList<RetornoLexico1> retornoLexico = new ArrayList<RetornoLexico1>();

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
                            RetornoLexico1 retorno = identificarPalavraReservada(comando);
                            if (retorno.Codigo != 0) {
                                retorno.LinhaArquivo = numeroLinha;
                                retornoLexico.add(retorno);
                                break;
                            }
                        } while ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[a-zA-Z]"));

                        if ((expressao.length - 1) >= i && String.valueOf(expressao[i]).matches("[0-9]")) {
                            //retorna erro
                            RetornoLexico1 retorno = new RetornoLexico1();
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
                                        RetornoLexico1 retorno = new RetornoLexico1();
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
                                RetornoLexico1 retorno = new RetornoLexico1();
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
                                        RetornoLexico1 retorno = new RetornoLexico1();
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
                                RetornoLexico1 retorno = new RetornoLexico1();
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
                        RetornoLexico1 retorno = new RetornoLexico1();
                        retorno.Codigo = EnumCodigo.IDENTIFICADOR.getCodigo();
                        retorno.LinhaArquivo = numeroLinha;
                        retorno.Token = acumulador;
                        retornoLexico.add(retorno);
                    }

                    if ((expressao.length - 1) >= i) {
                        RetornoLexico1 retorno = null;
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
                     RetornoLexico1 retorno = null;
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

        RetornoLexico1 finalArquivo = new RetornoLexico1();
        finalArquivo.Codigo = EnumCodigo.FINALARQUIVO.getCodigo();
        finalArquivo.Token = "$";

        return retornoLexico;
    }

    private static RetornoLexico1 identificarPalavraReservada(String palavra) {
        RetornoLexico1 retorno = new RetornoLexico1();
        switch (palavra) {

            case "PROGRAM":
                retorno.Codigo = EnumCodigo.PROGRAM.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "LABEL":
                retorno.Codigo = EnumCodigo.LABEL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CONST":
                retorno.Codigo = EnumCodigo.CONST.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "VAR":
                retorno.Codigo = EnumCodigo.VAR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "PROCEDURE":
                retorno.Codigo = EnumCodigo.PROCEDURE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "BEGIN":
                retorno.Codigo = EnumCodigo.BEGIN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "END":
                retorno.Codigo = EnumCodigo.END.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "INT":
                retorno.Codigo = EnumCodigo.INT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ARRAY":
                retorno.Codigo = EnumCodigo.ARRAY.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OF":
                retorno.Codigo = EnumCodigo.OF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CALL":
                retorno.Codigo = EnumCodigo.CALL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "GOTO":
                retorno.Codigo = EnumCodigo.GOTO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "IF":
                retorno.Codigo = EnumCodigo.IF.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "THEN":
                retorno.Codigo = EnumCodigo.THEN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "ELSE":
                retorno.Codigo = EnumCodigo.ELSE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WHILE":
                retorno.Codigo = EnumCodigo.WHILE.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "DO":
                retorno.Codigo = EnumCodigo.DO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "REPEAT":
                retorno.Codigo = EnumCodigo.REPEAT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "UNTIL":
                retorno.Codigo = EnumCodigo.UNTIL.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "READLN":
                retorno.Codigo = EnumCodigo.READLN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "WRITELN":
                retorno.Codigo = EnumCodigo.WRITELN.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "OR":
                retorno.Codigo = EnumCodigo.OR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "AND":
                retorno.Codigo = EnumCodigo.AND.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "NOT":
                retorno.Codigo = EnumCodigo.NOT.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "FOR":
                retorno.Codigo = EnumCodigo.FOR.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "TO":
                retorno.Codigo = EnumCodigo.TO.getCodigo();
                retorno.Token = palavra;
                return retorno;

            case "CASE":
                retorno.Codigo = EnumCodigo.CASE.getCodigo();
                retorno.Token = palavra;
                return retorno;
        }

        return new RetornoLexico1();
    }

    private static RetornoLexico1 retornoLiteralmente(String literalmente, int linha) {
        RetornoLexico1 retorno = new RetornoLexico1();
        retorno.LinhaArquivo = linha;
        retorno.Codigo = EnumCodigo.LITERAL.getCodigo();
        retorno.Token = literalmente;
        return retorno;
    }

    private static RetornoLexico1 retornoSimboloSimples(char simbolo) {
        RetornoLexico1 retorno = new RetornoLexico1();
        switch (simbolo) {
            case '+':
                retorno.Codigo = EnumCodigo.SOMA.getCodigo();
                retorno.Token = "+";
                return retorno;
            case '-':
                retorno.Codigo = EnumCodigo.SUBTRACAO.getCodigo();
                retorno.Token = "-";
                return retorno;
            case '*':
                retorno.Codigo = EnumCodigo.MULTIPLICACAO.getCodigo();
                retorno.Token = "*";
                return retorno;
            case '/':
                retorno.Codigo = EnumCodigo.DIVISAO.getCodigo();
                retorno.Token = "/";
                return retorno;
            case '[':
                retorno.Codigo = EnumCodigo.ABRECOLCHETE.getCodigo();
                retorno.Token = "[";
                return retorno;
            case ']':
                retorno.Codigo = EnumCodigo.FECHACOLCHETE.getCodigo();
                retorno.Token = "]";
                return retorno;
            case '(':
                retorno.Codigo = EnumCodigo.ABREPARENTESE.getCodigo();
                retorno.Token = "(";
                return retorno;
            case ')':
                retorno.Codigo = EnumCodigo.FECHAPARENTESE.getCodigo();
                retorno.Token = ")";
                return retorno;
            case ';':
                retorno.Codigo = EnumCodigo.PONTOVIRGULA.getCodigo();
                retorno.Token = ";";
                return retorno;
            case '=':
                retorno.Codigo = EnumCodigo.IGUAL.getCodigo();
                retorno.Token = "=";
                return retorno;

        }
        return null;
    }

    private static RetornoLexico1 retornoSimboloDuploUnico(String simbolos) {
        RetornoLexico1 retorno = new RetornoLexico1();
        switch (simbolos) {
            case ">":
                retorno.Codigo = EnumCodigo.MAIOR.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            case ":":
                retorno.Codigo = EnumCodigo.DOISPONTOS.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<":
                retorno.Codigo = EnumCodigo.MENOR.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case ".":
                retorno.Codigo = EnumCodigo.PONTO.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            default:
                return null;
        }
    }

    private static RetornoLexico1 retornoSimboloDuplo(String simbolos) {
        RetornoLexico1 retorno = new RetornoLexico1();
        switch (simbolos) {
            case ":=":
                retorno.Codigo = EnumCodigo.ATRIBUICAODUPLO.getCodigo();
                retorno.Token = ":=";
                return retorno;

            case ">=":
                retorno.Codigo = EnumCodigo.MAIORIGUAL.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<=":
                retorno.Codigo = EnumCodigo.MENORIGUAL.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "<>":
                retorno.Codigo = EnumCodigo.DIFERENTEDE.getCodigo();
                retorno.Token = simbolos;
                return retorno;

            case "..":
                retorno.Codigo = EnumCodigo.PONTOSSEGUIDOS.getCodigo();
                retorno.Token = simbolos;
                return retorno;
            default:
                return null;
        }
    }

}
