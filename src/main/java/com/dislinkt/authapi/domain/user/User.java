package com.dislinkt.authapi.domain.user;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dislinkt.authapi.domain.authority.Authority;
import com.dislinkt.authapi.domain.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class User extends BaseEntity implements UserDetails{

	@Column(nullable = true)
	protected String type;

	@Column(nullable = true)
	protected String firstName;

	@Column(nullable = true)
	protected String lastName;

	@Column(unique = true)
	protected String username;
	
	@Column(nullable = true, unique=true)
	protected String email;

	@Column(unique=false,nullable = true)
	protected String password;

	@Column(nullable = true)
	protected Boolean active;

	@Column(nullable = true)
	protected Boolean verified;

	@ManyToMany(fetch = FetchType.EAGER)
	//@JoinColumn(name = "user_id", nullable = false)
	protected Set<Authority> authority;

	@Column(name = "last_password_reset_date",nullable = true)
    private Timestamp lastPasswordResetDate;
	
	


	public void setPassword(String password) {
		Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
		this.password = password;
	}

	
	@Override
    public boolean isEnabled() {
        return true;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authority;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String toString() {
		return "User [id=" + this.getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", active=" + active + ", verified=" + verified 
				+ ", authority=" + authority + ", lastPasswordResetDate=" + lastPasswordResetDate + "]";
	}

	

}