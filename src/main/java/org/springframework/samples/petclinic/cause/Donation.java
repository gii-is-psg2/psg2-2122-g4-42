package org.springframework.samples.petclinic.cause;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {
    
    @Column(name = "donation_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    @Min(0)
    @Column(name = "amount")
    private double amount;

    @NotNull
    @NotEmpty
    @Column(name = "client")
    private String client;
    
    @ManyToOne
    @JoinColumn(name = "cause_id")
    private Cause cause;

    public Donation() {
        this.date = LocalDate.now();
    }
}
