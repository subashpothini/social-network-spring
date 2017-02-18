package org.asaunin.socialnetwork.repository;

import org.asaunin.socialnetwork.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

	Person findByShortName(String shortName);

	Person findByEmail(String email);

	Optional<Person> findById(Long id);

	@Query("SELECT p FROM Person p " +
			"WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findPeople(
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);

	@Query("SELECT p FROM Person p " +
			"WHERE (:person) MEMBER OF p.friendOf " +
			"   AND LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findFriends(
			@Param("person") Person person,
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);

	@Query("SELECT p FROM Person p " +
			"WHERE (:person) MEMBER OF p.friends " +
			"   AND LOWER(p.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"ORDER BY p.fullName")
	Page<Person> findFriendOf(
			@Param("person") Person person,
			@Param("searchTerm") String searchTerm,
			Pageable pageRequest);

}