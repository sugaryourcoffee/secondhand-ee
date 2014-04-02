package de.sugaryourcoffee.secondhandee.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import de.sugaryourcoffee.secondhandee.model.ItemList;

/**
 * 
 */
@Stateless
@Path("/itemlists")
public class ItemListEndpoint
{
   @PersistenceContext(unitName = "secondhandeePU")
   private EntityManager em;

   @POST
   @Consumes("application/xml")
   public Response create(ItemList entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(ItemListEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      ItemList entity = em.find(ItemList.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/xml")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<ItemList> findByIdQuery = em.createQuery("SELECT DISTINCT i FROM ItemList i LEFT JOIN FETCH i.seller LEFT JOIN FETCH i.event LEFT JOIN FETCH i.items WHERE i.id = :entityId ORDER BY i.id", ItemList.class);
      findByIdQuery.setParameter("entityId", id);
      ItemList entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/xml")
   public List<ItemList> listAll()
   {
      final List<ItemList> results = em.createQuery("SELECT DISTINCT i FROM ItemList i LEFT JOIN FETCH i.seller LEFT JOIN FETCH i.event LEFT JOIN FETCH i.items ORDER BY i.id", ItemList.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(ItemList entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}