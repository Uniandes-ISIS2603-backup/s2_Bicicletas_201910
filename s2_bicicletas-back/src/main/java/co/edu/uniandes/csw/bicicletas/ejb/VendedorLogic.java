package co.edu.uniandes.csw.bicicletas.ejb;

import co.edu.uniandes.csw.bicicletas.entities.VendedorEntity;
import co.edu.uniandes.csw.bicicletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicicletas.persistence.VendedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan José Torres <jj.torresr@uniandes.edu.co>
 */
@Stateless
public class VendedorLogic
{

    private static final Logger LOGGER = Logger.getLogger(VendedorLogic.class.getName());

    /**
     * Persistencia de vendedor para probar sus métodos.
     */
    @Inject
    private VendedorPersistence pVendedor;

    /**
     * Se crea un nuevo vendedor
     *
     * @param user usuaio a crear en el sistema
     * @return el usuario creado
     * @throws BusinessLogicException si se rompe alguna regla de negocio. <br>
     * 1.Ninguno de los atributos es vacio o nulo. <br>
     * 2.No hay otro vendedor o comprador con el mismo login. <br>
     * 3.El número de telefono de celular es valido (10 digitos y empieza por 3)
     */
    public VendedorEntity createVendedor(VendedorEntity user) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de usuario");
        if (user.getLogin() != null && !user.getLogin().equals(""))
        {
            if (pVendedor.findByLogin(user.getLogin()) != null)
            {
                throw new BusinessLogicException("Ya existe un vendedor con ese Login");
            }
        }
        else
        {
            throw new BusinessLogicException("El login no es valido");
        }

        if (user.getPassword() == null || user.getPassword().equals(""))
        {
            throw new BusinessLogicException("La contraseña no es valida");
        }
        if (user.getNombre() == null || user.getNombre().equals(""))
        {
            throw new BusinessLogicException("El nombre no es valido");
        }
        if (user.getTelefono() != null)
        {
            if (user.getTelefono() < 3000000000L || user.getTelefono() >= 4000000000L)
            {
                throw new BusinessLogicException("El telefono no es valido");
            }
        }
        else
        {
            throw new BusinessLogicException("El telefono no puede ser vacio");
        }
        pVendedor.create(user);
        LOGGER.log(Level.INFO, "Se creó el usuario");
        return user;
    }

    /**
     * Se retorna el vendedor con id asignado.
     *
     * @param id ID del vendedor a buscar
     * @return vendedor con id o null si no existe.
     */
    public VendedorEntity findVendedor(Long id)
    {
        LOGGER.log(Level.INFO, "Se buscará el vendedor con id: {0}", id);
        VendedorEntity buscado = pVendedor.find(id);
        if (buscado == null)
        {
            LOGGER.log(Level.SEVERE, "No existe el vendedor con id: {0}", id);
        }
        LOGGER.log(Level.INFO, "Se termina la busqueda del vendedor con id: {0}", id);
        return buscado;
    }

    /**
     * Encuentra un vendedor por su login
     * @param login Login del vendedor
     * @return Vendedor con login dado
     */
    public VendedorEntity findByLogin(String login)
    {
        LOGGER.log(Level.INFO, "Se buscará el vendedor con login {0}", login);
        VendedorEntity buscado = pVendedor.findByLogin(login);
        if (buscado == null)
        {
            LOGGER.log(Level.SEVERE, "No existe el vendedor con login {0}", login);
        }
        LOGGER.log(Level.INFO, "Se termina la busqueda del vendedor con login {0}", login);
        return buscado;
    }

    /**
     * Encuentra un vendedor por su login y contraseña
     * @param login Login del vendedor
     * @param contrasena Contraseña del vendedor
     * @return Vendedor buscado (si existe)
     */
    public VendedorEntity authVendedor(String login, String contrasena)
    {
        LOGGER.log(Level.INFO, "Se buscará el vendedor con login {0}", login);
        VendedorEntity buscado = pVendedor.authVendedor(login, contrasena);
        if (buscado == null)
        {
            LOGGER.log(Level.SEVERE, "No existe el vendedor con login {0}", login);
        }
        LOGGER.log(Level.INFO, "Se termina la busqueda del vendedor con login {0}", login);
        return buscado;
    }

    /**
     * Se retornan todos los vendedores.
     * @return Lista con todos los vendedores.
     */
    public List<VendedorEntity> findAllVendedores()
    {
        LOGGER.log(Level.INFO, "se buscarán todos los vendedores");
        List<VendedorEntity> vendedores = pVendedor.findAll();
        if (vendedores == null || vendedores.isEmpty())
        {
            LOGGER.log(Level.SEVERE, "No existen vendedores");
        }
        LOGGER.log(Level.INFO, "Se termina la busqueda de los vendedores");
        return vendedores;
    }

    /**
     * Se actualiza un nuevo vendedor
     *
     * @param nuevo usuaio a actualizar en el sistema
     * @return el usuario actualizado
     * @throws BusinessLogicException si se rompe alguna regla de negocio. <br>
     * 1.Ninguno de los atributos es vacio o nulo. <br>
     * 2.No hay otro vendedor o comprador con el mismo login. <br>
     * 3.El número de telefono de celular es valido (10 digitos y empieza por 3)
     */
    public VendedorEntity updateVendedor(VendedorEntity nuevo) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualización de vendedor");
        if (nuevo.getLogin() == null || nuevo.getLogin().equals(""))
        {
            throw new BusinessLogicException("El login no es valido");
        }

        if (nuevo.getPassword() == null || nuevo.getPassword().equals(""))
        {
            throw new BusinessLogicException("La contraseña no es valida");
        }
        if (nuevo.getNombre() == null || nuevo.getNombre().equals(""))
        {
            throw new BusinessLogicException("El nombre no es valido");
        }
        if (nuevo.getTelefono() != null)
        {
            if (nuevo.getTelefono() < 3000000000L || nuevo.getTelefono() >= 4000000000L)
            {
                throw new BusinessLogicException("El telefono no es valido");
            }
        }
        else
        {
            throw new BusinessLogicException("El telefono no puede ser vacio");
        }

        VendedorEntity bd = pVendedor.update(nuevo);
        LOGGER.log(Level.INFO, "Se termina de actualizar el vendedor");
        return bd;
    }

    /**
     * Se elimina al vendedor con id dado
     * @param id ID del vendedor a eliminar.
     */
    public void deleteVendedor(Long id)
    {
        LOGGER.log(Level.INFO, "se borrará el Vendedor con id: {0}", id);
        pVendedor.delete(id);
        LOGGER.log(Level.INFO, "se borró al vendedor con id: {0}",id);
    }
}