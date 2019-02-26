/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.ejb;

import co.edu.uniandes.csw.bicicletas.entities.CompradorEntity;
import co.edu.uniandes.csw.bicicletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicicletas.persistence.CompradorPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Lozano
 */

@Stateless
public class CompradorLogic {
    
    @Inject private CompradorPersistence cp;
    
    public CompradorEntity createCategoria(CompradorEntity pComprador) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un comprador.");
        
        if(cp.findByName(pComprador.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un comprador con el nombre \"" + pComprador.getNombre() + "\"");
        } else if(pComprador.getNombre().isEmpty()) {
            throw new BusinessLogicException("El nombre del comprador no puede estar vacío.");
        }
        
        cp.crearEntity(pComprador);
        
        LOGGER.log(Level.INFO, "Termina proceso de crear una comprador.");
        return pComprador;
    }
    
    public CompradorEntity getCategoria(Long pCompradorId) {
        LOGGER.log(Level.INFO, "Inicia proceso búsqueda de un comprador.");
        
        CompradorEntity ret = cp.encontrarEntity(pCompradorId);
        
        LOGGER.log(Level.INFO, "Termina proceso de búsqueda de un comprador.");
        return ret;
    }
    
    public CompradorEntity getCategoriaPorNombre(String pLoggin) {
        LOGGER.log(Level.INFO, "Inicia proceso de búsqueda de un comprador por Loggin.");
        
        CompradorEntity ret = cp.findByLogin(pLoggin);
        
        LOGGER.log(Level.INFO, "Termina proceso de búsqueda de un comprador por Loggin");
        return ret;
    }
    
     public CompradorEntity updateCategoria(CompradorEntity pComprador) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar categoría.");
        
        if(cp.findByName(pComprador.getNombre()) != null) {
            throw new BusinessLogicException("El comprador con el nombre dado ya existe");
        }
        
        if(pComprador.getNombre().isEmpty()) {
            throw new BusinessLogicException("El nombre del comprador no puede estar vacío.");
        }
        
        cp.crearEntity(pComprador);
        
        LOGGER.log(Level.INFO, "Termina proceso de actualizar categoría."); 
        
        return pComprador;
    }
    
    
}
