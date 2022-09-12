package com.dislinkt.authapi.service.authority;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dislinkt.authapi.domain.authority.Authority;
import com.dislinkt.authapi.repository.AuthorityRepository;

@Service
public class AuthorityService{

	@Autowired 
	private AuthorityRepository authorityRepository;
	
	public List<Authority> findAll() {
		
		return authorityRepository.findAll();
	}

	public Authority findOne(Long id) {
		return authorityRepository.findById(id).orElse(null);
	}

	public Authority create(Authority entity) throws Exception {
		Authority a = new Authority();
		a.setRole(entity.getRole());
		return authorityRepository.insert(a);
	}

	public Authority update(Authority entity, Long id) throws Exception {
		Authority existingA = authorityRepository.findById(id).orElse(null);
		if(existingA == null) {
			throw new Exception("Authority with given id doesn't exist");
		}
		existingA.setRole(entity.getRole());
		return authorityRepository.save(existingA);
	}

	public void delete(Long id) throws Exception {
		Optional<Authority> optA = authorityRepository.findById(id);
		if(!optA.isPresent()) {
			throw new Exception("Authority with given id doesn't exist");
		}
		Authority existingA = optA.orElse(null);		
		authorityRepository.delete(existingA);		
		
	}
	public Authority findByRole(String role) throws Exception {
		return  authorityRepository.findByRole(role);
		
	}

    public Set<Authority> findById(Long id) {
        Authority auth = this.authorityRepository.findById(id).orElse(null);
        Set<Authority> auths = new HashSet<Authority>();
        if(auth == null) {
        	return auths;
        }
        auths.add(auth);
        return auths;
    }

    public Set<Authority> findByName(String name) {
        Authority auth = this.authorityRepository.findByRole(name);
        Set<Authority> auths = new HashSet<Authority>();
        auths.add(auth);
        return auths;
    }

}
