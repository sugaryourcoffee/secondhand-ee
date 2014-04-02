package de.sugaryourcoffee.secondhandee.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import de.sugaryourcoffee.secondhandee.model.User;
import javax.persistence.ManyToOne;
import de.sugaryourcoffee.secondhandee.model.Event;
import de.sugaryourcoffee.secondhandee.model.Item;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ItemList implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @DecimalMin("1")
   private int listNumber;

   @Column
   private String remarks;

   @Column
   @NotNull
   private String container;

   @Temporal(TemporalType.TIMESTAMP)
   private Date acceptanceDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date accountanceDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;

   @ManyToOne
   private User seller;

   @ManyToOne
   private Event event;

   @OneToMany(mappedBy = "itemList", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Item> items = new HashSet<Item>();

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((ItemList) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public int getListNumber()
   {
      return this.listNumber;
   }

   public void setListNumber(final int listNumber)
   {
      this.listNumber = listNumber;
   }

   public String getRemarks()
   {
      return this.remarks;
   }

   public void setRemarks(final String remarks)
   {
      this.remarks = remarks;
   }

   public String getContainer()
   {
      return this.container;
   }

   public void setContainer(final String container)
   {
      this.container = container;
   }

   public Date getAcceptanceDate()
   {
      return this.acceptanceDate;
   }

   public void setAcceptanceDate(final Date acceptanceDate)
   {
      this.acceptanceDate = acceptanceDate;
   }

   public Date getAccountanceDate()
   {
      return this.accountanceDate;
   }

   public void setAccountanceDate(final Date accountanceDate)
   {
      this.accountanceDate = accountanceDate;
   }

   public Date getCreatedAt()
   {
      return this.createdAt;
   }

   public void setCreatedAt(final Date createdAt)
   {
      this.createdAt = createdAt;
   }

   public Date getUpdatedAt()
   {
      return this.updatedAt;
   }

   public void setUpdatedAt(final Date updatedAt)
   {
      this.updatedAt = updatedAt;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "listNumber: " + listNumber;
      if (remarks != null && !remarks.trim().isEmpty())
         result += ", remarks: " + remarks;
      if (container != null && !container.trim().isEmpty())
         result += ", container: " + container;
      return result;
   }

   public User getSeller()
   {
      return this.seller;
   }

   public void setSeller(final User seller)
   {
      this.seller = seller;
   }

   public Event getEvent()
   {
      return this.event;
   }

   public void setEvent(final Event event)
   {
      this.event = event;
   }

   public Set<Item> getItems()
   {
      return this.items;
   }

   public void setItems(final Set<Item> items)
   {
      this.items = items;
   }
}