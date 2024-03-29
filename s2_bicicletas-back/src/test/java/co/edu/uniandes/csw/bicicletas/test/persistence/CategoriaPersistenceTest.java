/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicicletas.test.persistence;

import co.edu.uniandes.csw.bicicletas.entities.CategoriaEntity;
import co.edu.uniandes.csw.bicicletas.persistence.CategoriaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Andres Donoso
 */
@RunWith(Arquillian.class)
public class CategoriaPersistenceTest {
    @Inject
    private CategoriaPersistence cp;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<CategoriaEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(CategoriaPersistence.class.getPackage())
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
            em.joinTransaction();
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            CategoriaEntity entity = factory.manufacturePojo(CategoriaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
    public void createCategoriaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        CategoriaEntity resultado = cp.create(newEntity);
        
        Assert.assertNotNull(resultado);
        CategoriaEntity entity = em.find(CategoriaEntity.class, resultado.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void deleteCategoriaTest() {
        CategoriaEntity categoria = data.get(0);
        cp.delete(categoria.getId());
        CategoriaEntity borrado = em.find(CategoriaEntity.class, categoria.getId());
        Assert.assertNull(borrado);
    }
    
    @Test
    public void findByNameTest() {
        CategoriaEntity categoria = data.get(0);
        CategoriaEntity newEntity = cp.findByName(categoria.getNombre());
        
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), categoria.getNombre());
    }
    
    @Test
    public void updateTest() {
        CategoriaEntity categoria = data.get(0);
        String nombre = "nombre1";
        categoria.setNombre(nombre);
        
        cp.update(categoria);
        
        Assert.assertEquals(nombre, cp.find(data.get(0).getId()).getNombre());
    }
}
