package com.devsuperior.dslearnbds.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.devsuperior.dslearnbds.entities.pk.EnrollmentPK;

@Entity
@Table(name = "tb_enrollment")
public class Enrollment {
	
	@EmbeddedId
	private EnrollmentPK id = new EnrollmentPK();
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant enrollMoment;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant refundMoment;
	private boolean available;
	private boolean onlyUpdate;
	@ManyToMany(mappedBy = "enrollmentsDone")
	private Set<Lesson> lessonsDone = new HashSet<>();
	@OneToMany(mappedBy = "enrollment")
	private List<Deliver> deliveries = new ArrayList<>();
	
	public Enrollment() {
	}

	public Enrollment(User user, Offer offer, Instant entollMoment, Instant refundMoment, boolean avaliable,
			boolean onlyUpdate) {
		super();
		id.setUser(user);
		id.setOffer(offer);
		this.enrollMoment = entollMoment;
		this.refundMoment = refundMoment;
		this.available = avaliable;
		this.onlyUpdate = onlyUpdate;
	}
	
	public List<Deliver> getDeliveries() {
		return deliveries;
	}

	public User getStudant() {
		return id.getUser();
	}
	
	public void setStudant(User user) {
		id.setUser(user);
	}
	
	public Offer getOffer() {
		return id.getOffer();
	}
	
	public void setOffer(Offer offer) {
		id.setOffer(offer);
	}

	public Instant getEntollMoment() {
		return enrollMoment;
	}

	public void setEntollMoment(Instant entollMoment) {
		this.enrollMoment = entollMoment;
	}

	public Instant getRefundMoment() {
		return refundMoment;
	}

	public void setRefundMoment(Instant refundMoment) {
		this.refundMoment = refundMoment;
	}

	public boolean isAvaliable() {
		return available;
	}

	public void setAvaliable(boolean avaliable) {
		this.available = avaliable;
	}

	public boolean isOnlyUpdate() {
		return onlyUpdate;
	}

	public void setOnlyUpdate(boolean onlyUpdate) {
		this.onlyUpdate = onlyUpdate;
	}
}
