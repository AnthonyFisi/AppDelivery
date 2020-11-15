package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Venta  implements Serializable {

    @SerializedName("idEmpresa")
    @Expose
    private int idEmpresa;

    @SerializedName("idtipopago")
    @Expose
    private int idtipopago;

    @SerializedName("idhorario")
    @Expose
    private int idhorario;

    @SerializedName("idubicacion")
    @Expose
    private int idubicacion;

    @SerializedName("venta_fechaentrega")
    @Expose
    private String  venta_fechaentrega;

    @SerializedName("venta_costodelivery")
    @Expose
    private float venta_costodelivery;

    @SerializedName("venta_costototal")
    @Expose
    private float venta_costototal;

    @SerializedName("comentario")
    @Expose
    private String comentario;


    @SerializedName("idestado_pago")
    @Expose
    private int idestado_pago;

    @SerializedName("idtipo_envio")
    @Expose
    private int idtipo_envio;

    @SerializedName("idUsuario")
    @Expose
    private int idUsuario;

    @SerializedName("repsuesta_registro_venta")
    @Expose
    private boolean repsuesta_registro_venta;

    @SerializedName("numeromesa")
    @Expose
    private int numeromesa;

    @SerializedName("descuento_mesa")
    @Expose
    private float descuento_mesa;

    @SerializedName("mesa")
    @Expose
    private boolean mesa;

    @SerializedName("venta_costopedido")
    @Expose
    private float venta_costopedido;

    @SerializedName("celular_adicional")
    @Expose
    private String celular_adicional;

    @SerializedName("comentario_ubicacion")
    @Expose
    private String comentario_ubicacion;

    @SerializedName("empresa_posistion")
    @Expose
    private List<Double> empresa_posistion;

    @SerializedName("usuario_position")
    @Expose
    private List<Double> usuario_position;



    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(int idtipopago) {
        this.idtipopago = idtipopago;
    }

    public int getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(int idhorario) {
        this.idhorario = idhorario;
    }

    public int getIdubicacion() {
        return idubicacion;
    }

    public void setIdubicacion(int idubicacion) {
        this.idubicacion = idubicacion;
    }


    public String getVenta_fechaentrega() {
        return venta_fechaentrega;
    }

    public void setVenta_fechaentrega(String venta_fechaentrega) {
        this.venta_fechaentrega = venta_fechaentrega;
    }

    public float getVenta_costodelivery() {
        return venta_costodelivery;
    }

    public void setVenta_costodelivery(float venta_costodelivery) {
        this.venta_costodelivery = venta_costodelivery;
    }

    public float getVenta_costototal() {
        return venta_costototal;
    }

    public void setVenta_costototal(float venta_costototal) {
        this.venta_costototal = venta_costototal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdestado_pago() {
        return idestado_pago;
    }

    public void setIdestado_pago(int idestado_pago) {
        this.idestado_pago = idestado_pago;
    }

    public int getIdtipo_envio() {
        return idtipo_envio;
    }

    public void setIdtipo_envio(int idtipo_envio) {
        this.idtipo_envio = idtipo_envio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isRepsuesta_registro_venta() {
        return repsuesta_registro_venta;
    }

    public void setRepsuesta_registro_venta(boolean repsuesta_registro_venta) {
        this.repsuesta_registro_venta = repsuesta_registro_venta;
    }

    public int getNumeromesa() {
        return numeromesa;
    }

    public void setNumeromesa(int numeromesa) {
        this.numeromesa = numeromesa;
    }

    public float getDescuento_mesa() {
        return descuento_mesa;
    }

    public void setDescuento_mesa(float descuento_mesa) {
        this.descuento_mesa = descuento_mesa;
    }

    public boolean isMesa() {
        return mesa;
    }

    public void setMesa(boolean mesa) {
        this.mesa = mesa;
    }

    public float getVenta_costopedido() {
        return venta_costopedido;
    }

    public void setVenta_costopedido(float venta_costopedido) {
        this.venta_costopedido = venta_costopedido;
    }

    public List<Double> getEmpresa_posistion() {
        return empresa_posistion;
    }

    public void setEmpresa_posistion(List<Double> empresa_posistion) {
        this.empresa_posistion = empresa_posistion;
    }

    public List<Double> getUsuario_position() {
        return usuario_position;
    }

    public void setUsuario_position(List<Double> usuario_position) {
        this.usuario_position = usuario_position;
    }

    public String getCelular_adicional() {
        return celular_adicional;
    }

    public void setCelular_adicional(String celular_adicional) {
        this.celular_adicional = celular_adicional;
    }

    public String getComentario_ubicacion() {
        return comentario_ubicacion;
    }

    public void setComentario_ubicacion(String comentario_ubicacion) {
        this.comentario_ubicacion = comentario_ubicacion;
    }
}
