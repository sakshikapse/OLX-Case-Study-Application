package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Advertise;
import com.olx.entity.AdvertiseEntity;
import com.olx.exception.InvalidAdvertiseIdException;
import com.olx.exception.InvalidAuthorizationToken;
import com.olx.repository.AdvertiseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdvertiseServiceImpl implements AdvertiseService {

	@Autowired
	private AdvertiseRepository advertiseRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MasterDataDelegate masterDataDelegate;

	// Autowiring login delegate
	@Autowired
	LoginDelegate loginDelegate;

	@Autowired
	EntityManager entityManager;

	@Override
	public List<Advertise> getAllAdvertise() {
		List<AdvertiseEntity> advertiseEntityList = advertiseRepository.findAll();
		List<Advertise> advertiseDtoList = new ArrayList<Advertise>();
		for (AdvertiseEntity advertiseEntity : advertiseEntityList) {
			Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
			advertiseDtoList.add(advertiseDto);
		}
		// log.info("Successfully fetched all the records");
		return advertiseDtoList;
	}

	@Override
	public Advertise createNewAdvertise(String authToken, Advertise advertise) {
		// Call OLX-Login
		// I have to validate the token before I save the status in the database system
		// We have to confirm first whether authToken is valid
		// Any REST service which is demanding auth token in header you have to first
		// validate the authToken before executing any business logic.
		// So in service layer you have to validate authToken by writing a new interface
		// named LoginDelegate
		// And in the LoginDelegate interface I will validate the token.

		if (!loginDelegate.isValidToken(authToken)) {
			// Custom message in global exception handler
			throw new InvalidAuthorizationToken(authToken);
			// String username = jwtUtil.getUsername(authToken);
		}

		AdvertiseEntity advertiseEntity = this.modelMapper.map(advertise, AdvertiseEntity.class);
		advertiseEntity.setActive("1");
		advertiseEntity.setPostedBy("Sakshi kapse");
		advertiseEntity.setCreatedDate(advertiseEntity.getCreatedDate().now());
		advertiseEntity.setModifiedDate(advertiseEntity.getCreatedDate().now());
		advertiseEntity = this.advertiseRepository.save(advertiseEntity);
		Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
		advertiseDto = addStatusNameandCategory(advertiseDto);

		// Call OLX-Masterdata
		// String statusText =
		// masterDataDelegate.getStatusById(advertise.getStatusId());
		// String categoryText =
		// masterDataDelegate.getCategoryById(advertise.getCategoryId());
		// advertise.setStatus(statusText);
		// advertise.setCategory(categoryText);
		// return advertise;
		return advertiseDto;

	}

	public Advertise addStatusNameandCategory(Advertise advertise) {
		String statusName = this.masterDataDelegate.getStatusById(advertise.getStatusId());
		String categoryName = this.masterDataDelegate.getCategoryById(advertise.getCategoryId());
		advertise.setStatus(statusName);
		advertise.setCategory(categoryName);
		return advertise;
	}

	@Override
	public Advertise updateAdvertise(int id, Advertise advertise, String authToken) {
		if (!loginDelegate.isValidToken(authToken)) {
			// Custom message in global exception handler
			throw new InvalidAuthorizationToken(authToken);
			// String username = jwtUtil.getUsername(authToken);
		}
		Optional<AdvertiseEntity> optionalAdvertiseEntity = advertiseRepository.findById(id);
		if (optionalAdvertiseEntity.isPresent()) {
			AdvertiseEntity advertiseEntity = optionalAdvertiseEntity.get();
			advertise.setId(id);
			advertiseEntity = this.modelMapper.map(advertise, AdvertiseEntity.class);
			advertiseEntity.setActive("1");
			advertiseEntity.setPostedBy("Sakshi kapse");
			advertiseEntity.setCreatedDate(advertiseEntity.getCreatedDate().now());
			advertiseEntity.setModifiedDate(advertiseEntity.getCreatedDate().now());
			advertiseEntity = advertiseRepository.save(advertiseEntity);
			Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
			return advertiseDto;
		}
		throw new InvalidAdvertiseIdException(" " + id);
	}

	@Override
	public boolean deleteAdvertiseById(int advertiseId, String authToken) {
		if (!loginDelegate.isValidToken(authToken)) {
			throw new InvalidAuthorizationToken(authToken);
		}
		Optional<AdvertiseEntity> optionalAdvertiseEntity = advertiseRepository.findById(advertiseId);
		if (optionalAdvertiseEntity.isPresent()) {
			advertiseRepository.deleteById(advertiseId);
			return true;
		}
		// return false;
		throw new InvalidAdvertiseIdException(" " + advertiseId);
	}

	@Override
	public Advertise getAdvertiseById(String authToken, int advertiseId) {
		if (!loginDelegate.isValidToken(authToken)) {
			throw new InvalidAuthorizationToken(authToken);
		}
		Optional<AdvertiseEntity> optionalAdvertiseEntity = advertiseRepository.findById(advertiseId);
		if (optionalAdvertiseEntity.isPresent()) {
			AdvertiseEntity advertiseEntity = optionalAdvertiseEntity.get();
			Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
			advertiseDto = addStatusNameandCategory(advertiseDto);
			return advertiseDto;
		}
		throw new InvalidAdvertiseIdException(" " + advertiseId);
	}

	@Override
	public List<Advertise> getAllAdvertisesByUser(String authToken) {
		if (!loginDelegate.isValidToken(authToken)) {
			throw new InvalidAuthorizationToken(authToken);
		}
		List<AdvertiseEntity> advertiseEntity = this.advertiseRepository.findAll();
		// Correct this method
		return getAdvertiseDtoList(advertiseEntity);
	}

	private List<Advertise> getAdvertiseDtoList(List<AdvertiseEntity> advertiseEntity) {
		List<Advertise> advertiseDtoList = new ArrayList<Advertise>();
		for (AdvertiseEntity advertisesEntity : advertiseEntity) {
			Advertise advertiseDto = this.modelMapper.map(advertisesEntity, Advertise.class);
			advertiseDtoList.add(advertiseDto);
		}
		return advertiseDtoList;
	}

	@Override
	public List<Advertise> getAdvertiseByFilter(String searchText, int category, int postedBy, String dateCondition,
			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, int startIndex, int records) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);

		Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);

		Predicate finalPredicate = null;

		if (searchText != null && !"".equals(searchText)) {
			Predicate predicateTitle = criteriaBuilder.like(rootEntity.get("title"), searchText);
			finalPredicate = criteriaBuilder.and(predicateTitle);
			Predicate predicateDescription = criteriaBuilder.like(rootEntity.get("description"), searchText);
			finalPredicate = criteriaBuilder.and(predicateDescription);
		}
		Predicate predicateCategory = criteriaBuilder.like(rootEntity.get("categoryId"), searchText);
		finalPredicate = criteriaBuilder.and(predicateCategory);

		// if(postedBy != null && !"".equals(postedBy)) {
		// Predicate predicatePostedBy =
		// criteriaBuilder.like(rootEntity.get("postBy"),postedBy);
		// finalPredicate = criteriaBuilder.and(predicatePostedBy);
		// }

		criteriaQuery.where(finalPredicate);
		TypedQuery<AdvertiseEntity> query = entityManager.createQuery(criteriaQuery);
		List<AdvertiseEntity> advertiseEntityList = query.getResultList();

	}

	@Override
	public List<Advertise> getAdvertiseBySearch(String searchText) {
		List<AdvertiseEntity> advertiseEntityList = this.advertiseRepository.findBySearch(searchText);
		return getAdvertiseDtoList(advertiseEntityList);
	}

	@Override
	public Advertise getAdvertiseByUserById(String authToken, int id) {
		if (!loginDelegate.isValidToken(authToken)) {
			throw new InvalidAuthorizationToken(authToken);
		}
		Optional<AdvertiseEntity> optionalAdvertiseEntity = advertiseRepository.findById(id);
		if (optionalAdvertiseEntity.isPresent()) {
			AdvertiseEntity advertiseEntity = optionalAdvertiseEntity.get();
			Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
			advertiseDto = addStatusNameandCategory(advertiseDto);
			return advertiseDto;
		}
		throw new InvalidAdvertiseIdException(" " + id);
	}

	/*
	 * String status =
	 * this.masterDataDelegate.getStatusById(advertiseEntity.getStatus()); String
	 * category =
	 * this.masterDataDelegate.getCategoryById(advertiseEntity.getCategory());
	 * 
	 * 
	 * Advertise advertise = new Advertise();
	 * advertise.setId(advertiseEntity.getId()); advertise.setCategory(category);
	 * advertise.setDescription(advertiseEntity.getDescription());
	 * advertise.setCreatedDate(advertiseEntity.getCreatedDate());
	 * advertise.setModifiedDate(advertiseEntity.getModifiedDate());
	 * advertise.setTitle(advertiseEntity.getTitle());
	 * advertise.setUsername(advertiseEntity.getUsername());
	 * advertise.setStatus(status); System.out.println(advertiseResp);
	 * AdvertiseEntity advertiseEntity = new AdvertiseEntity();
	 * advertiseEntity.setActive("true");
	 */

}

// added normal one
//	@Override
//	public Advertise createNewUser(Advertise advertise) {
//		AdvertiseEntity advertiseEntity = this.modelMapper.map(advertise, AdvertiseEntity.class);
//		advertiseEntity = this.advertiseRepository.save(advertiseEntity);
//		Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
//		log.info("Successfully inserted a new record");
//		return advertiseDto;
//	}
