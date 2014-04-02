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
import de.sugaryourcoffee.secondhandee.model.Event;

/**
 * 
 */
@Stateless
@Path("/events")
public class EventEndpoint
{
   @PersistenceContext(unitName = "secondhandeePU")
   private EntityManager em;

   @POST
   @Consumes("application/xml")
   public Response create(Event entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(EventEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Event entity = em.find(Event.class, id);
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
      TypedQuery<Event> findByIdQuery = em.createQuery("SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.itemLists LEFT JOIN FETCH e.sellings WHERE e.id = :entityId ORDER BY e.id", Event.class);
      findByIdQuery.setParameter("entityId", id);
      Event entity;
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
   public List<Event> listAll()
   {
      final List<Event> results = em.createQuery("SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.itemLists LEFT JOIN FETCH e.sellings ORDER BY e.id", Event.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(Event entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}