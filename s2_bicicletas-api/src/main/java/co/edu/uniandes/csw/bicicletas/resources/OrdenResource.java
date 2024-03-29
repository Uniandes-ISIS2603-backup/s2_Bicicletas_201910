/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.resources;

import co.edu.uniandes.csw.bicicletas.dtos.OrdenDTO;
import co.edu.uniandes.csw.bicicletas.dtos.OrdenDetailDTO;
import co.edu.uniandes.csw.bicicletas.ejb.OrdenLogic;
import co.edu.uniandes.csw.bicicletas.entities.OrdenEntity;
import co.edu.uniandes.csw.bicicletas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Mateo
 */
@Path("ordenes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrdenResource {


    /**
     * Logica de la orden
     */
    @Inject
    private OrdenLogic ordenLogic;

    /**
     * Servicio que crea una orden y la persiste
     * @param orden Orden a crear y persistir
     * @return ordenDTO. El DTO de la orden creada
     * @throws Exception  Si hubo un problema de lógica al crear la orden
     */
    @POST
    public OrdenDTO createOrden(OrdenDTO orden) throws Exception {
        OrdenEntity ordenEntity = orden.toEntity();
        OrdenEntity nuevaOrdenEntity = ordenLogic.createOrden(ordenEntity);
        OrdenDTO nuevaOrdenDTO = new OrdenDTO(nuevaOrdenEntity);
        return nuevaOrdenDTO;
    }

    /**
     * Servicio que obtiene el detalle de una orden con un id especificado
     * @param id Id de la orden a obtener
     * @return la orden obtenida
     * @throws WebApplicationException Si la orden a obtener no existe
     */
    @GET
    @Path("{id: \\d+}")
    public OrdenDetailDTO getOrden(@PathParam("id") long id) throws WebApplicationException {

        OrdenEntity ordenEntity = ordenLogic.getOrden(id);
        if (ordenEntity == null) {
            throw new WebApplicationException("El recurso /ordenes/" + id + " no existe.", 404);
        }
        OrdenDetailDTO detailDTO = new OrdenDetailDTO(ordenEntity);
        return detailDTO;
    }

    /**
     * Servicio que obtiene la lista de ordenes
     * @return Lista de ordenes
     */
    @GET
    public List<OrdenDetailDTO> getOrdenes() {
        List<OrdenDetailDTO> listaOrdenes = listEntity2DetailDTO(ordenLogic.getOrdenes());
        return listaOrdenes;
    }
    
    /**
     * Método auxiliar para convertir una lista de entities a una lista de detalles
     * @param entityList Lista de entities a convertir
     * @return Lista de detalles
     */
    private List<OrdenDetailDTO> listEntity2DetailDTO(List<OrdenEntity> entityList) {
        List<OrdenDetailDTO> list = new ArrayList<>();
        for (OrdenEntity entity : entityList) {
            list.add(new OrdenDetailDTO(entity));
        }
        return list;
}
    /**
     * Obtener la clase de asociación que relaciona las bicicletas de una orden con la orden
     * @param ordenId Id de la orden cuyas bicicletas se quieren obtener
     * @return Clase de asociación de una orden y sus bicicletas
     */
    @Path("{ordenId: \\d+}/bicicletas")
    public Class<OrdenBicicletasResource> getOrdenBicicletasResource(@PathParam("ordenId") Long ordenId) {
        if (ordenLogic.getOrden(ordenId) == null) {
            throw new WebApplicationException("El recurso /categoriaNombre/" + ordenId + " no existe.", 404);
        }
        return OrdenBicicletasResource.class;
    }

}
