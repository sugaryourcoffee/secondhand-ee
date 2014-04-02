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
import de.sugaryourcoffee.secondhandee.model.Item;

/**
 * 
 */
@Stateless
@Path("/items")
public class ItemEndpoint
{
   @PersistenceContext(unitName = "secondhandeePU")
   private EntityManager em;

   @POST
   @Consumes("application/xml")
   public Response create(Item entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(ItemEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Item entity = em.find(Item.class, id);
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
      TypedQuery<Item> findByIdQuery = em.createQuery("SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.itemList LEFT JOIN FETCH i.selling WHERE i.id = :entityId ORDER BY i.id", Item.class);
      findByIdQuery.setParameter("entityId", id);
      Item entity;
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
   public List<Item> listAll()
   {
      final List<Item> results = em.createQuery("SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.itemList LEFT JOIN FETCH i.selling ORDER BY i.id", Item.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(Item entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}