/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.dtos;

import co.edu.uniandes.csw.bicicletas.entities.BicicletaEntity;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class BicicletaDTO implements Serializable {

    private Long id;

     /**
     * La calificacion promedio de la bicicleta
     */
    private Double calificacion;
    /**
     * La descripcion de la bicicleta
     */
    private String descripcion;
    
     /**
     * La referencia de la bicicleta
     */
    private Integer descuento;

    /**
     * La referencia de la bicicleta
     */
    private String referencia;

    /**
     * El precio de la bicicleta
     */
    private Double precio;

     /**
     * Indica si la bicicleta es usada o no
     */
    private Boolean usada;

     /**
     * Indica la cantidad de bicicletas disponibles en la tienda
     */
    private Integer stock;

    /**
     * Las rutas de las imagenes de la bicicleta
     */
    private ArrayList<String> album;

    /**
     * La marca de la bicicleta
     */
    private MarcaDTO marca;

     /**
     * LA categoria de la bicicleta
     */
    private CategoriaDTO categoria;
     
    /**
     * La orden asociada a la bicicleta
     */
    private OrdenDTO orden;

    /**
     * Constructor por defecto
     */
    public BicicletaDTO() {
        //constructor vacio
    }

    /**
     * Constructor a partir de la entidad
     *
     * @param bikeEntity La entidad de la bicicleta
     */
    public BicicletaDTO(BicicletaEntity bikeEntity) {
        if (bikeEntity != null) {

            this.id = bikeEntity.getId();
            this.descripcion = bikeEntity.getDescripcion();
            this.referencia = bikeEntity.getReferencia();
            this.precio = bikeEntity.getPrecio();
            this.usada = bikeEntity.getUsada();
            this.stock = bikeEntity.getStock();
            this.album = bikeEntity.getAlbum();
            this.calificacion = bikeEntity.getCalificacion();
            this.descuento = bikeEntity.getDescuento();
            

            if (bikeEntity.getCategoria() != null) {
                this.categoria = new CategoriaDTO(bikeEntity.getCategoria());
            } else {
                this.categoria = null;
            }

            if (bikeEntity.getMarca() != null) {
                this.marca = new MarcaDTO(bikeEntity.getMarca());
            } else {
                this.marca = null;
            }
            
            if(bikeEntity.getOrden() !=null ){
                this.orden = new OrdenDTO(bikeEntity.getOrden());
            } else {
                this.orden = null;
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    public BicicletaEntity toEntity() {
        BicicletaEntity bicicletaEntity = new BicicletaEntity();
        bicicletaEntity.setAlbum(this.getAlbum());
        bicicletaEntity.setDescripcion(this.descripcion);
        bicicletaEntity.setId(this.id);
        bicicletaEntity.setPrecio(this.precio);
        bicicletaEntity.setReferencia(this.referencia);
        bicicletaEntity.setStock(this.stock);
        bicicletaEntity.setUsada(this.usada);
        bicicletaEntity.setCalificacion(this.calificacion);
        bicicletaEntity.setDescuento(this.descuento);
        if (this.marca != null) {
            bicicletaEntity.setMarca(this.marca.toEntity());
        }
        if (this.categoria != null) {
            bicicletaEntity.setCategoria(this.categoria.toEntity());
        }
        
        if(this.orden!=null){
            bicicletaEntity.setOrden(this.orden.toEntity());
        }

        return bicicletaEntity;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the usada
     */
    public Boolean getUsada() {
        return usada;
    }

    /**
     * @param usada the usada to set
     */
    public void setUsada(Boolean usada) {
        this.usada = usada;
    }

    /**
     * @return the stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    
    /**
     * @return the marca
     */
    public MarcaDTO getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }

    /**
     * @return the categoria
     */
    public CategoriaDTO getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the orden
     */
    public OrdenDTO getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(OrdenDTO orden) {
        this.orden = orden;
    }

    /**
     * @return the calificacion
     */
    public Double getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the album
     */
    public ArrayList<String> getAlbum() {
        return album;
    }

    /**
     * @param album the album to set
     */
    public void setAlbum(ArrayList<String> album) {
        this.album = album;
    }

    /**
     * @return the descuento
     */
    public Integer getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

}
