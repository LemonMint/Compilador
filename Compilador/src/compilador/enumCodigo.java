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
    public enum enumCodigo{
    
        PROGRAM(1),
        LABEL(2),
        CONST(3),
        VAR(4),
        PROCEDURE(5),
        BEGIN(6),
        END(7),
        INT(8),
        ARRAY(9),
        OF(10),
        CALL(11),
        GOTO(12),
        IF(13),
        THEN(14),
        ELSE(15),
        WHILE(16),
        DO(17),
        REPEAT(18),
        UNTIL(19),
        READLN(20),
        WRITELN(21),
        OR(22),
        AND(23),
        NOT(24),
        IDENTIFICADOR(25),
        INTEGER(26),
        FOR(27),
        TO(28),
        CASE(29),
        SOMA(30),
        SUBTRACAO(31),
        MULTIPLICACAO(32),
        DIVISAO(33),
        ABRECOLCHETE(34),
        FECHACOLCHETE(35),
        ABREPARENTESE(36),
        FECHAPARENTESE(37),
        ATRIBUICAODUPLO(38),
        DOISPONTOS(39),
        IGUAL(40),
        MAIOR(41),
        MAIORIGUAL(42),
        MENOR(43),
        MENORIGUAL(44),
        DIFERENTEDE(45),
        VIRGULA(46),
        PONTOVIRGULA(47),
        LITERAL(48),
        PONTO(49),
        PONTOSSEGUIDOS(50),
        FINALARQUIVO(51),
        VAZIO(52);

        private int codigo;
        
        enumCodigo(int codigo){
            this.codigo = codigo;
        }

        public int getCodigo(){
            return this.codigo;
        }
    
    }

