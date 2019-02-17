/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Mateo
 */

@Entity
public class OrdenEntity extends BaseEntity implements Serializable {
    
     /**
     * Identificador de la orden
     */
    private Integer identificador;
    
    /**
     * Fecha de realizacion de la orden
     */
    private String fecha;
    
    /**
     * Cantidad de productos comprados en la orden
     */
    private Integer cantidad;
    
    /**
     * Costo total de la orden
     */
    private Double costoTotal;
    
     /**
     * Medio de pago utilizado en la orden
     */
  //  private MedioPago medioPago;
    
    public OrdenEntity()
    {
        
    }

    /**
     * @return the identificador
     */
    public Integer getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the costoTotal
     */
    public Double getCostoTotal() {
        return costoTotal;
    }

    /**
     * @param costoTotal the costoTotal to set
     */
    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }
    

}