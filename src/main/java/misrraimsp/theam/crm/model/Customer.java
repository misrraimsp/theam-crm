package misrraimsp.theam.crm.model;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Entity
public class Customer extends AuditableEntity {

    @NotBlank(message = "Customer's Name needs not to be empty")
    @Pattern(regexp = TEXT_BASIC)
    private String name;

    @NotBlank(message = "Customer's Surname needs not to be empty")
    @Pattern(regexp = TEXT_BASIC)
    private String surname;

    @OneToOne
    @Valid
    private Image image;

    public String getImageUrl() {
        return (image != null) ? "/user/customers/" + this.id + "/image" : null;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getImageId() {
        return (this.image != null) ? this.image.getId() : null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String toString() {
        return "Customer(name=" + this.getName() + ", surname=" + this.getSurname() + ", imageUrl=" + this.getImageUrl() + ")";
    }
}
