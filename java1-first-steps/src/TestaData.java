class DataException extends Exception {
  public DataException(String msg) {
    super(msg);
  }
}

class Data {
  private int dia;
  private int mes;
  private int ano;

  public Data(int dia, int mes, int ano) throws DataException{
      this.dia = validaDia(dia, mes, ano);
      this.mes = validaMes(dia, mes, ano);
      this.ano = validaAno(dia, mes, ano);
  }

  private int validaDia(int dia, int mes, int ano) throws DataException {
    boolean valid = false;
    validaMes(dia, mes, ano);
    switch(mes) {
      case 1: case 3: case 5: case 7: case 8: case 10: case 12:
        if(dia >= 1 && dia <= 31) { valid = !valid; }
        break;
      case 4: case 6: case 9: case 11:
        if(dia >= 1 && dia <= 30) { valid = !valid; }
        break;
      case 2:
        switch(dia) {
          case 29:
            if((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) { valid = !valid; }
            break;
          default:
            if(dia >= 0 && dia <= 28) { valid = !valid; }
            break;
        }
        break;
    }
    if(valid) {
      return dia;
    }
    else {
      throw new DataException(String.format("Erro! Dia inválido: %s/%s/%s", dia, mes, ano));
    }
  }

  private int validaMes(int dia, int mes, int ano) throws DataException {
    if(mes >= 1 && mes <= 12) {
      return mes;
    } else {
      throw new DataException(String.format("Erro! Mês inválido: %s/%s/%s", dia, mes, ano));
    }
  }

  private int validaAno(int dia, int mes, int ano) throws DataException {
    if(ano >= 0) {
      return ano;
    } else {
      throw new DataException(String.format("Erro! Ano inválido: %s/%s/%s", dia, mes, ano));
    }
  }

  public String toString() {
    return String.format("%s/%s/%s", this.dia, this.mes, this.ano);
  }

}

public class TestaData {
  public static void main(String[] args)  throws DataException {

    Data dia1 = new Data(2,2,2010);
    System.out.println(dia1);

    Data dia2 = new Data(30,1,2010);
    System.out.println(dia2);

    // Data dia3 = new Data(30,13,2010);
    // System.out.println(dia3);

    // Data dia4 = new Data(30,1,-2);
    // System.out.println(dia4);

    Data dia5 = new Data(29,2,2000);
    System.out.println(dia5);
  }
}
