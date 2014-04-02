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
import de.sugaryourcoffee.secondhandee.model.Event;
import javax.persistence.ManyToOne;
import de.sugaryourcoffee.secondhandee.model.Item;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Selling implements Serializable
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
   private int sellingNumber;

   @Temporal(TemporalType.TIMESTAMP)
   private Date sellingDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;

   @ManyToOne
   private Event event;

   @OneToMany(mappedBy = "selling", cascade = CascadeType.ALL, orphanRemoval = true)
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
         return id.equals(((Selling) that).id);
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

   public int getSellingNumber()
   {
      return this.sellingNumber;
   }

   public void setSellingNumber(final int sellingNumber)
   {
      this.sellingNumber = sellingNumber;
   }

   public Date getSellingDate()
   {
      return this.sellingDate;
   }

   public void setSellingDate(final Date sellingDate)
   {
      this.sellingDate = sellingDate;
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
      result += "sellingNumber: " + sellingNumber;
      return result;
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