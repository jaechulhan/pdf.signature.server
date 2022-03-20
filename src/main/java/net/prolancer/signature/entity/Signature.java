package net.prolancer.signature.entity;

import net.prolancer.signature.common.validator.CheckDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Signature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sign Image should not be null or empty
    @NotEmpty
    private String signImage;

    // Sign Date should not be null or empty
    // Sign Date should be MM/dd/yyyy
    @NotEmpty
    @CheckDateFormat(pattern = "MM/dd/yyyy", message = "{validation.signdate.dateformat.error}")
    private String signDate;

    public Signature() {
    }

    public Signature(String signImage, String signDate) {
        this.signImage = signImage;
        this.signDate = signDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSignImage() {
        return signImage;
    }

    public void setSignImage(String signImage) {
        this.signImage = signImage;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "id=" + id +
                ", signImage='" + signImage + '\'' +
                ", signDate='" + signDate + '\'' +
                '}';
    }
}
