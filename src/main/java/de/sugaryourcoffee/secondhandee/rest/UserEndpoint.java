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
import de.sugaryourcoffee.secondhandee.model.User;

/**
 * 
 */
@Stateless
@Path("/users")
public class UserEndpoint
{
   @PersistenceContext(unitName = "secondhandeePU")
   private EntityManager em;

   @POST
   @Consumes("application/xml")
   public Response create(User entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(UserEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      User entity = em.find(User.class, id);
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
      TypedQuery<User> findByIdQuery = em.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.itemLists WHERE u.id = :entityId ORDER BY u.id", User.class);
      findByIdQuery.setParameter("entityId", id);
      User entity;
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
   public List<User> listAll()
   {
      final List<User> results = em.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.itemLists ORDER BY u.id", User.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/xml")
   public Response update(User entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}