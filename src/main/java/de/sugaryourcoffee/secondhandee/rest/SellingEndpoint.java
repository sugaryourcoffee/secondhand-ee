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
import de.sugaryourcoffee.secondhandee.model.Selling;

/**
 * 
 */
@Stateless
@Path("/sellings")
public class SellingEndpoint
{
   @PersistenceContext(unitName = "secondhandeePU")
   private EntityManager em;

   @POST
   @Consumes("application/xml")
   public Response create(Selling entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(SellingEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Selling entity = em.find(Selling.class, id);
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
      TypedQuery<Selling> findByIdQuery = em.createQuery("SELECT DISTINCT s FROM Selling s LEFT JOIN FETCH s.event LEFT JOIN FETCH s.items WHERE s.id = :entityId ORDER BY s.id", Selling.class);
      findByIdQuery.setParameter("entityId", id);
      Selling entity;
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
   public List<Selling> listAll()
   {
      final List<Selling> results = em.createQuery("SELECT DISTINCT s FROM Selling s LEFT JOIN FETCH s.event LEFT JOIN FETCH s.items ORDER BY s.id", Selling.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(Selling entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}