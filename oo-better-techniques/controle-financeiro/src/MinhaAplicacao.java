import java.util.Calendar;

public class MinhaAplicacao {
      public static void main(String[] args) {
        ArmazenadorDeDividas bd = new BancoDeDados("servidor", "usuario", "senha");
        BalancoEmpresa balanco = new BalancoEmpresa(bd);

        Documento cnpjPapelaria = new Cnpj("00.000.001/0001-01");
        Divida divida = new Divida("Papelaria", cnpjPapelaria, 100);
        balanco.registraDivida(divida);

        Documento cpfCredor = new Cpf("000.000.001-01");
        Pagamento pagamento = new Pagamento("Luiz",cpfCredor, 100);
        pagamento.setData(Calendar.getInstance());

        balanco.pagaDivida(cnpjPapelaria, pagamento);

        bd.desconecta();
      }
    }