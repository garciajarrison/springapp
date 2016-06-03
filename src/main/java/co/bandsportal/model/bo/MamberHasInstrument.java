package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="member_has_instrument") 
public class MamberHasInstrument implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_member")
	private Member member;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_instrument")
	private Instrument instrument;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_band")
	private Band band;
	
	public MamberHasInstrument(){
		band = new Band();
		member = new Member();
		instrument = new Instrument();
	}

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Instrument getInstrument() {
		return instrument;
	}
	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	public Band getBand() {
		return band;
	}
	public void setBand(Band band) {
		this.band = band;
	}	
	

}
