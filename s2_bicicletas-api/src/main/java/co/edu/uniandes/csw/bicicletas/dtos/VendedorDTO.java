/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.dtos;

import co.edu.uniandes.csw.bicicletas.entities.VendedorEntity;

/**
 * @author Juan José Torres
 */
public class VendedorDTO extends UsuarioDTO
{

    private String cedula;

    private String telefono;

    public VendedorDTO()
    {
    }

    public VendedorDTO(VendedorEntity vendedor)
    {
        super(vendedor);
        if (vendedor != null)
        {
            cedula = vendedor.getCedula();
        }
    }

    /**
     * @return the cedula
     */
    public String getCedula()
    {
        return cedula;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(String cedula)
    {
        this.cedula = cedula;
    }

    /**
     * @return the telefono
     */
    public String getTelefono()
    {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }
}
