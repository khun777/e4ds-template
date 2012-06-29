package ch.rasc.e4ds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import ch.rasc.e4ds.entity.LoggingEvent;

public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long>,
		QueryDslPredicateExecutor<LoggingEvent> {

	List<LoggingEvent> findByLevelString(String level);
}
