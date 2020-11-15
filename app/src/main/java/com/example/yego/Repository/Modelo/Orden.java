package com.example.yego.Repository.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Orden  implements Serializable {

    @SerializedName("idventa")
    @Expose
    private int idventa;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("nombre_empresa")
    @Expose
    private String nombre_empresa;

    @SerializedName("celular_empresa")
    @Expose
    private String celular_empresa;

    @SerializedName("urlfoto_empresa")
    @Expose
    private String urlfoto_empresa;

    @SerializedName("tiempo_aproximado_entrega")
    @Expose
    private String tiempo_aproximado_entrega;

    @SerializedName("emp_maps_coordenada_x")
    @Expose
    private String emp_maps_coordenada_x;

    @SerializedName("emp_maps_coordenada_Y")
    @Expose
    private String emp_maps_coordenada_Y;

    @SerializedName("detalle_ubicacion")
    @Expose
    private String detalle_ubicacion;

    @SerializedName("idtipopago")
    @Expose
    private int idtipopago;

    @SerializedName("tipopago_nombre")
    @Expose
    private String tipopago_nombre;

    @SerializedName("idhorario")
    @Expose
    private int idhorario;

    @SerializedName("horario_nombre")
    @Expose
    private String horario_nombre;

    @SerializedName("horario_inicio")
    @Expose
    private int horario_inicio;

    @SerializedName("horario_fin")
    @Expose
    private int horario_fin;

    @SerializedName("idubicacion")
    @Expose
    private int idubicacion;

    @SerializedName("ubicacion_nombre")
    @Expose
    private String ubicacion_nombre;

    @SerializedName("ubicacion_comentarios")
    @Expose
    private String ubicacion_comentarios;

    @SerializedName("maps_coordenada_x")
    @Expose
    private String maps_coordenada_x;

    @SerializedName("maps_coordenada_y")
    @Expose
    private String maps_coordenada_y;

    @SerializedName("venta_fecha")
    @Expose
    private Timestamp ventafecha;

    @SerializedName("venta_fechaentrega")
    @Expose
    private Timestamp venta_fechaentrega;

    @SerializedName("venta_costodelivery")
    @Expose
    private float venta_costodelivery;

    @SerializedName("venta_costototal")
    @Expose
    private float venta_costototal;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("idpedido")
    @Expose
    private int idpedido;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;

    @SerializedName("idestado_empresa")
    @Expose
    private int idestado_empresa;

    @SerializedName("tipo_estado")
    @Expose
    private String tipo_estado;

    @SerializedName("idestado_pago")
    @Expose
    private int idestado_pago;

    @SerializedName("nombre_estado")
    @Expose
    private String nombre_estado;

    @SerializedName("ordendisponible")
    @Expose
    private boolean ordendisponible;

    @SerializedName("tiempo_espera")
    @Expose
    private String tiempo_espera;

    @SerializedName("cancelar")
    @Expose
    private boolean cancelar;

    @SerializedName("idtipo_envio")
    @Expose
    private int idtipo_envio;

    @SerializedName("nombre_tipo_envio")
    @Expose
    private String nombre_tipo_envio;

    @SerializedName("idrepartidor")
    @Expose
    private int idrepartidor;

    @SerializedName("idestado_general")
    @Expose
    private int idestado_general;


    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getCelular_empresa() {
        return celular_empresa;
    }

    public void setCelular_empresa(String celular_empresa) {
        this.celular_empresa = celular_empresa;
    }

    public String getUrlfoto_empresa() {
        return urlfoto_empresa;
    }

    public void setUrlfoto_empresa(String urlfoto_empresa) {
        this.urlfoto_empresa = urlfoto_empresa;
    }

    public String getTiempo_aproximado_entrega() {
        return tiempo_aproximado_entrega;
    }

    public void setTiempo_aproximado_entrega(String tiempo_aproximado_entrega) {
        this.tiempo_aproximado_entrega = tiempo_aproximado_entrega;
    }

    public String getEmp_maps_coordenada_x() {
        return emp_maps_coordenada_x;
    }

    public void setEmp_maps_coordenada_x(String emp_maps_coordenada_x) {
        this.emp_maps_coordenada_x = emp_maps_coordenada_x;
    }

    public String getEmp_maps_coordenada_Y() {
        return emp_maps_coordenada_Y;
    }

    public void setEmp_maps_coordenada_Y(String emp_maps_coordenada_Y) {
        this.emp_maps_coordenada_Y = emp_maps_coordenada_Y;
    }

    public String getDetalle_ubicacion() {
        return detalle_ubicacion;
    }

    public void setDetalle_ubicacion(String detalle_ubicacion) {
        this.detalle_ubicacion = detalle_ubicacion;
    }

    public int getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(int idtipopago) {
        this.idtipopago = idtipopago;
    }

    public String getTipopago_nombre() {
        return tipopago_nombre;
    }

    public void setTipopago_nombre(String tipopago_nombre) {
        this.tipopago_nombre = tipopago_nombre;
    }

    public int getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(int idhorario) {
        this.idhorario = idhorario;
    }

    public String getHorario_nombre() {
        return horario_nombre;
    }

    public void setHorario_nombre(String horario_nombre) {
        this.horario_nombre = horario_nombre;
    }

    public int getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(int horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public int getHorario_fin() {
        return horario_fin;
    }

    public void setHorario_fin(int horario_fin) {
        this.horario_fin = horario_fin;
    }

    public int getIdubicacion() {
        return idubicacion;
    }

    public void setIdubicacion(int idubicacion) {
        this.idubicacion = idubicacion;
    }

    public String getUbicacion_nombre() {
        return ubicacion_nombre;
    }

    public void setUbicacion_nombre(String ubicacion_nombre) {
        this.ubicacion_nombre = ubicacion_nombre;
    }

    public String getUbicacion_comentarios() {
        return ubicacion_comentarios;
    }

    public void setUbicacion_comentarios(String ubicacion_comentarios) {
        this.ubicacion_comentarios = ubicacion_comentarios;
    }

    public String getMaps_coordenada_x() {
        return maps_coordenada_x;
    }

    public void setMaps_coordenada_x(String maps_coordenada_x) {
        this.maps_coordenada_x = maps_coordenada_x;
    }

    public String getMaps_coordenada_y() {
        return maps_coordenada_y;
    }

    public void setMaps_coordenada_y(String maps_coordenada_y) {
        this.maps_coordenada_y = maps_coordenada_y;
    }

    public Timestamp getVenta_fechaentrega() {
        return venta_fechaentrega;
    }

    public void setVenta_fechaentrega(Timestamp venta_fechaentrega) {
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

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdestado_empresa() {
        return idestado_empresa;
    }

    public void setIdestado_empresa(int idestado_empresa) {
        this.idestado_empresa = idestado_empresa;
    }

    public String getTipo_estado() {
        return tipo_estado;
    }

    public void setTipo_estado(String tipo_estado) {
        this.tipo_estado = tipo_estado;
    }

    public int getIdestado_pago() {
        return idestado_pago;
    }

    public void setIdestado_pago(int idestado_pago) {
        this.idestado_pago = idestado_pago;
    }

    public String getNombre_estado() {
        return nombre_estado;
    }

    public void setNombre_estado(String nombre_estado) {
        this.nombre_estado = nombre_estado;
    }


    public String getTiempo_espera() {
        return tiempo_espera;
    }

    public void setTiempo_espera(String tiempo_espera) {
        this.tiempo_espera = tiempo_espera;
    }

    public boolean isCancelar() {
        return cancelar;
    }

    public void setCancelar(boolean cancelar) {
        this.cancelar = cancelar;
    }

    public int getIdtipo_envio() {
        return idtipo_envio;
    }

    public void setIdtipo_envio(int idtipo_envio) {
        this.idtipo_envio = idtipo_envio;
    }

    public String getNombre_tipo_envio() {
        return nombre_tipo_envio;
    }

    public void setNombre_tipo_envio(String nombre_tipo_envio) {
        this.nombre_tipo_envio = nombre_tipo_envio;
    }


    public Timestamp getVentafecha() {
        return ventafecha;
    }

    public void setVentafecha(Timestamp ventafecha) {
        this.ventafecha = ventafecha;
    }

    public boolean isOrdendisponible() {
        return ordendisponible;
    }

    public void setOrdendisponible(boolean ordendisponible) {
        this.ordendisponible = ordendisponible;
    }

    public int getIdrepartidor() {
        return idrepartidor;
    }

    public void setIdrepartidor(int idrepartidor) {
        this.idrepartidor = idrepartidor;
    }

    public int getIdestado_general() {
        return idestado_general;
    }

    public void setIdestado_general(int idestado_general) {
        this.idestado_general = idestado_general;
    }


}
