/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.entities;

import co.edu.uniandes.csw.bicicletas.podam.TelefonoStrategy;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Juan José Torres
 */
@Entity
public class VendedorEntity extends UsuarioEntity
{

    private static final Logger LOGGER = Logger.getLogger(VendedorEntity.class.getName());

    @PodamStrategyValue(TelefonoStrategy.class)
    private Long telefono;

    @PodamExclude
    @OneToMany(mappedBy = "vendedor")
    private List<VentaEntity> ventas;
    
    public VendedorEntity()
    {
    }

    /**
     * @return the telefono
     */
    public Long getTelefono()
    {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Long telefono)
    {
        this.telefono = telefono;
    }

    /**
     * @return the ventas
     */
    public List<VentaEntity> getVentas()
    {
        return ventas;
    }

    /**
     * @param ventas the ventas to set
     */
    public void setVentas(List<VentaEntity> ventas)
    {
        this.ventas = ventas;
    }

}
