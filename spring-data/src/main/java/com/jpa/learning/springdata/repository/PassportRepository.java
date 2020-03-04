package com.jpa.learning.springdata.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jpa.learning.springdata.model.Passport;
import com.jpa.learning.springdata.resourceObject.PassportRequest;
import com.jpa.learning.springdata.resourceObject.PassportResponse;

@Repository
@Transactional
public class PassportRepository {

	@Autowired
	EntityManager em;

	public List<PassportResponse> getAllPassports() {
		List<Passport> passports = em.createNamedQuery("get_all_passports").getResultList();
		ModelMapper modelMapper = new ModelMapper();
		List<PassportResponse> list = Arrays.asList(modelMapper.map(passports, PassportResponse[].class));
		return list;
	}

	public PassportResponse addorUpdatePassport(PassportRequest request) {
		Passport passport = null != request.getId() ? em.find(Passport.class, request.getId()) : null;
		ModelMapper modelMapper = new ModelMapper();
		Passport passportEntity = modelMapper.map(request, Passport.class);
		if (Objects.isNull(passport)) {
			em.persist(passportEntity);
		} else {
			em.merge(passportEntity);
		}
		return modelMapper.map(passportEntity, PassportResponse.class);
	}

	public String deletePassport(Long id) {
		Passport passport = em.find(Passport.class, id);
		String message = Strings.EMPTY;
		if (Objects.isNull(passport)) {
			message = "No passport with this Id found to delete !!";
		} else {
			em.remove(passport);
			message = "Passport with given id is deleted : " + id;
		}

		return message;
	}

}
