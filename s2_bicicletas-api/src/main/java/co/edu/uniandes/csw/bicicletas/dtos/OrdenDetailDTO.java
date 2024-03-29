/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.dtos;

import co.edu.uniandes.csw.bicicletas.entities.BicicletaEntity;
import co.edu.uniandes.csw.bicicletas.entities.OrdenEntity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Mateo
 */
public class OrdenDetailDTO extends OrdenDTO implements Serializable {

    /**
     * Lista de tipo BicicletaDTO compradas en una orden
     */
    private List<BicicletaDTO> bicicletas;
    /**
     * Constructor por defecto
     */
    public OrdenDetailDTO() {

    }

    public OrdenDetailDTO(OrdenEntity ordenEntity) {
        super(ordenEntity);
        if(ordenEntity != null) {
            List<BicicletaEntity> listaB = ordenEntity.getBicicletasOrden();
            for(BicicletaEntity be: listaB){
                bicicletas.add(new BicicletaDTO(be));
            }
        }
    }

    @Override
    public OrdenEntity toEntity() {
        OrdenEntity ordenEntity = super.toEntity();
       
        return ordenEntity;
    }

    /**
     * @return the bicicletas
     */
    public List<BicicletaDTO> getBicicletas() {
        return bicicletas;
    }

    /**
     * @param bicicletas the bicicletas to set
     */
    public void setBicicletas(List<BicicletaDTO> bicicletas) {
        this.bicicletas = bicicletas;
    }
}
