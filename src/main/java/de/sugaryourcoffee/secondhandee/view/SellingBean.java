package de.sugaryourcoffee.secondhandee.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.sugaryourcoffee.secondhandee.model.Selling;
import de.sugaryourcoffee.secondhandee.model.Event;
import de.sugaryourcoffee.secondhandee.model.Item;
import java.util.Iterator;

/**
 * Backing bean for Selling entities.
 * <p>
 * This class provides CRUD functionality for all Selling entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class SellingBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Selling entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Selling selling;

   public Selling getSelling()
   {
      return this.selling;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.selling = this.example;
      }
      else
      {
         this.selling = findById(getId());
      }
   }

   public Selling findById(Long id)
   {

      return this.entityManager.find(Selling.class, id);
   }

   /*
    * Support updating and deleting Selling entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.selling);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.selling);
            return "view?faces-redirect=true&id=" + this.selling.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         Selling deletableEntity = findById(getId());
         Event event = deletableEntity.getEvent();
         event.getSellings().remove(deletableEntity);
         deletableEntity.setEvent(null);
         this.entityManager.merge(event);
         Iterator<Item> iterItems = deletableEntity.getItems().iterator();
         for (; iterItems.hasNext();)
         {
            Item nextInItems = iterItems.next();
            nextInItems.setSelling(null);
            iterItems.remove();
            this.entityManager.merge(nextInItems);
         }
         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Selling entities with pagination
    */

   private int page;
   private long count;
   private List<Selling> pageItems;

   private Selling example = new Selling();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Selling getExample()
   {
      return this.example;
   }

   public void setExample(Selling example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Selling> root = countCriteria.from(Selling.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Selling> criteria = builder.createQuery(Selling.class);
      root = criteria.from(Selling.class);
      TypedQuery<Selling> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Selling> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      int sellingNumber = this.example.getSellingNumber();
      if (sellingNumber != 0)
      {
         predicatesList.add(builder.equal(root.get("sellingNumber"), sellingNumber));
      }
      Event event = this.example.getEvent();
      if (event != null)
      {
         predicatesList.add(builder.equal(root.get("event"), event));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Selling> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Selling entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Selling> getAll()
   {

      CriteriaQuery<Selling> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Selling.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Selling.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final SellingBean ejbProxy = this.sessionContext.getBusinessObject(SellingBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Selling) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Selling add = new Selling();

   public Selling getAdd()
   {
      return this.add;
   }

   public Selling getAdded()
   {
      Selling added = this.add;
      this.add = new Selling();
      return added;
   }
}