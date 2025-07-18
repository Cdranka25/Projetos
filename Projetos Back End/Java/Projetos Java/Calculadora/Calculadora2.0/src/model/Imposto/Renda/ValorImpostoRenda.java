/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Imposto.Renda;

import static model.Imposto.Renda.Percentual_Inss.PRIMEIRA;
import static model.Imposto.Renda.Percentual_Inss.QUARTA;
import static model.Imposto.Renda.Percentual_Inss.SEGUNDA;
import static model.Imposto.Renda.Percentual_Inss.TERCEIRA;

/**
 *
 * @author user
 */
public class ValorImpostoRenda {

    private double salario;
    private Percentual_Inss perInss;
    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Percentual_Inss getPerInss() {
        return perInss;
    }

    public void setPerInss(Percentual_Inss perInss) {
        this.perInss = perInss;
    }

    public double getSalario() {

        return salario;
    }

    public double calcularINSS() {
        //INSS = Instituto Nacional do Seguro Social
        double imposto = 0, diferenca = 0;

        if (getSalario() <= 1302.00) {
            //1ª faixa 
            imposto = getSalario() * (7.5 / 100);
              perInss = PRIMEIRA;
        } else if (getSalario() > 1302.00 && getSalario() <= 2571.29) {
            //2ª faixa              
            imposto = getSalario() * (9.0 / 100);
              perInss = SEGUNDA;
        } else if (getSalario() > 2571.29 && getSalario() <= 3856.94) {
            //3ª faixa              
            imposto = getSalario() * (12.0 / 100);
               perInss = TERCEIRA;
        } else if (getSalario() > 3856.94) {
            //4ª faixa              
            imposto = getSalario() * (14.0 / 100);
              perInss =  QUARTA;
        }
        return imposto;
    }

    public double calcularIRPF() {
        //IRPF = Imposto sobre a Renda das Pessoas Físicas
        double imposto = 0, diferenca = 0;

        if (getSalario() > 2259.20) {  
            // 1º Faixa, não paga imposto
            if (getSalario() <= 2826.65) { //2ª faixa  
                //2ª faixa
                diferenca = getSalario() - 2259.20;
                imposto = diferenca * (7.5 / 100);

            } else if (getSalario() <= 3751.05) { //3ª faixa              
                //2ªFaixa
                diferenca = 2826.65 - 2259.20;
                imposto = diferenca * (7.5 / 100);

                //3ª faixa
                diferenca = getSalario() - 2826.65;
                imposto += diferenca * (15. / 100);

            } else if (getSalario() <= 4664.68) {//4ª Faixa
                //2ªFaixa
                diferenca = 2826.65 - 2259.20;
                imposto = diferenca * (7.5 / 100);

                //3ª faixa
                diferenca = 3751.05 - 2826.65;
                imposto += diferenca * (15.0 / 100);

                //4ª Faixa
                diferenca = getSalario() - 3751.05;
                imposto += diferenca * (22.5 / 100);

            } else {  //faixa final
                //2ªFaixa
                diferenca = 2826.65 - 2259.20;
                imposto = diferenca * (7.5 / 100);

                //3ª faixa
                diferenca = 3751.05 - 2826.65;
                imposto += diferenca * (15.0 / 100);

                //4ª Faixa
                diferenca = 4664.68 - 3751.05;
                imposto += diferenca * (22.5 / 100);

                diferenca = getSalario() - 4664.68;
                imposto += diferenca * (27.5 / 100);
            }
        }
        return imposto;
    }
}
