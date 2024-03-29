/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.test.logic;

import co.edu.uniandes.csw.bicicletas.ejb.BicicletaLogic;
import co.edu.uniandes.csw.bicicletas.entities.BicicletaEntity;
import co.edu.uniandes.csw.bicicletas.entities.CategoriaEntity;
import co.edu.uniandes.csw.bicicletas.entities.MarcaEntity;
import co.edu.uniandes.csw.bicicletas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicicletas.persistence.BicicletaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Andrea
 */
@RunWith(Arquillian.class)
public class BicicletaLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * Inyección de la dependencia a la clase EditorialLogic cuyos métodos se
     * van a probar.
     */
    @Inject
    private BicicletaLogic bicicletaLogic;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    private UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<BicicletaEntity> data = new ArrayList<BicicletaEntity>();
    private List<MarcaEntity> dataMarcas = new ArrayList<MarcaEntity>();
    private List<CategoriaEntity> dataCategorias = new ArrayList<CategoriaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BicicletaEntity.class.getPackage())
                .addPackage(BicicletaLogic.class.getPackage())
                .addPackage(BicicletaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from BicicletaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
        
            CategoriaEntity cat = factory.manufacturePojo(CategoriaEntity.class);
            MarcaEntity marca = factory.manufacturePojo(MarcaEntity.class);
         
            em.persist(cat);
            em.persist(marca);
            dataMarcas.add(marca);
            dataCategorias.add(cat);

        }
        for (int i = 0; i < 3; i++) {
            BicicletaEntity entity = factory.manufacturePojo(BicicletaEntity.class);

            entity.setCategoria(dataCategorias.get(i));
            entity.setMarca(dataMarcas.get(i));

            Double precio = entity.getPrecio();
            entity.setPrecio(precio < 0.0 ? precio * -1 : precio);

            Integer stock = entity.getStock();
            entity.setStock(stock < 0 ? stock * -1 : stock);

            em.persist(entity);
            data.add(entity);

        }
           
    }

    /**
     * Prueba para crear una Bicicleta.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void createBicicletaTest() throws BusinessLogicException {
        BicicletaEntity newEntity = factory.manufacturePojo(BicicletaEntity.class);
        
        newEntity.setCategoria(dataCategorias.get(0));
        newEntity.setMarca(dataMarcas.get(0));
        Double precio = newEntity.getPrecio();
        newEntity.setPrecio(precio < 0.0 ? precio * -1 : precio);
        Integer stock = newEntity.getStock();
         newEntity.setStock(stock < 0 ? stock * -1 : stock);
        
        BicicletaEntity result = bicicletaLogic.createBicicleta(newEntity);
        Assert.assertNotNull(result);
        BicicletaEntity entity = em.find(BicicletaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getReferencia(), entity.getReferencia());
    }

    /**
     * Prueba para crear una bicicleta con la misma referencia de otra Bicicleta
     * que ya existe.
     *
     * @throws BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createBicicletaConMismaReferenciaTest() throws BusinessLogicException {
        BicicletaEntity newEntity = factory.manufacturePojo(BicicletaEntity.class);
        
        newEntity.setCategoria(dataCategorias.get(0));
        newEntity.setMarca(dataMarcas.get(0));
     
        Double precio = newEntity.getPrecio();
        newEntity.setPrecio(precio < 0.0 ? precio * -1 : precio);
        Integer stock = newEntity.getStock();
         newEntity.setStock(stock < 0 ? stock * -1 : stock);
        
        newEntity.setReferencia(data.get(0).getReferencia());
        bicicletaLogic.createBicicleta(newEntity);
    }

    /**
     * Prueba para eliminar una Bicicleta.
     *
     * @throws BusinessLogicException
     */
    @Test
    public void deleteBicicletaTest() throws BusinessLogicException {
        BicicletaEntity entity = data.get(1);
        bicicletaLogic.deleteBicicleta(entity.getId());
        BicicletaEntity deleted = em.find(BicicletaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    @Test
    public void getBicicletaTest() {
        BicicletaEntity entity = data.get(0);
        BicicletaEntity resultado = bicicletaLogic.getBicicleta(entity.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entity.getId(), resultado.getId());
    }

    @Test
    public void getBicicletasTest() {
        List<BicicletaEntity> bicicletasEncontradas = bicicletaLogic.getBicicletas();
        Assert.assertEquals(data.size(), bicicletasEncontradas.size());
        boolean existe = false;
        for (BicicletaEntity r : data) {
            for (BicicletaEntity r2 : bicicletasEncontradas) {
                if (r.getId().equals(r2.getId())) {
                    existe = true;
                    break;
                }
            }
        }
        Assert.assertTrue(existe);
    }

    @Test
    public void getBicicletaPorReferenciaTest() {
        BicicletaEntity entity = data.get(0);
        BicicletaEntity resultado = bicicletaLogic.getBicicletaPorReferencia(entity.getReferencia());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entity.getReferencia(), resultado.getReferencia());
    }

    @Test
    public void updateBicicletaTest() throws BusinessLogicException {

        Long idActualizar = data.get(0).getId();
        BicicletaEntity nuevaB = factory.manufacturePojo(BicicletaEntity.class);
        
        CategoriaEntity cat = factory.manufacturePojo(CategoriaEntity.class);
        MarcaEntity marca = factory.manufacturePojo(MarcaEntity.class);
        nuevaB.setCategoria(cat);
        nuevaB.setMarca(marca);
        Double precio = nuevaB.getPrecio();
        nuevaB.setPrecio(precio < 0.0 ? precio * -1 : precio);
        Integer stock = nuevaB.getStock();
         nuevaB.setStock(stock < 0 ? stock * -1 : stock);
        
        nuevaB.setId(idActualizar);
        bicicletaLogic.ubdateBicicleta(nuevaB);

        BicicletaEntity recuperada = bicicletaLogic.getBicicleta(idActualizar);

        Assert.assertEquals(nuevaB.getPrecio(), recuperada.getPrecio());
        Assert.assertEquals(nuevaB.getReferencia(), recuperada.getReferencia());
    }
}
