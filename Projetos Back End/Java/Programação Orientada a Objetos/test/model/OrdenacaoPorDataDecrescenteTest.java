package model;

import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.OrdenacaoPorDataDecrescente;
import model.TipoTransacao;
import model.Transacao;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrdenacaoPorDataDecrescenteTest {

    @Test
    public void testCompare() {
        Transacao t1 = new Transacao(100.0, TipoTransacao.Despesa, LocalDate.of(2023, 6, 1));
        Transacao t2 = new Transacao(200.0, TipoTransacao.Receita, LocalDate.of(2023, 6, 2));
        
        List<Transacao> transacoes = Arrays.asList(t1, t2);
        Collections.sort(transacoes, new OrdenacaoPorDataDecrescente());
        
        assertThat(transacoes.get(0), is(t2));
        assertThat(transacoes.get(1), is(t1));
    }
}
