/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Lemon
 */
public enum Regras {
    
    REGRA1(new Integer[]{1, 25, 47, 54, 49}), 
    REGRA2(new Integer[]{55, 58,  60,  63,  65}),
    REGRA3(new Integer[]{2,  56, 47}),
    REGRA4(new Integer[]{25 , 57}),
    REGRA5(new Integer[]{52}),
    REGRA6(new Integer[]{46  ,25  ,57}),
    REGRA7(new Integer[]{52}),
    REGRA8(new Integer[]{3  ,25  ,40  ,26  ,47  ,59}),
    REGRA9(new Integer[]{52}),
    REGRA10(new Integer[]{25  ,40  ,26  ,47  ,59}),
    REGRA11(new Integer[]{52}),
    REGRA12(new Integer[]{4  ,56  ,39  ,62  ,47, 61}),
    REGRA13(new Integer[]{52}),
    REGRA14(new Integer[]{56,  39,  62 , 47,  61}),
    REGRA15(new Integer[]{52}),
    REGRA16(new Integer[]{8}),
    REGRA17(new Integer[]{9  ,34  ,26  ,50  ,26  ,35  ,10  ,8}),
    REGRA18(new Integer[]{5  ,25,  64 , 47 , 54,  47 , 63}),
    REGRA19(new Integer[]{52}),
    REGRA20(new Integer[]{52}),
    REGRA21(new Integer[]{36  ,56  ,39  ,8  ,37}),
    REGRA22(new Integer[]{6  ,67  ,66  ,7}),
    REGRA23(new Integer[]{52}),
    REGRA24(new Integer[]{47  ,67  ,66}),
    REGRA25(new Integer[]{25 , 68}),
    REGRA26(new Integer[]{39  ,67}),
    REGRA27(new Integer[]{69 , 38,  78}),
    REGRA28(new Integer[]{52}),
    REGRA29(new Integer[]{34  ,78  ,35}),
    REGRA30(new Integer[]{65}),
    REGRA31(new Integer[]{52}),
    REGRA32(new Integer[]{11  ,25  ,70}),
    REGRA33(new Integer[]{52}),
    REGRA34(new Integer[]{36  ,78  ,71  ,37}),
    REGRA35(new Integer[]{52}),
    REGRA36(new Integer[]{46  ,78  ,71}),
    REGRA37(new Integer[]{12  ,25}),
    REGRA38(new Integer[]{13  ,78  ,14  ,67  ,72}),
    REGRA39(new Integer[]{52}),
    REGRA40(new Integer[]{15  ,67}),
    REGRA41(new Integer[]{16  ,78  ,17  ,67}),
    REGRA42(new Integer[]{18  ,67  ,19  ,78}),
    REGRA43(new Integer[]{20  ,36  ,73  ,75  ,37}),
    REGRA44(new Integer[]{25  ,74}),
    REGRA45(new Integer[]{52}),
    REGRA46(new Integer[]{34  ,78  ,35}),
    REGRA47(new Integer[]{52}),
    REGRA48(new Integer[]{46  ,73  ,75}),
    REGRA49(new Integer[]{21  ,36  ,76  ,77  ,37}),
    REGRA50(new Integer[]{48}),
    REGRA51(new Integer[]{78}),
    REGRA52(new Integer[]{52}),
    REGRA53(new Integer[]{46  ,76  ,77}),
    REGRA54(new Integer[]{29  ,78  ,10  ,85  ,7}),
    REGRA55(new Integer[]{26  ,87  ,39  ,67  ,86}),
    REGRA56(new Integer[]{46  ,26  ,87}),
    REGRA57(new Integer[]{52}),
    REGRA58(new Integer[]{52}),
    REGRA59(new Integer[]{47  ,85}),
    REGRA60(new Integer[]{27  ,25  ,38  ,78  ,28  ,78  ,17  ,67}),
    REGRA61(new Integer[]{80  ,79}),
    REGRA62(new Integer[]{52}),
    REGRA63(new Integer[]{40  ,80}),
    REGRA64(new Integer[]{43  ,80}),
    REGRA65(new Integer[]{41  ,80}),
    REGRA66(new Integer[]{42  ,80}),
    REGRA67(new Integer[]{44  ,80}),
    REGRA68(new Integer[]{45  ,80}),
    REGRA69(new Integer[]{30  ,82  ,81}),
    REGRA70(new Integer[]{31  ,82  ,81}),
    REGRA71(new Integer[]{82  ,81}),
    REGRA72(new Integer[]{30  ,82  ,81}),
    REGRA73(new Integer[]{31  ,82  ,81}),
    REGRA74(new Integer[]{22  ,82  ,81}),
    REGRA75(new Integer[]{52}),
    REGRA76(new Integer[]{84  ,83}),
    REGRA77(new Integer[]{52}),
    REGRA78(new Integer[]{32  ,84  ,83}),
    REGRA79(new Integer[]{33  ,84  ,83}),
    REGRA80(new Integer[]{23  ,84  ,83}),
    REGRA81(new Integer[]{26}),
    REGRA82(new Integer[]{36  ,78  ,37}),
    REGRA83(new Integer[]{24  ,84}),
    REGRA84(new Integer[]{73});
    
    public Integer[] getRegra() {
        return regra;
    }

    private final Integer[] regra;

    private Regras(Integer[] regras) {
        this.regra = regras;
    }

    public List getLista() {
        List lista = Arrays.asList(regra);
        //Collections.reverse(lista);
        return lista;
    }

}