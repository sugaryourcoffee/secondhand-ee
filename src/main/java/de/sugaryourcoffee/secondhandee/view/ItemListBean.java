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

import de.sugaryourcoffee.secondhandee.model.ItemList;
import de.sugaryourcoffee.secondhandee.model.Event;
import de.sugaryourcoffee.secondhandee.model.Item;
import de.sugaryourcoffee.secondhandee.model.User;
import java.util.Iterator;

/**
 * Backing bean for ItemList entities.
 * <p>
 * This class provides CRUD functionality for all ItemList entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class ItemListBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving ItemList entities
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

   private ItemList itemList;

   public ItemList getItemList()
   {
      return this.itemList;
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
         this.itemList = this.example;
      }
      else
      {
         this.itemList = findById(getId());
      }
   }

   public ItemList findById(Long id)
   {

      return this.entityManager.find(ItemList.class, id);
   }

   /*
    * Support updating and deleting ItemList entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.itemList);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.itemList);
            return "view?faces-redirect=true&id=" + this.itemList.getId();
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
         ItemList deletableEntity = findById(getId());
         User seller = deletableEntity.getSeller();
         seller.getItemLists().remove(deletableEntity);
         deletableEntity.setSeller(null);
         this.entityManager.merge(seller);
         Event event = deletableEntity.getEvent();
         event.getItemLists().remove(deletableEntity);
         deletableEntity.setEvent(null);
         this.entityManager.merge(event);
         Iterator<Item> iterItems = deletableEntity.getItems().iterator();
         for (; iterItems.hasNext();)
         {
            Item nextInItems = iterItems.next();
            nextInItems.setItemList(null);
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
    * Support searching ItemList entities with pagination
    */

   private int page;
   private long count;
   private List<ItemList> pageItems;

   private ItemList example = new ItemList();

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

   public ItemList getExample()
   {
      return this.example;
   }

   public void setExample(ItemList example)
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
      Root<ItemList> root = countCriteria.from(ItemList.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<ItemList> criteria = builder.createQuery(ItemList.class);
      root = criteria.from(ItemList.class);
      TypedQuery<ItemList> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<ItemList> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      int listNumber = this.example.getListNumber();
      if (listNumber != 0)
      {
         predicatesList.add(builder.equal(root.get("listNumber"), listNumber));
      }
      String remarks = this.example.getRemarks();
      if (remarks != null && !"".equals(remarks))
      {
         predicatesList.add(builder.like(root.<String> get("remarks"), '%' + remarks + '%'));
      }
      String container = this.example.getContainer();
      if (container != null && !"".equals(container))
      {
         predicatesList.add(builder.like(root.<String> get("container"), '%' + container + '%'));
      }
      User seller = this.example.getSeller();
      if (seller != null)
      {
         predicatesList.add(builder.equal(root.get("seller"), seller));
      }
      Event event = this.example.getEvent();
      if (event != null)
      {
         predicatesList.add(builder.equal(root.get("event"), event));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<ItemList> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back ItemList entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<ItemList> getAll()
   {

      CriteriaQuery<ItemList> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(ItemList.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(ItemList.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final ItemListBean ejbProxy = this.sessionContext.getBusinessObject(ItemListBean.class);

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

            return String.valueOf(((ItemList) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private ItemList add = new ItemList();

   public ItemList getAdd()
   {
      return this.add;
   }

   public ItemList getAdded()
   {
      ItemList added = this.add;
      this.add = new ItemList();
      return added;
   }
}