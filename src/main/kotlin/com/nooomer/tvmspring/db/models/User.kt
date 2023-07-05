package com.nooomer.tvmspring.db.models

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
open class User(
    @Column(name = "surename", nullable = false, length = Integer.MAX_VALUE)
    open var surename: String,
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    open var name: String,
    @Column(name = "s_name", nullable = false, length = Integer.MAX_VALUE)
    open var sName: String,
    @Column(name = "phone_number", nullable = false, length = Integer.MAX_VALUE)
    open var phoneNumberP: String,
    @Column(name = "insurance_policy_number", nullable = false, length = Integer.MAX_VALUE)
    open var insurancePolicyNumber: String,
    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    open var passwordP: String,
    @Column(name = "user_type", length = Integer.MAX_VALUE)
    open var userType: String,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    @Fetch(FetchMode.SUBSELECT)
    open var roles: MutableSet<Role>,
) : Base(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.name))
        }
        return authorities
    }

    override fun getPassword(): String {
        return passwordP
    }

    override fun getUsername(): String {
        return phoneNumberP
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}