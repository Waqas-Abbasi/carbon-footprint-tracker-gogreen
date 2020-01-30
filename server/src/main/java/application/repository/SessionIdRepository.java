package application.repository;

  import application.model.Session;
  import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionIdRepository extends JpaRepository<Session, Integer> {
  Session findBySessionId(String sessionId);
}
