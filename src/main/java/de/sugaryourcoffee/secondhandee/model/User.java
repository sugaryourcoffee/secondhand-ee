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
import de.sugaryourcoffee.secondhandee.model.ItemList;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class User implements Serializable
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
   @NotNull
   private String surname;

   @Column
   @NotNull
   private String givenName;

   @Column
   @NotNull
   private String email;

   @Column
   @NotNull
   private String phone;

   @Column
   @NotNull
   private String street;

   @Column
   @NotNull
   private String zipCode;

   @Column
   @NotNull
   private String town;

   @Column
   @NotNull
   private String country;

   @Column
   private boolean admin;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updatedAt;

   @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<ItemList> itemLists = new HashSet<ItemList>();

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
         return id.equals(((User) that).id);
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

   public String getSurname()
   {
      return this.surname;
   }

   public void setSurname(final String surname)
   {
      this.surname = surname;
   }

   public String getGivenName()
   {
      return this.givenName;
   }

   public void setGivenName(final String givenName)
   {
      this.givenName = givenName;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getPhone()
   {
      return this.phone;
   }

   public void setPhone(final String phone)
   {
      this.phone = phone;
   }

   public String getStreet()
   {
      return this.street;
   }

   public void setStreet(final String street)
   {
      this.street = street;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getTown()
   {
      return this.town;
   }

   public void setTown(final String town)
   {
      this.town = town;
   }

   public String getCountry()
   {
      return this.country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   public boolean getAdmin()
   {
      return this.admin;
   }

   public void setAdmin(final boolean admin)
   {
      this.admin = admin;
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
      if (surname != null && !surname.trim().isEmpty())
         result += "surname: " + surname;
      if (givenName != null && !givenName.trim().isEmpty())
         result += ", givenName: " + givenName;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (street != null && !street.trim().isEmpty())
         result += ", street: " + street;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (town != null && !town.trim().isEmpty())
         result += ", town: " + town;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      result += ", admin: " + admin;
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
}