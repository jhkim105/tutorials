package jhkim105.tutorials.osiv.repository;


import jhkim105.tutorials.osiv.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

  @EntityGraph("User.roles")
  User getWithRolesById(String id);

  @Query("select u from User u join fetch u.roles where u.id = :id")
  User getUsingFetchJoin(@Param("id") String id);

}
