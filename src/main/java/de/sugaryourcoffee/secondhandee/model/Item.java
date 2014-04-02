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
import de.sugaryourcoffee.secondhandee.model.ItemList;
import javax.persistence.ManyToOne;
import de.sugaryourcoffee.secondhandee.model.Selling;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Item implements Serializable
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
   private int itemNumber;

   @Column
   @NotNull
   private String description;

   @Column
   @NotNull
   private String size;

   @Column
   @NotNull
   private Double price;

   @Column
   private boolean sold;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;

   @ManyToOne
   private ItemList itemList;

   @ManyToOne
   private Selling selling;

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
         return id.equals(((Item) that).id);
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

   public int getItemNumber()
   {
      return this.itemNumber;
   }

   public void setItemNumber(final int itemNumber)
   {
      this.itemNumber = itemNumber;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public String getSize()
   {
      return this.size;
   }

   public void setSize(final String size)
   {
      this.size = size;
   }

   public Double getPrice()
   {
      return this.price;
   }

   public void setPrice(final Double price)
   {
      this.price = price;
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
      result += "itemNumber: " + itemNumber;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (size != null && !size.trim().isEmpty())
         result += ", size: " + size;
      if (price != null)
         result += ", price: " + price;
      result += ", sold: " + sold;
      return result;
   }

   public ItemList getItemList()
   {
      return this.itemList;
   }

   public void setItemList(final ItemList itemList)
   {
      this.itemList = itemList;
   }

   public Selling getSelling()
   {
      return this.selling;
   }

   public void setSelling(final Selling selling)
   {
      this.selling = selling;
   }
}