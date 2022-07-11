package Business.FactoryMethod;

import Model.Cliente;

public abstract class Notifica {
    private String msg;
    private Cliente cliente;

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    //metodo che lascia la sua implementazione alle sottoclassi
    public abstract boolean inviaNotifica();
}
