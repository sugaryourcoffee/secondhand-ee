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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import de.sugaryourcoffee.secondhandee.model.ItemList;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import de.sugaryourcoffee.secondhandee.model.Selling;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Event implements Serializable
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

   @Temporal(TemporalType.TIMESTAMP)
   @NotNull
   private Date eventDate;

   @Column
   @NotNull
   private String location;

   @Column
   @DecimalMin("1")
   private int maxLists;

   @Column
   @DecimalMin("1")
   private int maxItemsPerList;

   @Column
   @DecimalMin("1")
   private Double deduction;

   @Column
   @DecimalMin("1")
   private Double fee;

   @Column
   @DecimalMin("1")
   private Double provision;

   @Column
   private boolean sold;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;

   @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<ItemList> itemLists = new HashSet<ItemList>();

   @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Selling> sellings = new HashSet<Selling>();

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
         return id.equals(((Event) that).id);
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

   public Date getEventDate()
   {
      return this.eventDate;
   }

   public void setEventDate(final Date eventDate)
   {
      this.eventDate = eventDate;
   }

   public String getLocation()
   {
      return this.location;
   }

   public void setLocation(final String location)
   {
      this.location = location;
   }

   public int getMaxLists()
   {
      return this.maxLists;
   }

   public void setMaxLists(final int maxLists)
   {
      this.maxLists = maxLists;
   }

   public int getMaxItemsPerList()
   {
      return this.maxItemsPerList;
   }

   public void setMaxItemsPerList(final int maxItemsPerList)
   {
      this.maxItemsPerList = maxItemsPerList;
   }

   public Double getDeduction()
   {
      return this.deduction;
   }

   public void setDeduction(final Double deduction)
   {
      this.deduction = deduction;
   }

   public Double getFee()
   {
      return this.fee;
   }

   public void setFee(final Double fee)
   {
      this.fee = fee;
   }

   public Double getProvision()
   {
      return this.provision;
   }

   public void setProvision(final Double provision)
   {
      this.provision = provision;
   }

   public boolean getSold()
   {
      return this.sold;
   }

   public void setSold(final boolean sold)
   {
      this.sold = sold;
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
      if (location != null && !location.trim().isEmpty())
         result += "location: " + location;
      result += ", maxLists: " + maxLists;
      result += ", maxItemsPerList: " + maxItemsPerList;
      if (deduction != null)
         result += ", deduction: " + deduction;
      if (fee != null)
         result += ", fee: " + fee;
      if (provision != null)
         result += ", provision: " + provision;
      result += ", sold: " + sold;
      return result;
   }

   public Set<ItemList> getItemLists()
   {
      return this.itemLists;
   }

   public void setItemLists(final Set<ItemList> itemLists)
   {
      this.itemLists = itemLists;
   }

   public Set<Selling> getSellings()
   {
      return this.sellings;
   }

   public void setSellings(final Set<Selling> sellings)
   {
      this.sellings = sellings;
   }
}