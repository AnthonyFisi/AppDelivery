package com.example.yego.Repository.Modelo;

import com.google.android.gms.common.api.Batch;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoJOINregistroPedidoJOINpedido implements Serializable {

   public static List<ProductoJOINregistroPedidoJOINpedido> carrito= new ArrayList<>();

   public static int TotalProductos=0;
   public static float TotalCosto=0;

    @SerializedName("idproducto")
    @Expose
    private int idproducto;

    @SerializedName("idempresa")
    @Expose
    private int idempresa;

    @SerializedName("producto_nombre")
    @Expose
    private String producto_nombre;

    @SerializedName("producto_precio")
    @Expose
    private float producto_precio;

    @SerializedName("producto_uriimagen")
    @Expose
    private String producto_uriimagen;

    @SerializedName("producto_calificacion")
    @Expose
    private float producto_calificacion;

    @SerializedName("producto_descuento")
    @Expose
    private float producto_descuento;

    @SerializedName("producto_precio_descuento")
    @Expose
    private float producto_precio_descuento;

    @SerializedName("registropedido_cantidadtotal")
    @Expose
    private int registropedido_cantidadtotal;

    @SerializedName("registropedido_preciototal")
    @Expose
    private float registropedido_preciototal;

    @SerializedName("idpedido")
    @Expose
    private int idpedido;

    @SerializedName("idusuario")
    @Expose
    private int idusuario;



    @SerializedName("pedido_estado")
    @Expose
    private boolean pedido_estado;

    @SerializedName("pedido_montototal")
    @Expose
    private float pedido_montototal;

    @SerializedName("pedido_cantidadtotal")
    @Expose
    private int pedido_cantidadtotal;



    @SerializedName("nombre_empresa")
    @Expose
    private String nombre_empresa;

    @SerializedName("costo_delivery")
    @Expose
    private float costo_delivery;

    @SerializedName("urlfoto_empresa")
    @Expose
    private String urlfoto_empresa;

    @SerializedName("icono_empresa")
    @Expose
    private String icono_empresa;

    @SerializedName("comentario")
    @Expose
    private String comentario;

    @SerializedName("tipomenu")
    @Expose
    private int tipomenu;



    public ProductoJOINregistroPedidoJOINpedido(){}

    public ProductoJOINregistroPedidoJOINpedido(int idproducto, int idempresa, String producto_nombre, float producto_precio, String producto_uriimagen, float producto_calificacion, float producto_descuento, float producto_precio_descuento, int registropedido_cantidadtotal, float registropedido_preciototal, int idpedido, int idusuario, boolean pedido_estado, float pedido_montototal, int pedido_cantidadtotal, String nombre_empresa, float costo_delivery, String urlfoto_empresa, String icono_empresa,int tipomenu) {
        this.idproducto = idproducto;
        this.idempresa = idempresa;
        this.producto_nombre = producto_nombre;
        this.producto_precio = producto_precio;
        this.producto_uriimagen = producto_uriimagen;
        this.producto_calificacion = producto_calificacion;
        this.producto_descuento = producto_descuento;
        this.producto_precio_descuento = producto_precio_descuento;
        this.registropedido_cantidadtotal = registropedido_cantidadtotal;
        this.registropedido_preciototal = registropedido_preciototal;
        this.idpedido = idpedido;
        this.idusuario = idusuario;
        this.pedido_estado = pedido_estado;
        this.pedido_montototal = pedido_montototal;
        this.pedido_cantidadtotal = pedido_cantidadtotal;
        this.nombre_empresa = nombre_empresa;
        this.costo_delivery = costo_delivery;
        this.urlfoto_empresa = urlfoto_empresa;
        this.icono_empresa = icono_empresa;
        this.tipomenu=tipomenu;
    }

 public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public float getPedido_montototal() {
        return pedido_montototal;
    }

    public void setPedido_montototal(float pedido_montototal) {
        this.pedido_montototal = pedido_montototal;
    }

    public int getPedido_cantidadtotal() {
        return pedido_cantidadtotal;
    }

    public void setPedido_cantidadtotal(int pedido_cantidadtotal) {
        this.pedido_cantidadtotal = pedido_cantidadtotal;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getProducto_nombre() {
        return producto_nombre;
    }

    public void setProducto_nombre(String producto_nombre) {
        this.producto_nombre = producto_nombre;
    }

    public float getProducto_precio() {
        return producto_precio;
    }

    public void setProducto_precio(float producto_precio) {
        this.producto_precio = producto_precio;
    }

    public String getProducto_uriimagen() {
        return producto_uriimagen;
    }

    public void setProducto_uriimagen(String producto_uriimagen) {
        this.producto_uriimagen = producto_uriimagen;
    }

    public float getProducto_calificacion() {
        return producto_calificacion;
    }

    public void setProducto_calificacion(float producto_calificacion) {
        this.producto_calificacion = producto_calificacion;
    }

    public float getProducto_descuento() {
        return producto_descuento;
    }

    public void setProducto_descuento(float producto_descuento) {
        this.producto_descuento = producto_descuento;
    }

    public float getProducto_precio_descuento() {
        return producto_precio_descuento;
    }

    public void setProducto_precio_descuento(float producto_precio_descuento) {
        this.producto_precio_descuento = producto_precio_descuento;
    }

    public int getRegistropedido_cantidadtotal() {
        return registropedido_cantidadtotal;
    }

    public void setRegistropedido_cantidadtotal(int registropedido_cantidadtotal) {
        this.registropedido_cantidadtotal = registropedido_cantidadtotal;
    }

    public float getRegistropedido_preciototal() {
        return registropedido_preciototal;
    }

    public void setRegistropedido_preciototal(float registropedido_preciototal) {
        this.registropedido_preciototal = registropedido_preciototal;
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

    public boolean isPedido_estado() {
        return pedido_estado;
    }

    public void setPedido_estado(boolean pedido_estado) {
        this.pedido_estado = pedido_estado;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public float getCosto_delivery() {
        return costo_delivery;
    }

    public void setCosto_delivery(float costo_delivery) {
        this.costo_delivery = costo_delivery;
    }

    public String getUrlfoto_empresa() {
        return urlfoto_empresa;
    }

    public void setUrlfoto_empresa(String urlfoto_empresa) {
        this.urlfoto_empresa = urlfoto_empresa;
    }

    public String getIcono_empresa() {
        return icono_empresa;
    }

    public void setIcono_empresa(String icono_empresa) {
        this.icono_empresa = icono_empresa;
    }

    public int getTipomenu() {
        return tipomenu;
    }

    public void setTipomenu(int tipomenu) {
        this.tipomenu = tipomenu;
    }

    public ProductoJOINregistroPedidoJOINpedido existeObjeto(int idproducto){
        ProductoJOINregistroPedidoJOINpedido producto=null;

        for(ProductoJOINregistroPedidoJOINpedido objeto:carrito){

            if(objeto.getIdproducto() == idproducto ){
                producto=objeto;
            }
        }

        return producto;
    }

   /* public static void totalProductos(List<ProductoJOINregistroPedidoJOINpedido> lista){

        for(ProductoJOINregistroPedidoJOINpedido objeto:lista){

            TotalProductos=TotalProductos+objeto.getRegistropedido_cantidadtotal();
        }
    }*/

    public static int totalProductosByEmpresa(int idEmpresa){
        int cantidadTotal=0;

        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){

            if(objeto.getIdempresa()== idEmpresa){
                cantidadTotal+=objeto.getRegistropedido_cantidadtotal();
            }
        }

        return cantidadTotal;
    }

    public static float totalCostoByEmpresa(int idEmpresa){
        float costoTotal=0;

        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){

            if(objeto.getIdempresa()== idEmpresa){
                costoTotal+=objeto.getRegistropedido_preciototal();
            }
        }

        return costoTotal;
    }


/*
    public static void totalCostoProductos(List<ProductoJOINregistroPedidoJOINpedido> lista){

        for(ProductoJOINregistroPedidoJOINpedido objeto:lista){

            TotalCosto+=objeto.getRegistropedido_preciototal();
        }
    }*/

    public static  void removeProductosByEmpresa(int idEmpresa){

        Iterator<ProductoJOINregistroPedidoJOINpedido> iterator=ProductoJOINregistroPedidoJOINpedido.carrito.iterator();

        while (iterator.hasNext()) {

            ProductoJOINregistroPedidoJOINpedido producto=(ProductoJOINregistroPedidoJOINpedido) iterator.next();

            if(producto.getIdempresa()==idEmpresa){
                iterator.remove();
            }

        }
/*
        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){
            System.out.println(objeto.getProducto_nombre() + " idempresa " +objeto.getIdempresa());
           if(objeto.getIdempresa()== idEmpresa){
               System.out.println(objeto.getProducto_nombre());

               ProductoJOINregistroPedidoJOINpedido.carrito.remove(position);
            }

           position++;
        }*/
    }

    public static  List<ProductoJOINregistroPedidoJOINpedido> getListaEmpresa(int idEmpresa){

        List<ProductoJOINregistroPedidoJOINpedido> lista= new ArrayList<>();

        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){
           if(objeto.getIdempresa()== idEmpresa){
               lista.add(objeto);
            }
        }

        return lista;
    }


    public static int cantidadDescuento(int idempresa){

        int cant_entrada=0;
        int cant_segundo=0;

        int cantidad_final;

        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){
            if(objeto.getIdempresa()== idempresa){
                System.out.println(objeto.getIdempresa()+"/"+objeto.getIdproducto()+"/"+objeto.getNombre_empresa() +"/"+objeto.getRegistropedido_cantidadtotal());
            }
        }

        for(ProductoJOINregistroPedidoJOINpedido objeto:ProductoJOINregistroPedidoJOINpedido.carrito){

            if(objeto.getIdempresa()== idempresa) {

                if (objeto.getTipomenu() == 1) {

                   // System.out.println("cantidad de codigo 1 " + objeto.getTipomenu());
                    cant_entrada += objeto.getRegistropedido_cantidadtotal();
                    System.out.println("cantidad de codigo 1 " + objeto.getTipomenu()+" / "+objeto.getRegistropedido_cantidadtotal());


                }
                if (objeto.getTipomenu() == 2) {
                    System.out.println("cantidad de codigo 2 " + objeto.getTipomenu()+" / "+objeto.getRegistropedido_cantidadtotal());


                    cant_segundo += objeto.getRegistropedido_cantidadtotal();
                }

            }
        }

        if(cant_entrada> cant_segundo){
            cantidad_final=cant_segundo;
            System.out.println("cantidad de cantidad final ENTRADA MAYOR" +cantidad_final);

        }else {
            cantidad_final=cant_entrada;
            System.out.println("cantidad de cantidad final SENGUNDO MAYOR" +cantidad_final);

        }


        return cantidad_final;
    }
}
